package sc2002.engine;

// TODO: Person 3 - Implement items
public class SmokeBombItem implements Item {
    @Override
    public String name() {
        return "SmokeBomb";
    }

    @Override
    public void use(BattleState state, Combatant user, Combatant target) {
        user.consumeItem(name());
        state.setEnemyDamageZeroRounds(2);
    }
}

