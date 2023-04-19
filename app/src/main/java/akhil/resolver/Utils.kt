package akhil.resolver

import android.content.Context
import android.content.pm.PackageManager

fun isAppInstalled(context: Context, packageName: String): Boolean {
    val packageManager = context.packageManager
    return try {
        // Attempt to get the application info for the specified package name
        packageManager.getApplicationInfo(packageName, 0)
        // If the application info is retrieved without an exception, the app is installed
        true
    } catch (e: PackageManager.NameNotFoundException) {
        // If a NameNotFoundException is thrown, the app is not installed
        false
    }
}