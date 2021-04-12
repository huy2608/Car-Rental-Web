<%-- 
    Document   : verify
    Created on : Mar 2, 2021, 10:02:11 PM
    Author     : Shi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <span>We already send a verification  code to your email.</span>
        
        <form action="verifyCode" method="post">
            <input type="text" name="authcode" >
            <input type="submit" value="verify">
            <input type="hidden" name="txtUsername" value="${requestScope.EMAIL}" />
            <input type="hidden" name="txtPassword" value="${requestScope.PASSWORD}" />
            <input type="hidden" name="name" value="${requestScope.NAME}" />
            <input type="hidden" name="code" value="${requestScope.CODE}" />
        </form>
    </body>
</html>
