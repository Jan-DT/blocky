package nl.jandt.block.example;

import net.minestom.server.entity.Player;
import nl.jandt.blocky.engine.core.trait.Behaviour;
import nl.jandt.blocky.engine.impl.trait.PlayerTrait;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerAllowFlight extends Behaviour {
    private static final Logger log = LoggerFactory.getLogger(PlayerAllowFlight.class);
    private Player player;

    public PlayerAllowFlight() {
        super(false);
    }

    protected void onSetup() {
        parentEventNode().addListener(PlayerTrait.PlayerChangeEvent.class, this::onPlayerChange);

        tryGetTrait(PlayerTrait.class).ifPresent(t -> this.player = t.getPlayer());
    }

    private void onPlayerChange(PlayerTrait.@NotNull PlayerChangeEvent playerChangeEvent) {
        final var oldPlayer = playerChangeEvent.getOldPlayer();
        if (oldPlayer != null) disableFlightForPlayer(oldPlayer);

        this.player = playerChangeEvent.getPlayer();
        enableFlightForPlayer(this.player);
    }

    @Override
    protected void onUpdate() {
//        if (this.player.isFlying())
//            log.trace("Player is flying!");
    }

    @Override
    protected void onEnable() {
        if (this.player == null) return;

        enableFlightForPlayer(this.player);
    }

    @Override
    protected void onDisable() {
        if (this.player == null) return;

        disableFlightForPlayer(this.player);
    }

    public static void enableFlightForPlayer(@NotNull Player player) {
        player.setAllowFlying(true);
        player.setFlying(true);
        log.debug("flight enabled for {}", player);
    }

    public static void disableFlightForPlayer(@NotNull Player player) {
        player.setAllowFlying(false);
        player.setFlying(false);
        log.debug("flight disabled for {}", player);
    }
}
