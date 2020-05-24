package zjw.cat.producer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import zjw.cat.producer.CalcCountService;
import zjw.cat.producer.entity.CalcCount;
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

    @Autowired
    private CalcCountService calcCountService;

    @Override
    public void add(Student s) {
//        throw new ValidationException("haha");
        this.add2(s);
    }


    public void add2(Student s) {
//        throw new ValidationException("haha");
        this.save(s);
        CalcCount c = new CalcCount();
        c.setName("haha");
        c.setStoreCount(1);
        c.setVersion(1);
        calcCountService.add(c);
    }
}
































