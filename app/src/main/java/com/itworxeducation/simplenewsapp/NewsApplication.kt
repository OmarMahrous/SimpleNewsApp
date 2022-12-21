package com.itworxeducation.simplenewsapp

import android.app.Activity
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : Application(){



    companion object{


        fun exitApp(context: Activity){
            context.finishAffinity()
        }
    }

}