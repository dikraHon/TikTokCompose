package com.app.showcode

import android.content.Context
import android.util.Log

object CodeLoader {
    private const val TAG = "CodeLoader"

    fun loadFromAssets(context: Context, fileName: String): String {
        return try {
            context.assets.open("code/$fileName").bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading file: $fileName", e)
            "Error loading file: ${e.message}"
        }
    }

    fun listCodeFiles(context: Context): List<String> {
        return try {
            context.assets.list("code")?.toList() ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Error listing files: ${e.message}", e)
            emptyList()
        }
    }
}
