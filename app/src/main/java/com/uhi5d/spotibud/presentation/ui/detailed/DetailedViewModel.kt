package com.uhi5d.spotibud.presentation.ui.detailed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.uhi5d.spotibud.data.local.datastore.DataStoreManager
import com.uhi5d.spotibud.domain.model.album.Album
import com.uhi5d.spotibud.domain.model.albumstracks.AlbumsTracksResponse
import com.uhi5d.spotibud.domain.model.artistalbums.ArtistAlbums
import com.uhi5d.spotibud.domain.model.artists.Artists
import com.uhi5d.spotibud.domain.model.artisttoptracks.ArtistTopTracks
import com.uhi5d.spotibud.domain.model.relatedartists.RelatedArtists
import com.uhi5d.spotibud.domain.model.track.Track
import com.uhi5d.spotibud.domain.model.trackaudiofeatures.TrackAudioFeatures
import com.uhi5d.spotibud.domain.usecase.UseCase
import com.uhi5d.spotibud.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedViewModel
@Inject constructor(
    private val useCase: UseCase,
    dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _artistsTopTracks: MutableLiveData<DataState<ArtistTopTracks>> = MutableLiveData()
    val artistsTopTracks get() = _artistsTopTracks

    private val _artistsAlbums: MutableLiveData<DataState<ArtistAlbums>> = MutableLiveData()
    val artistsAlbums get() = _artistsAlbums

    private val _relatedArtists: MutableLiveData<DataState<RelatedArtists>> = MutableLiveData()
    val relatedArtists get() = _relatedArtists

    private val _albumTracks: MutableLiveData<DataState<AlbumsTracksResponse>> = MutableLiveData()
    val albumTracks get() = _albumTracks

    private val _audioFeatures: MutableLiveData<DataState<TrackAudioFeatures>> = MutableLiveData()
    val audioFeatures get() = _audioFeatures

    private val _track: MutableLiveData<DataState<Track>> = MutableLiveData()
    val track get() = _track

    private val _artists: MutableLiveData<DataState<Artists>> = MutableLiveData()
    val artists get() = _artists

    private val _album: MutableLiveData<DataState<Album>> = MutableLiveData()
    val album get() = _album

    val token = dataStoreManager.getToken.asLiveData(viewModelScope.coroutineContext)

    fun getArtistsTopTracks(token: String, artistId: String, market: String) =
        viewModelScope.launch {
            useCase.getArtistTopTracks(token, artistId, market).collect { state ->
                _artistsTopTracks.value = state
            }
        }

    fun getArtistsAlbums(token: String, artistId: String, market: String) = viewModelScope.launch {
        useCase.getArtistAlbums(token, artistId, market).collect { state ->
            _artistsAlbums.value = state
        }
    }

    fun getRelatedArtists(token: String, artistId: String, market: String) = viewModelScope.launch {
        useCase.getArtistRelatedArtists(token, artistId).collect { state ->
            _relatedArtists.value = state
        }
    }

    fun getAlbumsTracks(token: String, albumId: String) = viewModelScope.launch {
        useCase.getAlbumsTracks(token, albumId).collect { state ->
            _albumTracks.value = state
        }
    }

    fun getTracksAudioFeatures(token: String, trackId: String) = viewModelScope.launch {
        useCase.getTrackAudioFeature(token, trackId).collect { state ->
            _audioFeatures.value = state
        }
    }

    fun getTrack(token: String, trackId: String) = viewModelScope.launch {
        useCase.getTrack(token, trackId).collect { state ->
            _track.value = state
        }
    }

    fun getArtists(token: String, artistIds: String) = viewModelScope.launch {
        useCase.getSeveralArtists(token, artistIds).collect { state ->
            _artists.value = state
        }
    }

    fun getAlbum(token: String, albumId: String) = viewModelScope.launch {
        useCase.getAlbum(token, albumId).collect { state ->
            _album.value = state
        }
    }

}