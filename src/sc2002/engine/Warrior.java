package sc2002.engine;

// TODO: Person 2 - Implement player characters
public class Warrior extends PlayerCombatant {
    private SpecialSkillAction shieldBash;

    public Warrior(String name) {
        super(name, Team.WARRIOR, 260, 40, 20, 30);
        this.shieldBash = new WarriorShieldBash();
    }

    @Override
    public void performAction(List<Combatant> enemies) {
        Action action = new BasicAttackAction(this, enemies.get(0));
        action.execute();
    }

    @Override
    public void chooseAction(List<Combatant> enemies) {
        useSpecialSkill(enemies);
    }

    @Override
    public void useSpecialSkill(List<Combatant> enemies) {
        shieldBash.execute(this, enemies);
    }

    @Override
    public void updateCooldown() {
        shieldBash.tick();
    }
}

