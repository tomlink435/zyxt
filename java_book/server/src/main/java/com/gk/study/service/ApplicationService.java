package com.gk.study.service;

import com.gk.study.entity.Application;

import java.util.List;

public interface ApplicationService {

    void save(Application application);

    List<Application> list();

    void pass(Long id);

    void reviewPass(Long id);
}
