package com.lloyds.features.cast.components

import android.text.TextUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.lloyds.features.R

@Composable
fun HorizontalCastItem(
    name: String,
    countryName: String,
    imageUrl: String,
    birthDay: String,
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    ).state
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {})
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .height(150.dp)
                    .align(CenterVertically)
                    .fillMaxWidth(0.3f),
                shape = RoundedCornerShape(0.dp),
            ) {
                Column(
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (imageState is AsyncImagePainter.State.Error) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                                .height(250.dp)
                                .clip(RoundedCornerShape(22.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .testTag(countryName),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(70.dp),
                                imageVector = Icons.Rounded.ImageNotSupported,
                                contentDescription = countryName
                            )
                        }
                    }

                    if (imageState is AsyncImagePainter.State.Success) {

                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                                .height(250.dp)
                                .clip(RoundedCornerShape(22.dp))
                                .testTag(countryName),
                            painter = imageState.painter,
                            contentDescription = name,
                            contentScale = ContentScale.Crop
                        )
                    }

                }

            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .testTag(name)
            ) {
                Text(
                    text = name,
                    maxLines = 1,
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold, FontWeight.Bold)),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = countryName,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Black))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (!TextUtils.isEmpty(birthDay)) birthDay else "NA",
                    modifier = Modifier.testTag(if (!TextUtils.isEmpty(birthDay)) birthDay else "NA"),
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Black))
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}