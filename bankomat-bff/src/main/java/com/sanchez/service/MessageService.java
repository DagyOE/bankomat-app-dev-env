package com.sanchez.service;

import com.sanchez.enumerate.Variable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyAfterEndFlow(DelegateExecution execution) {
        messagingTemplate.convertAndSend(
                "/topic/processStatus",
                "Process completed:" + execution.getVariable(Variable.TRANSACTION_ID.getKey())
        );
    }
}
