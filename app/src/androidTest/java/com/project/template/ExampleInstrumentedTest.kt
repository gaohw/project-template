package com.project.template

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ctsi.vip.lib.framework.http.response.BeanResponse
import com.ctsi.vip.lib.framework.utils.JsonUtils
import com.google.gson.reflect.TypeToken

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.project.template", appContext.packageName)]
//    }

    @Test
    fun test() {
        testLocal<String>("{\"code\":0,\"data\":1,\"msg\":\"\"}")
    }

    private fun <T> testLocal(result: String) {
        val response = JsonUtils.fromJson<BeanResponse<T>>(
            result,
            object : TypeToken<BeanResponse<T>>() {}.type
        )
        println("================> ${response.data}")
    }
}