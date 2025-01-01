package com.lloyds.features.shows

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.lloyds.features.R
import com.lloyds.features.utils.Screen
import com.llyod.remote.data.model.Shows

@Composable
fun ShowItem(
    shows: Shows,
    navHostController: NavHostController
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(shows.image?.medium)
            .size(Size.ORIGINAL)
            .build()
    ).state
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(250.dp)
            .testTag(shows.name!!)
    ) {
        Column(
            modifier = Modifier
                .testTag("Clickable_Show")
                .background(Color.White)
                .clickable {
                    navHostController.navigate(Screen.Details.route + "/${shows.id}")
                },
        ) {
            if (imageState is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(150.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        )
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .testTag(shows.name!!),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(70.dp),
                        imageVector = Icons.Rounded.ImageNotSupported,
                        contentDescription = shows.name
                    )
                }
            }

            if (imageState is AsyncImagePainter.State.Success) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            )
                        )
                        .testTag(shows.name!!),
                    painter = imageState.painter,
                    contentDescription = shows.name,
                    contentScale = ContentScale.FillBounds,
                )
            }

            Spacer(modifier = Modifier.height(6.dp))


            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .testTag("Test Show"),
                text = shows.name!!,
                color = Color.Black,
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.poppins_bold, FontWeight.Bold)),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .testTag(shows.premiered!!),
                text = shows.premiered!!,
                color = Color.Black,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Normal)),
                maxLines = 1,
            )

        }
    }
}