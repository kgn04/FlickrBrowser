package com.example.flickrbrowser

import Requester
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.flickrbrowser.ui.theme.FlickrBrowserTheme
import fuel.Fuel
import fuel.get


class ResultsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrBrowserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val requester = getIntent().getStringExtra("tags")?.let {
                        getIntent().getStringExtra("tagmode")?.let { it1 ->
                            getIntent().getStringExtra("language")?.let { it2 ->
                                Requester(
                                    it,
                                    it1,
                                    it2
                                )
                            }
                        }
                    }
                    if (requester != null) {
                        Greeting2(requester.getImagesURLs())
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting2(photosList: List<String>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(photosList) { photo ->
                AsyncImage(
                    model = photo,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview2() {
//    FlickrBrowserTheme {
//        Greeting2()
//    }
//}