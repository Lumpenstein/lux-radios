import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.widget.Toast

data class RadioStation(val url: String, val name: String, val description: String)

class RadioPlayer(private val applicationContext: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun initPlayer() {
        mediaPlayer = MediaPlayer()

        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build()
        )
    }

    fun destroyPlayer() {
        mediaPlayer?.release()
    }

    fun setStation(station: RadioStation) {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
        }
        mediaPlayer?.reset()

        Toast.makeText(applicationContext, "Starting ${station.name} ...", Toast.LENGTH_SHORT)
            .show()
        // val url = "http://stream.rpgamers.net:8000/rpgn" // RPGN Radio

        mediaPlayer?.setDataSource(station.url)

        mediaPlayer?.setOnPreparedListener(MediaPlayer.OnPreparedListener {
            mediaPlayer?.start()
        })

        try {
            mediaPlayer?.prepareAsync() // might take long! (for buffering, etc)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Error starting stream", Toast.LENGTH_LONG).show()
        }
    }
}