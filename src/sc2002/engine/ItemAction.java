package sc2002.engine;

public class ItemAction implements Action {
    private final Item item;

    public ItemAction(Item item) {
        this.item = item;
    }

    @Override
    public String name() {
        return "Item(" + item.name() + ")";
    }

    @Override
    public boolean execute(BattleState state, Combatant actor, Combatant target) {
        return item.use(state, actor, target);
    }
}

