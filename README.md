# openportal


http://portal.openportal.com.cn/wxpc.jsp?appId=wx3e336d4576886268&extend=222.240.217.107:127.0.0.1&timestamp=1516008797585&sign=1ae499e38d76c93a572204bb90d16ce0&shop_id=7452864&authUrl=http://portal.openportal.com.cn:80/weixinPCAuth.action&mac=&ssid=WXLeeSon5G&bssid=

http://localhost:8080/openportal/index.jsp


修改portal_bas表里的isNtf字段，可以重设已登陆状态！
同时com.leeson.portal.core.model.OnlineMap也会记录用户登陆状态。





Navigated to http://localhost:8080/OpenPortal/index.jsp
17:59:04.494 index.jsp:102 0 1 3 4 5 6 7
17:59:11.427 VM2186 auth.js:335 weixinLogin
17:59:12.204 VM2186 auth.js:406 /OpenPortal/wxpc.jsp?appId=wx3e336d4576886268&extend=127.0.0.1:127.0.0.1&timestamp=1516183151926&sign=5288ae9631498debe8be02da3ef52a1c&shop_id=7452864&authUrl=http://localhost:8080/OpenPortal/weixinPCAuth.action&mac=&ssid=WXLeeSon5G&bssid=
17:59:12.288 Navigated to http://localhost:8080/OpenPortal/wxpc.jsp?appId=wx3e336d4576886268&extend=127.0.0.1:127.0.0.1&timestamp=1516183151926&sign=5288ae9631498debe8be02da3ef52a1c&shop_id=7452864&authUrl=http://localhost:8080/OpenPortal/weixinPCAuth.action&mac=&ssid=WXLeeSon5G&bssid=
扫了二维码后，Network中不停的刷

Request URL:https://wifi.weixin.qq.com/cgi-bin/pollpcresult?ticket=65491651xyooou08sx&callback=callback12373
Request Method:GET
Status Code:200 OK
Remote Address:101.226.90.179:443
Referrer Policy:no-referrer-when-downgrade
Response Headers
view source
Cache-Control:no-cache, must-revalidate
Connection:keep-alive
Content-Length:103
Content-Type:application/javascript; charset=utf8
Date:Wed, 17 Jan 2018 10:02:09 GMT
Request Headers
view source
Accept:*/*
Accept-Encoding:gzip, deflate, br
Accept-Language:zh-CN,zh;q=0.8
Connection:keep-alive
Cookie:pgv_pvi=4703671296; RK=jDUamdjOHe; ptcz=b16bd3986c2663753f949ed48bfe5a51ce74b39b377df2c8a60440c64854ff02; pt2gguin=o0306184384
Host:wifi.weixin.qq.com
Referer:http://localhost:8080/OpenPortal/wxpc.jsp?appId=wx3e336d4576886268&extend=127.0.0.1:127.0.0.1&timestamp=1516183151926&sign=5288ae9631498debe8be02da3ef52a1c&shop_id=7452864&authUrl=http://localhost:8080/OpenPortal/weixinPCAuth.action&mac=&ssid=WXLeeSon5G&bssid=
User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.31 Safari/537.36


-----------------------------------
index.jsp:102 0 1 3 4 5 6 7
18:21:50.758 VM29 auth.js:335 weixinLogin
18:21:51.985 VM29 auth.js:406 /OpenPortal/wxpc.jsp?appId=wx3e336d4576886268&extend=127.0.0.1:127.0.0.1&timestamp=1516184511637&sign=d0b0798a89bc09ef9d3563a947a7c697&shop_id=7452864&authUrl=http://localhost:8080/OpenPortal/weixinPCAuth.action&mac=&ssid=WXLeeSon5G&bssid=
18:21:52.202 Navigated to http://localhost:8080/OpenPortal/wxpc.jsp?appId=wx3e336d4576886268&extend=127.0.0.1:127.0.0.1&timestamp=1516184511637&sign=d0b0798a89bc09ef9d3563a947a7c697&shop_id=7452864&authUrl=http://localhost:8080/OpenPortal/weixinPCAuth.action&mac=&ssid=WXLeeSon5G&bssid=
18:21:52.218 pcauth.js:853 genQrCode:http://mp.weixin.qq.com/mp/wifi?q=pc&appid=wx3e336d4576886268&shopid=7452864&ticket=65491651alq3h6k1mp6
18:21:52.241 pcauth.js:795 makeCode:106


-------portal相关的连接

https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444894086




openporatl登陆页面
http://120.79.45.9/portaluser/login.action
