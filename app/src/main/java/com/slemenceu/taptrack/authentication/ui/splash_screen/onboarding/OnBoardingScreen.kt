package com.slemenceu.taptrack.authentication.ui.splash_screen.onboarding


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.authentication.ui.splash_screen.composable.MyButton
import kotlinx.coroutines.delay

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onGetStartedClicked: () -> Unit
) {

    val animations = listOf(
        R.raw.annoy_anim,
        R.raw.control_anim,
        R.raw.smooth_anim,
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
        pageCount = {animations.size},
        initialPage = 0
    )

    LaunchedEffect(Unit) {

        while (pageState.currentPage < pageState.pageCount - 1) {
            delay(3000)
            val currentPage = pageState.currentPage
            pageState.animateScrollToPage(
                page = currentPage+ 1,
                pageOffsetFraction = 0f
            )
        }

    }
    Box(
        modifier = modifier.fillMaxSize()
    ){
        HorizontalPager(state = pageState) {
            PageScreen(
                animation = animations[it],
                title = stringResource(id = titles[it]),
                description = stringResource(id = descriptions[it]),
            )
        }
        AnimatedVisibility(
            visible = pageState.currentPage == 2,
            enter = scaleIn() ,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp, start = 60.dp, end = 60.dp)

        ) {
            MyButton(
                text = "Get Started",
            ) {
                onGetStartedClicked()
            }
        }

        HorizontalPagerIndicator(
            pageCount = 3,
            currentPage = pageState.currentPage,
            targetPage = pageState.currentPage +1,
            currentPageOffsetFraction = 0f,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 40.dp),
        )
    }
}