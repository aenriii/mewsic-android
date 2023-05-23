package cat.jai.mewsic.data.standards

import cat.jai.mewsic.data.generator.Generator
import cat.jai.mewsic.data.supporting.ImageSource
import kotlin.random.Random

interface MediaMetadata {
    fun getTitle(): String
    fun getSubtitle(): String
    fun getTrititle(): String?

    fun getArtwork(): ImageSource
}

fun testData_MediaMetadata(count: Int): Generator<MediaMetadata> {
    var curr = 0
    return Generator {
        if (curr >= count) return@Generator null
        curr++

        return@Generator object : MediaMetadata {
            val rand = Random(curr)
            val source = ImageSource.UrlImageSource(
                "https://placekitten.com/${200+rand.nextInt(100)}/${200+rand.nextInt(100)}"
            )
            val _title = "Some Title ($curr)"
            override fun getTitle(): String {
                return _title
            }

            override fun getSubtitle(): String {
                return "Some Artist Name"
            }

            override fun getTrititle(): String? {
                return "Generic"
            }

            override fun getArtwork(): ImageSource {
                return source
            }

        }

    }
}