package lu.lumpenstein.luxradios

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.*
import androidx.annotation.RequiresApi
import androidx.compose.ui.res.stringResource
import androidx.core.app.NotificationCompat
import androidx.media.MediaBrowserServiceCompat

private const val LOG_TAG = "LuxRadioLogTag"
private const val MY_MEDIA_ROOT_ID = "media_root_id"
private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"

class RadioPlayerService() : MediaBrowserServiceCompat() {
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var stateBuilder: PlaybackStateCompat.Builder

    // Never called
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        RadioPlayer.initPlayer()

        val action: String? = intent?.action
        when (action) {
            ACTION_PLAY.toString() -> {
                RadioPlayer.play()
            }
            ACTION_PAUSE.toString() -> {
                RadioPlayer.pause()
            }
            ACTION_STOP.toString() -> {
                RadioPlayer.stop()
            }

        }

        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        RadioPlayer.initPlayer()

        // Create a MediaSessionCompat
        mediaSession = buildMediaSession()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Create the NotificationChannel
        val mChannel = buildNotificationChannel()
        if (mChannel != null) {
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(mChannel)
            // Create the notification
            val notification = buildNotification(notificationManager)
            // Start the service in foreground (no sleepy) with the mandatory notification
            startForeground(1, notification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RadioPlayer.destroyPlayer()
        stopSelf()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return MediaBrowserServiceCompat.BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null)
        // (Optional) Control the level of access for the specified package name.
        // You'll need to write your own logic to do this.
        //        return if (allowBrowsing(clientPackageName, clientUid)) {
        //            // Returns a root ID that clients can use with onLoadChildren() to retrieve
        //            // the content hierarchy.
        //            MediaBrowserServiceCompat.BrowserRoot(MY_MEDIA_ROOT_ID, null)
        //        } else {
        //            // Clients can connect, but this BrowserRoot is an empty hierachy
        //            // so onLoadChildren returns nothing. This disables the ability to browse for content.
        //            MediaBrowserServiceCompat.BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null)
        //        }
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<List<MediaBrowserCompat.MediaItem>>
    ) {
        //  Browsing not allowed
        if (MY_EMPTY_MEDIA_ROOT_ID == parentId) {
            result.sendResult(null)
            return
        }

        // Assume for example that the music catalog is already loaded/cached.

        val mediaItems = emptyList<MediaBrowserCompat.MediaItem>()

        // Check if this is the root menu:
        if (MY_MEDIA_ROOT_ID == parentId) {
            // Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
        } else {
            // Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }
        result.sendResult(mediaItems)
    }

    private fun buildMediaSession(): MediaSessionCompat {
        return MediaSessionCompat(baseContext, LOG_TAG).apply {

            // Enable callbacks from MediaButtons and TransportControls
            setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                        or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )

            // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
            stateBuilder = PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY
                            or PlaybackStateCompat.ACTION_PLAY_PAUSE
                )

            setPlaybackState(stateBuilder.build())

            // MySessionCallback() has methods that handle callbacks from a media controller
            setCallback(RadioPlayerSessionCallback())

            // Set the session's token so that client activities can communicate with it.
            setSessionToken(sessionToken)
        }
    }

    private fun buildNotificationChannel(): NotificationChannel? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("YOLO", name, importance)
            mChannel.description = descriptionText
            return mChannel
        }
        return null
    }

    private fun buildNotification(notificationManager: NotificationManager): Notification {
        // The PendingIntent to launch our activity if the user selects this notification
        val contentIntent =
            PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), 0)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(
                applicationContext,
                notificationManager.getNotificationChannel("YOLO").id
            )
                // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_launcher_background)
                // Add media control buttons that invoke intents in your media service
                //            .addAction(R.drawable.ic_launcher_background, "Previous", prevPendingIntent) // #0
//                .addAction(R.drawable.ic_launcher_background, "Pause", null) // #1
                //            .addAction(R.drawable.ic_launcher_background, "Next", nextPendingIntent) // #2
                // Apply the media style template
//                .setStyle(
//                    androidx.media.app.NotificationCompat.MediaStyle()
////                        .setShowActionsInCompactView(0/* #1: pause button \*/)
//                        .setMediaSession(mediaSession?.getSessionToken())
//                )
                .setSilent(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(contentIntent) // When notification is clicked launch activity
                //            .setLargeIcon(albumArtBitmap)
                .build()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}