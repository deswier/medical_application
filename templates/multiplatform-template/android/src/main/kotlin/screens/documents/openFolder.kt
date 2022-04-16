package screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun folderFromDocumentScreen(navController: NavHostController) {
//    var expanded = remember { mutableStateOf(false) }
//    val openDialog = remember { mutableStateOf(false) }
//    val folders = remember { parentFolder.folders }
//
//    Scaffold(
//        topBar = {
//            TopAppBar {
//                Text(parentFolder.name, fontSize = 22.sp, modifier = Modifier.padding(horizontal = 20.dp))
//                Spacer(Modifier.weight(1f, true))
//                ProvideTextStyle(
//                    TextStyle(color = Color.White, fontSize = 8.sp)
//                ) {}
//                Box {
//                    IconButton(
//                        onClick = {
//                            expanded.value = true
//                        }) { Icon(Icons.Filled.Add, "contentDescription") }
//                    DropdownMenu(
//                        expanded = expanded.value,
//                        onDismissRequest = { expanded.value = false }
//                    ) {
//                        Text(
//                            "Добавить папку",
//                            fontSize = 18.sp,
//                            modifier = Modifier.padding(10.dp).clickable(onClick = {
//                                openDialog.value = true
//                            })
//                        )
//                        Text(
//                            "Добавить файл",
//                            fontSize = 18.sp,
//                            modifier = Modifier.padding(10.dp).clickable(onClick = {})
//                        )
//                        Divider()
//                        Text("Настройки", fontSize = 18.sp, modifier = Modifier.padding(10.dp).clickable(onClick = {}))
//                    }
//                }
//            }
//        },
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            if (openDialog.value) {
//                val folderName = remember { mutableStateOf("") }
//                AlertDialog(
//                    modifier = Modifier.width(300.dp),
//                    onDismissRequest = {
//                        openDialog.value = false
//                    },
//                    title = { Text(text = "Добавить папку") },
//                    text = {
//                        Column {
//                            Text("Введите название папки")
//                            outlinedTextFieldValidation(
//                                value = folderName.value,
//                                onValueChange = {
//                                    folderName.value = it
//                                },
//                            )
//                        }
//                    },
//                    buttons = {
//
//                        Row(modifier = Modifier.fillMaxWidth()) {
//
//                            Button(
//                                modifier = Modifier.padding(horizontal = 20.dp),
//                                onClick = {
//                                    if (folderName.value != "") {
//                                        folders.add(Folder(folderName.value))
//                                        openDialog.value = false
//                                    }
//                                },
//                                colors = ButtonDefaults.buttonColors(
//                                    disabledBackgroundColor = Color.White,
//                                    disabledContentColor = DarkBlue,
//                                    backgroundColor = Color.White,
//                                    contentColor = Color.Black
//                                ),
//                                elevation = null
//
//                            ) {
//                                Text("Добавить")
//                            }
//
//                            Button(
//                                modifier = Modifier.padding(horizontal = 15.dp),
//                                onClick = { openDialog.value = false },
//                                colors = ButtonDefaults.buttonColors(
//                                    disabledBackgroundColor = Color.White,
//                                    disabledContentColor = DarkBlue,
//                                    backgroundColor = Color.White,
//                                    contentColor = MaterialTheme.colors.error
//                                ),
//                                elevation = null
//                            )
//                            {
//                                Text("Отменить")
//                            }
//                        }
//                    }
//                )
//            }
//            for (item in folders) {
//                Column(
//                    modifier = Modifier.fillMaxWidth().height(100.dp).clickable(onClick = {
//                        folderFromDocumentScreen(navController, item)
//                    })
//                ) {
//                    Row() {
//                        Icon(
//                            Icons.Filled.Email,
//                            "folderIcon",
//                        )
//                        Text(
//                            item.name, modifier = Modifier.padding(horizontal = 30.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
}