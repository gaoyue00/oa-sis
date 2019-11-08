package com.cjs.example.lock.aop;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class LoginAop {
    private static final Logger logger = LoggerFactory.getLogger(LoginAop.class);

    @Pointcut("execution(public * com.cjs.example.lock.controller..*(..))")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //IP地址
        String ipAddr = getRemoteHost(request);
        String url = request.getRequestURL().toString();
        String reqParam = preHandle(joinPoint, request);
        long startTime = System.currentTimeMillis();
        logger.info("=============================================== START =================================================");
        logger.info("*************************************请求参数开始打印***********************************************");
        logger.info("*请求地址 : {}",url);
        logger.info("*请求参数 : {}",reqParam);
        // 打印 Http method
        logger.info("*请求方式 : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        logger.info("*请求类名 : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        logger.info("*请求源IP :【{}】", ipAddr);
        logger.info("*************************************请求参数结束打印***********************************************");


        Object result = joinPoint.proceed();
        String respParam = JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
        logger.info("*************************************返回参数开始打印***********************************************");
        logger.info("*请求源IP :【{}】,请求URL:【{}】", ipAddr, url);
        logger.info("*返回参数 : {}", respParam);
        // 执行耗时
        logger.info("*执行耗时 : {} ms", System.currentTimeMillis() - startTime);
        logger.info("*************************************返回参数结束打印***********************************************");
        logger.info("================================================ EDN =================================================");
        // 每个请求之间空一行
        logger.info("");
        return result;
    }

    /**
     * 入参数据
     *
     * @param joinPoint
     * @param request
     * @return
     */
    private String preHandle(ProceedingJoinPoint joinPoint, HttpServletRequest request) {

        String reqParam = "";
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Annotation[] annotations = targetMethod.getAnnotations();
        for (Annotation annotation : annotations) {
            //此处可以改成自定义的注解
            if (annotation.annotationType().equals(RequestMapping.class)) {
                reqParam = JSONObject.toJSONString(request.getParameterMap());
                break;
            }
        }
        if (StringUtils.isEmpty(reqParam) || "{}".equals(reqParam)) {
            Object[] obj = joinPoint.getArgs();
            if (obj.length > 0)
                reqParam = JSONObject.toJSONString(obj[0]);
        }
        return reqParam;
    }

    /**
     * 获取目标主机的ip
     *
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
