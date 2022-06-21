package com.example.server.service;

import com.example.server.model.dao.manager.DepartmentMapper;
import com.example.server.model.dto.manager.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentMapper departmentMapper;

    public Department selectDept(Long id){
        return departmentMapper.findByDeptInfo(id);
    }
}
