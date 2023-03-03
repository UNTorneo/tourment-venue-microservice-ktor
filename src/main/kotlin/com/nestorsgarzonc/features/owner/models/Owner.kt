package com.nestorsgarzonc.features.owner.models

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("")
data class Owner(val id: String)