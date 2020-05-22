package zjw.cat.producer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"zjw.cat.producer.mapper"})
public class CatProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatProducerApplication.class, args);
    }

}
