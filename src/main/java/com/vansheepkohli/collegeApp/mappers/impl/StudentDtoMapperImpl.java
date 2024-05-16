package com.vansheepkohli.collegeApp.mappers.impl;

import com.vansheepkohli.collegeApp.mappers.Mapper;
import com.vansheepkohli.collegeApp.model.Student;
import com.vansheepkohli.collegeApp.model.dto.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentDtoMapperImpl implements Mapper<Student, StudentDto> {
    private final ModelMapper modelMapper;

    public StudentDtoMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public StudentDto mapTo(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public Student mapFrom(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }
}
