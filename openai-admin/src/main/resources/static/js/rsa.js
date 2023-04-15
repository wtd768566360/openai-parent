let $ = "";
layui.use(['jquery'], function () {
    $ = layui.jquery;
})


let getPublicKey = function () {
    // 获取公钥
    var publicKey = sessionStorage.getItem("public_key");
    if (!publicKey) {
        //获取公钥
        $.ajax({
            url: '/system/public/key',
            type: "get",
            async: false,
            success: function (data) {
                publicKey = data.data;
                sessionStorage.setItem('public_key', data.data);
            }
        });
    }
    // 加密
    return publicKey;
}

let RSAEncrypt = function (data) {
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(getPublicKey());
    return encrypt.encrypt(data);
}