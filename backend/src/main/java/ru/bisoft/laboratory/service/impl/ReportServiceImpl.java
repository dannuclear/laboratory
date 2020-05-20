package ru.bisoft.laboratory.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import ru.bisoft.laboratory.domain.Department;
import ru.bisoft.laboratory.domain.Organization;
import ru.bisoft.laboratory.domain.Report;
import ru.bisoft.laboratory.domain.auth.User;
import ru.bisoft.laboratory.repository.ReportRepository;
import ru.bisoft.laboratory.service.ReportService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('REPORT_ADMIN')")
public class ReportServiceImpl implements ReportService {

	private final ReportRepository reportRepository;
	private final DataSource dataSource;

	@Override
	@PreAuthorize("hasAuthority('REPORT_WRITE') or hasRole('REPORT_ADMIN')")
	public Report create() {
		log.info("Создаем отчет");
		Report report = new Report();
		return report;
	}

	@Override
	@PreAuthorize("hasAuthority('REPORT_READ') or hasRole('REPORT_ADMIN')")
	public Report findById(Integer id) {
		log.info("Извлекаем отчет по id {}", id);
		return reportRepository.findById(id).orElse(null);
	}

	@Override
	@PreAuthorize("hasAuthority('REPORT_READ') or hasRole('REPORT_ADMIN')")
	public Page<Report> findAll(Pageable pageable) {
		log.info("Извлекаем все показатели постранично {}", pageable);
		return reportRepository.findAll(pageable);
	}

