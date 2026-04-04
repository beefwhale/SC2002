package sc2002.engine;

import java.util.List;

public class BattleSetup {
    private final Combatant player;
    private final List<Combatant> initialEnemies;
    private final List<Combatant> backupEnemies;

    public BattleSetup(Combatant player, List<Combatant> initialEnemies, List<Combatant> backupEnemies) {
        this.player = player;
        this.initialEnemies = initialEnemies;
        this.backupEnemies = backupEnemies;
    }

    public BattleState toBattleState() {
        return new BattleState(player, initialEnemies, backupEnemies);
    }
}

