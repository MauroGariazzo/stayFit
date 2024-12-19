package com.stayFit.utils;

import java.util.List;
import com.stayFit.portion.PortionGetResponseDTO;

public interface PortionListener {
    void onPortionsAdded(String mealType, List<PortionGetResponseDTO> portions);
}

