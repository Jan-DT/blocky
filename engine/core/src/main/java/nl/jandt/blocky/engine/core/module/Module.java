package nl.jandt.blocky.engine.core.module;

import nl.jandt.blocky.engine.core.server.Server;

public interface Module {
    void initialize(Server server);
    Server getServer();
}
