package screens.tools

import OutlinedTextFieldFolder
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myapplication.tools.DateParser
import java.util.*

@Composable
fun showDatePicker(
    context: Context,
    modifier: Modifier,
    verticalArrangement: Arrangement.HorizontalOrVertical,
    horizontalAlignment: Alignment.Horizontal
): Calendar {

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    val date = remember { mutableStateOf(Calendar.getInstance()) }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            calendar.set(y, m, d)
            date.value = calendar
        }, year, month, day
    )

    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {

        Spacer(modifier = Modifier.size(16.dp))
        Row() {
            OutlinedTextFieldFolder(
                value = DateParser.convertToString(date.value),
                onValueChange = {
                },
                enabled = false,
                label = { Text(text = "Date") },
            )
            IconButton(
                onClick = { datePickerDialog.show() },
                Modifier.width(50.dp).padding(top = 20.dp)
            ) {
                Icon(
                    Icons.Filled.DateRange,
                    "contentDescription",
                )
            }
        }
    }
    return date.value
}