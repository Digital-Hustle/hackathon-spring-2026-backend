package com.hustle.rag_workspace_ms.service.entity.impl;

import com.hustle.rag_workspace_ms.enums.ChatRole;
import com.hustle.rag_workspace_ms.factory.ChatMessageFactory;
import com.hustle.rag_workspace_ms.model.AiMessage;
import com.hustle.rag_workspace_ms.model.RagQueryRequest;
import com.hustle.rag_workspace_ms.model.RagQueryResponse;
import com.hustle.rag_workspace_ms.model.entity.Message;
import com.hustle.rag_workspace_ms.repository.ChatMessageRepository;
import com.hustle.rag_workspace_ms.service.domain.impl.RagService;
import com.hustle.rag_workspace_ms.service.entity.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final RagService ragService;

    @Override
    public Page<Message> getByChatId(UUID workspaceId, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return chatMessageRepository.findAllByWorkspaceId(workspaceId, sortedPageable);
    }

    @Override
    public Message sendNew(UUID workspaceId, String content) {

        RagQueryRequest ragQueryRequest = new RagQueryRequest(workspaceId, content, 10, 3);

        RagQueryResponse ragQueryResponse = ragService.query(ragQueryRequest);

        Message message = ChatMessageFactory.newOwnerChatMessage(workspaceId, content);

        Message savedMessage = chatMessageRepository.save(message);

        List<Message> chatMessages = chatMessageRepository.findAllByWorkspaceId(workspaceId);
        List<AiMessage> aiMessages = chatMessages.stream()
                .map(msg -> AiMessage.builder()
                        .role(msg.getOwnedByUser()
                                ? ChatRole.USER.toString().toLowerCase()
                                : ChatRole.ASSISTANT.toString().toLowerCase())
                        .content(msg.getContent())
                        .build())
                .toList();

//        String aiResponse = aiGateway.sendMessageToModel(aiMessages);
        String aiResponseText = ragQueryResponse.answer();

        Message aiMessage = ChatMessageFactory.newAiChatMessage(workspaceId, aiResponseText);

        chatMessageRepository.save(aiMessage);
        return savedMessage;
    }
}
