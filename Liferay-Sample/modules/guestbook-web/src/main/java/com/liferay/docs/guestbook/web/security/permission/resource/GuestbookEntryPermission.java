package com.liferay.docs.guestbook.web.security.permission.resource;

import com.example.book.model.GuestbookEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class GuestbookEntryPermission {

	public static boolean contains(PermissionChecker permissionChecker, GuestbookEntry entry, String actionId)
			throws PortalException {
		return _guestbookEntryModelResourcePermission.contains(permissionChecker, entry, actionId);

	}
	
	public static boolean contains(PermissionChecker permissionChecker, long entryId, String actionId)
			throws PortalException {

		return _guestbookEntryModelResourcePermission.contains(permissionChecker, entryId, actionId);
	}
	
	@Reference(target = "(model.class.name=com.example.book.model.GuestbookEntry)",
            unbind = "-")
    protected void setEntryModelPermission(ModelResourcePermission<GuestbookEntry> modelResourcePermission) {

        _guestbookEntryModelResourcePermission = modelResourcePermission;
    }
	
	private static ModelResourcePermission<GuestbookEntry>_guestbookEntryModelResourcePermission;

}
