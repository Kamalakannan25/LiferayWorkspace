package com.liferay.docs.guestbook.application.list;

import com.demo.guestbook.constants.GuestbookWebPortletKeys;
import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
				"panel.app.order:Integer=300",
				"panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT
		},
		service = PanelApp.class
		)
public class GuestbookAdminPanelApp extends BasePanelApp {

	@Override
	public Portlet getPortlet() {
		
		return _portlet;
	}

	@Override
	public String getPortletId() {
		
		return GuestbookWebPortletKeys.GUESTBOOK_ADMIN;
	}

	@Reference(target = "(javax.portlet.name=" + GuestbookWebPortletKeys.GUESTBOOK_ADMIN + ")"
			)
	private Portlet _portlet;
}
