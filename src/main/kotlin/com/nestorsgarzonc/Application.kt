package com.nestorsgarzonc
import com.nestorsgarzonc.core.env.EnvManager
import com.nestorsgarzonc.core.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureDI()
    //configureSecurity()
    val db by inject<DatabaseFactory>()
    db.init(config = environment.config)
    EnvManager.init(config = environment.config)
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}