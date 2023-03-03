package com.nestorsgarzonc.plugins
import com.nestorsgarzonc.di.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(listOf(diModule))
    }
}
