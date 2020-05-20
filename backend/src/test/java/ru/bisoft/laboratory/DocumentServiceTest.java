package ru.bisoft.laboratory;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.jsonwebtoken.lang.Assert;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.service.DocumentService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/spring/application-context.xml", "file:WebContent/WEB-INF/spring/jpa/jpa-context.xml", "file:WebContent/WEB-INF/spring/security/security-context.xml" })
public class DocumentServiceTest {

	@Autowired
	private DocumentService documentService;

	@Test
	public void crud() {/*
		Assert.notNull(documentService);
		Document doc = documentService.create();
		doc.setHeader("Тестовый Заголовок документа");
		doc.setBeginDate(LocalDate.now());
		doc.setEndDate(LocalDate.now());
		doc.setTitle("Тестовое Название документа");
		documentService.save(doc);
		doc.setHeader("Тестовый Заголовок документа 2");
		documentService.save(doc);
		documentService.delete(doc);
		Assert.notNull(doc.getId());
		*/
	}

	@Test
	public void search() {
		/*
		Page<Document> result = documentService.findByString("тест", null);
		Assert.notNull(result);
		result.getContent().forEach(d -> documentService.delete(d));*/
	}
}
