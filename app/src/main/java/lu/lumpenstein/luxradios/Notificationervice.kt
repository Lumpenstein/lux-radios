import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.content.ContextCompat
import lu.lumpenstein.luxradios.R
import androidx.core.app.NotificationCompat as NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.media.session.MediaButtonReceiver

public class NotificationService() {
//    fun init(context: Context) {
//        val notification = NotificationCompat.Builder(context, "ID")
//            // Show controls on lock screen even when user hides sensitive content.
//            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//            .setSmallIcon(R.drawable.ic_launcher_background)
//            // Add media control buttons that invoke intents in your media service
////            .addAction(R.drawable.ic_launcher_background, "Previous", prevPendingIntent) // #0
//            .addAction(R.drawable.ic_launcher_background, "Pause", pausePendingIntent) // #1
////            .addAction(R.drawable.ic_launcher_background, "Next", nextPendingIntent) // #2
//            // Apply the media style template
//            .setStyle(
//                MediaStyle()
//                    .setShowActionsInCompactView(1 /* #1: pause button \*/)
//                    .setMediaSession(mediaSession.getSessionToken())
//            )
//            .setContentTitle("Wonderful music")
//            .setContentText("My Awesome Band")
////            .setLargeIcon(albumArtBitmap)
//            .build()
//    }


    fun setupNotificationServiceExample(
        context: Context,
        channelId: String,
        mediaSession: MediaSessionCompat
    ) {
        // Given a media session and its context (usually the component containing the session)
        // Create a NotificationCompat.Builder

        // Get the session's metadata
        val controller = mediaSession.controller
        val mediaMetadata = controller.metadata
        val description = mediaMetadata?.description

        val builder = NotificationCompat.Builder(context, channelId).apply {
            // Add the metadata for the currently playing track
            setContentTitle(description?.title)
            setContentText(description?.subtitle)
            setSubText(description?.description)
            setLargeIcon(description?.iconBitmap)

            // Enable launching the player by clicking the notification
            setContentIntent(controller.sessionActivity)

            // Stop the service when the notification is swiped away
            setDeleteIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    context,
                    PlaybackStateCompat.ACTION_STOP
                )
                // TODO release player, maybe close app
            )

            // Make the transport controls visible on the lockscreen
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            // Add an app icon and set its accent color
            // Be careful about the color
            setSmallIcon(R.drawable.ic_launcher_foreground)
            color = ContextCompat.getColor(context, R.color.purple_500)

            // Add a pause button
            addAction(
                NotificationCompat.Action(
                    R.drawable.ic_launcher_foreground,
                    "Pause", // getString(R.string.pause),
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        context,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE
                    )
                )
            )

            // Take advantage of MediaStyle features
            setStyle(
                MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
                    .setShowActionsInCompactView(0)

                    // Add a cancel button
                    .setShowCancelButton(true)
                    .setCancelButtonIntent(
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context,
                            PlaybackStateCompat.ACTION_STOP
                        )
                    )
            )
        }


        // Display the notification and place the service in the foreground
//        startForeground(id, builder.build())
    }
}