package nl.jandt.blocky.engine.impl.command;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import nl.jandt.blocky.engine.impl.trait.PlayerTrait;
import nl.jandt.blocky.engine.impl.PlayerService;
import nl.jandt.blocky.engine.core.WorldObject;
import nl.jandt.blocky.engine.core.server.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BlockyCommand extends Command {
    private final Server server;

    public BlockyCommand(@NotNull Server server, @NotNull String name) {
        super(name);

        this.server = server;
    }

    public BlockyCommand(@NotNull Server server, @NotNull String name, @Nullable String... aliases) {
        super(name, aliases);

        this.server = server;
    }

    /**
     * @param player Player to get the object for. Usually something like {@code (Player) sender}.
     * @implNote Assumes the server has a {@link PlayerService} registered, or this will return {@code null}.
     * @return The {@link WorldObject} that has the {@link PlayerTrait} related to this player.
     */
    public @Nullable WorldObject getPlayerObject(Player player) {
        return server.getServiceManager()
                .tryGetService(PlayerService.class)
                .flatMap(playerService -> playerService.tryGetPlayerObject(player))
                .orElse(null);
    }

    /**
     * @param player Player to get the object for. Usually something like {@code (Player) sender}.
     * @implNote Assumes the server has a {@link PlayerService} registered, or this will return {@link Optional#empty()}.
     * @return The {@link WorldObject} that has the {@link PlayerTrait} related to this player.
     */
    public @NotNull Optional<WorldObject> tryGetPlayerObject(Player player) {
        return Optional.ofNullable(getPlayerObject(player));
    }
}
