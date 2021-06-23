package com.alan.tests.base;

import com.alan.testhelper.ApiHelper;
import com.alan.utils.ApiUtils;
import com.alan.utils.JexlTools;
import com.alan.utils.mysql.MySQL;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BaseTest {

    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    private static ThreadLocal<MySQL> mySQLThreadLocal = new ThreadLocal<>();

    public ApiHelper apiHelper = ApiUtils.create(ApiHelper.class);
    public Response response;

    public String host;
    public String port;
    public String username;
    public String password;
    public String wareHouse;
    public String ownerCode;
    public SoftAssert sa = new SoftAssert();

    /**
     * 设置数据库信息
     * @param host 数据库ip
     * @param port 数据库端口
     * @param username 登录用户
     * @param password 登录密码
     */
    @Step("设置数据库信息")
    public void setDBInfo (String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public SoftAssert getSa() {
        return sa;
    }

    /**
     * 获取查表条件
     * @param body 请求入参
     * @param mapStr 入参与表映射关系， queryCondition 为查表条件
     * @return
     */
    @Step("获取查表条件")
    public String getQueryConditions(String body, String mapStr) {
        JSONObject map = JSONObject.parseObject(mapStr);
        String cols = map.getString("queryCondition");

        StringBuffer result = new StringBuffer("");
        String typeStr = "=";
        String catStr = "";
        if (cols.contains("like:")) {
            cols = cols.split("like:")[1];
            typeStr = " like ";
            catStr = "%";
        }
        if (cols.contains("&")) {
            // & 表示为多条件
            String [] colArr = cols.split("&");
            for (int i = 0; i < colArr.length; i++) {
                String col = colArr[i];
                // 从入参获取值
                Object value = getValueFromBody(body, mapStr, col);
                if (i > 0) {
                    result.append(" AND ");
                }
                result.append(col).append(typeStr);
                if (value instanceof String) {
                    result.append("\"").append(catStr).append(value).append(catStr).append("\"");
                } else {
                    result.append(value);
                }
            }
        } else {
            Object value = getValueFromBody(body, mapStr, cols);

            if (value instanceof String) {
                result.append(cols).append(typeStr).append("\"").append(catStr).append(value).append(catStr).append("\"");
            } else {
                result.append(cols).append(typeStr).append(value);
            }
        }

        return result.toString();
    }

    /**
     * 根据表的字段名通过map，从body获取入参值
     * @param body 入参body
     * @param mapStr 入参与表字段映射
     * @param col 表字段名
     * @return 入参值
     */
    public Object getValueFromBody (String body, String mapStr, String col) {
        JSONObject map = JSONObject.parseObject(mapStr);
        for (Map.Entry entry: map.entrySet()) {
            String value = entry.getValue().toString();
            String key = entry.getKey().toString();
            if (value.contains(col) & key.contains("$")) {
                return JSONPath.read(body, key);
            }
        }
        return "";
    }

    public MySQL getMysql() {
        return mySQLThreadLocal.get();
    }

    /**
     * 查表
     * @param dbName 数据库名
     * @param tableName 表明
     * @param queryConditions 查询条件
     * @return
     * @throws Exception
     */
    @Step("查询数据库")
    public ResultSet getQueryResult(String dbName, String tableName, String queryConditions) throws Exception {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        MySQL mysql = new MySQL(url, username, password);
        mySQLThreadLocal.set(mysql);

        StringBuilder queryCond = new StringBuilder("SELECT * FROM " + tableName);
        if (!"".equals(queryConditions)) {
            queryCond.append(" WHERE ").append(queryConditions);
        }

        logger.info("db=[" + dbName + "] " +queryCond);
        ResultSet result = null;

        boolean flag = false;
        for (int i = 0; i < 9; i++) {
            result = mysql.query(queryCond.toString());
            if (result.next()) {
                result.previous();
                flag = true;
                break;
            } else {
                Thread.sleep(10*1000);
            }
        }

        if (flag) {
            logger.info("get data success.");
        } else {
            logger.error("get data empty.");
        }
        sa.assertTrue(flag, "db=[" + dbName + "] " +queryCond + ", get no DB result");

        return result;
    }

    @Step("数据对比")
    public void compare(String body, String mapStr) throws Exception {
        logger.info("******************************************************");
        logger.info("map = " + mapStr);
        String pValue = getQueryConditions(body, mapStr);
        JSONObject map = JSONObject.parseObject(mapStr);
        String [] infos = map.getString("baseInfo").split("\\.");

        ResultSet sqlResult = getQueryResult(infos[0], infos[1], pValue);
        while (sqlResult.next()) {
            for (Map.Entry entry: map.entrySet()) {
                String key = entry.getKey().toString();

                if (!key.contains("$") && !key.contains(".")) {
                    continue;
                }

                String reqValue = "";
                // $ 表示从body取值
                if (key.contains("$")) {
                    reqValue = "" + JSONPath.read(body, key);
                    // = 即 default 表示默认值
                } else if (key.contains("=")) {
                    reqValue = key.split("=")[1];
                }

                String col = entry.getValue().toString();
                // & 为同一入参对应多个表字段
                if (col.contains("&")) {
                    String [] cList = col.split("&");
                    for (int i = 0; i < cList.length; i++) {
                        col = cList[i];
                        compareData(reqValue, key, sqlResult, col);
                    }
                } else {
                    compareData(reqValue, key, sqlResult, col);
                }
            }
        }
        getMysql().getDataSource().close();
        logger.info("******************************************************\n");
    }

    /**
     * 数据校验
     * @param reqValue 入参值
     * @param key 映射key
     * @param sqlResult 表数据
     * @param col 表字段
     * @throws SQLException
     */
    @Step("入参数据与落库数据比对")
    public void compareData (Object reqValue, String key, ResultSet sqlResult, String col) throws SQLException {
        logger.info(key + " : " + col + ", reqValue=" + reqValue);
        Object dbValue;
        String scriptText = "";
        Object expect = reqValue;

        // # 为特殊表达式
        if (col.contains("#")) {
            String[] colArr = col.split("#");
            col = colArr[0];
            scriptText = colArr[1];
        }

        // ext_fields_json 为扩展字段
        if (col.contains("ext_fields_json")) {
            dbValue = sqlResult.getObject("ext_fields_json");
            if ("".equals(dbValue) || dbValue == null) {
                logger.info("[Not match]: [" + key + " vs " + col + "]: [req=" + expect + " vs db=" + dbValue + "]");
                return;
            }
            String extKey = col.split("\\.")[1];

            dbValue = JSONPath.read(dbValue.toString(), "$." + extKey).toString();
        } else {
            try {
                dbValue = sqlResult.getObject(col);
            } catch (Exception e) {
                dbValue = "no " +col;
            }
        }

        if (!"".equals(scriptText) && !(null == scriptText)) {
            expect = JexlTools.evaluate(scriptText, reqValue);
        }

        boolean flag = false;
        if (dbValue instanceof Number) {
            flag = (new BigDecimal(dbValue.toString())).compareTo(new BigDecimal(expect.toString())) == 0;
        } else {
            try {
                flag = dbValue.toString().contains(expect.toString());
            } catch (Exception e) {
                flag = false;
            }
        }

        StringBuilder msg = new StringBuilder()
                .append("[Not match]: [")
                .append(key)
                .append(" vs ")
                .append(col)
                .append("]: [req=")
                .append(reqValue)
                .append(", exp=")
                .append(expect)
                .append(" vs db=")
                .append(dbValue)
                .append("]");
        sa.assertTrue(flag, msg.toString());

        if (flag) {
//            logger.info("[match]: [" + key + " vs " + col +"]: [req=" + reqValue + ", exp=" + expect + " vs db=" + dbValue + "]");
        } else {
            logger.error("[Not match]: [" + key + " vs " + col +"]: [req=" + reqValue + ", exp=" + expect + " vs db=" + dbValue + "]");
        }
    }
}