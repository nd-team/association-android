package com.ike.sq.alliance.bean;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;


/**
 * 朋友分组
 *
 * @Author: [liguiqin]
 * @Date: [2017-07-19 15:07]
 * @Description: [ ]
 * @Version: [1.0.0]
 * @Copy: [com.bjike]
 */
@Entity
public class FriendGroup implements Parcelable {
    @Id
    private String id;
    @Property(nameInDb = "name")
    private String name;//分组名
    @Property(nameInDb = "userId")
    private String userId;//归属人

    public FriendGroup(String name,String userId){
        this.name=name;
        this.userId=userId;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(userId);
    }

    protected FriendGroup(Parcel parcel) {
        this.name = parcel.readString();
        this.userId = parcel.readString();
    }

    @Generated(hash = 1099616339)
    public FriendGroup(String id, String name, String userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    @Generated(hash = 1824386209)
    public FriendGroup() {
    }

    public static final Creator<FriendGroup> CREATOR = new Creator<FriendGroup>() {
        @Override
        public FriendGroup createFromParcel(Parcel in) {
            return new FriendGroup(in);
        }

        @Override
        public FriendGroup[] newArray(int size) {
            return new FriendGroup[size];
        }
    };
    @Override
    public String toString() {
        return "FriendGroup{" +
                "name='" + name + '\'' +
                ", userId='" + userId + '\''+
                '}';
    }

}
