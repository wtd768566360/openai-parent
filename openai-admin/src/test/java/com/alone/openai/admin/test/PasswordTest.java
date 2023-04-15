package com.alone.openai.admin.test;

import com.alone.openai.common.util.RsaUtil;

import java.security.InvalidKeyException;

public class PasswordTest {
    public static void main(String[] args) throws InvalidKeyException {
        http();
//        database();
//        dataBaseDecrypt();
    }


    public static void http() {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIZ9pzzcCwDOzURFazaWRKmvUb8Oj1Pbwwlu3tsHD" +
                "Ohj0qmyx+NGU86CR+urB4+bsUHFgBLXy14kkArgczGv61A2mKome4cYtUpAZztHA/UN80qsCSP7pc17MUx9rHceLN" +
                "82soye3bnv3TXeVaVI4tGpcYVtaDIGYLd/6hMGdnuQIDAQAB";

        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIhn2nPNwLAM7NREVrNpZEqa9Rvw6P" +
                "U9vDCW7e2wcM6GPSqbLH40ZTzoJH66sHj5uxQcWAEtfLXiSQCuBzMa/rUDaYqiZ7hxi1SkBnO0cD9Q3zSqwJI/ulz" +
                "XsxTH2sdx4s3zayjJ7due/dNd5VpUji0alxhW1oMgZgt3/qEwZ2e5AgMBAAECgYAr1/2+ZS7/J1hkLgcUKdcDp+UK" +
                "8xdd5Fb94zVcxe6sYzd7316gviSgPBmm5kjcyaBZKXccAcvpBm/imQ1VXuLpQaIU6/g8lmuZxzweaUNh/W1om5E4n" +
                "GBI+RIQS8arbFvL510P549U2juOQFFbhSOKdgDKU395J2HkAmV4oEs2EQJBAL+NjVxk8qUkdjw93VnBZBP6MTWjh5" +
                "bvyowQIuIkeMmWBk2WEWloXebDjFwvzHDzHEqCLG4Sh27OUJzuOPMzALUCQQC2THgv15MZ005rmc2Ys5rnJrxZ9DG" +
                "XADKizvuAAVlHV3u9M+eYub5OI+89gYq/Y7O9VsYcP1z9xCsMJMZtNeF1AkAXbGnS2I1LYHAM4cvFg/6G6ttFod+c" +
                "t2nfQsHhQXB7xhqzNhye9SI2a8coJrgrP68/9TcVprJk/FmjNwZDxwLZAkEAgi1DGe7X9XYmJsIyxZtjvzKzB41QK" +
                "CMVBZvGjpbn+QDotKgu9qmU0tnoSkSKHreEr20alStOLUx67PYJ2AzEzQJBAL6hFJQ8xDoYhsFgIeoN/jQxeO7rfh" +
                "ACK5KdglsN0/W1dO1m0oQM50LtsCtay7ZkQSiw/Y0Ox54DT0cacvbnE2A=";
        String aaa = RsaUtil.encrypt(publicKey, "123456");
        System.out.println(aaa);

        String aaa1 = RsaUtil.decrypt(privateKey, aaa);
        System.out.println(aaa1);
    }

    public static void dataBaseDecrypt() {
        String a = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKf0BMmfGuVWVIK7dMnl1Kq7y6yQuu iSObGlJzvDjMmk19kgO7uhC5utgMqpkojiUxdrL0FRBFjZMlfsU9S1yxLTqFIgBn3LpcHpcBiFbhqFr1Bt5ZVYB/D YMdD+viAsU50G9dgeOVm0FnrO/HV8PBdHACe6phlcgMlI4RJO8wDfAgMBAAECgYAdeqAof2uqG0gEFkD+ev+j5Ahw xop37cqAmrtTKxSYA/3610SEv1SZQ4/agn781bS95ItLzJuVuLG0av+f0zTMVVwhF24vshlfWXDLAsxru0NhL/ogz 4cqgBPlElOAXz6DE93DMqcaorkVYsRzhIycRCqd9KO0puv8I8UzECM0qQJBANInfujpIhZL2gYIgaUUavp+WVtC5H qlDWeHo9IuOKqXyCzJY2ZUL3hlfyNdFP9EiVhV6G0UZAap85987xBTjAsCQQDMl7cpVSYcoqt+AeVbWC7OIcyljnG I1v4S8JxENN2WME6rBMvFWk8ofdD3XDRsUT8WakiT4WWxVn0Awcpqyw79AkAdupaabjhzPQG3rsZgSvYjP3J2lYzq yKSPy8Fh/0Xm5BfeNhMIq8oXVwxYPVe2EMT5KRX7MFJCGxKqsirDfX4nAkBPWkztWBxXIPTZA808t6A3zT49vFjm1 VRobjB99+82EHiCtnJ+kj9/mgUR3A5tMLoCbKjHyrWRqdUEFSX4c9fVAkBCVf1G8fE/FglNzT8tpxO7bd5kQo+WZN YOyz7eChwnTKu3lLSrUgYaSrmiDYkudNvN/hO1V9BmNmO9mzvqtGN0";
        String b = "Vzxt8+KG1J5BGkoQT/cgL+kptlt6WL9Vz5dwe6x8UqkcJgZ5cNqIENB2VU4GzCTRL18H1AQ8PliqCzVyaeK9lNMsrAoQPSRbTq7x15BPbNI8hUPx3EmlPo1WjAsx7IP4zjEysCk+qhmMG01Hsf4ynOkrTBN/lIiE3gFpEBsJR+o=";
        String c = "Vzxt8+KG1J5BGkoQT/cgL+kptlt6WL9Vz5dwe6x8UqkcJgZ5cNqIENB2VU4GzCTRL18H1AQ8PliqCzVyaeK9lNMsrAoQPSRbTq7x15BPbNI8hUPx3EmlPo1WjAsx7IP4";
        System.out.println(b.length());
        System.out.println(c.length());
        String aaa1 = RsaUtil.decrypt(a, b);
        System.out.println(aaa1);
    }

