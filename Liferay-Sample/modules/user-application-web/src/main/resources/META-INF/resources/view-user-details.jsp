<%@page import="com.liferay.portal.kernel.model.Image"%>
<%@page import="com.liferay.portal.kernel.service.ImageLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.service.UserService"%>
<%@page import="com.liferay.portal.kernel.model.User"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.service.UserLocalServiceUtil"%>
<%@ include file="./init.jsp" %>


<%
String userId = request.getParameter("userId");
User user1 = UserLocalServiceUtil.getUser(Long.valueOf(userId));

System.out.println("imagesList>>>>>>>>>>"+ user1.getPortraitURL(themeDisplay));
%>
<<aui:form name="fm">
 <aui:row>
    <aui:col width="30">
        <aui:field-wrapper>
            <label>Image Id</label>
            <img src="<%= user1.getPortraitURL(themeDisplay) %>" alt="User Portrait" />
        </aui:field-wrapper>
    </aui:col> 
</aui:row>
<aui:row>
	<aui:col width="30">
		<aui:input name="userId" label="User ID:"   value="<%= user1.getUserId() %>" />
	</aui:col>
	<aui:col width="30">
		<aui:input name="companyId" label="Company ID:"   value="<%= user1.getCompanyId() %>" />
	</aui:col>
	<aui:col width="30">
		<aui:input name="contactId" label="Contact ID:"   value="<%= user1.getContactId() %>" />
	</aui:col>
</aui:row>

<aui:row>
	<aui:col width="50">
		<aui:input name="screenName" label="Screen Name :"   value="<%= user1.getScreenName() %>" />
	</aui:col>
	<aui:col width="50">
		<aui:input name="emailAddress" label="Email Address :"   value="<%= user1.getEmailAddress() %>" />
	</aui:col>
</aui:row>

<aui:row>
	<aui:col width="30">
		<aui:input name="firstName" label="First Name :"   value="<%= user1.getFirstName() %>" />
	</aui:col>
	<aui:col width="30">
		<aui:input name="middleName" label="Middle Name :"   value="<%= user1.getMiddleName() %>" />
	</aui:col>
	<aui:col width="30">
		<aui:input name="lastName" label="Last Name :"   value="<%= user1.getLastName() %>" />
	</aui:col>
</aui:row>

<aui:row>
	<aui:col width="30">
				<aui:input name="languageId" label="Language Id:"   value="<%= user1.getLanguageId() %>" />
	</aui:col>
	<aui:col width="30">
				<aui:input name="timeZoneId" label="Time Zone Id:"   value="<%= user1.getTimeZoneId() %>" />
	</aui:col>
	<aui:col width="30">
				<aui:input name="greeting" label="Greeting :"   value="<%= user1.getGreeting() %>" />
	</aui:col>
</aui:row>

<aui:row>
	<aui:col width="30">
				<aui:input name="loginDate" label="Login Date:"   value="<%= user1.getLoginDate() %>" />
	</aui:col>
	<aui:col width="30">
				<aui:input name="lastLoginDate" label="Last Login Date:"   value="<%= user1.getLastLoginDate() %>" />
	</aui:col>
	<aui:col width="30">
				<aui:input name="lastFailedLoginDate" label="Last Failed Login Date:"   value="<%= user1.getLastFailedLoginDate() %>" />
	</aui:col>
</aui:row>

<aui:row>
	<aui:col width="30">
				<aui:input name="loginIp" label="Login Ip:"   value="<%= user1.getLoginIP() %>" />
	</aui:col>
	<aui:col width="30">
				<aui:input name="lastLoginIp" label="Last Login Ip:"   value="<%= user1.getLastLoginIP() %>" />
	</aui:col>
	 
</aui:row>
</aui:form>

  <portlet:resourceURL id="serveResource" var="resourceURL">
   
</portlet:resourceURL> 

<%-- <portlet:resourceURL id="serveResource" var="resourceURL" /> --%>



<button onclick="downloadPDF()">Download PDF</button>

<script>
function downloadPDF() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '<%= resourceURL.toString() %>', true);
    xhr.responseType = 'arraybuffer';

    xhr.onload = function () {
        if (xhr.status === 200) {
            var blob = new Blob([xhr.response], { type: 'application/pdf' });

            var link = document.createElement('a');
            link.style.display = 'none';
            document.body.appendChild(link);

            var url = window.URL.createObjectURL(blob);
            link.href = url;
            link.download = 'user_details.pdf';

            link.click();
            window.URL.revokeObjectURL(url);
            document.body.removeChild(link);
        } else {
            console.error('Failed to download PDF');
        }
    };

    xhr.send();
}
</script>

<%-- 
<button onclick="downloadFile('pdf')">Download PDF</button>
<button onclick="downloadFile('csv')">Download CSV</button>

<script>
function downloadFile(fileType) {
    var xhr = new XMLHttpRequest();
    var resourceURL = "<%= resourceURL %>";  

    xhr.open('GET', resourceURL + '?fileType=' + fileType, true);
    xhr.responseType = 'arraybuffer';

    xhr.onload = function () {
        if (xhr.status === 200) {
            var contentType = fileType === 'pdf' ? 'application/pdf' : 'text/csv';
            console.log(contentType);
            var blob = new Blob([xhr.response], { type: contentType });
            console.log(blob);
            var link = document.createElement('a');
            link.style.display = 'none';
            document.body.appendChild(link);

            var url = window.URL.createObjectURL(blob);
            link.href = url;
            link.download = 'user_details.' + fileType;

            link.click();
            window.URL.revokeObjectURL(url);
            document.body.removeChild(link);
        } else {
            console.error('Failed to download ' + fileType.toUpperCase());
        }
    };

    xhr.send();
}
</script> --%>


