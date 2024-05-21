package com.example.pawrents.screens.sign_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pawrents.R
import com.example.pawrents.common.composable.BasicButton
import com.example.pawrents.common.composable.BasicToolbar
import com.example.pawrents.common.composable.EmailField
import com.example.pawrents.common.composable.PasswordField
import com.example.pawrents.common.composable.RepeatPasswordField
import com.example.pawrents.ui.theme.PawrentsTheme

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    openScreen: (String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) },
        onLoginClick = { viewModel.onLoginClick(openScreen) }
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val fieldModifier = Modifier
        .fillMaxWidth()
        .padding(16.dp, 4.dp)

    BasicToolbar(R.string.create_account)

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        EmailField(
            uiState.email,
            onEmailChange,
            fieldModifier
        )
        PasswordField(
            uiState.password,
            onPasswordChange,
            fieldModifier
        )
        RepeatPasswordField(
            uiState.repeatPassword,
            onRepeatPasswordChange,
            fieldModifier
        )

        BasicButton(
            text = R.string.create_account,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        ) {
            onSignUpClick()
        }

        Spacer(modifier = Modifier.padding(12.dp))
        Row {
            Text(
                text = "Already have an account? ",
            )
            Text(
                text = "Log in",
                modifier = Modifier.clickable(
                    onClick = { onLoginClick() }
                ),
                color = MaterialTheme.colors.primary
            )
        }
        Spacer(modifier = Modifier.padding(12.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val uiState = SignUpUiState(
        email = "email@test.com"
    )
     SignUpScreenContent(
            uiState = uiState,
            onEmailChange = { },
            onPasswordChange = { },
            onRepeatPasswordChange = { },
            onSignUpClick = { },
            onLoginClick = { }
        )
    }


