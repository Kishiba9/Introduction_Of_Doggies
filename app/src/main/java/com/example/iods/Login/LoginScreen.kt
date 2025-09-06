package com.example.iods.Login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iods.R
import com.example.iods.WoofTopAppBar
import com.example.iods.ui.navigation.NavigationDestination
import com.example.iods.ui.theme.WoofTheme

object LoginDestination : NavigationDestination {
    override val route = "login"

    override val titleRes = R.string.login_title
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel(),
    onClickToHomeScreen: () -> Unit,
) {

    val uiState by loginViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState.isLoggedIn) {
        // uiState.isLoggedIn が true になったら画面遷移をトリガー
        if(uiState.isLoggedIn) {
            onClickToHomeScreen()
        }
    }

    Scaffold(
        topBar = {
            WoofTopAppBar(
                title = stringResource(LoginDestination.titleRes),
                canNavigateBack = false,
                canLogout = false
            )
        }
    ) { innerPadding ->
        LoginBody(
            modifier = modifier,
            loginUiState = uiState,
            onUpdateLoginId = loginViewModel::updateLoginId,
            onUpdatePassword = loginViewModel::updatePassword,
            onLogin = loginViewModel::login,
            contentPadding = innerPadding
        )
    }
}

@Composable
fun LoginBody(
    modifier: Modifier = Modifier,
    loginUiState: LoginUiState,
    onUpdateLoginId: (String) -> Unit,
    onUpdatePassword: (String) -> Unit,
    onLogin: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            //この場合、fillMaxSize() は、modifier が持つパディング情報に関わらず、そのコンポーザブルが配置される親の利用可能な最大スペースを埋めようとします。
            .padding(contentPadding
//                top = dimensionResource(R.dimen.padding_large),
//                bottom = dimensionResource(R.dimen.padding_large)
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.login_button),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_small)
                )
        )

        TextField(
            value = loginUiState.loginId,
            onValueChange = { newLoginId ->
                onUpdateLoginId(newLoginId)
            },
            label = {Text(stringResource(R.string.login_button))},
            isError = loginUiState.loginIdError, // エラー表示
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                )
        )

        Text(
            text = if (loginUiState.loginIdError) stringResource(R.string.login_id_error_message) else "",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                .heightIn(min = 20.dp)
        )

        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))

        Text(
            text = stringResource(R.string.password_label),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_small)
                )
        )

        TextField(
            value = loginUiState.password,
            onValueChange = { newPassWord ->
                onUpdatePassword(newPassWord)
            },
            label = {Text(stringResource(R.string.password_label))},
            isError = loginUiState.passwordError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                )
        )

        Text(
            if (loginUiState.passwordError) stringResource(R.string.password_hint) else "",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                .heightIn(min = 20.dp)
        )

        // 現在持っているエラーメッセージを表示するため、それがわかるようにスペースを空ける、囲むなど工夫が必要
        // 既存のオブジェクトに対して、条件付きで何らかの処理を実行したいときに使う。オブジェクト自体は変更しない。主にnull安全な処理で威力を発揮。
        loginUiState.errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .heightIn(min = 20.dp)
            )
        } ?: run {
            Spacer(modifier = Modifier.heightIn(min = 50.dp))
        }

        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)))

        Button(
            onClick = onLogin,
            enabled = !loginUiState.isLoading,
            shape = RoundedCornerShape(percent = 50),
            modifier = Modifier.size(width = 150.dp, height = 50.dp)
        ) {
            if(loginUiState.isLoading) {
                Text("Loading...")
            } else {
                Text(
                    text = stringResource(R.string.login_title)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    WoofTheme(darkTheme = false) {
        LoginScreen(onClickToHomeScreen = {})
    }
}

@Preview
@Composable
fun WoofDarkThemePreviewLoginScreen() {
    WoofTheme(darkTheme = true) {
    LoginScreen(onClickToHomeScreen = {})
    }
}