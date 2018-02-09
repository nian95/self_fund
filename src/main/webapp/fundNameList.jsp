<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.action.FundNameAction" %>
<%@ page import="com.bean.FundNameBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>fundNameList.jsp</title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2018.1.117/styles/kendo.common.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2018.1.117/styles/kendo.rtl.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2018.1.117/styles/kendo.silver.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2018.1.117/styles/kendo.mobile.all.min.css"/>

    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="https://kendo.cdn.telerik.com/2018.1.117/js/kendo.all.min.js"></script>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
    <style>
        body{
            min-width:1200px;
            width: 1250px;
            margin:0 auto;
        }
    </style>
</head>
<body>
<div id="page">
    <div id="container" >
        <ul class="breadcrumb">
            <li class="breadcrumb-item"><a href="fundNameList.jsp">基金列表</a></li>
            <li class="breadcrumb-item"><a href="fundUnitNetValue.jsp">基金净值</a></li>
        </ul>
        <%
            List<FundNameBean> fundNameBeans = new ArrayList<FundNameBean>();
            FundNameAction actionObj = new FundNameAction();
            List<FundNameBean> fundNameBeanList = actionObj.queryFundNameListFromWeb();
            int pageSize = 20;
            int currentPage = 1;
            int pageIndex = 0;
            int resultCount = fundNameBeanList.size();
            int pageCount = (resultCount % pageSize == 0) ? (resultCount / pageSize) : (resultCount / pageSize + 1);
            int j = 0;
            if (request.getParameter("pageIndex") != null) {
                pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
            }
            currentPage = (pageIndex != 0) ? pageIndex : 1;
            int prePage = (pageIndex != 0) ? pageIndex : 1;
            int nextPage = (pageIndex != pageCount) ? pageIndex : pageCount;
            j = (currentPage - 1) * pageSize;

        %>
        <ul class="pagination pagination-sm">
            <li class="page-item"><a class="page-link" href="?pageIndex=<%= --prePage %>">Previous</a></li>
            <%
                for (int i = 1; i < pageCount; i++) {
                    if(i <= (currentPage + 2) && i >= currentPage - 2 ) {
                        if (i == currentPage) {

             %>
                            <li class="page-item active"><a class="page-link" href="?pageIndex=<%= i %>"><%=i %></a></li>
            <%
                        } else {

            %>
                            <li class="page-item"><a class="page-link" href="?pageIndex=<%= i %>"><%=i %></a></li>
            <%
                         }
                    }
              }
            %>

            <li class="page-item"><a class="page-link" href="?pageIndex=<%= ++nextPage %>">Next</a></li>
        </ul>
        <table id="fundNameList" class="table table-striped" >
            <tr >
                <th id="orderTitle"      width="15%">序列</th>
                <th id="fundCodeTitle"   width="25%">基金编码</th>
                <th id="fundNameTitle"   width="35%">基金名称</th>
                <th id="fundTypeTitle"   width="25%">基金类型</th>
            </tr>
            <%
                fundNameBeans = fundNameBeanList.subList((currentPage - 1) * pageSize, currentPage * pageSize);
                for (FundNameBean fundNameBean : fundNameBeans) {
            %>
            <tr>
                <td id="order"><%=++j %>
                </td>
                <td id="fundCode"><%=fundNameBean.getfCode() %>
                </td>
                <td id="fundName"><%=fundNameBean.getfName() %>
                </td>
                <td id="fundType"><%=fundNameBean.getfType() %>
                </td>
            </tr>
            <%
                }

            %>
        </table>
        <ul class="pagination pagination-sm">
            <li class="page-item"><a class="page-link" href="?pageIndex=<%= --prePage %>">Previous</a></li>
            <%
                for (int i = 1; i < pageCount; i++) {
                    if(i <= (currentPage + 2) && i >= currentPage - 2 ) {
                        if (i == currentPage) {

            %>
            <li class="page-item active"><a class="page-link" href="?pageIndex=<%= i %>"><%=i %></a></li>
            <%
            } else {

            %>
            <li class="page-item"><a class="page-link" href="?pageIndex=<%= i %>"><%=i %></a></li>
            <%
                        }
                    }
                }
            %>

            <li class="page-item"><a class="page-link" href="?pageIndex=<%= ++nextPage %>">Next</a></li>
        </ul>
    </div>

</div>
</body>
</html>