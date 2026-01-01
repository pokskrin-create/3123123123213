/* futuresolution  레이어팝업 처리(일반) popModalMobileMain Prompt 사용
 * @param schObj
 * @param url
 * @returns
 */
function fnMainPromptMobile(schObj, url){
	fnInitPromptGlobal();

	$("#popModalMobileMain").fadeIn(100);
	$(".pop_bg1").fadeIn(100);

	//값전달
	G_MODAL_ID = "popModalMobileMain";
	//기존 prompt내용 제거
	$("#popModalMobileMain > div.pop_cont").html("");

	var v_params = schObj.attr('data-params');
	v_params = eval("("+v_params+")");

	var v_modalBody = $("#popModalMobileMain > div.pop_cont");

	$("#popModalMobileMain > div.pop_cont").load(url, v_params, function(data){
		if(!isEmpty(schObj.attr('data-title'))){
			$("#popModalMobileMain .layer_ttl").text(schObj.attr('data-title'));
		}
	});
	fnSetPromptGlobal(schObj); //값 전달용 오브젝트id 저장
	
}

/* futuresolution  레이어팝업 처리(서브) popModalMobileSub Prompt 사용
 * @param schObj
 * @param url
 * @returns
 */
function fnSubPromptMobile(schObj, url){
	fnInitPromptGlobal();

	$("#popModalMobileSub").fadeIn(100);
	$(".pop_bg1").fadeIn(100);

	//값전달
	G_MODAL_ID = "popModalMobileSub";
	//기존 prompt내용 제거
	$("#popModalMobileSub > div.pop_cont").html("");

	var v_params = schObj.attr('data-params');
	v_params = eval("("+v_params+")");

	var v_modalBody = $("#popModalMobileSub > div.pop_cont");

	$("#popModalMobileSub > div.pop_cont").load(url, v_params, function(data){
		if(!isEmpty(schObj.attr('data-title'))){
			$("#popModalMobileSub .layer_ttl").text(schObj.attr('data-title'));
		}
	});

	fnSetPromptGlobal(schObj); //값 전달용 오브젝트id 저장

}

//모달 main/sub 팝업닫기
function fnPromptMobileClose(){
	//console.log("fnPromptCloseMobile G_MODAL_ID==>>", G_MODAL_ID);
	try {
		$("#" + G_MODAL_ID).find("table.IBMainTable").each(function(){
			var id = $(this).attr("id");
			IBSheet[id].dispose();
		});
	} catch (e) {}

	// main 프롬프트에서의 호출이면 팝업 배경 hide(sub일 때는 main 프롬프트가 열려있다는 것이기 때문에 hide처리 하지않음.)
	if( "popModalMobileMain" == G_MODAL_ID){
		$('#popModalMobileMain').fadeOut(100);
		$(".pop_bg1").fadeOut(100);
	}else{
		$('#popModalMobileSub').fadeOut(100);
		$(".pop_bg1").fadeOut(100);
		G_MODAL_ID = "popModalMobileMain";	// 서브 팝업이 닫히면 G_MODAL_ID 기본값 지정
	}

	return false;
}

function spaceMobile(e) {
	// startsWith func
	String.prototype.startsWith = function(str) {
		if (this.length < str.length) {
			return false;
		}
		return this.indexOf(str) == 0;
	}

	// endsWith func
	String.prototype.endsWith = function(str) {
		if (this.length < str.length) {
			return false;
		}
		return this.lastIndexOf(str) + str.length == this.length;
	}

	$('body').find($("[class*='mar_']")).each(function() {
		var _mar = $(this).attr('class').split(' ');

		for (var i = 0; i < _mar.length; i++) {
			var num = '';
			var position = '';
			var arr = _mar[i].split('');
			if (_mar[i].startsWith('mar_') === true) {
				for (var j = 0; j < arr.slice(5).length; j++) {
					num += arr.slice(5)[j];
				}

				if (arr[4] === 't')
					position += 'margin-top';
				if (arr[4] === 'r')
					position += 'margin-right';
				if (arr[4] === 'b')
					position += 'margin-bottom';
				if (arr[4] === 'l')
					position += 'margin-left';

				$(this).css(position, parseInt(num));
			}
		}
	});

	$('body').find($("[class*='pad_']")).each(function() {
		var _pad = $(this).attr('class').split(' ');

		for (var i = 0; i < _pad.length; i++) {
			var num = '';
			var position = '';
			var arr = _pad[i].split('');
			if (_pad[i].startsWith('pad_') === true) {
				for (var j = 0; j < arr.slice(5).length; j++) {
					num += arr.slice(5)[j];
				}

				if (arr[4] === 't')
					position += 'padding-top';
				if (arr[4] === 'r')
					position += 'padding-right';
				if (arr[4] === 'b')
					position += 'padding-bottom';
				if (arr[4] === 'l')
					position += 'padding-left';

				$(this).css(position, parseInt(num));
			}
		}
	});

	$('body').find($("[class*='w']")).each(function() {
		var _w = $(this).attr('class').split(' ');

		for (var i = 0; i < _w.length; i++) {
			var num = _w[i].replace(/[^0-9]/g, '');
			if (_w[i].startsWith('w')
					&& (_w[i].endsWith('p') || _w[i].endsWith('px'))) {
				if (_w[i].startsWith('w') === true
						&& _w[i].endsWith('p'))
					$(this).css("width", num + "%");
				else if (_w[i].startsWith('w') === true
						&& _w[i].endsWith('px'))
					$(this).css("width", num + "px");
			}
		}
	});

	$('body').find($("[class*='fs']")).each(function() {
		var _fs = $(this).attr('class').split(' ');

		for (var i = 0; i < _fs.length; i++) {
			var num = _fs[i].replace(/[^0-9,_]/g, '');
			var underbar = _fs[i].replace(/[^_]/g, '');
			if (_fs[i].startsWith('fs')
					&& (_fs[i].endsWith('p')
							|| _fs[i].endsWith('px') || _fs[i]
							.endsWith('rem'))) {
				if (underbar) {
					num = num.replace('_', '.');
				}
				if (_fs[i].startsWith('fs') === true
						&& _fs[i].endsWith('p'))
					$(this).css("fontSize", num + "%");
				else if (_fs[i].startsWith('fs') === true
						&& _fs[i].endsWith('px'))
					$(this).css("fontSize", num + "px");
				else if (_fs[i].startsWith('fs') === true
						&& _fs[i].endsWith('rem'))
					$(this).css("fontSize", num + "rem");
			}
		}
	});
};

$(function(){
	spaceMobile();
	$('#popModalMobileMain > .pop_close').click(function() {
		fnPromptMobileClose();
	});
	$('#popModalMobileSub > .sub_close').click(function() {
		fnPromptMobileClose();
	});
});