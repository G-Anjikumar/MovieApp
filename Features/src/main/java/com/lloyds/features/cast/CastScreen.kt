package com.lloyds.features.cast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lloyds.features.cast.components.HorizontalCastItem

@Composable
fun CastScreen() {
    val castViewModel: CastViewModel = hiltViewModel()
    val castState = castViewModel.castListState.collectAsState().value
    if (castState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
        ) {
            items(castState.caseList.size) { index ->
                HorizontalCastItem(
                    castState.caseList[index].person.name?: "",
                    castState.caseList[index].person.country.name?: "",
                    castState.caseList[index].person.image.medium?: "",
                    castState.caseList[index].person.birthday?: "",
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}