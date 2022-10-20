package link.limecode.mywallet.presentation.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import link.limecode.mywallet.app.di.AppContainer
import link.limecode.mywallet.app.theme.LocalCustomColors
import link.limecode.mywallet.app.theme.MyWalletTheme

@OptIn(ExperimentalAnimationApi::class)
@Suppress("FunctionName")
@Composable
fun MyWalletApp(
    appContainer: AppContainer,
    @Suppress("UnusedPrivateMember", "unused_parameter") viewModel: ViewModel
) {
    MyWalletTheme {

        val systemUiController = rememberSystemUiController()
        val statusBarColor = if (!isSystemInDarkTheme()) {
            MaterialTheme.colors.primary
        } else {
            LocalCustomColors.current.surfaceElevated
        }

        DisposableEffect(systemUiController, statusBarColor) {
            systemUiController.setSystemBarsColor(
                color = statusBarColor,
                darkIcons = false
            )
            onDispose {}
        }

        val navController = rememberAnimatedNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            MyWalletAppNavigation(
                modifier = Modifier.padding(padding),
                appContainer = appContainer,
                navController = navController
            )
        }
    }
}
