About this project:
This is a prototype to apply Spring AOP Aspect on selected classes and selected Methods using AspectJ pointcut expression.

0. After downloading the project in a directory 

1. Build the project using: mvn clean install

2. Run the project using: mvn exec:java

3. Examine the output from the LoggingAspect and confirm that the LoggingAspect is applied only on com.aus.arif.Employee class and the Aspect is not applied on com.aus.aziz.AccessCard class (as its in different package) because of the below give pointcut expression which explicitly select classes only in com.aus.arif package.
   
4. Examine the output from the LoggingAspect and confirm that the LoggingAspect is NOT applied on Employee.getSalary method because
   all methods annotated with @Secret annotation are excluded from the below give pointcut expression.
   
   
        @Around("execution(public * com.*.arif.*.get*()) "
            + "&& !@annotation(com.aus.annotations.Secret)")
   
==============================================================
5. If you like to apply LoggingAspect on both class i.e com.aus.arif.Employee and on com.aus.aziz.AccessCard then open
   class LoggingAspect.java in IDE and change the pointcut expression to:
   
   
        @Around("execution(public * com.aus.*.*.get*()) "
            + "&& !@annotation(com.aus.annotations.Secret)")
   
6. Then rebuild and run the project using step 1 and step 2 above.

https://www.youracclaim.com/users/arif-shaikh/badges
