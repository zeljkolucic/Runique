package com.zeljkolucic.run.location

import android.location.Location
import com.zeljkolucic.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.zeljkolucic.core.domain.location.Location(
            latitude = latitude,
            longitude = longitude
        ),
        altitude = altitude
    )
}