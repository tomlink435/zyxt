package com.gk.study.service.impl;

import com.gk.study.entity.Application;
import com.gk.study.mapper.ApplicationMapper;
import com.gk.study.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public void save(Application application) {
        applicationMapper.insert(application);
    }
}
