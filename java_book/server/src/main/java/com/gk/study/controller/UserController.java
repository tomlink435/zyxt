package com.gk.study.controller;

import com.gk.study.common.APIResponse;
import com.gk.study.common.BaseContext;
import com.gk.study.common.JwtProperties;
import com.gk.study.common.ResponeCode;
import com.gk.study.entity.User;
import com.gk.study.permission.Access;
import com.gk.study.permission.AccessLevel;
import com.gk.study.pojo.DTO.LoginFormDTO;
import com.gk.study.pojo.DTO.UserLoginDTO;
import com.gk.study.pojo.VO.UserLoginVO;
import com.gk.study.pojo.entity.UserLogin;
import com.gk.study.service.UserService;
import com.gk.study.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
/**
 * @author lengqin1024(微信)
 * @email net936@163.com
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JwtProperties jwtProperties;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    String salt = "abcd1234";

    @Autowired
    UserService userService;

    @Value("${File.uploadPath}")
    private String uploadPath;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public APIResponse list(String keyword){
        List<User> list =  userService.getUserList(keyword);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public APIResponse detail(Long userId){
        User user =  userService.getUserDetail(userId);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", user);
    }

    // 后台用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public APIResponse login(User user){
        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword() + salt).getBytes()));
        User responseUser =  userService.getAdminUser(user);
        if(responseUser != null) {
            return new APIResponse(ResponeCode.SUCCESS, "查询成功", responseUser);
        }else {
            return new APIResponse(ResponeCode.FAIL, "用户名或密码错误");
        }
    }

//    @PostMapping("/userLogin")
//    public APIResponse userLogin(UserLoginDTO userLoginDTO){
//        log.info("用户登录{}", userLoginDTO);
//        User user = new User();
//        BeanUtils.copyProperties(userLoginDTO, user);
//
//        //用户输入账密
//        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword() + salt).getBytes()));
//        User responseUser =  userService.getNormalUser(user);
//
//        //验证账密
//        if(responseUser != null) {
//            //为用户生成Jwt令牌
//            HashMap<String, Object> claims = new HashMap<>();
//            claims.put("userId", responseUser.getId());
//            String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
//
//            UserLoginVO userLoginVO = UserLoginVO.builder()
//                    .username(user.getUsername())
//                    .password(user.getPassword())
//                    .token(token)
//                    .id(responseUser.getId())
//                    .build();
//
//            return new APIResponse(ResponeCode.SUCCESS, "查询成功", userLoginVO);
//        }else {
//            return new APIResponse(ResponeCode.FAIL, "用户名或密码错误");
//        }
//    }

    // 普通用户注册
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    @Transactional
    public APIResponse userRegister(User user) throws IOException {

        if (!StringUtils.isEmpty(user.getUsername())
                && !StringUtils.isEmpty(user.getPassword())
                && !StringUtils.isEmpty(user.getRePassword())) {
            // 查重
            if(userService.getUserByUserName(user.getUsername()) != null) {
                return new APIResponse(ResponeCode.FAIL, "用户名重复");
            }
            // 验证密码
            if(!user.getPassword().equals(user.getRePassword())) {
                return new APIResponse(ResponeCode.FAIL, "密码不一致");
            }
            String md5Str = DigestUtils.md5DigestAsHex((user.getPassword() + salt).getBytes());
            // 设置密码
            user.setPassword(md5Str);
            md5Str = DigestUtils.md5DigestAsHex((user.getUsername() + salt).getBytes());
            // 设置token
            user.setToken(md5Str);

            String avatar = saveAvatar(user);
            if(!StringUtils.isEmpty(avatar)) {
                user.avatar = avatar;
            }
            // 设置角色
            user.setRole(String.valueOf(User.NormalUser));
            user.setCreateTime(String.valueOf(System.currentTimeMillis()));

            userService.createUser(user);
            return new APIResponse(ResponeCode.SUCCESS, "创建成功");
        }
        return new APIResponse(ResponeCode.FAIL, "创建失败");
    }

    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Transactional
    public APIResponse create(User user) throws IOException {

        if (!StringUtils.isEmpty(user.getUsername()) || !StringUtils.isEmpty(user.getPassword())) {
            // 查重
            if(userService.getUserByUserName(user.getUsername()) != null) {
                return new APIResponse(ResponeCode.FAIL, "用户名重复");
            }
            String md5Str = DigestUtils.md5DigestAsHex((user.getPassword() + salt).getBytes());
            // 设置密码
            user.setPassword(md5Str);
            md5Str = DigestUtils.md5DigestAsHex((user.getUsername() + salt).getBytes());
            // 设置token
            user.setToken(md5Str);
            user.setCreateTime(String.valueOf(System.currentTimeMillis()));

            String avatar = saveAvatar(user);
            if(!StringUtils.isEmpty(avatar)) {
                user.avatar = avatar;
            }
            userService.createUser(user);
            return new APIResponse(ResponeCode.SUCCESS, "创建成功");
        }
        return new APIResponse(ResponeCode.FAIL, "创建失败");
    }

    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public APIResponse delete(String ids){
        System.out.println("ids===" + ids);
        // 批量删除
        String[] arr = ids.split(",");
        for (String id : arr) {
            userService.deleteUser(id);
        }
        return new APIResponse(ResponeCode.SUCCESS, "删除成功");
    }

    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public APIResponse update(User user) throws IOException {
        // update不能修改密码，故置空
        user.setPassword(null);
        String avatar = saveAvatar(user);
        if(!StringUtils.isEmpty(avatar)) {
            user.avatar = avatar;
        }
        userService.updateUser(user);
        System.out.println(user);
        return new APIResponse(ResponeCode.SUCCESS, "更新成功");
    }


    /**
     * 更新用户信息
     * @param user
     * @return
     * @throws IOException
     */
    @Access(level = AccessLevel.LOGIN)
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @Transactional
    public APIResponse updateUserInfo(User user) throws IOException {
        User tmpUser =  userService.getUserDetail(user.getId());
        if(tmpUser.getRole().equals(String.valueOf(User.NormalUser))){
            // username和password不能改，故置空
            user.setUsername(null);
            user.setPassword(null);
            user.setRole(String.valueOf(User.NormalUser));
            String avatar = saveAvatar(user);
            if(!StringUtils.isEmpty(avatar)) {
                user.avatar = avatar;
            }
            userService.updateUser(user);
            return new APIResponse(ResponeCode.SUCCESS, "更新成功");
        }else {
            return new APIResponse(ResponeCode.FAIL, "非法操作");
        }
    }

    @Access(level = AccessLevel.LOGIN)
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    @Transactional
    public APIResponse updatePwd(Long userId, String password, String newPassword) throws IOException {
        User user =  userService.getUserDetail(userId);
        if(user.getRole().equals(String.valueOf(User.NormalUser))) {
            String md5Pwd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
            if(user.getPassword().equals(md5Pwd)){
                user.setPassword(DigestUtils.md5DigestAsHex((newPassword + salt).getBytes()));
                userService.updateUser(user);
            }else {
                return new APIResponse(ResponeCode.FAIL, "原密码错误");
            }
            return new APIResponse(ResponeCode.SUCCESS, "更新成功");
        }else {
            return new APIResponse(ResponeCode.FAIL, "非法操作");
        }

    }

    public String saveAvatar(User user) throws IOException {
        MultipartFile file = user.getAvatarFile();
        String newFileName = null;
        if(file !=null && !file.isEmpty()) {

            // 存文件
            String oldFileName = file.getOriginalFilename();
            String randomStr = UUID.randomUUID().toString();
            newFileName = randomStr + oldFileName.substring(oldFileName.lastIndexOf("."));
            String filePath = uploadPath + File.separator + "avatar" + File.separator + newFileName;
            File destFile = new File(filePath);
            if(!destFile.getParentFile().exists()){
                destFile.getParentFile().mkdirs();
            }
            file.transferTo(destFile);
        }
        if(!StringUtils.isEmpty(newFileName)) {
            user.avatar = newFileName;
        }
        return newFileName;
    }


    /**
     * 发送手机验证码
     * @return
     */
    @PostMapping("/code/{phone}")
    public APIResponse sendCode(@PathVariable("phone") String phone){
        log.info("发送手机验证码:{}", phone);
        return userService.sendCode(phone);
    }

    @PostMapping("/userLogin")
    public APIResponse login(@RequestBody LoginFormDTO loginFormDTO){
        log.info("用户登录:{}", loginFormDTO);
        return userService.login(loginFormDTO);
    }

    @PostMapping("/userLogout")
    public APIResponse logout(HttpServletRequest request){
        log.info("用户登出:{}", request);
        userService.logout(request);
        return new APIResponse(ResponeCode.SUCCESS, "登出成功");
    }

}
