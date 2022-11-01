package lu.lumpenstein.luxradios

import RadioPlayer
import RadioStation
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Image
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import lu.lumpenstein.luxradios.ui.theme.LuxRadiosTheme

public val stations: List<RadioStation> = listOf(
    RadioStation(
        name = "RTL Letzebuerg",
        description = "RTL Radio Lëtzebuerg",
        url = "https://shoutcast.rtl.lu/rtl"
    ),
    RadioStation(
        name = "Radio ARA",
        description = "ARA is the Community radio of Luxembourg since 1992, and it plays a social key role for all the citizens that live in the country, by broadcasting its program in more than 10 different languages, and bringing local communities together.",
        url = "https://www.ara.lu/live/"
    ),
    RadioStation(
        name = "Eldo Radio",
        description = "",
        url = "https://stream.rtl.lu/live/hls/radio/eldo"
    ),
    RadioStation(
        name = "Essentiel Radio",
        description = "",
        url = "https://lessentielradio.ice.infomaniak.ch/lessentielradio-128.mp3"
    ),
    RadioStation(
        name = "100 komma 7",
        description = "",
        url = "https://100komma7--di--nacs-ice-01--02--cdn.cast.addradio.de/100komma7/live/mp3/128/stream.mp3"
    ),
    RadioStation(
        name = "RTL Radio",
        description = "Deutschlands Hitradio",
        url = "https://rtlberlin.streamabc.net/rtlb-rtldenational-mp3-128-2770113"
    )
)

private var radioPlayer: RadioPlayer? = null

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LuxRadiosTheme {
                App(stations)
            }
        }

        radioPlayer = RadioPlayer(applicationContext)
        radioPlayer?.initPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        radioPlayer?.destroyPlayer()
    }
}

@Composable
fun App(stations: List<RadioStation>) {
    LazyColumn() {
        items(stations) { station ->
            RadioStationCard(station)
        }
    }
}

@Composable
fun RadioStationCard(station: RadioStation) {
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .wrapContentSize()
        .clickable(
            onClick = {
                radioPlayer?.setStation(station)
            }
        )) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, Color.Red, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = station.name,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = station.description)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(color = Color.Green) {
        Text(text = "Hello, $name!")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LuxRadiosTheme {
        Greeting("Android")
    }
}