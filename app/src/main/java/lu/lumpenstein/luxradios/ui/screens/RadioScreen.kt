package lu.lumpenstein.luxradios.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import lu.lumpenstein.luxradios.R
import lu.lumpenstein.luxradios.RadioPlayer
import lu.lumpenstein.luxradios.data.RadioStation
import lu.lumpenstein.luxradios.data.RadioStations

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RadioScreen(/*viewModel: RadioViewModel = viewModel()*/) {
//    var isPlaying = remember { mutableStateOf(RadioPlayer.getMediaPlayer()?.isPlaying) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /* APP TITLE */
        Text(
            text = stringResource(id = R.string.app_name),
            Modifier
                .padding(vertical = 15.dp),
            fontWeight = FontWeight.W500, fontSize = 8.em
        )

        /* RADIO STATION LIST */
        LazyVerticalGrid(cells = GridCells.Fixed(2)) {
            items(RadioStations) { station ->
                RadioStationCard(station)
            }
        }

        /* MEDIA CONTROLS */
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            if (true) { // TODO uiState.isPlaying
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_stop_24),
                "Stop Button",

                modifier = Modifier.clickable(onClick = {
                    RadioPlayer.stop()
                }).size(48.dp)
            )
            } else {
                Text(text = "Select a station to start the radio")
            }
        }
    }
}

@Composable
private fun RadioStationCard(station: RadioStation) {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .padding(all = 10.dp)
            .wrapContentSize()
            .clickable(onClick = {
                RadioPlayer.setStation(station) // TODO use function passed to composable
            })
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(station.img),
                contentDescription = "Logo of radio station",
                modifier = Modifier
//                    .fillMaxSize()
                    .size(128.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = station.name),
            )
        }
    }
}