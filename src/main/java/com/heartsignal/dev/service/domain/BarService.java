package com.heartsignal.dev.service.domain;


import com.heartsignal.dev.repository.BarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BarService {

    private final BarRepository barRepository;
}
