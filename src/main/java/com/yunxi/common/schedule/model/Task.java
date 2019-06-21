package com.yunxi.common.schedule.model;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.yunxi.common.schedule.concurrent.execute.Executer;
import com.yunxi.common.schedule.concurrent.load.Loader;
import com.yunxi.common.schedule.concurrent.split.Splitor;

/**
 * 定时任务
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: Task.java, v 0.1 2019年6月21日 上午10:36:11 leukony Exp $
 */
public class Task {

    /** 任务名称 */
    private String                 name;

    /** 拆分器 */
    private Splitor                splitor;

    /** 加载器 */
    private Loader                 loader;

    /** 执行器 */
    private Executer               executer;

    /** 是否采用异步方式执行任务 */
    private boolean                async;

    /** 任务执行线程池, 异步方式任务必须提供线程池 */
    private ThreadPoolTaskExecutor threadPool;

    /**
      * Getter method for property <tt>name</tt>.
      * 
      * @return property value of name
      */
    public String getName() {
        return name;
    }

    /**
      * Setter method for property <tt>name</tt>.
      * 
      * @param name value to be assigned to property name
      */
    public void setName(String name) {
        this.name = name;
    }

    /**
      * Getter method for property <tt>splitor</tt>.
      * 
      * @return property value of splitor
      */
    public Splitor getSplitor() {
        return splitor;
    }

    /**
      * Setter method for property <tt>splitor</tt>.
      * 
      * @param splitor value to be assigned to property splitor
      */
    public void setSplitor(Splitor splitor) {
        this.splitor = splitor;
    }

    /**
      * Getter method for property <tt>loader</tt>.
      * 
      * @return property value of loader
      */
    public Loader getLoader() {
        return loader;
    }

    /**
      * Setter method for property <tt>loader</tt>.
      * 
      * @param loader value to be assigned to property loader
      */
    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    /**
      * Getter method for property <tt>executer</tt>.
      * 
      * @return property value of executer
      */
    public Executer getExecuter() {
        return executer;
    }

    /**
      * Setter method for property <tt>executer</tt>.
      * 
      * @param executer value to be assigned to property executer
      */
    public void setExecuter(Executer executer) {
        this.executer = executer;
    }

    /**
      * Getter method for property <tt>async</tt>.
      * 
      * @return property value of async
      */
    public boolean isAsync() {
        return async;
    }

    /**
      * Setter method for property <tt>async</tt>.
      * 
      * @param async value to be assigned to property async
      */
    public void setAsync(boolean async) {
        this.async = async;
    }

    /**
      * Getter method for property <tt>threadPool</tt>.
      * 
      * @return property value of threadPool
      */
    public ThreadPoolTaskExecutor getThreadPool() {
        return threadPool;
    }

    /**
      * Setter method for property <tt>threadPool</tt>.
      * 
      * @param threadPool value to be assigned to property threadPool
      */
    public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
        this.threadPool = threadPool;
    }
}