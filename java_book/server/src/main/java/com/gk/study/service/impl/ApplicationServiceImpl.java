package com.gk.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.common.BaseException;
import com.gk.study.entity.Application;
import com.gk.study.entity.ThingTag;
import com.gk.study.mapper.ApplicationMapper;
import com.gk.study.pojo.DTO.ApplicationDTO;
import com.gk.study.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public void save(Application application) {
        applicationMapper.insert(application);
    }

    @Override
    public List<Application> list() {
        List<Application> applicationList = applicationMapper.selectList(new QueryWrapper<>());
        return applicationList;
    }

    /**
     * 通过审核
     * @param id
     */
    @Override
    public void pass(Long id) {
        Application application = applicationMapper.selectById(id);
        //只有状态处于0(未审核)的数据才能进行审核

        if(application==null || application.getStatus() != 0){
            throw new BaseException("申请状态不正确");
        }

        //审核状态改为1，表示通过审核
        application.setStatus(1);

        //更新申请
        applicationMapper.updateById(application);
    }

    /**
     * 拒绝审核
     * @param id
     */
    @Override
    public void reject(Long id) {
        Application application = applicationMapper.selectById(id);

        //只有状态处于0(未审核)的数据才能进行审核
        if(application==null || application.getStatus() != 0){
            throw new BaseException("申请状态不正确");
        }

        //审核状态改为2，表示申请未通过审核
        application.setStatus(2);
        applicationMapper.updateById(application);
    }

    @Override
    public List<Application> query(ApplicationDTO applicationDTO) {
        QueryWrapper<Application> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", applicationDTO.getUserId());
        List<Application> applicationList = applicationMapper.selectList(queryWrapper);
        return applicationList;
    }
}
