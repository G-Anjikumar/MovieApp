package com.llyod.sample.ui.home.presentation

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
import com.llyod.sample.ui.home.presentation.components.HorizontalCastItem
import com.llyod.sample.ui.home.viewmodel.ShowListState

@Composable
fun CastScreen(
    showListState: ShowListState,
) {

    if (showListState.isLoading) {
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
            items(showListState.caseList.size) { index ->
                HorizontalCastItem(
                    showListState.caseList[index].person.name,
                    showListState.caseList[index].person.country.name,
                    showListState.caseList[index].person.image.medium,
                    showListState.caseList[index].person.birthday,
                )
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }

}