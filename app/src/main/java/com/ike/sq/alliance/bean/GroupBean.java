package com.ike.sq.alliance.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 聊天群
 *
 * @Author: [liguiqin]
 * @Date: [2017-07-19 15:04]
 * @Description: [ ]
 * @Version: [1.0.0]
 * @Copy: [com.bjike]
 */
@Entity
public class GroupBean implements Serializable {
    static final long serialVersionUID = 42L;
    @Id
    private String id;
    @Property(nameInDb = "name")
    private String name;//聊天室名称
    @Property(nameInDb = "userId")
    private String userId;//创建人
    @Property(nameInDb = "headPath")
    private String headPath;//群头像
    @Property(nameInDb = "description")
    private String description;//群描述


    @Generated(hash = 1146445523)
    public GroupBean(String id, String name, String userId, String headPath,
            String description) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.headPath = headPath;
        this.description = description;
    }

    @Generated(hash = 405578774)
    public GroupBean() {
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

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
