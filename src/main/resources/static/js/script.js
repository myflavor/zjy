mailExp = /^([a-zA-Z\d])(\w|\-)+@[a-zA-Z\d]+\.[a-zA-Z]{2,4}$/;
$(".login").click(function (event) {
    event.preventDefault();
    if ($("#openid").val() == "" || $("#mail").val() == "" || $("#cdk").val() == "") {
        $(".nodetxt").html("亲，你有至少一行未填！");
    } else if (!mailExp.test($("#mail").val())) {
        $(".nodetxt").html("亲，邮箱格式不正确！");
    } else {
        $(".nodetxt").html("提交中，请耐心等待");
        $(".login").attr("disabled", "disabled");
        $.post("api/verify", {openid: $("#openid").val(), mail: $("#mail").val(), cdkey: $("#cdk").val()},
            function (data) {
                $(".nodetxt").html(data);
                $(".login").removeAttr("disabled");
            });
    }
});