	@Override
	@PreAuthorize("hasAuthority('REPORT_WRITE') or hasRole('REPORT_ADMIN')")
	public Report save(Report entity) {
		log.info("Сохраняем отчет {}", entity);
		return reportRepository.save(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('REPORT_WRITE') or hasRole('REPORT_ADMIN')")
	public void delete(Report entity) {
		log.info("Удаляем отчет {}", entity);
		reportRepository.delete(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('REPORT_WRITE') or hasRole('REPORT_ADMIN')")
	public Iterable<Report> saveAll(Iterable<Report> entities) {
		log.info("Сохраняем коллекцию показателей {}", entities);
		return reportRepository.saveAll(entities);
	}

	@Override
	@PreAuthorize("hasAuthority('REPORT_READ') or hasRole('REPORT_ADMIN')")
	public Page<Report> findByString(String value, Pageable pageable) {
		if (StringUtils.isEmpty(value))
			return findAll(pageable);
		log.info("Извлекаем все показатели по фильтру {} постранично {}", value, pageable);
		return reportRepository.findByNameContainsIgnoreCase(value, pageable);
	}

	@Override
	@PreAuthorize("permitAll()")
	public byte[] generateReport(Integer id, Report.Format format, Map<String, Object> params) {
		return generateReport(id, format, params, null);
	}

	@Override
	@PreAuthorize("permitAll()")
	public byte[] generateReport(Report report, Report.Format format, Map<String, Object> params) {
		return generateReport(report, format, params, null);
	}

	@Override
	@PreAuthorize("permitAll()")
	public byte[] generateReport(Integer id, Report.Format format, Map<String, Object> params, JRDataSource jrDataSource) {
		Report report = reportRepository.findById(id).get();
		return generateReport(report, format, params, jrDataSource);
	}

	@Override
	@PreAuthorize("permitAll()")
	public byte[] generateReport(Report report, Report.Format format, Map<String, Object> params, JRDataSource jrDataSource) {
		if (report == null)
			throw new IllegalArgumentException("Отчет не найден");
		if (report.getData() == null || report.getData().length == 0)
			throw new IllegalArgumentException("Отчет не скомпилирован");
		if (format == null)
			throw new IllegalArgumentException("Формат не задан скомпилирован");
		if (params == null)
			params = new HashMap<String, Object>();
		// params.putAll(getGlobalParams());
		log.info("Формирование HTML отчета: {} с параметрами {}", report.getName(), params);

		byte[] bytes = null;
		JasperReport jasperReport = null;
		Connection connection = null;
		try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
			jasperReport = (JasperReport) JRLoader.loadObject(new ByteArrayInputStream(report.getData()));

			connection = dataSource.getConnection();
			JasperPrint jasperPrint = null;
			if (jrDataSource != null)
				jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrDataSource);
			else
				jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);

			switch (format) {
			case DBF:
				break;
			case DOC:
				JRDocxExporter docExporter = new JRDocxExporter();
				docExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				docExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
				docExporter.exportReport();
				break;
			case EXCEL:
				JRXlsExporter xlsExporter = new JRXlsExporter();
				SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
				configuration.setCollapseRowSpan(true);
				xlsExporter.setConfiguration(configuration);
				xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
				xlsExporter.exportReport();
				break;
			case HTML:
				HtmlExporter htmlExporter = new HtmlExporter();
				htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(byteArray));
				htmlExporter.exportReport();
				break;
			case ODT:
				JROdtExporter odtExporter = new JROdtExporter();
				odtExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				odtExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
				odtExporter.exportReport();
				break;
			case PDF:
				JasperExportManager.exportReportToPdfStream(jasperPrint, byteArray);
				break;
			case RTF:
				JRRtfExporter rtfExporter = new JRRtfExporter();
				rtfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				rtfExporter.setExporterOutput(new SimpleWriterExporterOutput(byteArray));
				rtfExporter.exportReport();
				break;
			case XML:
				break;
			default:
				break;
			}
			bytes = byteArray.toByteArray();
		} catch (JRException | IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	private Map<String, Object> getGlobalParams() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Organization organization = null;
		Department department = null;
		if (user != null) {
			organization = user.getEmployee().getOrganization();
			department = user.getEmployee().getDepartment();
		}
		Map<String, Object> globalParams = new HashMap<>();
		if (organization != null) {
			globalParams.put("ORGANIZATION_NAME", organization.getName()); // Наименование учреждения
			globalParams.put("ORGANIZATION_FULLNAME", organization.getFullName()); // Полное наименование учреждения
			globalParams.put("ORGANIZATION_INN", organization.getINN()); // ИНН учреждения
			globalParams.put("ORGANIZATION_OGRN", organization.getOGRN()); // ОГРН учреждения
			globalParams.put("ORGANIZATION_ADDRESS", organization.getAddress()); // Адрес учреждения
//			globalParams.put("DIRECTOR_INITIAL", organization.getDirectorInitial()); // Инициалы директора
//			globalParams.put("DIRECTOR_POST", organization.getDirectorPost()); // Должность директора
			globalParams.put("USER_INITIAL", user.getEmployee().getSurname()); // Инициалы пользователя
			globalParams.put("USER_POST", "Должность пользователя"); // Должность пользователя
//			globalParams.put("ACCOUNTANT_INITIAL", organization.getAccountantInitial()); // Инициалы главного бухгалтера
//			globalParams.put("DISTRICT", organization.getDistrict()); // Район
		}
		if (department != null) {
//			globalParams.put("DEPARTMENT_SHORT_NAME", department.getShortName()); // Наименование отдела (краткое)
//			globalParams.put("DEPARTMENT_FULL_NAME", department.getFullName()); // Наименование отдела (полное)
//			globalParams.put("DEPARTMENT_CHIEF_FULL_NAME", department.getChiefFullName()); // ФИО начальника отдела
//			globalParams.put("DEPARTMENT_CHIEF_INITIAL", department.getChiefInitial()); // Инициалы начальника отдела
//			globalParams.put("DEPARTMENT_CHIEF_POST_NAME", department.getChiefPostName()); // Должность начальника отдела
//			globalParams.put("DEPARTMENT_PHONE", department.getPhone()); // Телефон отдела
		}
		return globalParams;
	}

	@Override
	public void compileReport(Report report) throws Exception {
		if (report == null || report.getSource() == null || report.getSource().length == 0)
			throw new IllegalArgumentException("Не задан отчет либо исходник");
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ByteArrayInputStream bais = new ByteArrayInputStream(report.getSource());
			JasperCompileManager.compileReportToStream(bais, baos);
			report.setData(baos.toByteArray());
			bais.close();
		} catch (JRException | IOException e) {
			throw e;
		}
	}
}
