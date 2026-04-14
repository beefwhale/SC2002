package sc2002.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// TODO: Person 2 - Extend/customize character base class as needed
public class BaseCombatant implements Combatant {
    private final String name;
    private final Team team;
    private final int maxHp;
    private final int baseAttack;
    private final int baseDefense;
    private final int speed;
    private int hp;
    private int attackBonus;
    private int defenseBonus;
    private int defenseBonusRounds;
    private int skillCooldown;
    private boolean actionBlockedForTurn;
    private boolean canAct;
    private final List<StatusEffect> statusEffects = new ArrayList<>();
    private final Map<String, Integer> inventory = new HashMap<>();

    public BaseCombatant(String name, Team team, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.team = team;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.baseAttack = attack;
        this.baseDefense = defense;
        this.speed = speed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getAttack() {
        return baseAttack + attackBonus;
    }

    @Override
    public int getDefense() {
        return baseDefense + defenseBonus;
    }
    
    @Override
    public boolean canAct() {
    return this.canAct && isAlive();
    }

    @Override
    public void setCanAct(boolean canAct) {
    this.canAct = canAct;
    }

    @Override
    public int setHP(int HP) {
    if (HP > maxHp) {
        this.hp = maxHp;
    } else if (HP < 0) {
        this.hp = 0;
    } else {
        this.hp = HP;
    }
    return this.hp;}//modified

    @Override
    public int getSkillCooldown() {
        return skillCooldown;
    }

    @Override
    public void setSkillCooldown(int turns) {
        skillCooldown = Math.max(0, turns);
    }

    @Override
    public void tickSkillCooldown() {
        if (skillCooldown > 0) {
            skillCooldown--;
        }
    }

    @Override
    public void applyDamage(int damage) {
        hp = Math.max(0, hp - Math.max(0, damage));
    }

    @Override
    public void heal(int amount) {
        hp = Math.min(maxHp, hp + Math.max(0, amount));
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public void addStatusEffect(StatusEffect effect) {
        statusEffects.add(effect);
    }

    @Override
    public void applyTurnStartEffects() {
        for (StatusEffect effect : statusEffects) {
            effect.onTurnStart(this);
        }
        removeExpiredEffects();
    }

    @Override
    public void applyTurnEndEffects() {
        for (StatusEffect effect : statusEffects) {
            effect.onTurnEnd(this);
        }
        removeExpiredEffects();
    }

    @Override
    public List<StatusEffect> getStatusEffects() {
        return statusEffects;
    }

    @Override
    public void addAttackBonus(int amount) {
        attackBonus += amount;
    }

    @Override
    public void addDefenseBonus(int amount, int rounds) {
        defenseBonus += amount;
        defenseBonusRounds = Math.max(defenseBonusRounds, rounds);
    }

    @Override
    public void onRoundEnd() {
        if (defenseBonusRounds > 0) {
            defenseBonusRounds--;
            if (defenseBonusRounds == 0) {
                defenseBonus = 0;
            }
        }
    }

    @Override
    public boolean consumeActionBlockedForTurn() {
        boolean blocked = actionBlockedForTurn;
        actionBlockedForTurn = false;
        return blocked;
    }

    @Override
    public void blockActionForCurrentTurn() {
        actionBlockedForTurn = true;
    }

    @Override
    public void consumeItem(String itemName) {
        int count = getItemCount(itemName);
        if (count <= 0) {
            throw new IllegalStateException("Item not available: " + itemName);
        }
        inventory.put(itemName, count - 1);
    }

    @Override
    public int getItemCount(String itemName) {
        return inventory.getOrDefault(itemName, 0);
    }

    @Override
    public void giveItem(String itemName, int count) {
        inventory.put(itemName, getItemCount(itemName) + Math.max(0, count));
    }

    private void removeExpiredEffects() {
        Iterator<StatusEffect> iterator = statusEffects.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isExpired()) {
                iterator.remove();
            }
        }
    }
}

