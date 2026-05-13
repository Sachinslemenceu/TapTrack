package com.slemenceu.taptrack.features.connection.data.utils

import java.nio.ByteBuffer

fun intToByte(value: Int): ByteArray {
    return ByteBuffer.allocate(4).putInt(value).array()
}