package com.yunxi.common.schedule.concurrent.execute;

/**
 * 子任务执行
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: Executer.java, v 0.1 2019年6月21日 下午2:02:50 leukony Exp $
 */
public interface Executer {

    /**
     * 子任务执行业务逻辑执行
     * 
     * @param businessKey 子任务业务标识
     * @throws Exception
     */
    void execute(String businessKey) throws Exception;
}