package com.vansheepkohli.collegeApp.services;



import com.vansheepkohli.collegeApp.model.CourseMaterial;
import com.vansheepkohli.collegeApp.model.dto.CourseMaterialDto;
import com.vansheepkohli.collegeApp.repositories.CourseMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseMaterialService {
    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    public void addCourseMaterial(CourseMaterialDto courseMaterialDto) {
        CourseMaterial courseMaterial = CourseMaterial.builder()
                .courseCode(courseMaterialDto.getCourseCode())
                .url(courseMaterialDto.getUrl())
                .build();

       courseMaterialRepository.save(courseMaterial);
    }
}
