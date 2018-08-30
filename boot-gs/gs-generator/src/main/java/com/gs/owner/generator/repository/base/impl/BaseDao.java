package com.gs.owner.generator.repository.base.impl;

import com.gs.owner.generator.bean.ConnectBean;
import com.gs.owner.generator.repository.base.IBaseDao;
import com.gs.owner.generator.repository.impl.TableResitory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/30
 */
public class BaseDao implements IBaseDao {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取sqlSession
     * @return
     */
    protected SqlSession getSqlSession(ConnectBean connectBean) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver", connectBean.getType().getDriverClassName());
        properties.setProperty("jdbc.url", connectBean.getUrl());
        properties.setProperty("jdbc.username", connectBean.getUsername());
        properties.setProperty("jdbc.password", connectBean.getPassword());

        String resource = "mybatis/mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, properties);
        return sqlSessionFactory.openSession();
    }

    /**
     * 获取sqlId
     * @return
     */
    protected String getSqlId() {
        //决定了mapper.xml namespace中的值
        String name = this.getClass().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(".").append(Thread.currentThread().getStackTrace()[2].getMethodName());
        return sb.toString();
    }
}
