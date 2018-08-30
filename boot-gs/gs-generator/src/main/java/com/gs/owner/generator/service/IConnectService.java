package com.gs.owner.generator.service;

import com.gs.owner.generator.bean.ConnectBean;

/**
 * Created by guoshuai on 2018/8/24 0024.
 */
public interface IConnectService {

    /**
     * 测试数据库连接
     * @param connectBean
     * @return
     */
    boolean test(ConnectBean connectBean) throws Exception;

}
