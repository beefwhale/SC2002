package sc2002.engine;

// TODO: Person 3 - Implement items
public class PowerStoneItem implements Item {
    private final Action freeSkillAction;

    public PowerStoneItem(Action freeSkillAction) {
        this.freeSkillAction = freeSkillAction;
    }

    @Override
    public String name() {
        return "PowerStone";
    }

    @Override
    public void use(BattleState state, Combatant user, Combatant target) {
        user.consumeItem(name());
        freeSkillAction.execute(state, user, target);
    }
}

