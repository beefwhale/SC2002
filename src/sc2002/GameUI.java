package sc2002;

import java.util.Scanner;
import sc2002.engine.Action;

public class GameUI {
    private final Scanner scanner;

    public GameUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void startGame() {
        printWelcome();
        int choice = getChoice();
        System.out.println("You selected option: " + choice);
    }

    public int getChoice() {
        return getIntegerInput("Enter your choice: ");
    }

    public int getPlayerActionChoice() {
        printActionMenu();
        return getIntegerInput("Choose an action: ");
    }

    private void printWelcome() {
        System.out.println("Welcome to the game");
    }

    private void printActionMenu() {
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Item");
        System.out.println("4. Special Skill");
    }

    private int getIntegerInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}