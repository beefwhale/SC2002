package sc2002.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// TODO: Person 4 - Implement and enhance CLI UI
public class ConsolePlayerDecisionPort implements PlayerDecisionPort {
    private final Scanner scanner;
    private final Action basicAttack;
    private final Action defend;
    private final Action specialSkill;
    private final Item potion;
    private final Item smokeBomb;
    private final Item powerStone;

    public ConsolePlayerDecisionPort(
        Scanner scanner,
        Action basicAttack,
        Action defend,
        Action specialSkill,
        Item potion,
        Item smokeBomb,
        Item powerStone
    ) {
        this.scanner = scanner;
        this.basicAttack = basicAttack;
        this.defend = defend;
        this.specialSkill = specialSkill;
        this.potion = potion;
        this.smokeBomb = smokeBomb;
        this.powerStone = powerStone;
    }

    @Override
    public PlannedAction chooseAction(BattleState state, Combatant player) {
        List<Combatant> aliveEnemies = aliveEnemies(state);
        while (true) {
            printState(state, player, aliveEnemies);
            String raw = scanner.nextLine().trim();
            switch (raw) {
                case "1":
                    return new PlannedAction(basicAttack, pickEnemy(aliveEnemies));
                case "2":
                    return new PlannedAction(defend, player);
                case "3":
                    if (player.getSkillCooldown() > 0) {
                        System.out.println("Special skill on cooldown: " + player.getSkillCooldown());
                        continue;
                    }
                    return new PlannedAction(specialSkill, pickEnemy(aliveEnemies));
                case "4":
                    return chooseItem(state, player, aliveEnemies);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private PlannedAction chooseItem(BattleState state, Combatant player, List<Combatant> aliveEnemies) {
        System.out.println("Select item: 1) Potion 2) PowerStone 3) SmokeBomb");
        String raw = scanner.nextLine().trim();
        if ("1".equals(raw) && player.getItemCount(potion.name()) > 0) {
            return new PlannedAction(new ItemAction(potion), player);
        }
        if ("2".equals(raw) && player.getItemCount(powerStone.name()) > 0) {
            return new PlannedAction(new ItemAction(powerStone), pickEnemy(aliveEnemies));
        }
        if ("3".equals(raw) && player.getItemCount(smokeBomb.name()) > 0) {
            return new PlannedAction(new ItemAction(smokeBomb), player);
        }
        System.out.println("Item unavailable");
        return chooseAction(state, player);
    }

    private Combatant pickEnemy(List<Combatant> aliveEnemies) {
        if (aliveEnemies.isEmpty()) {
            return null;
        }
        if (aliveEnemies.size() == 1) {
            return aliveEnemies.get(0);
        }

        System.out.println("Select target:");
        for (int i = 0; i < aliveEnemies.size(); i++) {
            Combatant enemy = aliveEnemies.get(i);
            System.out.println((i + 1) + ") " + enemy.getName() + " HP=" + enemy.getHp());
        }

        while (true) {
            String raw = scanner.nextLine().trim();
            try {
                int index = Integer.parseInt(raw) - 1;
                if (index >= 0 && index < aliveEnemies.size()) {
                    return aliveEnemies.get(index);
                }
            } catch (NumberFormatException ignored) {
                // fall through
            }
            System.out.println("Invalid target");
        }
    }

    private List<Combatant> aliveEnemies(BattleState state) {
        List<Combatant> alive = new ArrayList<>();
        for (Combatant enemy : state.activeEnemies()) {
            if (enemy.isAlive()) {
                alive.add(enemy);
            }
        }
        return alive;
    }

    private void printState(BattleState state, Combatant player, List<Combatant> aliveEnemies) {
        System.out.println("\nRound " + state.getRoundCount());
        System.out.println(player.getName() + " HP=" + player.getHp() + "/" + player.getMaxHp() +
            " CD=" + player.getSkillCooldown() +
            " Potion=" + player.getItemCount("Potion") +
            " PowerStone=" + player.getItemCount("PowerStone") +
            " SmokeBomb=" + player.getItemCount("SmokeBomb"));
        for (Combatant enemy : aliveEnemies) {
            System.out.println(" - " + enemy.getName() + " HP=" + enemy.getHp());
        }
        System.out.println("Choose action: 1) BasicAttack 2) Defend 3) SpecialSkill 4) Item");
    }
}

