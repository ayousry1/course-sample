package com.ease.course.services;

import com.ease.course.exceptions.CourseNotFoundException;
import com.ease.course.models.CourseDTO;
import com.ease.course.models.CourseEntity;
import com.ease.course.repositories.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final ModelMapper modelMapper;


    @Autowired
    public CourseService(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    public List<CourseDTO> getAllCourses() {
        List<CourseEntity> courseEntities = this.courseRepository.findAll();
        return courseEntities.stream().map(entity -> modelMapper.map(entity, CourseDTO.class))
                             .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Integer id) {
        Optional<CourseEntity> foundCourse = this.courseRepository.findById(id);
        if (foundCourse.isPresent()) {
            return modelMapper.map(foundCourse.get(), CourseDTO.class);
        } else {
            throw new CourseNotFoundException(id);
        }
    }

    public void createCourse(CourseDTO course) {
        CourseEntity entity = modelMapper.map(course, CourseEntity.class);
        this.courseRepository.save(entity);
    }

    public void updateCourseById(CourseDTO course, Integer id) {

        Optional<CourseEntity> foundCourse = this.courseRepository.findById(id);

        if (foundCourse.isPresent()) {
            CourseEntity courseEntity = foundCourse.get();
            courseEntity.setDescription(course.getDescription());
            courseEntity.setTitle(course.getTitle());
            this.courseRepository.save(courseEntity);
        } else {
            throw new CourseNotFoundException(id);
        }
    }

    public void deleteCourse(Integer id) {

        Optional<CourseEntity> foundCourse = this.courseRepository.findById(id);

        if (foundCourse.isPresent()) {
            this.courseRepository.delete(foundCourse.get());
        } else {
            throw new CourseNotFoundException(id);
        }


    }
}
