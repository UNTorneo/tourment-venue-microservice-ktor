package com.nestorsgarzonc.core.di

import com.nestorsgarzonc.features.owner.controller.OwnerController
import com.nestorsgarzonc.core.plugins.DatabaseFactory
import com.nestorsgarzonc.features.court.controller.CourtController
import com.nestorsgarzonc.features.photos.controller.PhotosController
import com.nestorsgarzonc.features.schedule.controller.ScheduleController
import com.nestorsgarzonc.features.venue.controller.VenueController
import org.koin.dsl.module

val diModule = module {
    single { DatabaseFactory }
    single { OwnerController() }
    single { VenueController() }
    single { CourtController() }
    single { PhotosController() }
    single { ScheduleController() }
//    single { UserService(get()) }
}