package com.vansheepkohli.collegeApp.mappers.impl;

import com.vansheepkohli.collegeApp.mappers.Mapper;
import com.vansheepkohli.collegeApp.model.CourseMaterial;
import com.vansheepkohli.collegeApp.model.dto.CourseMaterialDto;
import org.modelmapper.ModelMapper;

public class CourseMaterialDtoMapperImpl implements Mapper<CourseMaterial, CourseMaterialDto> {
    private final ModelMapper modelMapper;

    public CourseMaterialDtoMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseMaterialDto mapTo(CourseMaterial courseMaterial) {
        return modelMapper.map(courseMaterial, CourseMaterialDto.class);
    }

    @Override
    public CourseMaterial mapFrom(CourseMaterialDto courseMaterialDto) {
        return modelMapper.map(courseMaterialDto, CourseMaterial.class);
    }
}
