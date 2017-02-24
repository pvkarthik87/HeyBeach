package com.karcompany.heybeach.networking;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pvkarthik on 2017-02-23.
 */

public enum ApiType implements Parcelable {
	FETCH_BEACHES,
	REGISTER,
	LOGIN,
	LOGOUT;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		dest.writeInt(ordinal());
	}

	public static final Creator<ApiType> CREATOR = new Creator<ApiType>() {
		@Override
		public ApiType createFromParcel(final Parcel source) {
			return ApiType.values()[source.readInt()];
		}

		@Override
		public ApiType[] newArray(final int size) {
			return new ApiType[size];
		}
	};
}
