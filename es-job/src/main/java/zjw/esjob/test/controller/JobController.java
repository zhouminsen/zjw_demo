package zjw.esjob.test.controller;

import com.dangdang.ddframe.job.lite.lifecycle.api.JobOperateAPI;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobStatisticsAPI;
import com.dangdang.ddframe.job.lite.lifecycle.domain.JobBriefInfo;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zjw.esjob.test.job.ElasticJobHandler;

import java.util.Collection;

import static zjw.esjob.test.job.ElasticJobHandler.generateKey;

/**
 * Created by Administrator on 2020-05-20.
 */
@RequestMapping("/job")
@RestController
public class JobController {

    @Autowired
    private JobOperateAPI jobOperateAPI;

    @Autowired
    private JobStatisticsAPI jobStatisticsAPI;

    @Autowired
    private ElasticJobHandler elasticJobHandler;

    @GetMapping("/delete")
    public String delete(Integer i) {
        String jobName = generateKey(i);
        jobOperateAPI.remove(Optional.of(jobName), Optional.<String>absent());
        return "删除成功";
    }

    /**
     * 集群模式下，必须所以服务都添加该job，不然会造成某台服务没添加该job，导致该台服务丢失该job
     * 非addJob只需要挑选任意一台服务执行，即可全部生效
     *
     * @param i
     * @return
     */
    @GetMapping("/add")
    public String add(Integer i) {
        String jobName = generateKey(i);
        elasticJobHandler.addJob(jobName, "0 0/1 * * * ?", 1, "周家伟" + i);
        return "添加成功";
    }

    @GetMapping("/disable")
    public String disable(Integer i) {
        String jobName = generateKey(i);
        jobOperateAPI.disable(Optional.of(jobName), Optional.<String>absent());
        return "禁用成功";
    }


    @GetMapping("/enable")
    public String enable(Integer i) {
        String jobName = generateKey(i);
        jobOperateAPI.enable(Optional.of(jobName), Optional.<String>absent());
        return "启动成功";
    }

    @GetMapping("/trigger")
    public String trigger(Integer i) {
        String jobName = generateKey(i);
        jobOperateAPI.trigger(Optional.of(jobName), Optional.<String>absent());
        return "立即执行成功";
    }

    @GetMapping("/getAllJob")
    public Collection<JobBriefInfo> getAllJob() {
        Collection<JobBriefInfo> allJobsBriefInfo = jobStatisticsAPI.getAllJobsBriefInfo();
        return allJobsBriefInfo;
    }
}
