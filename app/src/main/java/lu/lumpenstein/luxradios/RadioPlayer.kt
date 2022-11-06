package lu.lumpenstein.luxradios

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import lu.lumpenstein.luxradios.data.RadioStation

// object in Kotlin = Singleton
object RadioPlayer {


    private var mediaPlayer: MediaPlayer? = null
    var isInit = false

    fun initPlayer() {
        if (isInit) {
            return
        }

        mediaPlayer = MediaPlayer()

        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC).build()
        )

        // Always start radio once prepared
        mediaPlayer?.setOnPreparedListener {
            play()
        }

        // Restart radio on error
        mediaPlayer?.setOnErrorListener(MediaPlayer.OnErrorListener { mediaPlayer, i, i2 ->
            play()
            true // true = listener handled error
        })

        isInit = true
    }

    fun destroyPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
        isInit = false
    }

    fun setStation(station: RadioStation) {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
        }
        mediaPlayer?.reset()

        mediaPlayer?.setDataSource(station.url)

        try {
            mediaPlayer?.prepareAsync() // might take long! (for buffering, etc)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun play() {
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun stop() {
        mediaPlayer?.stop()
    }

    fun getCurrentSelectedStation(): Int? {
        try {
            val x = mediaPlayer?.getSelectedTrack(MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_AUDIO)
            return x
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        return null
    }

    fun getMediaPlayer(): MediaPlayer? {
        return mediaPlayer
    }
}