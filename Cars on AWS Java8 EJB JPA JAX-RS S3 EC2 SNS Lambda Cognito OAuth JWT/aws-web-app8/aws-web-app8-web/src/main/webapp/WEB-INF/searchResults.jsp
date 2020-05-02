<!DOCTYPE html>
<%@ page import="com.arif.CarsRepo" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="com.arif.car.jpa.CarStock" %>
<%@ page import="com.arif.car.jpa.Car" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Car Search</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <script src="${pageContext.request.contextPath}/js/jquery.org.min.js"></script>
    </head>
    <body>

        <%@ include file="/WEB-INF/header.jsp" %>  
        <div style="background-color:white;color:black;padding:0px;margin-top:0px;width:100%;overflow-x: hidden;">
            <div class="generic-container">
                <div class="generic-content">
                    <div style="padding: 50px 100px 50px 70px;">
                        <h3 class="letterpress">Search Results</h3>

                        <c:forEach items="${searchResultCars}" var="car">
                            <table >
                                <tr> 
                                    <td>
                                        <c:if test="${!empty car.photos}">
                                            <a href="/aws-web-app8-web/cars/v1/cardetail/${car.stockId}">
                                                <img width="300" src="${car.photos.toArray()[0].awsPreSignedUrl}" />
                                            </a>
                                        </c:if>
                                        <c:if test="${empty car.photos}">
                                            <a href="/aws-web-app8-web/cars/v1/cardetail/${car.stockId}">
                                                <img width="300" src="${pageContext.request.contextPath}/images/noimage.png" />
                                            </a>
                                        </c:if>
                                    </td>

                                    <td cellpadding="10">
                                        <h3>
                                            <pre>
   Make: ${car.car.make}    Model: ${car.car.model}    Usage: ${car.usage}
   Year: ${car.year}    Price: ${car.price}    Location: ${car.location}
   Phone: ${car.contactPhone}    Email: ${car.contactEmail}
                                            </pre>
                                        </h3>
                                    </td>
                                </tr>
                            </table>
                        </c:forEach>  
                        <%                            
                            if (request.getAttribute("searchedCarsCurrPageNum") == null) {
                                request.setAttribute("searchedCarsCurrPageNum", new Integer(0));
                            }
                        %>                        
                        <form method="POST">
                            <input type="hidden" id="CarMake1" name="CarMake1" value="Ford">
                            <input type="hidden" id="Make" name="Make" value="${param.Make}">
                            <input type="hidden" id="Model" name="Model" value="${param.Model}">
                            <input type="hidden" id="Location" name="Location" value="${param.Location}">
                            <input type="hidden" id="PriceMin" name="Make" PriceMin="${param.PriceMin}">
                            <input type="hidden" id="PriceMin" name="PriceMin" value="${param.PriceMin}">
                            <input type="hidden" id="PriceMax" name="PriceMax" value="${param.PriceMax}">
                            <input type="hidden" id="Year" name="Year" value="${param.Year}">
                            <input type="hidden" id="UsedNew" name="UsedNew" value="${param.UsedNew}">
                            <input type="hidden" id="Keywords" name="Keywords" value="${param.Keywords}">
                            <input type="hidden" id="scCurrPageNum" name="scCurrPageNum" value="${requestScope.searchedCarsCurrPageNum}">

                            <button type="submit" class="buttonAsLink" formaction="/aws-web-app8-web/cars/v1/prevSearch">Prev</button>
                            <button type="submit" class="buttonAsLink" formaction="/aws-web-app8-web/cars/v1/nextSearch">Next</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


    </body>
</html>