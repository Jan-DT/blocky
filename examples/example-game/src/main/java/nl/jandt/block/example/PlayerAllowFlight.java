package nl.jandt.block.example;

import net.minestom.server.entity.Player;
import nl.jandt.blocky.engine.core.trait.Behaviour;
import nl.jandt.blocky.engine.core.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerAllowFlight extends Behaviour {
    private static final Logger log = LoggerFactory.getLogger(PlayerAllowFlight.class);
    private Player player;

    public PlayerAllowFlight(Container container) {
        super(container, true);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    protected void onUpdate() {
//        if (this.player.isFlying())
//            log.trace("Player is flying!");
    }

    @Override
    protected void onEnable() {
        this.player.setAllowFlying(true);
        this.player.setFlying(true);
        log.debug("flight enabled for {}", player);
    }

    @Override
    protected void onDisable() {
        this.player.setAllowFlying(false);
        this.player.setFlying(false);
        log.debug("flight disabled for {}", player);
    }
}
