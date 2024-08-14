package com.gk.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gk.study.common.APIResponse;
import com.gk.study.common.BaseException;
import com.gk.study.common.ResponeCode;
import com.gk.study.entity.Application;
import com.gk.study.entity.Thing;
import com.gk.study.entity.User;
import com.gk.study.mapper.ApplicationMapper;
import com.gk.study.mapper.ThingMapper;
import com.gk.study.mapper.UserMapper;
import com.gk.study.pojo.DTO.ApplicationDTO;
import com.gk.study.service.ApplicationService;
import com.gk.study.utils.RegexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ThingMapper thingMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public APIResponse save(ApplicationDTO applicationDTO) {
        String name = applicationDTO.getName();
        String email = applicationDTO.getEmail();
        String phone = applicationDTO.getPhone();
        String company = applicationDTO.getCompany();
        String purpose = applicationDTO.getPurpose();
        String projectName = applicationDTO.getProjectName();
        String projectLeader = applicationDTO.getProjectLeader();
        String projectType = applicationDTO.getProjectType();

        //为空判断
        if("".equals(name) || name == null){
            return new APIResponse(ResponeCode.FAIL, "姓名不能为空", applicationDTO);
        }
        if("".equals(email) || email == null){
            return new APIResponse(ResponeCode.FAIL, "E-mail不能为空", applicationDTO);
        }
        if("".equals(phone) || phone == null){
            return new APIResponse(ResponeCode.FAIL, "电话不能为空", applicationDTO);
        }
        if("".equals(company) || company == null){
            return new APIResponse(ResponeCode.FAIL, "工作单位不能为空", applicationDTO);
        }
        if("".equals(purpose) || purpose == null){
            return new APIResponse(ResponeCode.FAIL, "数据使用用途不能为空", applicationDTO);
        }
        if("".equals(projectName) || projectName == null){
            return new APIResponse(ResponeCode.FAIL, "拟支持项目/课题名称不能为空", applicationDTO);
        }
        if("".equals(projectLeader) || projectLeader == null){
            return new APIResponse(ResponeCode.FAIL, "项目/课题主持人不能为空", applicationDTO);
        }
        if("".equals(projectType) || projectType == null){
            return new APIResponse(ResponeCode.FAIL, "项目/课题类型不能为空", applicationDTO);
        }
        //数据填写规范判断
        //1. 判断姓名是否符合规范
        if(RegexUtils.isNameInvalid(name)){
            return new APIResponse(ResponeCode.FAIL, "请输入正确格式的姓名", applicationDTO);
        }
        //2. 判断email是否符合规范
        if(RegexUtils.isEmailInvalid(email)){
            return new APIResponse(ResponeCode.FAIL, "请输入正确格式的E-mail", applicationDTO);
        }
        //3. 判断电话是否符合规范
        if(RegexUtils.isPhoneInvalid(phone)){
            return new APIResponse(ResponeCode.FAIL, "请输入正确格式的电话", applicationDTO);
        }
        //4. 项目/课题主持人
        if(RegexUtils.isNameInvalid(name)){
            return new APIResponse(ResponeCode.FAIL, "请输入正确格式的项目/课题主持人", applicationDTO);
        }

        //判断用户是否存在
        Long userId = applicationDTO.getUserId();
        User user = userMapper.selectById(userId);
        if(user == null){
            return new APIResponse(ResponeCode.FAIL, "该用户不存在", applicationDTO);
        }

        //判断数据是否存在
        Long thingId = applicationDTO.getThingId();
        Thing thing = thingMapper.selectById(thingId);
        if(thing == null){
            return new APIResponse(ResponeCode.FAIL, "申请数据不存在或被删除", applicationDTO);
        }

        //申请成功
        Application application = new Application();
        BeanUtils.copyProperties(applicationDTO, application);
        application.setCreateTime(LocalDateTime.now());
        applicationMapper.insert(application);

        return new APIResponse(ResponeCode.SUCCESS);
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
