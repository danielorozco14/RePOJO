package com.deushdezt.laboratorio4.pojos

import android.os.Parcel
import android.os.Parcelable

data class Movie (
        val name:String = "N/A",
        val country:String = "N/A",
        val value: String = "N/A",
        val value_us:String = "N/A",
        val year:String = "N/A",
        val review:String = "N/A",
        val isAvailable:String = "N/A",
        val img:String = "N/A",
        val _id:String = "N/A",
        val _v:String = "N/A"
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            name = parcel.readString(),
            country = parcel.readString(),
            value = parcel.readString(),
            value_us = parcel.readString(),
            year = parcel.readString(),
            review = parcel.readString(),
            isAvailable = parcel.readString(),
            img = parcel.readString(),
            _id= parcel.readString(),
            __v = parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeString(value)
        parcel.writeString(year)
        parcel.writeString(value_us)
        parcel.writeString(review)
        parcel.writeString(isAvailable)
        parcel.writeString(img)
        parcel.writeString(_id)
        parcel.writeString(_v)

    }

    override fun describeContents() = 0

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(parcel: Parcel): Movie = Movie(parcel)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }

}
