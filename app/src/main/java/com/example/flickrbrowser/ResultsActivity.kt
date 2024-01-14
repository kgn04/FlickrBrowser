package com.example.flickrbrowser

import Requester
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.flickrbrowser.ui.theme.FlickrBrowserTheme



class ResultsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrBrowserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val requester = intent.getStringExtra("tags")?.let {
                        intent.getStringExtra("tagmode")?.let { it1 ->
                                Requester(
                                    it,
                                    it1
                                )
                        }
                    }
                    if (requester != null) {
                        ImagesList(requester.getImagesURLs())
                    }
                }
            }
        }
    }
}