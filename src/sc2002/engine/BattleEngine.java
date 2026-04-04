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
        while (outcome == BattleOutcome.ONGOING) {
            state.incrementRound();
            List<Combatant> order = turnOrderStrategy.order(state);
            for (Combatant actor : order) {
                if (!actor.isAlive()) {
                    continue;
                }

                actor.applyTurnStartEffects();
                outcome = evaluateOutcome(state);
                if (outcome != BattleOutcome.ONGOING) {
                    break;
                }

                if (!actor.consumeActionBlockedForTurn()) {
                    PlannedAction action = chooseAction(state, actor);
                    if (action != null && action.action() != null) {
                        action.action().execute(state, actor, action.target());
                    }
                }

                actor.applyTurnEndEffects();
                actor.tickSkillCooldown();
                state.spawnBackupIfReady();

                outcome = evaluateOutcome(state);
                if (outcome != BattleOutcome.ONGOING) {
                    break;
                }
            }
            state.onRoundEnd();
            outcome = evaluateOutcome(state);
        }

        long enemiesRemaining = state.activeEnemies().stream().filter(Combatant::isAlive).count();
        return new BattleResult(outcome, state.getRoundCount(), state.player().getHp(), enemiesRemaining);
    }

    private PlannedAction chooseAction(BattleState state, Combatant actor) {
        if (actor.team() == Team.PLAYER) {
            return playerDecisionPort.chooseAction(state, actor);
        }
        return enemyActionPolicy.chooseAction(state, actor, state.player());
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

