package com.uhi5d.statibud.data.remote

private val CLIENT_ID = "d3810deed3744f56b5cc8a5a905c803f"
private val REDIRECT_URI = "com.uhi5d.spotibud://callback"
private val REQUEST_CODE = 1337

sealed class Scopes {
    val USER_READ_PRIVATE = "user-read-private"
    val PLAYLIST_READ = "playlist-read"
    val PLAYLIST_READ_PRIVATE = "playlist-read-private"
    val STREAMING = "streaming"
    val USER_TOP_READ = "user-top-read"
    val USER_READ_RECENTLY_PLAYED = "user-read-recently-played"
    val USER_READ_EMAIL = "user-read-email"
    val USER_READ_PLAYBACK_STATE = " user-read-playback-state"
}