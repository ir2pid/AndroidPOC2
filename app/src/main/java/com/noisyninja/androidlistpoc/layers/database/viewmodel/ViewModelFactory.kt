package com.noisyninja.androidlistpoc.layers.database.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * View model factory
 * Created by sudiptadutta on 10/05/18.
 */

class ViewModelFactory @Inject constructor() : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // if (modelClass.isAssignableFrom(MeViewModel::class.java)) {
        return MeViewModel() as T
        //}
    }
}
