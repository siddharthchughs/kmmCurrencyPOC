package com.example.mycurrency.domain.model

import mycurrency.composeapp.generated.resources.Res
import mycurrency.composeapp.generated.resources.european_union
import mycurrency.composeapp.generated.resources.united_kingdom
import mycurrency.composeapp.generated.resources.united_states
import org.jetbrains.compose.resources.DrawableResource

enum class CurrencyCode(
    val country: String,
    val flag: DrawableResource
) {
    USD("United States", flag = Res.drawable.united_states),
    EUR("Europe", flag = Res.drawable.european_union),
    GBP("United Kingdom", flag = Res.drawable.united_kingdom),

}