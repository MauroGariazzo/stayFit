package com.stayFit.portion;

import java.util.List;
import com.stayFit.models.Portion;

public interface IPortionDAO {
	public void upsert(PortionCreateRequestDTO pcrDTO) throws Exception;
	public List<Portion> getMealPortions(PortionGetRequestDTO pgrDTO)throws Exception;
	public void delete(int id)throws Exception;
}
