package sc2002;

import java.util.Scanner;
import sc2002.InputHandle;
import sc2002.engine.Action;
import sc2002.engine.BasicAttackAction;
import sc2002.engine.BasicAttackEnemyPolicy;
import sc2002.engine.BattleEngine;
import sc2002.engine.BattleResult;
import sc2002.engine.BattleSetup;
import sc2002.engine.ConsolePlayerDecisionPort;
import sc2002.engine.DefendAction;
import sc2002.engine.Difficulty;
import sc2002.engine.Item;
import sc2002.engine.LevelFactory;
import sc2002.engine.PlayerCombatant;
import sc2002.engine.PotionItem;
import sc2002.engine.PowerStoneItem;
import sc2002.engine.SmokeBombItem;
import sc2002.engine.SpecialSkillAction;
import sc2002.engine.SpeedTurnOrderStrategy;
import sc2002.engine.Warrior;
import sc2002.engine.Wizard;

public class GameMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputHandle inputHandle = new InputHandle(scanner);

        printGameContextAndInstructions();

        System.out.println("Choose player: 1) Warrior 2) Wizard");
        int playerChoice = inputHandle.readChoice("Enter choice: ", 1,2);
        PlayerCombatant player = (playerChoice == 2) ? new Wizard() : new Warrior();

        Item potion = new PotionItem();
        Item smokeBomb = new SmokeBombItem();
        Action freeSkillAction = new SpecialSkillAction(true);
        Item powerStone = new PowerStoneItem(freeSkillAction);

        chooseItems(inputHandle, player, potion, smokeBomb, powerStone);

        System.out.println("Choose difficulty: 1) Easy 2) Medium 3) Hard");
        int difficultyChoice = inputHandle.readChoice("Enter difficulty: ", 1,3);
        Difficulty difficulty = (difficultyChoice == 2)? Difficulty.MEDIUM: (difficultyChoice == 3 ? Difficulty.HARD : Difficulty.EASY);

        LevelFactory levelFactory = new LevelFactory();
        BattleSetup setup = levelFactory.create(player, difficulty);

        Action basicAttack = new BasicAttackAction();
        Action defend = new DefendAction();
        Action skill = new SpecialSkillAction();

        ConsolePlayerDecisionPort consoleDecisionPort = new ConsolePlayerDecisionPort(
            scanner, basicAttack, defend, skill, potion, smokeBomb, powerStone
        );

        BattleEngine engine = new BattleEngine(
            new SpeedTurnOrderStrategy(),
            consoleDecisionPort,
            new BasicAttackEnemyPolicy(basicAttack)
        );

        BattleResult result = engine.run(setup.toBattleState());
        System.out.println("\n=== Game Over ===");
        System.out.println("Outcome: " + result.outcome());
        System.out.println("Rounds: " + result.totalRounds());
        if (result.playerRemainingHp() > 0) {
            System.out.println("Remaining HP: " + result.playerRemainingHp());
        } else {
            System.out.println("Enemies remaining: " + result.enemiesRemaining());
        }
    }

    private static void chooseItems(InputHandle inputHandle, PlayerCombatant player, Item potion, Item smokeBomb, Item powerStone) {
        System.out.println("Choose 2 items (duplicates allowed): 1) Potion 2) PowerStone 3) SmokeBomb");
        for (int i = 0; i < 2; i++) {
            int choice = inputHandle.readChoice("Enter item choice: ", 1,3);

            switch(choice){
                case 1:
                    player.giveItem(potion.name(),1);
                    break;
                case 2:
                    player.giveItem(powerStone.name(),1);
                    break;
                case 3:
                    player.giveItem(smokeBomb.name(),1);
                    break;
                default:
                    System.out.println("Invalid item choice");
            }
        }
    }

    private static void printGameContextAndInstructions() {
        System.out.println("===============================");
        System.out.println("=== Turn-Based Combat Arena ===");
        System.out.println("===============================");
        System.out.println("Goal: Defeat all enemies before your HP reaches 0.");
        System.out.println("Setup: Choose player -> choose 2 items -> choose difficulty -> battle starts.");
        System.out.println("Turn order is based on speed. Higher speed acts first.");
        System.out.println("Input guide: Enter numbers only (e.g., 1, 2, 3, 4) when prompted.");
        System.out.println("Actions: 1) BasicAttack 2) Defend 3) SpecialSkill 4) Item\n");
    }
}
