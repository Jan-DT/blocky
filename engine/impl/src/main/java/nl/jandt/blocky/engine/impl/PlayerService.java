package nl.jandt.blocky.engine.impl;

import net.minestom.server.entity.Player;
import nl.jandt.blocky.engine.core.WorldObject;
import nl.jandt.blocky.engine.core.service.Service;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerService implements Service {
    private final Map<Player, WorldObject> playerObjectMap = new ConcurrentHashMap<>();
    private final Map<WorldObject, Player> objectPlayerMap = new ConcurrentHashMap<>();

    public @NotNull Optional<WorldObject> setPlayerObject(Player player, WorldObject object) {
        objectPlayerMap.put(object, player);
        return Optional.ofNullable(playerObjectMap.put(player, object));
    }

    public boolean objectHasPlayer(WorldObject object) {
        return objectPlayerMap.containsKey(object);
    }

    public boolean playerHasObject(Player player) {
        return playerObjectMap.containsKey(player);
    }

    public @Nullable Player getObjectPlayer(WorldObject object) {
        return objectPlayerMap.get(object);
    }

    public Optional<Player> tryGetObjectPlayer(WorldObject object) {
        return Optional.ofNullable(getObjectPlayer(object));
    }

    public @Nullable WorldObject getPlayerObject(Player player) {
        return playerObjectMap.get(player);
    }

    public Optional<WorldObject> tryGetPlayerObject(Player player) {
        return Optional.ofNullable(getPlayerObject(player));
    }
}
