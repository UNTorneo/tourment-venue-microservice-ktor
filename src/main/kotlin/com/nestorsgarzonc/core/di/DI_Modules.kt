package com.nestorsgarzonc.core.di
import com.nestorsgarzonc.features.owner.controller.OwnerController
import com.nestorsgarzonc.core.plugins.DatabaseFactory
import com.nestorsgarzonc.features.venue.controller.VenueController
import org.koin.dsl.module

val diModule = module {
    single<DatabaseFactory> { DatabaseFactory }
    single<OwnerController> {OwnerController()}
    single<VenueController> { VenueController() }
//    single { UserService(get()) }
}