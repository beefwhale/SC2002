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
    public boolean use(BattleState state, Combatant user, Combatant target) {
        if (use.getItemCount(name()) <= 0){
            System.out.println("No more PowerStone");
            return flase;
        }

        
        user.consumeItem(name());
        if (!freeSkillAction.execute(state, user, target)) {
            return false;
        }

        return true;
    }
}

