package com.bjpowernode.crm.settings.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.exception.CrmExceptionEnum;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/settings/user/login")
    @ResponseBody
    public ResultVo login(User user, String code, HttpSession session, HttpServletRequest request) {
        ResultVo resultVo = new ResultVo();

        try {
//           获取正确的验证码
            String correctVerifyCode = (String) session.getAttribute("correctVerifyCode");
//            获取登录的ip地址
            String remoteAddr = request.getRemoteAddr();
            user.setAllowIps(remoteAddr);

            user = userService.login(user, code, correctVerifyCode);
            resultVo.setOk(true);
//            把用用户存入session完成登录的最后一步
            session.setAttribute("user", user);
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }

        return resultVo;
    }

    @RequestMapping("/settings/user/toIndex")
    public String toIndex() {
//        E:\Desktop\bjpn\01-course\09-JavaProject\IDEA\05-CRM\crm\src\main\webapp\WEB-INF\workbench

        return "workbench/index";
    }

    @RequestMapping("/settings/user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login.jsp";
    }
    @RequestMapping("/sittings/user/verifyOldPwd")
    @ResponseBody
    public ResultVo verifyOldPwd(String oldPwd,HttpSession session){
        ResultVo resultVo = new ResultVo();
        try {
//        获取当前登录用户的密码
            User user = (User) session.getAttribute("user");
            String correctPwd = user.getLoginPwd();
            userService.verifyOldPwd(oldPwd,correctPwd);
            resultVo.setOk(true);
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    @RequestMapping("/settings/user/updateUser")
    @ResponseBody
    public ResultVo updaterUser(User user,HttpSession session){

        ResultVo resultVo = new ResultVo();
//     获取登录用户信息
        User user1 = CommonUtil.getUserBySession(session);
        try {
//        将已经登录用户的id赋值给形参user
            user.setId(user1.getId());
            userService.updateUser(user);
            resultVo.setOk(true);
            resultVo.setMessage("更新用户成功");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    @RequestMapping("/settings/user/updateUserFileUpload")
    @ResponseBody
    public ResultVo updateUserFileUpload(User user,HttpSession session){

        ResultVo resultVo = new ResultVo();
//     获取登录用户信息
        User user1 = CommonUtil.getUserBySession(session);
        try {
//        将已经登录用户的id赋值给形参user
            user.setId(user1.getId());
            userService.updateUser(user);
            resultVo.setOk(true);
            resultVo.setMessage("更改用户头像成功");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }



    @RequestMapping("/settings/user/fileUpload")
    @ResponseBody
    public ResultVo fileUpload(HttpSession session,MultipartFile img,HttpServletRequest request){
        ResultVo resultVo = new ResultVo();
        try {
            String realPath = session.getServletContext().getRealPath("/upload");

            File file = new File(realPath);
            if (!file.exists()) {
//                如果目录不存在创建目录
//                创建多级目录
                file.mkdirs();
            }
//      获取上传文件的文件名
                String fileName = img.getOriginalFilename();
                 fileName = UUIDUtil.getUUID() + fileName;
//                 检验后缀名是否合法
                suffix(fileName);
//                检验文件名是否合法
                maxSize(img.getSize());
//                 upload/神乐.jpg
                    img.transferTo(new File(realPath + File.separator +fileName));
//                    将照片在服务器的实际路径放入隐藏input元素中
            String contextPath = request.getContextPath();
            String photoPath = contextPath + File.separator + "upload" + File.separator + fileName;
                    resultVo.setMessage("上传头像成功");
                    resultVo.setT(photoPath);
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultVo;
    }
//   校验文件大小是否合法
    private void maxSize(long size) {
        long maxSize = 2 * 1024 * 1024;
        if (size > maxSize) {
            throw new CrmException(CrmExceptionEnum.USER_UPDATE_SIZE);
        }
    }


//   校验后缀名是否合法
    private void suffix(String fileName) {
//        jpg/png/webp/gif
        String suffixes = "jpg,png,webp,gif";
//        最后一个点的位置加一（从点的后面截取剩余的所有的后缀）
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(!suffixes.contains(suffix)){
            throw new CrmException(CrmExceptionEnum.USER_UPDATE_SUFFIX);

        }
    }
}



