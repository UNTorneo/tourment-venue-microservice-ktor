package com.nestorsgarzonc.core.env

import io.ktor.server.config.*

object EnvManager {
    lateinit var usersMS: String
    fun init(config: ApplicationConfig){
        usersMS=config.propertyOrNull("deployment.ms.usersMS")?.getString() ?: "http://127.0.0.1:8081"
    }
}