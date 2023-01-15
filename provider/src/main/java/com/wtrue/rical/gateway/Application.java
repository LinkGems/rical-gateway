package com.wtrue.rical.gateway;

import com.wtrue.rical.gateway.reference.ConsumerReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/7/18 4:44 PM
 */
@MapperScan("com.wtrue.rical.gateway.dao.mapper")
@SpringBootApplication
@EnableDubbo
@Slf4j
public class Application {

    @Resource
    private ConsumerReference consumerReference;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        // 关闭spring的shutdown hook，后续手动触发
        application.setRegisterShutdownHook(false);
        final ConfigurableApplicationContext context = application.run(args);
        Runtime.getRuntime().addShutdownHook(new Thread("T_SHUTDOWN_HOOK") {
            public void run() {
                log.info("”====================shutdown App====================“。");
                //....这里可以做其他优雅退出处理，例如回收本地线程池、关闭定时调度器等的操作

                // 每次等1000ms，最多等5次；优雅退出时间是动态的（可能1秒就能优雅退出完毕）
                // 但如果退出时间大于5秒，那么则放弃优雅退出，直接退出。
                waitDubboShutdown(1000,5);

                // 关闭spring容器
                context.close();
            }
        });

        log.info("==[BackendApp#main] - Application started.");
    }

    /**
     * 等待Dubbo退出，优雅退出的shutdown hook可使用
     * @param sleepMillis 每次发现Dubbo没退出完就睡眠等待的毫秒数
     * @param sleepMaxTimes 最多睡眠的次数，避免一直dubbo退出太久卡住程序的退出，达到此次数后会不再等待
     */
    private static void waitDubboShutdown(long sleepMillis, int sleepMaxTimes) {
        for (int sleepWaitTimes=0; sleepWaitTimes <sleepMaxTimes; sleepWaitTimes++){
            // 如果dubbo的server没有关闭完成，会睡眠等待，最多等待三次
            Collection existingDubboServers = DubboProtocol.getDubboProtocol().getServers();
            Collection existingDubboExporters  = DubboProtocol.getDubboProtocol().getExporters();
            log.info("existing dubbo servers : {}, existing dubbo expoerters {} ,  sleepWaitTimes : {}", existingDubboServers, existingDubboExporters, sleepWaitTimes);
            if (!existingDubboServers.isEmpty() || !existingDubboExporters.isEmpty()) {
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }

        // 优雅退出失败，打印日志
        Collection existingDubboServers = DubboProtocol.getDubboProtocol().getServers();
        if (!existingDubboServers.isEmpty()) {
            log.warn("DUBBO服务Server依然存在，不再等待其销毁，可能会导致优雅退出失败 {}",existingDubboServers);
        }

        Collection existingDubboExporters  = DubboProtocol.getDubboProtocol().getExporters();
        if (!existingDubboExporters.isEmpty()) {
            log.warn("DUBBO服务Exporters依然存在，不再等待其销毁，可能会导致优雅退出失败 {}",existingDubboExporters);
        }

        log.info("==[BackendApp#waitDubboShutdown] - Application stopped.");
    }
}
