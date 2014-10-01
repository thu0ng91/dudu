<!doctype html>
<!--[if lt IE 7]> <html class="no-js ie6 oldie" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js ie7 oldie" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js ie8 oldie" lang="en"> <![endif]-->
<!--[if gt IE 8]> <html class="no-js" lang="zh_CN"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title>404错误提示页</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <!-- CSS: implied media=all -->
    <link rel="stylesheet" href="${contextPath}/themes/${themeName}/single/css/404style.css">
    <style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
    </style>
    <script type="text/javascript" src="${contextPath}/themes/${themeName}/pc/js/lib/jquery-1.7.1.min.js"></script>
    <script type="text/javascript"> 
        $(document).ready(function () {
            window.setTimeout(function () {
                location.href = "${siteUrl}";
            }, 15000)
        });
    </script>
</head>
<body>
    <div id="error-container">
        <div id="container">
            <div id="title">
                <h1>对不起, 你访问的页面不存在!</h1>
            </div>
            <div id="content">
                <div class="left">
                    <p class="no-top">&nbsp;&nbsp;&nbsp;可能是如下原因引起了这个错误:</p>
                    <ul>
                        <li>&nbsp;&nbsp;&nbsp;URL输入错误</li>
                        <li>&nbsp;&nbsp;&nbsp;链接已失效</li>
                        <li>&nbsp;&nbsp;&nbsp;其他原因...</li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="right">                 
                    <p class="no-top">推荐您通过以下链接继续访问本站：</p>
                    <ul class="links">
                        <li><a href="/">» 网站首页</a></li>
                        <li><a href="/top/lastupdate.html">» 小说排行版</a></li>
                        <li><a href="/list/1.html">» 玄幻魔法小说</a></li>
                    </ul>
                    <ul class="links">
                        <li><a href="/list/2.html">» 都市言情小说</a></li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="clearfix"></div>
                <div align="center"><span class="STYLE1">你找不到的只是网页，他们找不到的却是家和亲人！爱心公益，需要你我... </span></div>
                <script type="text/javascript" src="http://www.qq.com/404/search_children.js?edition=small" charset="utf-8"></script>
            </div>
        </div>
    </div>
</body>

</html>