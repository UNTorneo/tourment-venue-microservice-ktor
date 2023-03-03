package com.nestorsgarzonc.di
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

// Constructor DSL
val diModule = module {
    //singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    //singleOf(::UserService)
}

// Classic DSL
//val appModule = module {
//    single<UserRepository> { UserRepositoryImpl() }
//    single { UserService(get()) }
//}