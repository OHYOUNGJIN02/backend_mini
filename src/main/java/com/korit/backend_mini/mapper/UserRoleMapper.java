package com.korit.backend_mini.mapper;

import com.korit.backend_mini.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    void addUserRole(UserRole userRole);
    void updateUserRole(UserRole userRole);
}
