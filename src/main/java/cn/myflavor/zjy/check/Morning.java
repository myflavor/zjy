package cn.myflavor.zjy.check;

import cn.myflavor.zjy.util.JSJUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.util.HashMap;

public class Morning extends BaseCheck {
    @Override
    public String getUrlCore() {
        return "/f/J30YUa/s/elVofh";
    }

    @Override
    public String getName() {
        return "晨检";
    }

    @Override
    public RequestBody getPostData() {
        String address = document.select("span[data-display-field-type=\"address-field\"]").html();
        String province = JSJUtil.addressResolution(address).get(0).get("province");
        String city = JSJUtil.addressResolution(address).get(0).get("city");
        String district = JSJUtil.addressResolution(address).get(0).get("county");
        try {
            province = select("entry[field_51][province]");
            city = select("entry[field_51][city]");
            district = select("entry[field_51][district]");
        } catch (Exception e) {
            logger.info("所在地找不到");
            tips = " 由于无法获取所在地故使用籍贯地址";
        }
        FormBody.Builder body = new FormBody.Builder();
        body.add("utf8", "✓")
                .add("_method", "patch")
                .add("authenticity_token", JSJUtil.getToken())
                .add("t", JSJUtil.getT())
                .add("entry[field_51][province]", province)
                .add("entry[field_51][city]", city)
                .add("entry[field_51][district]", district);
        HashMap<String, Integer> question = new HashMap<>();
        question.put("entry[field_38][]", 0);
        question.put("entry[field_40][]", 0);
        question.put("entry[field_125]", 1);
        question.put("entry[field_46]", 0);
        question.put("entry[field_75]", 0);
        question.put("entry[field_150]", 0);
        question.put("entry[field_109]", 1);
        question.put("entry[field_97]", 1);
        question.put("entry[field_112][]", 0);
        question.put("entry[field_113]", 2);
        question.put("entry[field_114][]", 0);
        question.put("entry[field_128]", 0);
        question.put("entry[field_130]", 0);
        question.put("entry[field_96][]", 0);
        question.put("entry[field_152]", 1);
        for (String s : question.keySet()) {
            body.add(s, input(s, question.get(s)));
        }
        body.add("entry[field_131][latitude]", "0.000000000");
        body.add("entry[field_131][longitude]", "0.000000000");
        body.add("entry[field_131][address]", "浙江省杭州市江干区");
        body.add("commit", "提交");
        return body.build();
    }
}
