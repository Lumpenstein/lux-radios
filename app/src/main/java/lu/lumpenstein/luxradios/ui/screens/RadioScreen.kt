package lu.lumpenstein.luxradios.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import lu.lumpenstein.luxradios.R
import lu.lumpenstein.luxradios.RadioPlayer
import lu.lumpenstein.luxradios.data.RadioStation
import lu.lumpenstein.luxradios.data.RadioStations

@Composable
fun RadioScreen(radioViewModel: RadioViewModel) {
    val mainUiState by radioViewModel.uiState.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.verticalScroll(rememberScrollState())

//        modifier = Modifier.scrollable(enabled = true, orientation = Orientation.Vertical, state = )
    ) {

        /* APP TITLE */
        Text(
            text = stringResource(id = R.string.app_name),
            Modifier.padding(vertical = 15.dp),
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W500,
            fontSize = 8.em,
            fontFamily = MaterialTheme.typography.h1.fontFamily,
        )

        /* MEDIA CONTROLS */
        when (mainUiState.playerState) {
            PlayerState.PLAYING -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_stop_24),
                    contentDescription = "Stop Button",
                    modifier = Modifier
                        .clickable(onClick = {
                            RadioPlayer.stop()
                        })
                        .size(48.dp),
                    tint = MaterialTheme.colors.onBackground,
                )
            }
            PlayerState.STOPPED -> {
                Text(
                    text = "Select a station to start the radio",
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 4.em,
                    fontFamily = MaterialTheme.typography.body1.fontFamily,
                )
            }
            PlayerState.BUFFERING -> {
                Text(text = "Buffering")
            }
            else -> {
                Text(text = "Unknown Player state")
            }
        }


        /* RADIO STATION LIST */
        LazyVerticalGrid(columns = GridCells.Fixed(2), Modifier.height(500.dp), content = {
            items(RadioStations.size) { index ->
                RadioStationCard(RadioStations[index])
            }
        })

        /* FOOTER */
        Text(
            text = stringResource(id = R.string.footer_text),
            color = MaterialTheme.colors.onBackground,
        )
    }
}

@Composable
private fun RadioStationCard(station: RadioStation) {
    // A card container using the 'surface' color from the theme by default
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .padding(all = 10.dp)
            .wrapContentSize()
            .clickable(onClick = {
                RadioPlayer.setStation(station) // TODO use function passed to composable
            }),
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painter = painterResource(station.img),
                contentDescription = "Logo of radio station",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(128.dp)
                    .padding(8.dp)
                    .clip(CircleShape))

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = stringResource(id = station.name), color = MaterialTheme.colors.onSurface)
        }
    }
}