package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Report;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.ReportService;

import javax.validation.Valid;
import java.util.Map;

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
                fileName.append("report").append('.').append(format.getLabel());
                break;
            case DOC:
                mimeType = "application/msword";
                fileName.append("report").append('.').append(format.getLabel());
                break;
            case EXCEL:
                mimeType = "application/msexcel";
                fileName.append("report").append('.').append("xlsx");
                break;
            case HTML:
                mimeType = "text/html";
                fileName.append("report").append('.').append(format.getLabel());
                break;
            case ODT:
                mimeType = "application/vnd.oasis.opendocument.text";
                fileName.append("report").append('.').append(format.getLabel());
                break;
            case PDF:
                mimeType = "application/pdf";
                fileName.append("report").append('.').append(format.getLabel());
                break;
            case RTF:
                mimeType = "application/msword";
                fileName.append("report").append('.').append("docx");
                break;
            case XML:
                mimeType = "text/xml";
                fileName.append("report").append('.').append(format.getLabel());
                break;
            default:
                break;
        }

        return ResponseEntity //
                .ok() //
                .header("Content-Type", mimeType + "; charset=UTF-8") //
                .header("Content-Disposition", "inline; filename=\"" + fileName.toString() + "\"") //
                .body(bytes);
    }
}
