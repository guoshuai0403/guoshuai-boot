package com.gs.owner.generator.service.impl;

import com.gs.owner.generator.bean.ColumnBean;
import com.gs.owner.generator.bean.TableBean;
import com.gs.owner.generator.bean.enums.DataBaseType;
import com.gs.owner.generator.repository.IColumnRepository;
import com.gs.owner.generator.service.IColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/31
 */
@Service
public class ColumnService implements IColumnService {

    @Autowired
    private IColumnRepository columnRepository;

    @Override
    public List<ColumnBean> getByTableName(TableBean tableBean) throws Exception {
        // 参数验证
        Assert.notNull(tableBean, "ColumnService.getByTableName is error, because tableBean is null");
        Assert.notNull(tableBean.getConnectBean(), "ColumnService.getByTableName is error, because tableBean.ConnectBean is null");
        Assert.notNull(tableBean.getName(), "ColumnService.getByTableName is error, because tableBean.Name is null");
        // 根据不同类型的数据库执行不同的sql语句
        DataBaseType type = tableBean.getConnectBean().getType();
        switch (type){
            case MYSQL:
                // 执行mysql的查询语句
                return columnRepository.mysqlGetAllByTableName(tableBean);
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
