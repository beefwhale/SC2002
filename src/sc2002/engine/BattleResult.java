package sc2002.engine;

public class BattleResult {
    private final BattleOutcome outcome;
    private final int totalRounds;
    private final int playerRemainingHp;
    private final long enemiesRemaining;
    private final BattleStats stats;

    public BattleResult(BattleOutcome outcome, int totalRounds, int playerRemainingHp, long enemiesRemaining) {
        this(outcome, totalRounds, playerRemainingHp, enemiesRemaining, null);
    }

    public BattleResult(
        BattleOutcome outcome,
        int totalRounds,
        int playerRemainingHp,
        long enemiesRemaining,
        BattleStats stats
    ) {
        this.outcome = outcome;
        this.totalRounds = totalRounds;
        this.playerRemainingHp = playerRemainingHp;
        this.enemiesRemaining = enemiesRemaining;
        this.stats = stats;
    }

    public BattleOutcome outcome() {
        return outcome;
    }

    public int totalRounds() {
        return totalRounds;
    }

    public int playerRemainingHp() {
        return playerRemainingHp;
    }

    public long enemiesRemaining() {
        return enemiesRemaining;
    }

    public BattleStats stats() {
        return stats;
    }
}

