package com.user.application.web.portlet;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.user.application.web.constants.UserApplicationWebPortletKeys;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Suchismita
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=UserApplicationWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + UserApplicationWebPortletKeys.USERAPPLICATIONWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class UserApplicationWebPortlet extends MVCPortlet {
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		List<User> usersList = UserLocalServiceUtil.getUsers(-1, -1);
		usersList = usersList.stream().filter(u -> !u.getEmailAddress().equalsIgnoreCase("test@liferay.com"))
				.filter(u -> !u.getEmailAddress().equalsIgnoreCase("default@liferay.com"))
				.filter(u -> !u.getEmailAddress().equalsIgnoreCase("default-service-account@liferay.com"))
				.filter(u -> !u.getEmailAddress().equalsIgnoreCase("anonymous20096_1@liferay.com")).collect(Collectors.toList());
		//List<Image> imagesList = ImageLocalServiceUtil.getImages(-1, -1);
		renderRequest.setAttribute("usersList", usersList);
		//renderRequest.setAttribute("imagesList", imagesList);
		super.render(renderRequest, renderResponse);
	}
	
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
	        throws IOException, PortletException {

	    ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute("LIFERAY_SHARED_THEME_DISPLAY");
	    System.out.println("Inside pdf the method.....");

	    String userIdParam = resourceRequest.getParameter("userId");
	    if (userIdParam == null) {
	        System.out.println("User ID is missing.");
	        return;
	    }

	    long userId = Long.parseLong(userIdParam);
	    User user = null;

	    try {
	        user = UserLocalServiceUtil.getUser(userId);
	    } catch (PortalException e1) {
	        e1.printStackTrace();
	    }

	    if (user == null) {
	        System.out.println("User not found.");
	        return;
	    }

	    try {
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        OutputStream outputStream = resourceResponse.getPortletOutputStream();
	        Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);

	        document.open();

	        PdfPTable table = new PdfPTable(2); 

	        if (user.getPortraitId() > 0) {
	            String profilePic = user.getPortraitURL(themeDisplay);
	            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(themeDisplay.getPortalURL() + profilePic);
	            image.scaleToFit(150, 150); 
	            image.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
	            PdfPCell imageCell = new PdfPCell(image, true);
	            imageCell.setColspan(2);
	            imageCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
	            imageCell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
	            imageCell.setFixedHeight(150); 
	            table.addCell(imageCell);
	        }

	        addDataCell(table, "User ID", String.valueOf(user.getUserId()));
	        addDataCell(table, "Company ID", String.valueOf(user.getCompanyId()));
	        addDataCell(table, "Contact ID", String.valueOf(user.getContactId()));
	        addDataCell(table, "Screen Name", user.getScreenName());
	        addDataCell(table, "Email Address", user.getEmailAddress());
	        addDataCell(table, "First Name", user.getFirstName());
	        addDataCell(table, "Middle Name", user.getMiddleName());
	        addDataCell(table, "Last Name", user.getLastName());
	        addDataCell(table, "Language ID", user.getLanguageId());
	        addDataCell(table, "Time Zone ID", user.getTimeZoneId());
	        addDataCell(table, "Greeting", user.getGreeting());
	        addDataCell(table, "Login Date", String.valueOf(user.getLoginDate()));
	        addDataCell(table, "Last Login Date", String.valueOf(user.getLastLoginDate()));
	        addDataCell(table, "Last Failed Login Date", String.valueOf(user.getLastFailedLoginDate()));
	        addDataCell(table, "Login IP", user.getLoginIP());
	        addDataCell(table, "Last Login IP", user.getLastLoginIP());

	        document.add(table);

	        document.close();
	        byteArrayOutputStream.writeTo(outputStream);

	    } catch (DocumentException e) {
	        e.printStackTrace();
	    } catch (PortalException e) {
	        e.printStackTrace();
	    }

	    super.serveResource(resourceRequest, resourceResponse);
	}

	private void addDataCell(PdfPTable table, String label, String value) {
	    PdfPCell labelCell = new PdfPCell(new Paragraph(label));
	    table.addCell(labelCell);

	    PdfPCell valueCell = new PdfPCell(new Paragraph(value));
	    table.addCell(valueCell);
	}
	
