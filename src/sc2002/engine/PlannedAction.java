package sc2002.engine;

public class PlannedAction {
    private final Action action;
    private final Combatant target;

    public PlannedAction(Action action, Combatant target) {
        this.action = action;
        this.target = target;
    }

    public Action action() {
        return action;
    }

    public Combatant target() {
        return target;
    }
}

