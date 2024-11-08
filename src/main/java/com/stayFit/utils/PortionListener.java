package com.stayFit.utils;

import java.util.List;
import com.stayFit.portion.PortionCreateRequestDTO;

public interface PortionListener {
    void onPortionsAdded(String mealType, List<PortionCreateRequestDTO> portions);
}

