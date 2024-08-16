package nl.jandt.blocky.engine.command;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandResult;

import java.util.UUID;

public interface CommandHandler {
    UUID register(Command command);

    void unregister(Command command);
    void unregister(UUID uuid);

    Command getCommand(UUID uuid);
    Command getCommand(String command);

    UUID getUuidForCommand(Command command);
    UUID getUuidForCommand(String command);
}
