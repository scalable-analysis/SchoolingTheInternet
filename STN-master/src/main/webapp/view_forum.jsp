<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="structure/meta.jsp" %>
    <title>View forum</title>
    <%@ include file="structure/header.jsp" %>
</head>
<body>

<%@ include file="structure/beans.jsp" %>
${user.verifyAcces(pageContext.request,pageContext.response)}
<%@ include file="structure/statusbar.jsp" %>

<c:if test="${param.id != null}">

    <jsp:useBean id="topics" class="com.stn.helpers.TopicHelper"/>
    <c:set var="topic" value='${topics.getTopics(param.id)}'/>

<table class="black" style="margin-top: 8pt; width: 570pt">
    <tr>
        <td class="center"><h1 style="color: white">View Category</h1>
    </tr>
    <tr>
        <td class="center">
            <table class="black" border="1" style="margin-top: 5pt; width: 90%; border-collapse: collapse;">
                <tr>
                    <th style="text-align: left; padding: 3pt; width: 300pt">View Topic</th>
                    <th style="text-align: center; padding: 3pt">Posts</th>
                    <th style="text-align: center; padding: 3pt">Author</th>
                </tr>
                <c:forEach items="${topic}" var="t">
                    <tr>
                    <td class='row2' style="text-align: left"><b><a class="ui" href='/topic.jsp?id=${t.getTopicId()}'>${t.getName()}</a></b></td>
                        <td class='row2' style="text-align: center">${t.getTotalposts()}</td>
                        <td class='row2' style="text-align: center"><a href='/userdetails.jsp?id=${t.getAuthorId()}' style="text-decoration: none"><b style="color: ${user.classColor(t.getAuthorClass())}">${e:forHtml(t.getAuthorName())}</b></a></td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
    <tr>
        <td class="center" style="padding-top: 10pt">
            <form method="post" action="/AddTopic">
                <p style="display: inline">Topic Name</p><input type="text" name="topic_name" style="width: 150pt; margin-left: 10pt"><br/>
                <textarea rows="6" cols="60" style="margin-top: 8pt" name="body"></textarea><br/>
                <input type="hidden" name="group_id" value="${param.id}">
                <input type="submit" name="Add" value="Add Topic" style="margin-top: 5pt"><br/>
            </form>
                ${sessionScope.error}
            <br/>
            <br/>
        </td>
    </tr>
</table>
</c:if>

<%@ include file="structure/footer.jsp" %>
</body>
</html>
