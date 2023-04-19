package akhil.resolver

import akhil.resolver.resolver.ContentResolverHelper
import akhil.resolver.resolver.RoomItem
import akhil.resolver.ui.theme.RoomResolverTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    var contentResolverHelper: ContentResolverHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomResolverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initContentProvider()
    }

    private fun initContentProvider() {
        if (contentResolverHelper == null)
            contentResolverHelper = ContentResolverHelper(this)
    }


    @Composable
    fun Greeting(name: String) {

        val context = LocalContext.current
        val isInstalled = isAppInstalled(context, "com.akhil.room")
        var id by remember {
            mutableStateOf(0)
        }
        var roomItem by remember {
            mutableStateOf(RoomItem())
        }
        LaunchedEffect(key1 = id, block = {
            contentResolverHelper
                ?.getRoomItem(id)
                ?.let {
                    roomItem = it
                }
        })
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Hello $name!", style = TextStyle(
                    color = Color.Black, fontSize = 25.sp
                ), modifier = Modifier
                    .size(200.dp)
                    .clickable {
                        id++
                    }
            )

            Text(
                text = "${roomItem.id}, ${roomItem.title}, ${roomItem.content}", style = TextStyle(
                    color = Color.Black, fontSize = 25.sp, fontFamily = FontFamily.Serif
                ), modifier = Modifier
                    .size(200.dp)

            )

            Text(
                text = "Content provider is ${if (isInstalled) "installed" else "not installed"}",
                style = TextStyle(
                    color = if (isInstalled) Color.Green else Color.Red, fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }

    }
}
