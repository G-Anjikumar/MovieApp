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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lloyds.features.cast.components.HorizontalCastItem
import com.llyod.remote.utils.UiState

@Composable
fun CastScreen(
    uiState: UiState,
) {

    if (uiState.isLoading) {
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
            items(uiState.caseList.size) { index ->
                HorizontalCastItem(
                    uiState.caseList[index].person.name ?: "",
                    uiState.caseList[index].person.country.name ?: "",
                    uiState.caseList[index].person.image.medium ?: "",
                    uiState.caseList[index].person.birthday ?: ""
                )
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }

}