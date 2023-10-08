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

/**
 * @param text is the content for the tag
 * [text.first()] is the main content
 * [text[1]] is the link or img src
 * [text[2]] is the description or alt
 */
fun EditorKey.controlStyle(vararg text:String):String = when(this){
    Bold -> "<strong>${text.first()}</strong>"
    Italics -> "<em>${text.first()}t</em>"
    Link -> "<a href=\"${text[1]}\", desc=\"${text[2]}\">${text.first()}<a/>"
    CapsOn -> "<strong><h1>${text.first()}<h1/><strong/>"
    CapsOff -> "<strong><h3>${text.first()}<h3/><strong/>"
    Quote -> "<blockquote class=\"blockquote\">\n" +
            "  <p>${text.first()}</p>\n" +
            "</blockquote>"
    Code -> "<code>${text.first()}<code/>"
    Picture -> "<img src=\"${text[1]}\" alt=\"${text[2]}\" style=\"max-width:100%\">${text.first()}<img/>"
}