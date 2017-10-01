package com.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.service.FundActualTimeInfoService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.bean.FundActualTimeInfoBean;
import com.common.DBHelper;

public class FundActualTimeInfoServiceImpl implements FundActualTimeInfoService {

	@SuppressWarnings("unused")
	final private String[] convertArray = { "fundcode", "name", "jzrq", "dwjz",
			"gsz", "gszzl", "gztime" };
	private HttpClient client;
	private HttpGet request;
	private DBHelper dBHelper;

	@Override
	public FundActualTimeInfoBean queryFundActualTimeInfoFromWeb(String webUrl,
			String fCode) {
		FundActualTimeInfoBean fundActualTimeInfo = new FundActualTimeInfoBean();
		String url = webUrl + fCode + ".js";
		client = new DefaultHttpClient();
		request = new HttpGet(url);
		try {

			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				System.out.println("status = 200");
				HttpEntity entity = response.getEntity();
				String entityString = EntityUtils.toString(entity, "UTF-8");
				System.out.println(entityString);
				Map<String, String> resultMap = this.convertStringToMap(
						entityString, convertArray);
				this.fillFundActualTimeInfo(resultMap, fundActualTimeInfo);
				return fundActualTimeInfo;

			}
		} catch (Exception e) {
			System.out.println("get�����ύʧ��:" + url);
		}
		return fundActualTimeInfo;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void saveFundActualTimeInfoToLocalDB(
			FundActualTimeInfoBean fundActualTimeInfo) {
		String sql = "insert into T_Fund_Actual_Time_Info (f_Code , f_Name , unit_Net_Value_Date , unit_Net_Value , estimates_Vaule , estimates_Vaule_Rate , estimates_Time) values (?,?,?,?,?,?,?)";
		dBHelper = new DBHelper(sql);

		try {
			dBHelper.preparedStatement.setString(1,
					fundActualTimeInfo.getfCode());
			dBHelper.preparedStatement.setString(2,
					fundActualTimeInfo.getfName());
			dBHelper.preparedStatement.setString(3,
					fundActualTimeInfo.getUnitNetValueDate());
			dBHelper.preparedStatement.setString(4,
					fundActualTimeInfo.getUnitNetValue());
			dBHelper.preparedStatement.setString(5,
					fundActualTimeInfo.getEstimatesVaule());
			dBHelper.preparedStatement.setString(6,
					fundActualTimeInfo.getEstimatesVauleRate());
			dBHelper.preparedStatement.setString(7,
					fundActualTimeInfo.getEstimatesTime());
			dBHelper.preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public FundActualTimeInfoBean queryFundActualTimeInfoFromDB(String fCode) {
		FundActualTimeInfoBean fundActualTimeInfo = new FundActualTimeInfoBean();
		String sql = "select * from T_Fund_Actual_Time_Info";
		dBHelper = new DBHelper(sql);
		try {
			ResultSet result = dBHelper.preparedStatement.executeQuery();
			while (result.next()) {
				fundActualTimeInfo.setfCode(result.getString("f_Code"));
				fundActualTimeInfo.setfName(result.getString("f_Name"));
				fundActualTimeInfo.setUnitNetValueDate(result.getString("unit_Net_Value_Date"));
				fundActualTimeInfo.setUnitNetValue(result.getString("unit_Net_Value"));
				fundActualTimeInfo.setEstimatesVaule(result.getString("estimates_Vaule"));
				fundActualTimeInfo
						.setEstimatesVauleRate(result.getString("estimates_Vaule_Rate"));
				fundActualTimeInfo.setEstimatesTime(result.getString("estimates_Time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fundActualTimeInfo;
	}

	private Map<String, String> convertStringToMap(final String responsSting,
			final String[] convertArray) {
		Map<String, String> resultMap = new HashMap<String, String>();
		int length = convertArray.length;
		int beginIndex = 0;
		int endIndex = 0;

		for (int i = 0; i < length; i++) {
			beginIndex = responsSting.indexOf(convertArray[i])
					+ convertArray[i].length() + 3;
			if (i < length - 1) {
				endIndex = responsSting.indexOf(convertArray[i + 1]) - 3;
			} else {
				endIndex = responsSting.length() - 4;
			}

			resultMap.put(convertArray[i],
					responsSting.substring(beginIndex, endIndex));
			System.out.println(i + " : "
					+ responsSting.substring(beginIndex, endIndex));
		}
		return resultMap;

	}

	private void fillFundActualTimeInfo(Map<String, String> resultMap,
			FundActualTimeInfoBean fundActualTimeInfo) {
		fundActualTimeInfo.setfCode(resultMap.get(convertArray[0]));
		fundActualTimeInfo.setfName(resultMap.get(convertArray[1]));
		fundActualTimeInfo.setUnitNetValueDate(resultMap.get(convertArray[2]));
		fundActualTimeInfo.setUnitNetValue(resultMap.get(convertArray[3]));
		fundActualTimeInfo.setEstimatesVaule(resultMap.get(convertArray[4]));
		fundActualTimeInfo
				.setEstimatesVauleRate(resultMap.get(convertArray[5]));
		fundActualTimeInfo.setEstimatesTime(resultMap.get(convertArray[6]));
	}

	@Override
	public void deleteFundActualTimeInfoFromLocalDB(String fCode) {
		String deleteSql = "";
		String tableName = "T_Fund_Actual_Time_Info";
		if (fCode.isEmpty()) {
			deleteSql = "delete from " + tableName;
		} else {
			deleteSql = "delete from " + tableName + "where f_code = " + fCode;
		}
		dBHelper = new DBHelper(deleteSql);
		try {
			dBHelper.preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
