package nl.jandt.block.example;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.timer.TaskSchedule;
import nl.jandt.blocky.engine.impl.MinestomModule;
import nl.jandt.blocky.engine.impl.MinestomServer;
import nl.jandt.blocky.engine.impl.command.CommandService;
import nl.jandt.blocky.engine.core.module.BasicModule;
import nl.jandt.blocky.engine.core.world.World;
import nl.jandt.blocky.engine.impl.event.EventService;
import nl.jandt.blocky.engine.impl.trait.PlayerTrait;

public class JavaServer extends BasicModule {
    public static void main(String[] args) {
        MinestomServer
                .init(new JavaServer())
                .run("0.0.0.0", 25565);
    }

    @Override
    public MinestomServer getServer() {
        return (MinestomServer) super.getServer();
    }

    @Override
    public void setup() {
        registerModule(new MinestomModule());

        final var eventNode = getService(EventService.class).eventNode();
        final var world = new World(getServer());
        MinecraftServer.getSchedulerManager().submitTask(() -> {
            world._update();
            return TaskSchedule.nextTick();
        });

        eventNode.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.setSpawningInstance(MinecraftServer.getInstanceManager().createInstanceContainer());
        });

        eventNode.addListener(PlayerSpawnEvent.class, event -> {
            final var player = event.getPlayer();
            final var playerObject = world.createObject();
            playerObject.tryAddTrait(PlayerTrait.class)
                    .ifPresent(t -> t.setPlayer(player));
            playerObject.tryAddTrait(PlayerAllowFlight.class);
        });

        getService(CommandService.class)
                .register(new FlightCommand(getServer()));
    }
}
