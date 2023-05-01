<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script type="text/javascript">
		$(document).ready(function(e){
			setActiveMenu('menu1', 'menu1-1');
			next("1", true);
		});

		var answerArr;
		var collectArr;
		var uanswerArr = ["F", "F", "F", "F", "F", "F"];

		function next(index, isSuffled) {
			var ind = "0";

			
			// 보기 셔플
			if(!$("input:checkbox[id='chkbx-option3']").is(":checked") || !isSuffled ) {
				ind = index*1 + "";
			}

			var obj = new Object();
			obj.ind = ind;
			obj.subject = "abap";

			answerArr = ["", "", "", "", "", ""];
			collectArr = ["", "", "", "", "", ""];
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "${ctx}/quiz/abap/getQuiz.do",
				contentType : 'application/json',
				data : JSON.stringify(obj),
				success : function(result) {
					$(".question").empty();
					$(".answer").empty();
					
					var html = "";
					if(result.length < 1) return;
					/*
					* 문제 번호
					*/
					$("#quiz-num").val(result[0].ind);
					var qustion_number = "";
					qustion_number += "Question " + result[0].ind + ". </br>";
					$(".num-quiz").append(qustion_number);

					/*
					* 문제
					*/ 
					$(".main-quiz").append(result[0].main_question);
					
					$(".sub-quiz").append(result[0].sub_question);

					answerArr = [result[0].answer1, result[0].answer2, result[0].answer3, result[0].answer4, result[0].answer5, result[0].answer6];
					collectArr = [result[0].collect1, result[0].collect2, result[0].collect3, result[0].collect4, result[0].collect5, result[0].collect6];

					// 보기 셔플
					if($("input:checkbox[id='chkbx-option1']").is(":checked")) {
						shuffleAns();
					}
					
					/*
					* 보기
					*/
					for(var i = 0; i < answerArr.length; i++) {
						if(answerArr[i] != null) {
							html += "<tr class='answer-row' id='answer" + (i+1) + "' index='" + (i+1) + "'>";
							html += "<td>";
							html += "<div class='chkbx'>";
							html += "<input type='checkbox' class='checkbox' id='chkbx" + (i+1) + "' name='checkbox-btn'>";
							html += "<label for='chkbx" + (i+1) + "'>" + answerArr[i] + "</label>";
							html += "</div>";
							html += "</td>";
							html += "</tr>";
						}
					}
				    					
					$(".answer").append(html);

					$(".answer tr").each(function (index, item) {

						$(item).click(function () {
							if($("input:checkbox[id='chkbx" + $(this).attr("index") + "']").is(":checked")) {
								$("input:checkbox[id='chkbx" + $(this).attr("index") + "']").prop("checked", false);
							} else {
								$("input:checkbox[id='chkbx" + $(this).attr("index") + "']").prop("checked", true);
							};
						});
						
					});

				},
				error : function(e) {
				}, 
				complete:function(){
			    }
			});
		}

		function conf() {
			uanswerArr = ["F", "F", "F", "F", "F", "F"];
			
			$(".answer tr").each(function (index, item) {
				if($("input:checkbox[id='chkbx" + (index+1) + "']").is(":checked")) {
					uanswerArr[index] = "T"
				}
			});

			for(var i = 0; i < uanswerArr.length; i++) {
				if(collectArr[i] == "T") {
					
					$("#answer" + (i+1)).css("background-color", "#E4F7BA");
				} else {
					//$("#answer" + (i+1)).css("background-color", "#FFA7A7");
				}
				/*
				if(collectArr[i] == "T") {
					$("#answer" + (i+1)).css("background-color", "#555");
				} else if (collectArr[i] == "F" && uanswerArr[i] == "T") {
					$("#answer" + (i+1)).css("background-color", "#111");
				}
				*/
			}
			
		}

		function shuffleAns() {
			for(var i = 0; i < answerArr.length; i++) {
				if(answerArr[i] == null)  {
					answerArr.splice(i, 1);
					collectArr.splice(i, 1);
					i--;
				}
			}

			var res_answerArr = new Array();
			var res_collectArr = new Array();

			for(var i = 0; i < answerArr.length; i++) {
				res_answerArr[i] = "";
				res_collectArr[i] = "";
			}

			for(var i = 0; i < answerArr.length; i++) {
				while(true) {
					var ran = Math.floor(Math.random() * answerArr.length);
					//alert("ran ====> " + ran + "\nran_answerArr[ran] ====> " + res_answerArr[ran]);
					if(res_answerArr[ran] == "") {
						res_answerArr[ran] = answerArr[i];
						res_collectArr[ran] = collectArr[i];
						break;
					}
				}
			}
			answerArr = res_answerArr;
			collectArr = res_collectArr;

			//alert("ran_answerArr.length ====> " + ran_answerArr.length + "\nran_collectArr.length ====> " + ran_collectArr.length);
			
		}
		
		
	</script>
</head>
<body>
<div class="tbl">
	<table>
		<colgroup>
			<col style="width:100%;">
		</colgroup>
		<thead>
			<tr>
				<th>
					<div><p class="question num-quiz"></p></div>
					<div><p class="question main-quiz"></p></div>
					<div><p class="question sub-quiz"></p></div>
				</th>
			</tr>
		</thead>
		<tbody class="answer">

		</tbody>
	</table>
</div>
<div class="filter-area">
	<div class="left">
		<input type="text" id="quiz-num" class="input-num" value="1" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
		<button class="btn-tb-top" onclick="next($('#quiz-num').val(), false);">이동</button>
	</div>
	<div class="right">
		<button class="btn-tb-top" onclick="conf();">확인</button>
		<button class="btn-tb-top" onclick="next($('#quiz-num').val()*1+1, true);">다음</button>
	</div>
</div>
</body>
</html>
