var app = new Vue({
    el: '.vue-box',
    data: {
        p: { // 查询参数
        },
        dataCount: 0,
        // 统计数据
        sta: {
            userCount: 0,
            goodsCount: 0,

        },
    },
    methods: {
        // 数值跳动
        slowMotion: function (obj, prop, endValue, time) {
            let timeNow = 0;
            let fn = function () {
                // 如果已经接近 or 时间已到，则立即结束
                var jdz = Math.abs(obj[prop] - endValue);
                if (jdz < 2 || timeNow >= time) {
                    // console.log('到点了');
                    obj[prop] = endValue;
                } else {
                    if (jdz < 100) {
                        obj[prop] += 1;
                    } else {
                        obj[prop] += parseInt((endValue - obj[prop]) / 10);		 // 平均一下
                    }
                    timeNow += 30;
                    setTimeout(fn, 30);
                }
            }
            fn();
        },
        // 设置统计数据的数值
        setStaDataValue: function (staData) {
            for (let key in staData) {
                this.slowMotion(this.sta, key, staData[key], 3000);
            }
        },
        // 刷新第一行数据
        f5StaData: function () {
            // 刷新第一行数据
            let thisDom = this;
            $.ajax({
                type: 'GET',
                url: "/admin/simpleinfo",
                success: function (result) {
                    thisDom.setStaDataValue({
                        userCount: result.usercount,
                        goodsCount: result.cdkcount,
                    });
                }, error: function (xhr, ajaxOptions, thrownError) {
                    if (xhr.status == 403) {
                        parent.location.href = "/admin/login.html";
                    }
                }
            });

        },

    },
    mounted: function () {
        // 刷新
        this.f5StaData();
    }
})