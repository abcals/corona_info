package com.greenart.mapper;

import com.greenart.vo.CoronaLocalVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoronaLocalMapper {
    public void insertCoronaLocal(CoronaLocalVO vo);
}
