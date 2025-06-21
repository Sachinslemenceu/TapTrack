package com.slemenceu.taptrack.mousepad.ui.pc_guide_screen

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.mousepad.ui.pc_guide_screen.composables.TopAppBar

@Composable
fun PcGuideScreen(
    onBackClicked: () -> Unit
) {

    val context = LocalContext.current
    val downloadUri = stringResource(R.string.download_uri)
    Scaffold(
        containerColor = Color.White,
        topBar = { TopAppBar(
            title = "Help",
            onBackClicked = {
                onBackClicked()
            }
        ) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .verticalScroll(
                    state = rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            TitleHelp("How to Connect to PC?")
            Spacer(Modifier.height(20.dp))
            DetailsComponent(
                title = "Step 1",
                description = "Make sure both pc and mobile are connected to same wifi network.",
                image = R.drawable.same_network_img

            )
            DetailsComponent(
                title = "Step 2",
                description = "Download the TapTrack PC app via the link given below.",
                image = R.drawable.pc_connection_img

            )
            Text(
                text = "Download here",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF445EEE),
                modifier = Modifier.clickable(
                    onClick = {
                        val intent = Intent(ACTION_VIEW, downloadUri.toUri())
                        context.startActivity(intent)
                    }
                )
            )
            DetailsComponent(
                title = "Step 3",
                description = "Open the TapTrack Pc app and scan the Qr code via mobile app to connect.",
                image = R.drawable.qr_scanning_img

            )

        }
    }
}


@Composable
private fun DetailsComponent(
    title: String,
    description: String,
    @DrawableRes image: Int,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        TitleHelp(title, modifier = Modifier.align(Alignment.Start))
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp *0.5f)
        )
        Spacer(Modifier.height(10.dp))
        DescriptionHelp(text = description)
        Spacer(Modifier.height(10.dp))

    }
}

@Composable
private fun TitleHelp(
    text: String,
    modifier: Modifier = Modifier,
    ) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.SansSerif,
        modifier = modifier
    )
}
@Composable
private fun DescriptionHelp(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        fontFamily = FontFamily.SansSerif,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}


@Preview
@Composable
private fun PcGuideScreenPreview() {
    PcGuideScreen(
        onBackClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailComponentPreview() {
    DetailsComponent(
        title = "Title",
        description = "Make sure both pc and mobile are connected to same wifi network.",
        image = R.drawable.qr_scanning_img
    )
}