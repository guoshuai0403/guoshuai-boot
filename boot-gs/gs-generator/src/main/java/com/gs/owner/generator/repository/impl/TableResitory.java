package com.gs.owner.generator.repository.impl;

import com.gs.common.service.bean.pojo.PagePojo;
import com.gs.owner.generator.bean.TableBean;
import com.gs.owner.generator.repository.ITableResitory;
import com.gs.owner.generator.repository.base.impl.BaseDao;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

/**
 * Created by guoshuai on 2018/8/30 0030.
 */
@Repository
public class TableResitory extends BaseDao implements ITableResitory {


    @Override
    public PagePojo<TableBean> getByPage(TableBean tableBean) throws Exception {
        try {
            PagePojo<TableBean> pagePojo = new PagePojo<TableBean>();
            // 查询当前页的数据
            List<TableBean> tableBeans = super.getSqlSession(tableBean.getConnectBean())
                    .selectList(super.getSqlId(), tableBean);
            // 设置当前页的数据
            pagePojo.setData(tableBeans);
            // 设置当前页码
            pagePojo.setPageIndex(tableBean.getPageIndex());
            // 设置当前页面大小
            pagePojo.setPageSize(tableBean.getPageSize());
            // 设置符合条件的总条数
            pagePojo.setRecord(tableBean.getRecord());
            return pagePojo;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
