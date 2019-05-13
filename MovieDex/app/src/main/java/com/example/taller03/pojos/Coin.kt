package com.example.taller03.pojos

import android.os.Parcel
import android.os.Parcelable

data class Coin(
    val name: String = "N/A",
    val country: String = "N/A",
    val value: Int = 0,
    val value_us: Int = 0,
    val year: Int = 9999,
    val review: String = "N/A",
    val isAvailable: Int = 0,
    val img: String = "N/A",
    val _id: String = "N/A",
    val _v: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeInt(value)
        parcel.writeInt(year)
        parcel.writeInt(value_us)
        parcel.writeString(review)
        parcel.writeInt(isAvailable)
        parcel.writeString(img)
        parcel.writeString(_id)
        parcel.writeInt(_v)

    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Coin> {
            override fun createFromParcel(parcel: Parcel): Coin = Coin(parcel)
            override fun newArray(size: Int): Array<Coin?> = arrayOfNulls(size)
        }
    }

}
