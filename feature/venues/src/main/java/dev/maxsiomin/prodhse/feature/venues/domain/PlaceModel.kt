package dev.maxsiomin.prodhse.feature.venues.domain

import android.os.Parcel
import android.os.Parcelable

data class PlaceModel(
    val name: String,
    val address: String,
    val photoUrl: String?,
    val id: String,
    val categories: String,
    val relatedPlaces: List<RelatedPlaceModel>? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(RelatedPlaceModel)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(photoUrl)
        parcel.writeString(id)
        parcel.writeString(categories)
        parcel.writeTypedList(relatedPlaces)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlaceModel> {
        override fun createFromParcel(parcel: Parcel): PlaceModel {
            return PlaceModel(parcel)
        }

        override fun newArray(size: Int): Array<PlaceModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class RelatedPlaceModel(
    val id: String,
    val name: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RelatedPlaceModel> {
        override fun createFromParcel(parcel: Parcel): RelatedPlaceModel {
            return RelatedPlaceModel(parcel)
        }

        override fun newArray(size: Int): Array<RelatedPlaceModel?> {
            return arrayOfNulls(size)
        }
    }
}
