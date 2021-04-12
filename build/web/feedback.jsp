<%-- 
    Document   : feedback.jsp
    Created on : Mar 6, 2021, 7:33:12 PM
    Author     : Shi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feed Back Page</title>
    </head>
    <body>
        <c:set var="welcome" value="${sessionScope.FULLNAME}"/>
        <c:if test="${not empty welcome}">
            Welcome <font color="blue"> ${welcome} </font></br>   
            <form action="logout" method="POST">
                <input type="submit" value="Log out" name="btAction" />
            </form>
        </c:if>
        <h1>Your Feedback</h1>
        <form action="insertFeedback" method="POST">
            Content: <input type="text" name="txtContent" value="" />
            Rating: <select name="cbRating">
                <option value="1" selected>1*</option>
                <option value="2" >2*</option>
                <option value="3" >3*</option>
                <option value="4" >4*</option>
                <option value="5" >5*</option>
                <option value="6" >6*</option>
                <option value="7" >7*</option>
                <option value="8" >8*</option>
                <option value="9" >9*</option>
                <option value="10" >10*</option>
                <input type="submit" value="Submit" />
                <input type="hidden" name="txtBillDetailId" value="${param.txtBillDetailId}" />
        </form>
        <a href="viewdetail">Go back</a>
    </body>
</html>
