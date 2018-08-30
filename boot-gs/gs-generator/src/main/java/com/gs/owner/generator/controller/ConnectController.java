package com.gs.owner.generator.controller;

import com.gs.common.controller.BaseController;
import com.gs.common.service.bean.response.bean.ResponseMessage;
import com.gs.common.service.bean.response.code.ResponseCode;
import com.gs.owner.generator.bean.ConnectBean;
import com.gs.owner.generator.service.IConnectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by guoshuai on 2018/8/24 0024.
 */
@Controller
@RequestMapping(value = "/connect")
public class ConnectController extends BaseController {

    @Autowired
    private IConnectService connectService;

    @RequestMapping(value = "/index")
    public ModelAndView connect(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("connect");
        modelAndView.addObject("connectBean", session.getAttribute("connectBean"));
        return modelAndView;
    }

    @RequestMapping(value = "/set")
    @ResponseBody
    public ResponseMessage setConnect(ConnectBean connectBean, HttpSession session) throws Exception {

        Assert.notNull(connectBean.getType(), "database type is null");
        Assert.notNull(connectBean.getPosition(), "database position is null");
        Assert.notNull(connectBean.getIp(), "database ip is null");
        Assert.notNull(connectBean.getPort(), "database port is null");
        Assert.notNull(connectBean.getDbName(), "database dbName is null");
        Assert.notNull(connectBean.getUsername(), "database username is null");
        Assert.notNull(connectBean.getPassword(), "database password is null");

        // 测试数据库连接
        boolean test = connectService.test(connectBean);

        // 响应客户端
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setObj(test);
        if (!test) {
            responseMessage.setCode(ResponseCode.VALID_TONKEN.getCode());
            responseMessage.setMsg("数据库不能正常连接，请确认参数");
        }

        session.setAttribute("connectBean", connectBean);
        return responseMessage;
    }

    /**
     * 测试数据库连接
     * @return
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public ResponseMessage test(ConnectBean connectBean, HttpServletRequest request) throws Exception {

        Assert.notNull(connectBean.getType(), "database type is null");
        Assert.notNull(connectBean.getPosition(), "database position is null");
        Assert.notNull(connectBean.getIp(), "database ip is null");
        Assert.notNull(connectBean.getPort(), "database port is null");
        Assert.notNull(connectBean.getDbName(), "database dbName is null");
        Assert.notNull(connectBean.getUsername(), "database username is null");
        Assert.notNull(connectBean.getPassword(), "database password is null");

        ResponseMessage responseMessage = new ResponseMessage();
        boolean test = connectService.test(connectBean);
        responseMessage.setObj(test);
        return responseMessage;
    }
}
