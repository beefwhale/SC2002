package sc2002.engine;

import java.util.List;

public class SpecialSkillAction implements Action {
    private final boolean ignoreCooldown;

    public SpecialSkillAction() {
        this(false);
    }

    public SpecialSkillAction(boolean ignoreCooldown) {
        this.ignoreCooldown = ignoreCooldown;
    }

    @Override
    public String name() {
        return "SpecialSkill";
    }

    @Override
    public boolean execute(BattleState state, Combatant actor, Combatant target) {
        if (!(actor instanceof PlayerCombatant)) {
            return false;
        }
        PlayerCombatant player = (PlayerCombatant) actor;
        if (!ignoreCooldown && player.getSkillCooldown() > 0) {
            System.out.println("Special skill on cooldown");
            return false;
        }

        if (player.getPlayerType() == PlayerType.WARRIOR) {
            if (target == null || !target.isAlive()) {
                return false;
            }
            int damage = Math.max(0, player.getAttack() - target.getDefense());
            target.applyDamage(damage);
            target.addStatusEffect(new StunEffect(2));
        } else {
            List<Combatant> enemies = state.activeEnemies();
            for (Combatant enemy : enemies) {
                if (!enemy.isAlive()) {
                    continue;
                }
                int damage = Math.max(0, player.getAttack() - enemy.getDefense());
                enemy.applyDamage(damage);
                if (!enemy.isAlive()) {
                    player.addAttackBonus(10);
                }
            }
        }

        if (!ignoreCooldown) {
            player.setSkillCooldown(3);
        }

        return true;
    }
}

