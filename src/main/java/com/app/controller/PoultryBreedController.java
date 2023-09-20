package com.app.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
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

import com.app.dto.PoultryBreedDTO;
import com.app.dto.PoultryResponse;
import com.app.email.helper.FlyingSaucerPDFUtil;
import com.app.entity.PoultryBreed;
import com.app.entity.PoultryMapping;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.BreedService;
import com.app.service.MessagePropertyService;
import com.app.service.PoultryBreedService;
import com.app.service.PoultryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/poultrybreed")
@Api(value = "BreedType Rest API's", produces = "application/json", consumes = "application/json")
public class PoultryBreedController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private @NonNull PoultryBreedService poultryBreedService;

	@Autowired
	private @NonNull ResponseGenerator responseGenerator;

	@Autowired
	private @NonNull PoultryService poultryService;

	private @NonNull BreedService breedService;

	@Autowired
	MessagePropertyService messagePropertyService;

	@Autowired
	private @NonNull FlyingSaucerPDFUtil flyingSaucerPDFUtil;

	@ApiOperation(value = " Create PoultryBreed List.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> createBreed(@RequestBody PoultryMapping poultryMapping,
			@RequestHeader HttpHeaders httpHeader) throws Exception {

		logger.info("poultrybreed create started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			poultryBreedService.saveOrUpdate(poultryMapping);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("poultrybreed.create"),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while creating poultrybreed {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Allow to  Update poultrybreed List.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> updateBreed(@RequestBody PoultryMapping poultryMapping,
			@RequestHeader HttpHeaders httpHeader) throws Exception {

		logger.info("poultrybreed updated started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		Object isPoultryIdUnique = poultryBreedService.countByPoultryId(poultryMapping.getPoultryId(),
				poultryMapping.getId());
		if (null != isPoultryIdUnique) {
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("poultrybreed.poultryId"),
					HttpStatus.BAD_REQUEST);
		}

		Set<UUID> breedIds = new HashSet<>();
		for (PoultryBreed poultryBreed : poultryMapping.getPoultryBreed()) {
			UUID breedId = poultryBreed.getBreedId();
			if (breedIds.contains(breedId)) {
				return responseGenerator.errorResponse(context,
						messagePropertyService.getMessage("poultrybreed.breedId"), HttpStatus.BAD_REQUEST);
			}
			breedIds.add(breedId);
		}

		poultryBreedService.Update(poultryMapping);
		try {
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("poultrybreed.update"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while getting poultrybreed {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Allow to Delete poultrybreed List.", response = Response.class)
	@DeleteMapping(value = "/delete/{poultryBreedId}", produces = "application/json")
	public ResponseEntity<?> deleteBreed(@PathVariable("poultryBreedId") UUID poultryBreedId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {

		logger.info("poultrybreed delete started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			poultryBreedService.deleteBreed(poultryBreedId);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("poultrybreed.delete"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while deleting poultrybreed {}", e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("poultrybreed.invalid"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get poultrybreed List.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getBreed(@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("poultrybreed get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("poultrybreed.get"),
					poultryBreedService.getAll(), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while getting poultrybreed {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = " Get poultrybreed List By id.", response = Response.class)
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getByIdBreed(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("poultrybreed get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("poultrybreed.get"),
					poultryBreedService.getId(id), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while  getting poultrybreed {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = " Get poultrybreed List By poultryId.", response = Response.class)
	@RequestMapping(value = "/poultry/get/{poultryId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getByid(@PathVariable("poultryId") UUID poultryId, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		logger.info("poultrybreed get started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("poultrybreed.get"),
					poultryBreedService.getByPoultryId(poultryId), HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while  getting poultrybreed {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to download pdf poultry by poultry id", response = Response.class)
	@RequestMapping(value = "/print/view/{breedMappingId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> generatePoultryPdf(
			@ApiParam(value = "breedMappingId UUID") @PathVariable("breedMappingId") UUID breedMappingId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("poultrybreed get started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		List<PoultryBreedDTO> poultryBreed = poultryBreedService.getPoultryBreedByPoultryId(breedMappingId);
		for (PoultryBreedDTO obj : poultryBreed) {
			PoultryResponse poultryBreed2 = poultryService.getPoultryId(obj.getPoultryId());

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("breedList", poultryBreed);
			response.put("poultyrobj", poultryBreed2);

			try {
				byte[] report = flyingSaucerPDFUtil.generatePdf("breed/pdf-breed", response);
				InputStream excelInputStream = new ByteArrayInputStream(report);
				return ResponseEntity.ok().header("Access-Control-Expose-Headers", "Content-Disposition")
						.header("Access-Control-Expose-Headers", "Content-Disposition")
						.header("Content-Disposition", "attachment; filename= Poultyr Breed.pdf")
						.body(new InputStreamResource(excelInputStream));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);

				return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
			}

		}
		return null;
	}

	@ApiOperation(value = "Allows to download StudentDetails excel file.", response = Response.class)
	@RequestMapping(value = "/print/xl/{breedMappingId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> downloadPoultryBreedExcelFile(
			@ApiParam(value = "UUID") @PathVariable("breedMappingId") UUID breedMappingId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		List<PoultryBreedDTO> poultryBreed = poultryBreedService.getPoultryBreedByPoultryId(breedMappingId);

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("poultryBreed", poultryBreed);
		try {
			Resource resource = new ClassPathResource("poultry-breed .xlsx");
			InputStream fileStream = resource.getInputStream();

			XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
			XSSFSheet worksheet = workbook.getSheetAt(0);
			XSSFCellStyle style1 = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			for (int i = 0; i < poultryBreed.size(); i++) {
				XSSFRow row = worksheet.createRow(i + 1);
			//	row.createCell(0).setCellValue(poultryBreed.get(i).getPoultryName());
				row.createCell(0).setCellValue(poultryBreed.get(i).getBreedName());
				row.createCell(1).setCellValue(poultryBreed.get(i).getTotalCount());
			}
			for (PoultryBreedDTO obj : poultryBreed) {
				PoultryResponse poultryBreed2 = poultryService.getPoultryId(obj.getPoultryId());
			
			String    Poultry = poultryBreed2.getPoultryName();
			int lastRow = poultryBreed.size() + 3;
			XSSFRow totalRow = worksheet.createRow(lastRow);
			XSSFCellStyle boldStyle = workbook.createCellStyle();
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);
			boldStyle.setFont(boldFont);
			XSSFCell cell = totalRow.createCell(0);
			cell.setCellValue("POULTRY");
			cell.setCellStyle(boldStyle);

		    totalRow.createCell(1).setCellValue(Poultry);
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			workbook.close();
			byte[] barray = bos.toByteArray();
			InputStream excelInputStream = new ByteArrayInputStream(barray);
			String fileName = "poultry-breed." + "xlsx";
			return ResponseEntity.ok().header("Access-Control-Expose-Headers", "Content-Disposition")
					.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
					.body(new InputStreamResource(excelInputStream));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allows to fetch all poultry details as a list.", response = Response.class)
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchPoultry(@RequestParam("poultryName") String poultryName,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		logger.info("poultry breed search started {}", LocalDateTime.now());
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messagePropertyService.getMessage("poultry.get"),
					poultryBreedService.getAllPoultryBreed(poultryName), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error occured while updating poultry {}", e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Allow to Delete poultrybreed List.", response = Response.class)
	@DeleteMapping(value = "/breed/delete/{id}", produces = "application/json")
	public ResponseEntity<?> deleteBreedId(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {

		logger.info("poultrybreed delete started {}", LocalDateTime.now());

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		try {
			poultryBreedService.deleteBreedId(id);
			return responseGenerator.successResponse(context, messagePropertyService.getMessage("poultrybreed.delete"),
					HttpStatus.OK);
		} catch (Exception e) {

			logger.error("error occured while deleting poultrybreed {}", e);
			return responseGenerator.errorResponse(context, messagePropertyService.getMessage("poultrybreed.invalid"),
					HttpStatus.BAD_REQUEST);
		}
	}
}
