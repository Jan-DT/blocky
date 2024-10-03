package nl.jandt.blocky

import net.minestom.server.entity.Player
import nl.jandt.blocky.engine.core.server.Server
import nl.jandt.blocky.engine.impl.command.BlockyCommand

class FlightCommand(server: Server) : BlockyCommand(server, "fly") {
    init {
        addSyntax({ sender, _ ->
            if (sender !is Player) return@addSyntax

            val playerObject = getPlayerObject(sender)
            checkNotNull(playerObject)

            val flightTrait: PlayerAllowFlight? = playerObject.getTrait(PlayerAllowFlight::class.java)

            if (flightTrait != null) {
                playerObject.removeTrait(flightTrait)
                sender.sendMessage("You can no longer fly!")
            } else {
                playerObject.addTrait(PlayerAllowFlight::class.java)
                sender.sendMessage("You can now fly!")
            }
        })
    }
}
