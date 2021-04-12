<%-- 
    Document   : billdetail
    Created on : Mar 6, 2021, 12:38:42 PM
    Author     : Shi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bill Detail Page</title>
    </head>
    <body>
        <c:set var="welcome" value="${sessionScope.FULLNAME}"/>
        <c:if test="${not empty welcome}">
            Welcome <font color="blue"> ${welcome} </font></br>   
            <form action="logout" method="POST">
                <input type="submit" value="Log out" name="btAction" />
            </form>
        </c:if>
        <h1>Bill Detail</h1>
        <c:set var="list" value="${sessionScope.BILLDETAILLIST}"/>
        <c:if test="${not empty list}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Car Name</th>
                        <th>Rental Date</th>
                        <th>Return Date</th>
                        <th>Amount</th>
                        <th>Total Price</th>
                        <th>Feed Back</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="billDetail" items="${list}">
                        <tr>
                            <td>${billDetail.cardto.name}</td>
                            <td>${billDetail.rentalDate}</td>
                            <td>${billDetail.returnDate}</td>
                            <td>${billDetail.totalCar}</td>
                            <td>${billDetail.totalPrice}</td>
                            <td>
                                <c:set var="feedbacklist" value="${sessionScope.FEEDBACKLIST}"/>
                                <c:forEach var="feedback" items="${feedbacklist[billDetail.billDetailId]}">
                                    <table border="1">
                                        <thead>
                                            <tr>
                                                <th>Content</th>
                                                <th>Rating</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>${feedback.content}</td>
                                                <td>${feedback.rating}*</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </c:forEach>
                            </td>
                            <td>
                                <c:url var="feedback" value="feedbackPage">
                                    <c:param name="txtBillDetailId" value="${billDetail.billDetailId}"/>
                                </c:url>
                                <a href="${feedback}">Feedback</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <a href="viewHistory">Go back</a>
    </body>
</html>
