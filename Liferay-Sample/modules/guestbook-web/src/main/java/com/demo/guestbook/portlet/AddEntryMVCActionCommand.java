package com.demo.guestbook.portlet;

import com.example.book.model.GuestbookEntry;
import com.example.book.service.GuestbookEntryLocalService;
import com.example.book.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		property = {
			"javax.portlet.name=GuestbookWeb",
			"mvc.command.name=/addEntry"
		},
		service = MVCActionCommand.class
	)

public class AddEntryMVCActionCommand implements MVCActionCommand {
	@Reference
	private GuestbookEntryLocalService _guestbookEntryLocalService;

	@Reference
	private GuestbookLocalService _guestbookLocalService;
	@Override
	public boolean processAction(ActionRequest request, ActionResponse response) throws PortletException {
		
		System.out.println("Add Entry>>>>>>>>>>>>>>from mvc action command>");

		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(GuestbookEntry.class.getName(), request);
		} catch (PortalException e1) {
		}
		if(serviceContext == null)
			return false;
		
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
		return false;
	}

}
