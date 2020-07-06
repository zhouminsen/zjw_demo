package zjw.esjob.test.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

/**
 * @Title: SimpleTaskTest
 * @Package: com.cjlogistics.sos.common.common.elasticjob
 * @Description: (用一句话描述该类的作用)
 * @author: yuanjingyun
 * @date: 2019/12/23
 * @version: V1.0
 * @copyright: 上海芯港信息科技有限公司
 */
@Slf4j
public class SimpleTask implements SimpleJob {


    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("-----任务开始执行{}，{}-----", shardingContext.getJobName(), LocalTime.now().toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        log.info("-----任务执行完成{}，{}-----", shardingContext.getJobName(), LocalTime.now().toString());
    }


}
