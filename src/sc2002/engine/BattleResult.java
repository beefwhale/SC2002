package sc2002.engine;

public class BattleResult {
    private final BattleOutcome outcome;
    private final int totalRounds;
    private final int playerRemainingHp;
    private final long enemiesRemaining;

    public BattleResult(BattleOutcome outcome, int totalRounds, int playerRemainingHp, long enemiesRemaining) {
        this.outcome = outcome;
        this.totalRounds = totalRounds;
        this.playerRemainingHp = playerRemainingHp;
        this.enemiesRemaining = enemiesRemaining;
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
}

