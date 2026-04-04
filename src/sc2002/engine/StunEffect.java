package sc2002.engine;

// TODO: Person 3 - Implement status effects
public class StunEffect implements StatusEffect {
    private int turnsRemaining;

    public StunEffect(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    public String name() {
        return "Stun";
    }

    @Override
    public void onTurnStart(Combatant target) {
        if (turnsRemaining > 0) {
            target.blockActionForCurrentTurn();
            turnsRemaining--;
        }
    }

    @Override
    public boolean isExpired() {
        return turnsRemaining <= 0;
    }
}

