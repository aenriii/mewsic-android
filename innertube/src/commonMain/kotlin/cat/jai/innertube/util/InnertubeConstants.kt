package cat.jai.innertube.util

object InnertubeConstants {
    object Api {
        const val url = "https://www.youtube.com/youtubei/v1/"
        const val key = "AIzaSyCtkvNIR1HCEwzsqK6JuE6KqpyjusIRI30"
    }
    object Client {
        object Name {
            const val android = "ANDROID"
            const val web = "WEB"
            const val tv = "TVHTML5"
        }
        object Version {
            const val android = "17.11.37"
            const val web = "2.20230221.01.00"
            const val tv = "2.20230221.01.00"
        }
    }
    object Platform {
        const val android = "MOBILE"
        const val web = "DESKTOP"
        const val tv = "TVHTML5" // todo: confirm this
    }
    object FormFactor {
        const val android = "SMALL_FORM_FACTOR"
        const val web = "UNKNOWN_FORM_FACTOR"
        const val tv = "TV" // todo: confirm this
    }

    object Regices {
        object Tv {
            val baseJs = Regex("""<script id="base-js" src="(.*?)" nonce=".*?"></script>""")
            val client = Regex("""clientId:"([-\w]+\.apps\.googleusercontent\.com)",\w+:"(\w+)"""")
        }

    }
}