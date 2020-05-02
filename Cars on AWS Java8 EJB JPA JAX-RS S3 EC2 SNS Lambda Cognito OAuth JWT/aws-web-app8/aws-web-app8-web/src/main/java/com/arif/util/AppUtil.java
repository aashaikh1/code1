package com.arif.util;

import com.arif.CarSearchCriteria;
import java.time.Instant;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.arif.car.jpa.CarStock;
import com.arif.car.jpa.Car;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author arif
 */
public class AppUtil {

    private static final int CSRF_LENGTH = 16;

    public static String getCSRFString() {
        StringBuffer stringBuilder = new StringBuffer();
        Random random = new Random();
        while (stringBuilder.length() < CSRF_LENGTH) {
            stringBuilder.append(Integer.toHexString(random.nextInt()));
        }
        return stringBuilder.toString().substring(0, CSRF_LENGTH);
    }

    public static boolean isAuthenticated(HttpSession session) {
        if (session == null || session.getAttribute("nickname") == null) {
            return false;
        }
        if (session.getAttribute("exp") == null) {
            return false;
        } else {
            if (getRemainingExpirySeconds((Long) session.getAttribute("exp")) <= 0) {
                session.invalidate();
                return false;
            }
        }
        return true;
    }

    public static boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!AppUtil.isAuthenticated(request.getSession(false))) {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                return false;
            }
            return true;
        } catch (ServletException|IOException e) {
            throw new RuntimeException("Problem in AppUtil.isAuthenticated", e);
        }
    }

    public static void setUserInSession(HttpSession session, Map claims) {
        if (session == null || claims == null) {
            return;
        }
        session.setAttribute("nickname", claims.get("nickname"));
        session.setAttribute("cognitousername", claims.get("cognito:username"));
        session.setAttribute("exp", claims.get("exp"));
        session.setAttribute("email", claims.get("email"));
    }

    public static long getRemainingExpirySeconds(Long timeStamp) {
        try {
            Instant exp = Instant.ofEpochSecond(timeStamp);
            return exp.getEpochSecond() - Instant.now().getEpochSecond();
        } catch (Exception e) {
            System.out.println("Exception with expiry time " + e);
        }
        return -1;
    }

    public static CarSearchCriteria extractRequestParams(HttpServletRequest request) {
        CarSearchCriteria searchCriteria = new CarSearchCriteria();
        searchCriteria.setMake(request.getParameter("Make"));
        searchCriteria.setModel(request.getParameter("Model"));
        searchCriteria.setLocation(request.getParameter("Location"));
        searchCriteria.setPriceMin(request.getParameter("PriceMin"));
        searchCriteria.setPriceMax(request.getParameter("PriceMax"));
        searchCriteria.setNewCar(request.getParameter("UsedNew"));
        searchCriteria.setKeywords(request.getParameter("Keywords"));
        searchCriteria.setDescription(request.getParameter("Description"));
        searchCriteria.setPhone(request.getParameter("Phone"));
        searchCriteria.setModelYear(request.getParameter("Year"));
        searchCriteria.setEmail((String) request.getSession(false).getAttribute("email"));
        searchCriteria.setCognitoUsername((String) request.getSession(false).getAttribute("cognitousername"));
        return searchCriteria;
    }

    public static CarStock buildCarStock(HttpServletRequest request) {
        CarStock carStock = new CarStock();
        Car car = new Car();
        car.setMake(request.getParameter("Make"));
        car.setModel(request.getParameter("Model"));
        carStock.setStockId(request.getParameter("CarStockId"));
        if (request.getParameter("CarStockVersion") != null) {
            carStock.setVersion(Integer.parseInt(request.getParameter("CarStockVersion")));
        }
        carStock.setLocation(request.getParameter("Location"));
        carStock.setPrice(Integer.parseInt(request.getParameter("Price")));
        carStock.setUsage(request.getParameter("UsedNew"));
        carStock.setYear(Integer.parseInt(request.getParameter("Year")));
        carStock.setKilometers(Integer.parseInt(request.getParameter("Kilometers")));
        carStock.setKeywords(request.getParameter("Keywords"));
        carStock.setDescription(request.getParameter("Description"));
        carStock.setContactPhone(request.getParameter("Phone"));
        carStock.setContactEmail((String) request.getSession(false).getAttribute("email"));
        carStock.setCognitoUsername((String) request.getSession(false).getAttribute("cognitousername"));
        carStock.setCar(car);
        return carStock;
    }

    public static String getClientIP(HttpServletRequest request) {
        String clientIP = request.getHeader("X-FORWARDED-FOR");
        if (clientIP == null || "".endsWith(clientIP.trim())) {
            clientIP = request.getRemoteAddr();
        }
        return clientIP;
    }

}
