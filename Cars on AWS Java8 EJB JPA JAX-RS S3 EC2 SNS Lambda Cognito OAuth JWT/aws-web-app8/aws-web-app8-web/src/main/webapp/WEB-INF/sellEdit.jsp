<!DOCTYPE html>
<%@ page import="com.arif.CarsRepo" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="com.arif.car.jpa.CarStock" %>
<%@ page import="com.arif.car.jpa.Car" %>
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

            $(document).ready(function () {
                $(window).keydown(function (event) {
                    if (event.target.tagName != 'TEXTAREA') {
                        if (event.keyCode == 13) {
                            event.preventDefault();
                            return false;
                        }
                    }
                });
            });
            
            $(document).ready(function(){
                $(document).on("click", '.scriptpost', postfunction);
            });            
            
            function postfunction(e){
                var r = confirm($(this).text());
                if (r == true) {
                    targetUrl = $(this).attr('href');
                    $('<form/>').append().attr('action', targetUrl ).attr('method', 'POST').appendTo('body').submit();
                }
                return false; 
            };            
        </script>          
    </head>
    <body>

        <%@ include file="/WEB-INF/header.jsp" %>                 

        <div style="background-color:white;color:black;padding:0px;margin-top:0px;width:100%;overflow-x: hidden;">
            <div class="generic-container">
                <div class="generic-content">
                    
                    <div class="generic-form">
                        <form method="POST" class="register-form" id="register-form" action="/aws-web-app8-web/cars/v1/updateSell">
                            <input type="hidden" id="CarMake1" name="CarMake1" value="Ford">
                            <input type="hidden" id="CarStockId" name="CarStockId" value="${myCarDetails.stockId}">
                            <input type="hidden" id="CarStockVersion" name="CarStockVersion" value="${myCarDetails.version}">
                            <h2>Post details of you Car for Selling</h2>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="Make">Make :</label>
                                    <div class="form-select">
                                        <select name="Make" id="Make">
                                            <option value="${myCarDetails.car.make}" selected>${myCarDetails.car.make}</option>
                                            <c:forEach items="${carMakes}" var="carmake">
                                                <c:if test="${myCarDetails.car.make != carmake}">
                                                    <option value="${carmake}">${carmake}</option>
                                                </c:if>                                       
                                            </c:forEach>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="Model">Model :</label>
                                    <div class="form-select">
                                        <select name="Model" id="Model" name="Model">
                                            <option value="${myCarDetails.car.model}" selected>${myCarDetails.car.model}</option>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">                            
                                <div class="form-group">
                                    <label for="Price">Price :</label>
                                    <input type="text" name="Price" id="Price" name="Price" value="${myCarDetails.price}">
                                </div>
                                <div class="form-group">
                                    <label for="Kilometers">Kilometers :</label>
                                    <input type="text" name="Kilometers" id="Kilometers" name="Kilometers" value="${myCarDetails.kilometers}">
                                </div>
                            </div>
                            <div class="form-radio">
                                <label for="UsedNew" class="radio-label">Used New :</label>
                                <div class="form-radio-item">
                                    <c:if test="${myCarDetails.usage == 'Usd'}">
                                        <input type="radio" name="UsedNew" id="Usd" value="Usd" checked>
                                    </c:if>
                                    <c:if test="${myCarDetails.usage != 'Usd'}">
                                        <input type="radio" name="UsedNew" id="Usd" value="Usd">
                                    </c:if>
                                    <label for="Usd">Used</label>
                                    <span class="check"></span>
                                </div>
                                <div class="form-radio-item">
                                    <c:if test="${myCarDetails.usage == 'New'}">
                                        <input type="radio" name="UsedNew" id="New" value="New" checked>
                                    </c:if>
                                    <c:if test="${myCarDetails.usage != 'New'}">
                                        <input type="radio" name="UsedNew" id="New" value="New">
                                    </c:if>
                                    <label for="New">New</label>
                                    <span class="check"></span>
                                </div>
                            </div>                            
                            <div class="form-row"> 
                                <div class="form-group">
                                    <label for="Location">Location :</label>
                                    <div class="form-select">
                                        <select name="Location" id="Location" name="Location">
                                            <option value="${myCarDetails.location}" selected>${myCarDetails.location}</option>
                                            <c:forEach items="${auStates}" var="state">
                                                <c:if test="${myCarDetails.location != state}">
                                                    <option value="${state}">${state}</option>
                                                </c:if>                                       
                                            </c:forEach>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="Phone">Phone :</label>
                                    <input type="text" name="Phone" id="Phone" name="Phone" value="${myCarDetails.contactPhone}">
                                </div>
                                <div class="form-group">
                                    <label for="Year">Model Year :</label>
                                    <div class="form-select">
                                        <select name="Year" id="Year" name="Year">
                                            <option value="${myCarDetails.year}" selected>${myCarDetails.year}</option>
                                            <c:forEach items="${modelYears}" var="modelYear">
                                                <c:if test="${myCarDetails.year != modelYear}">
                                                    <option value="${modelYear}">${modelYear}</option>
                                                </c:if>                                       
                                            </c:forEach>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="Description">Description :</label>
                                <textarea name="Description" id="Description" name="Description" rows="8" style="width:100%;height:8em;">
                                    ${myCarDetails.description}
                                </textarea> 
                            </div>
                            <div class="form-submit">
                                <input type="submit" value="Reset All" class="submit" name="reset" id="reset" />
                                <input type="submit" value="Update Car" class="submit" name="submit" id="submit" />
                            </div>
                        </form>
                        <div class="register-form">
                            <c:if test="${!empty myCarDetails.photos}">
                                <h3>My Car Photos</h3>
                                <c:forEach items="${myCarDetails.photos}" var="carPhoto">
                                    <img width="300" src="${carPhoto.awsPreSignedUrl}" />
                                    <a href="/aws-web-app8-web/cars/v1/deletePhoto/${carPhoto.objectKey}" class="scriptpost"><span aria-hidden="true">Delete Photo</span></a>
                                </c:forEach>                            
                            </c:if>  
                        </div>

                        <c:if test="${fn:length(myCarDetails.photos) < 3}">
                            <form method="POST" enctype="multipart/form-data" class="register-form" id="register-form" action="/aws-web-app8-web/cars/v1/addPhoto/${myCarDetails.stockId}">
                                <h3>Upload Car Photos</h3>
                                <h5>Photos greater than 500KB will not be saved.</h5>
                                <c:forEach begin="1" end="${3 - fn:length(myCarDetails.photos)}" varStatus="loop">
                                    <input name="file" type="file" id="file">                                    
                                </c:forEach>      
                                <div class="form-submit">    
                                    <input type="submit" value="Reset All" class="submit" name="reset" id="reset" disabled/>    
                                    <input type="submit" value="Update Photos" class="submit" name="submit" id="submit" />
                                </div>
                            </form>
                        </c:if>
                            
                        <div class="register-form">
                            <!--upon click on below link a JQuery POST function will be executed defined on class scriptpost-->     
                            <a href="/aws-web-app8-web/cars/v1/deleteCar/${myCarDetails.stockId}" class="scriptpost">Delete this Car and any/all of its Photos</a>
                        </div>                            
                    </div>
                    
                </div>
            </div>
        </div>
    </body>
</html>