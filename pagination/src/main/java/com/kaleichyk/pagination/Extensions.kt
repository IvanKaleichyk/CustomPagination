package com.kaleichyk.pagination

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast

private const val TAG = "MAIN_APP_TAG"

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun showLog(text: String) {
    Log.d(TAG, text)
}

fun ViewGroup.getView(res: Int) = LayoutInflater.from(context).inflate(res, this, false)