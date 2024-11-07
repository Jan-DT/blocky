package nl.jandt.blocky

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.timer.TaskSchedule
import nl.jandt.blocky.engine.core.module.BasicModule
import nl.jandt.blocky.engine.core.world.World
import nl.jandt.blocky.engine.impl.MinestomModule
import nl.jandt.blocky.engine.impl.MinestomServer
import nl.jandt.blocky.engine.impl.command.CommandService
import nl.jandt.blocky.engine.impl.event.EventService
import nl.jandt.blocky.engine.impl.trait.PlayerTrait

fun main() {
    MinestomServer.init(KotlinServer())
        .run("0.0.0.0", 25565)
}

class KotlinServer : BasicModule() {
    override fun getServer(): MinestomServer = super.server as MinestomServer

    override fun setup() {
        registerModule(MinestomModule())

        val eventNode = getService(EventService::class.java).eventNode()
        val world = World(server)
        MinecraftServer.getSchedulerManager().submitTask {
            world._update()
            TaskSchedule.nextTick()
        }

        eventNode.addListener(AsyncPlayerConfigurationEvent::class.java) { event ->
            event.spawningInstance = MinecraftServer.getInstanceManager().createInstanceContainer()
        }

        eventNode.addListener(PlayerSpawnEvent::class.java) { event ->
            val player = event.player
            val playerObject = world.createObject()
            playerObject.tryAddTrait(PlayerTrait::class.java)
                .ifPresent { t: PlayerTrait -> t.setPlayer(player) }
            playerObject.addTrait(PlayerAllowFlight::class.java)
        }

        getService(CommandService::class.java)
            .register(FlightCommand(server))
    }
}
