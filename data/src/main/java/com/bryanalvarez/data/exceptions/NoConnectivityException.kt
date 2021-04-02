package com.bryanalvarez.data.exceptions

import java.io.IOException

/**
 * Exception to shows the user there's no internet connection
 * @param message message to be displayed
 */
class NoConnectivityException(message: String = ""): IOException(message)