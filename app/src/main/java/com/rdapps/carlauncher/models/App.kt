package com.rdapps.carlauncher.models

import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable

data class App(
    val name: String,
    val icon: Drawable?,
    val packageName: String,
    val className: String
) {
    companion object {
        fun prepareFrom(packageManager: PackageManager, resolveInfo: ResolveInfo): App {
            return App(
                resolveInfo.loadLabel(packageManager).toString(),
                resolveInfo.loadIcon(packageManager),
                resolveInfo.activityInfo.packageName,
                resolveInfo.activityInfo.name
            )
        }
    }
}
