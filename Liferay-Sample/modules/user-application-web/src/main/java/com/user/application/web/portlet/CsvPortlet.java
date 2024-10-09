//package com.user.application.web.portlet;
//
//
//import com.liferay.portal.kernel.exception.PortalException;
//import com.liferay.portal.kernel.model.User;
//import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
//import com.liferay.portal.kernel.service.UserLocalServiceUtil;
//import com.liferay.portal.kernel.theme.ThemeDisplay;
//import com.liferay.portal.kernel.util.WebKeys;
//import com.user.application.web.constants.UserApplicationWebPortletKeys;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//
//import javax.portlet.Portlet;
//import javax.portlet.PortletException;
//import javax.portlet.ResourceRequest;
//import javax.portlet.ResourceResponse;
//
//import org.osgi.service.component.annotations.Component;
//
//
//@Component(
//		immediate = true,
//		property = {
//			"com.liferay.portlet.display-category=category.sample",
//			"com.liferay.portlet.header-portlet-css=/css/main.css",
//			"com.liferay.portlet.instanceable=true",
//			"javax.portlet.display-name=UserCsvFile",
//			"javax.portlet.init-param.template-path=/",
//			"javax.portlet.init-param.view-template=/view.jsp",
//			"javax.portlet.name=" + UserApplicationWebPortletKeys.USERCSVdOWNLOAD,
//			"javax.portlet.resource-bundle=content.Language",
//			"javax.portlet.security-role-ref=power-user,user"
//		},
//		service = Portlet.class
//	)
//public class CsvPortlet extends MVCPortlet{
//
//	@Override
//	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
//	        throws IOException, PortletException {
//		System.out.println("inside the csv method.....");
//
////	    List<User> users = UserLocalServiceUtil.getUsers(-1, -1);
////	    System.out.println("Users List...." + users);
//	    
//	    String userIdParam = resourceRequest.getParameter("userId");
//	    if (userIdParam == null) {
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
//}
