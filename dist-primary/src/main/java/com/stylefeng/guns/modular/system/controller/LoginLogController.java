package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.modular.system.dao.LogDao;
import com.stylefeng.guns.modular.system.warpper.LogWarpper;
import com.stylefeng.guns.common.persistence.model.OperationLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 日志管理的控制器
 *
 * @author fengshuonan
 * @Date 2017年4月5日 19:45:36
 */
@Controller
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController {

    private static String PREFIX = "/system/log/";

    @Resource
    private LogDao logDao;

    /**
     * 跳转到日志管理的首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "login_log.html";
    }

    /**
     * 查询登录日志列表
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logName) {
        Page p = new PageFactory<OperationLog>().defaultPage();
        List<Map<String, Object>> result = logDao.getLoginLogs(beginTime, endTime, logName);
        List<OperationLog> list= (List<OperationLog>) new LogWarpper(result).warp();
        return super.packForBT(list,p.getTotal());
    }

    /**
     * 清空日志
     */
    @BussinessLog("清空登录日志")
    @RequestMapping("/delLoginLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        SqlRunner.db().delete("delete from login_log");
        return SUCCESS_TIP;
    }
}
