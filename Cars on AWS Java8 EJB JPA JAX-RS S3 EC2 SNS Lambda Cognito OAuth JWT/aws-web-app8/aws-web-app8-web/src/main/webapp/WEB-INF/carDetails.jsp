<!DOCTYPE html>
<%@ page import="com.arif.CarsRepo" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="com.arif.car.jpa.CarStock" %>
<%@ page import="com.arif.car.jpa.Car" %>
<%@ page import="com.arif.car.jpa.Photo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Car Search</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/slider-styles.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
        <script src="${pageContext.request.contextPath}/js/slider-jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/slider.js"></script>        
    </head>
    <body>
        <%@ include file="/WEB-INF/header.jsp" %>   
        <div style="background-color:white;color:black;padding:0px;margin-top:0px;width:100%;overflow-x: hidden;">
            <div class="generic-container">
                <div class="generic-content">
                    <div style="padding: 10px 100px 50px 70px;">
                        <%                            
                            CarStock car = (CarStock) request.getAttribute("carStock");
                        %>                        
                        <h3 class="letterpress2">${carStock.car.make} &nbsp; ${carStock.car.model} </h3>

                        <table >
                            <tr> 
                                <td>
                                    <c:if test="${fn:length(carStock.photos) > 1}">
                                        <div class="sliderContainer">
                                            <%
                                                if(car.getPhotos() != null){
                                                    for (Photo photo : car.getPhotos()) {
                                            %>                        
                                            <img width="300" src="<%= photo.getAwsPreSignedUrl()%>">
                                            <%
                                                    }
                                                }
                                            %>
                                            <div class="nextCircle">
                                                <i class="fa fa-arrow-right next" aria-hidden="true"></i>
                                            </div>
                                            <div class="prevCircle">
                                                <i class="fa fa-arrow-left previous" aria-hidden="true"></i>
                                            </div>
                                        </div>  
                                    </c:if>                                             
                                    <c:if test="${fn:length(carStock.photos) == 1}">
                                        <div class="sliderContainer">
                                            <img width="300" src="${carStock.photos.toArray()[0].awsPreSignedUrl}" />
                                        </div> 
                                    </c:if>                                      
                                    <c:if test="${fn:length(carStock.photos) < 1}">
                                        <div class="sliderContainer">
                                            <img width="300" src="${pageContext.request.contextPath}/images/noimage.png" />
                                        </div> 
                                    </c:if>                                      
                                </td>

                                <td>
                                    <pre>
   Make: ${carStock.car.make}    Model: ${carStock.car.model}    Usage: ${carStock.usage}
   Year: ${carStock.year}    Price: ${carStock.price}    Location: ${carStock.location}
   Phone: ${carStock.contactPhone}    Email: ${carStock.contactEmail}
                                    </pre>         
                                    
                                    <% 
                                        if(car.getCognitoUsername().equals(request.getSession(false).getAttribute("cognitousername")))
                                        {
                                    %>
                                    <a href="/aws-web-app8-web/cars/v1/editSell/${carStock.stockId}">Edit</a>
                                    <% 
                                        }
                                    %>
                                </td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td>
                                    <pre>
   Details: ${carStock.description} 
                                    </pre>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>