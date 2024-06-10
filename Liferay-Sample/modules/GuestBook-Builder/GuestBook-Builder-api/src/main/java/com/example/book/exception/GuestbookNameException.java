package com.example.book.exception;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public class GuestbookNameException extends PortalException {

	public GuestbookNameException() {
	}

	public GuestbookNameException(String msg) {
		super(msg);
	}

	public GuestbookNameException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public GuestbookNameException(Throwable cause) {
		super(cause);
	}
}
