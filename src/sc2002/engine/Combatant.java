package sc2002.engine;

import java.util.List;
import java.util.ArrayList;

public abstract class Combatant {//modified interface to abstract class
    protected String name;
    protected int maxHP;
    protected int currentHP;
    protected int attack;
    protected int defense;
    protected int speed;
    protected boolean canAct;

    // combatant can have several effects
    protected List<Effect> activeEffects;
    
    //Constructor
    public Combatant(String name, int maxHP, int attack, int defense, int speed) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.canAct = true;
        this.activeEffects = new ArrayList<>();
    }

    //getters and setters
    public String getName() { return name; }
    public int getMaxHP() { return maxHP; }
    public int getCurrentHP() { return currentHP; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public boolean canAct() { return canAct; }
    public void setCanAct(boolean canAct) {
        this.canAct = canAct;}
    public int setCurrentHP(int currentHP){return currentHP;}

    // basic logic of battle
    public void takeDamage(int damage) {
        int newHP = currentHP - damage;
        currentHP = Math.max(0, newHP); // hp>=0
        if (currentHP == 0) {
            System.out.println(name + " has been defeated!");
        }
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    // manage effects
    public void addEffect(Effect effect) {
        activeEffects.add(effect);
        effect.applyEffect(this);
    }

    public void updateEffects() {
        List<Effect> expired = new ArrayList<>();
        for (Effect e : activeEffects) {
            if (e.isActive()) {
                e.tick();
            } else {
                expired.add(e);
            }
        }
        // remove expired effects
        for (Effect e : expired) {
            activeEffects.remove(e);
        }
    }

    // abstract class: different character has different action
    public abstract void performAction(Combatant target);

    public String toString() {
        return name + " [HP: " + currentHP + "/" + maxHP +
               ", ATK: " + attack +
               ", DEF: " + defense +
               ", SPD: " + speed +
               ", CanAct: " + canAct + "]";
    }
}

