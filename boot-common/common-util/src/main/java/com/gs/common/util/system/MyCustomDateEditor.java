package com.gs.common.util.system;

import com.gs.common.util.file.Ftp;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 重写spring日期转换器，自定义日期转换器
 *
 * @auth gs
 * @since 2018年4月19日下午1:28:13
 */
public class MyCustomDateEditor extends PropertyEditorSupport {

	private static Log log = LogFactory.getLog(Ftp.class);

	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss SSS";

	/**
	 * 日期处理
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isEmpty(text)) {
			// Treat empty String as null value.
			setValue(null);
		} else {
			try {
				setValue(this.dateAdapter(text));
			} catch (Exception ex) {
				ex.printStackTrace();
				MyCustomDateEditor.log.error("出错日志====" + ex.getMessage());
			}
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		SimpleDateFormat dateFormat = new SimpleDateFormat(MyCustomDateEditor.DEFAULT_FORMAT);
		return (value != null ? dateFormat.format(value) : "");
	}

	/**
	 * 字符串转日期适配方法
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @throws Exception
	 */
	public Date dateAdapter(String dateStr) throws Exception {
		Date date = null;
		String fm = "";
		if (StringUtils.isNotEmpty(dateStr)) {
			// 判断是不是日期字符串，如Wed May 28 08:00:00 CST 2014
			if (dateStr.contains("CST")) {
				fm = MyCustomDateEditor.DEFAULT_FORMAT;
			} else {
				dateStr = dateStr.replace("年", "-").replace("月", "-").replace("日", "").replaceAll("/", "-")
						.replaceAll("\\.", "-").trim();
				// 确定日期格式
				if (Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}.*").matcher(dateStr).matches()) {
					fm = "yyyy-MM-dd";
				} else if (Pattern.compile("^[0-9]{4}-[0-9]{1}-[0-9]+.*||^[0-9]{4}-[0-9]+-[0-9]{1}.*").matcher(dateStr)
						.matches()) {
					fm = "yyyy-M-d";
				} else if (Pattern.compile("^[0-9]{2}-[0-9]{2}-[0-9]{2}.*").matcher(dateStr).matches()) {
					fm = "yy-MM-dd";
				} else if (Pattern.compile("^[0-9]{2}-[0-9]{1}-[0-9]+.*||^[0-9]{2}-[0-9]+-[0-9]{1}.*").matcher(dateStr)
						.matches()) {
					fm = "yy-M-d";
				}
				if (StringUtils.isEmpty(fm)) {
					dateStr = " "+dateStr;
				}
				// 确定时间格式
				if (Pattern.compile(".*[ ][0-9]{1,2}").matcher(dateStr).matches()) {
					fm += " HH";
				} else if (Pattern.compile(".*[ ][0-9]{1,2}:[0-9]{1,2}").matcher(dateStr).matches()) {
					fm += " HH:mm";
				} else if (Pattern.compile(".*[ ][0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}").matcher(dateStr).matches()) {
					fm += " HH:mm:ss";
				} else if (Pattern.compile(".*[ ][0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}:[0-9]{0,3}").matcher(dateStr).matches()) {
					fm += " HH:mm:ss:sss";
				}
				if (StringUtils.isNotEmpty(fm.trim())) {
					try {
						date = new SimpleDateFormat(fm).parse(dateStr);
					} catch (ParseException e) {
						throw new Exception("参数字符串" + dateStr + "无法被转换为日期格式！");
					}
				}
			}
		}
		return date;
	}
}
