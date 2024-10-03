package nl.jandt.blocky

import io.github.oshai.kotlinlogging.KotlinLogging
import net.minestom.server.entity.Player
import nl.jandt.blocky.engine.core.Container
import nl.jandt.blocky.engine.core.trait.Behaviour
import nl.jandt.blocky.engine.impl.trait.PlayerTrait

private val log = KotlinLogging.logger {}

class PlayerAllowFlight(container: Container) : Behaviour(container) {
    private var player: Player? = null

    init {
        parentEventNode().addListener(PlayerTrait.PlayerChangeEvent::class.java, ::onPlayerChange)
        player = getTrait(PlayerTrait::class.java)?.player
    }

    private fun onPlayerChange(playerChangeEvent: PlayerTrait.PlayerChangeEvent) {
        val oldPlayer = playerChangeEvent.oldPlayer
        if (oldPlayer != null) disableFlightForPlayer(oldPlayer)

        player = playerChangeEvent.player
        enableFlightForPlayer(player!!)
    }

    override fun onEnable() {
        if (this.player == null) return

        enableFlightForPlayer(player!!)
    }

    override fun onDisable() {
        if (this.player == null) return

        disableFlightForPlayer(player!!)
    }

    private fun enableFlightForPlayer(player: Player) {
        player.isAllowFlying = true
        player.isFlying = true
        log.debug { "flight enabled for $player" }
    }

    private fun disableFlightForPlayer(player: Player) {
        player.isAllowFlying = false
        player.isFlying = false
        log.debug { "flight disabled for $player" }
    }
}
