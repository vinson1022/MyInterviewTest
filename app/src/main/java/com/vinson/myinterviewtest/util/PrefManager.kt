package com.vinson.myinterviewtest.util

import android.util.Log
import com.vinson.baseui.preference.BasePref
import com.vinson.baseui.preference.PreferenceProperty
import com.vinson.baseui.preference.PreferenceRepository

object PrefManager {
    const val TAG = "PreManager"

    fun logout() {
        AppPref.clear()
        Log.d(TAG, "logout() called")
    }
}

object AppPref: BasePref() {
    override val repository = PreferenceRepository(appCxt(), "interview.app")

    private const val KEY_HISTORY_QUERY = "history_query"

    var rawHistoryQueries by PreferenceProperty(repository, KEY_HISTORY_QUERY, "")
}