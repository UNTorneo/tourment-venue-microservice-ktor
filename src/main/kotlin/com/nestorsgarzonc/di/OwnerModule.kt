package com.nestorsgarzonc.di
import com.nestorsgarzonc.plugins.DatabaseFactory
import org.koin.dsl.module

val diModule = module {
    single<DatabaseFactory> { DatabaseFactory() }
//    single { UserService(get()) }
}