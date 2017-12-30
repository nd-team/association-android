package com.ike.sq.alliance.dao;


import com.ike.sq.alliance.bean.Msg;
import com.ike.sq.alliance.dao.base.BaseManager;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Mao Jiqing on 2016/10/15.
 */

public class ChatDbManager extends BaseManager<Msg,Long> {
    @Override
    public AbstractDao<Msg, Long> getAbstractDao() {
        return daoSession.getMsgDao();
    }
}
