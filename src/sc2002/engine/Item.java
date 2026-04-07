package sc2002.engine;

public interface Item {
    String name();

    boolean use(BattleState state, Combatant user, Combatant target);
}

