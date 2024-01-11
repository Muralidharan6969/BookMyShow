package com.example.bookmyshow.Services;

import com.example.bookmyshow.Models.Show;
import com.example.bookmyshow.Models.ShowSeatMapping;
import com.example.bookmyshow.Models.ShowSeatType;
import com.example.bookmyshow.Repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculator {

    private ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    PriceCalculator(ShowSeatTypeRepository showSeatTypeRepository){
        this.showSeatTypeRepository = showSeatTypeRepository;
    }
    public int finalAmount(List<ShowSeatMapping> showSeatMappings, Show show){
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
        int totalAmount = 0;
        for(ShowSeatMapping showSeatMapping : showSeatMappings){
            for(ShowSeatType showSeatType : showSeatTypes){
                if(showSeatMapping.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    totalAmount += showSeatType.getPrice();
                    break;
                }
            }
        }
        return totalAmount;
    }
}
