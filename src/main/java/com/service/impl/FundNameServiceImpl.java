package com.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.service.FundNameService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.bean.FundNameBean;

import com.common.DBHelper;

public class FundNameServiceImpl implements FundNameService {

	private HttpClient client;
	private HttpGet request;
	private DBHelper dBHelper;

	@Override
	public List<FundNameBean> queryFundNameListFromWeb(String webUrl) {
		String url = webUrl;
		client = new DefaultHttpClient();
		request = new HttpGet(url);
		List<FundNameBean> fundNameList = new ArrayList<FundNameBean>();
		FundNameBean fundName = null;
		try {

			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				System.out.println("status = 200");
				HttpEntity entity = response.getEntity();
				String entityString = EntityUtils.toString(entity, "UTF-8");
				int beginIndex = entityString.indexOf("?var r = [")
						+ "?var r = [".length() + 1;
				int endIndex = entityString.length() - 2;
				String originalString = entityString.substring(beginIndex,
						endIndex);
				List<String> strList = new ArrayList<String>();
				this.fillStringToList(originalString, strList);
				System.out.println("strList.size() : " + strList.size());
				for (String str : strList) {
					String cutStr = str.replace("[", "").replace("]", "");
					String[] strArray = cutStr.split(",");
					fundName = new FundNameBean();
					this.fileFundName(strArray, fundName);
					fundNameList.add(fundName);

				}

			}
		} catch (Exception e) {
			System.out.println("get�����ύʧ��:" + url);
		}

		return fundNameList;
	}

	@Override
	public void saveFundNameListToDB(List<FundNameBean> fundNameList) {

		String sql = "insert into t_fund_name_list t (f_Code , f_name_pi , f_name , f_type , f_full_name_pi) values (?,?,?,?,?)";
		dBHelper = new DBHelper(sql);
		for (int i = 0; i < fundNameList.size(); i++) {
			try {
				dBHelper.preparedStatement.setString(1, fundNameList.get(i)
						.getfCode());
				dBHelper.preparedStatement.setString(2, fundNameList.get(i)
						.getfNamePI());
				dBHelper.preparedStatement.setString(3, fundNameList.get(i)
						.getfName());
				dBHelper.preparedStatement.setString(4, fundNameList.get(i)
						.getfType());
				dBHelper.preparedStatement.setString(5, fundNameList.get(i)
						.getfFullNamePI());
				dBHelper.preparedStatement.addBatch();
				if (i % 500 == 0) {
					dBHelper.preparedStatement.executeBatch();
				}
				dBHelper.preparedStatement.executeBatch();
				// dBHelper.preparedStatement.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public List<FundNameBean> queryFundNameListFromDB(int page, int pageSize) {
		List<FundNameBean> fundNameList = new ArrayList<FundNameBean>();
		FundNameBean fundName = null;
		String countSql = "select count(*) count from t_fund_name_list";
		DBHelper countDBHelper = new DBHelper(countSql);
		int maxPage = 0;
		int count = 0;
		try {
			ResultSet resultSetCount = countDBHelper.preparedStatement
					.executeQuery();
			while (resultSetCount.next()) {
				count = resultSetCount.getInt("count");
				if (count % pageSize > 0) {
					maxPage = count / pageSize + 1;
				} else {
					maxPage = count / pageSize;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		int beginIndex = 0;
		int endIndex = 0;
		if (page <= maxPage) {
			beginIndex = (page - 1) * pageSize;
			endIndex = page * pageSize;
		} else {
			beginIndex = 0;
			endIndex = count;
		}
		String contentSql = "select * from ("
				+ "select rownum as id ,t.* from t_fund_name_list t order by t.f_code asc "
				+ ") t2 " + "where " + "t2.id > " + beginIndex
				+ "and t2.id <= " + endIndex;
		DBHelper contentDBHelper = new DBHelper(contentSql);
		try {
			ResultSet resultSetContent = contentDBHelper.preparedStatement
					.executeQuery();
			while (resultSetContent.next()) {
				fundName = new FundNameBean();
				fundName.setfCode(resultSetContent.getString("f_code"));
				fundName.setfNamePI(resultSetContent.getString("f_name_pi"));
				fundName.setfName(resultSetContent.getString("f_name"));
				fundName.setfType(resultSetContent.getString("f_type"));
				fundName.setfFullNamePI(resultSetContent
						.getString("f_full_name_pi"));
				fundNameList.add(fundName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fundNameList;
	}

	@Override
	public FundNameBean queryFundNameFromDB(String fCode) {
		FundNameBean fundName = new FundNameBean();
		String sql = "select * from t_fund_name_list t where t.f_code = " + fCode;
		DBHelper fundNamedBHelper = new DBHelper(sql);
		try {
			ResultSet resultSet = fundNamedBHelper.preparedStatement.executeQuery();
			while (resultSet.next()) {
				fundName = new FundNameBean();
				fundName.setfCode(resultSet.getString("f_code"));
				fundName.setfNamePI(resultSet.getString("f_name_pi"));
				fundName.setfName(resultSet.getString("f_name"));
				fundName.setfType(resultSet.getString("f_type"));
				fundName.setfFullNamePI(resultSet
						.getString("f_full_name_pi"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return fundName;
	}

	private void fillStringToList(String originalString, List<String> strList) {
		String indexStr = ",[";
		while (originalString.indexOf(indexStr) > 0) {
			int start = 0;
			int end = originalString.indexOf(",[");
			String str = originalString.substring(start, end);
			strList.add(str);
			originalString = originalString.substring(end + 1);

		}
		strList.add(originalString);
	}

	private FundNameBean fileFundName(String[] fNameArray,
			FundNameBean fNameObject) {

		fNameObject.setfCode(fNameArray[0].replaceAll("\"", ""));
		fNameObject.setfNamePI(fNameArray[1].replaceAll("\"", ""));
		fNameObject.setfName(fNameArray[2].replaceAll("\"", ""));
		fNameObject.setfType(fNameArray[3].replaceAll("\"", ""));
		fNameObject.setfFullNamePI(fNameArray[4].replaceAll("\"", ""));
		return fNameObject;
	}

	private void printArray(String[] fNameArray) {
		for (int i = 0; i < fNameArray.length; i++) {
			System.out.println("fNameArray[" + i + "] : " + fNameArray[i]);
		}

	}

	private void printArrayCut(String[] fNameArray) {

		for (int i = 0; i < fNameArray.length; i++) {
			System.out.println("fNameArray[" + i + "] : "
					+ fNameArray[i].replaceAll("\"", ""));
		}

	}
}
