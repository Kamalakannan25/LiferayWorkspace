<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.example.book.service.GuestbookEntryLocalServiceUtil"%>
<%@page import="com.example.book.model.GuestbookEntry"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>
<h2>CSV File, XLSX File and DOCX File</h2>

 <portlet:resourceURL var="resourceURL" >
</portlet:resourceURL> 


<script>
function downloadCSV() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '<portlet:resourceURL id="serveResource" />?fileType=csv', true);
    xhr.responseType = 'arraybuffer';

    xhr.onload = function () {
        if (xhr.status === 200) {
            var blob = new Blob([xhr.response], { type: 'text/csv' });

            var link = document.createElement('a');
            link.style.display = 'none';
            document.body.appendChild(link);

            var url = window.URL.createObjectURL(blob);
            link.href = url;
            link.download = 'guestbook_entries.csv';

            link.click();
            window.URL.revokeObjectURL(url);
            document.body.removeChild(link);
        } else {
            console.error('Failed to download CSV');
        }
    };

    xhr.send();
}
</script>

<button onclick="downloadCSV()">Download CSV</button>


<script>
function downloadXLSX() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '<portlet:resourceURL id="serveResource" />?fileType=xlsx', true);
    xhr.responseType = 'arraybuffer';

    xhr.onload = function () {
        if (xhr.status === 200) {
            var blob = new Blob([xhr.response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

            var link = document.createElement('a');
            link.style.display = 'none';
            document.body.appendChild(link);

            var url = window.URL.createObjectURL(blob);
            link.href = url;
            link.download = 'GuestbookEntries.xlsx';

            link.click();
            window.URL.revokeObjectURL(url);
            document.body.removeChild(link);
        } else {
            console.error('Failed to download XLSX');
        }
    };

    xhr.send();
}
</script>

<button onclick="downloadXLSX()">Download XLSX</button>

<script>
function downloadDocx() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '<portlet:resourceURL id="serveResource" />?fileType=docx', true);
    xhr.responseType = 'arraybuffer';

    xhr.onload = function () {
        if (xhr.status === 200) {
            var blob = new Blob([xhr.response], { type: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' });

            var link = document.createElement('a');
            link.style.display = 'none';
            document.body.appendChild(link);

            var url = window.URL.createObjectURL(blob);
            link.href = url;
            link.download = 'GuestbookEntries.docx';

            link.click();
            window.URL.revokeObjectURL(url);
            document.body.removeChild(link);
        } else {
            console.error('Failed to download DOCX');
        }
    };

    xhr.send();
}
</script>

<button onclick="downloadDocx()">Download DOCX</button>
<%--
<button onclick="downloadFile('pdf')">Download PDF</button>
<button onclick="downloadFile('csv')">Download CSV</button>

<script>
function downloadFile(fileType) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '<%= resourceURL.toString() %>?fileType=' + fileType, true);
    xhr.responseType = 'arraybuffer';

    xhr.onload = function () {
        if (xhr.status === 200) {
            var contentType = fileType === 'pdf' ? 'application/pdf' : 'text/csv';
            var blob = new Blob([xhr.response], { type: contentType });

            var link = document.createElement('a');
            link.style.display = 'none';
            document.body.appendChild(link);

            var url = window.URL.createObjectURL(blob);
            link.href = url;
            link.download = 'guestbook_entries.' + fileType;

            link.click();
            window.URL.revokeObjectURL(url);
            document.body.removeChild(link);
        } else {
            console.error('Failed to download ' + fileType.toUpperCase());
        }
    };

    xhr.send();
}
</script>

<button id="ajaxButton">Click Me</button> --%>