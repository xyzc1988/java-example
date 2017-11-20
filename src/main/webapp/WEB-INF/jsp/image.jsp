<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="/common/include.jsp" %>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                url: "${ctx}/image/getBase64Image",
                type: "GET"
            }).then(function (data) {
                $("#image1").attr("src", data);

            });

            loadImage("${ctx}/images/10.png",function (img) {
                $("#preloadImg").attr("src", img.src);
            });

        });

        /**
         * 可以避免ie6内存泄露和onload不触发
         * @param url
         * @param callback
         */
        function loadImage(url, callback) {
            var img = new Image(); //创建一个Image对象，实现图片的预下载
            img.onload = function(){
                img.onload = null;
                callback(img);
            };
            img.src = url;
        }

        function getPreloadImgAttr(url,callback){
            var oImg = new Image(); //创建一个Image对象,实现图片的预加载
            if(oImg.complete){
                //如果图片已经存在于浏览器缓存,直接调用回调函数
                callback.call(oImg);
                return; //直接返回,不再处理onload事件
            }
            oImg.onload = function(){
                //图片下载完毕时异步调用callback函数
                oImg.onload = null;
                callback.call(oImg);
            };
            oImg.src = url;　　    // 看下一节,其实应当先进行onload的绑定,再赋值给src
        }
        getPreloadImgAttr('image/example.jpg',function(){
            console.log(this.width, this.height);
        });


    </script>
</head>
<body>
<div>
    <img id="image2" src="${ctx}/image/getImageBody" style="width: 100px;height: 100px;"/>
    <img id="image" src="${ctx}/image/getImage" style="width: 50px;height: 50px;"/>
    <img id="image1" style="width: 50px;height: 50px;"/>

    <img id="preloadImg" style="width: 50px;height: 50px;"/>
</div>


</body>
</html>
