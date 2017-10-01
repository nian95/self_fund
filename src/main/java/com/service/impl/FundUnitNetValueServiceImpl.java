package com.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.service.FundUnitNetValueService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.bean.FundNameBean;
import com.bean.FundUnitNetValueBean;
import com.common.DBHelper;

public class FundUnitNetValueServiceImpl implements FundUnitNetValueService {

	private DBHelper dBHelper;
	private HttpClient client;
	private HttpGet request;
	
	
	@Override
	public void dropFundUnitNetValueTable(String fCode) {
		 String tableName = "t_fund_net_value_" + fCode;
		 String dropSql = "drop table " + tableName ;	
		 DBHelper dropDBHelper = new DBHelper(dropSql);
		 try {
				 dropDBHelper.preparedStatement.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public void createFundUnitNetValueTable(String fCode) {
		
		String tableName = "t_fund_net_value_" + fCode;
		String primaryKey = "net_value_key_" + fCode;
		String createSql =  "create table " + tableName + "("
						+ "f_Code varchar2(20) not null,"
						+ "unit_Net_Value_Date varchar2(20),"
						+ "unit_Net_Value varchar2(20),"
						+ " CONSTRAINTS " + primaryKey +  " PRIMARY KEY (f_Code,unit_Net_Value_Date)"
					  + ")";
		
		DBHelper createDBHelper = new DBHelper(createSql);
		try {
			createDBHelper.preparedStatement.executeQuery();
			createDBHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createFundUnitNetValueTables(List<FundNameBean> fundNameList) {
		int i = 0;
		dBHelper = new DBHelper("select * from dual");
		for (FundNameBean fundName : fundNameList) {
			i++;
			String fCode = fundName.getfCode();
			String primaryKey = "net_value_key_" + fCode;
			String tableName = "t_fund_net_value_" + fCode;
			String createSql =  "create table " + tableName + "("
					+ "f_Code varchar2(20) not null,"
					+ "unit_Net_Value_Date varchar2(20),"
					+ "unit_Net_Value varchar2(20),"
					+ " CONSTRAINTS " + primaryKey +  " PRIMARY KEY (f_Code,unit_Net_Value_Date)"
				  + ")";
			try {
				dBHelper.statement.addBatch(createSql);
				if (i % 500 == 0 || i == fundNameList.size()) {
					dBHelper.statement.executeBatch();
					dBHelper.statement.clearBatch();
				} 
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	@Override
	public List<FundUnitNetValueBean> queryFundUnitNetValueFromWeb(
			String webUrl, String fCode) {
		String url = webUrl + fCode + ".js";
		client = new DefaultHttpClient();
		request = new HttpGet(url);
		List<FundUnitNetValueBean> fundUnitNetValueList = new ArrayList<FundUnitNetValueBean>();
		FundUnitNetValueBean fundUnitNetValue = null;
		try {

			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				
				System.out.println("status = 200");
				HttpEntity entity = response.getEntity();
				String entityString = EntityUtils.toString(entity, "UTF-8");
				int beginIndex = entityString.indexOf("var Data_netWorthTrend =") + "var Data_netWorthTrend =".length() + 2;
				int endIndex = entityString.indexOf("/*�ۼƾ�ֵ����*/") - 4;
				String cutString = entityString.substring(beginIndex, endIndex);
				List<String> strList = new ArrayList<String>();
				this.fillStringToList(cutString , strList);
//				System.out.println("entityString : " + cutString);
				for (int i = 0; i < strList.size(); i++) {
//					System.out.println(i + " : " + strList.get(i));
//					this.printString(strList.get(i));
					fundUnitNetValue = new FundUnitNetValueBean();
					fundUnitNetValue.setfCode(fCode);
					this.fillStringToObj(strList.get(i), fundUnitNetValue);
					fundUnitNetValueList.add(fundUnitNetValue);

				}
				
			}
		} catch (Exception e) {
			System.out.println("get�����ύʧ��:" + url);
		}
		return fundUnitNetValueList;
	}

	@Override
	public void saveFundUnitNetValueToDB(
			List<FundUnitNetValueBean> fundUnitNetValueList) {
		
		if (fundUnitNetValueList.isEmpty()) {
			return;
		}
		String fCode = fundUnitNetValueList.get(0).getfCode();
		String tableName = "t_fund_net_value_" + fCode;
		String insertSql = "insert into " + tableName + "( f_Code , unit_Net_Value_Date , unit_Net_Value) values (?,?,?) ";
		DBHelper insertDBHelper = new DBHelper(insertSql);
		try {
			for (int i = 0; i < fundUnitNetValueList.size(); i++) {
				insertDBHelper.preparedStatement.setString(1, fundUnitNetValueList.get(i).getfCode());
				insertDBHelper.preparedStatement.setString(2, fundUnitNetValueList.get(i).getUnitNetValueDate());
				insertDBHelper.preparedStatement.setString(3, fundUnitNetValueList.get(i).getUnitNetValue());
				insertDBHelper.preparedStatement.addBatch();
				if ((i % 50 == 0) ||  (i == fundUnitNetValueList.size() - 1 )) {
					insertDBHelper.preparedStatement.executeBatch();
					insertDBHelper.preparedStatement.clearBatch();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<FundUnitNetValueBean> queryFundUnitNetValueFromDB(
			String webUrl, String fCode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void fillStringToList(String originalString, List<String> strList) {
		String indexStr = ",{";
		while (originalString.indexOf(indexStr) > 0) {
			int start = 0;
			int end = originalString.indexOf(indexStr);
			String str = originalString.substring(start, end);
			strList.add(str);
			originalString = originalString.substring(end + 1);

		}
		strList.add(originalString);
	}
	
	private void fillStringToObj(String cutString , FundUnitNetValueBean fundUnitNetValue) {
		String[] splitArray = cutString.split(",");
		int xBeginIndex = splitArray[0].indexOf("{\"x\":") + "{\"x\":".length();
		int yBeginIndex = splitArray[1].indexOf("\"y\":") + "\"y\":".length();
//		System.out.println("array[x] : " + splitArray[0].substring(xBeginIndex));
//		System.out.println("array[y] : " + splitArray[1].substring(yBeginIndex));
		fundUnitNetValue.setUnitNetValueDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(splitArray[0].substring(xBeginIndex)))));
		fundUnitNetValue.setUnitNetValue(splitArray[1].substring(yBeginIndex));
		
	}
	private void printString(String cutString) {
		String[] splitArray = cutString.split(",");
		System.out.println("--------------------------------------");
		for (int i = 0; i < splitArray.length; i++) {
			
			System.out.println(splitArray[i]);
		}
		
	}

	

}
