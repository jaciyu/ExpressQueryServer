package com.express.core.spider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.express.core.constant.ExpressConstant;
import com.express.core.http.HttpUtils;
import com.express.core.response.ResponseData;
import com.express.core.response.ResponseMsg;
import com.express.core.util.DateUtil;
import com.express.core.util.StringUtils;

/**
 * 圆通爬虫
 * @author yuwenjin
 * @date 2016年5月6日
 */
public class StoSpider {
	private static final Logger log = Logger.getLogger(StoSpider.class);
	private static String URL = "http://q1.sto.cn/chaxun/result?express_no=";
	private static String KUAI_DI100_URL = "http://www.kuaidi100.com/query?type=shentong&postid=";
	//请求 URL: http://m.kuaidihelp.com/express/query?word=229855869255
	//请求 URL: http://www.kuaidi100.com/query?type=shentong&postid=229855869255&id=1&valicode=&temp=0.8856839774927254
	//请求 URL: http://www.kiees.cn/sto.php?wen=229855869255&ajax=1&rnd=0.33698065829933327
	//请求 URL: http://www.ckd.cn/query.php?corp_code=shentong&waybill=229855869255
	
	@SuppressWarnings("unchecked")
	public ResponseMsg spider(String number){
		ResponseMsg msg = new ResponseMsg();
		msg.setCode("shentong");
		msg.setNumber(number);
		try {
			String resoponText = HttpUtils.doGet(URL+number);
			//System.out.println(resoponText);
			Document doc = Jsoup.parse(resoponText);
			Element table = doc.select("table").first();
			msg.setData(Collections.EMPTY_LIST);
			if(table ==null){
				msg.setMessage("此单号无记录，请核对快递公司名称和运单号码是否正确！");
			}else {
				Elements tr = table.select("tr");
				if(tr.size()>=2){
					Element status = tr.first().select("td").first().select("ul").first().select("li").first().select("i").first();
					String statusText = status.text();
					msg.setStatus(statusText);
					if("无记录".equals(statusText)){
						msg.setStatu(ExpressConstant.EXPRESS_NOEXIST);
						msg.setMessage("抱歉，此单号无记录。<br>如确认运单号无误，可能是快件信息尚未发出，或有延迟，请稍后再试。");
					}else{
						Elements tds = tr.get(1).select("td");
						if(tds.size()>=2){
							Elements lis = tds.get(1).select("ul").first().select("li");
							if(lis!=null){
								List<ResponseData> list = new ArrayList<ResponseData>();
								for (Element eli : lis) {
									String time =eli.select("div").first().text();
									String context = eli.select("span").first().text();
									if(!StringUtils.isEmpty(time)&&!StringUtils.isEmpty(context)){
										ResponseData data = new ResponseData();
										data.setTime(DateUtil.parse(time));
										data.setContext(context);
										list.add(data);
									}
								}
								msg.setData(list);
							}
						}
						msg.setStatus(statusText);
						if("运送中".equals(statusText)){
							msg.setStatu(ExpressConstant.EXPRESS_ONWAY);
						}
						if("已签收".equals(statusText)){
							msg.setStatu(ExpressConstant.EXPRESS_RECEIVE);
						}
						if("派件中".equals(statusText)){
							msg.setStatu(ExpressConstant.EXPRESS_SEND);
						}
/*						if("已收件".equals(statusText)){
							msg.setStatu(ExpressConstant.EXPRESS_EMBRACEPARTS);
						}
						if("".equals(statusText)){
							msg.setStatu(ExpressConstant.EXPRESS_REJECT);
						}
						if("".equals(statusText)){
							msg.setStatu(ExpressConstant.EXPRESS_RETURN);
						}
						if("".equals(statusText)){
							msg.setStatu(ExpressConstant.EXPRESS_DIFFICULT);
						}*/
					}
				}				
			}

		} catch (Exception e) {
			log.error("func[spider]-StoSpider-excetion[{}]",e);
			msg.setNumber(number);
			msg.setStatu(-2);
			msg.setStatus("接口异常");
			msg.setMessage(e.getMessage());
			msg.setData(Collections.EMPTY_LIST);
		}
		return msg;
	}
	public static void main(String[] args){
		StoSpider ytoSpider = new StoSpider();
		//229698906739
		//229855869255
		//229855869258
		ResponseMsg msg = ytoSpider.spider("229698906739");
		System.out.println(JSON.toJSONStringWithDateFormat(msg,"yyyy-MM-dd HH:mm:ss"));
	}
}
