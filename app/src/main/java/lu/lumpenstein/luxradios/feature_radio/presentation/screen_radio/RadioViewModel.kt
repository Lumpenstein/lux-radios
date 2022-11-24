package lu.lumpenstein.luxradios.feature_radio.presentation.screen_radio

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import lu.lumpenstein.luxradios.data.RadioStation

enum class PlayerState {
    STOPPED, BUFFERING, PLAYING, ERROR
}

data class RadioUiState(
    val playerState: PlayerState = PlayerState.STOPPED,
    val selectedStation: RadioStation? = null
)

class RadioViewModel : ViewModel() {
    // Radio UI state
    private val _uiState = MutableStateFlow(RadioUiState())
    // Backing property to avoid state updates from other classes
    val uiState: StateFlow<RadioUiState> = _uiState.asStateFlow()

    fun updateIsPlaying(newState: PlayerState) {
        _uiState.update { currentState ->
            currentState.copy(
                playerState = newState,
            )
        }
    }

    fun updateSelectedStation(newStation: RadioStation) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedStation = newStation,
            )
        }
    }
}