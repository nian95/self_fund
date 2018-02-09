/**
 * Created by nian on 2018/1/27.
 */
FundUnitNetValueList  = (function ($) {

    var FUND_CODE = "基金代码";
    var FUND_NET_VALUE_DATE = "净值日日";
    var FUND_NET_VALUE = "净值";

    var fundUnitNetValueListView = SearchListView.extend({
        /**
         * Grid列
         * @returns {Array.<Object>}
         */
        getGridColumns: function () {
            return [ {
                field : "fCode",
                width : '15%',
                title : FUND_CODE
            },
                {
                    field : "unitNetValueDate",
                    width : '40%',
                    title : FUND_NET_VALUE_DATE,
                    format : "{0: yyyy-MM-dd hh:mm:ss}"
                },
                {
                    field : "unitNetValue",
                    width : '15%',
                    title : FUND_NET_VALUE
                }];
        }
    });
    var searchListView = new fundUnitNetValueListView("#surveyEventLogDiv", getEstimatingURL("/survey/loadSurveyEventLogs"), null, null, null, null);

    return {
        initialize: function(){
            searchListView.initialize();
        }
    };
})(jQuery);