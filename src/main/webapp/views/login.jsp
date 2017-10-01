<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.cccis.foundation.authentication.AuthConstant" %>
<%@ page import="com.cccis.foundation.pub.utils.SpringBeanUtils" %>
<%@ page import="com.cccis.hub.sysconfig.service.LoginConfig" %>
<%
    LoginConfig loginConfig = SpringBeanUtils.getBean(LoginConfig.class);
    String rememberDay = loginConfig.getRememberDay();
    //影响session的获取，暂时去掉
    /*request.getSession().invalidate();
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("JSESSIONID".equalsIgnoreCase(cookie.getName())) {
                cookie.setMaxAge(0);
            }
        }
    }*/
    String versionStr = "版本号：";
%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@ include file="/WEB-INF/pages/pub/top.jsp" %>
    <script type="text/javascript">
        var REMEMBER_DAY = <%=rememberDay%>;
    </script>
    <jwr:script src="/bundles/login.js"/>
    <jwr:style src="/bundles/webEsLogin.css"/>
    <title>login Page</title>
</head>

<body>
<script type="text/javascript">
    var WEB_SUITE_VERSION = "<%=WebSuiteVersion.VERSION%>";
</script>
<div class="loginWrap">
    <div class="loginBanner"><img src="<%=request.getContextPath()%>/css/images/login/leftImage.png"
                                  draggable="false" alt="login"/></div>
    <div class="loginForm">
        <div class="loginLogo">
            <img src="<%=request.getContextPath()%>/css/images/login/ccclogo.png" alt="ccclogo" draggable="false"/>
            <%--<img src="<%=request.getContextPath()%>/css/images/login/dazong_logo.png" alt="dazonglogo"--%>
                 <%--draggable="false"/>--%>
        </div>
        <div class="loginTitle">
            <ccc:stringResource resCode="RES_421" defaultValue="车辆在线定损系统"/>
        </div>
        <form action="<%=request.getContextPath()%><%=AuthConstant.SECURITY_URL%>" method="post" name="loginForm"
              id="loginForm" style="margin:0">
            <span id="errorMessage"></span>
            <!-- this url if for session timeout -->
            <input type="hidden" id="url" name="url" value="<%= request.getParameter("url")%>"/>

            <div class="placeholderBox loginPlaceholderBox">
                <input type="text" id="j_username" name="<%=AuthConstant.LOGIN_PARAMETER_USERNAME%>"
                       value="${param.j_username}" class="k-textbox width323" placeHolderValue="用户名"/>
            </div>
            <div class="placeholderBox loginPlaceholderBox passWord ">
                <input id="j_password" type="password" name="<%=AuthConstant.LOGIN_PARAMETER_PASSWORD%>"
                       value="${param.j_username}" class="k-textbox width323" style="ime-mode: disabled;" maxlength="30"
                       test="12345" placeHolderValue="密码"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"/>
                <input id="j_passwordInPlainCode" type="text" name="<%=AuthConstant.LOGIN_PARAMETER_PASSWORD%>"
                       value="${param.j_username}" class="k-textbox width323" style="ime-mode: disabled;" maxlength="30"
                       test="12345" placeHolderValue="密码"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"/>
                <span id="passwordDisplay" class="passwordDisabled"></span>
            </div>
            <div class="placeholderBox  loginPlaceholderBox">
                <input id="captchaStr" name="<%=AuthConstant.SIMPLECAPTCHA %>" type="text" class="k-textbox width153"
                       style="ime-mode: disabled;" placeHolderValue="验证码"/>
                <span class="verImage"><img id="captchaImg"
                                            src="<%=request.getContextPath()%>/simpleCaptcha.sca?width=116&height=37"
                                            draggable="false" class="captchaStr" title="看不清，换一张。"/></span>
            </div>
            <div class="rememberBox"><input type="checkbox" id="remember_me" class="checkbox"/><span
                    class="checkboxText">记住用户</span></div>
            <div class="loginButton"><a id="submitLogin"><img
                    src="<%=request.getContextPath()%>/css/images/login/loginIcon.png" draggable="false" alt=""/></a>
            </div>
        </form>
        <div class="versionDiv">
            <span class="versionText">
                <label><%=versionStr+WebSuiteVersion.VERSION%></label>
            </span>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/pages/pub/bottom.jsp" %>
<%@ include file="/WEB-INF/pages/pub/copyright.jsp" %>
</body>
</html>
