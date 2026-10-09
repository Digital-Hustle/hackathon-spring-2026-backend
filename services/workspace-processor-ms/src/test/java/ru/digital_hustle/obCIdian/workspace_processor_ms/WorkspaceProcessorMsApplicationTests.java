package ru.digital_hustle.obCIdian.workspace_processor_ms;

import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(properties = {
		"app.minio.enabled=false",
		"app.rag.enabled=false"
})
class WorkspaceProcessorMsApplicationTests {

	@MockitoBean
	private MinioClient minioClient;

	@Test
	void contextLoads() {
	}

}
