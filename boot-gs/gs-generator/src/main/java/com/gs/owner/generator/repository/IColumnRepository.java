package com.gs.owner.generator.repository;

import com.gs.owner.generator.bean.ColumnBean;
import com.gs.owner.generator.bean.TableBean;

import java.util.List;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/31
 */
public interface IColumnRepository {

    /**
     * 根据表名查询所有列信息 - mysql
     * @param tableBean
     * @return
     * @throws Exception
     */
    List<ColumnBean> mysqlGetAllByTableName(TableBean tableBean) throws Exception;
}
