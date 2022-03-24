package screens.profile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.myapplication.model.Profile

@Composable
fun ImagePicker(profile: Profile) {
    var imageUrl by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(profile.photo) }
    val flagPhoto = remember { mutableStateOf(profile.photo != null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri
    }

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            // verticalArrangement = Arrangement.Top
        ) {
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                // .background(Color.LightGray)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // if (flagPhoto.value) { //if  photo

            imageUrl?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                    profile.setPhoto(bitmap.value)
                    flagPhoto.value = true;
                }
//                if (flagPhoto.value) { //if  photo
//
//                    bitmap.value?.let { bitmap ->
//
//                        Image(
//                            bitmap = bitmap.asImageBitmap(),
//                            contentDescription = "Gallery Image",
//                            modifier = Modifier.size(100.dp)//.padding(100.dp,50.dp)
//                                .clip(CircleShape)
//                                .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
//                        )
//                    }
//                    profile.setPhoto(bitmap.value)
//                }
            }
            if (flagPhoto.value) { //if  photo
                if (flagPhoto.value) {

                    bitmap.value?.let { bitmap ->

                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Gallery Image",
                            modifier = Modifier.size(100.dp)//.padding(100.dp,50.dp)
                                .clip(CircleShape)
                                .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
                        )
                    }
                    profile.setPhoto(bitmap.value)
                }//if  photo
                Button(
                    //    modifier = Modifier.padding(200.dp),
                    onClick = {
                        imageUrl = null
                        bitmap.value = null
                        flagPhoto.value = false;
                        profile.setPhoto(null)
                    }
                ) {
                    Text(
                        text = "Delete photo",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {

                val resId = context.resources.getIdentifier("no_photo", "drawable", context.packageName)
                val img = context.getDrawable(resId)
                if (img != null) {
                    Image(
                        //                        bitmap = bitmap.asImageBitmap(),
                        bitmap = img.toBitmap(100, 100).asImageBitmap(),
                        contentDescription = "Gallery Image",
                        modifier = Modifier.size(100.dp)//.padding(100.dp,50.dp)
                            .clip(CircleShape)
                            .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
                    )
                }
            }


            Spacer(modifier = Modifier.padding(20.dp))

            Button(
                onClick = {
                    launcher.launch("image/*")
                }
            ) {
                Text(
                    text = "Change photo",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}