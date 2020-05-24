package zjw.cat.producer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zjw.cat.producer.entity.Student;

/**
 * @author Administrator
 */
public interface StudentService extends IService<Student> {

    void add(Student s);
}
