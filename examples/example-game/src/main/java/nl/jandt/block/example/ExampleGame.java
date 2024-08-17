package nl.jandt.block.example;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import nl.jandt.blocky.engine.annotation.BlockyModule;
import nl.jandt.blocky.engine.module.Entrypoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@BlockyModule(
        identifier = "example_game",
        name = "Example Game",
        version = "0.0.1"
)
public class ExampleGame implements Entrypoint {
    private static final Logger log = LoggerFactory.getLogger(ExampleGame.class);

    @Override
    public void init() {
        log.info("starting example game, should be after compatible_game!");
    }

    @Override
    public void registerEvents(EventNode<Event> eventNode) {
    }
}
