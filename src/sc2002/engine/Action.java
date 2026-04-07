package sc2002.engine;

public interface Action {
    String name();

    boolean execute(BattleState state, Combatant actor, Combatant target);
}

