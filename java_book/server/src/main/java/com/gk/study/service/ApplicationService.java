package com.gk.study.service;

import com.gk.study.entity.Application;
import com.gk.study.pojo.DTO.ApplicationDTO;

import java.util.List;

public interface ApplicationService {

    void save(Application application);

    List<Application> list();

    void pass(Long id);

    void reject(Long id);

    List<Application> query(ApplicationDTO applicationDTO);
}
