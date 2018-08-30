package com.gs.owner.generator.controller;

import com.gs.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/30
 */
@Controller
@RequestMapping(value = "/table")
public class TableController extends BaseController {

    @RequestMapping
    public ModelAndView index(HttpSession session){

        ModelAndView table = new ModelAndView("table");
        return table;
    }
}
