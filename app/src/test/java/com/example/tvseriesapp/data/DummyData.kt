package com.example.tvseriesapp.data

import com.example.tvseriesapp.data.remote.dto.EpisodeDto
import com.example.tvseriesapp.data.remote.dto.TvShowDto
import com.example.tvseriesapp.domain.model.Episode
import com.example.tvseriesapp.domain.model.TvShow
import com.example.tvseriesapp.domain.model.TvShowDetail

val tvShowDto = TvShowDto(
    id = 1,
    weight = 0,
    updated = 0,
    runtime = 0,
    averageRuntime = 0,
    status = "",
    type = "",
    name = "",
    ended = "",
    language = "",
    officialSite = "",
    premiered = "",
    summary = "",
    url = "",
    genres = emptyList(),
    rating = null,
    dvdCountry = null,
    externals = null,
    image = null,
    links = null,
    network = null,
    schedule = null,
    webChannel = null
)

val episodeDto = EpisodeDto(
    id = 0,
    url = "",
    name = "",
    summary = "",
    type = "",
    airDate = "",
    airStamp = "",
    airtime = "",
    links = null,
    image = null,
    rating = null,
    season = 0,
    number = 0,
    runtime = 0,
)

val tvShow = TvShow(
    id = 1,
    name = "",
    image = "",
    language = "",
    premiered = "",
    runtime = 0,
    rating = 0f
)

val episode = Episode(
    id = 1,
    name = "",
    image = "",
    summary = "",
    runtime = 0,
    number = 0,
    season = 0,
    rating = 0f
)

val tvShowDetail = TvShowDetail(
    id = 1,
    name = "",
    summary = "",
    image = "",
    language = "",
    time = "",
    genres = emptyList(),
    days = emptyList(),
    rating = 0f,
    runtime = 0
)