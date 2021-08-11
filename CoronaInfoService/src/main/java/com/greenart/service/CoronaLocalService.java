package com.greenart.service;

import com.greenart.mapper.CoronaLocalMapper;
import com.greenart.vo.CoronaLocalVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoronaLocalService {
    @Autowired
    CoronaLocalMapper mapper;
    public void insertCoronaLocal(CoronaLocalVO vo){
        mapper.insertCoronaLocal(vo);
    }
}
