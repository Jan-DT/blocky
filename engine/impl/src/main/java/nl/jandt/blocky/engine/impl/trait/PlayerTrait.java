package nl.jandt.blocky.engine.impl.trait;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerMoveEvent;
import nl.jandt.blocky.engine.impl.PlayerService;
import nl.jandt.blocky.engine.core.Container;
import nl.jandt.blocky.engine.core.trait.Behaviour;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerTrait extends Behaviour {
    private static final Logger log = LoggerFactory.getLogger(PlayerTrait.class);
    private Player player;

    public PlayerTrait(Container container) {
        super(container);
    }

    public void initialize(@NotNull Player player) {
        this.player = player;

        player.eventNode().addListener(PlayerMoveEvent.class, this::onMovement);
        getServer().getServiceManager()
                .tryGetService(PlayerService.class)
                .ifPresent(t -> t.setPlayerObject(player, getContainer()));
    }

    private void onMovement(@NotNull PlayerMoveEvent event) {
        log.debug("Player {} moved {}", this.player, event.getNewPosition());
    }
}
