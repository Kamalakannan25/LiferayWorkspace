<%@page import="com.example.book.service.GuestbookLocalServiceUtil"%>
<%@page import="com.example.book.model.Guestbook"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ include file="../init.jsp" %>

<% 
	long guestbookId = ParamUtil.getLong(request, "guestbookId");

Guestbook guestbook = null;
if(guestbookId > 0){
	guestbook = GuestbookLocalServiceUtil.getGuestbook(guestbookId);
}

%>

<portlet:renderURL>
	<portlet:param name="mvcPath" value="/guestbook_admin/view.jsp"/>
</portlet:renderURL>

<portlet:actionURL name='<%= guestbook == null ? "addGuestbook" : "updateGuestbook" %>'  var="editGuestbookURL" />

<aui:form action="<%= editGuestbookURL %>" name="<portlet:namespace />fm">
	<aui:model-context bean="<%= guestbook %>" model="<%= Guestbook.class %>" />
		<aui:input name="guestbookId" type="hidden" value='<%= guestbook == null ? "" : guestbook.getGuestbookId() %>' />
		
		<aui:fieldset>
			<aui:input name="name" />
		</aui:fieldset>
	<aui:button-row>
		<aui:button type="submit" />
		<%-- <aui:button onClick="<%= viewURL %>" type="cancel" /> --%>
	</aui:button-row>
</aui:form>


