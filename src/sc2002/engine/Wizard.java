package sc2002.engine;

public class Wizard extends PlayerCombatant {
    public Wizard() {
        this("Wizard");
    }

    public Wizard(String name) {
        super(name, PlayerType.WIZARD, 200, 50, 10, 20);
    }
}
