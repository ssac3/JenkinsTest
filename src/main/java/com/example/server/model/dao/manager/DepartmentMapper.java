package com.example.server.model.dao.manager;

import com.example.server.model.dto.manager.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DepartmentMapper {

    int existDeptId(Long id);
    Department findByDeptInfo(Long id);

    void updateByOne(String id, String start_time, String end_time);

    void save(Department department);
}
