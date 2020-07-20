package zjw.cat.producer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.junit.Test;

/**
 * @author zhoum on 2019-08-13.
 */
public class Others {


    /**
     * https://dubbo.apache.org/zh-cn/docs/user/configuration/api.html
     */
    @Test
    public void test() {
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("cat-consumer");

// 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");

// 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

// 引用远程服务
        ReferenceConfig<CalcCountService> reference = new ReferenceConfig<CalcCountService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(CalcCountService.class);
        reference.setTimeout(4000);

// 和本地bean一样使用xxxService
        CalcCountService calcCountService = reference.get();// 注意：此代理对象内部封装了所有通讯细
        Integer count = calcCountService.count(1, 2);
        System.out.println("是什么：" + count);
    }
}
