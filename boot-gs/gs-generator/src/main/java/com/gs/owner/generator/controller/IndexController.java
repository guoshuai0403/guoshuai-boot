package com.gs.owner.generator.controller;

import com.gs.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by guoshuai on 2018/8/24 0024.
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = {"/", "/index"})
    public String index(){
        return "forward:/connect/index";
    }
}
