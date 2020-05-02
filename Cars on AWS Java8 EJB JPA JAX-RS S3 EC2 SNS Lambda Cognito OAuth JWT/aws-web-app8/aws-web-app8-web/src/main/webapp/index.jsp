<%@page import="com.arif.AppContext"%>
<!DOCTYPE html>
<%@ page import="com.arif.CarsRepo" %>
<%@ page import="com.arif.SNSNotificationLocal" %>
<%@ page import="com.arif.AppContext" %>
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

        <script>
            $(document).on("change", "#Make", function () {
                var params = {Make: $("#Make option:selected").text()};
                $.get("${pageContext.request.contextPath}/cars/v1/model", $.param(params), function (responseJson) {
                    var $select = $("#Model");
                    $select.find("option").remove();
                    $.each(responseJson, function (index, model) {
                        $("<option>").val(model.id).text(model.name).appendTo($select);
                    });
                });
            });
        </script>          
    </head>
    <body>

        <%@ include file="/WEB-INF/header.jsp" %>                 
        <%                            
            if (request.getAttribute("carMakes") == null) {
                List<String> carMakes = AppContext.getInstance().getDistinctCarMake();
                request.setAttribute("carMakes", carMakes);
            }
            if (request.getAttribute("auStates") == null) {
                List<String> auStates = AppContext.getInstance().getAuStates();
                request.setAttribute("auStates", auStates);
            }
        %>                        
        
        <div style="background-color:white;color:black;padding:0px;margin-top:0px;width:100%;overflow-x: hidden;">
            <div class="generic-container">
                <div class="generic-content">
                    <div class="generic-form">
                        <form method="POST" class="register-form" id="register-form" action="/aws-web-app8-web/cars/v1/search">
                            <input type="hidden" id="CarMake1" name="CarMake1" value="Ford">
                            <h2>Car Search</h2>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="Make">Make :</label>
                                    <div class="form-select">
                                        <select name="Make" id="Make">
                                            <option value=""></option>
                                            <c:forEach items="${carMakes}" var="carmake">
                                                <option value="${carmake}">${carmake}</option>
                                            </c:forEach>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="Model">Model :</label>
                                    <div class="form-select">
                                        <select name="Model" id="Model" name="Model">
                                            <option value=""></option>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="PriceMin">Price Min :</label>
                                    <div class="form-select">
                                        <select name="PriceMin" id="PriceMin" name="PriceMin">
                                            <option value=""></option>
                                            <option value="10000">10,000</option>
                                            <option value="15000">15,000</option>
                                            <option value="20000">20,000</option>
                                            <option value="25000">25,000</option>
                                            <option value="30000">30,000</option>
                                            <option value="35000">35,000</option>
                                            <option value="40000">40,000</option>
                                            <option value="45000">45,000</option>
                                            <option value="50000">50,000</option>
                                            <option value="75000">75,000</option>
                                            <option value="85000">85,000</option>
                                            <option value="100000">100,000</option>
                                            <option value="125000">125,000</option>
                                            <option value="150000">150,000</option>
                                            <option value="175000">175,000</option>
                                            <option value="200000">200,000</option>
                                            <option value="225000">225,000</option>
                                            <option value="250000">250,000</option>
                                            <option value="300000">300,000</option>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="PriceMax">Price Max :</label>
                                    <div class="form-select">
                                        <select name="PriceMax" id="PriceMax" name="PriceMax">
                                            <option value=""></option>
                                            <option value="10000">10,000</option>
                                            <option value="15000">15,000</option>
                                            <option value="20000">20,000</option>
                                            <option value="25000">25,000</option>
                                            <option value="30000">30,000</option>
                                            <option value="35000">35,000</option>
                                            <option value="40000">40,000</option>
                                            <option value="45000">45,000</option>
                                            <option value="50000">50,000</option>
                                            <option value="75000">75,000</option>
                                            <option value="85000">85,000</option>
                                            <option value="100000">100,000</option>
                                            <option value="125000">125,000</option>
                                            <option value="150000">150,000</option>
                                            <option value="175000">175,000</option>
                                            <option value="200000">200,000</option>
                                            <option value="225000">225,000</option>
                                            <option value="250000">250,000</option>
                                            <option value="300000">300,000</option>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="UsedNew">UsedNew :</label>
                                    <div class="form-select">
                                        <select name="UsedNew" id="UsedNew">
                                            <option value="Any">Any</option>
                                            <option value="Usd">Used</option>
                                            <option value="New">New</option>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                            
                                <div class="form-group">
                                    <label for="Year">Model Year :</label>
                                    <div class="form-select">
                                        <select name="Year" id="Year" name="Year">
                                            <option value=""></option>
                                            <option value="2020">2020</option>
                                            <option value="2019">2019</option>
                                            <option value="2018">2018</option>
                                            <option value="2017">2017</option>
                                            <option value="2016">2016</option>
                                            <option value="2015">2015</option>
                                            <option value="2014">2014</option>
                                            <option value="2013">2013</option>
                                            <option value="2012">2012</option>
                                            <option value="2011">2011</option>
                                            <option value="2010">2010</option>
                                            <option value="2009">2009</option>
                                            <option value="2008">2008</option>
                                            <option value="2007">2007</option>
                                            <option value="2006">2006</option>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="Location">Location :</label>
                                <div class="form-select">
                                    <select name="Location" id="Location" name="Location">
                                        <option value=""></option>
                                        <c:forEach items="${auStates}" var="auState">
                                            <option value="${auState}">${auState}</option>
                                        </c:forEach>
                                    </select>
                                    <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                </div>
                            </div>
                            <div class="form-submit">
                                <input type="submit" value="Reset All" class="submit" name="reset" id="reset" />
                                <input type="submit" value="Submit Form" class="submit" name="submit" id="submit" />
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <div style="background-color:white;color:black;padding:0px;margin-top:0px;width:100%;overflow-x: hidden;">
            <div class="generic-container">
                <div class="generic-content">
                    <div style="padding: 0px 100px 0px 70px;">
                        <h3 class="letterpress">Featured Cars</h3>
                        <%!
                            //no class level variables
                        %>
                        <%                            
                            com.arif.CarsRepo carsRepo = null;
                            com.arif.SNSNotificationLocal awsSNS = null;
                            List<CarStock> featuredCars = new ArrayList();

                            if (request.getAttribute("featuredCars") == null) {
                                Context context = null;
                                try {
                                    context = new InitialContext();
                                    carsRepo = (CarsRepo) context.lookup("java:app/com.arif-aws-web-app8-ejb-1.0-SNAPSHOT/EJBCarsRepo!com.arif.CarsRepo");
                                    awsSNS = (SNSNotificationLocal) context.lookup("java:app/com.arif-aws-web-app8-ejb-1.0-SNAPSHOT/SNSNotification!com.arif.SNSNotificationLocal");
                                    awsSNS.send("ArifCarsWebApp index.jsp Visited");
                                    featuredCars = carsRepo.getFeaturedCars(0);
                                    request.setAttribute("featuredCars", featuredCars);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            if (request.getAttribute("featuredCarsCurrPageNum") == null) {
                                request.setAttribute("featuredCarsCurrPageNum", new Integer(0));
                            }
                        %>                        

                        <c:forEach items="${featuredCars}" var="car">
                            <table>
                                <tbody>
                                    <tr>
                                        <td rowspan="0">
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
                                    <tr>
                                        <td cellpadding="10"></td>
                                    </tr>
                                </tbody>                                
                            </table>
                        </c:forEach>                        
                        <a href="/aws-web-app8-web/cars/v1/prev?fcCurrPageNum=${requestScope.featuredCarsCurrPageNum}">Prev</a>
                        <a href="/aws-web-app8-web/cars/v1/next?fcCurrPageNum=${requestScope.featuredCarsCurrPageNum}">Next</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>