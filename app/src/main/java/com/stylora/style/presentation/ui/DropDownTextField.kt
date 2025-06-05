package com.stylora.style.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.stylora.style.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextField(
    options: List<String>,
    selectedOption: String,
    placeholder: String,
    onOptionSelected: (String) -> Unit,
    label: String = "", modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = label,
                    fontFamily = FontFamily(Font(R.font.iransansxfanum_regular))
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = FontFamily(Font(R.font.iransansxfanum_regular))
                )
            },
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}
                ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(R.color.blue),
                unfocusedIndicatorColor = colorResource(R.color.light_gray),
                errorIndicatorColor = colorResource(R.color.red),
                focusedContainerColor = colorResource(R.color.white),
                unfocusedContainerColor = colorResource(R.color.white),
                errorContainerColor = colorResource(R.color.white),
                focusedTextColor = colorResource(R.color.dark_gray),
                focusedPlaceholderColor = colorResource(R.color.blue),
                unfocusedPlaceholderColor = colorResource(R.color.light_gray),
                focusedLabelColor = colorResource(R.color.blue),
                unfocusedLabelColor = colorResource(R.color.light_gray)
            ),
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.iransansxfanum_regular)),
                fontSize = 12.sp
            ),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier.background(color = colorResource(R.color.white))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = option, fontSize = 16.sp, fontFamily = FontFamily(
                                Font(R.font.iransansxfanum_regular)
                            ),
                            color = colorResource(if (selectedOption == option) R.color.white else R.color.black),
                            textAlign = TextAlign.Start
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }, modifier = modifier.background(
                        color = colorResource(if (selectedOption == option) R.color.blue else R.color.white)
                    )
                )
            }
        }
    }
}
