package nl.jandt.blocky.extensions

import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import nl.jandt.blocky.engine.core.trait.Behaviour

val Behaviour.parentEventNode: EventNode<Event>
    get() = this.parentEventNode()
