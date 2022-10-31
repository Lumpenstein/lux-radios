package lu.lumpenstein.luxradios

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lu.lumpenstein.luxradios.ui.theme.LuxRadiosTheme


class MainActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LuxRadiosTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.primary) {
                    Greeting("Androids")
                }
            }
        }
        setupPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    private fun setupPlayer() {
        val url = "http://stream.rpgamers.net:8000/rpgn" // your URL here
        mediaPlayer = MediaPlayer()

        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build()
            )
        } else {
            mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }

        try {
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.setOnPreparedListener(OnPreparedListener {
                mediaPlayer?.start()
                Toast.makeText(applicationContext, "Stream is being started", Toast.LENGTH_SHORT)
                    .show()
            })
            mediaPlayer?.prepareAsync() // might take long! (for buffering, etc)

            Toast.makeText(applicationContext, "Stream is being prepared", Toast.LENGTH_SHORT)
                .show()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()

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