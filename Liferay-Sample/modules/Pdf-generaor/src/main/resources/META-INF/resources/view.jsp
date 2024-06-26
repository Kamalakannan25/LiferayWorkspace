<%@ include file="/init.jsp" %>

<h1>PDF Generator class</h1>

<%-- <theme:defineObjects/>
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
</form>  --%>

<portlet:resourceURL var="resourceURL" >
</portlet:resourceURL> 

<button onclick="downloadPDF('pdf')">Download PDF</button>
<!-- <button onclick="downloadFile('csv')">Download CSV</button> -->

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

<%-- <html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    function downloadPDF() {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', '<%= resourceURL.toString() %>', true);
        xhr.responseType = 'arraybuffer';

        xhr.onload = function () {
            if (xhr.status === 200) {
                var blob = new Blob([xhr.response], { type: 'application/pdf' });

                // Create a link element, hide it, direct it towards the blob, and then 'click' it programatically
                var link = document.createElement('a');
                link.style.display = 'none';
                document.body.appendChild(link);

                // Create a DOMString representing the blob and point the link element towards it
                var url = window.URL.createObjectURL(blob);
                link.href = url;
                link.download = 'guestbook_entries.pdf';

                // Clean up and remove the link
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
</head>
<body>
    <button onclick="downloadPDF()" class="btn btn-primary">Download PDF</button>
</body>
</html> --%>


