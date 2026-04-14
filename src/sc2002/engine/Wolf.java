package sc2002.engine;

// TODO: Person 2 - Implement enemy characters
public class Wolf extends BaseCombatant {
    public BattleState state;
    public Wolf(String name) {
        super(name, Team.ENEMY, 40, 45, 5, 35);
    }
    public void performAction(List<Combatant> enemies) {
        if (!enemies.isEmpty()) {
            Combatant target = enemies.get(0);
            Action action = new BasicAttackAction();
            action.execute(state, this, target);
        }
    }
}

