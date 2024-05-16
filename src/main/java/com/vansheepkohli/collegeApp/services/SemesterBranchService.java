package com.vansheepkohli.collegeApp.services;

import com.vansheepkohli.collegeApp.model.Branch;
import com.vansheepkohli.collegeApp.model.BranchSemesterMapping;
import com.vansheepkohli.collegeApp.model.Semester;
import com.vansheepkohli.collegeApp.repositories.BranchRepository;
import com.vansheepkohli.collegeApp.repositories.BranchSemesterMappingRepository;
import com.vansheepkohli.collegeApp.repositories.SemesterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterBranchService {
    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchSemesterMappingRepository branchSemesterMappingRepository;

    @Transactional
    public void createSemesterBranchAssociations() {
        // Assuming semesters 1 to 8
        for (int i = 1; i <= 8; i++) {
            Semester semester = new Semester();
            semester.setSemesterNumber(i);
            semesterRepository.save(semester);
        }

        // Assuming branches
        String[] branches = {"CSE", "ME", "IT", "ENC", "CIVIL"};
        for (String branchName : branches) {
            Branch branch = new Branch();
            branch.setName(branchName);
            branchRepository.save(branch);
        }

        // Creating associations
        List<Semester> allSemesters = semesterRepository.findAll();
        List<Branch> allBranches = branchRepository.findAll();

        for (Semester semester : allSemesters) {
            for (Branch branch : allBranches) {
                BranchSemesterMapping branchSemesterMapping = new BranchSemesterMapping();
                branchSemesterMapping.setSemester(semester);
                branchSemesterMapping.setBranch(branch);
                branchSemesterMapping.setSemesterNumber(semester.getSemesterNumber());
                branchSemesterMapping.setBranchName(branch.getName());
                branchSemesterMappingRepository.save(branchSemesterMapping);
            }
        }

        branchRepository.saveAll(allBranches);
    }
}
