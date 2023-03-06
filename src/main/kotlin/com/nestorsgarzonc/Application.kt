package com.nestorsgarzonc
import com.nestorsgarzonc.core.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.nestorsgarzonc.plugins.*
import org.koin.ktor.ext.inject

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDI()
    //configureSecurity()
    val db by inject<DatabaseFactory>()
    db.init()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
