@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.ambits.whatsapp.status.saver.tools.ui.screens.lite_message

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ambits.whatsapp.status.saver.tools.ui.components.WhatsStatusAppBar
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme
import com.owlbuddy.www.countrycodechooser.CountryCodeChooser
import com.owlbuddy.www.countrycodechooser.utils.enums.CountryCodeType

@Composable
fun LiteMessageScreen(
    onBackPress: () -> Unit,
    onSendMessage: (phoneNumber: String, message: String) -> Unit
) {
    val pattern = remember { Regex("^\\d+\$") }
    WhatsappStatusSaverToolsTheme {
        var inputNumber by remember {
            mutableStateOf("")
        }
        var message by remember {
            mutableStateOf("")
        }
        var userSelectedCode = "+91"
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            WhatsStatusAppBar(
                title = "Lite Message",
                onBackPress = {
                    onBackPress()
                },
                onTailIconPress = {}
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Send Message",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row {
                    CountryCodeChooser(
                        modifier = Modifier
                            .weight(1.5f)
                            .height(55.dp)
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(5.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        defaultCountryCode = "91",
                        countryCodeType = CountryCodeType.TEXT,
                        onCountyCodeSelected = { code: String, codeWithPrefix: String ->
                            userSelectedCode = codeWithPrefix
                            Log.e("TAG", "LiteMessageScreen: $code :: $codeWithPrefix")
                        }
                    )

                    Spacer(modifier = Modifier.weight(0.3f))

                    OutlinedTextField(
                        modifier = Modifier.weight(8.2f),
                        singleLine = true,
                        value = inputNumber,
                        placeholder = {
                            Text("Contact Number")
                        },
                        onValueChange = {
                            if (it.isEmpty() || it.matches(pattern)) {
                                inputNumber = it
                            }

                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    value = message,
                    placeholder = {
                        Text("Message")
                    },
                    label = {
                        Text("Message")
                    },
                    onValueChange = {
                        message = it
                    },
                    maxLines = 5
                )
                TextButton(onClick = { message = "" }, modifier = Modifier.align(Alignment.End)) {
                    Text(text = "Clear message")
                }
                Spacer(modifier = Modifier.weight(1F))
                Button(
                    onClick = {
                        onSendMessage("$userSelectedCode$inputNumber", message)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = inputNumber.isNotEmpty() && message.isNotEmpty()
                ) {
                    Text(text = "Send Message")
                }
            }
        }
    }
}