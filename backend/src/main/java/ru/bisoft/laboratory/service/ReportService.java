package ru.bisoft.laboratory.service;

import net.sf.jasperreports.engine.JRDataSource;
import ru.bisoft.laboratory.domain.Report;
import ru.bisoft.laboratory.domain.Report.Format;

import java.util.Map;

public interface ReportService extends GuideService<Report> {

    byte[] generateReport(Report report, Format format, Map<String, Object> params, JRDataSource jrDataSource);

    byte[] generateReport(Integer id, Format format, Map<String, Object> params, JRDataSource jrDataSource);

    byte[] generateReport(Integer id, Format format, Map<String, Object> params);

    byte[] generateReport(Report report, Format format, Map<String, Object> params);

    void compileReport(Report report) throws Exception;

}
