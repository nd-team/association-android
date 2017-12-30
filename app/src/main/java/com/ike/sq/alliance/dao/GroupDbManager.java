package com.ike.sq.alliance.dao;


import com.ike.sq.alliance.bean.GroupBean;
import com.ike.sq.alliance.bean.Msg;
import com.ike.sq.alliance.dao.base.BaseManager;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Mao Jiqing on 2016/10/15.
 */

public class GroupDbManager extends BaseManager<GroupBean,String> {
    @Override
    public AbstractDao<GroupBean, String> getAbstractDao() {
        return daoSession.getGroupBeanDao();
    }
}
