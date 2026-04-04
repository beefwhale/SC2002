package sc2002.engine;

import java.util.ArrayList;
import java.util.List;

public class LevelFactory {
    public BattleSetup create(Combatant player, Difficulty difficulty) {
        List<Combatant> initial = new ArrayList<>();
        List<Combatant> backup = new ArrayList<>();

        switch (difficulty) {
            case EASY:
                initial.add(new Goblin("Goblin A"));
                initial.add(new Goblin("Goblin B"));
                initial.add(new Goblin("Goblin C"));
                break;
            case MEDIUM:
                initial.add(new Goblin("Goblin"));
                initial.add(new Wolf("Wolf"));
                backup.add(new Wolf("Wolf A"));
                backup.add(new Wolf("Wolf B"));
                break;
            case HARD:
                initial.add(new Goblin("Goblin A"));
                initial.add(new Goblin("Goblin B"));
                backup.add(new Goblin("Goblin C"));
                backup.add(new Wolf("Wolf A"));
                backup.add(new Wolf("Wolf B"));
                break;
            default:
                throw new IllegalArgumentException("Unsupported difficulty: " + difficulty);
        }

        return new BattleSetup(player, initial, backup);
    }
}

