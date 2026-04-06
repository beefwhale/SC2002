package sc2002.engine;

// TODO: Person 2 - Implement player characters
public class Wizard extends PlayerCombatant {
    public Wizard() {
        super("Wizard", PlayerType.WIZARD, 200, 50, 10, 20);
    }
    @Override
    public void performAction(Combatant target) {
        basicAttack(target);
    }

    public void basicAttack(Combatant target) {
        int damage = Math.max(0, this.attack - target.getDefense());
        target.takeDamage(damage);
        System.out.println(this.name + " attacks " + target.getName() + " for " + damage + " damage!");
    }

    public void defend() {
        this.defense += 10;
        System.out.println(this.name + " defends, increasing defense by 10 for this and next round!");
    }

    @Override
    public void useSpecialSkill(List<Combatant> enemies) {
        if (skillCooldown == 0) {
            System.out.println(this.name + " unleashes Arcane Blast!");

            for (Combatant enemy : enemies) {
                if (enemy.isAlive()) {
                    int damage = Math.max(0, this.attack - enemy.getDefense());
                    enemy.takeDamage(damage);
                    System.out.println("Arcane Blast hits " + enemy.getName() + " for " + damage + " damage!");

                    if (!enemy.isAlive()) {
                        this.attack += 10;
                        System.out.println(this.name + " defeated " + enemy.getName() +
                                           " and gains +10 Attack! Current ATK: " + this.attack);
                    }
                }
            }
            skillCooldown = 3;
        } else {
            System.out.println("Arcane Blast is on cooldown for " + skillCooldown + " more turns!");
        }
    }

    @Override
    public void chooseAction(List<Combatant> enemies) {
        useSpecialSkill(enemies);
    }
}

