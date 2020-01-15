package com.cjs.example.lock.constant;
import	java.lang.ref.Reference;

import com.cjs.example.lock.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
@ConditionalOnClass(RedissonClient.class)
public class RequestLockAspect {

    private ExpressionParser parser = new SpelExpressionParser();
    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Autowired
    private RedissonClient redissonClient;

    @Around("@annotation(requestLock)")
    public Object invoked(ProceedingJoinPoint point, RequestLock requestLock) throws Throwable {
        Object[] args = point.getArgs();
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        String spel = requestLock.key();
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        Expression expression = parser.parseExpression(spel);
        String key = expression.getValue(context, String.class);

        if(StringUtils.isBlank(key)){
            throw new ApiException("无法处理当前请求");
        }

        RLock lock = redissonClient.getLock(key);
        Boolean isLock = lock.tryLock(1,180,TimeUnit.SECONDS);
        if(!isLock){
            throw new ApiException("当前已有正在处理的请求,请稍后再试");
        }
        //调用目标方法
        try{
            return point.proceed();
        }catch (Exception e){
            throw e;
        }finally {
            log.info("释放锁------");
            //释放锁
            lock.unlock();
        }
    }
}