    public static void database() {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCn9ATJnxrlVlSCu3TJ5dSqu8uskLrokjmxpSc7w4z" +
                "JpNfZIDu7oQubrYDKqZKI4lMXay9BUQRY2TJX7FPUtcsS06hSIAZ9y6XB6XAYhW4aha9QbeWVWAfw2DHQ/r4gLFOd" +
                "BvXYHjlZtBZ6zvx1fDwXRwAnuqYZXIDJSOESTvMA3wIDAQAB";

//        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKf0BMmfGuVWVIK7dMnl1Kq7y6yQuu" +
//                "iSObGlJzvDjMmk19kgO7uhC5utgMqpkojiUxdrL0FRBFjZMlfsU9S1yxLTqFIgBn3LpcHpcBiFbhqFr1Bt5ZVYB/D" +
//                "YMdD+viAsU50G9dgeOVm0FnrO/HV8PBdHACe6phlcgMlI4RJO8wDfAgMBAAECgYAdeqAof2uqG0gEFkD+ev+j5Ahw" +
//                "xop37cqAmrtTKxSYA/3610SEv1SZQ4/agn781bS95ItLzJuVuLG0av+f0zTMVVwhF24vshlfWXDLAsxru0NhL/ogz" +
//                "4cqgBPlElOAXz6DE93DMqcaorkVYsRzhIycRCqd9KO0puv8I8UzECM0qQJBANInfujpIhZL2gYIgaUUavp+WVtC5H" +
//                "qlDWeHo9IuOKqXyCzJY2ZUL3hlfyNdFP9EiVhV6G0UZAap85987xBTjAsCQQDMl7cpVSYcoqt+AeVbWC7OIcyljnG" +
//                "I1v4S8JxENN2WME6rBMvFWk8ofdD3XDRsUT8WakiT4WWxVn0Awcpqyw79AkAdupaabjhzPQG3rsZgSvYjP3J2lYzq" +
//                "yKSPy8Fh/0Xm5BfeNhMIq8oXVwxYPVe2EMT5KRX7MFJCGxKqsirDfX4nAkBPWkztWBxXIPTZA808t6A3zT49vFjm1" +
//                "VRobjB99+82EHiCtnJ+kj9/mgUR3A5tMLoCbKjHyrWRqdUEFSX4c9fVAkBCVf1G8fE/FglNzT8tpxO7bd5kQo+WZN" +
//                "YOyz7eChwnTKu3lLSrUgYaSrmiDYkudNvN/hO1V9BmNmO9mzvqtGN0";

        String privateKey1 = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKf0BMmfGuVWVIK7dMnl1Kq7y6yQuu" +
                "iSObGlJzvDjMmk19kgO7uhC5utgMqpkojiUxdrL0FRBFjZMlfsU9S1yxLTqFIgBn3LpcHpcBiFbhqFr1Bt5ZVYB/D" +
                "YMdD+viAsU50G9dgeOVm0FnrO/HV8PBdHACe6phlcgMlI4RJO8wDfAgMBAAECgYAdeqAof2uqG0gEFkD+ev+j5Ahw" +
                "xop37cqAmrtTKxSYA/3610SEv1SZQ4/agn781bS95ItLzJuVuLG0av+f0zTMVVwhF24vshlfWXDLAsxru0NhL/ogz" +
                "4cqgBPlElOAXz6DE93DMqcaorkVYsRzhIycRCqd9KO0puv8I8UzECM0qQJBANInfujpIhZL2gYIgaUUavp+WVtC5H" +
                "qlDWeHo9IuOKqXyCzJY2ZUL3hlfyNdFP9EiVhV6G0UZAap85987xBTjAsCQQDMl7cpVSYcoqt+AeVbWC7OIcyljnG" +
                "I1v4S8JxENN2WME6rBMvFWk8ofdD3XDRsUT8WakiT4WWxVn0Awcpqyw79AkAdupaabjhzPQG3rsZgSvYjP3J2lYzq" +
                "yKSPy8Fh/0Xm5BfeNhMIq8oXVwxYPVe2EMT5KRX7MFJCGxKqsirDfX4nAkBPWkztWBxXIPTZA808t6A3zT49vFjm1" +
                "VRobjB99+82EHiCtnJ+kj9/mgUR3A5tMLoCbKjHyrWRqdUEFSX4c9fVAkBCVf1G8fE/FglNzT8tpxO7bd5kQo+WZN" +
                "YOyz7eChwnTKu3lLSrUgYaSrmiDYkudNvN/hO1V9BmNmO9mzvqtGN0";

        String aaa = RsaUtil.encrypt(publicKey, "123456");
        System.out.println(aaa);
//        String b = "prun7FQyotMQl5uxD9NyY8pN8vBeokthjNLcJhwXAX1vfnltEL7FQnc5VRmDdBVe+kRUdmi7/105kCXHsBxlKa87zHUBT94Qo1/bH4R2XJYT6pA0/mDES7hkn2ZiZCw+";

        String aaa1 = RsaUtil.decrypt(privateKey1, aaa );
        System.out.println(aaa1);
    }
}
