<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/structure/meta.jsp" %>
    <title>User List</title>
    <%@ include file="/structure/header.jsp" %>
</head>
<body>
<%@ include file="/structure/beans.jsp" %>
${user.verifyAcces(pageContext.request,pageContext.response)}
<%@ include file="/structure/statusbar.jsp" %>

<jsp:useBean id="facultate" class="com.stn.helpers.UserHelper"/>
<c:set var="users" value='${facultate.getUsers()}'/>

<table class="black" style="margin-top: 8pt; width: 570pt">
    <tr>
        <td class="center"><h1>User List</h1></td>
    </tr>

    <tr>
        <td class="center">
            <table class="black" border="1" style="margin-top: 5pt; width: 90%; border-collapse: collapse;">
                <tr>
                    <th style="text-align: left; padding: 3pt;">Id</th>
                    <th style="text-align: center; padding: 3pt">Username</th>
                    <th style="text-align: center; padding: 3pt">Join Date</th>
                    <th style="text-align: center; padding: 3pt">Last Seen</th>
                    <th style="text-align: center; padding: 3pt">Country</th>
                    <th style="text-align: center; padding: 3pt">Ip</th>
                </tr>
                <c:forEach items="${users}" var="u">
                    <tr>
                        <td class='row2' style="text-align: center">${u.getId()}</td>
                        <td class='row' style="text-align: left">
                            <a href='/userdetails.jsp?id=${u.getId()}' style="text-decoration: none"><b style="color: ${user.classColor(u.getUserClass())}">${e:forHtml(u.getUserName())}</b></a>
                            <c:if test="${u.getUserClass() == 0}">
                                <img src="/img/disabled_small.png" alt="Disabled" style="display: inline; vertical-align: bottom; margin-left: 1pt" title="Disabled">
                            </c:if>
                            <c:if test="${u.getDonor() == 1}">
                                <img src="/img/star.gif" alt="Donor" style="display: inline; vertical-align: bottom" title="Donor">
                            </c:if>
                        </td>
                        <td class='row' style="text-align: center">${tool.formatDate(u.getJoinDate(),2)}</td>
                        <td class='row' style="text-align: center">${tool.formatDate(u.getLastSeen(),2)}</td>
                        <td class='row' style="text-align: center">
                            <c:if test="${u.getCountryId() > 0}">
                                <img src="/img/flag/${u.getCountryImage()}" class="smile" title="${u.getCountryName()}">
                            </c:if>
                        </td>
                        <td class='row' style="text-align: center">${u.getIp()}</td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>

    <tr>
        <td>
            <br/><br/>
        </td>
    </tr>

</table>

<%@ include file="/structure/footer.jsp" %>
</body>
</html>
