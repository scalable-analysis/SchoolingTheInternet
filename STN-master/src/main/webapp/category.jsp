<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="structure/meta.jsp" %>
    <title>Categories</title>
    <%@ include file="structure/header.jsp" %>
</head>
<body>
<%@ include file="structure/beans.jsp" %>
${user.verifyAcces(pageContext.request,pageContext.response)}
<%@ include file="structure/statusbar.jsp" %>

<jsp:useBean id="categorie" class="com.stn.helpers.CategoryHelper"/>
<c:set var="category" value="${categorie.getCategories(userInfo.getIdFacultate())}"/>

<table class="black" style="margin-top: 8pt; width: 570pt">

    <tr>
        <td class="center"><h1 style="color: white">Forum ${userInfo.getFacultate()}</h1>
    </tr>

    <tr>
        <td class="center">
            <table class="black" border="1" style="margin-top: 5pt; width: 90%; border-collapse: collapse;">
                <tr>
                    <th style="text-align: left; padding: 3pt; width: 400pt">Pinned Category</th>
                    <th style="text-align: center; padding: 3pt">Topics</th>
                    <th style="text-align: center; padding: 3pt">Posts</th>
                </tr>
                <c:forEach items="${category}" var="cat">
                    <c:if test="${cat.getPinned() == 1}">
                    <tr>
                        <td class='row2' style="text-align: left">
                            <img src="img/cat.png" style="vertical-align: middle">
                            <div style="display: inline-block; vertical-align: middle; margin-left: 3pt">
                                <b><a class="ui" href="view_forum.jsp?id=${cat.getId()}">${cat.getCategorie()}</a></b><br/>
                                    ${cat.getDescriere()}
                            </div>
                        </td>
                        <td class='row2' style="text-align: center">${cat.getTotalTopics()}</td>
                        <td class='row2' style="text-align: center">${cat.getTotalPosts()}</td>
                    </tr>
                    </c:if>
                </c:forEach>
            </table>
        </td>
    </tr>

    <tr>
        <td class="center">
            <table class="black" border="1" style="margin-top: 5pt; width: 90%; border-collapse: collapse;">
                <tr>
                    <th style="text-align: left; padding: 3pt; width: 400pt">Category</th>
                    <th style="text-align: center; padding: 3pt">Topics</th>
                    <th style="text-align: center; padding: 3pt">Posts</th>
                </tr>
                <c:forEach items="${category}" var="cat">
                    <c:if test="${cat.getPinned() != 1}">
                        <tr>
                            <td class='row2' style="text-align: left">
                                <img src="img/cat.png" style="vertical-align: middle">
                                <div style="display: inline-block; vertical-align: middle; margin-left: 3pt">
                                    <b><a class="ui" href="view_forum.jsp?id=${cat.getId()}">${cat.getCategorie()}</a></b><br/>
                                        ${cat.getDescriere()}
                                </div>
                            </td>
                            <td class='row2' style="text-align: center">${cat.getTotalTopics()}</td>
                            <td class='row2' style="text-align: center">${cat.getTotalPosts()}</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </td>
    </tr>

    <c:if test="${userInfo.getUserClass() > 4 || userInfo.getUserClass() == 2}">

    <tr>
        <td class="center" style="padding-top: 10pt">
            <form name="adaugaCategorie" action="/CategoryProcess" method="post">
                <p style="display: inline">Category Name</p><input type="text" name="categorie" style="width: 150pt; margin-left: 10pt"><br/>
                <textarea rows="6" cols="60" style="margin-top: 8pt" name="descriere"></textarea><br/><br/>
                <input type="checkbox" name="pinned" value="1">This is a pinned category.<br/>
                <input type="hidden" name="idFacultate" value="${userInfo.getIdFacultate()}">
                <input type="hidden" name="idSerie" value="${userInfo.getIdSerie()}">
                <input type="hidden" name="idGrupa" value="${userInfo.getIdGrupa()}">
                <input type="submit" name="Add Category" value="Add Category" style="margin-top: 5pt"><br/>
            </form>
            ${sessionScope.error}
            <br/>
            <br/>
        </td>
    </tr>

    </c:if>

    <tr>
        <td>
            <br/><br/>
        </td>
    </tr>

</table>

<%@ include file="structure/footer.jsp" %>

</body>
</html>