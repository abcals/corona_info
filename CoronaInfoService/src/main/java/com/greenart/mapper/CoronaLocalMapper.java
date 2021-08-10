package com.greenart.mapper;

import com.greenart.vo.Corona_LocalVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoronaLocalMapper {
    public void insertCoronaLocal(Corona_LocalVO vo);
}
