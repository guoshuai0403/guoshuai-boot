package com.gs.common.service.repository.impl;

import com.gs.common.service.bean.pojo.BasePojo;
import com.gs.common.service.bean.pojo.PagePojo;
import com.gs.common.service.repository.IBaseRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/30
 */
public class BaseRepository<T extends BasePojo>/* extends SqlSessionDaoSupport*/ implements IBaseRepository<T> {

//    @Override
//    @Autowired
//    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
//        super.setSqlSessionFactory(sqlSessionFactory);
//    }
//
//    @Override
//    @Autowired
//    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
//        super.setSqlSessionTemplate(sqlSessionTemplate);
//    }

    /**
     * 单条新增
     *
     * @param pojo
     * @return 新增的对象
     */
    public T doInser(T pojo) throws Exception {

        Assert.notNull(pojo, "doInsert is error, because pojo is null");

        Assert.notNull(pojo.getId(), "doInsert is error, because pojo.getId() is null");

        this.getSqlSession().insert(this.getSqlId(),pojo);

        return pojo;
    }

    /**
     * 根据主键Id删除
     *
     * @param id
     * @return 删除的条数
     */
    public Integer doDeleteById(Object id) throws Exception {

        Assert.notNull(id,"doDeleteById is error,because id is null");

        return this.getSqlSession().delete(this.getSqlId(), id);
    }

    /**
     * 根据条件删除
     *
     * @param pojo
     * @return 删除的条数
     */
    public Integer doDelete(T pojo) throws Exception {

        Assert.notNull(pojo,"doDelete is error,because pojo ni null");

        return this.getSqlSession().delete(this.getSqlId(), pojo);
    }

    /**
     * 单条修改
     *
     * @param pojo
     * @return 修改的条数
     */
    public Integer doUpdateById(T pojo) throws Exception {

        Assert.notNull(pojo, "doUpdateById is error, because pojo is null");
        Assert.notNull(pojo.getId(), "doUpdateById is error, because pojo.getId() is null");

        return this.getSqlSession().update(this.getSqlId(), pojo);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public T doSelectById(Object id) throws Exception {

        Assert.notNull(id,"doSelectById is error,because id is null");

        return this.getSqlSession().selectOne(this.getSqlId(),id);
    }

    /**
     * 根据条件查找单条数据
     *
     * @param pojo
     * @return
     */
    public T doSelectOne(T pojo) throws Exception {

        Assert.notNull(pojo, "doSelectOne is error, because pojo is null");

        List<T> list = this.getSqlSession().selectList(this.getSqlId(), pojo);

        // 查询结果为空时返回null
        if (CollectionUtils.isEmpty(list)) {

            return null;
        }

        // 查询出多个的时候抛出异常，这里没用mybatis的是为了方便用户读取异常信息
        if (list.size() > 1) {

            throw new IllegalArgumentException("结果异常，请检查参数...");
        }

        return list.get(0);
    }

    /**
     * 根据条件查找多条数据
     *
     * @param pojo
     * @return
     */
    public List<T> doSelectList(T pojo) throws Exception {
        return this.getSqlSession().selectList(this.getSqlId(), pojo);
    }

    /**
     * 分页查找数据
     *
     * @param pojo
     * @return
     */
    public PagePojo<T> doSelectPage(T pojo) throws Exception {

        if (pojo instanceof PagePojo) {

            List<T> objects = this.getSqlSession().selectList(this.getSqlId(), pojo);

            PagePojo<T> page = new PagePojo<T>();

            page.setPageIndex(pojo.getPageIndex());
            page.setPageSize(pojo.getPageSize());
            page.setRecord(pojo.getRecord());

            if (objects == null) {

                objects = new ArrayList<>();
            }

            page.setData(objects);

            return page;

        }

        return null;
    }

    /**
     * 获取sqlId
     * @return
     */
    protected String getSqlId() {
        //决定了mapper.xml namespace中的值
        String name = this.getClass().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(".").append(Thread.currentThread().getStackTrace()[2].getMethodName());
        return sb.toString();
    }

    public SqlSession getSqlSession(){
        return null;
    }
}
