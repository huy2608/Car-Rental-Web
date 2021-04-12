<%-- 
    Document   : update
    Created on : Jan 9, 2021, 7:11:35 PM
    Author     : Shi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Page</title>
    </head>
    <body>
        <c:set var="welcome" value="${sessionScope.FULLNAME}"/>
        <c:if test="${not empty welcome}">
            Welcome <font color="blue"> ${welcome} </font></br>   
        </c:if>
        <form action="create" method="POST">
            <c:set var="error" value="${requestScope.ERRORFILE}"/>
            Id: <input type="text" minlength="1" maxlength="50" name="txtId" value="${param.txtId}"/></br>
            <c:if test="${not empty requestScope.IDLENGTHERROR}">
                <font color="red" >${requestScope.IDLENGTHERROR}</font></br>
            </c:if> 
            <c:if test="${not empty requestScope.IDEXISTED}">
                <font color="red" >${requestScope.IDEXISTED}</font></br>
            </c:if>
            Name: <input type="text" minlength="1" maxlength="256" name="txtName" value="${param.txtName}" /></br>
            <c:if test="${not empty requestScope.NAMELENGTHERROR}">
                <font color="red" >${requestScope.NAMELENGTHERROR}</font></br>
            </c:if>
            Color: <input type="text" minlength="1" maxlength="20" name="txtColor" value="${param.txtColor}"/></br>
            <c:if test="${not empty requestScope.COLORLENGTHERROR}">
                <font color="red" >${requestScope.COLORLENGTHERROR}</font></br>
            </c:if>
            Year: <input type="number" name="txtYear" value="${param.txtYear}"/></br>
            <c:if test="${not empty requestScope.YEARLENGTHERROR}">
                <font color="red" >${requestScope.YEARLENGTHERROR}</font></br>
            </c:if>
            Price: <input type="number" min="0" max="1000" step="0.01" name="txtPrice" value="${param.txtPrice}" required="required"/></br>
            <c:if test="${not empty requestScope.PRICELENGTHERROR}">
                <font color="red" >${requestScope.PRICELENGTHERROR}</font></br>
            </c:if>
            Category: <select name="cbCategory">
                <option value="" >--None--</option>
                <c:set var="category" value="${sessionScope.CATEGORYLIST}"/>
                <c:if test="${not empty category}">
                    <c:forEach var="categoryList" items="${category}">
                        <option value="${categoryList}" <c:if test="${param.cbCategory eq categoryList}">selected</c:if>>${categoryList}</option>
                    </c:forEach>
                </c:if>
            </select></br>
            Quantity: <input type="number" min="0" max="1000" name="txtQuantity" value="${param.txtQuantity}"/></br>
            <c:if test="${not empty requestScope.QUANTITYLENGTHERROR}">
                <font color="red" >${requestScope.QUANTITYLENGTHERROR}</font></br>
            </c:if>
            <input type="submit" value="Create"/>
        </form>
        <a href="searchPage">Return to search page</a>
    </body>
</html>
