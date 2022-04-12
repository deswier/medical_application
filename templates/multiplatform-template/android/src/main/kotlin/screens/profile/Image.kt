package screens.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.myapplication.model.Profile

@Composable
fun imagePicker(profile: Profile, edit: Boolean) {
    var imageUrl by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(profile.photo) }
    val imageSize = 100.dp
    val context = LocalContext.current
    val flagPhoto = remember { mutableStateOf(profile.photo != null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        imageUrl?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(LocalContext.current.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(LocalContext.current.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
            profile.setPhoto(bitmap.value)
            flagPhoto.value = true
        }
        IconButton(
            modifier = Modifier.size(imageSize).clip(CircleShape)
                .border(width = 3.dp, color = Color.LightGray, shape = CircleShape),
            onClick = {
                if (edit) {
                    launcher.launch("image/*")
                }
            },
        ) {
            if (flagPhoto.value) { //if  photo
                bitmap.value?.let { bitmap ->
                    photo(bitmap, imageSize)
                    profile.setPhoto(bitmap)
                }
            } else {
                noPhoto(context)
            }
        }
        if (edit) {
            IconButton(
                onClick = {
                    imageUrl = null
                    bitmap.value = null
                    flagPhoto.value = false
                    profile.setPhoto(null)
                }
            ) {
                Icon(
                    Icons.Filled.Delete,
                    "contentDescription",
                )
            }
        }
    }
}

@Composable
fun noPhoto(context: Context) {
    val resId = context.resources.getIdentifier("no_photo", "drawable", context.packageName)
    val img = context.getDrawable(resId)
    if (img != null) {
        Image(
            bitmap = img.toBitmap(100, 100).asImageBitmap(),
            contentDescription = "Gallery Image",
            modifier = Modifier.size(100.dp)
                .clip(CircleShape)
                .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
        )
    }
}

@Composable
fun photo(bitmap: Bitmap, imageSize: Dp) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "Gallery Image",
        modifier = Modifier.size(imageSize)
            .clip(CircleShape)
            .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
    )
}