package com.nestorsgarzonc.core.plugins
import com.nestorsgarzonc.core.di.diModule
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
