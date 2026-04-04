package sc2002.engine;

public class DefendAction implements Action {
    @Override
    public String name() {
        return "Defend";
    }

    @Override
    public void execute(BattleState state, Combatant actor, Combatant target) {
        actor.addDefenseBonus(10, 2);
    }
}

