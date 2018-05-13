package com.noisyninja.androidlistpoc.layers.database.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.noisyninja.androidlistpoc.layers.database.DataBaseModule
import javax.inject.Inject

/**
 * View model factory
 * Created by sudiptadutta on 10/05/18.
 */

class ViewModelFactory @Inject constructor(val dataBaseModule: DataBaseModule) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MeViewModel::class.java)) {
            MeViewModel(dataBaseModule) as T
        } else {//(modelClass.equals(CustomerViewModel.class))
            MeViewModel(dataBaseModule) as T
        }
    }
}
