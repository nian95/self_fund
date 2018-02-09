<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.action.FundNameAction" %>
<%@ page import="com.bean.FundNameBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>fundList.jsp</title>
    <meta charset="UTF-8"/>
    <style type="text/css">
        table {
            border-collapse:collapse;
        }

        table, td, th{
            border:1px solid black;
        }


    </style>
</head>
<body>
<div id="page">
    <div id="title">
        <label id="titleLabel">基金列表</label>
    </div>
    <div id="content" >
        <table id="fundList" border="1"  width="100%">
            <tr >
                <th id="orderTitle"      width="15%">序列</th>
                <th id="fundCodeTitle"   width="25%">基金编码</th>
                <th id="fundNameTitle"   width="35%">基金名称</th>
                <th id="fundTypeTitle"   width="25%">基金类型</th>
            </tr>
            <c:forEach items="${fundNameBeanList}" var="fundNameBean">
            <tr>
                <td id="order"><%=++j %>
                </td>
                <td id="fundCode">${fundNameBean.getfCode() }</td>
                <td id="fundName">${fundNameBean.getfName()}</td>
                <td id="fundType">${fundNameBean.getfType()}</td>
            </tr>
            </c:forEach>
        </table>
    </div>

</div>
</body>
</html>