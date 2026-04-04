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
    public void execute(BattleState state, Combatant actor, Combatant target) {
        item.use(state, actor, target);
    }
}

