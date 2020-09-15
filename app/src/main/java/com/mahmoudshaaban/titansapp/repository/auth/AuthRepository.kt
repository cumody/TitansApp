package com.mahmoudshaaban.titansapp.repository.auth

import com.mahmoudshaaban.titansapp.api.auth.OpenApiAuthService
import com.mahmoudshaaban.titansapp.persistence.AccountPropertiesDao
import com.mahmoudshaaban.titansapp.persistence.AuthTokenDao
import com.mahmoudshaaban.titansapp.session.SessionManger

class AuthRepository
constructor(
    val authTokenDao: AuthTokenDao,
    val sessionManger: SessionManger,
    val openApiAuthService: OpenApiAuthService,
    val accountPropertiesDao: AccountPropertiesDao


) {

}