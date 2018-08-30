package com.gs.owner.generator.repository;

import com.gs.owner.generator.bean.ConnectBean;
import org.springframework.stereotype.Repository;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/30
 */
public interface ITestRepository {

    /**
     * 测试数据库连接
     * @param connectBean
     * @return
     */
    boolean test(ConnectBean connectBean) throws Exception;
}
