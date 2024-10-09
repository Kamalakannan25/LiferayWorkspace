package internal.security.permission.resource;

import com.demo.guestbook.constants.GuestbookWebPortletKeys;
import com.example.book.constants.GuestbookConstants;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class GuestbookPortletResourcePermissionRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();
		properties.put("resource.name", GuestbookConstants.RESOURCE_NAME);

		_serviceRegistration = bundleContext.registerService(PortletResourcePermission.class,
				PortletResourcePermissionFactory.create(GuestbookConstants.RESOURCE_NAME,
						new StagedPortletPermissionLogic(_stagingPermission, GuestbookWebPortletKeys.GUESTBOOKWEB)),
				properties);
	}
	
	@Deactivate
	private void deactivate() {
		 _serviceRegistration.unregister();
	}
	
	private ServiceRegistration<PortletResourcePermission> _serviceRegistration;
	
	@Reference
	private StagingPermission _stagingPermission;
	
	
}
