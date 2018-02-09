package com.demo;

import java.util.List;

import com.action.FundNameAction;
import com.common.CollectionUtils;
import com.common.FundConstant;
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
		demo demo = new demo();
		demo.insertFunUnitToTable();
	}

	/**** �ָ��� ***********************************************************************************/

	/**
	 * ����ÿ������ľ�ֵ��
	 */
	private void createFunUnitTable() {
		FundNameServiceImpl fundNameServiceImpl = new FundNameServiceImpl();
		List<FundNameBean> fundNameList = fundNameServiceImpl.queryFundNameListFromWeb(FundConstant.FUND_ALL_NAME_LIST_URL);
		System.out.println("�������� : " + fundNameList.size());
		FundUnitNetValueServiceImpl fundUnitNetValue = new FundUnitNetValueServiceImpl();
		fundUnitNetValue.createFundUnitNetValueTables(fundNameList);
	}

	/**
	 * ��ÿ������ľ�ֵ����¼��ֵ
	 */
	private void insertFunUnitToTable() {
		FundNameServiceImpl fundNameServiceImpl = new FundNameServiceImpl();
		List<FundNameBean> fundNameList = fundNameServiceImpl.queryFundNameListFromWeb(FundConstant.FUND_ALL_NAME_LIST_URL);
		System.out.println("�������� : " + fundNameList.size());
		FundUnitNetValueServiceImpl fundUnitNetValue = new FundUnitNetValueServiceImpl();
		List<FundUnitNetValueBean> fundUnitNetValueList = null;
		for (FundNameBean fund : fundNameList) {
			fundUnitNetValueList = fundUnitNetValue.queryFundUnitNetValueFromWeb(FundConstant.FUND_DETAIL_INTO_URL,fund.getfCode());
			if (!CollectionUtils.isNullOrEmpty(fundUnitNetValueList)) {
				fundUnitNetValue.saveFundUnitNetValueToDB(fundUnitNetValueList);
				System.out.println(fund.getfCode() + "-" + fund.getfName() + " : "+ " �о�ֵ��");
			} else {
				System.out.println(fund.getfCode() + "-" + fund.getfName() + " : "+ "�޾�ֵ��");
			}
		}
	}

}
