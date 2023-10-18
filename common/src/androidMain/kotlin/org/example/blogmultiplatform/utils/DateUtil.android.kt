package org.example.blogmultiplatform.utils

import android.icu.text.DateFormat
import java.util.*

actual object DateUtil {
    actual fun Long.toReadableDate(): String {
        return DateFormat.getDateInstance().format(Date(this))
    }
}