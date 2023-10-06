package org.example.blogmultiplatform.models

import org.example.blogmultiplatform.models.EditorKey.*
import org.example.blogmultiplatform.res.*

val EditorKey.icon: String
    get() = when (this) {
        Bold -> Res.Images.icBold
        Italics -> Res.Images.icItalics
        Link -> Res.Images.icLink
        CapsOn -> Res.Images.icLetterCase
        CapsOff -> Res.Images.icLetterCase
        Quote -> Res.Images.icQuote
        Code -> Res.Images.icCode
        Picture -> Res.Images.icPicture
    }