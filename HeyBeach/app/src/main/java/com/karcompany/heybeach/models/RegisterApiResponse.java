package com.karcompany.heybeach.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;

/**
 * Created by pvkarthik on 2017-02-23.
 */

public class RegisterApiResponse implements Parcelable {

	private String mAuthToken;

	public final static Creator<RegisterApiResponse> CREATOR = new Creator<RegisterApiResponse>() {


		@SuppressWarnings({
				"unchecked"
		})
		public RegisterApiResponse createFromParcel(Parcel in) {
			RegisterApiResponse instance = new RegisterApiResponse();
			instance.mAuthToken = in.readString();
			return instance;
		}

		public RegisterApiResponse[] newArray(int size) {
			return (new RegisterApiResponse[size]);
		}

	};

	public String getAuthToken() {
		return mAuthToken;
	}

	public void setAuthToken(String authToken) {
		mAuthToken = authToken;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(mAuthToken).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof RegisterApiResponse)) {
			return false;
		}
		RegisterApiResponse rhs = ((RegisterApiResponse) other);
		return new EqualsBuilder().append(mAuthToken, rhs.mAuthToken).isEquals();
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mAuthToken);
	}

	public int describeContents() {
		return  0;
	}

}
