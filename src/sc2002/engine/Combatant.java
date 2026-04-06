package sc2002.engine;

import java.util.List;
import java.util.ArrayList;

public interface Combatant {
    public String getName();
    public Team getTeam() ;
    public int getMaxHp();
    public int getHp() ;
    public int getSpeed() ;
    public int getAttack();
    public int getDefense() ;
    public int getSkillCooldown() ;
    public boolean canAct();
    public void setCanAct(boolean canAct) ;    
    public int setHP(int HP);
    public void setSkillCooldown(int turns) ;
    public void tickSkillCooldown() ;
    public void applyDamage(int damage);
    public void heal(int amount) ;
    public boolean isAlive() ;
    public void addStatusEffect(StatusEffect effect);
    public void applyTurnStartEffects();
    public void applyTurnEndEffects() ;
    public List<StatusEffect> getStatusEffects();
    public void addAttackBonus(int amount) ;
    public void addDefenseBonus(int amount, int rounds);
    public void onRoundEnd() ;
    public boolean consumeActionBlockedForTurn() ;
    public void blockActionForCurrentTurn() ;
    public void consumeItem(String itemName) ;
    public int getItemCount(String itemName) ;
    public void giveItem(String itemName, int count) ;
    private void removeExpiredEffects() ;
    
    public abstract void performAction(Combatant target);
}

