package lab;

import org.aspectj.lang.ProceedingJoinPoint;

public class LoggingAsp {


    public Object AroundMenuChoose(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("Menu.choose() called");

        Object targetMethodResult = proceedingJoinPoint.proceed();

        System.out.println("Menu.choose() returned " + targetMethodResult.toString());

        return targetMethodResult;
    }
}