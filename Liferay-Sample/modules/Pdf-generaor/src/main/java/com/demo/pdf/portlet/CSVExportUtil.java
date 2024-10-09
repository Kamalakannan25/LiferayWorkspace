package com.demo.pdf.portlet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

public class CSVExportUtil {

	public static final String CSV_SEPARATOR = ",";
	
	public static void exportCSVData(ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			String fileName, String[] columnNames, List<String[]> data) throws Exception {

		StringBundler sb = new StringBundler();

		for (String columnName : columnNames) {
			sb.append(getCSVFormattedValue(columnName));
			sb.append(CSV_SEPARATOR);
		}
		sb.setIndex(sb.index() - 1);
		sb.append(CharPool.NEW_LINE);

		for (String[] row : data) {
			for (String value : row) {
				sb.append(getCSVFormattedValue(value));
				sb.append(CSV_SEPARATOR);
			}
			sb.setIndex(sb.index() - 1);
			sb.append(CharPool.NEW_LINE);
		}

		byte[] bytes = sb.toString().getBytes();
		String contentType = ContentTypes.APPLICATION_TEXT;
		PortletResponseUtil.sendFile(resourceRequest, resourceResponse, fileName, bytes, contentType);
	}

	private static String getCSVFormattedValue(String value) {
		StringBundler sb = new StringBundler(3);
		sb.append(CharPool.QUOTE);
		sb.append(StringUtil.replace(value, CharPool.QUOTE, StringPool.DOUBLE_QUOTE));
		sb.append(CharPool.QUOTE);
		return sb.toString();
	}
}
