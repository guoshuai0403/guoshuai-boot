package com.gs.owner.generator.service.impl;

import com.gs.common.service.bean.pojo.PagePojo;
import com.gs.owner.generator.bean.TableBean;
import com.gs.owner.generator.bean.enums.DataBaseType;
import com.gs.owner.generator.repository.ITableRepository;
import com.gs.owner.generator.service.ITableService;
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
public class TableService implements ITableService {

    @Autowired
    private ITableRepository tableResitory;

    @Override
    public PagePojo<TableBean> getAll(TableBean tableBean) throws Exception {
        // 参数验证
        Assert.notNull(tableBean, "ColumnService.getByTableName is error, because tableBean is null");
        Assert.notNull(tableBean.getConnectBean(), "ColumnService.getByTableName is error, because tableBean.ConnectBean is null");

        // 根据不同类型的数据库执行不同的sql语句
        DataBaseType type = tableBean.getConnectBean().getType();
        switch (type){
            case MYSQL:
                // 执行mysql的查询语句
                return tableResitory.mysqlGetByPage(tableBean);
            case ORACLE:
                // 执行oracle的查询语句
                break;
            case SQL_SERVER:
                // 执行sql-server的查询语句
                break;
        }
        return null;
    }
}
