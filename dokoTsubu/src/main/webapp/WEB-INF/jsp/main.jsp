<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>どこつぶ</title>
</head>
<body style="font-family: Arial, sans-serif; max-width: 800px; margin: 0 auto; padding: 20px;">

    <h1>どこつぶメイン</h1>

    <div>
        <p><c:out value="${loginUser.name}" />さん、ログイン中</p>
        <a href="Logout" style="color: #dc3545; text-decoration: none; margin-left: 10px;">ログアウト</a>
    </div>

    <div style="display: grid; grid-gap: 20px;">
        <div>
            <form action="Main" method="post">
                <input type="text" name="text" style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px;">
                <input type="submit" value="つぶやく" style="background-color: #007bff; color: #fff; padding: 8px 16px; border-radius: 4px; border: none; cursor: pointer;">
            </form>
        </div>

        <c:if test="${not empty errorMsg}">
            <p style="color: #dc3545; font-weight: bold;"><c:out value="${errorMsg}" /></p>
        </c:if>

        <c:forEach var="mutter" items="${mutterList}">
            <p style="border: 1px solid #ccc; padding: 10px; border-radius: 4px;">
                <span style="font-weight: bold; margin-right: 5px;"><c:out value="${mutter.id}" />:</span>
                <span style="margin-right: 5px;"><c:out value="${mutter.userName}" />:</span>
                <span><c:out value="${mutter.text}" /></span>

                <form action="Main" method="post" style="display: inline; margin-left: 10px;">
                    <input type="hidden" name="muId" value="${mutter.id}" />
                    <input type="hidden" name="name" value="${mutter.userName}" />
                    <input type="submit" value="Delete" style="font-size: 16px; background-color: transparent; color: #dc3545; border: none; cursor: pointer;">
                </form>
            </p>
        </c:forEach>
    </div>

</body>
</html>
