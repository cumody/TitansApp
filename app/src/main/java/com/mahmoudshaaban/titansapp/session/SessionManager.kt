package com.mahmoudshaaban.titansapp.session

import com.mahmoudshaaban.titansapp.persistence.AuthTokenDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager
@Inject
constructor(
    authTokenDao: AuthTokenDao
) {

}