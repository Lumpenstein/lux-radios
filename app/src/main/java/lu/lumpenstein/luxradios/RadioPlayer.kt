package lu.lumpenstein.luxradios

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer

data class RadioStation(val url: String, val name: String, val description: String, val order: Int, val img: Int)

// object in Kotlin = Singleton
object RadioPlayer {
    val stations: List<RadioStation> = listOf(
        RadioStation(
            name = "RTL Letzebuerg",
            description = "RTL Radio Lëtzebuerg",
            url = "https://shoutcast.rtl.lu/rtl",
            order = 1,
            img = R.drawable.logo_rtl_luxembourg
        ),
        RadioStation(
            name = "Radio ARA",
            description = "ARA is the Community radio of Luxembourg since 1992, and it plays a social key role for all the citizens that live in the country, by broadcasting its program in more than 10 different languages, and bringing local communities together.",
            url = "https://www.ara.lu/live/",
            order = 2,
            img = R.drawable.logo_ara
        ),
        RadioStation(
            name = "Eldo Radio",
            description = "",
            url = "https://stream.rtl.lu/live/hls/radio/eldo",
            order = 3,
            img = R.drawable.logo_eldo
        ),
        RadioStation(
            name = "Essentiel Radio",
            description = "",
            url = "https://lessentielradio.ice.infomaniak.ch/lessentielradio-128.mp3",
            order = 4,
            img = R.drawable.logo_essentiel
        ),
        RadioStation(
            name = "100 komma 7",
            description = "",
            url = "https://100komma7--di--nacs-ice-01--02--cdn.cast.addradio.de/100komma7/live/mp3/128/stream.mp3",
            order = 5,
            img = R.drawable.logo_100_komma_7
        ),
        RadioStation(
            name = "RTL Radio",
            description = "Deutschlands Hitradio",
            url = "https://rtlberlin.streamabc.net/rtlb-rtldenational-mp3-128-2770113",
            order = 6,
            img = R.drawable.logo_rtl_de
        )
    )

    private var mediaPlayer: MediaPlayer? = null
    var isInit = false

    fun initPlayer() {
        mediaPlayer = MediaPlayer()

        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC).build()
        )
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

        mediaPlayer?.setOnPreparedListener(MediaPlayer.OnPreparedListener {
            mediaPlayer?.start()
        })

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