package com.slemenceu.taptrack.features.authentication.ui.splash_screen.onboarding


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.core.composables.MyPrimaryButton
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey300
import com.slemenceu.taptrack.ui.theme.lightGrey400
import kotlinx.coroutines.delay

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onGetStartedClicked: () -> Unit,
    onSignInClicked: () -> Unit,
) {

    val animationIcons = listOf(
        R.drawable.onboarding_anim_icon_1,
        R.drawable.onboarding_anim_icon_2,
        R.drawable.onboarding_anim_icon_3,
    )
    val descriptionIcons = listOf(
        R.drawable.onboarding_icon_1,
        R.drawable.onboarding_icon_2,
        R.drawable.onboarding_icon_3,
    )
    val descriptions = listOf(
        R.string.annoy_desc,
        R.string.control_desc,
        R.string.smooth_desc,
    )
    val titles = listOf(
        R.string.annoy_title,
        R.string.control_title,
        R.string.smooth_title,
    )
    val pageState = rememberPagerState(
        pageCount = { animationIcons.size },
        initialPage = 0
    )
    val signInText =buildAnnotatedString {
        append("Already have an account? ")

        withLink(
            LinkAnnotation.Clickable(
                tag = "terms",
                styles = TextLinkStyles(
                    style = SpanStyle(color = green500, fontWeight = FontWeight.Bold)
                ),
                linkInteractionListener = { onSignInClicked() }
            )
        ) {
            append("Sign In")
        }

    }

    LaunchedEffect(Unit) {

        while (pageState.currentPage < pageState.pageCount - 1) {
            delay(3000)
            val currentPage = pageState.currentPage
            pageState.animateScrollToPage(
                page = currentPage + 1,
                pageOffsetFraction = 0f
            )
        }

    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.tap_track_icon),
                contentDescription = "App Icon",
                tint = Color.Unspecified,
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.White)) {
                        append("Turn your phone into ")
                    }
                    withStyle(style = SpanStyle(color = green500)) {
                        append("a PC mouse")
                    }
                },
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Wirelessly control your computer with intuitive touch gestures.",
                fontSize = 13.sp,
                color = lightGrey400,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.weight(0.5f))

        HorizontalPager(state = pageState) {
            PageScreen(
                animationIcon = animationIcons[it],
                title = stringResource(id = titles[it]),
                description = stringResource(id = descriptions[it]),
                descriptionIcon = descriptionIcons[it]
            )
        }
        Spacer(Modifier.weight(0.5f))
        HorizontalPagerIndicator(
            pageCount = 3,
            currentPage = pageState.currentPage,
            targetPage = pageState.currentPage + 1,
            currentPageOffsetFraction = 0f,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))
        MyPrimaryButton(
            text = "Get Started",
        ) {
            onGetStartedClicked()
        }
        Spacer(Modifier.height(20.dp))
        Text(
            text = signInText,
            style = MaterialTheme.typography.bodySmall.copy(color = lightGrey300)
        )

        Spacer(Modifier.weight(0.5f))


    }
}


@Preview
@Composable
private fun OnboardingScreenPreview() {
    Scaffold(
        containerColor = darkBlue900
    ) {
        OnBoardingScreen(
            onGetStartedClicked = {},
            onSignInClicked = {},
            modifier = Modifier.padding(it)
        )
    }
}