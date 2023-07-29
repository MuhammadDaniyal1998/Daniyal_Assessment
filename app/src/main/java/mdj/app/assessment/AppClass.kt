package mdj.app.assessment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppClass : Application() {

    companion object {
        lateinit var application: AppClass
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}