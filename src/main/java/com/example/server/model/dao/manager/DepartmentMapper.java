package com.example.server.model.dao.manager;

import com.example.server.model.dto.manager.Department;
import com.example.server.model.dto.manager.VacationView;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface DepartmentMapper {

    int existDeptId(Long id);
    Department findByDeptInfo(Long id);

    void updateByOne(LocalDateTime startTime, LocalDateTime endTime, Long id);

    List<VacationView> findByVacationAll();


}
