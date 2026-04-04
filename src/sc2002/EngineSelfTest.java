package sc2002;

import java.util.Arrays;
import sc2002.engine.Action;
import sc2002.engine.BasicAttackAction;
import sc2002.engine.BasicAttackEnemyPolicy;
import sc2002.engine.BattleEngine;
import sc2002.engine.BattleOutcome;
import sc2002.engine.BattleResult;
import sc2002.engine.BattleSetup;
import sc2002.engine.Difficulty;
import sc2002.engine.Item;
import sc2002.engine.LevelFactory;
import sc2002.engine.PlayerCombatant;
import sc2002.engine.PowerStoneItem;
import sc2002.engine.PotionItem;
import sc2002.engine.ScriptedPlayerDecisionPort;
import sc2002.engine.SmokeBombItem;
import sc2002.engine.SpecialSkillAction;
import sc2002.engine.SpeedTurnOrderStrategy;
import sc2002.engine.Warrior;
import sc2002.engine.Wizard;

public class EngineSelfTest {
    public static void main(String[] args) {
        testEasyWarriorVictory();
        testMediumBackupSpawnFlow();
        testWizardArcaneBlastBuff();
        System.out.println("All engine self-tests passed.");
    }

    private static void testEasyWarriorVictory() {
        PlayerCombatant player = new Warrior();
        Item potion = new PotionItem();
        Item smoke = new SmokeBombItem();
        Item stone = new PowerStoneItem(new SpecialSkillAction(true));
        player.giveItem(potion.name(), 1);
        player.giveItem(smoke.name(), 1);

        Action basicAttack = new BasicAttackAction();
        Action defend = new sc2002.engine.DefendAction();
        Action skill = new SpecialSkillAction();

        BattleEngine engine = new BattleEngine(
            new SpeedTurnOrderStrategy(),
            new ScriptedPlayerDecisionPort(
                Arrays.asList("SKILL", "BASIC", "SMOKE", "DEFEND", "POTION", "BASIC", "BASIC", "BASIC", "BASIC"),
                basicAttack,
                defend,
                skill,
                potion,
                smoke,
                stone
            ),
            new BasicAttackEnemyPolicy(basicAttack)
        );

        BattleSetup setup = new LevelFactory().create(player, Difficulty.EASY);
        BattleResult result = engine.run(setup.toBattleState());
        assertTrue(result.outcome() == BattleOutcome.VICTORY, "Easy warrior scenario should end in victory");
        assertTrue(result.totalRounds() > 0, "Round count should be tracked");
    }

    private static void testMediumBackupSpawnFlow() {
        PlayerCombatant player = new Warrior();
        Item potion = new PotionItem();
        Item smoke = new SmokeBombItem();
        Item stone = new PowerStoneItem(new SpecialSkillAction(true));
        player.giveItem(stone.name(), 1);
        player.giveItem(potion.name(), 1);

        Action basicAttack = new BasicAttackAction();
        Action defend = new sc2002.engine.DefendAction();
        Action skill = new SpecialSkillAction();

        BattleEngine engine = new BattleEngine(
            new SpeedTurnOrderStrategy(),
            new ScriptedPlayerDecisionPort(
                Arrays.asList("SKILL", "BASIC", "BASIC", "SKILL", "STONE", "BASIC", "BASIC", "BASIC"),
                basicAttack,
                defend,
                skill,
                potion,
                smoke,
                stone
            ),
            new BasicAttackEnemyPolicy(basicAttack)
        );

        BattleSetup setup = new LevelFactory().create(player, Difficulty.MEDIUM);
        BattleResult result = engine.run(setup.toBattleState());
        assertTrue(result.outcome() == BattleOutcome.VICTORY, "Medium flow should support backup spawn and still finish");
        assertTrue(result.totalRounds() >= 3, "Medium flow should process multiple rounds");
    }

    private static void testWizardArcaneBlastBuff() {
        Wizard wizard = new Wizard();
        Item potion = new PotionItem();
        Item smoke = new SmokeBombItem();
        Item stone = new PowerStoneItem(new SpecialSkillAction(true));
        wizard.giveItem(stone.name(), 1);
        wizard.giveItem(potion.name(), 1);

        Action basicAttack = new BasicAttackAction();
        Action defend = new sc2002.engine.DefendAction();
        Action skill = new SpecialSkillAction();

        BattleEngine engine = new BattleEngine(
            new SpeedTurnOrderStrategy(),
            new ScriptedPlayerDecisionPort(
                Arrays.asList("SKILL", "BASIC", "STONE", "BASIC"),
                basicAttack,
                defend,
                skill,
                potion,
                smoke,
                stone
            ),
            new BasicAttackEnemyPolicy(basicAttack)
        );

        BattleSetup setup = new LevelFactory().create(wizard, Difficulty.MEDIUM);
        BattleResult result = engine.run(setup.toBattleState());
        assertTrue(result.outcome() == BattleOutcome.VICTORY, "Wizard medium scenario should be winnable");
        assertTrue(wizard.getAttack() >= 60, "Arcane Blast kills should increase wizard attack");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}

