package lu.lumpenstein.luxradios

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import lu.lumpenstein.luxradios.data.RadioStation
import lu.lumpenstein.luxradios.feature_radio.presentation.screen_radio.PlayerState
import lu.lumpenstein.luxradios.feature_radio.presentation.screen_radio.RadioViewModel

// object in Kotlin = Singleton
object RadioPlayer {
    private var isInit = false
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var radioViewModel: RadioViewModel

    fun initPlayer(viewModel: RadioViewModel) {
        if (isInit) {
            return
        }

        radioViewModel = viewModel

        mediaPlayer = MediaPlayer()

        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC).build()
        )

        // Always start radio once prepared
        mediaPlayer.setOnPreparedListener {
            radioViewModel.updateIsPlaying(PlayerState.PLAYING)
            play()
        }

        // NOT Restart radio on error
        mediaPlayer.setOnErrorListener(MediaPlayer.OnErrorListener { mediaPlayer, i, i2 ->
//            play()
            radioViewModel.updateIsPlaying(PlayerState.ERROR)
            true // true = listener handled error
        })

        isInit = true
    }

    fun destroyPlayer() {
        mediaPlayer.release()
        isInit = false
    }

    fun setStation(station: RadioStation) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.reset()

        radioViewModel.updateSelectedStation(station)
        mediaPlayer.setDataSource(station.url)

        try {
            radioViewModel.updateIsPlaying(PlayerState.BUFFERING)
            mediaPlayer.prepareAsync() // might take long! (for buffering, etc)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun play() {
        radioViewModel.updateIsPlaying(PlayerState.PLAYING)
        mediaPlayer.start()
    }

    fun pause() {
        radioViewModel.updateIsPlaying(PlayerState.STOPPED)
        mediaPlayer.pause()
    }

    fun stop() {
        radioViewModel.updateIsPlaying(PlayerState.STOPPED)
        mediaPlayer.stop()
    }

    fun getCurrentSelectedStation(): Int? {
        try {
            val x = mediaPlayer.getSelectedTrack(MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_AUDIO)
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