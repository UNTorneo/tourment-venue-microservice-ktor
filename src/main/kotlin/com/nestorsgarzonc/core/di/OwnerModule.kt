package com.nestorsgarzonc.core.di
import com.nestorsgarzonc.features.owner.controller.OwnerController
import com.nestorsgarzonc.core.plugins.DatabaseFactory
import org.koin.dsl.module

val diModule = module {
    single<DatabaseFactory> { DatabaseFactory }
    single<OwnerController> {OwnerController()}
//    single { UserService(get()) }
}