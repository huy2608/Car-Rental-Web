<%-- 
    Document   : search.jsp
    Created on : Feb 26, 2021, 7:30:50 PM
    Author     : Shi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <h1>Car Rental</h1>
        <c:set var="welcome" value="${sessionScope.FULLNAME}"/>
        <c:if test="${not empty welcome}">
            Welcome <font color="blue"> ${welcome} </font></br>   
            <form action="logout" method="POST">
                <input type="submit" value="Log out" name="btAction" />
            </form>
            <form action="viewHistory" method="POST">
                <input type="submit" value="History" />
            </form>
        </c:if>
        <c:if test="${not empty sessionScope.ADMIN}">
            <a href="createPage" style="font-size: 40px;">Create New Food</a>
        </c:if>
        <form action="search" method="POST">
            Search: <input type="text" minlength="0" maxlength="256" name="txtName" value="${param.txtName}" /></br>
            Rental Date: <input type="date" name="rentalDate" value="${param.rentalDate}" /></br>
            <c:if test="${not empty requestScope.RENTALDATEERROR}">
                <font color="red"> ${requestScope.RENTALDATEERROR}</font></br>
            </c:if>
            Return Date: <input type="date" name="returnDate" value="${param.returnDate}" /></br>
            <c:if test="${not empty requestScope.RETURNDATEERROR}">
                <font color="red"> ${requestScope.RETURNDATEERROR}</font></br>
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
            Amount: <input type="number" name="amount" value="${param.amount}" /></br>
            <c:if test="${not empty requestScope.AMOUNTERROR}">
                <font color="red"> ${requestScope.AMOUNTERROR}</font></br>
            </c:if>
            <input type="submit" value="Search" name="btAction" />
        </form>
        <c:set var="date" value="${requestScope.DATEERROR}"/>
        <c:if test="${not empty date}">
            <font color="red"> ${date}</font>
        </c:if>
        <c:if test="${not empty requestScope.ERROR}">
            <font color="red"> ${requestScope.ERROR}</font></br>
        </c:if>
        <c:set var="carlist" value="${sessionScope.CARLIST}"/>
        <c:if test="${not empty carlist}">
            <c:if test="${empty requestScope.SEARCHERROR}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Color</th>
                            <th>Year</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Quantity</th>
                                <c:if test="${empty sessionScope.ADMIN}">
                                <th></th>
                                </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${carlist}" varStatus="counter">
                            <tr>
                                <td>${counter.count}.</td>
                                <td>
                                    ${dto.name}    
                                </td>
                                <td>
                                    ${dto.color} 
                                </td>
                                <td>
                                    ${dto.year} 
                                </td>
                                <td>
                                    ${dto.category}
                                </td>
                                <td>
                                    ${dto.price}$
                                </td>
                                <td>
                                    ${dto.quantity}
                                </td>
                                <c:if test="${empty sessionScope.ADMIN}">
                                    <td>
                                        <form action="AddToCart" method="POST">
                                            <input type="submit" value="Add To Cart"/>
                                            <input type="hidden" name="carId" value="${dto.id}"/>
                                            <input type="hidden" name="carName" value="${dto.name}"/>
                                            <input type="hidden" name="carColor" value="${dto.color} "/>
                                            <input type="hidden" name="carYear" value="${dto.year} "/>
                                            <input type="hidden" name="carCategory" value="${dto.category} "/>
                                            <input type="hidden" name="carPrice" value="${dto.price} "/>
                                            <input type="hidden" name="searchName" value="${param.txtName}" />
                                            <input type="hidden" name="searchRentalDate" value="${param.rentalDate}" />
                                            <input type="hidden" name="searchReturnDate" value="${param.returnDate}" />
                                            <input type="hidden" name="searchCategory" value="${param.cbCategory}" />
                                            <input type="hidden" name="searchAmount" value="${param.amount}" />
                                        </form>
                                    </td>
                                </c:if>
                            </tr>
                        </tbody>
                    </c:forEach>
                </table>

                <%--For displaying Previous link except for the 1st page --%>
                <c:set var="currentPage" value="${sessionScope.CURRENTPAGE}"/> 
                <c:set var="noOfPage" value="${sessionScope.NOOFPAGE}"/>
                <c:if test="${currentPage != 1}">
                <td>
                    <c:url var="searchFood" value="">          
                        <c:param name="txtName" value="${param.txtName}"/>
                        <c:param name="rentalDate" value="${param.rentalDate}"/>
                        <c:param name="returnDate" value="${param.returnDate}"/>
                        <c:param name="cbCategory" value="${param.cbCategory}"/>
                        <c:param name="amount" value="${param.amount}"/>
                        <c:param name="page" value="${currentPage - 1}"/>
                    </c:url>
                    <a href="${searchFood}">Previous</a>
                </td>

            </c:if>

            <%--For displaying Page numbers. 
            The when condition does not display a link for the current page--%>
            <table border="1" cellpadding="5" cellspacing="5">
                <tr>
                    <c:forEach begin="1" end="${sessionScope.NOOFPAGE}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <c:url var="searchFood" value="">          
                                        <c:param name="txtName" value="${param.txtName}"/>
                                        <c:param name="rentalDate" value="${param.rentalDate}"/>
                                        <c:param name="returnDate" value="${param.returnDate}"/>
                                        <c:param name="cbCategory" value="${param.cbCategory}"/>
                                        <c:param name="amount" value="${param.amount}"/>
                                        <c:param name="page" value="${i}"/>
                                    </c:url>
                                    <a href="${searchFood}">${i}</a>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </table>

            <%--For displaying Next link --%>
            <c:if test="${currentPage lt noOfPage}">
                <td>
                    <c:url var="searchFood" value="">          
                        <c:param name="txtName" value="${param.txtName}"/>
                        <c:param name="rentalDate" value="${param.rentalDate}"/>
                        <c:param name="returnDate" value="${param.returnDate}"/>
                        <c:param name="cbCategory" value="${param.cbCategory}"/>
                        <c:param name="amount" value="${param.amount}"/>
                        <c:param name="page" value="${currentPage + 1}"/>
                    </c:url>
                    <a href="${searchFood}">Next</a>
                </td>
            </c:if>
        </c:if>
    </c:if>
    <c:if test="${empty carlist}">
        No Result</br>
    </c:if>
    <c:if test="${not empty requestScope.SEARCHERROR}">
        No Result</br>
    </c:if>
    <c:if test="${not empty requestScope.ADDERROR}">
        <font color="red"> ${requestScope.ADDERROR}</font></br>
    </c:if>
    <c:if test="${not empty requestScope.INVALIDDATE}">
        <font color="red"> ${requestScope.INVALIDDATE}</font></br>
    </c:if>
    <a href="viewCartPage">View Cart</a>
    <c:if test="${empty welcome}">
        <a href="loginPage">Return to login</a>
    </c:if>
</body>
</html>
