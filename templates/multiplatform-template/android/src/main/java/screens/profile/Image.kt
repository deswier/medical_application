package screens.profile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.core.graphics.drawable.toBitmap
import com.myapplication.model.Profile

@Composable
fun imagePicker(profile: Profile, edit: Boolean) {
    var imageUrl by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(profile.photo) }
    val flagPhoto = remember { mutableStateOf(profile.photo != null) }
    val imageSize = 100.dp
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight().fillMaxSize().padding(0.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        imageUrl?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
                profile.setPhoto(bitmap.value)
                flagPhoto.value = true
            }
        }
        if (flagPhoto.value) { //if  photo
            if (flagPhoto.value) {
                bitmap.value?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Gallery Image",
                        modifier = Modifier.size(imageSize)
                            .clip(CircleShape)
                            .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
                    )
                }
                profile.setPhoto(bitmap.value)
            }
            if (edit) {
                Button(
                    onClick = {
                        imageUrl = null
                        bitmap.value = null
                        flagPhoto.value = false
                        profile.setPhoto(null)
                    }
                ) {
                    Text(
                        text = "Delete photo",
                        color = Color.White,
                        fontSize = 10.sp,
                    )
                }
            }
        } else {

            val resId = context.resources.getIdentifier("no_photo", "drawable", context.packageName)
            val img = context.getDrawable(resId)
            if (img != null) {
                Image(
                    bitmap = img.toBitmap(100, 100).asImageBitmap(),
                    contentDescription = "Gallery Image",
                    modifier = Modifier.size(100.dp)
                        .clip(CircleShape)
                        .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
                    //  .clickable(onClick = { count.value += 1 })

                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight().fillMaxSize().padding(0.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextButton(
            modifier = Modifier.requiredSize(imageSize).clip(CircleShape)
                .border(width = 3.dp, color = Color.LightGray, shape = CircleShape),
            onClick = {
                launcher.launch("image/*")
            }) {
        }
    }
}