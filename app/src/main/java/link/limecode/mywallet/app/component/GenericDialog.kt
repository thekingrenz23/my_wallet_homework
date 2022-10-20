package link.limecode.mywallet.app.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

private const val TRANSITION_INITIALIZE = 0
private const val TRANSITION_ENTER = 1
private const val TRANSITION_EXIT = 2
private const val TRANSITION_GONE = 3

@Suppress("LongParameterList", "LongMethod", "FunctionNaming")
@Composable
fun GenericDialog(
    visible: Boolean,
    icon: ImageVector,
    title: String,
    body: String,
    onDismiss: () -> Unit,
    actionLabel: String
) {
    val density = LocalDensity.current
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp
    var transitionState by remember { mutableStateOf(TRANSITION_GONE) }

    LaunchedEffect(visible) {
        if (visible) {
            when (transitionState) {
                TRANSITION_EXIT -> transitionState = TRANSITION_ENTER
                TRANSITION_GONE -> transitionState = TRANSITION_INITIALIZE
                TRANSITION_INITIALIZE -> transitionState = TRANSITION_ENTER
            }
        } else {
            when (transitionState) {
                TRANSITION_INITIALIZE -> transitionState = TRANSITION_GONE
                TRANSITION_ENTER -> transitionState = TRANSITION_EXIT
            }
        }
    }

    if (transitionState != TRANSITION_GONE) {
        Dialog(onDismissRequest = {}) {

            if (transitionState == TRANSITION_INITIALIZE) {
                GenericDialogLayout(
                    icon = icon,
                    title = title,
                    body = body,
                    onDismiss = onDismiss,
                    actionLabel = actionLabel,
                    modifier = Modifier.alpha(0f)
                        .onGloballyPositioned {
                        transitionState = TRANSITION_ENTER
                    }
                )
            }

            AnimatedVisibility(
                visible = transitionState == TRANSITION_ENTER,
                enter = slideInVertically(
                    initialOffsetY = {
                        with(density) { screenHeight.roundToPx() }
                    }
                ),
                exit = slideOutVertically(
                    targetOffsetY = {
                        with(density) { screenHeight.roundToPx() }
                    }
                )
            ) {
                remember {
                    object : RememberObserver {
                        override fun onAbandoned() {
                            transitionState = TRANSITION_GONE
                        }

                        override fun onForgotten() {
                            transitionState = TRANSITION_GONE
                        }

                        @Suppress("EmptyFunctionBlock")
                        override fun onRemembered() {
                        }
                    }
                }

                GenericDialogLayout(
                    icon = icon,
                    title = title,
                    body = body,
                    onDismiss = onDismiss,
                    actionLabel = actionLabel
                )
            }
        }
    }
}

@Suppress("FunctionNaming", "LongParameterList", "LongMethod")
@Composable
fun GenericDialogLayout(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    body: String,
    onDismiss: () -> Unit,
    actionLabel: String
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp,
        modifier = modifier.padding(
            start = 10.dp,
            top = 5.dp,
            end = 10.dp
        ),
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp)
                )
            }

            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.subtitle1
                )

                Text(
                    text = body,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body1
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .clickable(
                                onClick = onDismiss
                            ),
                        color = MaterialTheme.colors.secondary,
                        contentColor = MaterialTheme.colors.onSecondary
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = actionLabel)
                        }
                    }
                }
            }
        }
    }
}
