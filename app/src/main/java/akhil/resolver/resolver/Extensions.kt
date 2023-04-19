package akhil.resolver.resolver

import android.util.Log

fun Any?.loge(tag: String = "Akhil") {
    Log.e(tag, "${this?.toString()}")
}