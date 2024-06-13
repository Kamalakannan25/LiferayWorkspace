package com.demo.guestbook.portlet;

import com.demo.guestbook.constants.GuestbookWebPortletKeys;
import com.example.book.model.Guestbook;
import com.example.book.model.GuestbookEntry;
import com.example.book.service.GuestbookEntryLocalService;
import com.example.book.service.GuestbookEntryLocalServiceUtil;
import com.example.book.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Suchismita
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.social",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=GuestbookWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/guestbook/view.jsp",
		"javax.portlet.name=" + GuestbookWebPortletKeys.GUESTBOOKWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class GuestbookWebPortlet extends MVCPortlet {
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), renderRequest);

			long groupId = serviceContext.getScopeGroupId();
			long guestbookId = ParamUtil.getLong(renderRequest, "guestbookId");

			List<Guestbook> guestbooks = _guestbookLocalService.getGuestbooks(groupId);

			if (guestbooks.isEmpty()) {
				Guestbook guestbook = _guestbookLocalService.addGuestbook(serviceContext.getUserId(), "Main",
						serviceContext);
				guestbookId = guestbook.getGuestbookId();
			}
			if (guestbookId == 0) {
				guestbookId = guestbooks.get(0).getGuestbookId();
			}
			
			renderRequest.setAttribute("guestbookId", guestbookId);

		} catch (PortalException e) {
			e.printStackTrace();
		}

		super.render(renderRequest, renderResponse);
	}
	
	
	
		public void addEntry(ActionRequest request, ActionResponse response) throws PortalException {
		
		System.out.println("Add Entry>>>>>>>>>>>>>>>");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(GuestbookEntry.class.getName(), request);
		
		String userName = ParamUtil.getString(request, "name");
		String email = ParamUtil.getString(request, "email");
		String message = ParamUtil.getString(request, "message");
        long guestbookId = ParamUtil.getLong(request, "guestbookId");
        long entryId = ParamUtil.getLong(request, "entryId");
        
        if(entryId > 0) {
        	try {
        		_guestbookEntryLocalService.updateGuestbookEntry(serviceContext.getUserId(), guestbookId, 
        				entryId, userName, email, message, serviceContext);
        		
        		response.setRenderParameter("guestbookId", Long.toString(guestbookId));
        		
        	}catch(Exception e) {
        		System.out.println(e);
        		PortalUtil.copyRequestParameters(request, response);
        		response.setRenderParameter("mvcPath", "/guestbook/edit_entry.jsp");
        	}
        }else {
        	try {
        		 _guestbookEntryLocalService.addGuestbookEntry(serviceContext.getUserId(), guestbookId,
        				userName, email, message, serviceContext);
        		response.setRenderParameter("guestbookId", Long.toString(guestbookId));
        		SessionMessages.add(request, "entryAdded");
        		
        	}catch(Exception e) {
        		e.printStackTrace();
        		PortalUtil.copyRequestParameters(request, response);
        		response.setRenderParameter("mvcPath", "/guestbook/edit_entry.jsp");
        	}
        }
		
	}
		
	public void deleteEntry(ActionRequest request, ActionResponse response) throws PortalException {
		long entryId = ParamUtil.getLong(request, "entryId");
		long guestbookId = ParamUtil.getLong(request, "guestbookId");
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(GuestbookEntry.class.getName(), request);
		
		try {
			response.setRenderParameter("guestbookId", Long.toString(guestbookId));
			_guestbookEntryLocalService.deleteGuestbookEntry(entryId);
			SessionMessages.add(request, "entryDeleted");
		}catch(Exception e) {
			Logger.getLogger(GuestbookWebPortlet.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	
	@Reference
	private GuestbookEntryLocalService _guestbookEntryLocalService;

	@Reference
	private GuestbookLocalService _guestbookLocalService;
}