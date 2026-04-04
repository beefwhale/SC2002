package sc2002.engine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class ScriptedPlayerDecisionPort implements PlayerDecisionPort {
    private final Deque<String> script = new ArrayDeque<>();
    private final Action basicAttack;
    private final Action defend;
    private final Action specialSkill;
    private final Item potion;
    private final Item smokeBomb;
    private final Item powerStone;

    public ScriptedPlayerDecisionPort(
        List<String> script,
        Action basicAttack,
        Action defend,
        Action specialSkill,
        Item potion,
        Item smokeBomb,
        Item powerStone
    ) {
        this.script.addAll(script);
        this.basicAttack = basicAttack;
        this.defend = defend;
        this.specialSkill = specialSkill;
        this.potion = potion;
        this.smokeBomb = smokeBomb;
        this.powerStone = powerStone;
    }

    @Override
    public PlannedAction chooseAction(BattleState state, Combatant player) {
        String token = script.isEmpty() ? "BASIC" : script.removeFirst();
        Combatant target = firstAliveEnemy(state);

        if ("DEFEND".equalsIgnoreCase(token)) {
            return new PlannedAction(defend, player);
        }
        if ("SKILL".equalsIgnoreCase(token)) {
            if (player.getSkillCooldown() > 0) {
                return new PlannedAction(basicAttack, target);
            }
            return new PlannedAction(specialSkill, target);
        }
        if ("POTION".equalsIgnoreCase(token) && player.getItemCount(potion.name()) > 0) {
            return new PlannedAction(new ItemAction(potion), player);
        }
        if ("SMOKE".equalsIgnoreCase(token) && player.getItemCount(smokeBomb.name()) > 0) {
            return new PlannedAction(new ItemAction(smokeBomb), player);
        }
        if ("STONE".equalsIgnoreCase(token) && player.getItemCount(powerStone.name()) > 0) {
            return new PlannedAction(new ItemAction(powerStone), target);
        }
        return new PlannedAction(basicAttack, target);
    }

    private Combatant firstAliveEnemy(BattleState state) {
        for (Combatant enemy : state.activeEnemies()) {
            if (enemy.isAlive()) {
                return enemy;
            }
        }
        return null;
    }
}

