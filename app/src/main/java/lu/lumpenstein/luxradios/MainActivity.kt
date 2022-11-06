package lu.lumpenstein.luxradios

import android.content.ComponentName
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import lu.lumpenstein.luxradios.ui.screens.RadioScreen
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
                    RadioScreen()
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


