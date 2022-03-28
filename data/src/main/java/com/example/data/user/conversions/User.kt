package com.example.data.user.conversions

import com.example.data.user.model.response.UserDetailsResponse
import com.example.data.user.model.response.UserResponse
import com.example.domain.user.model.User
import com.example.domain.user.model.UserDetails

fun UserResponse.toDomain() = User(
    avatarUrl,
    eventsUrl,
    followersUrl,
    followingUrl,
    gistsUrl,
    gravatarId,
    htmlUrl,
    id,
    login,
    nodeId,
    organizationsUrl,
    receivedEventsUrl,
    reposUrl,
    siteAdmin,
    starredUrl,
    subscriptionsUrl,
    type,
    url
)

fun UserDetailsResponse.toDomain() = UserDetails(
    avatarUrl,
    bio,
    blog,
    company,
    createdAt,
    email,
    eventsUrl,
    followers,
    followersUrl,
    following,
    followingUrl,
    gistsUrl,
    gravatarId,
    hireable,
    htmlUrl,
    id,
    location,
    login,
    name,
    nodeId,
    organizationsUrl,
    publicGists,
    publicRepos,
    receivedEventsUrl,
    reposUrl,
    siteAdmin,
    starredUrl,
    subscriptionsUrl,
    twitterUsername,
    type,
    updatedAt,
    url
)