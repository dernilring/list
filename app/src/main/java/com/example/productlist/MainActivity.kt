package com.example.productlist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productlist.ui.theme.ProductListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductListTheme {
                val context = LocalContext.current
                val viewModel: LampViewModel = viewModel()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            shape = CircleShape,
                            containerColor = Color.Cyan,
                            onClick = { viewModel.addLamp() },
                        ) {
                            Text(
                                text = "+",
                                fontSize = 24.sp,
                                modifier = Modifier.padding(horizontal = 2.dp)
                            )
                        }
                    }
                ) { innerPadding ->
                    lampsList(
                        lampsList = viewModel.lampList,
                        modifier = Modifier.padding(innerPadding),
                        onItemClick = { currLamp ->
                            Toast.makeText(
                                context,
                                "${currLamp.title} — ${currLamp.price}",
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        onItemLongClick = { lampToRemove ->
                            viewModel.removeLamp(lampToRemove)
                        },
                        onFavoriteClick = { lamp ->
                            viewModel.toggleFavorite(lamp)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun lampsList(
    lampsList: List<Lamp>,
    modifier: Modifier = Modifier,
    onItemClick: (Lamp) -> Unit,
    onItemLongClick: (Lamp) -> Unit,
    onFavoriteClick: (Lamp) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(lampsList, key = { it.id }) { currentLamp ->
            lampItem(
                lamp = currentLamp,
                onItemClick = onItemClick,
                onItemLongClick = onItemLongClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Composable
fun lampItem(
    lamp: Lamp,
    onItemClick: (Lamp) -> Unit,
    onItemLongClick: (Lamp) -> Unit,
    onFavoriteClick: (Lamp) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onItemClick(lamp) },
                onLongClick = { onItemLongClick(lamp) }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(lamp.photoId),
                contentDescription = lamp.title,
                modifier = Modifier
                    .size(70.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lamp.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${lamp.price}",
                    fontSize = 14.sp
                )
            }
            IconButton(onClick = { onFavoriteClick(lamp) }) {
                Icon(
                    painter = painterResource(
                        if (lamp.isInCard) R.drawable.baseline_favorite_24
                        else R.drawable.outline_arrow_drop_down_circle_24
                    ),
                    contentDescription = if (lamp.isInCard) "Убрать из избранного"
                    else "В избранное",
                    tint = if (lamp.isInCard) Color.Red else Color.DarkGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProductListTheme {
        val sample = listOf(
            Lamp(title = "lamp1", price = 100.0, photoId = R.drawable.lamp1),
            Lamp(title = "lamp2", price = 99.9, photoId = R.drawable.lamp2, isInCard = true),
            Lamp(title = "lamp3", price = 79.9, photoId = R.drawable.lamp3),
        )
        lampsList(
            lampsList = sample,
            onItemClick = {},
            onItemLongClick = {},
            onFavoriteClick = {}
        )
    }
}