 <%@ include file="../init.jsp" %>
<%@page import="com.liferay.docs.guestbook.web.security.permission.resource.GuestbookModelPermission"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.petra.string.StringPool"%>
<%@page import="com.example.book.service.GuestbookLocalServiceUtil"%>
<%@page import="com.example.book.model.Guestbook"%>
<%@page import="java.util.List"%>
<%@page import="com.example.book.service.GuestbookEntryLocalServiceUtil"%>
<%@ page import="com.example.book.model.Guestbook" %> 


<h2>view page</h2>



<%--  <theme:defineObjects/>
<portlet:defineObjects />
<portlet:resourceURL var="resourceURL"/>
<script type="text/javascript">
function callServeResource(){
    AUI().use('aui-io-request', function(A){
        A.io.request('<%=resourceURL.toString()%>');
    });
}
 
</script>

<form name="fm" id="fm">
<input type="button" value="Click" onclick="callServeResource()">
</form> 


<liferay-ui:success key="entryAdded" message="entry-added" />
<liferay-ui:success key="entryDeleted" message="entry-deleted" />

<%
   long guestbookId = Long.valueOf((Long) renderRequest.getAttribute("guestbookId"));
%>

 <portlet:renderURL var="searchURL">
	<portlet:param name="mvcPath" value="/guestbook/view_search.jsp" />
</portlet:renderURL>

<aui:form action="<%=searchURL.toString() %>" name="fm">

	<div class="row">
		<div class="col-md-8">
			<aui:input inlineLabel="left" label="" name="keywords" placeholder="search-entries" size="256" />
		</div>

		<div class="col-md-4">
			<aui:button type="submit" value="search" />
		</div>
	</div>

</aui:form>

 <aui:nav cssClass="nav-tabs">
<% 
List<Guestbook> guestbooks = GuestbookLocalServiceUtil.getGuestbooks(scopeGroupId);

for(int i=0; i < guestbooks.size(); i++){
Guestbook curGuestbook = guestbooks.get(i);
	String cssClass = StringPool.BLANK;

if (curGuestbook.getGuestbookId() == guestbookId) {
	cssClass = "active";
	}
	
%>
<c:if test='<%= GuestbookPermission.contains(permissionChecker, scopeGroupId, "VIEW") %>'>
<portlet:renderURL var="viewPageURL">
		<portlet:param name="mvcPath" value="/guestbook/view.jsp" />
		<portlet:param name="guestbookId" value="<%=String.valueOf(curGuestbook.getGuestbookId())%>" />
	</portlet:renderURL>
	
<aui:nav-item cssClass="<%=cssClass%>" href="<%=viewPageURL%>" label="<%=HtmlUtil.escape(curGuestbook.getName())%>" />
</c:if>
<%} %>
</aui:nav> 

<aui:button-row cssClass="guestbook-buttons">
	<c:if test='<%= GuestbookPermission.contains(permissionChecker, scopeGroupId, "ADD_ENTRY") %>'>
		<portlet:renderURL var="addEntryURL">
			<portlet:param name="mvcPath" value="/guestbook/edit_entry.jsp" />
			<portlet:param name="guestbookId" value="<%=String.valueOf(guestbookId)%>" />
		</portlet:renderURL>

		<aui:button onClick="<%=addEntryURL.toString()%>" value="Add Entry"></aui:button>
	</c:if>
</aui:button-row>


 <liferay-ui:search-container total="<%= GuestbookEntryLocalServiceUtil.getGuestbookEntriesCount() %>" >
	<liferay-ui:search-container-results
		results="<%= GuestbookEntryLocalServiceUtil.getGuestbookEntries(scopeGroupId.longValue(), guestbookId, searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.example.book.model.GuestbookEntry"
		modelVar="entry" >

		<liferay-ui:search-container-column-text property="message" />
		<liferay-ui:search-container-column-text property="name" />
		<liferay-ui:search-container-column-jsp align="right" path="/guestbook/entry_actions.jsp" />
			
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>   --%>



<%-- <portlet:resourceURL var="resourceURL" >
</portlet:resourceURL>




<aui:button value="Download" onclick="downloadPDF()" type="button" class="btn btn-primary">Download PDF</aui:button>
   <script>
    function downloadPDF() {
        console.log("Script called:");

        var url = '<portlet:renderURL var="resourceURL" />';
        
        $.ajax({
            async: false,
            cache: false,
            dataType: 'json',
            url: '${resourceURL}',
            type: 'GET',
            data: {
                'cm': 'vk'
            },
            success: function(response) {
                console.log('Success:', response);
            },
            error: function(xhr, status, error) {

                console.error('Error:', error);
            }
        });
    }
</script> --%>