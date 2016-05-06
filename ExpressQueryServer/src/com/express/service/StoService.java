package com.express.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.express.service.base.impl.SelectService;

@Service("stoService")
public class StoService extends SelectService{
	@Override
	public Map<String, Object> selectResult(Map<String, Object> model){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "申通的实现");
		return result;
	}
}
