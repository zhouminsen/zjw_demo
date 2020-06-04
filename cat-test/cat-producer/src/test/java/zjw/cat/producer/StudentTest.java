package zjw.cat.producer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zjw.cat.producer.mapper.StudentMapper;

/**
 * @author zhoum on 2019-08-13.
 */
public class StudentTest extends BaseTest {

    @Autowired
    private StudentMapper studentMapper;


    @Test
    public void test() {
        studentMapper.insertOrUpdateMenu(1000,"haha",100000);
    }
}
