package com.example.recruiterapp.common



import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordInputField(
    password: String,
    onPasswordChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        placeholder = { Text("Enter your password") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Default.Visibility
            } else {
                Icons.Default.VisibilityOff
            }

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = "Toggle password visibility")
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun UserEmailInput(text:String) {
    OutlinedTextField(
        value = text,
        onValueChange = {},
        label = { Text("Enter your email") }
    )
}

@Composable
fun UserNameInput(text:String){
    OutlinedTextField(
        value = text,
        onValueChange = {},
        label = {Text("Enter your display name")}
    )
}


@Composable
fun CaptureOrSelectPhoto() {
    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher to capture a photo
    val captureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            savePhotoToLocalStorage(context, bitmap)
        } else {
            Toast.makeText(context, "Photo capture failed", Toast.LENGTH_SHORT).show()
        }
    }

    // Launcher to pick a photo from the gallery
    val pickLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            photoUri = uri
            savePhotoFromUriToLocalStorage(context, uri)
        } else {
            Toast.makeText(context, "No photo selected", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.height(250.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Button(onClick = { captureLauncher.launch(null) }) {
            Text("Capture Photo")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { pickLauncher.launch("image/*") }) {
            Text("Select Photo from Gallery")
        }
    }
}

fun savePhotoToLocalStorage(context: Context, bitmap: android.graphics.Bitmap) {
    val filename = "IMG_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.jpg"
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    val contentResolver = context.contentResolver
    var outputStream: OutputStream? = null
    var uri: Uri? = null

    try {
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (imageUri != null) {
            uri = imageUri
            outputStream = contentResolver.openOutputStream(imageUri)
            if (outputStream != null) {
                bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            Toast.makeText(context, "Photo saved successfully!", Toast.LENGTH_SHORT).show()
        } else {
            throw IOException("Failed to create new MediaStore record.")
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error saving photo: ${e.message}", Toast.LENGTH_LONG).show()
        uri?.let { contentResolver.delete(it, null, null) } // Clean up partially created file
    } finally {
        outputStream?.close()
    }
}

fun savePhotoFromUriToLocalStorage(context: Context, uri: Uri) {
    val contentResolver = context.contentResolver
    val filename = "IMG_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.jpg"
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    var outputStream: OutputStream? = null
    var newUri: Uri? = null

    try {
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (imageUri != null) {
            newUri = imageUri
            outputStream = contentResolver.openOutputStream(imageUri)
            val inputStream = contentResolver.openInputStream(uri)

            if (inputStream != null && outputStream != null) {
                inputStream.copyTo(outputStream)
                Toast.makeText(context, "Photo saved successfully!", Toast.LENGTH_SHORT).show()
            } else {
                throw IOException("Failed to copy photo to storage.")
            }
        } else {
            throw IOException("Failed to create new MediaStore record.")
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error saving photo: ${e.message}", Toast.LENGTH_LONG).show()
        newUri?.let { contentResolver.delete(it, null, null) } // Clean up partially created file
    } finally {
        outputStream?.close()
    }
}
