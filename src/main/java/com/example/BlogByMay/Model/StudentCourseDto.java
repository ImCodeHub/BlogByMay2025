package com.example.BlogByMay.Model;

import com.example.BlogByMay.Entity.Course;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentCourseDto {
    private String fullName;
    private List<Course> courses;
}
