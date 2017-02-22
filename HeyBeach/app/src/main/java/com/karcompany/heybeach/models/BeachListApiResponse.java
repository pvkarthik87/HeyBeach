package com.karcompany.heybeach.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;

/**
 * Created by pvkarthik on 2017-02-22.
 */

public class BeachListApiResponse implements Parcelable {

	private int mPageNo;
	private ArrayList<BeachMetaData> mBeachList;

	public final static Creator<BeachListApiResponse> CREATOR = new Creator<BeachListApiResponse>() {


		@SuppressWarnings({
				"unchecked"
		})
		public BeachListApiResponse createFromParcel(Parcel in) {
			BeachListApiResponse instance = new BeachListApiResponse();
			instance.mPageNo = ((Integer) in.readValue((Integer.class.getClassLoader())));
			in.readList(instance.mBeachList, (BeachMetaData.class.getClassLoader()));
			return instance;
		}

		public BeachListApiResponse[] newArray(int size) {
			return (new BeachListApiResponse[size]);
		}

	};

	public int getPageNo() {
		return mPageNo;
	}

	public void setPageNo(int pageNo) {
		mPageNo = pageNo;
	}

	public ArrayList<BeachMetaData> getBeachList() {
		return mBeachList;
	}

	public void setBeachList(ArrayList<BeachMetaData> beachList) {
		mBeachList = beachList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(mPageNo).append(mBeachList).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof BeachListApiResponse)) {
			return false;
		}
		BeachListApiResponse rhs = ((BeachListApiResponse) other);
		return new EqualsBuilder().append(mPageNo, rhs.mPageNo).append(mBeachList, rhs.mBeachList).isEquals();
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(mPageNo);
		dest.writeList(mBeachList);
	}

	public int describeContents() {
		return  0;
	}

}
