package com.ckatsidzira.presentation.util

fun String.toRegularCase(): String {
    val lowerCaseString = this.lowercase()
    return lowerCaseString.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}