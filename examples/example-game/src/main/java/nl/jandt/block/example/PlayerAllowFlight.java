package nl.jandt.block.example;

import net.minestom.server.entity.Player;
import nl.jandt.blocky.engine.core.Behaviour;
import nl.jandt.blocky.engine.core.Container;

public class PlayerAllowFlight extends Behaviour {
    private Player player;

    public PlayerAllowFlight(Container container) {
        super(container, false);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    protected void onEnable() {
        this.player.setAllowFlying(true);
    }

    @Override
    protected void onDisable() {
        this.player.setAllowFlying(false);
    }
}
