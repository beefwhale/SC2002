package sc2002.engine;

public interface StatusEffect {
    String name();

    void onTurnStart(Combatant target);

    default void onTurnEnd(Combatant target) {
        // Optional by design.
    }

    boolean isExpired();
}

