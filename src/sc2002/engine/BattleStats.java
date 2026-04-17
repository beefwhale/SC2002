package sc2002.engine;

public class BattleStats {
    private final int playerActions;
    private final int enemyActions;
    private final int basicAttacksUsed;
    private final int defendUsed;
    private final int specialSkillsUsed;
    private final int itemsUsed;
    private final int playerDamageDealt;
    private final int playerDamageTaken;
    private final int enemiesDefeated;
    private final int enemyTurnsSkipped;
    private final int backupSpawns;

    public BattleStats(
        int playerActions,
        int enemyActions,
        int basicAttacksUsed,
        int defendUsed,
        int specialSkillsUsed,
        int itemsUsed,
        int playerDamageDealt,
        int playerDamageTaken,
        int enemiesDefeated,
        int enemyTurnsSkipped,
        int backupSpawns
    ) {
        this.playerActions = playerActions;
        this.enemyActions = enemyActions;
        this.basicAttacksUsed = basicAttacksUsed;
        this.defendUsed = defendUsed;
        this.specialSkillsUsed = specialSkillsUsed;
        this.itemsUsed = itemsUsed;
        this.playerDamageDealt = playerDamageDealt;
        this.playerDamageTaken = playerDamageTaken;
        this.enemiesDefeated = enemiesDefeated;
        this.enemyTurnsSkipped = enemyTurnsSkipped;
        this.backupSpawns = backupSpawns;
    }

    public int playerActions() {
        return playerActions;
    }

    public int enemyActions() {
        return enemyActions;
    }

    public int basicAttacksUsed() {
        return basicAttacksUsed;
    }

    public int defendUsed() {
        return defendUsed;
    }

    public int specialSkillsUsed() {
        return specialSkillsUsed;
    }

    public int itemsUsed() {
        return itemsUsed;
    }

    public int playerDamageDealt() {
        return playerDamageDealt;
    }

    public int playerDamageTaken() {
        return playerDamageTaken;
    }

    public int enemiesDefeated() {
        return enemiesDefeated;
    }

    public int enemyTurnsSkipped() {
        return enemyTurnsSkipped;
    }

    public int backupSpawns() {
        return backupSpawns;
    }
}

