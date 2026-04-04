package sc2002.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleState {
    private final Combatant player;
    private final List<Combatant> activeEnemies = new ArrayList<>();
    private final List<Combatant> backupEnemies = new ArrayList<>();
    private boolean backupSpawned;
    private int roundCount;
    private int enemyDamageZeroRounds;

    public BattleState(Combatant player, List<Combatant> initialEnemies, List<Combatant> backupEnemies) {
        this.player = player;
        this.activeEnemies.addAll(initialEnemies);
        this.backupEnemies.addAll(backupEnemies);
    }

    public Combatant player() {
        return player;
    }

    public List<Combatant> activeEnemies() {
        return Collections.unmodifiableList(activeEnemies);
    }

    public List<Combatant> allCombatants() {
        List<Combatant> all = new ArrayList<>();
        all.add(player);
        all.addAll(activeEnemies);
        return all;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void incrementRound() {
        roundCount++;
    }

    public void spawnBackupIfReady() {
        if (backupSpawned || backupEnemies.isEmpty()) {
            return;
        }
        boolean initialWaveDefeated = activeEnemies.stream().noneMatch(Combatant::isAlive);
        if (initialWaveDefeated) {
            activeEnemies.addAll(backupEnemies);
            backupEnemies.clear();
            backupSpawned = true;
        }
    }

    public boolean isBackupSpawned() {
        return backupSpawned;
    }

    public boolean hasPendingBackup() {
        return !backupSpawned && !backupEnemies.isEmpty();
    }

    public void setEnemyDamageZeroRounds(int rounds) {
        enemyDamageZeroRounds = Math.max(enemyDamageZeroRounds, rounds);
    }

    public boolean isEnemyDamageZeroActive() {
        return enemyDamageZeroRounds > 0;
    }

    public void onRoundEnd() {
        if (enemyDamageZeroRounds > 0) {
            enemyDamageZeroRounds--;
        }
        for (Combatant combatant : allCombatants()) {
            combatant.onRoundEnd();
        }
    }
}

