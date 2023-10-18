package com.example.android.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.*

object ImageDecoder {
    fun String.decodeThumbnailImage():Bitmap?{
        return try {
            val byteArray = Base64.getDecoder().decode(cleanupImageString())
            return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
        }catch (e:Exception){
            null
        }
    }

    private fun String.cleanupImageString(): String {
        return this.replace("data:image/png;base64,","")
            .replace("data:image/jpeg;base64,","")
    }
}