package com.example.book.exception;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public class GuestbookEntryNameException extends PortalException {

	public GuestbookEntryNameException() {
	}

	public GuestbookEntryNameException(String msg) {
		super(msg);
	}

	public GuestbookEntryNameException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public GuestbookEntryNameException(Throwable cause) {
		super(cause);
	}
}
