package com.slemenceu.taptrack.core.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey400
import com.slemenceu.taptrack.ui.theme.lightGrey800

@Composable
fun AppTopBar(
    onBackClicked:() -> Unit,
    title: AnnotatedString =buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White)) {
            append("Tap")
        }
        withStyle(style = SpanStyle(color = green500)) {
            append("Track")
        }
    },
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = { }
) {
    Box(
        modifier = modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Surface(
                onClick = onBackClicked,
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp,darkBlue800),
                modifier = Modifier.size(48.dp),
                color = lightGrey800
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = lightGrey400,
                    modifier = Modifier
                        .padding(14.dp)
                )
            }

            Spacer(Modifier.weight(1f))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.weight(1f))
        }
        content()
    }
}


@Preview
@Composable
private fun AppTopBarPreview() {
    AppTopBar(
        onBackClicked = { },
        title = buildAnnotatedString {
            append("My Profile")
        }
    )
}