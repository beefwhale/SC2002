package sc2002.engine;

// TODO: Person 2 - Implement player characters
public class Warrior extends PlayerCombatant {
    public Warrior() {
        this("Warrior");
    }

    public Warrior(String name) {
        super(name, PlayerType.WARRIOR, 260, 40, 20, 30);
    }
    
}
