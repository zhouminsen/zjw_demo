package zjw.cat.consumer.config;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zjw.cat.producer.service.StudentService;

@Configuration
public class Consumer {

    @Reference(timeout = 10000, retries = 0)
    private StudentService studentService;


    @Bean
    public StudentService studentService() {
        return studentService;
    }

}
