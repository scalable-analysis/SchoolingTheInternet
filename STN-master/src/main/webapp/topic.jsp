<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="structure/meta.jsp" %>
    <%@ include file="structure/header.jsp" %>
    <title>Title</title>
</head>
<body>
<%@ include file="structure/beans.jsp" %>
${user.verifyAcces(pageContext.request,pageContext.response)}
<%@ include file="structure/statusbar.jsp" %>

<jsp:useBean id="commsb" class="com.stn.helpers.CommentsHelper"/>
<c:set var="useri" value='${user.getUsers()}'/>
<c:set var="comminfo" value="${commsb.getComments(param.id)}"/>

<table class="black" style="margin-top: 8pt; width: 570pt">

    <tr>
        <td style="padding-top: 10pt; padding-bottom: 15pt">
            <table class="black" cellspacing="0" style="width: 94%" style="background-color: #2c2c2c;">
                <tr id="comments">
                    <td style="background-color: #2c2c2c; padding: 8pt; text-align: center">
                        <table width="100%" border="0" cellpadding="5" cellspacing="7" style="background-color: #353535;">

                            <c:forEach items="${comminfo}" var="cw">

                                <tr id="comm_${cw.getId()}">
                                    <td width=100% colspan="2" style="background-color: #2c2c2c; text-align: left" id="title_${cw.getId()}">
                                        <b style="color : #99ccff">${tool.formatDate(cw.getDop(),4)} - </b>
                                        <c:if test="${userInfo.getUserClass() == 2 || userInfo.getUserClass() > 4 || userInfo.getId().equals(cw.getIdUser())}">
                                            <a class="ui" href="javascript:void(0);" onclick="return deleteComment(${cw.getId()},${param.id});" style="color: white" >[Delete]</a>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td width=100% style="text-align: left" id="body_${cw.getId()}">
                                            ${tool.formatText(e:forHtml(cw.getCont()))}
                                        <input type="hidden" value="${cw.getCont()}" id="comm_body_${cw.getId()}">
                                        <input type="hidden" value="${param.id}" id="comm_post_id">
                                                <div style="text-align: right; margin-top: 3pt;">
                                            <i>Added by <a href='/userdetails.jsp?id=${cw.getIdUser()}' style="text-decoration: none; color: ${user.classColor(cw.getUserClass())}">${e:forHtml(cw.getUsername())}</a>
                                                on ${tool.formatDate(cw.getDop(),3)}</i>
                                            <c:if test="${cw.getLastEdit() != null}">
                                                <br/>
                                                <i>Last edited on : ${tool.formatDate(cw.getLastEdit(),3)}</i>&nbsp;
                                            </c:if>
                                        </div>
                                        <c:if test="${userInfo.getUserClass() == 2 || userInfo.getUserClass() > 4 || userInfo.getId()==cw.getIdUser()}">
                                            <div style="text-align: center; margin-top: 3pt"><a class="ui" href="javascript:void(0);" style="color: #99ccff;" onclick=" return editComment(${cw.getId()},${param.id}); " id="comm_edit_body_${cw.getId()}">[Edit]</a></div>
                                        </c:if>
                                                <div style="text-align: center; margin-top: 3pt"><a class="ui" href="javascript:void(0);" style="color: #99ccff;" onclick=" return addReplyForm(${param.id},${cw.getId()}); " id="comm_reply_${cw.getId()}">[Reply]</a></div>
                                    </td>
                                </tr>

                            </c:forEach>

                        </table>

                        <hr>
                        <c:if test="${userInfo.getIdGrupa() != 0}">
                            <a href="javascript:void(0);" style="color: #99ccff; text-decoration: none;" onclick="return addCommentForm(${param.id});" id="commform">-- Add Comment --</a>
                        </c:if>
                        ${sessionScope.error}
                    </td>
                </tr>
            </table>
        </td>

    </tr>
</table>

<c:remove var="error" scope="session" />

<%@ include file="structure/footer.jsp" %>
</body>
</html>
