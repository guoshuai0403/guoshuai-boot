package com.gs.owner.generator.repository.impl;

import com.gs.common.service.repository.impl.BaseRepository;
import com.gs.owner.generator.bean.ConnectBean;
import com.gs.owner.generator.repository.ITestRepository;
import com.gs.owner.generator.repository.base.impl.BaseDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/30
 */
@Repository
public class TestRepository extends BaseDao implements ITestRepository {

    @Override
    public boolean test(ConnectBean connectBean) {

        try {
            Integer result = super.getSqlSession(connectBean).selectOne(super.getSqlId());
            Integer value = 1;
            if (value.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

}
