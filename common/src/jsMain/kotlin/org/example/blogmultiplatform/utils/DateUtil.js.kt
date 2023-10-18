package org.example.blogmultiplatform.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.toJSDate

actual object DateUtil {
    actual fun Long.toReadableDate(): String {
        return Instant.fromEpochMilliseconds(this).toJSDate()
            .toLocaleDateString()
    }
}