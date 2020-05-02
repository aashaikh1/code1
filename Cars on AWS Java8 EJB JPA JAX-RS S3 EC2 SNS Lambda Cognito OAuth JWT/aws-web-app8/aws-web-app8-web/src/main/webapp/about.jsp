<!DOCTYPE html>
<%@ page import="com.arif.SNSNotificationLocal" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Car Search</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            .container {
                position: absolute;
                left: 50%;	
                top: 50%;
                -webkit-transform: translateX(-50%) translateY(-50%);
                -moz-transform: translateX(-50%) translateY(-50%);
                transform: translateX(-50%) translateY(-50%);
            }
        </style>
    </head>
    <body>
        <%@ include file="/WEB-INF/header.jsp" %> 
        
        <%                            
            com.arif.SNSNotificationLocal awsSNS = null;
            Context context = null;
            try {
                context = new InitialContext();
                awsSNS = (SNSNotificationLocal) context.lookup("java:app/com.arif-aws-web-app8-ejb-1.0-SNAPSHOT/SNSNotification!com.arif.SNSNotificationLocal");
                awsSNS.send("ArifCarsWebApp about.jsp Visited");
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>                        
        
        <div class="container">
            <span>
                <center>This web application is developed by</center>
                <center><a style="text-decoration: none;" href="https://www.youracclaim.com/users/arif-shaikh/badges" target="_blank"><h3 class="letterpress2">Arif Shaikh</h3></a></center>
                <center>Java/JEE Developer</center>
                <center>Using below technologies:</center>
                <center>Core Java8</center>
                <center>JEE - EJB, JPA, JSP/Servlets, JAX-RS</center>
                <center>HTML, JQuery</center>
                <center>Amazon AWS - EC2, Cognito/OAuth, S3, SNS, Lambda, SES, RDS-MySQL, AWS-Java-SDK</center>
                <center>OAuth, JOSE, JWT (JSON Web Token), Maven, Linux, Glassfish</center>
            </span>
        </div>
    </body>
</html>