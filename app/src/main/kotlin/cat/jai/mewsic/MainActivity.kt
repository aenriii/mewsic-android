package cat.jai.mewsic

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material.icons.filled.YoutubeSearchedFor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.jai.mewsic.data.generator.toIterator
import cat.jai.mewsic.data.standards.testData_MediaMetadata
import cat.jai.mewsic.data.supporting.ImageSource
import cat.jai.mewsic.ui.components.library.LibraryEntry
import cat.jai.mewsic.ui.components.library.LibraryLazyList
import cat.jai.mewsic.ui.theme.MewsicΒTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MewsicΒTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // center an element
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
//                        LibraryEntry(
//                            modifier = Modifier.width(125.dp).height(225.dp),
//                            image = ImageSource.UrlImageSource("https://img.youtube.com/vi/H7ftUCwjFNg/hqdefault.jpg"),
//                            title = "Charming Chaos Compendium",
//                            subtitle = "Chonny Jash",
//                            trititle = "Album",
//                        )
                        val items = testData_MediaMetadata(20).toIterator().asSequence().toList()
                        LibraryLazyList(
                            items = items,
                            icon = ImageSource.VectorImageSource(Icons.Filled.VideoFile),
                            title = "Test"
                        ) {}
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MewsicΒTheme {
        Greeting("Android")
    }
}