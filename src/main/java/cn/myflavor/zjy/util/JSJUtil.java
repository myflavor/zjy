package cn.myflavor.zjy.util;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class JSJUtil {
    //定义一个Document用于Jsoup解析
    private static Document document;

    //设置Document对象
    public static void setDocument(Document document) {
        JSJUtil.document = document;
    }

    //返回post需要的authenticity_token
    public static String getToken() {
        return document.select("meta[name=\"csrf-token\"]").attr("content");
    }

    //返回post需要的T
    public static String getT() {
        return document.select("input[name=\"t\"]").attr("value");
    }

    //获取修改页面访问地址
    public static String getUrlChange() {
        return document.select("a[data-method=\"post\"]").attr("href");
    }

    //获取结果页面访问地址
    public static String getUrlResult() {
        return document.select("form").attr("action");
    }

    public static List<Map<String, String>> addressResolution(String address) {
        String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        Matcher m = Pattern.compile(regex).matcher(address);
        String province = null, city = null, county = null, town = null, village = null;
        List<Map<String, String>> table = new ArrayList<Map<String, String>>();
        Map<String, String> row = null;
        while (m.find()) {
            row = new LinkedHashMap<String, String>();
            province = m.group("province");
            row.put("province", province == null ? "" : province.trim());
            city = m.group("city");
            row.put("city", city == null ? "" : city.trim());
            county = m.group("county");
            row.put("county", county == null ? "" : county.trim());
            town = m.group("town");
            row.put("town", town == null ? "" : town.trim());
            village = m.group("village");
            row.put("village", village == null ? "" : village.trim());
            table.add(row);
        }
        return table;
    }
}
