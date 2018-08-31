package com.gs.owner.generator.repository.impl;

import com.gs.owner.generator.bean.ColumnBean;
import com.gs.owner.generator.bean.TableBean;
import com.gs.owner.generator.repository.IColumnRepository;
import com.gs.owner.generator.repository.base.impl.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/31
 */
@Repository
public class ColumnRepository extends BaseDao implements IColumnRepository {

    @Override
    public List<ColumnBean> mysqlGetAllByTableName(TableBean tableBean) throws Exception {
        List<ColumnBean> columnBeans = super.getSqlSession(tableBean.getConnectBean()).selectList(super.getSqlId(), tableBean);
        return columnBeans;
    }
}
