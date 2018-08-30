package com.gs.owner.generator.service.impl;

import com.gs.common.service.bean.pojo.PagePojo;
import com.gs.owner.generator.bean.ConnectBean;
import com.gs.owner.generator.bean.TableBean;
import com.gs.owner.generator.repository.ITableResitory;
import com.gs.owner.generator.service.ITableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/30
 */
@Service
public class TableService implements ITableService {

    @Autowired
    private ITableResitory tableResitory;

    @Override
    public PagePojo<TableBean> getAll(TableBean tableBean) throws Exception {
        return tableResitory.getByPage(tableBean);
    }
}
