package internal.security.permission.resource;

import com.demo.guestbook.constants.GuestbookWebPortletKeys;
import com.example.book.constants.GuestbookConstants;
import com.example.book.model.Guestbook;
import com.example.book.service.GuestbookLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.WorkflowedModelPermissionLogic;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;


@Component(immediate = true)
public class GuestbookModelResourcePermissionRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
        
	Dictionary<String, Object> properties = new HashMapDictionary<>();
	properties.put("com.example.book", Guestbook.class.getName());
	
	_serviceRegistration = bundleContext.registerService(ModelResourcePermission.class, 
			ModelResourcePermissionFactory.create(Guestbook.class, Guestbook::getGuestbookId, _guestbookLocalService::getGuestbook, 
					_portletResourcePermission, (modelresourcepermission, consumer) -> {
					consumer.accept(new StagedModelPermissionLogic<>(_stagingPermission, GuestbookWebPortletKeys.GUESTBOOKWEB,
							Guestbook::getGuestbookId));
					consumer.accept(new WorkflowedModelPermissionLogic<>(_workflowPermission, modelresourcepermission,
							_groupLocalService, Guestbook::getGuestbookId));
					}),
			properties);
	
	}
	
	
	@Deactivate
	public void deactivate() {
		_serviceRegistration.unregister();
	}
	
	@Reference
	private GuestbookLocalService _guestbookLocalService;
	
	@Reference(target = "(resource.name=" + GuestbookConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;
	
	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;
	
	@Reference
	private StagingPermission _stagingPermission;
	
	@Reference
	private WorkflowPermission _workflowPermission;
	
	@Reference
	private GroupLocalService _groupLocalService;
	
}
