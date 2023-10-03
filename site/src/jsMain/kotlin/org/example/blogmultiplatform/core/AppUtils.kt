package org.example.blogmultiplatform.core

import kotlinx.browser.localStorage
import org.example.blogmultiplatform.res.Res
import org.w3c.dom.get

object AppUtils{
    fun logout(){
        localStorage[Res.Strings.StoreKeys.user]
    }
}