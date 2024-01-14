package com.example.flickrbrowser

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Browser() {
    Column (modifier = Modifier
        .padding(start = 50.dp, end = 50.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "What are you\nlooking for?",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        TagsList()
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Should results contain at least one or all of the tags?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        var checked by remember { mutableStateOf(true) }
        Row (horizontalArrangement = Arrangement.SpaceEvenly){
            Text(
                text = "At least\none",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(50.dp))
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    chosenTagMode = if (checked) "all" else "any"
                }
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                text = "All of\nthem",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        val mContext = LocalContext.current
        Row {
            Spacer(modifier = Modifier.width(150.dp))
            Button(
                onClick = { val intent = Intent(mContext, ResultsActivity::class.java)
                    intent.putExtra("tags" , chosenTags.joinToString(separator = ","))
                    intent.putExtra("tagmode" , chosenTagMode)
                    intent.putExtra("language", chosenLang)
                    mContext.startActivity(intent) }) {
                Text("Let's find it!")
            }
        }

    }
}