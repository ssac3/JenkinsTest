package com.example.server.model.dao.manager;

import com.example.server.model.dto.manager.Department;
import com.example.server.model.dto.manager.RearrangeView;
import com.example.server.model.dto.manager.ResultAction;
import com.example.server.model.dto.manager.VacationView;
import com.example.server.model.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Mapper
public interface DepartmentMapper {

    int validDeptId(Long id);
    Department findByDeptInfo(Long id);

    void updateByOne(LocalDateTime startTime, LocalDateTime endTime, Long id);

    List<VacationView> findByVacationAll(Long id);

    int validVid(Long vId);
    void updateVacationByOne(String approvalFlag, Long vId);

    List<RearrangeView> findByRearrangeAll(Long id);

    void updateRearrangeByOne(Long rId, Long aId, String startTime, String endTime, String approvalFlag);

    ResultAction checkRearrangeUpdate();

    List<User> findEmpAllByDepId(Long id);
}
