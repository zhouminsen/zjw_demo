/**
 * @Title: ElasticJobConfig.java
 * @Package: com.cjlogistics.sos.common.common.config;
 * @Description: elasticjob 配置
 * @author: yuanjingyun
 * @date: 2019/8/28
 * @version: V1.0
 * @copyright: 上海芯港信息科技有限公司
 */
package zjw.esjob.test.config;

import com.dangdang.ddframe.job.lite.lifecycle.api.JobAPIFactory;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobOperateAPI;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobStatisticsAPI;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuanjingyun
 * @ClassName: ElasticJobConfig
 * @Description: elasticjob 配置
 * @date 2019/8/28
 */
@Configuration
public class ElasticJobConfig {

    @Value("${elasticjob.serverlists}")
    private String serverlists;

    @Value("${elasticjob.namespace}")
    private String namespace;


    @Bean
    public ZookeeperConfiguration zkConfig() {
        return new ZookeeperConfiguration(serverlists, namespace);
    }

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(ZookeeperConfiguration config) {
        return new ZookeeperRegistryCenter(config);
    }


    @Bean
    public ElasticJobListener elasticJobListener() {
        return new ElasticJobListener(10000, 10000);
    }

    @Bean
    public JobOperateAPI getJobOperatorAPI() {
        return JobAPIFactory.createJobOperateAPI(serverlists, namespace, Optional.fromNullable(null));
    }

    @Bean
    public JobStatisticsAPI getJobStatisticsAPI() {
        return JobAPIFactory.createJobStatisticsAPI(serverlists, namespace, Optional.fromNullable(null));
    }
}
