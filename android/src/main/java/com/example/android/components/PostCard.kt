package com.example.android.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.android.util.ImageDecoder.decodeThumbnailImage
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.utils.DateUtil.toReadableDate

@Composable
fun PostCard(post: PostLight, onClick: (PostLight) -> Unit) {
    val context = LocalContext.current
    val imageRequest = remember {
        ImageRequest.Builder(context)
            .data(post.thumbnail
                .takeIf { it.contains("http") } ?: post.thumbnail.decodeThumbnailImage())
            .build()
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick(post)
            }, tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                model = imageRequest,
                contentScale = ContentScale.Crop,
                contentDescription = "thumbnail"
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = post.date.toReadableDate(), modifier = Modifier
                        .padding(bottom = 6.dp)
                        .alpha(0.5f),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = post.title, modifier = Modifier
                        .padding(bottom = 6.dp)
                        .alpha(0.5f),
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
                Text(
                    text = post.title, modifier = Modifier
                        .padding(bottom = 6.dp)
                        .alpha(0.5f),
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal,
                    maxLines = 3
                )
                SuggestionChip(onClick = { /*TODO*/ }, label = {
                    Text(text = post.category)
                })
            }
        }
    }
}