package com.example.book.exception;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public class GuestbookEntryEmailException extends PortalException{

	public GuestbookEntryEmailException() {
	}

	public GuestbookEntryEmailException(String msg) {
		super(msg);
	}

	public GuestbookEntryEmailException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public GuestbookEntryEmailException(Throwable cause) {
		super(cause);
	}
}
