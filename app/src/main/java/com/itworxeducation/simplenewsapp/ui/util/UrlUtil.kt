package com.itworxeducation.simplenewsapp.ui.util

import android.content.Context
import android.content.Intent
import android.net.Uri

class UrlUtil {

    companion object{

        fun openWebPage(context: Context, url: String) {
            val webpage: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
        }

    }

}