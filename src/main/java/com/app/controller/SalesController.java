package com.app.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.app.dao.SalesDAO;
import com.app.dto.CustomerResponse;
import com.app.dto.PoultryResponse;
import com.app.dto.SaleInvoiceDTO;
import com.app.dto.SalesResponse;
import com.app.email.helper.FlyingSaucerPDFUtil;
import com.app.entity.ProductSales;
import com.app.entity.Sales;
import com.app.enumeration.RequestType;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.CustomerService;
import com.app.service.MessagePropertyService;
import com.app.service.PoultryService;
import com.app.service.SalesService;
import com.app.util.message.ResponseMessage;
import com.app.validator.SalesValidator;
import com.app.validator.ValidationResult;
import com.sun.istack.NotNull;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/sale")
@Api(value = "sale Rest API's", produces = "application/json", consumes = "application/json")
public class SalesController {

	private final SpringTemplateEngine templateEngine;

	public SalesController(SpringTemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private @NonNull ResponseGenerator responseGenerator;
	
	private @NonNull SalesDAO salesDAO;
	
	private @NonNull SalesService salesService;
	
	private @NonNull PoultryService poultryService;
	
	private @NonNull CustomerService customerService;
	
	@Autowired
	MessagePropertyService messagePropertyService;

	@Autowired
	private @NonNull FlyingSaucerPDFUtil flyingSaucerPDFUtil;

	@Autowired
	private @NotNull SalesValidator salesValidator;

	@ApiOperation(value = " Create sale List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createSale(@RequestBody ProductSales sales, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("sale updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		try {
		
		ValidationResult validationResult = salesValidator.validateBill(RequestType.POST, sales);
		if (null == sales) {
			return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
					HttpStatus.BAD_REQUEST);
		}
		ProductSales salesObj = salesService.findBySalesNo(sales.getSalesNo());
		if (null != salesObj) {
			return responseGenerator.errorResponse(context,
					messagePropertyService.getMessage("salesInvoice.duplicate"/* , params */), HttpStatus.BAD_REQUEST);
		}

		
			salesService.saveOrUpdate(sales);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("sale.create"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating sale {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = " Get sale List.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getSales(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("sale updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("sale.get"),
					salesService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating sale {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow to  Update sale List.", response = Response.class)
	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateSales(@RequestBody ProductSales sale, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("customer updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			ValidationResult validationResult = salesValidator.validateBill(RequestType.PUT, sale);
			if (null == sale) {
				return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT,
						HttpStatus.BAD_REQUEST);
			}
			ProductSales salesObj = salesService.findBySalesNoExcludeId(sale.getSalesNo(), sale.getId());
			if (null != salesObj) {
				String[] params = new String[] { sale.getSalesNo() };
				return responseGenerator.errorResponse(context,
						messagePropertyService.getMessage("sale.salesNo", params), HttpStatus.BAD_REQUEST);
			}
			Set<UUID> productSales = new HashSet<>();
			for (Sales product : sale.getSales()) {
				UUID productId = product.getProductId();
				if (productSales.contains(productId)) {
					return responseGenerator.errorResponse(context, messagePropertyService.getMessage("sale.product"),
							HttpStatus.BAD_REQUEST);
				}
				productSales.add(productId);
			}
			salesService.update(sale);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("sale.update"),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch sale by  id.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("customer get started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("sale.get.one"),
					salesService.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating sale {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch product by  PoultryId.", response = Response.class)
	@RequestMapping(value = "/get/product/{PoultryId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProduct(@PathVariable("PoultryId") UUID PoultryId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("customer get started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("sale.get.one"),
					salesService.findByPoultryId(PoultryId), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating sale {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow to Delete sale List.", response = Response.class)
	@DeleteMapping(value = "/delete/{id}", produces = "application/json")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("customer delete started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		salesService.delete(id);
		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("sale.delete"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating sale {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch all sale details as a list.", response = Response.class)
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchSales(@RequestParam("saleNo") String saleNo, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("expense search started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("sale.get"),
					salesService.getSales(saleNo), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while updating expense {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to download pdf poultry by poultry id", response = Response.class)
	@RequestMapping(value = "/print/view/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> generateSalesPdf(@ApiParam(value = "id UUID") @PathVariable("id") UUID id,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("Sales get started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		List<SalesResponse> salesPdf = salesService.getSalesPdf(id);
		SaleInvoiceDTO salesno = salesService.findBySale(id);
		int serialNumber = 1;
		List<Integer> serialNumbers = new ArrayList<>();

		for (SalesResponse obj : salesPdf) {
			serialNumber++;
			PoultryResponse poultryBreed2 = poultryService.getPoultryId(obj.getPoultryId());
			CustomerResponse customerobj = salesService.findByCustomerId(obj.getCustomerId());

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("serialNumbers", serialNumbers);
			serialNumbers.add(serialNumber);
			serialNumber++;

			response.put("salesList", salesPdf);
			response.put("salesnoobj", salesno);
			response.put("customer", customerobj);
			response.put("poultryobj", poultryBreed2);

			try {

				byte[] report = flyingSaucerPDFUtil.generatePdf("sales/salePdf", response);
				InputStream excelInputStream = new ByteArrayInputStream(report);
				return ResponseEntity.ok().header("Access-Control-Expose-Headers", "Content-Disposition")
						.header("Access-Control-Expose-Headers", "Content-Disposition")
						.header("Content-Disposition", "attachment; filename= salesInvoice.pdf")
						.body(new InputStreamResource(excelInputStream));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}
		return null;
	}

	@ApiOperation(value = "Allow to Delete sale List.", response = Response.class)
	@DeleteMapping(value = "/product/delete/{id}", produces = "application/json")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("customer delete started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		salesService.deleteById(id);
		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("sale.delete"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating sale {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to download Sales as excel file.", response = Response.class)
	@RequestMapping(value = "/print/view/xl/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> downloadStudentExcelFile(@ApiParam(value = "UUID") @PathVariable("id") UUID id,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("Sales get started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		List<SalesResponse> salesXl = salesService.getSalesPdf(id);
		SaleInvoiceDTO salesno = salesService.findBySale(id);

		for (SalesResponse obj : salesXl) {
			PoultryResponse poultryBreed2 = poultryService.getPoultryId(obj.getPoultryId());
			CustomerResponse customerobj = salesService.findByCustomerId(obj.getCustomerId());

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("salesList", salesXl);
			response.put("salesnoobj", salesno);
			response.put("customer", customerobj);
			response.put("poultryobj", poultryBreed2);
		}
		try {
			Resource resource = new ClassPathResource("salesInvoice.xlsx");
			InputStream fileStream = resource.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
			XSSFSheet worksheet = workbook.getSheetAt(0);
			XSSFCellStyle style1 = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);

			for (int i = 0; i < salesXl.size(); i++) {
				SalesResponse salesRecord = salesXl.get(i);

				// Add the sales record to the Excel file
				XSSFRow row = worksheet.createRow(i + 1);
//				row.createCell(0).setCellValue(salesRecord.getSalesNo());
//				row.createCell(1).setCellValue(salesRecord.getPoultryName());
//				row.createCell(2).setCellValue(salesRecord.getName());
				row.createCell(0).setCellValue(salesRecord.getProductName());
				row.createCell(1).setCellValue(salesRecord.getQuantity());
				row.createCell(2).setCellValue(salesRecord.getRate());
				row.createCell(3).setCellValue(salesRecord.getAmount());
			}

//Add the subtotal, discount, and total payable values to the Excel file
			String subTotals = salesno.getSubTotal();
			int lastRow = salesXl.size() + 3;
			XSSFRow totalRow = worksheet.createRow(lastRow);
			XSSFCellStyle boldStyle = workbook.createCellStyle();
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);
			boldStyle.setFont(boldFont);
			XSSFCell cell = totalRow.createCell(2);
			cell.setCellValue("SubTotal");
			cell.setCellStyle(boldStyle);
			totalRow.createCell(3).setCellValue(subTotals);

			String discounts = salesno.getDiscount();
			XSSFRow discountRow = worksheet.createRow(lastRow + 1);
			XSSFCellStyle boldStyles = workbook.createCellStyle();
			Font boldFonts = workbook.createFont();
			boldFonts.setBold(true);
			boldStyles.setFont(boldFonts);
			XSSFCell cells = discountRow.createCell(2);
			cells.setCellValue("Discount");
			cells.setCellStyle(boldStyles);
			discountRow.createCell(3).setCellValue(discounts);

			String totalPayables = salesno.getTotalPayable();
			XSSFRow payableRow = worksheet.createRow(lastRow + 2);
			XSSFCellStyle boldStylees = workbook.createCellStyle();
			Font bold_Fonts = workbook.createFont();
			bold_Fonts.setBold(true);
			boldStylees.setFont(bold_Fonts);
			XSSFCell cels = payableRow.createCell(2);
			cels.setCellValue("Total Payable");
			cels.setCellStyle(boldStylees);
			payableRow.createCell(3).setCellValue(totalPayables);
			
			String saleNo = salesno.getSalesNo();
			int saleNotRow = salesXl.size() + 3;
			XSSFRow saleNoRows = worksheet.createRow(lastRow+3);
			XSSFCellStyle boldStylessale = workbook.createCellStyle();
			Font bold_Fontssale = workbook.createFont();
			bold_Fontssale.setBold(true);
			boldStylessale.setFont(bold_Fontssale);
			XSSFCell celsale = saleNoRows.createCell(2);
			celsale.setCellValue("Sale No");
			celsale.setCellStyle(boldStylessale);
			saleNoRows.createCell(3).setCellValue(saleNo);

			String poultry = salesno.getPoultryName();
			XSSFRow poultryRow = worksheet.createRow(lastRow + 4);
			XSSFCellStyle boldStylespoultry = workbook.createCellStyle();
			Font bold_Fontspoultry = workbook.createFont();
			bold_Fontspoultry.setBold(true);
			boldStylespoultry.setFont(bold_Fontspoultry);
			XSSFCell cellpoultry = poultryRow.createCell(2);
			cellpoultry.setCellValue("Poultry");
			cellpoultry.setCellStyle(boldStylespoultry);
			poultryRow.createCell(3).setCellValue(poultry);

			String customerName = salesno.getCustomerName();
			XSSFRow customerNameRow = worksheet.createRow(lastRow + 5);
			XSSFCellStyle boldStylesCustomerName = workbook.createCellStyle();
			Font bold_FontsCustomerName = workbook.createFont();
			bold_FontsCustomerName.setBold(true);
			boldStylesCustomerName.setFont(bold_FontsCustomerName);
			XSSFCell cellCustomerName = customerNameRow.createCell(2);
			cellCustomerName.setCellValue("CustomerName");
			cellCustomerName.setCellStyle(boldStylees);
			customerNameRow.createCell(3).setCellValue(customerName);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			workbook.close();
			byte[] barray = bos.toByteArray();
			InputStream excelInputStream = new ByteArrayInputStream(barray);
			String fileName = "sales." + "xlsx";
			return ResponseEntity.ok().header("Access-Control-Expose-Headers", "Content-Disposition")
					.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
					.body(new InputStreamResource(excelInputStream));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = " Get sale List.", response = Response.class)
	@RequestMapping(value = "/get/transaction", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getSalesTransaction(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("sale updated started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("sale.get"),
					salesService.getTransactionAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating sale {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "Allows to fetch all expense details as a list.", response = Response.class)
	@RequestMapping(value = "/transaction/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchExpense(@RequestParam(value = "poultryName", required = false) String poultryName,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("expense search started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("manageExpense.get"),
					salesService.getTransaction(poultryName, startDate, endDate), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while updating expense {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
