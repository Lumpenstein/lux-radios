package lu.lumpenstein.luxradios

import android.support.v4.media.MediaBrowserCompat

class MediaBrowserCallbacks : MediaBrowserCompat.ConnectionCallback() {
    override fun onConnected() {
        super.onConnected()
        println("MediaBrowserCallbacks onConnected")
    }

    override fun onConnectionFailed() {
        super.onConnectionFailed()
        println("MediaBrowserCallbacks onConnectionFailed")
    }

    override fun onConnectionSuspended() {
        super.onConnectionSuspended()
        println ("MediaBrowserCallbacks onConnectionSuspended")

    }
}