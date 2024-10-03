package nl.jandt.blocky.engine.impl.trait;

import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.PlayerEvent;
import nl.jandt.blocky.engine.impl.PlayerService;
import nl.jandt.blocky.engine.core.Container;
import nl.jandt.blocky.engine.core.trait.Behaviour;
import nl.jandt.blocky.engine.impl.event.EventService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerTrait extends Behaviour {
    private @Nullable Player player;

    public PlayerTrait(Container container) {
        super(container);
    }

    public void setPlayer(@NotNull Player player) {
        final var oldPlayer = this.player;
        this.player = player;

        getServer().getServiceManager()
                .tryGetService(PlayerService.class)
                .ifPresent(ps -> ps.setPlayerObject(player, getContainer()));

        getServer().getServiceManager()
                .tryGetService(EventService.class)
                .ifPresent(es -> es.eventNode().call(new PlayerChangeEvent(this, player, oldPlayer)));
    }

    public @Nullable Player getPlayer() {
        return this.player;
    }

    public static class PlayerChangeEvent implements PlayerEvent {
        private final @NotNull PlayerTrait trait;
        private final @NotNull Player newPlayer;
        private final @Nullable Player oldPlayer;

        PlayerChangeEvent(@NotNull PlayerTrait trait, @NotNull Player newPlayer, @Nullable Player oldPlayer) {
            this.trait = trait;
            this.newPlayer = newPlayer;
            this.oldPlayer = oldPlayer;
        }

        public @NotNull PlayerTrait getPlayerTrait() {
            return this.trait;
        }

        @Override
        public @NotNull Player getPlayer() {
            return this.newPlayer;
        }

        public @Nullable Player getOldPlayer() {
            return oldPlayer;
        }
    }
}
