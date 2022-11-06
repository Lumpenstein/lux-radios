package lu.lumpenstein.luxradios.data

import lu.lumpenstein.luxradios.R

data class RadioStation(
    val url: String, val name: Int, val description: String, val order: Int, val img: Int
)

/**
 * A list of radio stations for the application.
 */
val RadioStations: List<RadioStation> = listOf(
    RadioStation(
        name = R.string.station_title_rtl,
        description = "RTL Radio Lëtzebuerg",
        url = "https://shoutcast.rtl.lu/rtl",
        order = 1,
        img = R.drawable.logo_rtl_luxembourg
    ), RadioStation(
        name = R.string.station_title_ara,
        description = "ARA is the Community radio of Luxembourg since 1992, and it plays a social key role for all the citizens that live in the country, by broadcasting its program in more than 10 different languages, and bringing local communities together.",
        url = "https://www.ara.lu/live/",
        order = 2,
        img = R.drawable.logo_ara
    ), RadioStation(
        name = R.string.station_title_eldo,
        description = "",
        url = "https://stream.rtl.lu/live/hls/radio/eldo",
        order = 3,
        img = R.drawable.logo_eldo
    ), RadioStation(
        name = R.string.station_title_essentiel,
        description = "",
        url = "https://lessentielradio.ice.infomaniak.ch/lessentielradio-128.mp3",
        order = 4,
        img = R.drawable.logo_essentiel
    ), RadioStation(
        name = R.string.station_title_100_komma_7,
        description = "",
        url = "https://100komma7--di--nacs-ice-01--02--cdn.cast.addradio.de/100komma7/live/mp3/128/stream.mp3",
        order = 5,
        img = R.drawable.logo_100_komma_7
    ), RadioStation(
        name = R.string.station_title_rtl_germany,
        description = "Deutschlands Hitradio",
        url = "https://rtlberlin.streamabc.net/rtlb-rtldenational-mp3-128-2770113",
        order = 6,
        img = R.drawable.logo_rtl_de
    )
)