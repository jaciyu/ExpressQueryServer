package com.express.service;

import java.util.HashMap;
import java.util.Map;

import com.express.service.base.impl.SelectService;

/**
 *
 * @author yuwenjin
 * @date 2016年5月6日
 */
public class YtoService extends SelectService{
	@Override
	public Map<String, Object> selectResult(Map<String, Object> model){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "圆通的实现");
		return result;		
	}
}
