package zjw.esjob.test.job;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zjw.esjob.test.config.ElasticJobListener;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ElasticJobHandler implements InitializingBean {
    @Resource
    private ZookeeperRegistryCenter registryCenter;
    @Resource
    private ElasticJobListener elasticJobListener;

    @Autowired

    private static final String TASK_PREFIX = "request_";

    private final static ConcurrentMap<String, Object> CONCURRENT_MAP = new ConcurrentHashMap<>();

    /**
     * @param jobName
     * @param jobClass
     * @param shardingTotalCount
     * @param cron
     * @param params             数据ID
     * @return
     */
    private static LiteJobConfiguration.Builder simpleJobConfigBuilder(String jobName,
                                                                       Class<? extends SimpleJob> jobClass,
                                                                       int shardingTotalCount,
                                                                       String cron,
                                                                       String params) {
        return LiteJobConfiguration.newBuilder(
                new SimpleJobConfiguration(
                        JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount).jobParameter(params).build(), jobClass.getCanonicalName())).overwrite(false);
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName            任务名
     * @param cron               表达式
     * @param shardingTotalCount 分片数
     */
    public void addJob(String jobName, String cron, Integer shardingTotalCount, String params) {
        LiteJobConfiguration jobConfig = simpleJobConfigBuilder(jobName, SimpleJob.class, shardingTotalCount, cron, params)
                .overwrite(true).build();

        new SpringJobScheduler(new SimpleTask(), registryCenter, jobConfig).init();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (int i = 0; i < 100; i++) {
            String jobName = generateKey(i);
            String cron = "0 0/5 * * * ?";
            this.addJob(jobName, cron, 1, "周家伟" + i);
        }
    }

    public static String generateKey(int i) {
        String jobName = TASK_PREFIX + "_" + i;
        return jobName;
    }

    public static ConcurrentMap<String, Object> getConcurrentMap() {
        return CONCURRENT_MAP;
    }
}
