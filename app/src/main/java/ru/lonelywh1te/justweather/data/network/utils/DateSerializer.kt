package ru.lonelywh1te.justweather.data.network.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Date

internal object DateSerializer: KSerializer<Date> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Date", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeInt().toLong() * 1000)
    }

    override fun serialize(encoder: Encoder, value: Date) {
        return encoder.encodeInt((value.time / 1000).toInt())
    }
}