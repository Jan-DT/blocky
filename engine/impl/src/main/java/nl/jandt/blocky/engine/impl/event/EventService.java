package nl.jandt.blocky.engine.impl.event;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import nl.jandt.blocky.engine.core.service.Service;

public interface EventService extends Service {
    EventNode<Event> eventNode();
}
