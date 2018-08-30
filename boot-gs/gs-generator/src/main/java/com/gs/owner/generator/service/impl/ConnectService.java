package com.gs.owner.generator.service.impl;

import com.gs.owner.generator.bean.ConnectBean;
import com.gs.owner.generator.repository.ITestRepository;
import com.gs.owner.generator.service.IConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/30
 */
@Service
public class ConnectService implements IConnectService{

    @Autowired
    private ITestRepository testRepository;

    @Override
    public boolean test(ConnectBean connectBean) throws Exception {

        Assert.notNull(connectBean.getType(), "database type is null");
        Assert.notNull(connectBean.getPosition(), "database position is null");
        Assert.notNull(connectBean.getIp(), "database ip is null");
        Assert.notNull(connectBean.getPort(), "database port is null");
        Assert.notNull(connectBean.getDbName(), "database dbName is null");
        Assert.notNull(connectBean.getUsername(), "database username is null");
        Assert.notNull(connectBean.getPassword(), "database password is null");

        return testRepository.test(connectBean);
    }
}
