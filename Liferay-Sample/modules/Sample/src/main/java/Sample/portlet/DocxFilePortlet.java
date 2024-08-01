package Sample.portlet;

import com.example.book.model.GuestbookEntry;
import com.example.book.service.GuestbookEntryLocalServiceUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.osgi.service.component.annotations.Component;

import Sample.constants.SamplePortletKeys;

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

public class DocxFilePortlet extends MVCPortlet{

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		
		System.out.println("Inside the Docx file method.....");
	    List<GuestbookEntry> guestbookEntries = GuestbookEntryLocalServiceUtil.getGuestbookEntries(-1, -1);
	    System.out.println("Guest Book Entries: " + guestbookEntries);

	    XWPFDocument document = new XWPFDocument();

	    XWPFTable table = document.createTable();
	    XWPFTableRow headerRow = table.getRow(0); 

	    String[] columnNames = {"Entry ID", "Guestbook ID", "Name", "Email", "Message"};
	    for (String columnName : columnNames) {
	        headerRow.addNewTableCell().setText(columnName);
	    }

	    for (GuestbookEntry entry : guestbookEntries) {
	        XWPFTableRow row = table.createRow();
	        row.getCell(1).setText(String.valueOf(entry.getEntryId()));
	        row.getCell(2).setText(String.valueOf(entry.getGuestbookId()));
	        row.getCell(3).setText(entry.getName());
	        row.getCell(4).setText(entry.getEmail());
	        row.getCell(5).setText(entry.getMessage());
	    }

	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    document.write(baos);
	    document.close();

	    byte[] bytes = baos.toByteArray();
	    String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	    PortletResponseUtil.sendFile(resourceRequest, resourceResponse, "GuestbookEntries.docx", bytes, contentType);

		
		super.serveResource(resourceRequest, resourceResponse);
	}
	
}