//	@Override
//	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
//			throws IOException, PortletException {
//		System.out.println("Inside serveResource method.....");
//
//		String fileType = resourceRequest.getParameter("fileType");
//		System.out.println("File type...." + fileType);
//		try {
//			if (fileType != null && !fileType.isEmpty()) {
//				if ("pdf".equalsIgnoreCase(fileType.trim())) {
//					generatePDF(resourceRequest, resourceResponse);
//				} else if ("csv".equalsIgnoreCase(fileType.trim())) {
//					generateCSV(resourceRequest, resourceResponse);
//				} else {
//					System.out.println("Invalid file type requested: " + fileType);
//				}
//			} else {
//				System.out.println("File Type parameter is missing or empty.");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void generatePDF(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
//	        throws IOException {
//	    System.out.println("Generating PDF...");
//
//	    ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute("LIFERAY_SHARED_THEME_DISPLAY");
//	    String userIdParam = resourceRequest.getParameter("userId");
//
//	    if (userIdParam == null || userIdParam.isEmpty()) {
//	        System.out.println("User ID is missing.");
//	        return;
//	    }
//
//	    long userId = Long.parseLong(userIdParam);
//	    User user = null;
//
//	    try {
//	        user = UserLocalServiceUtil.getUser(userId);
//	    } catch (PortalException e) {
//	        e.printStackTrace();
//	    }
//
//	    if (user == null) {
//	        System.out.println("User not found.");
//	        return;
//	    }
//
//	    try {
//	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//	        OutputStream outputStream = resourceResponse.getPortletOutputStream();
//	        Document document = new Document();
//	        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
//
//	        document.open();
//
//	        PdfPTable table = new PdfPTable(2);
//
//	        if (user.getPortraitId() > 0) {
//	            String profilePic = user.getPortraitURL(themeDisplay);
//	            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(themeDisplay.getPortalURL() + profilePic);
//	            image.scaleToFit(150, 150);
//	            image.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//	            PdfPCell imageCell = new PdfPCell(image, true);
//	            imageCell.setColspan(2);
//	            imageCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//	            imageCell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
//	            imageCell.setFixedHeight(150);
//	            table.addCell(imageCell);
//	        }
//
//	        addDataCell(table, "User ID", String.valueOf(user.getUserId()));
//	        addDataCell(table, "Company ID", String.valueOf(user.getCompanyId()));
//	        addDataCell(table, "Contact ID", String.valueOf(user.getContactId()));
//	        addDataCell(table, "Screen Name", user.getScreenName());
//	        addDataCell(table, "Email Address", user.getEmailAddress());
//	        addDataCell(table, "First Name", user.getFirstName());
//	        addDataCell(table, "Middle Name", user.getMiddleName());
//	        addDataCell(table, "Last Name", user.getLastName());
//	        addDataCell(table, "Language ID", user.getLanguageId());
//	        addDataCell(table, "Time Zone ID", user.getTimeZoneId());
//	        addDataCell(table, "Greeting", user.getGreeting());
//	        addDataCell(table, "Login Date", String.valueOf(user.getLoginDate()));
//	        addDataCell(table, "Last Login Date", String.valueOf(user.getLastLoginDate()));
//	        addDataCell(table, "Last Failed Login Date", String.valueOf(user.getLastFailedLoginDate()));
//	        addDataCell(table, "Login IP", user.getLoginIP());
//	        addDataCell(table, "Last Login IP", user.getLastLoginIP());
//
//	        document.add(table);
//
//	        document.close();
//	        byteArrayOutputStream.writeTo(outputStream);
//
//	    } catch (DocumentException | PortalException e) {
//	        e.printStackTrace();
//	    }
//	}
//
//	private void addDataCell(PdfPTable table, String label, String value) {
//	    PdfPCell labelCell = new PdfPCell(new Phrase(label));
//	    table.addCell(labelCell);
//
//	    PdfPCell valueCell = new PdfPCell(new Phrase(value));
//	    table.addCell(valueCell);
//	}
//	
//	private void generateCSV(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
//	        throws IOException {
//	    System.out.println("Generating CSV...");
//
//	    String userIdParam = resourceRequest.getParameter("userId");
//
//	    if (userIdParam == null || userIdParam.isEmpty()) {
//	        System.out.println("User ID is missing.");
//	        return;
//	    }
//
//	    long userId = Long.parseLong(userIdParam);
//	    User user = null;
//
//	    try {
//	        user = UserLocalServiceUtil.getUser(userId);
//	    } catch (PortalException e) {
//	        e.printStackTrace();
//	    }
//
//	    if (user == null) {
//	        System.out.println("User not found.");
//	        return;
//	    }
//
//	    StringBuilder csvContent = new StringBuilder();
//
//	    csvContent.append("User ID,Company ID,Contact ID,Screen Name,Email Address,First Name,Middle Name,Last Name,Language ID,Time Zone ID,Greeting,Login Date,Last Login Date,Last Failed Login Date,Login IP,Last Login IP\n");
//
//	    csvContent.append(user.getUserId()).append(",");
//	    csvContent.append(user.getCompanyId()).append(",");
//	    csvContent.append(user.getContactId()).append(",");
//	    csvContent.append(user.getScreenName()).append(",");
//	    csvContent.append(user.getEmailAddress()).append(",");
//	    csvContent.append(user.getFirstName()).append(",");
//	    csvContent.append(user.getMiddleName()).append(",");
//	    csvContent.append(user.getLastName()).append(",");
//	    csvContent.append(user.getLanguageId()).append(",");
//	    csvContent.append(user.getTimeZoneId()).append(",");
//	    csvContent.append(user.getGreeting()).append(",");
//	    csvContent.append(user.getLoginDate()).append(",");
//	    csvContent.append(user.getLastLoginDate()).append(",");
//	    csvContent.append(user.getLastFailedLoginDate()).append(",");
//	    csvContent.append(user.getLoginIP()).append(",");
//	    csvContent.append(user.getLastLoginIP()).append("\n");
//
//	    resourceResponse.setContentType("text/csv");
//	    resourceResponse.setProperty("Content-Disposition", "attachment; filename=user_data.csv");
//
//	    try (PrintWriter writer = resourceResponse.getWriter()) {
//	        writer.write(csvContent.toString());
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}

}