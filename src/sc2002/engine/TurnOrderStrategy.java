package sc2002.engine;

import java.util.List;

public interface TurnOrderStrategy {
    List<Combatant> order(BattleState state);
}

