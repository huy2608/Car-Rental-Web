<%-- 
    Document   : viewCart
    Created on : Feb 28, 2021, 3:22:40 PM
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
        <h1>Your Cart</h1>
        <c:if test="${not empty sessionScope}">
            <c:set var="welcome" value="${sessionScope.FULLNAME}"/></br>
            <c:if test="${not empty welcome}">
                Welcome <font color="blue"> ${welcome} </font>
                <form action="logout" method="POST">
                    <input type="submit" value="Log out"/></br>
                </form>
                <form action="viewHistory" method="POST">
                    <input type="submit" value="History" /></br>
                </form>
            </c:if>

            <c:set var="rentDay" value="${sessionScope.RENTDATE}"/>
            <c:set var="cart" value="${sessionScope.CARCART}"/>
            <c:if test="${not empty cart}">
                <c:set var="item" value="${cart.carCart}"/>
                <c:if test="${not empty item}">
                    RentalDate: ${sessionScope.RENTALDATE}</br>
                    ReturnDate: ${sessionScope.RETURNDATE}</br>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Total</th>
                                <th>Remove</th>
                            </tr>
                        </thead>
                        <tbody>

                            <c:forEach var="itemKey" items="${item}" varStatus="counter">
                            <form action="updateQuantity" method="POST">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>
                                        ${itemKey.value.name}
                                        <input type="hidden" name="txtId" value="${itemKey.value.id}" />
                                    </td>
                                    <td>
                                        <input type="number" name="txtQuantity" min="1" max="1000"value="${itemKey.value.quantity}" />$
                                        <input type="submit" value="Update Quantity"/>
                                    </td>
                                    <td>
                                        <c:set var="price" value="${itemKey.value.price}" />
                                        <c:set var="quantity" value="${itemKey.value.quantity}"/>
                                        ${price}$
                                    </td>
                                    <td>
                                        ${price*quantity*rentDay}$
                                    </td>
                                    <td>
                                        <c:url var="remove" value="removeCarFromCart">
                                            <c:param name="txtCarId" value="${itemKey.value.id}"/>
                                            <c:param name="page" value="${currentPage}"/>
                                        </c:url>
                                        <a href="${remove}" onclick="return confirm('Are you sure you want to remove')">Remove</a>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                    </tbody>
                </table>
                <c:if test="${not empty sessionScope.TOTALPRICEOFBILL}">
                    Total: ${sessionScope.TOTALPRICEOFBILL}$
                </c:if>
                <form action="checkout" method="POST">
                    <input type="submit" value="Check out"/>
                </form>
            </c:if>
            <form action="inputDiscount" method="POST">
                <input type="text" name="txtDiscountCode" value="${param.txtDiscountCode}" />
                <input type="submit" value="Submit" />
            </form>
        </c:if>
    </c:if>
    <c:if test="${not empty requestScope.CHECKOUTERROR}">
        <font color="red"> ${requestScope.CHECKOUTERROR}</font></br>
    </c:if>

    <c:if test="${not empty requestScope.DISCOUNTERROR}">
        ${requestScope.DISCOUNTERROR}
    </c:if>
    <a href="search">Go back to shopping</a> 
</body>
</html>
