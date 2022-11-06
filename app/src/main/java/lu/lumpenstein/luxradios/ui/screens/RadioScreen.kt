package lu.lumpenstein.luxradios.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lu.lumpenstein.luxradios.R
import lu.lumpenstein.luxradios.RadioPlayer
import lu.lumpenstein.luxradios.data.RadioStation
import lu.lumpenstein.luxradios.data.RadioStations

@Composable
fun RadioScreen(/*viewModel: RadioViewModel = viewModel()*/) {
//    var isPlaying = remember { mutableStateOf(RadioPlayer.getMediaPlayer()?.isPlaying) }
    Column() {
        Text(text = stringResource(id = R.string.app_name), Modifier.fillMaxWidth().padding(vertical = 15.dp))
        // List with Radio Stations
        LazyColumn() {
            items(RadioStations) { station ->
                RadioStationCard(station)
            }
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            //        Icon(painter = painterResource(id = R.drawable.ic_outline_play_arrow_24), "Play Button", modifier =  Modifier.clickable( onClick = {
            //            RadioPlayer.play()
            //        }))
            //        Icon(painter = painterResource(id = R.drawable.ic_outline_pause_24), "Pause Button", modifier =  Modifier.clickable( onClick = {
            //            RadioPlayer.pause()
            //        }))
//            if (uiState.isPlaying) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_stop_24),
                    "Stop Button",
                    modifier = Modifier.clickable(onClick = {
                        RadioPlayer.stop()
                    })
                )
//            } else {
//                Text(text = "Select a station to start playing the radio.")
//            }
        }
    }
}

@Composable
private fun RadioStationCard(station: RadioStation) {
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .wrapContentSize()
        .clickable(
            onClick = {
                RadioPlayer.setStation(station) // TODO use function passed to composable
            }
        )) {
        Image(
            painter = painterResource(station.img),
            contentDescription = "Logo of radio station",
            modifier = Modifier
                .size(40.dp)
                .border(1.5.dp, Color.Red)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = stringResource(id = station.name),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = station.description)
        }
    }
}