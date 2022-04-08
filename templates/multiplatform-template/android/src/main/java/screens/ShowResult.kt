package screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.model.Note
import com.myapplication.tools.DateParser
import theme.Green
import theme.LightGray
import java.util.*

@Composable
fun showResultScreen(navController: NavHostController, uuid: String) {
    val card = getCardOfResult(uuid)
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Scaffold(
            topBar = {
                TopAppBar {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        Modifier.width(50.dp)
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            "contentDescription",
                        )
                    }
                    Text(card.test, fontSize = 22.sp)
                    Spacer(Modifier.weight(1f, true))

                }
            },
        ) {
            ProvideTextStyle(
                TextStyle(color = Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp, 0.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    var text = DateParser.convertToTextDate(card.date)
                    if (card.lab != null && card.lab != "null")
                        text = text + ", " + card.lab
                    Text(
                        text,
                        modifier = Modifier.padding(top = 50.dp)
                    )
                    if (card.comment != null && card.comment != "null")
                        Text(
                            "Комментарий:  " + card.comment,
                        )

                    val edit = remember { mutableStateOf(false) }
                    var x = 50f
                    var y = 0f
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Canvas(modifier = Modifier.fillMaxWidth().height(200.dp).padding(top = 50.dp)) {
                            val canvasWidth = size.width
                            val canvasHeight = size.height
                            if (Note.referenceRange(card.referenceRange).size == 2) {
                                var firstIteration = true
                                val similar = getSimilarResult(card.test);
                                val minY = getMinResult(similar)
                                val maxY = getMaxResult(similar)
                                val diffRes = maxY - minY
                                val delta = canvasHeight / diffRes
                                val minus = minY * delta
                                val nextX = canvasWidth / (similar.size)
                                val textPaint = Paint().asFrameworkPaint().apply {
                                    isAntiAlias = true
                                    textSize = 12.sp.toPx()
                                    color = android.graphics.Color.BLUE
                                }
                                for (item in similar) {
                                    val res = item.result.toFloat()
                                    y = canvasHeight - (delta * res - minus)
                                    drawCircle(
                                        color = getResultColor(item),
                                        center = Offset(x = x, y = y),
                                        radius = 15f
                                    )
                                    drawLine(
                                        start = Offset(
                                            x = 7f,
                                            y = canvasHeight - (delta * Note.referenceRange(item.referenceRange)[1].toFloat() - minus)
                                        ),
                                        end = Offset(
                                            x = size.maxDimension,
                                            y = canvasHeight - (delta * Note.referenceRange(item.referenceRange)[1].toFloat() - minus)
                                        ),
                                        color = LightGray
                                    )
                                    drawLine(
                                        start = Offset(
                                            x = 7f,
                                            y = canvasHeight - (delta * Note.referenceRange(item.referenceRange)[0].toFloat() - minus)
                                        ),
                                        end = Offset(
                                            x = size.maxDimension,
                                            y = canvasHeight - (delta * Note.referenceRange(item.referenceRange)[0].toFloat() - minus)
                                        ),
                                        color = LightGray
                                    )
                                    drawIntoCanvas {
                                        it.nativeCanvas.drawText(
                                            Note.referenceRange(item.referenceRange)[0],
                                            0f,
                                            canvasHeight - (delta * Note.referenceRange(item.referenceRange)[0].toFloat() - minus),
                                            textPaint
                                        )
                                    }

                                    drawIntoCanvas {
                                        it.nativeCanvas.drawText(
                                            Note.referenceRange(item.referenceRange)[1],
                                            0f,
                                            canvasHeight - (delta * Note.referenceRange(item.referenceRange)[1].toFloat() - minus),
                                            textPaint
                                        )
                                    }

                                    drawIntoCanvas {
                                        it.nativeCanvas.drawText(
                                            DateParser.convertToStringWithoutYear(item.date),
                                            x, 550F, textPaint
                                        )
                                    }

                                    drawIntoCanvas {
                                        it.nativeCanvas.drawText(res.toString(), x, y + 55, textPaint)
                                    }
                                    x += nextX
                                }
                            } else {

                                drawCircle(
                                    color = getResultColor(card),
                                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                                    radius = size.minDimension / 2
                                )
                                val textPaint = Paint().asFrameworkPaint().apply {
                                    isAntiAlias = true
                                    textSize = 17.sp.toPx()
                                    color = android.graphics.Color.WHITE
                                }
                                val kPadding = 2 + (0.085 * (card.result + " " + card.unit).length)
                                drawIntoCanvas {
                                    it.nativeCanvas.drawText(
                                        card.result + " " + card.unit,
                                        (canvasWidth / kPadding).toFloat(), canvasHeight / 2, textPaint
                                    )
                                }
                            }
                        }
                        Text(
                            "Целевой результат: " + card.referenceRange,
                            color = Green,
                            modifier = Modifier.padding(15.dp)
                        )
                    }
                }
            }
        }
    }
}

fun getMinResult(similar: ArrayList<Note>): Float {
    var min = Note.referenceRange(similar[0].referenceRange)[0].toFloat()
    for (item in similar) {
        if (item.result.toFloat() < min) min = item.result.toFloat()
    }
    return min
}

fun getMaxResult(similar: ArrayList<Note>): Float {
    var max = Note.referenceRange(similar[0].referenceRange)[1].toFloat()
    for (item in similar) {
        if (item.result.toFloat() > max) max = item.result.toFloat()
    }
    return max
}

fun getSimilarResult(test: String): ArrayList<Note> {
    val notes = ArrayList<Note>()
    notes.add(
        Note(
            UUID.randomUUID(), "Invitro",
            "HbA1c", Date(), "17", "4-20", "%", null
        )
    )
    notes.add(
        Note(
            UUID.randomUUID(), "KDL",
            "HbA1c", Date(), "23", "4-20", "%", null
        )
    )
    notes.add(
        Note(
            UUID.randomUUID(), "Invitro",
            "HbA1c", DateParser.convertToDate("10-10-2021"), "30", "4-20", "%", null
        )
    )
    notes.add(
        Note(
            UUID.randomUUID(), "Invitro",
            "HbA1c", Date(), "15", "4-20", "%", null
        )
    )
    return notes
}

fun getCardOfResult(uuid: String): Note {
    //TODO with database
//    return Note(
//        UUID.fromString(uuid), "Invitro",
//        "Fe", Date(), "9", "9-30.4", "mm/l", "i love banana"
//    )
    return Note(
        UUID.fromString(uuid), "Invitro",
        "Fe", Date(), "выявленно", "не выявленно", "", "i love banana"
    )
}

fun getResultColor(item: Note): Color {
    return if (item.isNormalResult) Green
    else Color.Red
}