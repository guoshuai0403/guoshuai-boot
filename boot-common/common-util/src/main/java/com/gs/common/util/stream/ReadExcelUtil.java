package com.gs.common.util.stream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 * @auth gs
 * @since 2018年5月11日下午4:25:50
 */
public class ReadExcelUtil {

	/**
	 * 读取excel文件并转换成
	 * 半成品
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static Map<String, Object> readExcel(File file) throws Exception {
		Workbook workBook = null;
		// 文件名称
		String fileName = file.getName();
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		// 文件输入流
		FileInputStream inputStream = new FileInputStream(file);
		if (".xls".equals(fileType)) {
			workBook = new HSSFWorkbook(inputStream);
		} else if (".xlsx".equals(fileType)) {
			workBook = new XSSFWorkbook(inputStream);
		}
		if (workBook == null) {
			System.out.println("文件为null");
			return null;
		}
		// 获取第一个sheet
		Sheet sheet = workBook.getSheetAt(0);
		if (sheet == null) {
			return null;
		}
		Map<String, Object> result = new HashMap<>();
		// 逐行遍历
		for (int row = 1; row <= sheet.getLastRowNum(); row++) {
			Row rowData = sheet.getRow(row);
			// 第0列存储的是Key
			String key = rowData.getCell(0).getStringCellValue();
			// 第1列存储的是该参数是否为必填项
			String isMustStr = rowData.getCell(1).getStringCellValue();
			// 是否必填
			boolean isMust = true;
			if (isMustStr.contains("否")) {
				isMust = false;
			}
			// 第2列存储的是该参数对应的值的类型
			String paramTypeStr = rowData.getCell(2).getStringCellValue();
			boolean isNum = false;
			if (paramTypeStr.contains("数字")) {
				isNum = true;
			}
			// 第3列存储的是长度: F - 固定；V - 不固定
			String lengthStr = rowData.getCell(3).getStringCellValue().trim();
			// 该列字符长度
			int length = Integer.valueOf(lengthStr.substring(1));
			// 该列长度是否固定
			String stringFixed = lengthStr.substring(0, 1);
			boolean isFixed = false;
			if ("F".equals(stringFixed)) {
				isFixed = true;
			}
			// 其它列作为说明项暂时不用

			// 随机生成一个测试的值
			String value = "value";
			// 拼装结果
			result.put(key, value);
			// // 逐列遍历
			// for (int column = 0; column < rowData.getLastCellNum(); column++) {
			// Cell cell = rowData.getCell(column);
			// // 获取当前列的值
			// String str = cell.getStringCellValue();
			// System.out.println(row + ", " + column + " =================" + str);
			// }
		}
		return result;
	}

	/**
	 * 私有构造函数
	 */
	public ReadExcelUtil(){}
}
