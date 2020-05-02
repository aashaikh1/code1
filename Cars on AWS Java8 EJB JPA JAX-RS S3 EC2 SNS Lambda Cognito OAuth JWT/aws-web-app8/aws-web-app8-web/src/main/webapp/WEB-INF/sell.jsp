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
        </script>          
    </head>
    <body>

        <%@ include file="/WEB-INF/header.jsp" %>                 

        <div style="background-color:white;color:black;padding:0px;margin-top:0px;width:100%;overflow-x: hidden;">
            <div class="generic-container">
                <div class="generic-content">
                    <div class="generic-form">
                        <form method="POST" class="register-form" id="register-form" action="/aws-web-app8-web/cars/v1/addSell">
                            <input type="hidden" id="CarMake1" name="CarMake1" value="Ford">
                            <h2>Post details of you Car for Selling</h2>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="Make">Make :</label>
                                    <div class="form-select">
                                        <select name="Make" id="Make" required>
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
                                        <select name="Model" id="Model" name="Model" required>
                                            <option value=""></option>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">                            
                                <div class="form-group">
                                    <label for="Price">Price :</label>
                                    <input type="text" name="Price" id="Price" name="Price" required>
                                </div>
                                <div class="form-group">
                                    <label for="Kilometers">Kilometers :</label>
                                    <input type="text" name="Kilometers" id="Kilometers" name="Kilometers" required>
                                </div>
                            </div>
                            <div class="form-radio">
                                <label for="UsedNew" class="radio-label">Used New :</label>
                                <div class="form-radio-item">
                                    <input type="radio" name="UsedNew" id="Usd" value="Usd" required checked>
                                    <label for="Usd">Used</label>
                                    <span class="check"></span>
                                </div>
                                <div class="form-radio-item">
                                    <input type="radio" name="UsedNew" id="New" value="New" required>
                                    <label for="New">New</label>
                                    <span class="check"></span>
                                </div>
                            </div>                            
                            <div class="form-row"> 
                                <div class="form-group">
                                    <label for="Location">Location :</label>
                                    <div class="form-select">
                                        <select name="Location" id="Location" name="Location" required>
                                            <option value=""></option>
                                            <option value="ACT">ACT</option>
                                            <option value="NSW">NSW</option>
                                        </select>
                                        <span class="select-icon"><i class="zmdi zmdi-chevron-down"></i></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="Phone">Phone :</label>
                                    <input type="text" name="Phone" id="Phone" name="Phone" required>
                                </div>
                                <div class="form-group">
                                    <label for="Year">Model Year :</label>
                                    <div class="form-select">
                                        <select name="Year" id="Year" name="Year" required>
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
                                <label for="Description">Description :</label>
                                <textarea name="Description" id="Description" name="Description" rows="8" style="width:100%;height:8em;">
Vehicle: 
Car Inspection Details: 
Price: 
Kilometers: 
Car History Report: 
Color: 
Transmission: 
Body: 
Engine: 
Registration Plate: 
Registration Status: 
Release Date: 
Build Date: 
Model Year: 
Other Details:
                                </textarea> 

                                <!--                                <input type="textarea" name="Description" id="Description" name="Description" rows="4" style="all:unset;width:100%;height:4em;"></textarea>-->
                            </div>
                            <div class="form-submit">
                                <input type="submit" value="Reset All" class="submit" name="reset" id="reset" />
                                <input type="submit" value="Add Car" class="submit" name="submit" id="submit" />
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>