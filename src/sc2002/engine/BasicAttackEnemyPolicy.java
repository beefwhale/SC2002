package sc2002.engine;

public class BasicAttackEnemyPolicy implements EnemyActionPolicy {
    private final Action basicAttackAction;

    public BasicAttackEnemyPolicy(Action basicAttackAction) {
        this.basicAttackAction = basicAttackAction;
    }

    @Override
    public PlannedAction chooseAction(BattleState state, Combatant enemy, Combatant player) {
        return new PlannedAction(basicAttackAction, player);
    }
}

