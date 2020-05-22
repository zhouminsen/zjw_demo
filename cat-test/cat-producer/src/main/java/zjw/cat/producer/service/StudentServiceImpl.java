package zjw.cat.producer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import zjw.cat.producer.entity.Student;
import zjw.cat.producer.mapper.StudentMapper;

/**
 * @author zhoujiawei
 * @ClassName: IfmApiConfigServiceImpl
 * @Description: api service 接口
 * @since 2019-06-24
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public void add3(Student s) {
//        throw new ValidationException("haha");
        this.save(s);
    }
}
































