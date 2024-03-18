package dev.maxsiomin.prodhse.core

fun isDebug() = BuildConfig.DEBUG

fun isRelease() = !isDebug()
