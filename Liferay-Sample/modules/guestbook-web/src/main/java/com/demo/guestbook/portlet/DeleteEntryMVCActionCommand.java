package com.demo.guestbook.portlet;

import com.example.book.model.GuestbookEntry;
import com.example.book.service.GuestbookEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = {
		"javax.portlet.name=GuestbookWeb",
		"mvc.command.name=/deleteEntry"
		},
		service = MVCActionCommand.class
)
public class DeleteEntryMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(ActionRequest request, ActionResponse response) throws PortletException {
		long entryId = ParamUtil.getLong(request, "entryId");
		long guestbookId = ParamUtil.getLong(request, "guestbookId");
		
		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(GuestbookEntry.class.getName(), request);
		} catch (PortalException e1) {
			e1.printStackTrace();
		}
		
		try {
			response.setRenderParameter("guestbookId", Long.toString(guestbookId));
			_guestbookEntryLocalService.deleteGuestbookEntry(entryId);
			SessionMessages.add(request, "entryDeleted");
		}catch(Exception e) {
			Logger.getLogger(GuestbookWebPortlet.class.getName()).log(Level.SEVERE, null, e);
		}
		
		return false;
	}

	@Reference
	private GuestbookEntryLocalService _guestbookEntryLocalService;
}
