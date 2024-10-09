/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.book.service.impl;

import com.example.book.service.base.GuestbookServiceBaseImpl;

import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=gb",
		"json.web.service.context.path=Guestbook"
	},
	service = AopService.class
)
public class GuestbookServiceImpl extends GuestbookServiceBaseImpl {
}