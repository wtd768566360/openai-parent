layui.use(['jquery'], function () {
    var $ = layui.jquery;
    //ajax前置拦截事件
    $.ajaxPrefilter((options) => {
        options.headers = {
            "Content-Type": "application/json"
        };
    })
})

function dateTime(dataTime) {
    var datetime = new Date(dataTime);
    var date = datetime.toLocaleDateString()
    var time = datetime.toTimeString();
    return date + " " + time.substring(0, 9);
}
