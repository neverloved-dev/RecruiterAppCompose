package com.example.recruiterapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recruiterapp.common.PasswordInputField
import com.example.recruiterapp.common.UserEmailInput
import com.example.recruiterapp.screens.RegisterScreen
import com.example.recruiterapp.ui.theme.RecruiterAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecruiterAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    LoginScreenInputCard()
                }
            }
        }
    }

    @Composable
    fun LoginScreenInputCard(){
        val context = LocalContext.current
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Card(modifier = Modifier.padding(16.dp)) {
                    Column(modifier = Modifier.padding(25.dp)) {
                        UserEmailInput("")
                        PasswordInputField("") {}
                    }
                }
                Button(onClick = {/*TODO: make the search screen*/}) {
                    Text("Log In")
                }
                Button(onClick = { moveToRegisterScreen(context) }){
                    Text("Sign Up")
                }
            }
        }
        }


    @Preview
    @Composable
    fun LoginScreenInputCardPreview(){
        LoginScreenInputCard()
    }

    private  fun moveToRegisterScreen(context:Context){
        var intent = Intent(context, RegisterScreen::class.java)
        context.startActivity(intent)
    }

}


