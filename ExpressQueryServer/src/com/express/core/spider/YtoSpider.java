package com.express.core.spider;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.express.core.http.HttpUtils;

/**
 * 圆通爬虫
 * @author yuwenjin
 * @date 2016年5月6日
 */
public class YtoSpider {
	private static String URL = "http://q1.sto.cn/chaxun/result?express_no=";
	
	public Map<String, Object> spider(String number){
		String resoponText = HttpUtils.doGet(URL+number);
		Document doc = Jsoup.parse(resoponText);
		Element table = doc.select("table").first();
		Element element = table.select("tr").first().select("td").first().select("ul").first().select("li").first().select("i").first();
		System.out.println(element.text());
		return null;
	}
	public static void main(String[] args){
		YtoSpider ytoSpider = new YtoSpider();
		ytoSpider.spider("229698906739");
	}
}
