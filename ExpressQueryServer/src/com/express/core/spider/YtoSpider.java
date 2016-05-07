package com.express.core.spider;

import java.util.Map;

import com.express.core.http.HttpUtils;

public class YtoSpider {
	private static String URL = "http://weixin.yto56.com.cn/Service/WaybillTrace/YTOWaybillTrace.aspx?WaybillNO=";
	//http://www.kuaidi100.com/query?type=yuantong&postid=881443775034378914&id=1&valicode=&temp=0.31010607212566177
	//http://weixin.yto56.com.cn/Service/WaybillTrace/YTOWaybillTrace.aspx?WaybillNO=881667554913817264&Userid=oIIDzjgT1Ut6dO8uqGXOM3_pfA8U
	//http://qq.yto56.com.cn/NewWaybillTrace/WaybillTrace.aspx?WaybillNO=881667554913817264
	public Map<String, Object> spider(String number){
		String responseText = HttpUtils.doGet(URL+number);
		System.out.println(responseText);
		return null;
	}
	public static void main(String[] args){
		YtoSpider ytoSpider = new YtoSpider();
		ytoSpider.spider("881667554913817264");
	}
}
