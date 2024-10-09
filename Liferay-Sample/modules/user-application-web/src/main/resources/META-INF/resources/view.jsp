<%@page import="java.util.ArrayList"%>
<%@page import="com.liferay.portal.kernel.model.Image"%>
<%@page import="com.liferay.portal.kernel.model.User"%>
<%@page import="java.util.List"%>
<%@ include file="./init.jsp" %>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/2.0.8/css/dataTables.dataTables.min.css">
<script src="https://cdn.datatables.net/2.0.8/js/dataTables.min.js"></script>


<script>
new DataTable('#myTable');
</script>

<%
List<User> usersList = (List) request.getAttribute("usersList");
%>

<table id="myTable" class="display">
    <thead>
        <tr>
            <th>User ID</th>
            <th>Screen Name</th>
            <th>Name</th>
            <th>Email ID</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
    <% for (User userItem : usersList) { %>
        <tr>
            <td><%= userItem.getUserId() %></td>
            <td><%= userItem.getScreenName() %></td>
            <td><%= userItem.getFullName() %></td>
            <td><%= userItem.getDisplayEmailAddress() %></td>
            <portlet:renderURL var="viewUserURL">
                <portlet:param name="userId" value="<%=String.valueOf(userItem.getUserId())%>" />
                <portlet:param name="mvcPath" value="/view-user-details.jsp" />
            </portlet:renderURL>
            <td><a href="${viewUserURL}">View</a></td>
        </tr>
    <% } %>
    </tbody>
</table>