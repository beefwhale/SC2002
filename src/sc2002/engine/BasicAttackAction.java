package sc2002.engine;

public class BasicAttackAction implements Action {
    @Override
    public String name() {
        return "BasicAttack";
    }

    @Override
    public boolean execute(BattleState state, Combatant actor, Combatant target) {
        if (target == null || !target.isAlive()) {
            return false;
        }
        int damage = Math.max(0, actor.getAttack() - target.getDefense());
       if (actor.getTeam() == Team.ENEMY && state.isEnemyDamageZeroActive()) {
            // no damage?
            damage = 0;
        }
        target.applyDamage(damage);
        return true;
    }
}

