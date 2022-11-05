package lu.lumpenstein.luxradios

import android.content.ComponentName
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat

import lu.lumpenstein.luxradios.ui.theme.LuxRadiosTheme

private var radioPlayer: RadioPlayerService? = null
private var notification: NotificationCompat? = null

class MainActivity : ComponentActivity() {

    private lateinit var mediaBrowser: MediaBrowserCompat

    // TODO move everything to RadioApp.kt
    // TODO create VM for RadioApp.kt RadioAppState.kt to hold UiState
    // TODO get ui to change when isPLaying in VM changes
    // TODO use materialtheme for colors, typo, etc MaterialTheme.typography.subtitle2, MaterialTheme.shape.large
    // TODO attach OnError handler on mediaPlayer in RadioPlayer to change isPlaying back to false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LuxRadiosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App(RadioPlayer.stations)
                }
            }
        }

        // MediaBrowser connect activity with service, MediaBrowserCallbacks are used to get user inputs from the notification
        mediaBrowser = MediaBrowserCompat(
            this,
            ComponentName(this, RadioPlayerService::class.java),
            MediaBrowserCallbacks(),
            null // optional Bundle
        )
        // Start the background service that will run in the foreground (I know right)
        mediaBrowser.connect()

        // Since Lux Radios is a music player, the volume controls should adjust the music volume while
        // in the app.
        // TODO move to play, remove on pause/stop
        volumeControlStream = AudioManager.STREAM_MUSIC

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val intent = Intent("android.media.browse.MediaBrowserService")
//            intent.component = this.componentName
//            startForegroundService(intent)

//            val intent2 = Intent("lu.lumpenstein.test")
//            intent2.component = this.componentName
//            startForeground(intent2, notification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaBrowser.disconnect()
    }
}

@Composable
fun App(stations: List<RadioStation>) {
    var isPlaying = remember { mutableStateOf(RadioPlayer.getMediaPlayer()?.isPlaying) }
    Column() {
        // List with Radio Stations
        LazyColumn() {
            items(stations) { station ->
                RadioStationCard(station)
            }
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            //        Icon(painter = painterResource(id = R.drawable.ic_outline_play_arrow_24), "Play Button", modifier =  Modifier.clickable( onClick = {
            //            RadioPlayer.play()
            //        }))
            //        Icon(painter = painterResource(id = R.drawable.ic_outline_pause_24), "Pause Button", modifier =  Modifier.clickable( onClick = {
            //            RadioPlayer.pause()
            //        }))
            if (isPlaying.value == false) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_stop_24),
                    "Stop Button",
                    modifier = Modifier.clickable(onClick = {
                        RadioPlayer.stop()
                    })
                )
            }
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
                RadioPlayer.setStation(station)

            }
        )) {
        Image(
            painter = painterResource(station.img),
            contentDescription = "Logo of radio station",
            modifier = Modifier
                .size(40.dp)
                .border(1.5.dp, Color.Red)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = stringResource(id = station.name),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = station.description)
        }
    }
}
