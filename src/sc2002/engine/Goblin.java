package sc2002.engine;

// TODO: Person 2 - Implement enemy characters
public class Goblin extends BaseCombatant {
    public Goblin(String name) {
        super(name, Team.ENEMY, 55, 35, 15, 25);
    }
    @Override
    public void performAction(List<Combatant> enemies) {
        if (!enemies.isEmpty()) {
            Combatant target = enemies.get(0); // 简化为攻击第一个敌人
            Action action = new BasicAttackAction(this, target);
            action.execute();
        }
    }
}

