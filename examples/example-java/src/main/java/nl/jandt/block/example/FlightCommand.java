package nl.jandt.block.example;

import net.minestom.server.entity.Player;
import nl.jandt.blocky.engine.impl.command.BlockyCommand;
import nl.jandt.blocky.engine.core.server.Server;

public class FlightCommand extends BlockyCommand {
    public FlightCommand(Server server) {
        super(server, "fly");

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) {
                final var playerObject = getPlayerObject(player);
                assert playerObject != null;

                final var flightTrait = playerObject.tryGetTrait(PlayerAllowFlight.class);

                if (flightTrait.isPresent()) {
                    playerObject.removeTrait(flightTrait.get());
                    sender.sendMessage("You can no longer fly!");
                } else {
                    playerObject.tryAddTrait(PlayerAllowFlight.class);
                    sender.sendMessage("You can now fly!");
                }
            }
        });
    }
}
