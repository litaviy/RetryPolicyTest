package com.example.klitaviy.retrypolicytest

/**
 * Created by klitaviy on 9/7/18-5:03 PM.
 */
open class IngenicoException(message: String = "", reason: Throwable? = null) : Exception(message, reason)

// Reload Base Chain
open class NonFatalException(message: String = "", reason: Throwable? = null) : IngenicoException(message, reason)

// Login
class LoginFailedException(message: String = "") : IngenicoException(message)

class BadCredentialsException(message: String = "") : IngenicoException(message)

class NetworkException(message: String = "") : IngenicoException(message)

// Search
class DeviceNotFoundException(message: String = "") : NonFatalException(message)

// Device Registration
class DeviceRegistrationFailedException(message: String = "") : IngenicoException(message)

// Setup
class DeviceSetupException(message: String = "") : NonFatalException(message)
