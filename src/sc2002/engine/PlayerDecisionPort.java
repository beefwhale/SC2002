package sc2002.engine;

public interface PlayerDecisionPort {
    PlannedAction chooseAction(BattleState state, Combatant player);
}

