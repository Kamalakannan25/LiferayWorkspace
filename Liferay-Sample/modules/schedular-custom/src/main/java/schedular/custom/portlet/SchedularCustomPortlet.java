package schedular.custom.portlet;

import schedular.custom.constants.SchedularCustomPortletKeys;

import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Suchismita
 */
@Component(
	immediate = true,
	property = {
		"dispatch.task.executor.name=custom-name",
		"dispatch.task.executor.type=custom-type"
	},
	service = DispatchTaskExecutor.class
)
public class SchedularCustomPortlet implements DispatchTaskExecutor {

	@Override
	public void execute(long dispatchTriggerId) throws IOException, PortalException {
		System.out.println("Hello vinoth");
	}

	@Override
	public String getName() {
		return "custom-getname";
	}
}