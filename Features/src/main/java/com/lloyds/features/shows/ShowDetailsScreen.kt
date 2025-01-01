package com.lloyds.features.shows

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.lloyds.features.R
import com.lloyds.features.cast.components.RatingBar
import com.lloyds.features.utils.Utils

@Preview
@Composable
fun DetailsScreen() {

    val detailsViewModel = hiltViewModel<DetailViewModel>()
    val detailsState = detailsViewModel.detailsState.collectAsState().value

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(detailsState.shows?.image?.medium)
            .size(Size.ORIGINAL).build()
    ).state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.Black)
            .padding(10.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .fillMaxSize()
                    .fillMaxWidth()
                    .height(240.dp),
                contentAlignment = Alignment.Center
            ) {
                if (posterImageState is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = detailsState.shows?.name
                        )
                    }
                }

                if (posterImageState is AsyncImagePainter.State.Success) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        painter = posterImageState.painter,
                        contentDescription = detailsState.shows?.name,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            detailsState.shows?.let { shows ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = shows.name!!,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        RatingBar(
                            starsModifier = Modifier.size(18.dp),
                            rating = shows.rating?.average!! / 2
                        )

                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = shows.rating.toString().take(3),
                            color = Color.White,
                            fontSize = 14.sp,
                            maxLines = 1,
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(R.string.language) + shows.language,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(R.string.genre),
                        color = Color.White
                    )
                    repeat(shows.genres!!.size) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxHeight()
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth()
                                .padding(5.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text(
                                    modifier = Modifier.padding(start = 4.dp),
                                    text = shows.genres!![it],
                                    color = Color.Black,
                                    fontFamily = FontFamily(
                                        Font(
                                            R.font.poppins_bold,
                                            FontWeight.Bold
                                        )
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.displayMedium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(R.string.release_by) + shows.premiered,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Overview",
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        detailsState.shows?.let {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = Utils.removeHtmlTags(it.summary!!),
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
    }

}