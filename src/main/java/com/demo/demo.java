package com.demo;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.bean.FundNameBean;
import com.bean.FundUnitNetValueBean;
import com.service.impl.FundNameServiceImpl;
import com.service.impl.FundUnitNetValueServiceImpl;

public class demo {

	public static void main(String[] args) {
//		
		FundNameServiceImpl fundNameObj = new FundNameServiceImpl();
//		FundUnitNetValueServiceImpl netValueObj = new FundUnitNetValueServiceImpl();
		String url = "http://fund.eastmoney.com/js/fundcode_search.js";
		List<FundNameBean> fundNameList = fundNameObj.queryFundNameListFromWeb(url);
//		netValueObj.createFundUnitNetValueTables(fundNameList);
		
		String webUrl = "http://fund.eastmoney.com/pingzhongdata/";
		String fCode = "";
		FundUnitNetValueServiceImpl netValueObj = new FundUnitNetValueServiceImpl();
		List<FundUnitNetValueBean> fundUnitNetValueList = null;
		for (FundNameBean fundName : fundNameList) {
			fCode = fundName.getfCode();
			System.out.println("fCode : " + fCode);
//			if (Long.valueOf(fCode) > Long.valueOf("003708")) {
//			fundUnitNetValueList = netValueObj.queryFundUnitNetValueFromWeb(webUrl, fCode);
//			netValueObj.saveFundUnitNetValueToDB(fundUnitNetValueList);
//			}
		}		
		
//		for (FundUnitNetValueBean fundUnitNetValue : fundUnitNetValueList) {
//			System.out.println("----------------------------------");
//			System.out.println("����Code : " + fundUnitNetValue.getfCode());
//			System.out.println("����λ��ֵ : " + fundUnitNetValue.getUnitNetValue());
//			System.out.println("����λ��ֵ���� : " + fundUnitNetValue.getUnitNetValueDate());
//		}
		
		System.out.println("123");
	}


}
