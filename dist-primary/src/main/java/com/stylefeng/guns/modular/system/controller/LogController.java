package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.github.pagehelper.Page;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.constant.state.BizLogType;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.core.support.BeanKit;
import com.stylefeng.guns.modular.system.dao.LogDao;
import com.stylefeng.guns.modular.system.warpper.LogWarpper;
import com.stylefeng.guns.common.persistence.dao.OperationLogMapper;
import com.stylefeng.guns.common.persistence.model.OperationLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/log")
public class LogController extends BaseController {

    private static String PREFIX = "/system/log/";

    @Resource
    private OperationLogMapper operationLogMapper;

    @Resource
    private LogDao logDao;

    /**
     * 跳转到日志管理的首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "log.html";
    }

    /**
     * 查询操作日志列表
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logName, @RequestParam(required = false) Integer logType) {
        Page page = new PageFactory<OperationLog>().defaultPage();
        List<Map<String, Object>> result = logDao.getOperationLogs(beginTime, endTime, logName, BizLogType.valueOf(logType));
        List<OperationLog> lists =(List<OperationLog>) new LogWarpper(result).warp();
        return super.packForBT(lists,page.getTotal());
    }

    /**
     * 查询操作日志详情
     */
    @RequestMapping("/detail/{id}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object detail(@PathVariable Integer id) {
        OperationLog operationLog = operationLogMapper.selectById(id);
        Map<String, Object> stringObjectMap = BeanKit.beanToMap(operationLog);
        return super.warpObject(new LogWarpper(stringObjectMap));
    }

    /**
     * 清空日志
     */
    @BussinessLog(value = "清空业务日志")
    @RequestMapping("/delLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        SqlRunner.db().delete("delete from operation_log");
        return SUCCESS_TIP;
    }
}
