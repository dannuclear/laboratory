package ru.bisoft.laboratory.rest;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.bisoft.laboratory.domain.Report;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.ReportService;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;

	@GetMapping("/{report}")
	public ResponseEntity<Report> findOne(@PathVariable Report report) {
		return ResponseEntity.ok(report);
	}

	@GetMapping
	public ResponseEntity<PagedModel<Report>> findAll(Pageable pageable, @RequestParam(name = "search", required = false) String search) {
		Page<Report> page = reportService.findByString(search, pageable);
		return ResponseEntity.ok(PagedModel.wrap(page));
	}

	@PostMapping
	public Report save(@Valid @RequestBody final Report report) throws Exception {
		if (report != null && report.hasSource())
			reportService.compileReport(report);
		return reportService.save(report);
	}

	@PutMapping("/{report}")
	public Report save(@PathVariable Report report, @Valid @RequestBody Report newReport) throws Exception {
		if (newReport != null && newReport.hasSource())
			reportService.compileReport(newReport);
		return reportService.save(newReport);
	}

	@DeleteMapping("/{report}")
	public void delete(@PathVariable Report report) {
		reportService.delete(report);
	}

	@GetMapping("/generate")
	public ResponseEntity<byte[]> generate(//
			@RequestParam(value = "id", required = true) Integer id, //
			@RequestParam(value = "format", required = true) Report.Format format, @RequestParam Map<String, Object> parameters) {
		byte[] bytes = reportService.generateReport(id, format, parameters);

		String mimeType = null;
		StringBuilder fileName = new StringBuilder();
		switch (format) {
		case DBF:
			mimeType = "application/dbf";
			break;
		case DOC:
			mimeType = "application/msword";
			break;
		case EXCEL:
			mimeType = "application/msexcel";
			break;
		case HTML:
			mimeType = "text/html";
			break;
		case ODT:
			mimeType = "application/vnd.oasis.opendocument.text";
			break;
		case PDF:
			mimeType = "application/pdf";
			break;
		case RTF:
			mimeType = "application/msword";
			break;
		case XML:
			mimeType = "text/xml";
			break;
		default:
			break;
		}
		fileName.append("report").append('.').append(format.getLabel());
		return ResponseEntity //
				.ok() //
				.header("Content-Type", mimeType + "; charset=UTF-8") //
				.header("Content-Disposition", "inline; filename=\"" + fileName.toString() + "\"") //
				.body(bytes);
	}
}
