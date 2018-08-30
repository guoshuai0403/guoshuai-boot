package com.gs.owner.generator.controller;

import com.gs.common.controller.BaseController;
import com.gs.common.service.bean.response.bean.ResponseMessage;
import com.gs.owner.generator.bean.ConnectBean;
import org.apache.commons.lang3.StringUtils;
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

    @RequestMapping(value = "/index")
    public ModelAndView connect(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("connect");
        modelAndView.addObject("connectBean", session.getAttribute("connectBean"));
        return modelAndView;
    }

    @RequestMapping(value = "/set")
    @ResponseBody
    public ResponseMessage setConnect(ConnectBean connectBean, HttpSession session){
        session.setAttribute("connectBean", connectBean);
        ResponseMessage responseMessage = new ResponseMessage();
        return responseMessage;
    }

    /**
     * 测试数据库连接
     * @return
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public ResponseMessage test(ConnectBean connectBean, HttpServletRequest request){

        Assert.notNull(connectBean.getType(), "database type is null");
        Assert.notNull(connectBean.getPosition(), "database position is null");
        Assert.notNull(connectBean.getIp(), "database ip is null");
        Assert.notNull(connectBean.getPort(), "database port is null");
        Assert.notNull(connectBean.getUsername(), "database username is null");
        Assert.notNull(connectBean.getPassword(), "database password is null");

        ResponseMessage responseMessage = new ResponseMessage();
        return responseMessage;
    }
}
