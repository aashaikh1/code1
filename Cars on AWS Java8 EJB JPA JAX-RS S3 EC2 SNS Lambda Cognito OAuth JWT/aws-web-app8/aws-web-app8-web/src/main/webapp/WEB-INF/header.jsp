<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.arif.util.AppUtil" %>
<div class="topnav">
    <a class="active" href="/aws-web-app8-web/">Cars</a>
    <a href="/aws-web-app8-web/">Home</a>
    <div class="topnav-right">

        <%
            if (AppUtil.isAuthenticated(session)) {
        %>
        <a>Hello ${sessionScope.nickname}</a>
        <a href="/aws-web-app8-web/cars/v1/sell">Sell</a>
        <a href="/aws-web-app8-web/cars/v1/myCars">MyCars</a>
        <%
        } else {
        %>
        <a href="/aws-web-app8-web/cars/v1/login">login</a>
        <%
            }
        %>

        <a href="/aws-web-app8-web/cars/v1/logout">logout</a>
        <a href="/aws-web-app8-web/about.jsp">About</a>
    </div>
</div>  



