<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="structure/meta.jsp" %>
    <title>Edit user</title>
    <%@ include file="structure/header.jsp" %>
</head>
<body>
<jsp:useBean id="userdetails" class="com.stn.helpers.UserHelper"/>
<c:set var="user" value='${userdetails.getUserInfo(param.id)}'/>

${userdetails.verifyAcces(pageContext.request,pageContext.response)}

<form name="edit" action="EditUser" method="post">
    <table class="black" style="margin-top: 30pt;">
        <tr>
            <td class="right" style="padding-left: 30pt; padding-top: 8pt">User:</td>
            <td style="padding-right: 30pt; padding-top: 8pt"><input type="text" name="user" value="${user.getUserName()}" size="35"></td>
        </tr>
        <tr>
            <td class="right" style="padding-left: 30pt; padding-top: 8pt">First Name:</td>
            <td style="padding-right: 30pt; padding-top: 8pt"><input type="text" name="firstname" value="${user.getFirstName()}" size="35"></td>
        </tr>
        <tr>
            <td class="right" style="padding-left: 30pt; padding-top: 8pt">Last Name:</td>
            <td style="padding-right: 30pt; padding-top: 8pt"><input type="text" name="lastname" value="${user.getLastName()}" size="35"></td>
        </tr>
        <tr>
            <td class="right" style="padding-left: 30pt; padding-top: 8pt">Email:</td>
            <td style="padding-right: 30pt; padding-top: 8pt"><input type="text" name="email" value="${user.getEmail()}" size="35"></td>
        </tr>
        <tr>
            <td class="right" style="padding-left: 30pt; padding-top: 8pt">Avatar:</td>
            <td style="padding-right: 30pt; padding-top: 8pt"><input type="text" name="avatar" value="${user.getAvatar()}" size="35"></td>
        </tr>
        <tr>
            <td class="center" colspan="2"><b style="color: red; display: inline">${sessionScope.error}</b></td>
        </tr>
        <tr>
            <td colspan="2" class="center" style="padding-top: 20pt"><a href='changePassword.jsp?id=${sessionScope.userId}'>Change password</a>
        </tr>
        <tr>
            <td colspan="2" class="center" style="padding-top: 20pt"><input name="save_chenges" type="submit" value="Save chamges">
        </tr>
        <tr>
            <td colspan="2" height="24"> <input type="hidden" name="idaplicatie" value="${param.id}">   </td>
        </tr>
    </table>
</form>

<c:remove var="error" scope="session" />

<%@ include file="structure/footer.jsp" %>

</body>
</html>
