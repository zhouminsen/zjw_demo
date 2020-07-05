/**
 * @Title: ElasticJobListener.java
 * @Package: com.cjlogistics.sos.common.common.elasticjob;
 * @Description: 实现分布式任务监听器
 * @author: yuanjingyun
 * @date: 2019/9/3
 * @version: V1.0
 * @copyright: 上海芯港信息科技有限公司
 */
package zjw.esjob.test.config;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;


/**
 * @author yuanjingyun
 * @ClassName: ElasticJobListener
 * @Description: 实现分布式任务监听器
 * @date 2019/9/3
 */
public class ElasticJobListener extends AbstractDistributeOnceElasticJobListener {


    public ElasticJobListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    /**
     * 暂时存放
     *
     * @param shardingContexts
     */
    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
    	 // Do nothing
    }

    /**
     * 暂时存放
     *
     * @param shardingContexts
     */
    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
    	 // Do nothing
    }


}