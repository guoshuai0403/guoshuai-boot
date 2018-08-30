package com.gs.owner.generator.service;

import com.gs.common.service.bean.pojo.PagePojo;
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
     * 根据数据库连接分页查询表格
     * @param tableBean
     * @return
     * @throws Exception
     */
    PagePojo<TableBean> getAll(TableBean tableBean) throws Exception;
}
