package top.rainj2013.aop;

import com.google.common.base.Strings;
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
/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-19
 */
@Aspect
@Component
public class LoginCheckAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCheckAop.class);

    private static final Map<String, Object> DEFAULT_FAIL_RESULT = Maps.newHashMapWithExpectedSize(2);

    @Autowired
    private LoginCheckService loginCheckService;

    @Pointcut("@annotation(top.rainj2013.aop.LoginCheck)")
    public void point() {
    }

    static {
        DEFAULT_FAIL_RESULT.put("code", 0);
        DEFAULT_FAIL_RESULT.put("msg", "you do not have permission to access this resource");
    }

    @Around("point()")
    public Object dealLoginCheckAopAnnotation(ProceedingJoinPoint proceedingJoinPoint) {
        final MethodInvocationProceedingJoinPoint joinPoint = (MethodInvocationProceedingJoinPoint) proceedingJoinPoint;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object target = joinPoint.getTarget();
        try {
            method = target.getClass().getMethod(method.getName(), signature.getParameterTypes());
        } catch (NoSuchMethodException e) {
            LOGGER.error("method {} is not exist", method.getName(), e);
            return null;
        }
        LoginCheck loginCheck = method.getAnnotation(LoginCheck.class);
        Object failResult = loginCheck.failResult();
        if (Strings.isNullOrEmpty(failResult.toString())) {
            failResult = DEFAULT_FAIL_RESULT;
        }

        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return failResult;
        }
        Object lastArg = args[args.length - 1];
        if (lastArg == null) {
            return failResult;
        }
        String token = lastArg.toString();
        boolean isLogin = loginCheckService.check(token);
        if (!isLogin) {
            return failResult;
        }
        try {
            return proceed(joinPoint, args);
        } catch (Exception e) {
            LOGGER.error("process joinPoint error", e);
            return failResult;
        }
    }


    private Object proceed(ProceedingJoinPoint joinPoint, Object[] args) throws Exception {
        try {
            return joinPoint.proceed(args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
