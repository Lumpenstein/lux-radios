package lu.lumpenstein.luxradios

import android.content.ComponentName
import android.media.AudioManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.media.MediaBrowserCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import lu.lumpenstein.luxradios.ui.screens.RadioScreen
import lu.lumpenstein.luxradios.ui.screens.RadioViewModel
import lu.lumpenstein.luxradios.ui.theme.LuxRadiosTheme

class MainActivity : ComponentActivity() {

    private lateinit var mediaBrowser: MediaBrowserCompat

    // TODO use MainViewModel to hold ui and player state
    // get ui to change when isPLaying in VM changes
    // use materialtheme for colors, typo, etc MaterialTheme.typography.subtitle2, MaterialTheme.shape.large
    // attach OnError handler on mediaPlayer in RadioPlayer to change isPlaying back to false
    // aad pause/play stop/close app buttons to notification
    // determine minimum version (pump it up or fix notifications for older)

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {

        }

        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same DiceRollViewModel instance created by the first activity.

        // Use the 'by viewModels()' Kotlin property delegate
        // from the activity-ktx artifact
        val viewModel: RadioViewModel by viewModels()

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.uiState.collect {
//                    // Update UI elements
//
//                }
//            }
//        }

        // Setup RadioPlayer
        RadioPlayer.initPlayer(viewModel)

        setContent {
            val mainUiState by viewModel.uiState.collectAsState()
            // App wrapped in our material theme with dark/light mode support
            LuxRadiosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Text(text = mainUiState.playerState.toString())
                    RadioScreen(viewModel) // TODO use player state from VM
                }
            }
        }

        // MediaBrowser connect activity with the background service, MediaBrowserCallbacks are used to get user inputs from the notification
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
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaBrowser.disconnect()
    }
}


