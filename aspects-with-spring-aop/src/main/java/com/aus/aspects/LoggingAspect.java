package com.aus.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


@Aspect
@Component
@EnableAspectJAutoProxy
public class LoggingAspect {

//        Use below pointcut to apply advice on all classes in com.aus package on get method without param
//        @Around("execution(public * com.aus.*.*.get*()) "
//            + "&& !@annotation(com.aus.annotations.Secret)")

    
        @Around("execution(public * com.*.arif.*.get*()) "
            + "&& !@annotation(com.aus.annotations.Secret)")
	public Object monitor(ProceedingJoinPoint joinPoint) throws Throwable {
                Object returnValue = null;
		try {
			returnValue = joinPoint.proceed();
                        return returnValue;
		} finally {
                    //replace below System.out with appropriate Logger entry when u want to log in a log file instead
                    System.out.println("Around advice on class: "+ joinPoint.getTarget().getClass() +"; After returning from method: " 
                            + joinPoint.getSignature().getName() + " Return Value: " + returnValue); 
		}
	}
        
}