package cgtonboardingmoviesmain.moviesApi;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* cgtonboardingmoviesmain.moviesApi.controller.*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();

        String argsString = Arrays.stream(args)
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        logger.info("Method "+methodName+" in class "+className+" is called with arguments: "+argsString);

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        logger.info("Method "+methodName+" in class "+className+" executed in "+executionTime+" ms with result: "+result);

        return result;
    }
}
