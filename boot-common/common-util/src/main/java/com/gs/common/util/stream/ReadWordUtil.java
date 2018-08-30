package com.gs.common.util.stream;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * 读取word中的内容
 *
 * @auth gs
 * @since 2018年5月15日上午9:14:16
 */
public class ReadWordUtil {

	/**
	 * 读取word文档
	 * 
	 * @throws Exception
	 *
	 * @auth gs
	 * @since 2018年5月15日上午9:15:57
	 */
	public static void readFile(File file) throws Exception {

		// 文件名
		String fileName = file.getName();
		// 文件类型
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		// 文件输入流
		InputStream inputStream = new FileInputStream(file);
		if ("docx".equals(fileType)) {
			// 如果是office2007 docx格式
			// word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
			XWPFDocument xwpf = new XWPFDocument(inputStream);// 得到word文档的信息
			// List<XWPFParagraph> listParagraphs = xwpf.getParagraphs();//得到段落信息
			Iterator<XWPFTable> it = xwpf.getTablesIterator();// 得到word中的表格
			while (it.hasNext()) {
				XWPFTable table = it.next();
				List<XWPFTableRow> rows = table.getRows();
				// 读取每一行数据
				for (int i = 0; i < rows.size(); i++) {
					XWPFTableRow row = rows.get(i);
					// 读取每一列数据
					List<XWPFTableCell> cells = row.getTableCells();
					for (int j = 0; j < cells.size(); j++) {
						XWPFTableCell cell = cells.get(j);
						// 输出当前的单元格的数据
						System.out.println(cell.getText());
					}
				}
			}
		} else if ("doc".equals(fileType)) {
			// 如果是office2003 doc格式
			POIFSFileSystem pfs = new POIFSFileSystem(inputStream);
			HWPFDocument hwpf = new HWPFDocument(pfs);
			Range range = hwpf.getRange();// 得到文档的读取范围
			TableIterator it = new TableIterator(range);
			// 迭代文档中的表格
			while (it.hasNext()) {
				Table tb = (Table) it.next();
				// 迭代行，默认从0开始
				for (int i = 0; i < tb.numRows(); i++) {
					TableRow tr = tb.getRow(i);
					// 迭代列，默认从0开始
					for (int j = 0; j < tr.numCells(); j++) {
						TableCell td = tr.getCell(j);// 取得单元格
						// 取得单元格的内容
						for (int k = 0; k < td.numParagraphs(); k++) {
							Paragraph paragraph = td.getParagraph(k);
							String s = paragraph.text();
							// 去除后面的特殊符号
							if (null != s && !"".equals(s)) {
								s = s.substring(0, s.length() - 1);
							}
							System.out.println(s);
						}
					}
				}
			}
		}
	}
}
