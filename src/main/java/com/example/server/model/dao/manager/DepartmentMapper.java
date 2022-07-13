package com.example.server.model.dao.manager;

import com.example.server.model.dto.manager.*;
import com.example.server.model.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface DepartmentMapper {

    int validDeptId(Long id);
    Department findByDeptInfo(Long id);

    void updateByOne(LocalDateTime startTime, LocalDateTime endTime, Long id);

    List<VacationView> findByVacationAll(Long id);

    int validVid(Long vId);
    void updateVacationByOne(String approvalFlag, Long vId);

    VacationUpdate findByVid(Long vId);

    void updateRestTimeByUsername(Long username, Long restTime);

    List<RearrangeView> findByRearrangeAll(Long id);

    void updateRearrangeByOne(Long rId, Long aId, String startTime, String endTime, String approvalFlag);

    ResultAction checkRearrangeUpdate();

    List<User> findEmpAllByDepId(Long id);

    List<EmplAtndcView> findEmplAtndcById(Long username, String findDate);

    List<Month> findEmplAtndStatsById(Long username, Long year);

    EavView findEavByUsername(Long username, String findDate, String lastDate);

    List<Map> findOverTimeByDepId(Long depId, String findDate);

    String preFindOverTimeByDepId(Long depId, String findDate);

    List<Map> findPositionByDepId(Long depId);

    Map<String, Long> findCountByDepId(Long depId, String date);
}
