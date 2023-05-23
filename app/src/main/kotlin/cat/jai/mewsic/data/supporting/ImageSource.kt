package cat.jai.mewsic.data.supporting

import android.graphics.drawable.VectorDrawable
import androidx.compose.ui.graphics.vector.ImageVector
import java.io.InputStream

open class ImageSource {
    class UrlImageSource(public val url: String) : ImageSource() {
        
    }
    class DataStreamImageSource(public val dataStream: InputStream) : ImageSource() {
        
    }
    class ResourceImageSource(public val resourceId: Int) : ImageSource() {
        
    }
    class VectorImageSource(public val vector: ImageVector) : ImageSource() {

    }
    
}