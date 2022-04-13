package id.go.jabarprov.dbmpr.utils.functions

import androidx.core.net.toUri
import androidx.navigation.NavActionBuilder
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkRequest

fun createDeepLinkUri(): NavDeepLinkRequest {
    return NavDeepLinkRequest.Builder.fromUri("https://temanjabar.dbmpr.jabarprov.go.id/map?lat=-6.93727&long=107.60401".toUri())
        .build()
}