<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.action.FundNameAction" %>
<%@ page import="com.bean.FundNameBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bean.FundUnitNetValueBean" %>
<%@ page import="com.action.FundUnitNetValueAction" %>
<html>
<head>
    <title>fundUnitNetValue.jsp</title>
    <meta charset="UTF-8"/>
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="https://kendo.cdn.telerik.com/2018.1.117/js/kendo.all.min.js"></script>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2018.1.117/styles/kendo.common.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2018.1.117/styles/kendo.rtl.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2018.1.117/styles/kendo.silver.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2018.1.117/styles/kendo.mobile.all.min.css"/>
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="https://kendo.cdn.telerik.com/2018.1.117/js/kendo.all.min.js"></script>

    <style>
        body{
            min-width:1200px;
            width: 1250px;
            margin:0 auto;
        }
    </style>
</head>
<body>
    <div id="container" >
        <ul class="breadcrumb">
            <li class="breadcrumb-item"><a href="fundNameList.jsp">基金列表</a></li>
            <li class="breadcrumb-item"><a href="fundUnitNetValue.jsp">基金净值</a></li>
        </ul>
        <div id="example">
            <div class="demo-section k-content wide">
                <div id="chart"></div>
            </div>
            <script>
                var unitValues = [ {
                    createDate: "2001-12-18",
                    unitValue:1.0
                },
                    {
                        createDate: "2002-01-11",
                        unitValue:1.001
                    },
                    {
                        createDate: "2002-01-25",
                        unitValue:1.005
                    },
                    {
                        createDate: "2002-01-30",
                        unitValue:1.002
                    },
                    {
                        createDate: "2002-01-31",
                        unitValue:1.002
                    },
                    {
                        createDate: "2002-02-01",
                        unitValue:1.009
                    },
                    {
                        createDate: "2002-02-04",
                        unitValue:1.009
                    },
                    {
                        createDate: "2002-02-05",
                        unitValue:1.012
                    },
                    {
                        createDate: "2002-02-06",
                        unitValue:1.009
                    }
                ];
                function createChart() {
                    $("#chart").kendoChart({
                        dataSource: {data:unitValues},
                        title: {
                            text: "基金净值线性图"
                        },
                        legend: {
                            position: "top"
                        },
                        seriesDefaults: {
                            type: "line",
                            color:"green"

                        },
                        series: [{
                            field: "unitValue",
                            categoryField: "createDate",
                            name: "净值"
                        },
//                        }, {
//                            field: "hydro",
//                            categoryField: "year",
//                            name: "Hydro"
//                        }, {
//                            field: "wind",
//                            categoryField: "year",
//                            name: "Wind"
//                        }
 ],
//                        categoryAxis: {
//                            labels: {
//                                rotation: -90
//                            },
//                            crosshair: {
//                                visible: true
//                            }
//                        },
//                        valueAxis: {
//
//                            labels: {
//                                format: "N0"
//                            },
//
//                            majorUnit: 0.0001
//                        },
                        tooltip: {
                            visible: true//,
//                            shared: true,
//                            format: "YES"
                        }
                    });
                }

                $(document).ready(createChart);
                $(document).bind("kendo:skinChange", createChart);
            </script>
        </div>
        <%--<div id="fundUnitNetValueDiv" ></div>--%>
        <%
            List<FundUnitNetValueBean> fundNameBeans = new ArrayList<FundUnitNetValueBean>();
            FundUnitNetValueAction actionObj = new FundUnitNetValueAction();
            String netValueUrl = "http://fund.eastmoney.com/pingzhongdata/";
            String fundCode = "000001";
            List<FundUnitNetValueBean>  fundUnitNetValueList = actionObj.queryFundUnitNetValueFromWeb(netValueUrl , fundCode);
            int pageSize = 20;
            int currentPage = 1;
            int pageIndex = 0;
            int resultCount = fundUnitNetValueList.size();
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
                <th id="fCode"   width="25%">基金代码</th>
                <th id="unitNetValueDate"   width="35%">净值日日</th>
                <th id="unitNetValue"   width="25%">净值</th>
            </tr>
            <%
                List<FundUnitNetValueBean> fundUnitNetValues = fundUnitNetValueList.subList((currentPage - 1) * pageSize, currentPage * pageSize);
                for (FundUnitNetValueBean fundUnitNetValue : fundUnitNetValues) {
            %>
            <tr>
                <td id="order"><%=++j %>
                </td>
                <td id="fundCode"><%=fundUnitNetValue.getfCode() %>
                </td>
                <td id="fundUnitNetValueDate"><%=fundUnitNetValue.getUnitNetValueDate() %>
                </td>
                <td id="fundUnitNetValue"><%=fundUnitNetValue.getUnitNetValue() %>
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
    <%--<script>--%>
        <%--var FUND_CODE = "基金代码";--%>
        <%--var FUND_NET_VALUE_DATE = "净值日日";--%>
        <%--var FUND_NET_VALUE = "净值";--%>
        <%--$("#fundUnitNetValueDiv").kendoGrid({--%>
            <%--height: 550,--%>
<%--//            groupable: true,--%>
<%--//            sortable: true,--%>
            <%--pageable: {--%>
<%--//                refresh: true,--%>
<%--//                pageSizes: true,--%>
                <%--pageSize: 1,--%>
                <%--buttonCount: 5--%>
            <%--},--%>
            <%--columns:[--%>
                <%--{--%>
                    <%--field : "fCode",--%>
                    <%--width : '15%',--%>
                    <%--title : FUND_CODE--%>
                <%--},--%>
                <%--{--%>
                    <%--field : "unitNetValueDate",--%>
                    <%--width : '40%',--%>
                    <%--title : FUND_NET_VALUE_DATE,--%>
                    <%--format : "{0: yyyy-MM-dd hh:mm:ss}"--%>
                <%--},--%>
                <%--{--%>
                    <%--field : "unitNetValue",--%>
                    <%--width : '15%',--%>
                    <%--title : FUND_NET_VALUE--%>
                <%--}--%>
            <%--],--%>
            <%--dataSource: {--%>
                <%--data: [--%>
                    <%--{--%>
                        <%--fCode: "000001",--%>
                        <%--unitNetValueDate: "2002-11-11 00:00:00",--%>
                        <%--unitNetValue: "0.994"--%>
                    <%--},--%>
                    <%--{--%>
                        <%--fCode: "000001",--%>
                        <%--unitNetValueDate: "2002-11-12 00:00:00",--%>
                        <%--unitNetValue: "0.992"--%>
                    <%--},--%>
                    <%--{--%>
                        <%--fCode: "000001",--%>
                        <%--unitNetValueDate: "2002-11-13 00:00:00",--%>
                        <%--unitNetValue: "0.992"--%>
                    <%--}--%>
                <%--]--%>
            <%--}--%>
        <%--});--%>
    <%--</script>--%>

</body>
</html>