package cat.jai.mewsic.util.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

inline fun <reified T : Context> diLaunch(
    that: T
) {
    startKoin {
        androidContext(that)

        modules(

        )
    }
}