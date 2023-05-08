package com.ease.course.exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Integer id) {
        super("Course not found with id : " + id);
    }
}
