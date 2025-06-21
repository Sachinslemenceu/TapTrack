package com.slemenceu.taptrack.mousepad.ui.pc_guide_screen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    onBackClicked: () -> Unit
){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
        },
        navigationIcon = {
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = CardDefaults.outlinedCardBorder(
                    enabled = true
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clickable {
                        onBackClicked()
                    }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.padding(10.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            navigationIconContentColor = Color.Black,
            titleContentColor = Color.Black
        )
    )
}
