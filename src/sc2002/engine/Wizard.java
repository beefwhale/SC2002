package sc2002.engine;

// TODO: Person 2 - Implement player characters
public class Wizard extends PlayerCombatant {
    public Wizard() {
        this("Wizard");
    }

    public Wizard(String name) {
        super(name, PlayerType.WIZARD, 200, 50, 10, 20);
    }
}
