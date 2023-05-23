package cat.jai.innertube.util

import kotlin.random.Random

object RandUtils {
    fun randUuidString(): String {
        return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(Regex("[xy]")) {
            val r = (Random.nextFloat() * 16).toInt()
            val c = if (it.value == "x") r else (r and 0x3 or 0x8)
            c.toString(16)
        }
    }
}