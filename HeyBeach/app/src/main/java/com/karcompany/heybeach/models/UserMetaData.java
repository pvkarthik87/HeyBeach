/**
 * Created by pvkarthik on 2017-02-24.
 *
 * This is POJO class corresponding to server response (JSON).
 */
package com.karcompany.heybeach.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserMetaData implements Parcelable
{

    private String id;
    private String email;
    public final static Creator<UserMetaData> CREATOR = new Creator<UserMetaData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public UserMetaData createFromParcel(Parcel in) {
            UserMetaData instance = new UserMetaData();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public UserMetaData[] newArray(int size) {
            return (new UserMetaData[size]);
        }

    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(email).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserMetaData)) {
            return false;
        }
        UserMetaData rhs = ((UserMetaData) other);
        return new EqualsBuilder().append(id, rhs.id).append(email, rhs.email).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(email);
    }

    public int describeContents() {
        return  0;
    }

}
