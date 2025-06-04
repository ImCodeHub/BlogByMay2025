package com.example.BlogByMay.Controller;

import com.example.BlogByMay.Entity.Course;
import com.example.BlogByMay.Service.Course.CourseService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/v1")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final CourseService courseService;

    @PostMapping("create-course")
    public ResponseEntity<String> createCourse(@RequestBody Course course){
        String text = courseService.saveCourse(course);
        return new ResponseEntity<>(text, HttpStatus.CREATED);
    }

    @GetMapping("get-allCourses")
    public ResponseEntity<List<Course>> getAllCourse(){
        List<Course> courseList = courseService.findCourseList();
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }


}
