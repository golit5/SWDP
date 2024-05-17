package lab;

import aop.Person;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAsp {

    @Around("execution(public boolean Menu.choose())")
    public Object AroundMenuChoose(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("Menu.choose() called");

        Object targetMethodResult = proceedingJoinPoint.proceed();

        System.out.println("Menu.choose() returned " + targetMethodResult.toString());

        return targetMethodResult;
    }
}