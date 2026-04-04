package sc2002.engine;

// TODO: Person 3 - Implement actions
public class BasicAttackAction implements Action {
    @Override
    public String name() {
        return "BasicAttack";
    }

    @Override
    public void execute(BattleState state, Combatant actor, Combatant target) {
        if (target == null || !target.isAlive()) {
            return;
        }
        int damage = Math.max(0, actor.getAttack() - target.getDefense());
        if (actor.team() == Team.ENEMY && state.isEnemyDamageZeroActive()) {
            damage = 0;
        }
        target.applyDamage(damage);
    }
}

