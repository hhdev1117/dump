<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" session="false"%>
<%@ include file="../taglibs.jsp" %>

<html>
<head>
<!-- ie 호환성 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- ie 호환성 -->	
	<title></title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
	<link rel="stylesheet" type="text/css" href="${designPath}/css/font.css">
	<link rel="stylesheet" type="text/css" href="${designPath}/css/default.css">
	<link rel="stylesheet" type="text/css" href="${designPath}/css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="${designPath}/css/layout.css?ver=1">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.min.js"></script>
	<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.auto.min.js"></script>
	<script src="${designPath}/js/vendors/jquery-2.2.4.min.js"></script>
	<script src="${designPath}/js/vendors/jquery-1.11.1.min.js"></script>
	<script src="${designPath}/js/vendors/jquery-3.5.1.min.js"></script>
	<script src="${designPath}/js/vendors/jquery-ui.min.js"></script>
	<script src="${designPath}/js/vendors/jquery.fileDownload.js"></script>
	<script src="${designPath}/js/vendors/jquery-ui.js"></script>
	<script src="${designPath}/js/jquery-ui.min.js"></script>
	<script src="${designPath}/js/util.js"></script>
	
	<script src="${designPath}/js/chart.js"></script>
	<script src="${designPath}/js/chart.min.js"></script>
	<script src="${designPath}/js/vendors/jquery.form.js"></script>
	<script src="${designPath}/js/jquery_custom_function.js"></script>
	<script type="text/javascript">
		<%-- 
			README
			페이지가 메뉴를 통해 넘어오는경우가 있고, 
			주요지표 에서 값을 가지고 넘어오는경우가 있다.
			후자의 경우때문에 param변수를 생성했고
			모든 메뉴의 검색버튼 이벤트를 btnSearch()로 통일시켜줘야 한다.
		--%>
		
		$(document).ready(function(e){
			getContentTab("/abap/quiz1.do");
		});

		function getContentTab(page){
			var url = "${ctx}" + page;
			var targetDiv = ".container";

			$.post(url, function(result){
				$(targetDiv).empty();
				
				$(targetDiv).append(result).trigger("create");
			});
		}
		
		<%--=============================================================
		메뉴 활성화 CSS 변경
		=============================================================--%>
		function setActiveMenu(menuid, submenuid) {
			<%-- 메뉴 활성화 끄기 --%>
			$(".menu").attr("class", "menu");

			<%-- 활성화 메뉴 읽어서 활성화 --%>
			if(menuid != "") {
				$("#" + menuid).attr("class", "menu active");
			}

			<%-- 서브메뉴 활성화 끄기 --%>
			$(".subtitlemenu").attr("class", "subtitlemenu");

			<%-- 활성화 서브 메뉴 읽어서 활성화 --%>
			if(submenuid != "") {
				$("#" + submenuid).attr("class", "subtitlemenu on");
			}
		}

		function clickDetail(menuId, url) {
			if($("#" + menuId).attr("value") == "T") {
				$("#" + menuId).attr("value", "F");
				$("#" + menuId).attr("class", "menu");
				
				$('#' + menuId + '-sub').slideUp(300);
			} else {
				$('.menu').attr("value", "F");
				$("#" + menuId).attr("value", "T");
				$('#' + menuId + '-sub').attr("value", "T");

				$('.submenu').slideUp(300);
				
				$('#' + menuId + '-sub').slideDown(300);
			}

			/*
			if(menuId == "menu1"){
				if($("#menu1").attr("value") == "T") {
					$("#menu1").attr("value", "F");
					$("#menu1").attr("class", "menu");
					
					$('#menu1-sub').slideUp(300);
				} else {
					$("#menu1").attr("value", "T");
					$("#menu1-sub").attr("value", "T");
					
					$('#menu1-sub').slideDown(300);
				}
				
			}
			*/
		}

		function disableQuizNumber() {
			if($("input:checkbox[id='chkbx-option2']").is(":checked")) {
				$(".left").css("display", "none");
				$(".num-quiz").css("display", "none");
			} else {
				$(".left").css("display", "");
				$(".num-quiz").css("display", "");
			}
		}
	</script>
</head>
<%@ include file="../com/chart.jsp"%>
<body>
	<div class="wrap">
		<!-- header -->
		<div class="header">
			<div class="top-left">
				<a href="${ctx}/main.do"><h1 class="top-h1">Test</h1></a>
			</div>
			<div class="dropdown">
			    <button class="dropbtn">
			    	<span id="spanBcgName">Option </span>
			    	<i class="fa fa-caret-down"></i>
			    </button>
				<div class="dropdown-content dropdown-option">
					<div class="chkbx">
						<input type="checkbox" id="chkbx-option1" name="checkbox-btn">
						<label for="chkbx-option1">보기 랜덤</label>
					</div>
					<div class="chkbx">
						<input type="checkbox" id="chkbx-option2" name="checkbox-btn" onchange="disableQuizNumber();">
						<label for="chkbx-option2">문제 번호 가리기</label>
					</div>
					<div class="chkbx">
						<input type="checkbox" id="chkbx-option3" name="checkbox-btn">
						<label for="chkbx-option3">문제 랜덤</label>
					</div>
				</div>
			</div>
		</div>
		<!-- // header -->
		<!-- contents -->
		<div class="content-bg">
			<!-- left-menu -->
			<div class="left-bg">
				<ul class="main-menu">
					<li>
						<a href="javascript:void(0);" id="menu1" class="menu" onclick="clickDetail('menu1','/abap/quiz1.do'); setActiveMenu('menu1', 'menu1-1')" value="T">
							<!-- <img src="${webPath}/images/menu_1.png" alt="">  -->
							<div class="menu-tt">ABAP</div>
						</a>
						<div class="submenu" id="menu1-sub" style="">
							<div id="menu1-sub" style="display:inline;" value="T">
								<ul id="main-menu-list">
									<li>
										<a herf="javascript:void(0);" class="subtitlemenu on" id="menu1-1" onclick="getContentTab('/abap/quiz1.do');" style="cursor:hand;">DUMP1</a>
									</li>
									<li>
										<a herf="javascript:void(0);" class="subtitlemenu on" id="menu1-2" onclick="getContentTab('/abap/quiz2.do');" style="cursor:hand;">DUMP2</a>
									</li>
									 
								</ul>
							</div>
						</div>
					</li>
					
					<li>
						<a href="javascript:void(0);" id="menu2" class="menu" onclick="clickDetail('menu2','/abap/sqld30.do'); setActiveMenu('menu2', 'menu2-1')" value="F">
							<!-- <img src="${webPath}/images/menu_1.png" alt="">  -->
							<div class="menu-tt">SQLD</div>
						</a>
						<div class="submenu" id="menu2-sub" style="display:none">
							<div id="menu2-sub" style="display:inline;" value="T">
								<ul id="main-menu-list">
									<li>
										<a herf="javascript:void(0);" class="subtitlemenu on" id="menu2-1" onclick="getContentTab('/sqld/sqld30.do');" style="cursor:hand;">30회 기출문제</a>
									</li>
									<!-- 
									<li>
										<a herf="javascript:void(0);" class="subtitlemenu on" id="menu1-2" onclick="getContentTab('/abap/quiz1.do');" style="cursor:hand;">DUMP2</a>
									</li>
									 -->
								</ul>
							</div>
						</div>
					</li>
					
				</ul>
			</div>
			<!-- // left-menu -->
			<!-- container -->
			<div class="container" id="container">
			
			</div>
		</div>
	</div>
</body>