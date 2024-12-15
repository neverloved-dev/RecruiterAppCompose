package com.example.recruiterapp.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recruiterapp.common.CaptureOrSelectPhoto
import com.example.recruiterapp.common.PasswordInputField
import com.example.recruiterapp.common.UserEmailInput
import com.example.recruiterapp.common.UserNameInput
import com.example.recruiterapp.ui.theme.RecruiterAppTheme

class RegisterScreen : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecruiterAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    RegisterElementGroup()
                }
            }
        }
    }
    @Composable
    fun RegisterElementGroup(){
        val context = LocalContext.current
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp) ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally ) {
                Card(modifier = Modifier.padding(16.dp)){
                    Column(modifier = Modifier.padding(25.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally ){
                        UserEmailInput("")
                        PasswordInputField(""){}
                        PasswordInputField(""){}
                        UserNameInput("")
                        CaptureOrSelectPhoto()
                    }
                }
                Button(onClick = { moveToSearchScreen(context) }){
                    Text("Register")
                }
            }
        }
    }

    private fun moveToSearchScreen(context: Context){
        var intent = Intent(context, SearchScreen::class.java)
        context.startActivity(intent)
    }

    @Preview
    @Composable
    fun PreviewRegisterElementGroup(){
        RegisterElementGroup()
    }
}