package com.bill99.yn.webmgmt.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.bill99.yn.webmgmt.entity.TransactionDetail;
import com.bill99.yn.webmgmt.entity.TransactionSummary;

/**
 * 
 * @author Liu.D.H
 * @see http://www.andykhan.com/jexcelapi/tutorial.html
 * @see http://www.vogella.com/tutorials/JavaExcel/article.html
 */
public class ExcelUtil {
	private File excelFile;
	private List<TransactionDetail> transactionDetails;
	private List<TransactionSummary> transactionSummarys;
	private Date currentDay;
	private List<String> dateFilters;
	private Set<String> parsedDates;
	
	public ExcelUtil(String excelFilePath, List<String> dateFilters) {
		this(new File(excelFilePath), dateFilters);
	}
	
	public ExcelUtil(File excelFile, List<String> dateFilters) {
		this.excelFile = excelFile;
		this.dateFilters = dateFilters;
		this.parsedDates = new HashSet<String>();
		transactionDetails = new ArrayList<TransactionDetail>();
		transactionSummarys = new ArrayList<TransactionSummary>();
		parseExcelSheetTransactionDetail();
		parseExcelSheetTransactionSummary();
	}

	public List<TransactionDetail> getTransactionDetails() {
		return transactionDetails;
	}

	public List<TransactionSummary> getTransactionSummarys() {
		return transactionSummarys;
	}

	public Set<String> getParsedDates() {
		return parsedDates;
	}

	private List<TransactionDetail> parseExcelSheetTransactionDetail() {
		Workbook workbook;
		Sheet sheet = null;
		TransactionDetail transactionDetail = null;
		try {
			workbook = Workbook.getWorkbook(excelFile);
			sheet = workbook.getSheet("交易列表");
			// 验证数据格式
			if (getAllFields(TransactionDetail.class, new ArrayList<Field>()).size() != (sheet.getColumns() + 1)) {
				throw new Exception("Failed to excute parseExcelSheetTransactionDetail!");
			}
			// 从第二行开始遍历
			for (int i = 1; i < sheet.getRows(); i++) {
				transactionDetail = new TransactionDetail(
						sheet.getCell(0, i).getContents(),			// systemReferenceNumber
						sheet.getCell(1, i).getContents(),			// orginalTransactionSystemReferenceNumber
						sheet.getCell(2, i).getContents(),			// province
						sheet.getCell(3, i).getContents(),			// city
						sheet.getCell(4, i).getContents(),			// transactionType
						sheet.getCell(5, i).getContents(),			// customerName
						sheet.getCell(6, i).getContents(),			// shortCardNumber
						sheet.getCell(7, i).getContents(),			// publishCardInstitute
						sheet.getCell(8, i).getContents(),			// cardOrganization
						sheet.getCell(9, i).getContents(),			// transactionCash
						sheet.getCell(10, i).getContents(),			// originalTransactionCash
						DateUtil.parse(sheet.getCell(11, i).getContents()),		// transactionDatetime
						sheet.getCell(12, i).getContents(),			// transactionFlag
						sheet.getCell(13, i).getContents(),			// authorizationCode
						sheet.getCell(14, i).getContents(),			// responseCode
						sheet.getCell(15, i).getContents(),			// externalTraceNumber
						sheet.getCell(16, i).getContents(),			// externalCustomerNumber
						sheet.getCell(17, i).getContents(),			// serviceChannelTraceNumber
						sheet.getCell(18, i).getContents(),			// settleCash
						sheet.getCell(19, i).getContents(),			// settleDatetime
						sheet.getCell(20, i).getContents(),			// terminalNumber
						sheet.getCell(21, i).getContents(),			// originalTerminalNumber
						sheet.getCell(22, i).getContents(),			// operatorNumber
						sheet.getCell(23, i).getContents(),			// productNumber
						sheet.getCell(24, i).getContents(),			// orderCashType
						sheet.getCell(25, i).getContents(),			// orderOfferCash
						sheet.getCell(26, i).getContents(),			// orderPayCashType
						sheet.getCell(27, i).getContents(),			// orderPayCash
						sheet.getCell(28, i).getContents(),			// rateProvideFlag
						sheet.getCell(29, i).getContents(),			// rateOrientation
						sheet.getCell(30, i).getContents()			// rate
						);
				String dateTrimTime = Utils.formatDateTrimTime(transactionDetail.getTransactionDatetime());
				if(!dateFilters.contains(dateTrimTime)) {
					parsedDates.add(dateTrimTime);
					transactionDetails.add(transactionDetail);
				}
			}
			// 获取第一条记录的交易时间作为TransactionSummary的时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtil.parse(sheet.getCell(11, 2).getContents()));
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			currentDay = calendar.getTime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return transactionDetails;
	}

	private static final int summaryDateIndex = -1;
	private static final int customerNameIndex = 0;
	private static final int transactionCashIndex = 1;
	private static final int returnGoodsCashIndex = 2;
	private static final int transactionFeeIndex = 3;
	private static final int authorizationFeeIndex = 4;
	private static final int settleCashIndex = 5;
	private static final int transactionCountIndex = 6;
	private static final int returnGoodsCountIndex = 7;
	private static final int successfulCountIndex = 8;
	private static final int failedCountIndex = 9;
	
	private List<TransactionSummary> parseExcelSheetTransactionSummary() {
		Workbook workbook;
		Sheet sheet = null;
		TransactionSummary transactionSummary = null;
		try {
			workbook = Workbook.getWorkbook(excelFile);
			sheet = workbook.getSheet("按商户汇总");
			// 验证数据格式
			if (getAllFields(TransactionSummary.class, new ArrayList<Field>()).size() != (sheet.getColumns() + 2)) {
				throw new Exception("Failed to excute parseExcelSheetTransactionDetail!");
			}
			// 从第二行开始遍历
			for (int i = 1; i < sheet.getRows(); i++) {
				transactionSummary = new TransactionSummary(
						currentDay,																														// summaryDate
						sheet.getCell(customerNameIndex, i).getContents(),												// customerName
						Double.parseDouble(sheet.getCell(transactionCashIndex, i).getContents()),			// transactionCash
						Double.parseDouble(sheet.getCell(returnGoodsCashIndex, i).getContents()),			// returnGoodsCash
						Double.parseDouble(sheet.getCell(transactionFeeIndex, i).getContents()),				// transactionFee
						Double.parseDouble(sheet.getCell(authorizationFeeIndex, i).getContents()),			// authorizationFee
						Double.parseDouble(sheet.getCell(settleCashIndex, i).getContents()),						// settleCash
						Integer.parseInt(sheet.getCell(transactionCountIndex, i).getContents()),					// transactionCount
						Integer.parseInt(sheet.getCell(returnGoodsCountIndex, i).getContents()),				// returnGoodsCount
						Integer.parseInt(sheet.getCell(successfulCountIndex, i).getContents()),					// successfulCount
						Integer.parseInt(sheet.getCell(failedCountIndex, i).getContents())							// failedCount
						);
				transactionSummarys.add(transactionSummary);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactionSummarys;
	}

	public static List<Field> getAllFields(Class<?> type, List<Field> fields) {
		for (Field field : type.getDeclaredFields()) {
			fields.add(field);
		}
		if (type.getSuperclass() != null) {
			fields = getAllFields(type.getSuperclass(), fields);
		}
		return fields;
	}

}
