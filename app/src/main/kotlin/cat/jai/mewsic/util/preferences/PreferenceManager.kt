package cat.jai.mewsic.util.preferences

import android.content.SharedPreferences
import kotlin.reflect.KProperty

class PreferenceManager(sharedPreferences: SharedPreferences) : PreferenceAccessor(sharedPreferences) {
    var lastFmEndpoint by preference("lastFmEndpoint", "https://ws.audioscrobbler.com/2.0/")
}
