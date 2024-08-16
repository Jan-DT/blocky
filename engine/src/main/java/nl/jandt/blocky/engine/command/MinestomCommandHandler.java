package nl.jandt.blocky.engine.command;

import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MinestomCommandHandler implements CommandHandler {
    private final CommandManager commandManager;
    private final Map<UUID, Command> uuidCommandMap = new HashMap<>();

    public MinestomCommandHandler(final CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public UUID register(final Command command) {
        commandManager.register(command);

        final var uuid = UUID.randomUUID();
        uuidCommandMap.put(uuid, command);

        return uuid;
    }

    @Override
    public void unregister(final Command command) {
        commandManager.unregister(command);

        for (var entry : uuidCommandMap.entrySet()) {
            if (entry.getValue() == command) {
                uuidCommandMap.remove(entry.getKey());
            }
        }
    }

    @Override
    public void unregister(final UUID uuid) {
        commandManager.unregister(uuidCommandMap.remove(uuid));
    }

    @Override
    public @Nullable Command getCommand(final UUID uuid) {
        return uuidCommandMap.get(uuid);
    }

    @Override
    public @Nullable Command getCommand(final String command) {
        return commandManager.getCommand(command);
    }

    @Override
    public UUID getUuidForCommand(final Command command) {
        for (var entry : uuidCommandMap.entrySet()) {
            if (entry.getValue() == command) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public UUID getUuidForCommand(final String command) {
        for (var entry : uuidCommandMap.entrySet()) {
            if (entry.getValue().getName().equals(command)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
