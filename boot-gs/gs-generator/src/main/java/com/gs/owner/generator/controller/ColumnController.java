package com.gs.owner.generator.controller;

import com.gs.common.controller.BaseController;
import com.gs.common.service.bean.response.bean.ResponseMessage;
import com.gs.common.util.format.date.DateUtil;
import com.gs.owner.generator.bean.ColumnBean;
import com.gs.owner.generator.bean.ConnectBean;
import com.gs.owner.generator.bean.TableBean;
import com.gs.owner.generator.service.IColumnService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/31
 */
@Controller
@RequestMapping(value = "/column")
public class ColumnController extends BaseController {

    @Autowired
    private IColumnService columnService;

    /**
     * 根据表名查询该表的列信息
     * @param tableName
     * @return
     */
    @RequestMapping(value = "/getByTableName")
    @ResponseBody
//    public ResponseMessage getByTableName(String tableName, HttpSession session) throws Exception {
    public List<ColumnBean> getByTableName(String tableName, HttpSession session) throws Exception {
        // 参数验证
        Assert.notNull(tableName, "/column/getByTableName is refused, because tableName is null");
        // 执行业务
        TableBean tableBean = new TableBean();
        tableBean.setConnectBean((ConnectBean)session.getAttribute("connectBean"));
        tableBean.setName(tableName);
        List<ColumnBean> columnBeans = columnService.getByTableName(tableBean);
        for (ColumnBean columnBean: columnBeans) {
            columnBean.setCreateDate(DateUtil.currentTimestamp());
        }
        // 响应客户端
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setObj(columnBeans);
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("aaa", 111);
//        map.put("bbb", "222");
//        map.put("ccc", null);
//        map.put("ddd", new ArrayList<String>());
//        map.put("eee", 111);
//        responseMessage.setObj(map);
        return columnBeans;
//        return responseMessage;
    }
}
