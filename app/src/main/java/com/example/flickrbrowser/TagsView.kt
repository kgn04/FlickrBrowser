package com.example.flickrbrowser

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


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
        var lastTag by remember { mutableStateOf("") }
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                value = lastTag,
                onValueChange = { lastTag = it },
                label = { Text("Tag ${tags.size + 1}") }
            )
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(
                onClick = { if(lastTag != "" && !tags.contains(lastTag)) {
                    tags.add(lastTag)
                    lastTag = ""
                    chosenTags = tags
                } }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Remove")
            }
        }
    }
}