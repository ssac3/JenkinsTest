package com.example.server.model.dao.user;

import com.example.server.model.dto.user.MonthJoin;
import com.example.server.model.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    User findByUsername(Long username);
    void updateByUsername(User user);
    void save(User user);

<<<<<<< HEAD
    User pwBcrypt(String n_password);

    List<MonthJoin> getAllAttendance(Long username);
=======
    User pwBcrypt(String nPassword);

    User myview(Long username);
>>>>>>> 0b5f24c4b197e90b7068c82c2d3a98cbee6608f6

}
