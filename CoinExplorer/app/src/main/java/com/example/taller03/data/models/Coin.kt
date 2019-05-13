package com.example.taller03.data.models

import android.os.Parcel
import android.os.Parcelable

data class Coin(
    val _id: String = "N/A",
    val name: String = "N/A",
    val country: String = "N/A",
    val value: Float = 0.toFloat(),
    val value_us: Float = 0.toFloat(),
    val year: Int = 9999,
    val review: String = "N/A",
    val isAvailable: Boolean = false,
    val img: String = "N/A",
    val _v: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readString(),
        (parcel.readInt() == 1),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeFloat(value)
        parcel.writeFloat(value_us)
        parcel.writeInt(year)
        parcel.writeString(review)
        parcel.writeInt(if (isAvailable) 1 else 0)
        parcel.writeString(img)
        parcel.writeInt(_v)

    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Coin> {
            override fun createFromParcel(parcel: Parcel): Coin =
                Coin(parcel)

            override fun newArray(size: Int): Array<Coin?> = arrayOfNulls(size)
        }
    }

}
