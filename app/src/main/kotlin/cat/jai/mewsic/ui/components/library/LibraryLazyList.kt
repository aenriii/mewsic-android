package cat.jai.mewsic.ui.components.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.jai.mewsic.data.generator.Generator
import cat.jai.mewsic.data.generator.toIterator
import cat.jai.mewsic.data.standards.MediaMetadata
import cat.jai.mewsic.data.supporting.ImageSource
import cat.jai.mewsic.ui.components.ImageSourceImage

@Composable
fun LibraryLazyList(items: List<MediaMetadata>, icon: ImageSource, title: String, more: (() -> Unit)? = null) {
    var state = rememberLazyListState();
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column() {
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                ImageSourceImage(modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically), image = icon)
                Text(
                    text = title.toUpperCase(Locale.current),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .height(24.dp)
                        .align(Alignment.CenterVertically)
                )
                if (more != null) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = " MORE ",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier
                            .height(24.dp)
                            .align(Alignment.CenterVertically)
                            .clickable { more() }
                            .padding(1.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))


                }
            }
            LazyRow(
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 0.dp,
                    top = 8.dp,
                    bottom = 8.dp
                ),
                state = state,
            ) {
                items(items) {
                        LibraryEntry(
                            modifier = Modifier
                                .width(125.dp)
                                .height(225.dp),
                            image = it.getArtwork(),
                            title = it.getTitle(),
                            subtitle = it.getSubtitle(),
                            trititle = it.getTrititle(),
                            landscape = false,
                            onClick = {},
                            onLongClick = {}
                        )
                    Spacer(modifier = Modifier.width(8.dp))

                }
            }
        }
    }
}