package org.example.blogmultiplatform.models

import org.example.blogmultiplatform.models.EditorKey.*

enum class EditorKey {
    Bold,
    Italics,
    Link,
    CapsOn,
    CapsOff,
    Quote,
    Code,
    Image,
}

/**
 * @param text is the content for the tag
 * [text.first()] is the main content
 * [text[1]] is the link or img src
 * [text[2]] is the description or alt
 */
fun EditorKey.controlStyle(vararg text: String): String = when (this) {
    Bold -> "**${text.first()}**"
    Italics -> "*${text.first()}*"
    Link -> "[${text.getOrNull(1)}](${text.first()})"
    CapsOn -> "\n## ${text.first()}"
    CapsOff -> "\n### ${text.first()}"
    Quote -> "> ${text.first()}\n"
    Code -> "```" +
            "\n${text.first()}\n" +
            "```"
    Image -> "![${text.first()}](${text.getOrNull(1)})"
}

/**
 * Ho far the cursor has to move after style has been set
 */
val EditorKey.stylingCursorOffset:Int
    get() = when(this){
        Bold -> -2
        Italics -> 1
        Link -> -2
        CapsOn -> 3
        CapsOff -> 4
        Quote -> 2
        Code -> 4
        Image -> 0
    }