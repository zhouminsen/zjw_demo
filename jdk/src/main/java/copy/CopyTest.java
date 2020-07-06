package copy;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import util.UtilFuns;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhoum on 2019-07-22.
 */
public class CopyTest {

    /**
     * 必须实现cloneable接口，非深度拷贝
     *
     * @throws CloneNotSupportedException
     */
    @Test
    public void method() throws CloneNotSupportedException {
        Teacher teacher = new Teacher();
        teacher.setName("周老师");
        Student student = new Student();
        student.setName("伟哥");
        teacher.setStudent(student);
        System.out.println("打印原始对象");
        System.out.println(teacher);
        System.out.println(teacher.getStudent());
        //clone对象
//        Teacher teacher2 = (Teacher) teacher.clone();
        System.out.println(" 打印clone后的对象");
//        System.out.println(teacher2);
//        System.out.println(teacher2.getStudent());

    }

    /**
     * 无须实现任何接口
     */
    @Test
    public void jsonCopy() {
        Teacher teacher = new Teacher();
        teacher.setName("周老师");
        Student student = new Student();
        student.setName("伟哥");
        teacher.setStudent(student);
        System.out.println("打印原始对象");
        System.out.println(teacher);
        System.out.println(teacher.getStudent());
        //json序列化后的对象
        Teacher teacher2 = JSON.parseObject(JSON.toJSONString(teacher), Teacher.class);
        System.out.println(" 打印json序列化后的对象");
        System.out.println(teacher2);
        System.out.println(teacher2.getStudent());
    }

    /**
     * 必须实现序列化接口
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void deepCopy() throws IOException, ClassNotFoundException {
        Teacher teacher = new Teacher();
        teacher.setName("周老师");
        Student student = new Student();
        student.setName("伟哥");
        teacher.setStudent(student);
        System.out.println("打印原始对象");
        System.out.println(teacher);
        System.out.println(teacher.getStudent());
        //byte序列化后的对象
        List<Teacher> teachers = UtilFuns.deepCopy(Arrays.asList(teacher));
        Teacher teacher2 = teachers.get(0);
        System.out.println(" 打印byte序列化后的对象");
        System.out.println(teacher2);
        System.out.println(teacher2.getStudent());
    }
}
