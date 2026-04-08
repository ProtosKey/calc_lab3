package view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import view.utils.Colors
import view.utils.Sizes

@Composable
fun input(
    label: String,
    state: MutableState<String>,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = label,
            color = Colors.textMain,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = Sizes.indent)
        )

        val containerColor = if (isFocused) Colors.main else Colors.sidebar
        TextField(
            value = state.value,
            onValueChange = { state.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
                .border(
                    border = BorderStroke(
                        Sizes.skip,
                        if (isFocused) Colors.divider else Color.Transparent
                    ),
                    shape = RoundedCornerShape(Sizes.round)
                ).height(Sizes.middleHeight),
            shape = RoundedCornerShape(Sizes.round),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Colors.textMain,
                unfocusedTextColor = Colors.textMain,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                cursorColor = Colors.textMain,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
    }
}
