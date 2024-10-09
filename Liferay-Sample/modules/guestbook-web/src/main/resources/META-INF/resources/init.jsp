<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>

<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.docs.guestbook.web.security.permission.resource.GuestbookModelPermission" %>
<%@ page import="com.liferay.docs.guestbook.web.security.permission.resource.GuestbookEntryPermission" %>
<%@ page import="com.liferay.docs.guestbook.web.security.permission.resource.GuestbookPermission"%>
<%@ page import="com.liferay.portal.kernel.security.permission.ActionKeys" %>



<liferay-theme:defineObjects />

<portlet:defineObjects />