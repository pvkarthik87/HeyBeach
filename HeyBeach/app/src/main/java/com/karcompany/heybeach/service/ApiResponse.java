package com.karcompany.heybeach.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.karcompany.heybeach.models.BeachListApiResponse;
import com.karcompany.heybeach.networking.ApiType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by pvkarthik on 2017-02-23.
 */

public class ApiResponse implements Parcelable {

	public static final int SUCCESS = 0;
	public static final int ERROR = -1;

	private ApiType apiType;
	private int responseCode;
	private Object responseObj;

	public final static Creator<ApiResponse> CREATOR = new Creator<ApiResponse>() {


		@SuppressWarnings({
				"unchecked"
		})
		public ApiResponse createFromParcel(Parcel in) {
			ApiResponse instance = new ApiResponse();
			instance.apiType = in.readParcelable(ApiType.class.getClassLoader());
			instance.responseCode = ((Integer) in.readValue((Integer.class.getClassLoader())));
			switch (instance.apiType) {
				case FETCH_BEACHES: {
					instance.responseObj = in.readParcelable(BeachListApiResponse.class.getClassLoader());
				}
				break;
			}
			return instance;
		}

		public ApiResponse[] newArray(int size) {
			return (new ApiResponse[size]);
		}

	};

	public ApiType getApiType() {
		return apiType;
	}

	public void setApiType(ApiType apiType) {
		this.apiType = apiType;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public Object getResponse() {
		return responseObj;
	}

	public void setResponse(Object response) {
		responseObj = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(responseCode).append(responseObj).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof ApiResponse)) {
			return false;
		}
		ApiResponse rhs = ((ApiResponse) other);
		return new EqualsBuilder().append(responseCode, rhs.responseCode).append(responseObj, rhs.responseObj).isEquals();
	}

	public void writeToParcel(Parcel dest, int flags) {
		apiType.writeToParcel(dest, flags);
		dest.writeValue(responseCode);
		switch (apiType) {
			case FETCH_BEACHES: {
				((BeachListApiResponse)responseObj).writeToParcel(dest, flags);
			}
			break;
		}
	}

	public int describeContents() {
		return  0;
	}
}
