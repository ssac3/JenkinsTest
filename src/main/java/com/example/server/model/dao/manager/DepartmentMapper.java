package com.example.server.model.dao.manager;

import com.example.server.model.dto.manager.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DepartmentMapper {
    Department findByDeptInfo(Long id);

    void updateById(Department department);

    void save(Department department);
}
