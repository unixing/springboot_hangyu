package org.ldd.ssm.crm.web;

import java.io.IOException;  

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;

import org.ldd.ssm.crm.utils.VerifyCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <p><b>AuthImage Description:</b> (验证码)</p>
 * <b>DATE:</b> 2016年6月2日 下午3:53:12
 */
@Controller
public class AuthImageAction extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {  
    static final long serialVersionUID = 1L;  
    @RequestMapping("/authImage")
    @ResponseBody
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
          
        //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
        //存入会话session  
        HttpSession session = request.getSession(true);  
        //删除以前的
        session.removeAttribute("verifyCode");
        session.setAttribute("verifyCode", verifyCode);  
        //生成图片  
        int w = 100, h = 30;  
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);  
  
    }  
} 