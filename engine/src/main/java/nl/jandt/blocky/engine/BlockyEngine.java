package nl.jandt.blocky.engine;

import nl.jandt.blocky.engine.util.SemVer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockyEngine {
    public static final SemVer VERSION = SemVer.from("0.0.1");
    private static final Logger log = LoggerFactory.getLogger(BlockyEngine.class);

    public static void main(String[] args) {
        log.info("---");
        log.info("| Running Blocky v{} - © Copyright JanDT 2024", VERSION);
        log.info("| ");
        log.info("| This program is using the Blocky engine, which is free software,");
        log.info("| licensed under the GNU Lesser General Public License (LGPL) version 3.");
        log.info("| Please note: The program itself and other assets may be subject to different licensing terms.");
        log.info("---");
    }
}