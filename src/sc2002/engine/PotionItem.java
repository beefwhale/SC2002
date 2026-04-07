package sc2002.engine;

// TODO: Person 3 - Implement items
public class PotionItem implements Item {
    @Override
    public String name() {
        return "Potion";
    }

    @Override
    public boolean use(BattleState state, Combatant user, Combatant target) {
        if (user.getItemCount(name)) <= 0) {
            System.out.println("No more Potion");
            return;
        }
        
        user.consumeItem(name());
        user.heal(100);
    }
}

