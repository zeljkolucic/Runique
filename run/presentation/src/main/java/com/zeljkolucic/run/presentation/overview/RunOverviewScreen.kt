package com.zeljkolucic.run.presentation.overview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zeljkolucic.core.presentation.designsystem.AnalyticsIcon
import com.zeljkolucic.core.presentation.designsystem.LogoIcon
import com.zeljkolucic.core.presentation.designsystem.LogoutIcon
import com.zeljkolucic.core.presentation.designsystem.R
import com.zeljkolucic.core.presentation.designsystem.RunIcon
import com.zeljkolucic.core.presentation.designsystem.RuniqueTheme
import com.zeljkolucic.core.presentation.designsystem.components.RuniqueFloatingActionButton
import com.zeljkolucic.core.presentation.designsystem.components.RuniqueScaffold
import com.zeljkolucic.core.presentation.designsystem.components.RuniqueToolbar
import com.zeljkolucic.core.presentation.designsystem.components.util.DropDownItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreen(
    viewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverviewScreenContent(
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunOverviewScreenContent(
    onAction: (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    RuniqueScaffold(
        topAppBar = {
            RuniqueToolbar(
                showBackButton = false,
                title = stringResource(id = R.string.runique),
                scrollBehavior = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(id = R.string.logout)
                    )
                ),
                onMenuItemClick = { index ->
                    when(index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            RuniqueFloatingActionButton(
                icon = RunIcon,
                onClick = { onAction(RunOverviewAction.OnStartClick) }
            )
        }
    ) { padding ->

    }
}

@Preview
@Composable
private fun RunOverviewScreenPreview() {
    RuniqueTheme {
        RunOverviewScreenContent(
            onAction = {}
        )
    }
}