package sc2002.engine;

// TODO: Person 2 - Implement player characters
public class Warrior extends PlayerCombatant {
    public Warrior() {
        super("Warrior", PlayerType.WARRIOR, 260, 40, 20, 30);
        this.shieldBash = new WarriorShieldBash();
    }
    private SpecialSkill shieldBash;

    
    @Override
    public void performAction(Combatant target) {
        basicAttack(target);
    }

    public void basicAttack(Combatant target) {
        int damage = Math.max(0, this.attack - target.getDefense());
        target.takeDamage(damage);
        System.out.println(this.name + " attacks " + target.getName() + " for " + damage + " damage!");
    }

    public void defend() {
        this.defense += 10;
        System.out.println(this.name + " defends, increasing defense by 10 for this and next round!");
    }

    @Override
    public void useSpecialSkill(List<Combatant> enemies) {
        shieldBash.use(this, enemies);
    }

    @Override
    public void chooseAction(List<Combatant> enemies) {
        // CLI 输入逻辑，这里简化为默认使用 Shield Bash
        useSpecialSkill(enemies);
    }

    @Override
    public void updateCooldown() {
        shieldBash.tick();
    }
}

