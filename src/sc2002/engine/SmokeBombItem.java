package sc2002.engine;

// TODO: Person 3 - Implement items
public class SmokeBombItem implements Item {
    @Override
    public String name() {
        return "SmokeBomb";
    }

    @Override
    public boolean use(BattleState state, Combatant user, Combatant target) {
        if (user.GetItemCount(name()) <=0){
            System.out.println("No more SmokeBomb");
            return false;
        }
        
        user.consumeItem(name());
        state.setEnemyDamageZeroRounds(2);
        return true;
    }
}

