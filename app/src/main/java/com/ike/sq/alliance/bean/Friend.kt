package com.ike.sq.alliance.bean


import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Generated
import org.greenrobot.greendao.annotation.Id
import org.greenrobot.greendao.annotation.Property

import java.io.Serializable

/**
 * @Author: [liguiqin]
 * @Date: [2017-07-21 11:37]
 * @Description: [ ]
 * @Version: [1.0.0]
 * @Copy: [com.bjike]
 */

@Entity
class Friend : Parcelable {
    @Id
    var id: String? = null
    /**
     * 归属人
     */
    @Property(nameInDb = "userId")
    var userId: String? = null
    /**
     * 朋友id
     */
    @Property(nameInDb = "friendId")
    var friendId: String? = null
    @Property(nameInDb = "nickname")
    var nickname: String? = null//:昵称,
    @Property(nameInDb = "username")
    var username: String? = null//:用户名
    @Property(nameInDb = "headPath")
    var headPath: String? = null//:头像
    @Property(nameInDb = "online")
    var online: String? = null//:是否在线

    /**
     * 朋友备注
     */

//    public FriendGroup getFriendGroup() {
//        return friendGroup;
//    }
//
//    public void setFriendGroup(FriendGroup friendGroup) {
//        this.friendGroup = friendGroup;
//    }

    @Property(nameInDb = "remark")
    var remark: String? = null
    /**
     * 朋友所在分组
     */
    @Property(nameInDb = "friendGroup")
    private var friendGroup: FriendGroup? = null

    @Property(nameInDb = "applyType")
    var applyType: String? = null
    @Property(nameInDb = "letters")
    var letters: String? = null

    @Generated(hash = 1546125465)
    constructor() {

    }

    @Generated(hash = 1463432601)
    constructor(id: String, nickname: String, username: String, headPath: String, online: String, remark: String, applyType: String) {
        this.id = id
        this.nickname = nickname
        this.username = username
        this.headPath = headPath
        this.online = online
        this.remark = remark
        this.applyType = applyType
    }

    @Generated(hash = 1663432601)
    constructor(userId: String, friendId: String, nickname: String, username: String, headPath: String, online: String, remark: String, applyType: String) {
        this.userId = userId
        this.friendId = friendId
        this.nickname = nickname
        this.username = username
        this.headPath = headPath
        this.online = online
        this.remark = remark
        this.applyType = applyType
    }

    @Generated(hash = 1763433501)
    constructor(userId: String, friendId: String, remark: String, friendGroup: FriendGroup, applyType: String) {
        this.userId = userId
        this.friendId = friendId
        this.remark = remark
        this.friendGroup = friendGroup
        this.applyType = applyType
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.id)
        dest.writeString(this.userId)
        dest.writeString(this.friendId)
        dest.writeString(this.nickname)
        dest.writeString(this.username)
        dest.writeString(this.headPath)
        dest.writeString(this.online)
        dest.writeString(this.remark)
        dest.writeParcelable(friendGroup, flags)
        dest.writeString(this.applyType)
        dest.writeString(letters)
    }

    protected constructor(parcel: Parcel) {
        this.id = parcel.readString()
        this.userId = parcel.readString()
        this.friendId = parcel.readString()
        this.nickname = parcel.readString()
        this.username = parcel.readString()
        this.headPath = parcel.readString()
        this.online = parcel.readString()

        this.remark = parcel.readString()
        this.friendGroup = parcel.readParcelable(FriendGroup::class.java.classLoader)
        this.applyType = parcel.readString()
        this.letters = parcel.readString()
    }


    companion object CREATOR : Parcelable.Creator<Friend> {
        override fun createFromParcel(parcel: Parcel): Friend {
            return Friend(parcel)
        }

        override fun newArray(size: Int): Array<Friend?> {
            return arrayOfNulls(size)
        }
    }

}
