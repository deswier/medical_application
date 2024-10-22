package screens.result

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.model.Note
import com.myapplication.tools.DateParser
import factory.RequestFactory
import factory.call
import screens.navigation.BottomBarScreen
import theme.color.Green
import theme.color.LightGray
import theme.color.redText
import tools.getResultColor
import tools.getTextColor
import java.util.*

@Composable
fun showResultScreen(navController: NavHostController, uuid: UUID) {
    val contex = LocalContext.current
    val isDeleted = remember { mutableStateOf(false) }
    val card = remember { mutableStateOf(Note()) }
    if (!isDeleted.value) getCardOfResult(uuid, card)
    var similar = remember {
        mutableStateOf(ArrayList<Note>())
    }
    if (card.value.test != null && !isDeleted.value) getSimilarResult(card.value.test, similar)
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
                    Text(card.value.test ?: "", fontSize = 22.sp)
                    Spacer(Modifier.weight(1f, true))
                    IconButton(
                        onClick = {
                            isDeleted.value = true
                            navController.navigate(BottomBarScreen.Result.route)
                            RequestFactory.noteService.deleteNote(uuid).call()
                            Toast.makeText(contex, "Удалено", Toast.LENGTH_SHORT).show()
                        },
                        Modifier.width(50.dp)
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            "contentDescription",
                        )
                    }
                }
            },
        ) {
            ProvideTextStyle(
                TextStyle(color = getTextColor())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp, 0.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    var text = DateParser.convertToString(card.value.date)
                    if (card.value.lab != null && card.value.lab != "null")
                        text = text + ", " + card.value.lab
                    Text(
                        text,
                        modifier = Modifier.padding(top = 50.dp)
                    )
                    if (card.value.comment != null) {
                        Text(
                            "Комментарий:  " + card.value.comment,
                        )
                    }
                    Text(
                        (("Целевой результат: " + (card.value.referenceRange + " " + card.value.unit))),
                        color = Green,
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Canvas(modifier = Modifier.fillMaxWidth().height(200.dp).padding(top = 50.dp)) {
                            var x = 50f
                            var y: Float
                            val canvasWidth = size.width
                            val canvasHeight = size.height
                            if (Note.referenceRange(card.value.referenceRange ?: "").size == 2) {
                                val minY = getMinResult(similar.value)
                                val maxY = getMaxResult(similar.value)
                                val diffRes = maxY - minY
                                val delta = canvasHeight / diffRes
                                val minus = minY * delta
                                val nextX = canvasWidth / (similar.value.size)
                                val textPaint = Paint().asFrameworkPaint().apply {
                                    isAntiAlias = true
                                    textSize = 12.sp.toPx()
                                    color = android.graphics.Color.BLUE
                                }
                                for (item in similar.value) {
                                    try {
                                        val res = item.result.toFloat()
                                        y = canvasHeight - (delta * res - minus)
                                        drawCircle(
                                            color = getResultColor(item, Color.Green, redText),
                                            center = Offset(x = x, y = y),
                                            radius = 15f
                                        )
                                        drawLine(
                                            start = Offset(
                                                x = 7f,
                                                y = canvasHeight - (delta * Note.referenceRange(card.value.referenceRange)[1].toFloat() - minus)
                                            ),
                                            end = Offset(
                                                x = size.maxDimension,
                                                y = canvasHeight - (delta * Note.referenceRange(card.value.referenceRange)[1].toFloat() - minus)
                                            ),
                                            color = LightGray
                                        )
                                        drawLine(
                                            start = Offset(
                                                x = 7f,
                                                y = canvasHeight - (delta * Note.referenceRange(card.value.referenceRange)[0].toFloat() - minus)
                                            ),
                                            end = Offset(
                                                x = size.maxDimension,
                                                y = canvasHeight - (delta * Note.referenceRange(card.value.referenceRange)[0].toFloat() - minus)
                                            ),
                                            color = LightGray
                                        )
                                        drawIntoCanvas {
                                            it.nativeCanvas.drawText(
                                                Note.referenceRange(card.value.referenceRange)[0],
                                                0f,
                                                canvasHeight - (delta * Note.referenceRange(card.value.referenceRange)[0].toFloat() - minus),
                                                textPaint
                                            )
                                        }

                                        drawIntoCanvas {
                                            it.nativeCanvas.drawText(
                                                Note.referenceRange(card.value.referenceRange)[1],
                                                0f,
                                                canvasHeight - (delta * Note.referenceRange(card.value.referenceRange)[1].toFloat() - minus),
                                                textPaint
                                            )
                                        }

                                        drawIntoCanvas {
                                            it.nativeCanvas.drawText(
                                                DateParser.convertToShortString(item.date),
                                                x, 550F, textPaint
                                            )
                                        }

                                        drawIntoCanvas {
                                            it.nativeCanvas.drawText(res.toString(), x, y + 55, textPaint)
                                        }
                                        x += nextX
                                    } catch (_: NumberFormatException) {
                                    }

                                }
                            } else {

                                drawCircle(
                                    color = getResultColor(card.value, Green, redText),
                                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                                    radius = size.minDimension / 2
                                )
                                val textPaint = Paint().asFrameworkPaint().apply {
                                    isAntiAlias = true
                                    textSize = 17.sp.toPx()
                                    color = android.graphics.Color.WHITE
                                }
                                val kPadding = 2 + (0.085 * (card.value.result + " " + card.value.unit).length)
                                drawIntoCanvas {
                                    it.nativeCanvas.drawText(
                                        card.value.result + " " + card.value.unit,
                                        (canvasWidth / kPadding).toFloat(), canvasHeight / 2, textPaint
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getMinResult(similar: ArrayList<Note>): Float {
    var min = 10000f//Note.referenceRange(similar.value[0].referenceRange)[0].toFloat()
    for (item in similar) {
        try {
            if (item.result.toFloat() < min) min = item.result.toFloat()
        } catch (_: NumberFormatException) {
        }
    }
    return min
}

fun getMaxResult(similar: ArrayList<Note>): Float {
    var max = -1f//Note.referenceRange(similar.value[0].referenceRange)[1].toFloat()
    for (item in similar) {
        try {
            if (item.result.toFloat() > max) max = item.result.toFloat()
        } catch (_: NumberFormatException) {
        }
    }
    return max
}

fun getSimilarResult(test: String, results: MutableState<ArrayList<Note>>) {
    val list: ArrayList<Note> = arrayListOf()
    RequestFactory.noteService.getNote((test)).call(onSuccess = { _, v2 ->
        v2.body()?.forEach {
            list.add(it)
        }
        results.value = list
    })
}

fun getCardOfResult(uuid: UUID, card: MutableState<Note>) {
    RequestFactory.noteService.getNote((uuid)).call(onSuccess = { _, v2 ->
        card.value = Note(
            //todo change uuid
            v2.body()!!.user_id,
            UUID.randomUUID(),
            v2.body()!!.lab,
            v2.body()!!.test,
            Date(),
            v2.body()!!.result,
            v2.body()!!.referenceRange,
            v2.body()!!.unit,
            v2.body()?.comment
        )
    })
}