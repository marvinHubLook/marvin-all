package cn.edu.oauth2.mapper;

import cn.edu.oauth2.model.RoleFunc;

public interface RoleFuncMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleFunc record);

    int insertSelective(RoleFunc record);

    RoleFunc selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleFunc record);

    int updateByPrimaryKey(RoleFunc record);
}