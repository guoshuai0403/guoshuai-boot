package com.gs.owner.generator.repository;

import com.gs.common.service.bean.pojo.PagePojo;
import com.gs.owner.generator.bean.TableBean;

/**
 * 表格查询接口定义
 * Created by guoshuai on 2018/8/30 0030.
 */
public interface ITableRepository {

    /**
     * 分页查询数据库表格 - MySQL数据库
     * @param tableBean
     * @return
     * @throws Exception
     */
    PagePojo<TableBean> mysqlGetByPage(TableBean tableBean) throws Exception;
}
