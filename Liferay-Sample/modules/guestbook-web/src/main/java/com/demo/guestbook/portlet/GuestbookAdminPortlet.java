package com.demo.guestbook.portlet;

import com.demo.guestbook.constants.GuestbookWebPortletKeys;
import com.example.book.model.Guestbook;
import com.example.book.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		property = {
			"com.liferay.portlet.css-class-wrapper=portlet-admin",
			"com.liferay.portlet.display-category=category.hidden",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.preferences-owned-by-group=true",
			"com.liferay.portlet.private-request-attributes=false",
			"com.liferay.portlet.private-session-attributes=false",
			"com.liferay.portlet.render-weight=50",
			"com.liferay.portlet.use-default-template=true",
			"javax.portlet.display-name=Guestbooks",
			"javax.portlet.expiration-cache=0",
			"javax.portlet.init-param.template-path=/META-INF/resources/",
			"javax.portlet.init-param.view-template=/guestbook_admin/view.jsp",
			"javax.portlet.name=" + GuestbookWebPortletKeys.GUESTBOOK_ADMIN,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=administrator",
			"javax.portlet.version=3.0"
		},
		service = Portlet.class
	)
public class GuestbookAdminPortlet extends MVCPortlet {

	public void addGuestbook(ActionRequest request, ActionResponse response) throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), request);

		String name = ParamUtil.getString(request, "name");
		try {
			_guestbookLocalService.addGuestbook(serviceContext.getUserId(), name, serviceContext);
			SessionMessages.add(request, "guestbookAdded");
		} catch (PortalException pe) {
			Logger.getLogger(GuestbookAdminPortlet.class.getName()).log(Level.SEVERE, null, pe);
			response.setRenderParameter("mvcPath", "/guestbook_admin/edit_guestbook.jsp");
		}
	}
	
	public void updateGuestbook(ActionRequest request, ActionResponse response) throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), request);
		String name = ParamUtil.getString(request, "name");
		long guestbookId = ParamUtil.getLong(request, "guestbookId");

		try {
			_guestbookLocalService.updateGuestbook(serviceContext.getUserId(), guestbookId, name, serviceContext);
			SessionMessages.add(request, "guestbookUpdated");
		} catch (PortalException pe) {
			Logger.getLogger(GuestbookAdminPortlet.class.getName()).log(Level.SEVERE, null, pe);
			response.setRenderParameter("mvcPath", "/guestbook_admin/edit_guestbook.jsp");
		}
	}
	
	public void deleteGuestbook(ActionRequest request, ActionResponse response) throws PortalException {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), request);
		
		long guestbookId = ParamUtil.getLong(request, "guestbookId");
		
		try {
			_guestbookLocalService.deleteGuestbook(guestbookId, serviceContext);
			SessionMessages.add(request, "guestbookDeleted");
		}catch(PortalException pe) {
			Logger.getLogger(GuestbookAdminPortlet.class.getName()).log(Level.SEVERE, null, pe);
		}
	}
	
	@Reference
	private GuestbookLocalService _guestbookLocalService;
}
