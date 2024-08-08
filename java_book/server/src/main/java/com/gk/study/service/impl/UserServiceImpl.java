package com.gk.study.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import com.gk.study.pojo.DTO.LoginFormDTO;
import com.gk.study.pojo.DTO.UserDTO;
import com.gk.study.pojo.DTO.UserLoginDTO;
import com.gk.study.pojo.VO.UserVO;
import com.gk.study.service.UserService;
import com.gk.study.entity.User;
import com.gk.study.mapper.UserMapper;
import com.gk.study.utils.RegexUtils;
import com.gk.study.utils.SendSmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SendSmsUtils sendSmsUtils;


    @Override
    public List<User> getUserList(String keyword) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(keyword)){
            // like查询
            queryWrapper.like("username", keyword);
        }
        queryWrapper.orderBy(true, false, "create_time");
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public User getAdminUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());
        queryWrapper.gt("role", "1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User getNormalUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());
        queryWrapper.eq("role", "1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public void createUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void deleteUser(String id) {
        userMapper.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public User getUserByToken(String token) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("token", token);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User getUserByUserName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User getUserDetail(Long userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", userId);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @Override
    public APIResponse sendCode(String phone) {
        //1.校验手机号
        if(RegexUtils.isPhoneInvalid(phone)){
            //2.手机号格式错误
            return new APIResponse(ResponeCode.FAIL, "手机号格式错误");
        }
        //3.生成验证码
        String code = RandomUtil.randomNumbers(6);
        log.info("手机号:{}, 验证码:{}", phone, code);

        //4.保存至redis
        stringRedisTemplate.opsForValue().set("user:phone:" + phone, code, 5, TimeUnit.MINUTES);

        //5.发送验证码
//        try {
//            sendSmsUtils.sendCode(phone, code);
//        } catch (Exception e) {
//            return new APIResponse(ResponeCode.FAIL, "验证码发送失败");
//        }
        return new APIResponse(ResponeCode.SUCCESS,code);
    }

    @Override
    public APIResponse login(LoginFormDTO loginFormDTO) {
        //1. 校验手机号
        String phone = loginFormDTO.getPhone();
        boolean phoneInvalid = RegexUtils.isPhoneInvalid(phone);
        if(phoneInvalid){
            //2.手机号格式错误
            return new APIResponse(ResponeCode.FAIL, "手机号格式错误");
        }

        //2.校验验证码
        String code = loginFormDTO.getCode();
        boolean codeInvalid = RegexUtils.isCodeInvalid(code);
        if(codeInvalid){
            return new APIResponse(ResponeCode.FAIL, "验证码格式错误");
        }

        //3.判断手机号和验证码是否一致
        //不一致
        String cacheCode = stringRedisTemplate.opsForValue().get("user:phone:" + phone);
        if(cacheCode == null || !code.equals(cacheCode)){
            return new APIResponse(ResponeCode.FAIL, "验证码不正确");
        }
        //4.一致，查询用户是否存在
        User user = query().eq("mobile", phone).one();

        //5.不存在，创建新用户，保存到数据库
        if(user == null){
            user = User.builder()
                    .mobile(phone)
                    .build();
            save(user);
        }
        //6.保存用户到redis
        //6.1生成token
        String token = UUID.randomUUID().toString();
        //6.2将User转为HashMap存储
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((k, v) -> v.toString()));
        //6.3存至Redis
        String tokenKey = "login:token:" + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        //6.4设置有效期
        stringRedisTemplate.expire(tokenKey, 30, TimeUnit.MINUTES);

        UserVO userVO = UserVO.builder()
                .id(user.getId())
                .phone(phone)
                .token(token)
                .build();
        return new APIResponse(ResponeCode.SUCCESS, userVO);
    }

    /**
     * 用户登出
     * @param request
     */
    @Override
    public void logout(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        stringRedisTemplate.delete("login:token:" + token);
    }
}
