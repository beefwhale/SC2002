package sc2002.engine;

public interface Action {
    String name();

    void execute(BattleState state, Combatant actor, Combatant target);
}

