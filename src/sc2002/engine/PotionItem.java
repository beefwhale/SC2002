package sc2002.engine;

public class PotionItem implements Item {
    @Override
    public String name() {
        return "Potion";
    }

    @Override
    public void use(BattleState state, Combatant user, Combatant target) {
        user.consumeItem(name());
        user.heal(100);
    }
}

