package sc2002.engine;

// TODO: Person 2 - Implement enemy characters
import java.util.List;

public class Goblin extends BaseCombatant {
    public BattleState state;
    public Goblin(String name) {
        super(name, Team.ENEMY, 55, 35, 15, 25);
    }
    public void performAction(List<Combatant> enemies) {
        if (!enemies.isEmpty()) {
            Combatant target = enemies.get(0);
            Action action = new BasicAttackAction();
            action.execute(state, this, target);
        }
    }
}

