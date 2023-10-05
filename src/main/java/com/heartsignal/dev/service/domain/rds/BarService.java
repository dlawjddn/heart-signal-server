package com.heartsignal.dev.service.domain.rds;


import com.heartsignal.dev.domain.rds.Bar;
import com.heartsignal.dev.repository.rds.BarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarService {
    private final BarRepository barRepository;
    public List<String> findLocations(){
        return barRepository.findLocation();
    }
    public List<Bar> findBarsInLocation(String location){
        return barRepository.findByLocation(location);
    }

}
