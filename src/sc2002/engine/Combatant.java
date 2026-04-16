package sc2002.engine;

import java.util.List;

public interface Combatant {
    String getName();
    Team getTeam();
    int getMaxHp();
    int getHp();
    int getSpeed();
    int getAttack();
    int getDefense();
    int getSkillCooldown();

    boolean canAct();
    void setCanAct(boolean canAct);

    int setHP(int HP);

    void setSkillCooldown(int turns);
    void tickSkillCooldown();

    void applyDamage(int damage);
    void heal(int amount);

    boolean isAlive();

    void addStatusEffect(StatusEffect effect);
    void applyTurnStartEffects();
    void applyTurnEndEffects();

    List<StatusEffect> getStatusEffects();

    void addAttackBonus(int amount);
    void addDefenseBonus(int amount, int rounds);

    void onRoundEnd();

    boolean consumeActionBlockedForTurn();
    void blockActionForCurrentTurn();

    void consumeItem(String itemName);
    int getItemCount(String itemName);
    void giveItem(String itemName, int count);
}