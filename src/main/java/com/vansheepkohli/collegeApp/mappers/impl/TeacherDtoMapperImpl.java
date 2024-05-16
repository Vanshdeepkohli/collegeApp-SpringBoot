package com.vansheepkohli.collegeApp.mappers.impl;

import com.vansheepkohli.collegeApp.mappers.Mapper;
import com.vansheepkohli.collegeApp.model.Teacher;
import com.vansheepkohli.collegeApp.model.dto.TeacherDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TeacherDtoMapperImpl implements Mapper<Teacher, TeacherDto> {

    private final ModelMapper modelMapper;

    public TeacherDtoMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TeacherDto mapTo(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDto.class);
    }

    @Override
    public Teacher mapFrom(TeacherDto teacherDto) {
        return modelMapper.map(teacherDto, Teacher.class);
    }
}
