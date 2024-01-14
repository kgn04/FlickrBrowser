package com.example.flickrbrowser

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flickrbrowser.ui.theme.FlickrBrowserTheme

class RequesterView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrBrowserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Browser()
                }
            }
        }
    }
}

var chosenTags = listOf<String>()
var chosenTagMode = "all"
var chosenLang = "Any language"



@Composable
fun TagsList() {
    var tags by remember { mutableStateOf(mutableListOf<String>()) }
    tags.forEachIndexed { id, tag ->
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                enabled = false,
                value = tag,
                onValueChange = {  },
                label = { Text("Tag ${id + 1}") },
            )
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(
                onClick = { tags = tags.toMutableList().apply { remove(tag) }
                    chosenTags = tags
                }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove")
            }
        }
    }
    if (tags.size < 5) {
        var last_tag by remember {mutableStateOf("")}
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                value = last_tag,
                onValueChange = { last_tag = it },
                label = { Text("Tag ${tags.size + 1}") }
            )
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(
                onClick = { if(last_tag != "" && !tags.contains(last_tag)) {
                    tags.add(last_tag)
                    last_tag = ""
                    chosenTags = tags
                } }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Remove")
            }
        }
    }
}

@Composable
fun LanguageDropdownMenu() {
    val languages = listOf<String>("Any language", "English", "German", "Spanish", "French",
        "Italian", "Korean", "Portugese", "Chinese")
    var chosenLanguage by remember {
        mutableStateOf("Any language")
    }
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(
            onClick = { expanded = true }) {
            Text(text = chosenLanguage)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            languages.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = { chosenLanguage = it
                    chosenLang = it }
                )
            }
        }
    }
}

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
        LanguageDropdownMenu()
        Spacer(modifier = Modifier.height(20.dp))
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

@Preview(showBackground = true)
@Composable
fun BrowserPreview() {
    FlickrBrowserTheme {
        Browser()
    }
}