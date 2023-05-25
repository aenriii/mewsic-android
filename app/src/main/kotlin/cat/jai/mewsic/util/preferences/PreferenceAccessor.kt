package cat.jai.mewsic.util.preferences

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import kotlin.reflect.KProperty

open class PreferenceAccessor(protected val sharedPreferences: SharedPreferences) {
    protected class Preference<T>(
        private val key: String,
        private val defaultValue: T,
        val getter: SharedPreferences.(String, T) -> T,
        val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor,
        val preferences: SharedPreferences
    ) : MutableState<T> {
        override var value: T
            get() {
                return preferences.getter(key, defaultValue)
            }
            set(value) {
                preferences.edit().setter(key, value).apply()
            }

        override fun component1(): T {
            return value
        }

        override fun component2(): (T) -> Unit {
            return { value = it }
        }

        operator fun getValue(
            thisRef: Any?,
            property: KProperty<*>
        ): T = preferences.getter(key, defaultValue)

        operator fun setValue(
            thisRef: Any?,
            property: KProperty<*>,
            value: T
        ) = preferences.edit().setter(key, value).apply()
    }
    protected fun preference(
        key: String,
        defaultValue: String
    ) = Preference(key, defaultValue, { k, d -> getString(k, d) ?: d}, SharedPreferences.Editor::putString, sharedPreferences)

    protected fun preference(
        key: String,
        defaultValue: Int
    ) = Preference(key, defaultValue, SharedPreferences::getInt, SharedPreferences.Editor::putInt, sharedPreferences)

    protected fun preference(
        key: String,
        defaultValue: Long
    ) = Preference(key, defaultValue, SharedPreferences::getLong, SharedPreferences.Editor::putLong, sharedPreferences)

    protected fun preference(
        key: String,
        defaultValue: Float
    ) = Preference(key, defaultValue, SharedPreferences::getFloat, SharedPreferences.Editor::putFloat, sharedPreferences)

    protected fun preference(
        key: String,
        defaultValue: Boolean
    ) = Preference(key, defaultValue, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean, sharedPreferences)

    protected fun preference(
        key: String,
        defaultValue: Set<String>
    ) = Preference(key, defaultValue, SharedPreferences::getStringSet, SharedPreferences.Editor::putStringSet, sharedPreferences)

    protected inline fun <reified E : Enum<E>> preference(
        key: String,
        defaultValue: E,
    ) = Preference(key, defaultValue, { k, d -> enumValueOf(getString(k, d.name) ?: d.name) }, { k, v -> putString(k, v.name) }, sharedPreferences)

}