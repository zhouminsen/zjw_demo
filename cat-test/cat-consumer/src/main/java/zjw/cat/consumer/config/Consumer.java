package zjw.cat.consumer.config;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zjw.cat.producer.service.StudentService;

/**
 * @Title:
 * @Package:
 * @ClassName Consumer
 * @Author zhoujiawei
 * @Date 2019-09-04
 * @Version V1.0
 * @copyright: 上海芯港信息科技有限公司
 **/
@Configuration
public class Consumer {

    @Reference(timeout = 10000, retries = 0)
    private StudentService studentService;


    @Bean
    public StudentService studentService() {
        return studentService;
    }

}
