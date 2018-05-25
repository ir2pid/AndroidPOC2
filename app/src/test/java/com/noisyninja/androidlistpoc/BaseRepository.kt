package com.noisyninja.androidlistpoc

import com.noisyninja.androidlistpoc.model.MeResponse
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import okhttp3.mockwebserver.MockWebServer
import java.util.*


open class BaseRepository : BaseUnit() {

    lateinit var mMockWebServer: MockWebServer
    lateinit var mSubscriber: TestObserver<MeResponse>

    lateinit var meResponse: MeResponse

    fun setUpRepository() {

        mMockWebServer = MockWebServer()
        mSubscriber = TestObserver()
        meResponse = MeResponse()
        meResponse.people = Arrays.asList(me1, me2, me3, me4)

    }

}