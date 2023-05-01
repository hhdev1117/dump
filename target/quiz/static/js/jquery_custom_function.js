function ableCheckButton(flag) {
	if(flag) {
		
	}
}
/* 정답 체크 */
function collectCheck(collectArr, uanswerArr) {
	var rtn = true;
	for(var i = 0; i < collectArr.length; i++) {
		if(collectArr[i] != uanswerArr[i]) {
			rtn = false;
			break;
		}
	}
	
	if(rtn) {
		$(".question-collect").html("(Incorrect) ");
		$(".question-collect").css("color", "green");
		$(".num-quiz").css("color", "green");
		
		return "T";
	} else {
		$(".question-collect").html("(Wrong) ");
		$(".question-collect").css("color", "red");
		$(".num-quiz").css("color", "red");
		return "F"
	}
}

/* 결과 DB 저장 */
function insertCollectResult(subject, ind, result) {
	var obj = new Object();
	obj.subject = subject;
	obj.ind = ind;
	obj.result = result;
	
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/quiz/com/insertCollectResult.do",
		contentType : 'application/json',
		data : JSON.stringify(obj),
		success : function(result) {

		},
		error : function(e) {
		}, 
		complete:function(){
	    }
	});
}

var datas = new Array();
var labels = new Array();
function getErrorRateForChartData(subject) {
	
	datas = new Array();
	labels = new Array();
	
	var obj = new Object();
	obj.subject = subject;
	
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/quiz/com/getErrorRateForChartData.do",
		contentType : 'application/json',
		data : JSON.stringify(obj),
		async: false,
		success : function(result) {
			for(var i = 0; i < result.length; i++) {
				
				datas.push(result[i].errorRate);
				labels.push(result[i].ind + " 번");
			}
		},
		error : function(e) {
		}, 
		complete:function(){
	    }
	});
}

/* 정답률 그래프 띄우기 */
function collectRateChart(subject) {
	getErrorRateForChartData(subject);
	
	$('#chart').empty();
	$('#chart').append('<canvas id="myChart"></canvas>');

	var data = {
			labels: labels,
		    datasets: [{
		      label: '오답률',
		      backgroundColor: 'rgba(255, 99, 132, 0.2)',
		      borderColor: 'rgb(255, 99, 132)',
		      barPercentage: 0.5,
		      borderWidth: 1,
		      maxBarThickness: 10,
		      data: datas,
		    }]
	};

	var config = {
		    type: 'bar',
		    data: data,
		    scales: {
		    	y: {
		    		beginAtZero: true
		        }
		    }
	};
	
	var myChart = new Chart(
		    document.getElementById('myChart'),
		    config
	);
	
	myChart.destroy();
	
	myChart = new Chart(
		    document.getElementById('myChart'),
		    config
	);
}

