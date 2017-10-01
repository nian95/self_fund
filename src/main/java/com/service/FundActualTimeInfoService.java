package com.service;

import com.bean.FundActualTimeInfoBean;

public interface FundActualTimeInfoService {

	/**
	 * 从网上查询实时基金信息
	 * @param webUrl 请求地址
	 * @param fCode 基金Code
	 * @return 基金实时信息
	 *
	 */
	FundActualTimeInfoBean queryFundActualTimeInfoFromWeb(String webUrl, String fCode);

	/**
	 * 删除基金实时信息
	 * @param fCode 基金Code
	 */
	void deleteFundActualTimeInfoFromLocalDB(String fCode);

	/**
	 * 将web上的基金实时信息保存到本地数据库
	 * @param  fundActualTimeInfo 基金实时信息
	 */
	void saveFundActualTimeInfoToLocalDB(FundActualTimeInfoBean fundActualTimeInfo);

	/**
	 * 从数据库中查询基金实时信息
	 * @param fCode 基金code
	 */
	FundActualTimeInfoBean queryFundActualTimeInfoFromDB(String fCode);

}
