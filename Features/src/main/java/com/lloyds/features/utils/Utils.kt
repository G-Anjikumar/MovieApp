package com.lloyds.features.utils

import androidx.core.text.HtmlCompat

object Utils {

    fun removeHtmlTags(input: String): String {
        return HtmlCompat.fromHtml(input, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }
}