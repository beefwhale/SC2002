package sc2002.engine;

// TODO: Person 2 - Implement enemy characters
public class Wolf extends BaseCombatant {
    public Wolf(String name) {
        super(name, Team.ENEMY, 40, 45, 5, 35);
    }
    public void performAction(List<Combatant> enemies) {
        if (!enemies.isEmpty()) {
            Combatant target = enemies.get(0); // 简化为攻击第一个敌人
            Action action = new BasicAttackAction(this, target);
            action.execute();
        }
    }
}

