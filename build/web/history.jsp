<%-- 
    Document   : history
    Created on : Mar 4, 2021, 9:59:46 PM
    Author     : Shi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:set var="welcome" value="${sessionScope.FULLNAME}"/>
        <c:if test="${not empty welcome}">
            Welcome <font color="blue"> ${welcome} </font></br>   
            <form action="logout" method="POST">
                <input type="submit" value="Log out" name="btAction" />
            </form>
        </c:if>
        <h1>Your History</h1>
        <form action="searchHistory" method="POST">
            Search: 
            <input type="text" name="txtCarName" value="${param.txtCarName}" />
            <input type="date" name="calendar" value="${param.date}" />
            <input type="submit" value="Search"/>
        </form>
        <c:set var="welcome" value="${sessionScope.FULLNAME}"/></br>
        <c:set var="user" value="${sessionScope.USER}"/>
        <c:set var="list" value="${sessionScope.BILLLIST}"/>
        <c:if test="${not empty user}">
            <c:if test="${not empty list}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Bill Serial</th>
                            <th>Create Date</th>
                            <th>Bill Detail</th>
                            <th>Rental Date</th>
                            <th>Return Date</th>
                            <th>Discount</th>
                            <th>Total Price</th>
                            <th>Status</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bill" items="${list}" varStatus="counter">
                            <tr>
                                <td>${counter.count}.</td>
                                <td>${bill.billId}</td>
                                <td>${bill.createDate}</td>
                                <td>
                                    <c:url var="viewdetail" value="viewdetail">
                                        <c:param name="billId" value="${bill.billId}"/>
                                    </c:url>
                                    <a href="${viewdetail}">View Detail</a>
                                </td>
                                <td>${bill.rentalDate}</td>
                                <td>${bill.returnDate}</td>
                                <td>
                                    <c:if test="${not empty bill.discountCode.saleOff}">
                                        ${bill.discountCode.saleOff*100}%
                                    </c:if>
                                </td>
                                <td>${bill.totalPrice}</td>
                                <td>${bill.status}</td>
                                <td>
                                    <c:url var="deleteHistory" value="deleteHisory">
                                        <c:param name="billId" value="${bill.billId}"></c:param>
                                    </c:url>
                                    <a href="${deleteHistory}">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty list}">
                Empty History
            </c:if>
            <a href="search">Go back to shopping</a> 
        </c:if>
    </body>
</html>
