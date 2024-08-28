package com.example.littlelemon.ui.navigation

sealed interface Destinations {
    var route: String

    object Home : Destinations {
        override var route: String = "Home"
    }

    object Profile : Destinations {
        override var route: String = "Profile"
    }

    object Onboarding : Destinations {
        override var route: String = "Onboarding"
    }
}
