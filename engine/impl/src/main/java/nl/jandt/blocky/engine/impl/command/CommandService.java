package nl.jandt.blocky.engine.impl.command;

import net.minestom.server.command.builder.Command;
import nl.jandt.blocky.engine.core.service.Service;

import java.util.UUID;

public interface CommandService extends Service {
    UUID register(Command command);

    void unregister(Command command);
    void unregister(UUID uuid);

    Command getCommand(UUID uuid);
    Command getCommand(String command);

    UUID getUuidForCommand(Command command);
    UUID getUuidForCommand(String command);
}
