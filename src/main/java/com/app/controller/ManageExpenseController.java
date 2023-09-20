package com.app.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ManageExpenseDTO;
import com.app.dto.ManageExpenseResponseDTO;
import com.app.dto.PoultryResponse;
import com.app.email.helper.FlyingSaucerPDFUtil;
import com.app.entity.ManageExpense;
import com.app.repository.ManageExpenseRepository;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.ManageExpenseService;
import com.app.service.MessagePropertyService;
import com.app.service.PoultryService;
import com.app.util.message.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/manage")
@Api(value = "ManageExpense Rest API's", produces = "application/json", consumes = "application/json")
public class ManageExpenseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private @NonNull ResponseGenerator responseGenerator;

	private @NonNull ManageExpenseService manageExpenseService;

	private @NonNull ManageExpenseRepository manageExpenseRepository;

	@Autowired
	MessagePropertyService messagePropertyService;
	@Autowired
	private @NonNull FlyingSaucerPDFUtil flyingSaucerPDFUtil;

	PoultryService poultryService;

	static final String DATE_FORMAT_DB = "yyyy-MM-dd";

	@ApiOperation(value = " Create manageExpense List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createExpense(@RequestBody ManageExpense manage, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("ManageExpense created started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == manage) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		ManageExpense manageObj = manageExpenseService.findByExpenseHeadIdAndPoultryIdAndDate(manage.getExpenseHeadId(),
				manage.getPoultryId(), manage.getDate());
		if (null != manageObj) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("manageExpense.duplicate"), HttpStatus.BAD_REQUEST);
		}

		try {

			manageExpenseService.save(manage);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("manageExpense.create"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while creating ManageExpense {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get manageExpense List.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getExpense(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("ManageExpense created started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("manageExpense.get"),
					manageExpenseService.getAll(), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while creating ManageExpense {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get manageExpense List.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getById(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("ManageExpense created started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("manageExpense.get"),
					manageExpenseService.getById(id), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while creating ManageExpense {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get manageExpense List.", response = Response.class)
	@RequestMapping(value = "/getby/{poultryId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getByPoultryId(@PathVariable("poultryId") UUID poultryId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("ManageExpense created started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("manageExpense.get"),
					manageExpenseService.getByPoultryId(poultryId), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while creating ManageExpense {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get manageExpense List.", response = Response.class)
	@RequestMapping(value = "/getid/{expenseHeadId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getExpenseById(@PathVariable("expenseHeadId") UUID expenseHeadId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("agent updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("manageExpense.get"),
					manageExpenseService.getByexpenseId(expenseHeadId), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while getting agent {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/getexpense", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllExpense(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("agent updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("manageExpense.get"),
					manageExpenseService.getBypoultryId(), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while getting agent {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = " update manageExpense List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateExpense(@RequestBody ManageExpense manage, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("ManageExpense created started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		if (null == manage) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		ManageExpense manageObj = manageExpenseService.findByExpenseHeadIdAndPoultryIdAndDateExcludeId(
				manage.getExpenseHeadId(), manage.getPoultryId(), manage.getDate(), manage.getId());
		if (null != manageObj) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("manageExpense.duplicate"), HttpStatus.BAD_REQUEST);
		}
		try {
			manageExpenseService.update(manage);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("manageExpense.update"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while creating ManageExpense {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get manageExpense List.", response = Response.class)
	@DeleteMapping(value = "/delete/{id}", produces = "application/json")
	public ResponseEntity<?> deleteById(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("ManageExpense updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		manageExpenseService.deleteById(id);
		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("manageExpense.delete"),

					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while creating ManageExpense {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch all expense details as a list.", response = Response.class)
	@RequestMapping(value = "print/view", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> PoultryBreedExpense(@RequestParam("poultryName") String poultryName,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("poultrybreed get started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		ManageExpenseResponseDTO expense = manageExpenseService.getPdf(poultryName, startDate, endDate);
		List<ManageExpenseResponseDTO> manageExpense = manageExpenseService.getByPoultryExpenseId(poultryName,
				startDate, endDate);
		PoultryResponse poultryBreed2 = null;
		if (!CollectionUtils.isEmpty(manageExpense)) {
			poultryBreed2 = poultryService.getPoultryId(manageExpense.get(0).getPoultryId());
		}

		DateFormat input = new SimpleDateFormat(DATE_FORMAT_DB);
		Date staDate = input.parse(startDate);
		Date enDate = input.parse(endDate);
		DateFormat foemat = new SimpleDateFormat("dd/MM/yyyy");
		String startdate = foemat.format(staDate);
		String enddate = foemat.format(enDate);

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("expenseList", manageExpense);
		response.put("expence", expense);
		response.put("poultryobj", poultryBreed2);
		response.put("poultryname", poultryName);
		response.put("startdate", startdate);
		response.put("enddate", enddate);
		try {
			byte[] report = flyingSaucerPDFUtil.generatePdf("expense/expensePdf", response);
			InputStream excelInputStream = new ByteArrayInputStream(report);
			return ResponseEntity.ok().header("Access-Control-Expose-Headers", "Content-Disposition")
					.header("Access-Control-Expose-Headers", "Content-Disposition")
					.header("Content-Disposition", "attachment; filename= Expense.pdf")
					.body(new InputStreamResource(excelInputStream));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to download StudentDetails excel file.", response = Response.class)
	@RequestMapping(value = "/print/view/xl", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> downloadStudentExcelFile(@RequestParam("poultryName") String poultryName,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader HttpHeaders httpHeader) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		ManageExpenseResponseDTO manageExpense = manageExpenseService.getPdf(poultryName, startDate, endDate);
		List<ManageExpenseResponseDTO> manageExpens = manageExpenseService.getByPoultryExpenseId(poultryName, startDate,
				endDate);
		for (ManageExpenseResponseDTO obj : manageExpens) {
			PoultryResponse poultryBreed2 = poultryService.getPoultryId(manageExpense.getPoultryId());

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("expenseList", manageExpense);
			response.put("poultryobj", poultryBreed2);
			response.put("manageList", manageExpens);
		}
		try {
			Resource resource = new ClassPathResource("expense_template.xlsx");
			InputStream fileStream = resource.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
			XSSFSheet worksheet = workbook.getSheetAt(0);
			XSSFCellStyle style1 = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);

			for (int i = 0; i < manageExpens.size(); i++) {
				XSSFRow row = worksheet.createRow(i + 1);
				// row.createCell(0).setCellValue(manageExpens.get(i).getPoultryName());
				row.createCell(0).setCellValue(manageExpens.get(i).getExpenseType());
				row.createCell(1).setCellValue(manageExpens.get(i).getDescription());
				row.createCell(2).setCellValue(manageExpens.get(i).getFromDate());
				row.createCell(3).setCellValue(manageExpens.get(i).getAmount());

			}
			String totalAmount = manageExpense.getTotalAmount();
			int lastRow = manageExpens.size() + 3;
			XSSFRow totalRow = worksheet.createRow(lastRow);
			XSSFCellStyle boldStyle = workbook.createCellStyle();
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);
			boldStyle.setFont(boldFont);
			XSSFCell cell = totalRow.createCell(2);
			cell.setCellValue("TotalAmount");
			cell.setCellStyle(boldStyle);
			totalRow.createCell(3).setCellValue(totalAmount);

			String poultryNames = manageExpense.getPoultryName();
			XSSFRow totalRowa = worksheet.createRow(lastRow + 1);
			XSSFCellStyle boldStyles = workbook.createCellStyle();
			Font boldFonts = workbook.createFont();
			boldFonts.setBold(true);
			boldStyles.setFont(boldFonts);
			XSSFCell cells = totalRowa.createCell(2);
			cells.setCellValue("PoultryName");
			cells.setCellStyle(boldStyles);
			totalRowa.createCell(3).setCellValue(poultryNames);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			workbook.close();
			byte[] barray = bos.toByteArray();
			InputStream excelInputStream = new ByteArrayInputStream(barray);

			String fileName = "ManageExpense." + "xlsx";

			return ResponseEntity.ok().header("Access-Control-Expose-Headers", "Content-Disposition")
					.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
					.body(new InputStreamResource(excelInputStream));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch all expense details as a list.", response = Response.class)
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchExpense(@RequestParam(value = "poultryName", required = false) String poultryName,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("expense search started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("manageExpense.get"),
					manageExpenseService.getAllBreed(poultryName, startDate, endDate), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while updating expense {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
