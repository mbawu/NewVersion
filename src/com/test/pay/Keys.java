/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.test.pay;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088801827474643";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "bdhyxgf@163.com";

	//商户私钥，自助生成
//	public static final String PRIVATE = "MIICXAIBAAKBgQC111BfPfhZ4CPiILRt5sbeNxhLDPYLMXmoAp6It6/IatQ1M4A0hp/+T5DAri+tWTZyhSxVok9j3HiC8KsTTbdQXBpmNV/pq/R9N3haxohbWeqxWcMseYt9FdQlYcKSFQhSv/czT0IUEhSklPhEDp5IM/RiLP2wOLh3IwcSfC0ldwIDAQABAoGAVPqJeej4/e9sdYm5KD8YItL2jGRDO5nkc6q9scC96lhaUX5+5I0hifRe/lyotUci1cP8owxWM6Hdw7t7B2tjReWqY6mLr7MtlLLfm7S0EWacV5iBID5F5rfd9inuPTh8JtjVtmJO28tfVJJA+PniKelOeMkMOmMKzRgnIjQloFkCQQDlb/6dtzJYHZvBkWI5SYUT0J9rgrak3cA1Dj1RCKPO0vgsgnnQvJqvcPd8LcrvjFlTmiQm55wWKNykLpF8Ni61AkEAyuSrg+eYmi7ufwkguIL77qOigMGHMlpLKSLwGVDLZ4GQqjQYAVLcm9hHDLmPyovcvhh5ysPPLq3tcEqazPYy+wJBAIg7wdHgL3KG0i9TpiEh+HTVkXOC//RQ/4oBVINDQBDU0CuNpcs90hOOXkVL443tpjSv9/tp1xKJXMnYJOa+PWkCQDZPhdX2PbQwEelxgQ/DCDs2FD7RDyacYvgtAQZVyUz4ssfM0AwyqRkSiNpQJbx7Oeis1pU/WwtMxX1EizGISdcCQDkP5bT7HrRexKAmpl+J0Z7LTvvHC5AqVyU25pp5btTcPD7qQDvaZMmUah3VSwltH+wx6dRxWIeJC3L4oDar140=";
	public static final String PRIVATE = "MIICXQIBAAKBgQDBNfLxJMvlY63ODUAl+OUJ1H9vQWrd+4Unbo5Ai9B4yoBf54LljCZxxgKmfFsiPJQ3UgE6oxeOnH1aa/6HD5nSa75ocNRBbEWahK5FUQ71zM+asK7Q3oy3MvXSVj90C1sl5kXk2U+mauk/f3J1UkhKBQAsY81uLHQYPQnb3HV11wIDAQABAoGAQ7ZchhaXPrVIXEZYjPZFjsRiRONwSIu1hrRANm7JiQuvIe1I+pm7f3QdyasbZUxFxX1/4MT3pItDg0UF1t+o0HH6xxx1At6qCfUqtSDnVxnsQiD/P10U2eyJA4zONPF6QTo7qMtkCOnfv0hvFNcStIL3fGLoG7mbrcF2pqOeUwECQQDhvfoLu8Qcf8L6hvyRf+QeM3ySVQXP2ovTw47F5lInbmWdENqq11SS+JLkwjjZUTUU00JmD5sJQnoqsmgqYKNxAkEA2xu0T49yTZP9iKy+DXEFAZZdou1zW4Lr9VJdADvdrkFAdXgPXSPUf0zcTo69VxYpS1lqdHZqtAIv+0mKaHZ5xwJBAMNXSLO0lJxwtkFiY9ZoS/cSaJ6b8j6OCGeEFt0LVFYIwudqeT5SvT282igYJWdwTTWrJg75PBiELQEbhuX84BECQAgzeENGFjwrHuE/vflY0pwlmamgg6HYO6a6B3sbwodkL9p1vPV6gtik8tYJ57JbxP3qAd8ME6BbOJqKPSD91j8CQQCEi10J/Rxpbn/FLbuHIrXFs5g0Jv4D++y8oa6UdcdJUMIqNeSfm0HMRE7m9xtI9bbedl7bSHCc9ts+DX4Ub7n5";
	
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
