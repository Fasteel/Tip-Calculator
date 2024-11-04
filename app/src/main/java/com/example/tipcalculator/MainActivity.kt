package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipTimeLayout()
                }
            }
        }
    }
}

fun calculateTip(amount: Double, tipPercent: Double = 15.0, shouldRoundTip: Boolean): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(
        if (shouldRoundTip) ceil(tip).toInt()
        else tip
    )
}

@Composable
fun TipTimeLayout() {
    var amount by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf("") }
    var shouldRoundTip by remember { mutableStateOf(false) }

    val doubleAmount = amount.toDoubleOrNull() ?: 0.0
    val doubleTipPercent = tipPercent.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(doubleAmount, doubleTipPercent, shouldRoundTip)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            label = R.string.bill_amount,
            amount = amount,
            onValueChange = { amount = it },
            leadingIcon = R.drawable.money,
            iconDescription = R.string.input_icon_bill_description,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditNumberField(
            label = R.string.tip_label,
            amount = tipPercent,
            onValueChange = { tipPercent = it },
            leadingIcon = R.drawable.percent,
            iconDescription = R.string.input_icon_percent_description,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 24.dp)
        ) {
            Text(stringResource(R.string.tip_question))
            Switch(
                checked = shouldRoundTip,
                onCheckedChange = {
                    shouldRoundTip = it
                }
            )
        }
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @StringRes iconDescription: Int,
    @DrawableRes leadingIcon: Int,
    modifier: Modifier = Modifier,
    amount: String,
    onValueChange: (str: String) -> Unit
) {
    TextField(
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIcon),
                contentDescription = stringResource(iconDescription)
            )
        },
        value = amount,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(stringResource(label)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}