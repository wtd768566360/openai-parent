package com.alone.openai.admin.aop;

import com.alone.openai.admin.annotation.SessionValidator;
import com.alone.openai.admin.constant.Constant;
import com.alone.openai.admin.pojo.entity.AdminEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
@Aspect
public class SessionAspect {

    /**
     * 切点，方法上有注解或者类上有注解
     * 拦截类或者是方法上标注注解的方法
     */
    @Pointcut(value = "@annotation(com.alone.openai.admin.annotation.SessionValidator) || @within(com.alone.openai.admin.annotation.SessionValidator)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object before(ProceedingJoinPoint joinpoint) throws Throwable {
        // 获取方法方法上的SessionValidator注解
        MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
        Method method = methodSignature.getMethod();
        SessionValidator sessionValidator = method.getAnnotation(SessionValidator.class);
        // 如果有，并且值为false，则不校验
        if (sessionValidator != null && !sessionValidator.validated()) {
            return joinpoint.proceed(joinpoint.getArgs());
        }
        // 正常校验 获取request和response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null || requestAttributes.getResponse() == null) {
            // 如果不是从前段过来的，没有request，则直接放行
            return joinpoint.proceed(joinpoint.getArgs());
        }
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        // session中拿到user
        AdminEntity user = (AdminEntity) request.getSession().getAttribute(Constant.SESSION_NAME);
        if (user == null) {
            //重定向到登录界面
            response.sendRedirect("/page/login");
            return null;
        }
        // 放行
        return joinpoint.proceed(joinpoint.getArgs());
    }
}
