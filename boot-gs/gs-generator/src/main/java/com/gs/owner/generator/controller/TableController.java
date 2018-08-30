package com.gs.owner.generator.controller;

import com.gs.common.controller.BaseController;
import com.gs.common.service.bean.pojo.PagePojo;
import com.gs.owner.generator.bean.ConnectBean;
import com.gs.owner.generator.bean.TableBean;
import com.gs.owner.generator.service.ITableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/30
 */
@Controller
@RequestMapping(value = "/table")
public class TableController extends BaseController {

    @Autowired
    private ITableService tableService;

    /**
     * 分页展示表格
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping
    public ModelAndView index(HttpSession session, TableBean tableBean) throws Exception {
        tableBean.setConnectBean((ConnectBean)session.getAttribute("connectBean"));
        PagePojo<TableBean> tableBeans = tableService.getAll(tableBean);
        ModelAndView modelAndView = new ModelAndView("table");
        modelAndView.addObject(tableBeans);
        modelAndView.addObject("page", tableBeans);
        return modelAndView;
    }
}
