package sc2002.engine;

import java.util.List;

public class BattleEngine {
    private final TurnOrderStrategy turnOrderStrategy;
    private final PlayerDecisionPort playerDecisionPort;
    private final EnemyActionPolicy enemyActionPolicy;

    public BattleEngine(
        TurnOrderStrategy turnOrderStrategy,
        PlayerDecisionPort playerDecisionPort,
        EnemyActionPolicy enemyActionPolicy
    ) {
        this.turnOrderStrategy = turnOrderStrategy;
        this.playerDecisionPort = playerDecisionPort;
        this.enemyActionPolicy = enemyActionPolicy;
    }

    public BattleResult run(BattleState state) {
        BattleOutcome outcome = evaluateOutcome(state);
        int playerActions = 0;
        int enemyActions = 0;
        int basicAttacksUsed = 0;
        int defendUsed = 0;
        int specialSkillsUsed = 0;
        int itemsUsed = 0;
        int playerDamageDealt = 0;
        int playerDamageTaken = 0;
        int enemiesDefeated = 0;
        int enemyTurnsSkipped = 0;
        int backupSpawns = 0;
        boolean backupPreviouslySpawned = state.isBackupSpawned();

        while (outcome == BattleOutcome.ONGOING) {
            state.incrementRound();
            List<Combatant> order = turnOrderStrategy.order(state);

            for (Combatant actor : order) {
                if (!actor.isAlive()) {
                    continue;
                }

                // Cooldown decreases only when this combatant gets a turn.
                actor.tickSkillCooldown();
                actor.applyTurnStartEffects();

                outcome = evaluateOutcome(state);
                if (outcome != BattleOutcome.ONGOING) {
                    break;
                }

                if (!actor.consumeActionBlockedForTurn()) {
                    boolean actionSucceeded = false;

                    if (actor.getTeam() == Team.PLAYER) {
                        while (!actionSucceeded) {
                            PlannedAction action = chooseAction(state, actor);
                            if (action != null && action.action() != null) {
                                String actionName = action.action().name();
                                int enemyHpBefore = totalEnemyHp(state);
                                int aliveEnemiesBefore = countAliveEnemies(state);
                                actionSucceeded = action.action().execute(state, actor, action.target());
                                if (actionSucceeded) {
                                    playerActions++;
                                    if ("BasicAttack".equals(actionName)) {
                                        basicAttacksUsed++;
                                    } else if ("Defend".equals(actionName)) {
                                        defendUsed++;
                                    } else if ("SpecialSkill".equals(actionName)) {
                                        specialSkillsUsed++;
                                    } else if (actionName.startsWith("Item(")) {
                                        itemsUsed++;
                                    }
                                    playerDamageDealt += Math.max(0, enemyHpBefore - totalEnemyHp(state));
                                    enemiesDefeated += Math.max(0, aliveEnemiesBefore - countAliveEnemies(state));
                                }
                            }
                        }
                    } else {
                        PlannedAction action = chooseAction(state, actor);
                        if (action != null && action.action() != null) {
                            printEnemyAction(actor, action);
                            int playerHpBefore = state.player().getHp();
                            boolean executed = action.action().execute(state, actor, action.target());
                            if (executed) {
                                enemyActions++;
                                playerDamageTaken += Math.max(0, playerHpBefore - state.player().getHp());
                            }
                        }
                    }
                } else if (actor.getTeam() == Team.ENEMY) {
                    System.out.println("Enemy: " + actor.getName() + " → STUNNED: Turn skipped");
                    enemyTurnsSkipped++;
                }

                actor.applyTurnEndEffects();
                state.spawnBackupIfReady();
                if (!backupPreviouslySpawned && state.isBackupSpawned()) {
                    backupSpawns++;
                    backupPreviouslySpawned = true;
                }

                outcome = evaluateOutcome(state);
                if (outcome != BattleOutcome.ONGOING) {
                    break;
                }
            }

            state.onRoundEnd();
            outcome = evaluateOutcome(state);
        }

        long enemiesRemaining = state.activeEnemies().stream()
            .filter(Combatant::isAlive)
            .count();

        BattleStats stats = new BattleStats(
            playerActions,
            enemyActions,
            basicAttacksUsed,
            defendUsed,
            specialSkillsUsed,
            itemsUsed,
            playerDamageDealt,
            playerDamageTaken,
            enemiesDefeated,
            enemyTurnsSkipped,
            backupSpawns
        );

        return new BattleResult(
            outcome,
            state.getRoundCount(),
            state.player().getHp(),
            enemiesRemaining,
            stats
        );
    }

    private int totalEnemyHp(BattleState state) {
        int total = 0;
        for (Combatant enemy : state.activeEnemies()) {
            total += enemy.getHp();
        }
        return total;
    }

    private int countAliveEnemies(BattleState state) {
        int count = 0;
        for (Combatant enemy : state.activeEnemies()) {
            if (enemy.isAlive()) {
                count++;
            }
        }
        return count;
    }

    private PlannedAction chooseAction(BattleState state, Combatant actor) {
        if (actor.getTeam() == Team.PLAYER) {
            return playerDecisionPort.chooseAction(state, actor);
        }
        return enemyActionPolicy.chooseAction(state, actor, state.player());
    }

    private void printEnemyAction(Combatant actor, PlannedAction action) {
        String targetName = action.target() == null ? "no target" : action.target().getName();
        System.out.println("Enemy: " + actor.getName() + " → " + action.action().name() + " → " + targetName);
    }

    private BattleOutcome evaluateOutcome(BattleState state) {
        if (!state.player().isAlive()) {
            return BattleOutcome.DEFEAT;
        }

        boolean enemiesAlive = state.activeEnemies().stream().anyMatch(Combatant::isAlive);
        if (!enemiesAlive && !state.hasPendingBackup()) {
            return BattleOutcome.VICTORY;
        }

        return BattleOutcome.ONGOING;
    }
}
