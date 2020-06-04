package zjw.cat.producer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import zjw.cat.producer.entity.Student;

/**
 * @author zhoujiawei
 * @ClassName: IfmApiConfigMapper
 * @Description: api配置 mapper
 * @date 2019-09-11
 */
public interface StudentMapper extends BaseMapper<Student> {

    void insertOrUpdateMenu(@Param("id") int id, @Param("class_name") String className, @Param("score") int score);
}