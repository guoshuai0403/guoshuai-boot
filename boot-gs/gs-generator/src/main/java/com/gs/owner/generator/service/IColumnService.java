package com.gs.owner.generator.service;

import com.gs.owner.generator.bean.ColumnBean;
import com.gs.owner.generator.bean.TableBean;

import java.util.List;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/31
 */
public interface IColumnService {

    /**
     * 根据表名查询该表的所有列的信息
     * @param tableBean
     * @return
     * @throws Exception
     */
    List<ColumnBean> getByTableName(TableBean tableBean) throws Exception;
}
