package com.itworxeducation.simplenewsapp.ui.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent

class UrlUtil {

    companion object{

        private val TAG = "UrlUtil"

        fun openWebPage(context: Context, url: String) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                        // The URL should either launch directly in a non-browser app (if it's
                        // the default), or in the disambiguation dialog.
                        addCategory(Intent.CATEGORY_BROWSABLE)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
                    }
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    // Only browser apps are available, or a browser is the default.
                    // So you can open the URL directly in your app, for example in a
                    // Custom Tab.
                    Log.e(
                        TAG, "ActivityNotFoundException: " + e.message
                    )
//                openInCustomTabs(url)
                }
            } else {

                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse(url)
                if (browserIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(browserIntent)
                }
            }
        }

        fun openURL(context: Context, url: String) {
            val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            builder.setShowTitle(true)
            val customTabsIntent: CustomTabsIntent = builder.build()
            val browserIntent = Intent()
                .setAction(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setType("text/plain")
                .setData(Uri.fromParts("http", "", null))
            var possibleBrowsers: List<ResolveInfo>

            context.let {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                possibleBrowsers = it.getPackageManager()
                    .queryIntentActivities(browserIntent, PackageManager.MATCH_DEFAULT_ONLY)
                if (possibleBrowsers.size == 0) {
                    possibleBrowsers = it.getPackageManager()
                        .queryIntentActivities(browserIntent, PackageManager.MATCH_ALL)
                }
            } else {
                possibleBrowsers = it.getPackageManager()
                    .queryIntentActivities(browserIntent, PackageManager.MATCH_DEFAULT_ONLY)
            }
                val uri = Uri.parse(url)

                if (possibleBrowsers.size > 0) {

                customTabsIntent.intent.setPackage(possibleBrowsers[0].activityInfo.packageName)
                customTabsIntent.launchUrl(it, uri)
            } else {
                val browserIntent2 = Intent(Intent.ACTION_VIEW, uri)
                it.startActivity(browserIntent2)
            }
        }
        }


    }

}