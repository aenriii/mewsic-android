package cat.jai.mewsic.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import cat.jai.mewsic.data.supporting.ImageSource
import java.lang.Error

@Composable
fun <T : ImageSource> ImageSourceImage(
    modifier: Modifier = Modifier,
    image: T
) {
    when (image) {
        is ImageSource.UrlImageSource -> {
            val url = (image as ImageSource.UrlImageSource).url
            Box(modifier) {
                NetworkImage(
                    modifier = Modifier.aspectRatio(1f), // ratio
                    url = url,
                    contentScale = ContentScale.Crop,
                    contentDescription = "meow"
                )
            }
        }

        is ImageSource.DataStreamImageSource -> {
            // TODO: implement DataStreamImageSource
            throw Error("Unimplemented: image is ImageSource.DataStreamImageSource")
        }

        is ImageSource.ResourceImageSource -> {
            // TODO: implement ResourceImageSource
            throw Error("Unimplemented: image is ImageSource.ResourceImageSource")
        }

        is ImageSource.VectorImageSource -> {
            // image is an ImageVector
            val vector = (image as ImageSource.VectorImageSource).vector
            Box(modifier) {
                Icon(
                    modifier = Modifier.aspectRatio(1f), // ratio
                    imageVector = vector,
                    contentDescription = "meow"
                )
            }
        }

        else -> {
            throw Error("Unimplemented: image is ImageSource, image !is ImageSource.*")
        }
    }
}