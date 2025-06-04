package com.example.BlogByMay.Service.Course;

import com.example.BlogByMay.Entity.Course;
import com.example.BlogByMay.Entity.User;
import com.example.BlogByMay.Model.StudentCourseDto;
import com.example.BlogByMay.Repository.CourseRepository;
import com.example.BlogByMay.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService implements CourseInterface {
    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final UserRepository userRepository;


    @Override
    public String saveCourse(Course course) {
        courseRepository.save(course);
        return "Course has been created";
    }

    @Override
    public List<Course> findCourseList() {
        List<Course> all = courseRepository.findAll();
        return all;
    }

    @Override
    public StudentCourseDto findStudentCourseDetails(Long userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        Set<Course> courses = foundUser.getCourses();
        StudentCourseDto student = StudentCourseDto.builder()
                .fullName(foundUser.getFirstName()+" "+foundUser.getLastName())
                .courses(courses.stream().toList()).build();
        return student;
    }
}
