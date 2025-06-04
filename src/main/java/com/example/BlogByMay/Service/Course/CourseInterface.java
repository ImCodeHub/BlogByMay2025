package com.example.BlogByMay.Service.Course;

import com.example.BlogByMay.Entity.Course;
import com.example.BlogByMay.Model.StudentCourseDto;

import java.util.List;

public interface CourseInterface {
    public String saveCourse(Course course);
    public List<Course> findCourseList();
    public StudentCourseDto findStudentCourseDetails(Long userId);
}
