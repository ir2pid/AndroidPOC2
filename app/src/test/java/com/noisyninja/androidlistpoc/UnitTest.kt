package com.noisyninja.androidlistpoc

import android.content.Context
import com.noisyninja.androidlistpoc.layers.UtilModule
import com.noisyninja.androidlistpoc.model.Me
import com.noisyninja.androidlistpoc.model.Name
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {

    @Mock
    var context: Context? = null

    @Inject lateinit var utilModule: UtilModule

    lateinit var me: Me
    @Before
    fun setup() {
        utilModule = UtilModule(context)
        me = Me(Name("test"))
    }

    @Test
    fun marshall() {
        val json = utilModule.toJson(me)
        Assert.assertNotNull(json)
    }

    @Test
    fun unmarshall() {
        val json = utilModule.toJson(me)
        Assert.assertEquals(me.name.first, utilModule.fromJson(json, Me::class.java).name.first)
    }
}
