package sc2002.engine;

public interface Item {
    String name();

    void use(BattleState state, Combatant user, Combatant target);
}

