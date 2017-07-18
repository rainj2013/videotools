package top.rainj2013.aop;

import com.google.common.collect.Maps;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.rainj2013.service.LoginCheckService;

import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
public class LoginCheckAop {
    private final Logger logger = LoggerFactory.getLogger(LoginCheckAop.class);

    @Autowired
    private LoginCheckService loginCheckService;

    @Pointcut("@annotation(top.rainj2013.aop.LoginCheck)")
    public void point() {
    }

    @Around("point()")
    public Object dealLoginCheckAopAnnotation(ProceedingJoinPoint proceedingJoinPoint) {

        Map<String, Object> failResult = Maps.newHashMapWithExpectedSize(2);
        failResult.put("code", 0);
        failResult.put("msg", "you do not have permission to access");

        final MethodInvocationProceedingJoinPoint joinPoint = (MethodInvocationProceedingJoinPoint) proceedingJoinPoint;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object target = joinPoint.getTarget();
        try {
            method = target.getClass().getMethod(method.getName(), signature.getParameterTypes());
        } catch (NoSuchMethodException e) {
            logger.error("method {} is not exist", method.getName(), e);
            return null;
        }

        Object[] args = joinPoint.getArgs();
        String token = args[args.length - 1].toString();
        boolean isLogin = loginCheckService.check(token);
        if (!isLogin) {
            return failResult;
        }
        try {
            return proceed(joinPoint, args);
        } catch (Exception e) {
            logger.error("process joinPoint error", e);
            return failResult;
        }
    }


    private Object proceed(ProceedingJoinPoint joinpoint, Object[] args) throws Exception {
        try {
            return joinpoint.proceed(args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
