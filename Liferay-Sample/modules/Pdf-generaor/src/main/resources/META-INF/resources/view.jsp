<%@page import="com.liferay.portal.kernel.service.UserLocalServiceUtil"%>
<%@ include file="./init.jsp" %>

<h1>PDF Generator class</h1>

<portlet:resourceURL var="resourceURL" >
</portlet:resourceURL> 

<button onclick="downloadPDF('pdf')">Download PDF</button>


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
                link.download = 'guestbook_entries.pdf';

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

<aui:script>
$(document).ready(function() {
    function loadUsers(start, end) {
        $.ajax({
            url: '<%= renderResponse.createResourceURL() %>',
            method: 'GET',
            data: {
                start: start,
                end: end
            },
            success: function(data) {
                $('#userTable tbody').empty();
                $.each(data.users, function(index, user) {
                    $('#userTable tbody').append(
                        '<tr>' +
                        '<td>' + user.screenName + '</td>' +
                        '<td>' + user.emailAddresses + '</td>' +
                        '<td>' + user.firstName + '</td>' +
                        '<td>' + user.lastName + '</td>' +
                        '<td><a href="<portlet:renderURL><portlet:param name="mvcPath" value="/ViewDetails.jsp" /><portlet:param name="userId" value="' + user.userId + '" /></portlet:renderURL>">Edit</a></td>' +
                        '</tr>'
                    );
                });
            }
        });
    }

    loadUsers(0, 20);

    $('#loadMore').click(function() {
        var start = $('#userTable tbody tr').length;
        loadUsers(start, start + 20);
    });
});
</aui:script>

<table id="userTable">
    <thead>
        <tr>
            <th>Screen Name</th>
            <th>Email</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <!-- User rows will be appended here -->
    </tbody>
</table>

<button id="loadMore">Load More</button>

