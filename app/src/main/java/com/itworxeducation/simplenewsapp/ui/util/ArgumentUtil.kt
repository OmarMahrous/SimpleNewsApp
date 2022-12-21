package com.itworxeducation.simplenewsapp.ui.util

import android.os.Bundle

class ArgumentUtil {

    companion object{

         fun getStringArgument(args: Bundle?, argName: String): String? {
            return args?.getString(argName)
            }

        }
    }

