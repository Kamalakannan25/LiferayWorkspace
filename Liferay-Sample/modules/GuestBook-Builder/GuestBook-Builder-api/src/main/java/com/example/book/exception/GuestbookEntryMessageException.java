package com.example.book.exception;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public class GuestbookEntryMessageException extends PortalException{

	public GuestbookEntryMessageException() {
	}

	public GuestbookEntryMessageException(String msg) {
		super(msg);
	}

	public GuestbookEntryMessageException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public GuestbookEntryMessageException(Throwable cause) {
		super(cause);
	}
	
}
