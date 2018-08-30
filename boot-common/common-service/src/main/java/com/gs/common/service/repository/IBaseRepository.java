package com.gs.common.service.repository;


import com.gs.common.service.bean.pojo.BasePojo;
import com.gs.common.service.bean.pojo.PagePojo;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库基础操作（单表操作）接口定义
 * Created by SIMON
 * on 2017/9/29.
 */
public interface IBaseRepository<T extends BasePojo> extends Serializable {

    /**
     * 单条新增
     * @param pojo
     * @return 新增的对象
     */
    T doInser(T pojo) throws Exception;

    /**
     * 根据主键Id删除
     * @param id
     * @return 删除的条数
     */
    Integer doDeleteById(Object id) throws Exception;

    /**
     * 根据条件删除
     * @param pojo
     * @return 删除的条数
     */
    Integer doDelete(T pojo) throws Exception;

    /**
     * 单条修改
     * @param pojo
     * @return 修改的条数
     */
    Integer doUpdateById(T pojo) throws Exception;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T doSelectById(Object id) throws Exception;

    /**
     * 根据条件查找单条数据
     * @param pojo
     * @return
     */
    T doSelectOne(T pojo) throws Exception;

    /**
     * 根据条件查找多条数据
     * @param pojo
     * @return
     */
    List<T> doSelectList(T pojo) throws Exception;

    /**
     * 分页查找数据
     * @return
     */
    PagePojo<T> doSelectPage(T pojo) throws Exception;
}
