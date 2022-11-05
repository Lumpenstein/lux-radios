package lu.lumpenstein.luxradios

import android.support.v4.media.session.MediaSessionCompat

class RadioPlayerSessionCallback : MediaSessionCompat.Callback() {
    override fun onPrepare() {
        super.onPrepare()
        // A service that is only bound (and not started) is destroyed when all of its clients unbind.
        // If your UI activity disconnects at this point, the service is destroyed. This isn't a problem if you haven't played any music yet.
        // However, when playback starts, the user probably expects to continue listening even after switching apps.
        // You don't want to destroy the player when you unbind the UI to work with another app.
        // For this reason, you need to be sure that the service is started when it begins to play by calling startService()
        // To stop a started service, call Context.stopService() or stopSelf(). The system stops and destroys the service as soon as possible.
        // However, if one or more clients are still bound to the service, the call to stop the service is delayed until all its clients unbind.

        // Recap
        // The media session onPlay() callback should include code that calls startService().
        // This ensures that the service starts and continues to run, even when all UI MediaBrowser activities that are bound to it unbind.
    }

    override fun onPlay() {
        super.onPlay()
        // TODO Build and display the notification when the player starts playing.
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onSkipToPrevious() {
        super.onSkipToPrevious()
    }

    override fun onSkipToNext() {
        super.onSkipToNext()
    }
}
