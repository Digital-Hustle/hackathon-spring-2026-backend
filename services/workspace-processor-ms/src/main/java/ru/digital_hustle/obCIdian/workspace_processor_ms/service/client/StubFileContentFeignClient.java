package ru.digital_hustle.obCIdian.workspace_processor_ms.service.client;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentText;
import ru.digital_hustle.obCIdian.workspace_processor_ms.dto.response.DocumentTextRs;
import java.util.List;
import java.util.UUID;

@Service
@Profile("test") // Активируется ТОЛЬКО когда включен профиль 'test'
public class StubFileContentFeignClient implements SummaryFileContentFeignClient {

    @Override
    public DocumentTextRs getDocuments(UUID workspaceId) {
        // Генерируем фиктивные документы с тестовым контентом
        DocumentText doc1 = DocumentText.builder()
                .id(UUID.randomUUID())
                .content("""
                Протокол совещания от 20.10.2023.
                Присутствовали: Иванов А.А., Петров Б.Б.
                Обсуждали запуск проекта "Альфа".
                Срок запуска: 30.12.2023.
                Бюджет: 5 млн рублей.
                """)
                .build();

        DocumentText doc2 = DocumentText.builder()
                .id(UUID.randomUUID())
                .content("""
                Техническое задание на разработку.
                Ответственный: Сидоров В.В.
                Требования: высокая нагрузка, отказоустойчивость.
                Дедлайн первой версии: 01.11.2023.
                """)
                .build();

        return new DocumentTextRs(List.of(doc1, doc2));
    }
}