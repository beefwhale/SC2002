package sc2002.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpeedTurnOrderStrategy implements TurnOrderStrategy {
    @Override
    public List<Combatant> order(BattleState state) {
        List<Combatant> ordered = new ArrayList<>(state.allCombatants());
        ordered.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());
        return ordered;
    }
}

