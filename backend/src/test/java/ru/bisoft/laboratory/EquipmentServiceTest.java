package ru.bisoft.laboratory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.DocumentEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.domain.equipment.EquipmentMaintenance;
import ru.bisoft.laboratory.domain.equipment.EquipmentVerification;
import ru.bisoft.laboratory.service.DocumentEquipmentService;
import ru.bisoft.laboratory.service.DocumentService;
import ru.bisoft.laboratory.service.EquipmentMaintenanceService;
import ru.bisoft.laboratory.service.EquipmentService;
import ru.bisoft.laboratory.service.EquipmentVerificationService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/spring/application-context.xml", "file:WebContent/WEB-INF/spring/jpa/jpa-context.xml", "file:WebContent/WEB-INF/spring/security/security-context.xml" })
@WithMockUser(roles = { "ADMIN" })
public class EquipmentServiceTest {

	@Autowired
	private EquipmentService equipmentService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private DocumentEquipmentService documentEquipmentService;
	@Autowired
	private EquipmentVerificationService equipmentVerificationService;
	@Autowired
	private EquipmentMaintenanceService equipmentMaintenanceService;

	@Test
	public void crud() {
		assertNotNull(equipmentService);
		assertNotNull(documentService);
		Equipment equ = equipmentService.create();
		equ.setSerialNumber("12345");
		equ.setName("Тестовое оборудование 2");

		EquipmentVerification ev = EquipmentVerification.builder().date(LocalDate.now()).number("3231").build();
		equ.setVerificationList(Arrays.asList(ev));

		EquipmentMaintenance em = EquipmentMaintenance.builder().date(LocalDate.now()).number("3231").build();
		equ.setMaintenanceList(Arrays.asList(em));

		Document document = documentService.findById(42);
		assertNotNull(document);
		DocumentEquipment de = DocumentEquipment.builder().document(document).build();
		equ.setDocumentEquipments(Arrays.asList(de));

		equipmentService.save(equ);
		assertNotNull(equ.getId(), "Вернулось оборудование с пустым id");

		List<DocumentEquipment> del = documentEquipmentService.findByEquipment(equ, PageRequest.of(0, 999)).getContent();
		assertThat(del.size() == 1);

		List<EquipmentVerification> evl = equipmentVerificationService.findByEquipment(equ, PageRequest.of(0, 999)).getContent();
		assertThat(evl.size() == 1);

		List<EquipmentMaintenance> eml = equipmentMaintenanceService.findByEquipment(equ, PageRequest.of(0, 999)).getContent();
		assertThat(eml.size() == 1);

		equ.setMaintenanceList(Collections.emptyList());
		equ.setVerificationList(Collections.emptyList());
		equ.setDocumentEquipments(Collections.emptyList());

		equipmentService.save(equ);

		del = documentEquipmentService.findByEquipment(equ, PageRequest.of(0, 999)).getContent();
		assertThat(del.size() == 0);

		evl = equipmentVerificationService.findByEquipment(equ, PageRequest.of(0, 999)).getContent();
		assertThat(evl.size() == 0);

		eml = equipmentMaintenanceService.findByEquipment(equ, PageRequest.of(0, 999)).getContent();
		assertThat(eml.size() == 0);

		equipmentService.delete(equ);
	}

	@Test
	public void search() {
		/*
		 * Page<EquipmentPage> result = equipmentService.findByString("тест", null);
		 * Assert.notNull(result); Assert.notEmpty(result.getContent());
		 * result.getContent().forEach(d -> equipmentService.delete(d));
		 */
	}
}
