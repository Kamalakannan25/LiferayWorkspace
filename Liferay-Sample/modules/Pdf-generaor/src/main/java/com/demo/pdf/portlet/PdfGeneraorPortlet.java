package com.demo.pdf.portlet;

import com.demo.pdf.constants.PdfGeneraorPortletKeys;
import com.example.book.model.GuestbookEntry;
import com.example.book.service.GuestbookEntryLocalServiceUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;


@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=Test",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/view.jsp",
			"javax.portlet.name=" + PdfGeneraorPortletKeys.PDFGENERAOR,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)

public class PdfGeneraorPortlet extends MVCPortlet {

	
	@Override
    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
            throws IOException, PortletException {
        System.out.println("Inside the method.....");
        List<GuestbookEntry> guestbookEntries = GuestbookEntryLocalServiceUtil.getGuestbookEntries(-1, -1);
        System.out.println("Guest Book Entries: " + guestbookEntries);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStream outputStream = resourceResponse.getPortletOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            document.add(new Paragraph("Guestbook Entries:"));
            for (GuestbookEntry entry : guestbookEntries) {
                document.add(new Paragraph("Entry ID: " + entry.getEntryId()));
                document.add(new Paragraph("Guestbook ID: " + entry.getGuestbookId()));
                document.add(new Paragraph("Name: " + entry.getName()));
                document.add(new Paragraph("Email: " + entry.getEmail()));
                document.add(new Paragraph("Message: " + entry.getMessage()));
                document.add(new Paragraph("---------------"));
            }
            document.close();
            byteArrayOutputStream.writeTo(outputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
	
	
//	public static final String CSV_SEPARATOR = ",";
//
//    public static void exportCSVData(ResourceRequest resourceRequest, ResourceResponse resourceResponse, String fileName,
//                                     String[] columnNames, List<String[]> data) throws Exception {
//
//        StringBundler sb = new StringBundler();
//
//        for (String columnName : columnNames) {
//            sb.append(getCSVFormattedValue(columnName));
//            sb.append(CSV_SEPARATOR);
//        }
//        sb.setIndex(sb.index() - 1);
//        sb.append(CharPool.NEW_LINE);
//
//        for (String[] row : data) {
//            for (String value : row) {
//                sb.append(getCSVFormattedValue(value));
//                sb.append(CSV_SEPARATOR);
//            }
//            sb.setIndex(sb.index() - 1);
//            sb.append(CharPool.NEW_LINE);
//        }
//
//        byte[] bytes = sb.toString().getBytes();
//        String contentType = ContentTypes.APPLICATION_TEXT;
//        PortletResponseUtil.sendFile(resourceRequest, resourceResponse, fileName, bytes, contentType);
//    }
//
//    private static String getCSVFormattedValue(String value) {
//        StringBundler sb = new StringBundler(3);
//        sb.append(CharPool.QUOTE);
//        sb.append(StringUtil.replace(value, CharPool.QUOTE, StringPool.DOUBLE_QUOTE));
//        sb.append(CharPool.QUOTE);
//        return sb.toString();
//    }
	
	
	
//    @Override
//    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
//            throws IOException, PortletException {
//        System.out.println("Inside the method.....");
//        List<GuestbookEntry> guestbookEntries = GuestbookEntryLocalServiceUtil.getGuestbookEntries(-1, -1);
//        System.out.println("Guest Book Entries: " + guestbookEntries);
//        try {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            OutputStream outputStream = resourceResponse.getPortletOutputStream();
//            Document document = new Document();
//            PdfWriter.getInstance(document, byteArrayOutputStream);
//            document.open();
//            document.add(new Paragraph("Guestbook Entries:"));
//            for (GuestbookEntry entry : guestbookEntries) {
//                document.add(new Paragraph("Entry ID: " + entry.getEntryId()));
//                document.add(new Paragraph("Guestbook ID: " + entry.getGuestbookId()));
//                document.add(new Paragraph("Name: " + entry.getName()));
//                document.add(new Paragraph("Email: " + entry.getEmail()));
//                document.add(new Paragraph("Message: " + entry.getMessage()));
//                document.add(new Paragraph("---------------"));
//            }
//            document.close();
//            byteArrayOutputStream.writeTo(outputStream);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//    }
}
