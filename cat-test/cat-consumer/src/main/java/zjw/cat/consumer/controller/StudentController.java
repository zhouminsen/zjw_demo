package zjw.cat.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zjw.cat.consumer.annotation.LogAnnotation;
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

    @LogAnnotation("helloWorld")
    @PostMapping("/helloWorld")
    public Object helloWorld(@RequestBody Student student, HttpServletRequest request, HttpServletResponse response) {
        //do your business
        studentService.add(student);
        return "haha";
    }
}
