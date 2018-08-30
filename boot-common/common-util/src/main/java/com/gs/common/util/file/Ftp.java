package com.gs.common.util.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;

/**
 * 文件上传下载
 * gs
 */
public class Ftp {

	private static final Log log = LogFactory.getLog(Ftp.class);


	// 文件服务器ip地址
	private static String FTP_ADDRESS;

	// 文件服务器端口号
	private static Integer FTP_PORT;

	// 文件服务器用户名
	private static String FTP_USERNAME;

	// 文件服务器密码
	private static String FTP_PASSWORD;

	// 文件存放基础路径
	protected static String FTP_BASE_PATH;

	// 图片存放基础路径
	public static String IMAGE_BASE_PATH;

	// app存放路径
	protected static String APP_BASE_PATH;



	// 初始化文件上传服务器配置
	static {

		try {

			String UPLOAD_FILE = "config/ftp.xml";

			InputStream resourceAsStream = Ftp.class.getClassLoader().getResourceAsStream(UPLOAD_FILE);

			SAXReader t_reader = new SAXReader();

			Document document = t_reader.read(resourceAsStream);

			Element element = document.getRootElement();

			Ftp.FTP_ADDRESS = element.elementText("FTP_ADDRESS");

			Ftp.FTP_PORT = Integer.valueOf(element.elementText("FTP_PORT"));

			Ftp.FTP_USERNAME = element.elementText("FTP_USERNAME");

			Ftp.FTP_PASSWORD = element.elementText("FTP_PASSWORD");

			Ftp.FTP_BASE_PATH = element.elementText("FTP_BASE_PATH");

			Ftp.IMAGE_BASE_PATH = element.elementText("IMAGE_BASE_PATH");

			Ftp.APP_BASE_PATH = element.elementText("APP_BASE_PATH");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Description: 向FTP服务器上传文件 
	 * @param basePath FTP服务器基础目录
	 * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
	 * @param filename 上传到FTP服务器上的文件名 
	 * @param input 输入流 
	 * @return 成功返回true，否则返回false 
	 */
	public static boolean uploadFile(String basePath, String filePath, String filename, InputStream input) {

		log.info("进入图片上传" + System.currentTimeMillis());

		boolean result = false;
		FTPClient ftp = new FTPClient();

		try {
			int reply;
			ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {

				ftp.disconnect();
				return result;
			}
			//切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath+filePath)) {
				//如果目录不存在创建目录
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir)) continue;
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(tempPath)) {
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			log.info("图片上传开始上传");
			//设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//上传文件
			ftp.setControlEncoding("UTF-8");
			// 上传服务器时使用
			ftp.enterLocalPassiveMode();
			ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

			if (!ftp.storeFile(new String(filename.getBytes("UTF-8"),"iso-8859-1"),input)) {
				log.info("图片上传失败,进入if------------");
				input.close();
				ftp.logout();
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
		} catch (IOException e) {

			log.info("图片上传失败$$$$$$$$$$$$" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		log.info("结束图片上传" + System.currentTimeMillis());
		return result;
	}
	
	/** 
	 * Description: 从FTP服务器下载文件 
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名 
	 * @param localPath 下载后保存到本地的路径 
	 * @return 
	 */
	protected static boolean downloadFile(String remotePath,String fileName, String localPath) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

}
