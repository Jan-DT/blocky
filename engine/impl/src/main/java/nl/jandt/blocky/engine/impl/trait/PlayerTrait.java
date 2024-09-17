package nl.jandt.blocky.engine.impl.trait;

import net.minestom.server.entity.Player;
import nl.jandt.blocky.engine.impl.PlayerService;
import nl.jandt.blocky.engine.core.Container;
import nl.jandt.blocky.engine.core.trait.Behaviour;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerTrait extends Behaviour {
    private Player player;

    public PlayerTrait(Container container) {
        super(container);
    }

    public void initialize(@NotNull Player player) {
        this.player = player;

        getServer().getServiceManager()
                .tryGetService(PlayerService.class)
                .ifPresent(t -> t.setPlayerObject(player, getContainer()));
    }

    public @Nullable Player getPlayer() {
        return this.player;
    }
}
