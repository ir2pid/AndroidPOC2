package com.noisyninja.androidlistpoc.layers.database.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.noisyninja.androidlistpoc.layers.Utils
import com.noisyninja.androidlistpoc.model.Me
import com.noisyninja.androidlistpoc.layers.database.DataBaseModule


/**
 * view model for Me
 * Created by sudiptadutta on 10/05/18.
 */

class MeViewModel(var dataBaseModule: DataBaseModule) : ViewModel() {

    private val meLiveData: LiveData<List<Me>>

    init {
        Utils.logI(this.javaClass, "MeViewModel")
        meLiveData = dataBaseModule.all
    }

    fun getMe(): LiveData<List<Me>> {
        return meLiveData
    }
}
