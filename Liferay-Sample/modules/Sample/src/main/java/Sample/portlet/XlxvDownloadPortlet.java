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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

public class XlxvDownloadPortlet extends MVCPortlet{

	  @Override
	    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
	            throws IOException, PortletException {

	        System.out.println("Inside the method.....");
	        List<GuestbookEntry> guestbookEntries = GuestbookEntryLocalServiceUtil.getGuestbookEntries(-1, -1);
	        System.out.println("Guest Book Entries: " + guestbookEntries);

	        String[] columnNames = {"Entry ID", "Guestbook ID", "Name", "Email", "Message"};

	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Guestbook Entries");

	        Row headerRow = sheet.createRow(0);
	        for (int i = 0; i < columnNames.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(columnNames[i]);
	            
	        }
	       
	        int rowNum = 1;
	        for (GuestbookEntry entry : guestbookEntries) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(entry.getEntryId());
	            row.createCell(1).setCellValue(entry.getGuestbookId());
	            row.createCell(2).setCellValue(entry.getName());
	            row.createCell(3).setCellValue(entry.getEmail());
	            row.createCell(4).setCellValue(entry.getMessage());
	        }

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        workbook.write(baos);
	        workbook.close();

	        byte[] bytes = baos.toByteArray();
	        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	        PortletResponseUtil.sendFile(resourceRequest, resourceResponse, "GuestbookEntries.xlsx", bytes, contentType);

	        super.serveResource(resourceRequest, resourceResponse);
	    }
}
