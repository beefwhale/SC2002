package sc2002.engine;

public class PlayerCombatant extends BaseCombatant {
    private final PlayerType playerType;

    public PlayerCombatant(String name, PlayerType playerType, int hp, int attack, int defense, int speed) {
        super(name, Team.PLAYER, hp, attack, defense, speed);
        this.playerType = playerType;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
    public void useItem(Item item) {
        if (inventory.contains(item)) {
            item.use(this);
            inventory.remove(item);
            System.out.println(this.name + " used " + item.getName() + "!");
        } else {
            System.out.println(this.name + " does not have " + item.getName() + "!");
        }
    }

    public void updateCooldown() {
        if (skillCooldown > 0) {
            skillCooldown--;
        }
    }

    public abstract void useSpecialSkill(List<Combatant> enemies);

    public abstract void chooseAction(List<Combatant> enemies);
}

