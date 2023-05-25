package cat.jai.mewsic.util

import cat.jai.mewsic.util.di.diLaunch

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        diLaunch(this)
    }
}