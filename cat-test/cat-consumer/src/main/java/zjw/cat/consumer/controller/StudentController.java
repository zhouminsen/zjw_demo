package zjw.cat.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zjw.cat.producer.entity.Student;
import zjw.cat.producer.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2020-05-20.
 */
@RequestMapping("/student")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/helloWorld")
    public Object helloWorld(HttpServletRequest request, HttpServletResponse response) {
        //do your business
        Student s = new Student();
        s.setClassName("10年级");
        s.setScore(1005);
        studentService.add3(s);
        return "haha";
    }
}
