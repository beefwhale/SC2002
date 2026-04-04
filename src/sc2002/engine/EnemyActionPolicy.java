package sc2002.engine;

public interface EnemyActionPolicy {
    PlannedAction chooseAction(BattleState state, Combatant enemy, Combatant player);
}

