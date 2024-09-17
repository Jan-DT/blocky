package nl.jandt.block.example;

import net.minestom.server.entity.Player;
import nl.jandt.blocky.engine.core.trait.Behaviour;
import nl.jandt.blocky.engine.core.Container;
import nl.jandt.blocky.engine.impl.trait.PlayerTrait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerAllowFlight extends Behaviour {
    private static final Logger log = LoggerFactory.getLogger(PlayerAllowFlight.class);
    private Player player;

    public PlayerAllowFlight(Container container) {
        super(container, true);
    }

    @Override
    protected void onUpdate() {
//        if (this.player.isFlying())
//            log.trace("Player is flying!");
    }

    @Override
    protected void onEnable() {
        final var playerTrait = tryGetTrait(PlayerTrait.class);
        playerTrait.ifPresent(trait -> this.player = trait.getPlayer());

        if (this.player == null) return;

        this.player.setAllowFlying(true);
        this.player.setFlying(true);
        log.debug("flight enabled for {}", player);
    }

    @Override
    protected void onDisable() {
        if (this.player == null) return;

        this.player.setAllowFlying(false);
        this.player.setFlying(false);
        log.debug("flight disabled for {}", player);
    }
}
