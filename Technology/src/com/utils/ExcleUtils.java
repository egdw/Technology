package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.entites.User;

/**
 * ExcleUtils 工具类
 * 
 * @author hdy
 * 
 */
public class ExcleUtils {
	public static ArrayList<User> addUserByExcle(File file) {
		ArrayList<User> users = new ArrayList<User>();
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheet = wb.getSheetAt(0);
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		Row row = null;
		Cell cell_a = null;
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			row = sheet.getRow(i); // 取得第i行
			cell_a = row.getCell(0); // 取得i行的第一列
			cell_a.setCellType(Cell.CELL_TYPE_STRING);
			String cellValue =  cell_a.getStringCellValue().trim();
			User user = new User();
			user.setuName(cellValue);
			user.setuPassword(RandomUtils.getRandomNum(12));
			user.setAuth(3);
			user.setCreadtDate(new Date());
			users.add(user);
		}

		return users;
	}

	public static File outputWorkerList(ArrayList<User> users,
			HttpServletRequest request) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("list");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("用户");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("密码");
		cell.setCellStyle(style);
		for (int i = 0; i < users.size(); i++) {
			row = sheet.createRow((int) i + 1);
			User complete = users.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(complete.getuId());
			row.createCell((short) 1).setCellValue(complete.getuName());
			row.createCell((short) 2).setCellValue(complete.getuPassword());
		}

		try {
			String string = request
					.getSession()
					.getServletContext()
					.getRealPath(
							File.separator + "WEB-INF" + File.separator
									+ "upload");
			File file = new File(string, "workers.xls");
			FileOutputStream fout = new FileOutputStream(new File(string,
					"workers.xls"));
			wb.write(fout);
			fout.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static File outputUserList(ArrayList<User> users,
			HttpServletRequest request) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("list");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("用户");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("密码");
		cell.setCellStyle(style);
		for (int i = 0; i < users.size(); i++) {
			row = sheet.createRow((int) i + 1);
			User complete = users.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(complete.getuId());
			row.createCell((short) 1).setCellValue(complete.getuName());
			row.createCell((short) 2).setCellValue(complete.getuPassword());
		}

		try {
			String string = request
					.getSession()
					.getServletContext()
					.getRealPath(
							File.separator + "WEB-INF" + File.separator
									+ "upload");
			File file = new File(string, "users.xls");
			FileOutputStream fout = new FileOutputStream(new File(string,
					"users.xls"));
			wb.write(fout);
			fout.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出结果界面信息到excle当中
	 * 
	 * @return
	 */
	public static File outputTaskCompleteList(ArrayList<User> tasks,
			HttpServletRequest request) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("list");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("用户");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("完成数量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("创建日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("密码");
		cell.setCellStyle(style);
		for (int i = 0; i < tasks.size(); i++) {
			row = sheet.createRow((int) i + 1);
			User complete = tasks.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(complete.getuId());
			row.createCell((short) 1).setCellValue(complete.getuName());
			row.createCell((short) 2).setCellValue(complete.getCompleteNums());
			cell = row.createCell((short) 3);
			cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(complete.getCreadtDate()));
			row.createCell((short) 4).setCellValue(complete.getuPassword());
		}

		try {
			String string = request
					.getSession()
					.getServletContext()
					.getRealPath(
							File.separator + "WEB-INF" + File.separator
									+ "upload");
			File file = new File(string, "result.xls");
			FileOutputStream fout = new FileOutputStream(new File(string,
					"result.xls"));
			wb.write(fout);
			fout.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
