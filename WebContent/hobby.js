// AjaxでJSONを取得する
function executeAjax () {
	'use strict';

	// ?以降のパラメータを取得
	// 今回で言うとhttp://localhost:8080/wt1/hobby.html?q=0001でいう0001が取得される
	var parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent( parameter );
	parameter = parameter.split('=')[1];

	// --------------- TODO 編集ここから---------------
	var requestQuery = {
		shainId : parameter
	};
	$.ajax({
		Type : 'GET',
		url : '/wt2/api/hobby',
		dataType : 'json',
		data : requestQuery,
		success : function(pw) {
			console.log(pw);

			var hobby ='';

			for(var i = 0;i<pw.length; i++){
				var pwhobby = pw[i];

			// $('#hobbyTable').append('<tr>'+'<td>'+pwhobby.hobbyCategory+'</td>'+'</tr>');
		 	// $('#hobbyTable').append('<td>'+pwhobby.hobby+'</td>'+'</tr>');
				//$('#hobbyTable').append('<td>'+i+1+'</td>');
				$('#hobbyTable').append('<tr>'+'<td>'+(i+1)+'</td>'+'<td>'+pwhobby.hobbyCategory+'</td>'+ '<td>'+pwhobby.hobby+'</td>'+ '</tr>');

				//$('#hobbyTable').append('<tr>'+'</tr>');


			}
////			var htmlstr = '';
////			for (var i = 0; i < data.length; i++) {
////				var item = data[i];
////				htmlstr += '<div class="grid">';
////				htmlstr += '<div class="image" style="background-image: url('+ item.url + ')"></div>';
////				htmlstr += '<div class="itemName">';
////				htmlstr += '<a href="./itemDetail.html?itemCd=' + item.itemCd + '">'
////						+ item.itemName + '</a>';
////				htmlstr += '</div>'
//			}
//			$('#js-item-list').html(htmlstr);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});






	// ---------------ここまで---------------

}

$(document).ready(function () {
	'use strict';

	// 初期表示用
	executeAjax();

	// 更新ボタンにイベント設定
	$('#searchBtn').bind('click',executeAjax);

});