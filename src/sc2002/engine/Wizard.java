package sc2002.engine;

// TODO: Person 2 - Implement player characters
public class Wizard extends PlayerCombatant {
    private SpecialSkillAction arcaneBlast;

    public Wizard(String name) {
        super(name, Team.WIZARD, 200, 50, 10, 20); 
        this.arcaneBlast = new WizardArcaneBlast(); 
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
        arcaneBlast.execute(this, enemies);
    }

    @Override
    public void updateCooldown() {
        arcaneBlast.tick();
    }
}
