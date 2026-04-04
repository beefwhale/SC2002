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
}

