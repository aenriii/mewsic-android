package cat.jai.mewsic.ui.components.library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import cat.jai.mewsic.data.supporting.ImageSource
import java.lang.Error
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.jai.mewsic.ui.components.ImageSourceImage
import cat.jai.mewsic.ui.components.NetworkImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryEntry(
    modifier: Modifier = Modifier,
    image: ImageSource,
    title: String,
    subtitle: String,
    trititle: String? = null,
    landscape: Boolean = false,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {

    if (landscape) {
        // TODO: implement landscape
    } else {
        Box(modifier = modifier) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CardDefaults.elevatedShape)
                    .combinedClickable(
                        onClick = onClick,
                        onLongClick = onLongClick
                    ),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column {
                    ImageSourceImage(
                        modifier = Modifier.aspectRatio(1f).clip(
                            RoundedCornerShape(8.dp)
                        ), // ratio
                        image = image
                    )
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = title,
                            fontSize = 14.sp
                        )
                        if (trititle != null) {
                            Spacer(modifier = Modifier.weight(0.2f))
                        }
                        Text(
                            text = subtitle,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                        if (trititle != null) {
                            // space to make it appear at the bottom corner
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = trititle,
                                fontSize = 10.sp,
                                fontStyle = FontStyle.Italic,
                            )
                        }
                    }
                }
            }
        }
    }
    
}