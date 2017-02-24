/**
 * Created by pvkarthik on 2017-02-20.
 *
 * This is POJO class corresponding to server response (JSON).
 */
package com.karcompany.heybeach.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BeachMetaData implements Parcelable
{

    private String id;
    private String name;
    private String url;
    private String width;
    private String height;
    public final static Creator<BeachMetaData> CREATOR = new Creator<BeachMetaData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BeachMetaData createFromParcel(Parcel in) {
            BeachMetaData instance = new BeachMetaData();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.url = ((String) in.readValue((String.class.getClassLoader())));
            instance.width = ((String) in.readValue((String.class.getClassLoader())));
            instance.height = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public BeachMetaData[] newArray(int size) {
            return (new BeachMetaData[size]);
        }

    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String collectionName) {
        this.height = collectionName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(url).append(width).append(height).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof BeachMetaData)) {
            return false;
        }
        BeachMetaData rhs = ((BeachMetaData) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(url, rhs.url).append(width, rhs.width).append(height, rhs.height).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(url);
        dest.writeValue(width);
        dest.writeValue(height);
    }

    public int describeContents() {
        return  0;
    }

}
