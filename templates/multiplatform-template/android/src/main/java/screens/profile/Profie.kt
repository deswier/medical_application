import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import screens.profile.ImagePicker
import theme.ImagePickerTheme


@Composable
fun ProfileScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        // .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {

    }
    ImagePickerTheme {
        ImagePicker()
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen()
}
