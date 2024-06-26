package com.liferay.docs.guestbook.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

public class GuestbookPanelCategory extends BasePanelCategory {

	public static final String GUESTBOOK_PANEL_CATEGORY_KEY = "guestbook";

    @Override
    public String getKey() {
        return GUESTBOOK_PANEL_CATEGORY_KEY;
    }

    @Override
    public String getLabel(Locale locale) {
        return LanguageUtil.get(locale, "guestbook");
    }

    @Override
    public boolean isAllowScopeLayouts() {
        return true;
    }
}
