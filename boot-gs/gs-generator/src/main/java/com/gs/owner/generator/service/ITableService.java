package com.gs.owner.generator.service;

import com.gs.owner.generator.bean.ConnectBean;
import com.gs.owner.generator.bean.TableBean;

import java.util.List;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/30
 */
public interface ITableService {

    /**
     * 根据数据库连接查询所有表格
     * @param connectBean
     * @return
     * @throws Exception
     */
    List<TableBean> getAll(ConnectBean connectBean) throws Exception;
}
