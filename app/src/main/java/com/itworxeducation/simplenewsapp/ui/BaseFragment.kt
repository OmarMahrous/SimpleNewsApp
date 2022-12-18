package com.itworxeducation.simplenewsapp.ui

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment(contentLayoutId:Int) : Fragment(contentLayoutId) {

     fun showFieldErrorMessage(msg:String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}