package com.liferay.docs.guestbook.web.security.permission.resource;

import com.example.book.constants.GuestbookConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class GuestbookPermission {

	public static boolean contains(PermissionChecker permissionChecker, long groupId, String actionId) {
		return _portletResourcePermission.contains(permissionChecker, groupId, actionId);
	}
	
	@Reference(target = "(resource.name=" + GuestbookConstants.RESOURCE_NAME + ")" , unbind = "-")
	public void setPortletResourcePermission(PortletResourcePermission portletResourcePermission) {
		_portletResourcePermission = portletResourcePermission;
	}
	
	private static PortletResourcePermission _portletResourcePermission;
}
