package Sample.portlet;

import com.example.book.model.GuestbookEntry;
import com.example.book.service.GuestbookEntryLocalServiceUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

import Sample.constants.SamplePortletKeys;

/**
 * @author Suchismita
 */
@Component(immediate = true, property = { 
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", 
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Sample", 
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp", 
		"javax.portlet.name=" + SamplePortletKeys.SAMPLE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" 
		}, 
				service = Portlet.class)

public class SamplePortlet extends MVCPortlet {

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		 System.out.println("Inside the CSV method.....");
	        List<GuestbookEntry> guestbookEntries = GuestbookEntryLocalServiceUtil.getGuestbookEntries(-1, -1);
	        System.out.println("Guest Book Entries: " + guestbookEntries);

	        String[] columnNames = { "Entry ID", "Guestbook ID", "Name", "Email", "Message" };

	        final String COMMA = ",";

	        StringBundler sb = new StringBundler();

	        for (String columnName : columnNames) {
	            sb.append(CharPool.QUOTE);
	            sb.append(StringUtil.replace(columnName, CharPool.QUOTE, StringPool.DOUBLE_QUOTE));
	            sb.append(CharPool.QUOTE);
	            sb.append(COMMA);
	        }

	        sb.setIndex(sb.index() - 1);
	        sb.append(CharPool.NEW_LINE);

	        for (GuestbookEntry entry : guestbookEntries) {
	            sb.append(entry.getEntryId()).append(COMMA);
	            sb.append(entry.getGuestbookId()).append(COMMA);
	            sb.append(entry.getName()).append(COMMA);
	            sb.append(entry.getEmail()).append(COMMA);
	            sb.append(entry.getMessage());
	            sb.append(CharPool.NEW_LINE);
	        }

	        byte[] bytes = sb.toString().getBytes();
	        String contentType = ContentTypes.APPLICATION_TEXT;
	        PortletResponseUtil.sendFile(resourceRequest, resourceResponse, "guestbook_entries.csv", bytes, contentType);

	        super.serveResource(resourceRequest, resourceResponse);
	}
	
	
	
//	@Override
//	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
//	        throws IOException, PortletException {
//	    System.out.println("Inside the method.....");
//	    List<GuestbookEntry> guestbookEntries = GuestbookEntryLocalServiceUtil.getGuestbookEntries(-1, -1);
//	    System.out.println("Guest Book Entries: " + guestbookEntries);
//
//	    String fileType = resourceRequest.getParameter("fileType");
//	    if ("csv".equalsIgnoreCase(fileType)) {
//	       
//	        resourceResponse.setContentType("text/csv");
//	        resourceResponse.setProperty("Content-Disposition", "attachment; filename=guestbook_entries.csv");
//
//	        OutputStream outputStream = resourceResponse.getPortletOutputStream();
//	        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//	        writer.println("Entry ID,Guestbook ID,Name,Email,Message");
//	        
//	        for (GuestbookEntry entry : guestbookEntries) {
//	            writer.println(entry.getEntryId() + "," + 
//			        entry.getGuestbookId() + "," + 
//			        entry.getName() + "," + 
//			        entry.getEmail() + "," + 
//			        entry.getMessage());
//	            System.out.println("Guest book Entry   : " + entry);
//	            System.out.println("writer   : " + writer);
//	        }
//	        
//
//	        writer.flush();
//	        writer.close();
//	    } else {
//	       
//	        try {
//	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//	            OutputStream outputStream = resourceResponse.getPortletOutputStream();
//	            Document document = new Document();
//	            PdfWriter.getInstance(document, byteArrayOutputStream);
//	            document.open();
//	            document.add(new Paragraph("Guestbook Entries:"));
//	            for (GuestbookEntry entry : guestbookEntries) {
//	                document.add(new Paragraph("Entry ID: " + entry.getEntryId()));
//	                document.add(new Paragraph("Guestbook ID: " + entry.getGuestbookId()));
//	                document.add(new Paragraph("Name: " + entry.getName()));
//	                document.add(new Paragraph("Email: " + entry.getEmail()));
//	                document.add(new Paragraph("Message: " + entry.getMessage()));
//	                document.add(new Paragraph("---------------"));
//	            }
//	            document.close();
//	            byteArrayOutputStream.writeTo(outputStream);
//	        } catch (DocumentException e) {
//	            e.printStackTrace();
//	        }
//	    }
//	}
	
}