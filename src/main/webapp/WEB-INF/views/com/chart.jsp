<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" session="false"%>
<script type="text/javascript">

    function wrapWindowByMask() {
        var maskHeight = $(document).height(); 
        var maskWidth = $(window).width();
      
        $('#mask-chart').css({
            'width' : maskWidth,
            'height' : maskHeight
        });
        $('#mask-chart').fadeTo("slow", 0.5);
    }

	function openChartPopup(subject) {
		collectRateChart(subject);

        $('#layerbox-chart').css("position", "absolute");
        $('#layerbox-chart').css("top",(($(window).height() - $('#layerbox-chart').outerHeight()) / 2) + $(window).scrollTop());
        $('#layerbox-chart').css("left",(($(window).width() - $('#layerbox-chart').outerWidth()) / 2) + $(window).scrollLeft());
        $('#layerbox-chart').show();

        wrapWindowByMask();
	}

	<%--=============================================================
	차트 팝업 클로즈
	=============================================================--%>
	function closeChartPopup() {
        $('#layerbox-chart').hide();
        $('#mask-chart').hide();
	}

</script>
<div id="mask-chart" class="mask"></div>   
<div id="layerbox-chart" class="layerpop" style="width: 950px; height: 600px;">
	 <!-- popup 엑셀다운 문서 암호화 -->
    <div class="layerpop_area" style="width: 950px; height: 600px;">
    <div>
       <div class="title">오답률 순위</div>
       <div class="layerpop_close" onclick="javascript:closeChartPopup();">&#10799;</div>
    	</div>
      <div class="popup-content">
      	<div id="chart">
			<canvas id="myChart"></canvas>
		</div>
      	<div class="pop-btn">
      		<a href="javascript:closeChartPopup();" id="layerbox_close" class="btn-left">확인</a>
      	</div>
      </div>
    </div>
</div>