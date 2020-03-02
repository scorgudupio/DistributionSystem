package com.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.OperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 日志记录dao
 *
 * @author stylefeng
 * @Date 2017/4/16 23:44
 */
public interface LogDao {

    /**
     * 获取操作日志
     *
     * @author stylefeng
     * @Date 2017/4/16 23:48
     */
    List<Map<String, Object>> getOperationLogs( @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("logType") String logType);

    /**
     * 获取登录日志
     *
     * @author stylefeng
     * @Date 2017/4/16 23:48
     */
    List<Map<String, Object>> getLoginLogs(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName);
}
