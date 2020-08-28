package com.example.demo.service.impl;
/*
 * @author p78o2
 * @date 2020/8/28
 */

import com.example.demo.domain.po.Test;
import com.example.demo.mapper.TestMapper;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;
    @Override
    public List<Test> getAll() {
        return testMapper.getTests();
    }
}
