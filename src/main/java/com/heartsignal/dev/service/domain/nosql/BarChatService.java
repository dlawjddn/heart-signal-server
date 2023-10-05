package com.heartsignal.dev.service.domain.nosql;

import com.heartsignal.dev.repository.nosql.BarChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BarChatService {

    private final BarChatRepository barChatRepository;

}
