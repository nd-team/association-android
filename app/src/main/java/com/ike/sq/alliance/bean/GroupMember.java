package com.ike.sq.alliance.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.ike.sq.alliance.dao.DaoSession;
import com.ike.sq.alliance.dao.GroupBeanDao;
import com.ike.sq.alliance.dao.GroupMemberDao;


/**
 * 群成员
 *
 * @Author: [liguiqin]
 * @Date: [2017-07-19 15:11]
 * @Description: [ ]
 * @Version: [1.0.0]
 * @Copy: [com.bjike]
 */
@Entity
public class GroupMember implements Serializable {
    static final long serialVersionUID = 42L;
    @Id
    private String id;
    /**
     * 归属群
     */
    private String groupId;
    @ToOne(joinProperty = "groupId")
    private GroupBean groupBean;
    /**
     * 群成员id
     */
    @Property(nameInDb = "userId")
    private String userId;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 777902840)
    private transient GroupMemberDao myDao;

    @Generated(hash = 1806670034)
    public GroupMember(String id, String groupId, String userId) {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
    }

    @Generated(hash = 1668463032)
    public GroupMember() {
    }

    @Generated(hash = 916692355)
    private transient String groupBean__resolvedKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    @Keep
    public GroupBean getGroupBean() {
        return groupBean;
    }

    @Keep
    public void setGroupBean(GroupBean groupBean) {
        this.groupBean = groupBean;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1404684941)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGroupMemberDao() : null;
    }
}
