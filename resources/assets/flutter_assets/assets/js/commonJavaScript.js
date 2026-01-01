/* jquery radio select
 * ex) $('selector').radioSelect(value);
 * */
$.fn.radioSelect = function(val) {
    this.each(function() {
        var $this = $(this);

        if($this.val() == val) {
//        	$this.attr('checked', true);
        	$this.prop('checked', true);
        }
    });
    return this;
};

$(document).ready(function () {

	// ScrollTop
	$(window).scroll(function() {
		if ($(this).scrollTop() > 300) {
			$('.pw_scrolltop').fadeIn();
		} else {
			$('.pw_scrolltop').fadeOut();
		}
	});
	$('.pw_scrolltop').click(function() {
		$('html, body').animate({scrollTop : 0}, 500);
		return false;
	});

	/*Backspace 뒤로가기 기능 제거 이벤트 추가*/
	//뒤로 가기 등의 키 방지용
	document.onkeydown = function (e) {
		key = (e) ? e.keyCode : event.keyCode;

		if (key == 8 || key == 116) {
		  if (event.srcElement.type != "text" && event.srcElement.type != "textarea" && event.srcElement.type != "password" && event.srcElement.type != undefined) {
			if (e) { //표준
			  e.preventDefault();
			}
			else { //익스용
			  event.keyCode = 0;
			  event.returnValue = false;
			}
		  }
		}
	}

	history.pushState(null, null, location.href);
	function myPopstateEventListener (event) {
		if (true){
			history.pushState(null, null, location.href);
			//console.log('Back Button Prevented');
		} else {
			window.removeEventListener('popstate', myPopstateEventListener);
			history.back();
		}
	}
	window.addEventListener('popstate', myPopstateEventListener);

	//페이지접근 로그처리 - fnInitialization 에서 이동함(여러번 호출되서) 2023-04-20 ktw
	var v_url = location.href;
	var v_formId = $("#projectFromId").val();
	var v_form = $("#" + v_formId);
	var v_params = "";
	if(v_form) v_params = v_form.serialize();
	var v_menuForm = $("#menuListForm").val();
	var v_menuId = fnToString($("#hdnMenuNo").val());
	var v_programUrl = fnToString(window.location.pathname);
	var v_programQueryStr = fnToString(window.location.search);
	if ( v_programQueryStr != "" ) {
		v_programUrl = v_programUrl + "" + v_programQueryStr;
	}
//	console.log("v_programUrl==>>", v_programUrl);
//	console.log("v_programQueryStr==>>", v_programQueryStr);
	var v_data = {actionUrl:v_url, params:v_params, menuId:v_menuId, programUrl:v_programUrl};
	//var v_retAccsData = fnGetJsonData("/hs/system/scrtyMng/HsSystemScrtyMngAccsLogRegist.json", v_data);
	$.ajax({
		url: "/hs/system/scrtyMng/HsSystemScrtyMngAccsLogRegist.json", // HsSystemScrtyMngDAO.insertScrtyAccsLog
		type: "POST",
		data: v_data,
		dataType: "json"
	});

});

//override defaults
alertify.defaults.transition = "slide";
alertify.defaults.theme.ok = "btn btn-primary";
alertify.defaults.theme.cancel = "btn btn-danger";
alertify.defaults.theme.input = "form-control";

if(!window.Message){
	/**
	 * alert, confirm, prompt 메시지 출력 후 callback 실행 시 사용
	 */
	var Message = {
		alert: function(message, callbackOk, isModal){
			if(!callbackOk) callbackOk = null;
			var labels = {
					ok : g_ok
				};
			if(typeof message == 'object'){
				labels = message.labels || labels;
				message = message.message;
			}
			isModal = (fnToString(isModal) == "") ? true : false;
			var msgContents = '<div style="text-align:center"><img src="/images/icons8-exclamation-mark-96.png" alt="information"></div>';
			msgContents += message;
			return alertify.alert()
				.set({
					'title' : '<span style="vertical-align:middle;color:#FFFFFF;"><h3>' + 'Information' + '</h3></span>',
					'label' : g_ok,
					'message' : msgContents,
					'onok' : callbackOk,
					'modal': isModal,
					'onshow': function(){
						this.elements.header.style.background = "#0C88DA";//남색
//						$(".ajs-header").css("background-color", "#0C88DA");//남색
					}
				}).show();
		},
		alertSuccess: function(message, callbackOk, isModal){
			if(!callbackOk) callbackOk = null;
			var labels = {
					ok : g_ok
				};
			if(typeof message == 'object'){
				labels = message.labels || labels;
				message = message.message;
			}
			isModal = (fnToString(isModal) == "") ? true : false;
			var msgContents = '<div style="text-align:center"><img src="/images/icons8-checkmark-96.png" alt="success"></div>';
			msgContents += message;
			return alertify.alert()
				.set({
					'title' : '<span style="vertical-align:middle;color:#FFFFFF;"><h3>' + 'Success' + '</h3></span>',
					'label' : g_ok,
					'message' : msgContents,
					'onok' : callbackOk,
					'modal': isModal,
					'onshow': function(){
						this.elements.header.style.background = "#11945A";//녹색
					}
				  }).show();
		},
		alertError: function(message, callbackOk, isModal){
			if(!callbackOk) callbackOk = null;
			var labels = {
					ok : g_ok
				};
			if(typeof message == 'object'){
				labels = message.labels && message.labels || labels;
				message = message.message;
			}
			isModal = (fnToString(isModal) == "") ? true : false;
			var msgContents = '<div style="text-align:center"><img src="/images/icons8-unavailable-96.png" alt="error"></div>';
			msgContents += message;
			return alertify.alert()
				.set({
					'title' : '<span style="vertical-align:middle;color:#FFFFFF;"><h3>' + 'Error' + '</h3></span>',
					'label' : g_ok,
					'message' : msgContents,
					'onok' : callbackOk,
					'modal': isModal,
					'onshow': function(){
						this.elements.header.style.background = "#EC3745";//적색
					}
				}).show();
		},
		confirm: function(message, callbackOk, callbackCancel, isModal){
			if(!callbackOk) callbackOk = null;
			var labels = {
					ok: g_yes,
					cancel: g_no
				};
			if(typeof message == 'object'){
				labels = message.labels || labels;
				message = message.message;
			}
			isModal = (fnToString(isModal) == "") ? true : false;
			var msgContents = '<div style="text-align:center"><img src="/images/icons8-question-mark-96.png" alt="confirm"></div>';
			msgContents += message;
			return alertify.confirm()
				.set({
//					'title' : '<h3>' + g_title_confirm + '</h3>',
					'title' : '<span style="vertical-align:middle;color:#FFFFFF;"><h3>' + 'Confirm' + '</h3></span>',
					'labels' : labels,
					'message' : msgContents,
					'onok' : callbackOk,
					'oncancel' : callbackCancel,
					'modal': isModal,
					'onshow': function(){
						this.elements.header.style.background = "#0F8CDC";//파란색
					}
				}).show();
		},
		prompt: function(message, callbackOk, callbackCancel, isModal, title, defaultVal, inputType){
			if(!callbackOk) callbackOk = null;
			var labels = {
					ok: g_ok,
					cancel: g_cancel
				};
			if(typeof message == 'object'){
				labels = message.labels || labels;
				message = message.message;
			}
			isModal = (fnToString(isModal) == "") ? true : false;
			title = (fnToString(title) == "") ? g_title_prompt : title; // 타이틀
			defaultVal = (fnToString(defaultVal) == "") ? "" : defaultVal; // 기본값
			inputType = (fnToString(inputType) == "") ? "text" : inputType; // text / password
			var msgContents = '<div style="text-align:center"><img src="/images/icons8-exclamation-mark-96.png" alt="information"></div>';
			msgContents += message;
			return alertify.prompt()
				.set({
//					'title' : title,
					'title' : '<span style="vertical-align:middle;color:#FFFFFF;"><h3>' + title + '</h3></span>',
					'labels' : labels,
					'message' : msgContents,
					'value' : defaultVal,
					'onok' : callbackOk,
					'oncancel' : callbackCancel,
					'modal': isModal,
					'onshow': function(){
						//console.log("this.elements==>", this.elements);
						this.elements.header.style.background = "#0C88DA";//남색
						//$(".ajs-header").css("background-color", "#0C88DA");//남색
						//this.elements.content.childNodes[1].className = this.elements.content.childNodes[1].className + " text"; // 아래로 수정
						var inputObj = this.elements.content.childNodes[1];// input box
						//console.log("inputObj==>", inputObj);
						if($(inputObj).hasClass("text") === false) {
							// class가 존재하지 않음	
							$(inputObj).addClass("text")
						}
						$(inputObj).prop("type", inputType); // text / password

//						this.elements.footer.childNodes[1].lastChild.remove();
						var btnDanger = this.elements.footer.childNodes[1].getElementsByClassName('btn-danger');
						if(btnDanger.length>0){
							btnDanger[0].remove(); // prompt 취소버튼 삭제(평가)
						}
					}
				}).show();
		},
		success: function(message, callbackOk){
			if(!callbackOk) callbackOk = null;
			if(typeof message == 'object'){
				message = message.message;
			}
			return alertify.success(message);
		},
		error: function(message, callbackOk){
			if(!callbackOk) callbackOk = null;
			if(typeof message == 'object'){
				message = message.message;
			}
			return alertify.error(message);
		}
	};
}

/*
 * 속성 여부 조회 추가
 */
$.fn.hasAttr = function(name) {
	return this.attr(name) !== undefined;
};

/*
 * 로딩 마스크를 보인다
 */
$.fn.center = function () {
	this.css("position","absolute");
	this.css("top", ($("body").height()/2-100) + "px" );
	this.css("left", ($("body").width()/2-60) + "px" );
	return this;
}

/*
 * form의 데이터를 json 형태로 변환
 */
$.fn.serializeObject = function() {
	var obj = null;
	try {
		if ( this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) {
			var arr = this.serializeArray();
			if ( arr ) {
				obj = {};
				jQuery.each(arr, function() {
					obj[this.name] = this.value;
				});
			}
		}
	}
	catch(e) {Message.alertError(e.message);}
	finally  {}

	return obj;
};

/*
 * 초기설정 함수 include된 탭 페이지들이 load된 후에 적용해야 하므로 실행은 HsSystemIncBottomHtml.jsp에서 호출
 * - 참고 URL : http://digitalbush.com/projects/masked-input-plugin
 */
var showBsModal = function(e) {
	var mLink = e.relatedTarget ? $(e.relatedTarget) : $(e);
	var mTitle = $(mLink).attr('title');
	var mUrl = $(mLink).attr('data-url');
	var mParam = $(mLink).attr('data-params');
		mParam = eval("("+mParam+")");
	var modal = $(this);

	//헤더설정
	if(mTitle != "") {
		modal.find('.modal-title').text(mTitle);
	}

	if(mUrl != "") {
		modal.find('.modal-body').load(mUrl, mParam, function () {
			//$("#content > div.con_area").html("<h3>설정 테스트</h3>"); //페이지 로드후 html 변경처리
		});
		//modal.find(‘.modal-body input’).val(recipient);
	}
};

/**
 * JavaScript의 Modal 방식의 dialog를 지원하는 windows.showModalDialog의 기능이 chrome 37 버전부터 지원하지 않음 - 대체 추가
 * @param retVal
 * @returns
 */
function showModalDialogCallback(retVal) {
	if(retVal != null){
		var tmp = retVal.split("|");
		if(G_TXTID) {
		 G_TXTID.value = tmp[0];
		}
		if(G_TXTDESCR) {
		 G_TXTDESCR.value = tmp[1];
		}
	}
}

/**
 * 로딩 마스크를 보이게 한다.
 * @param flag
 * @returns
 */
function showLoadingMask(flag) {
	//크기 지정(로딩중 마우스 클릭을 막기 위해 필요)
	var _scrollTop = window.scrollY || document.documentElement.scrollTop;
	var maskWidth = $("body").width();
	var maskHeight = $("body").height();
	$("#loading-bg").show();
	$("#loading-mask").css("width", maskWidth + "px");
	$("#loading-mask").css("height", maskHeight + "px");
	$("#loading-mask").css("top", _scrollTop + "px");
	$("#loading-mask").show();

	if( String(flag) == "undefined"){
//		$("#loading-mask").focus();
	}else if (flag){
//	 	$("#loading-mask").focus();
	}
}

/**
 * 로딩 마스크를 숨긴다.
 * @returns
 */
function hideLoadingMask() {
	$("#loading-mask").fadeOut("slow");
	$("#loading-bg").fadeOut("slow");
//	$("#loading-bg").hide();
}

/**
 * 유효성 검사
 * @param form
 * @returns true(통과) / false(비 유효 항목 있음)
 */
function isValid(form){
	var varForm = form;
	var validateFunction = form.name;
	validateFunction = validateFunction.charAt(0).toUpperCase() + validateFunction.substr(1);
	validateFunction = "validate" + validateFunction;
	if( window[validateFunction] && ! window[validateFunction](varForm) ) {
		return false;
	} else {
		return true;
	}
}

/**
 * 처리완료 메세지 출력 후 callback 실행
 * @param addMsg 출력 메시지
 * @param callbackFunction callback function
 * @param data callback 시 넘길 데이터
 * @returns
 */
function fnCallSuccessMessage(message, callbackFunction, data) {
	Message.alertSuccess(message ? message : g_request, function() {
		if(callbackFunction) {
			callbackFunction(data);
		}
	});
}

/**
 * 페이지 이동. 페이지 번호가 있을 경우 해당 번호의 페이지로 이동
 * @param pageNo
 * @param form
 * @param action
 * @returns
 */
function fnLinkPage(pageNo, form, action){
	var varForm = form;
	if(varForm.pageIndex){
		varForm.pageIndex.value = pageNo;
	}
	varForm.action = action;
	varForm.submit();
}

/**
 * 페이지 이동(파라미터 POST처리)
 * @param p_url
 * @param p_params
 * @param p_target
 * @returns
 */
function fnPostMove(p_url, p_params, p_target) {
	var v_frm = document.createElement('form');

	var v_objs, v_value;
	var data = '';
	if(p_params){
		$.each(p_params, function(key, val){
			v_objs = document.createElement('input');
			v_objs.setAttribute('type', 'hidden');
			v_objs.setAttribute('name', key);
			v_objs.setAttribute('value', val);
			v_frm.appendChild(v_objs);
		});
	}

	if (p_target){
		v_frm.setAttribute('target', p_target);
	}

	v_frm.setAttribute('method', 'post');
	v_frm.setAttribute('action', p_url);
	document.body.appendChild(v_frm);
	v_frm.submit();
}

/**
 * 페이지 이동(파라미터 POST처리) - enctype="multipart/form-data" 없을경우만 사용
 * @param p_url
 * @param p_params
 * @param p_target
 * @param formId
 * @returns
 */
function fnPostMoveFormId(p_url, p_params, p_target, formId) {
	console.log("fnPostMoveFormId");
	var v_frm = document.createElement('form');

	var v_objs, v_value;
	var data = '';
	if(p_params){
		$.each(p_params, function(key, val){
			v_objs = document.createElement('input');
			v_objs.setAttribute('type', 'hidden');
			v_objs.setAttribute('name', key);
			v_objs.setAttribute('value', val);
			v_frm.appendChild(v_objs);
		});
	}

	$.each($('#'+formId).find("[name]"), function(){
		//if($(this).attr("type") == "file") { //파일로 넘길 시 VO 객체는 만들면 안된다.
			//v_objs = document.createElement('input');
			//v_objs.setAttribute('type', 'file');
			//v_objs.setAttribute('name', $(this).attr("name"));
			//v_objs.setAttribute('value', $(this)[0].files[0]);
			//v_frm.appendChild(v_objs);
		//} else {
			v_objs = document.createElement('input');
			v_objs.setAttribute('type', 'hidden');
			v_objs.setAttribute('name', $(this).attr("name"));
			v_objs.setAttribute('value', fnNvl($(this).val(), ''));
			v_frm.appendChild(v_objs);
		//}
	});

//	v_objs = document.createElement('input');
//	v_objs.setAttribute('type', 'hidden');
//	v_objs.setAttribute('name', "${_csrf.parameterName}");
//	v_objs.setAttribute('value', "${_csrf.token}");
//	v_frm.appendChild(v_objs);

	if (p_target) {
		v_frm.setAttribute('target', p_target);
	}

	v_frm.setAttribute('method', 'post');
	v_frm.setAttribute('action', p_url);
	document.body.appendChild(v_frm);
	v_frm.submit();
}

/*
 * 페이지 이동(파라미터 POST처리)
 */
function fnPostMoveForName(p_url, p_params, p_target) {
	var v_frm = document.createElement('form');

	var v_objs, v_value;
	var data = '';
	if(p_params){
		$.each(p_params, function(key, val){
		  	v_objs = document.createElement('input');
		  	v_objs.setAttribute('type', 'hidden');
		  	v_objs.setAttribute('name', val['name']);
		  	v_objs.setAttribute('value', val['value']);
		  	v_frm.appendChild(v_objs);
		});
	}

	if (p_target)
	  v_frm.setAttribute('target', p_target);

	v_frm.setAttribute('method', 'post');
	v_frm.setAttribute('action', p_url);
	document.body.appendChild(v_frm);
	v_frm.submit();
}

/**
 * 새로 검색 실행 또는 조회 페이지로 이동
 * @param form
 * @param actionUrl
 * @returns
 */
function fnSearch(form, actionUrl){
	if(isValid(form)) {
		fnLinkPage(1, form, actionUrl); // 페이지 번호를 첫 페이지로 셋팅 하여 검색 실행
	} else {
		return;
	}
}

/**
 * 등록 페이지로 이동
 * @param form
 * @param actionUrl
 * @returns
 */
function fnCreate(form, actionUrl){
	var varForm = form;
	varForm.action = actionUrl;
	varForm.submit();
}

/**
 * 목록 페이지로 이동
 * @param form
 * @param actionUrl
 * @returns
 */
function fnList(form, actionUrl){
	var varForm = form;
	varForm.attr("action", actionUrl);
	varForm.submit();
}

/**
 * 새창에서 페이지 열기
 * @param p_formId 전송 폼 아이디
 * @param p_actionUrl open URL
 * @param p_params 추가 파라메터
 * @returns
 */
function fnNewTab(p_formId, p_actionUrl, p_params){
	console.log("fnNewTab", p_formId, p_actionUrl, p_params);
	var v_frm = $("#"+p_formId);
	if(p_formId){
		v_frm.attr('target', "_blank");
		v_frm.attr('method', 'post');
		v_frm.attr('action', p_actionUrl);
	}else{
		console.log("new form");
		v_frm = document.createElement('form');
		v_frm.setAttribute('target', "_blank");
		v_frm.setAttribute('method', 'post');
		v_frm.setAttribute('action', p_actionUrl);
		document.body.appendChild(v_frm);
	}
	if(p_params){
		$.each(p_params, function(key, val){
			var v_objs = document.createElement('input');
			v_objs.setAttribute('type', 'hidden');
			v_objs.setAttribute('name', key);
			v_objs.setAttribute('value', val);
			v_frm.appendChild(v_objs);
		});
	}
	v_frm.submit();
}

/**
 * 업데이트 페이지로 이동
 * @param form
 * @param actionUrl
 * @returns
 */
function fnUpdate(form, actionUrl){
	var varForm = form;
	varForm.action = actionUrl;
	varForm.submit();
}

/**
 * confirm 후 form 데이터 삭제 submit
 * @param form
 * @param actionUrl
 * @returns
 */
function fnDelete(form, actionUrl){
	Message.confirm(g_del, function(isOk){
		if(!isOk) return;
		var varForm = form;
		varForm.action = actionUrl;
		varForm.submit();
	});
}

/**
 * confirm 후 form 데이터 저장 submit
 * @param form
 * @returns
 */
function fnSave(form){
	if($("#"+form.id).valid()) {
		Message.confirm(g_save, function(isOk){
			if(isOk){
				form.submit();
			}else return false;
		})
	}
}

/**
 * Ajax로 form 조건 검색 실행
 * @param formId
 * @param actionUrl
 * @param callbackFunction
 * @returns
 */
function fnSearchAjax(formId, actionUrl, callbackFunction){
	showLoadingMask(); //로딩시작
	//조회처리
	var procAjax = $.ajax({
		url: actionUrl,
		type: "post",
		dataType: "json",
		data: $('#'+formId).serialize(),
		cache: false,
		success: function(data, textStatus, jqXHR) {
			$("#ajax").remove();
			hideLoadingMask();
			if (jqXHR.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp";
			}

			if(data.message == null) {
				if(callbackFunction)
					callbackFunction(data);
			}
			else {
				Message.alertError(data.message );
			}
		},
		error:function(request,status,error){
			if (request.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp";
			}
			document.write(request.responseText);
		},
		complete: function() {
			hideLoadingMask(); //로딩마감
		}
	});
}

/**
 * Ajax로 jsonParams 검색조건 검색 셀행
 * @param jsonParams 검색조건
 * @param actionUrl 싱핼 url
 * @param callbackFunction
 * @param isLodingMask default : true
 * @returns
 */
function fnSearchJsonAjax(jsonParams, actionUrl, callbackFunction, isLodingMask){

	if(typeof isLodingMask == "undefined" || isLodingMask != false) {
		showLoadingMask(); //로딩시작
	}
	//조회처리
	var procAjax = $.ajax({
		url: actionUrl,
		type: "post",
		dataType: "json",
		data: jsonParams,
		cache: false,
		success: function(data, textStatus, jqXHR) {
			$("#ajax").remove();
			hideLoadingMask();
			if (jqXHR.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp";
			}

			if(data.message == null) {
				if(callbackFunction)
					callbackFunction(data);
			}
			else {
				Message.alertError(data.message );
			}
		},
		error:function(request,status,error){
			if (request.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp";
			}
			document.write(request.responseText);
		},
		complete: function() {
			if(typeof isLodingMask == "undefined" || isLodingMask != false) {
				hideLoadingMask(); //로딩마감
			}
		}
	});
}

/**
 * Ajax로 form 데이터 저장 실행( fnSaveAjax()와의 차이는 form validation체크를 하지 않음. )
 * @param formId
 * @param actionUrl
 * @param callbackFunction
 * @param isConfirmMessage 저장처리전 컨펌메시지 사용 여부 default : true
 * @param isSuccessMessage 저장처리후 완료메시지 사용 여부 default : true
 * @param isLodingMask default : true
 * @returns
 */
function fnNonValidateAjax(formId, actionUrl, callbackFunction, isConfirmMessage, isSuccessMessage, isLodingMask){
	var doing = function(){
		if(typeof isLodingMask == "undefined" || isLodingMask != false) {
			showLoadingMask(); //로딩시작
		}

		//저장처리
		var formData = new FormData();
		$.each($('#'+formId).find("[name]"), function(){
			if($(this).attr("type") == "file") { //파일로 넘길 시 VO 객체는 만들면 안된다.
				formData.append($(this).attr("name"), $(this)[0].files[0]);
			} else if("radio" == $(this).attr("type") || "checkbox" == $(this).attr("type")) {
				// radio 일 경우 check 된 것만
				if($(this).is(":checked")) {
					formData.append($(this).attr("name"), fnNvl($(this).val(), ''));
				}
			}  else {
				formData.append($(this).attr("name"), fnNvl($(this).val(), ''));
			}
		});

		var procAjax = $.ajax({
			url: actionUrl,
			type: 'POST',
			//dataType: "json",
			data: formData,
			processData: false,
			contentType: false,
			//async: false, //동기화처리
			cache: false,
			success: function(data, textStatus, jqXHR) {
				$("#ajax").remove();
				hideLoadingMask();
				if (jqXHR.status == 204) {
					console.log("ajax 204");
					location.href = "/common/sessionErrorForward.jsp";
				}

				//데이터 처리 시간 관계상 로딩 이미지 처리를 위해 수정함.
				if(data.message == null) {
					if(isSuccessMessage != false){
						fnCallSuccessMessage('', callbackFunction,data);
					}else{
						if(callbackFunction)
							callbackFunction(data);
					}
				} else {
					Message.alertError(g_failRequest+"-" + data.message );
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				if (jqXHR.status == 204) {
					console.log("ajax 204");
					location.href = "/common/sessionErrorForward.jsp";
				}
				Message.alertError(g_failRequest + jqXHR.responseText );
			},
			complete: function() {
				if(typeof isLodingMask == "undefined" || isLodingMask != false) {
					hideLoadingMask(); //로딩마감
				}
			}
		});
	}

	if(typeof isConfirmMessage == "undefined" || isConfirmMessage != false)
		Message.confirm(g_save, function(isOk){
			if(isOk) doing();
		});
	else {
		doing();
	}
	return false;
}

/**
 * Ajax 실행
 * @param options
 * @returns
 */
function fnCallAjax(p_option, p_succCallback, p_failCallback, p_errorCallback){
	if ( fnToString(p_option) == "" ) {
		Message.alertError("fnCallAjax option is null!!");
		return;
	}
	console.log("fnCallAjax", p_option);
	var doing = function(){
		if(typeof isLodingMask == "undefined" || isLodingMask != false) {
			showLoadingMask(); //로딩시작
		}

		//저장처리
		var formData = new FormData();

		var v_formId = fnToString(p_option.formId);
		if(v_formId != ''){
			if($("#"+v_formId).valid()) {
				console.log("fnCallAjax v_formId", v_formId);

				// 첨부파일 설정
				if(p_option && p_option.setAttachFile) {
					fnSetFileFormData(formData);    // 첨부파일 데이터
				} else {
					$.each($('#'+v_formId).find("[name]"), function(){
						if($(this).attr("type") == "file") { //파일로 넘길 시 VO 객체는 만들면 안된다.
						    formData.append($(this).attr("name"), $(this)[0].files[0]);
						}
					});
				}

				$.each($('#'+v_formId).find("[name]"), function(){
					if("radio" == $(this).attr("type") || "checkbox" == $(this).attr("type")) {
						// radio 일 경우 check 된 것만
						if($(this).is(":checked")) {
							formData.append($(this).attr("name"), fnNvl($(this).val(), ''));
						}
					}
					else {
						if($(this).hasAttr("data-amountOnly")){
							formData.append($(this).attr("name"), $(this).val().replace(/[^-\.[0-9]/gi,""));
						}
						else{
							formData.append($(this).attr("name"), fnNvl($(this).val(), ''));
						}
					}
				});
			}
		}

		if(fnToString(p_option.params) != ''){
			console.log("fnCallAjax params", p_option.params);
			$.each(p_option.params, function(key, val){
				formData.delete(key);
				formData.append(key, val);
			});
		}

		var v_successMsg = fnToString(p_option.successMsg);
		var v_failMsg = fnToString(p_option.failMsg);

		var procAjax = $.ajax({
			url: fnToString(p_option.url),
			type: 'POST',
			//dataType: "json",
			data: formData,
			processData: false,
			contentType: false,
			//async: fnToString(p_option.async).toLowerCase() == 'true', //동기화처리
			cache: false,
			success: function(data, textStatus, jqXHR) {
				//console.log("fnCallAjax success", data);
				$("#ajax").remove();
				hideLoadingMask();
				if (jqXHR.status == 204) {
					console.log("ajax 204");
					location.href = "/common/sessionErrorForward.jsp";
				}

				if(data.message == null) {
					if(v_successMsg != ''){
						fnCallSuccessMessage(v_successMsg, p_succCallback, data);
					}else{
						if(p_succCallback)
							p_succCallback(data);
					}
				} else {
					if(v_failMsg != ''){
						v_failMsg += "<br><br>" + data.message;
					}
					Message.alertError(v_failMsg);

					if(p_failCallback)// 실패시에도 후처리 해야 할 때가 있다.
						p_failCallback(data);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//console.log("fnCallAjax error", errorThrown);
				if (jqXHR.status == 204) {
					console.log("ajax 204");
					location.href = "/common/sessionErrorForward.jsp";
				}
				if(v_failMsg != ''){
					v_failMsg += "<br><br>" + jqXHR.responseText;
				}
				Message.alertError(v_failMsg);

				if(p_errorCallback)// 실패시에도 후처리 해야 할 때가 있다.
					p_errorCallback({message:"error"});
			},
			complete: function() {
				console.log("fnCallAjax complete");
				if(fnToString(p_option.isLodingMask).toLowerCase() != 'false') {
					hideLoadingMask(); //로딩마감
				}
			}
		});
	}

	var v_confirmMsg = fnToString(p_option.confirmMsg);
	if ( v_confirmMsg != "" ) {
		Message.confirm(v_confirmMsg, function(isOk){
			if(isOk) doing();
		});
	}
	else {
		doing();
	}

	return false;
}

/**
 * Ajax로 form 데이터 저장 실행
 * @param formId
 * @param actionUrl
 * @param callbackFunction
 * @param isConfirmMessage 저장처리전 컨펌메시지 사용 여부 default : true
 * @param isSuccessMessage 저장처리후 완료메시지 사용 여부 default : true
 * @param isLodingMask default : true
 * @returns
 */
function fnSaveAjax(formId, actionUrl, callbackFunction, isConfirmMessage, isSuccessMessage, isLodingMask, p_option){
	var doing = function(){
		if($("#"+formId).valid()) {
			if(typeof isLodingMask == "undefined" || isLodingMask != false) {
				showLoadingMask(); //로딩시작
			}

			$.each($("input:text[data-amountOnly]"), function(index, obj){
				$(this).val( $(this).val().replace(/[^-\.[0-9]/gi,"") );
			});

			//저장처리
			var formData = new FormData();

			// setAttachFile : 첨부파일 설정 여부
			if(p_option && p_option.setAttachFile) {
				fnSetFileFormData(formData);    // 첨부파일 데이터

			} else {

				$.each($('#'+formId).find("[name]"), function(){
					if($(this).attr("type") == "file") { //파일로 넘길 시 VO 객체는 만들면 안된다.
					    formData.append($(this).attr("name"), $(this)[0].files[0]);
					}
				});
			}

			$.each($('#'+formId).find("[name]"), function(){

				if("radio" == $(this).attr("type") || "checkbox" == $(this).attr("type")) {
					// radio 일 경우 check 된 것만
					if($(this).is(":checked")) {
						formData.append($(this).attr("name"), fnNvl($(this).val(), ''));
					}
				}  else {
					formData.append($(this).attr("name"), fnNvl($(this).val(), ''));
				}
			});

			var v_params = {};
			var v_saveSucc = g_saveSucc;
			var v_saveFail = g_saveFail;
			if ( fnToString(p_option) != "" ) {
				if ( fnToString(p_option.params) != "" ) {
					v_params = p_option.params;
				}
				if ( fnToString(p_option.saveSucc) != "" ) {
					v_saveSucc = p_option.saveSucc;
				}
				if ( fnToString(p_option.saveFail) != "" ) {
					v_saveFail = p_option.saveFail;
				}
			}
			v_saveFail += "<br><br>";

			$.each(v_params, function(key, val){
				formData.append(key, val);
				//console.log("p_params p_params==>>", key + ":" + val);
			});

			var procAjax = $.ajax({
				url: actionUrl,
				type: 'POST',
				//dataType: "json",
				data: formData,
				processData: false,
				contentType: false,
				//async: false, //동기화처리
				cache: false,
				success: function(data, textStatus, jqXHR) {
					$("#ajax").remove();
					hideLoadingMask();
					if (jqXHR.status == 204) {
						console.log("ajax 204");
						location.href = "/common/sessionErrorForward.jsp";
					}

					//데이터 처리 시간 관계상 로딩 이미지 처리를 위해 수정함.
					if(data.message == null) {
						if(isSuccessMessage != false){
							fnCallSuccessMessage(v_saveSucc, callbackFunction, data);
						}else{
							if(callbackFunction)
								callbackFunction(data);
						}
					} else {
						Message.alertError(v_saveFail + data.message, function(){
							if(callbackFunction) // 실패시에도 후처리 해야 할 때가 있다.
								callbackFunction(data);
						});

					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					if (jqXHR.status == 204) {
						console.log("ajax 204");
						location.href = "/common/sessionErrorForward.jsp";
					}
					Message.alertError(v_saveFail + jqXHR.responseText );
				},
				complete: function() {
					if(typeof isLodingMask == "undefined" || isLodingMask != false) {
						hideLoadingMask(); //로딩마감
					}
					$.each($("input:text[data-amountOnly]"), function(index, obj){
						$(this).val( fnFormatCurrency($(this).val()) );
					});
				}
			});
		}
	}
	var v_confirmMsg = "";
	if ( fnToString(p_option) != "" ) {
		if ( fnToString(p_option.confirmMsg) != "" ) {
			v_confirmMsg = p_option.confirmMsg;
		}
	}
	if ( typeof isConfirmMessage == "undefined" || isConfirmMessage != false ) {
		var msg = g_save;
		if ( fnToString(v_confirmMsg) != "" ) {
			msg = v_confirmMsg;
		}
		Message.confirm(msg, function(isOk){
			if(isOk) doing();
		});
	} else {
		doing();
	}
	return false;
}

/**
 * Ajax로 form 데이터 저장 실행
 * @param formId
 * @param actionUrl
 * @param callbackFunction
 * @param isConfirmMessage 저장처리전 컨펌메시지 사용 여부 default : true
 * @param isSuccessMessage 저장처리후 완료메시지 사용 여부 default : true
 * @param isLodingMask default : true
 * @returns
 */
function fnSaveAjaxArrData(formId, actionUrl, callbackFunction, isConfirmMessage, isSuccessMessage, isLodingMask, p_option){
	var doing = function(){
		if($("#"+formId).valid()) {
			if(typeof isLodingMask == "undefined" || isLodingMask != false) {
				showLoadingMask(); //로딩시작
			}

			$.each($("input:text[data-amountOnly]"), function(index, obj){
				$(this).val( $(this).val().replace(/[^-\.[0-9]/gi,"") );
			});

			//저장처리
			var formData = new FormData();

			// setAttachFile : 첨부파일 설정 여부
			if(p_option && p_option.setAttachFile) {
				fnSetFileFormData(formData);    // 첨부파일 데이터

			} else {

				$.each($('#'+formId).find("[name]"), function(){
					if($(this).attr("type") == "file") { //파일로 넘길 시 VO 객체는 만들면 안된다.
					    formData.append($(this).attr("name"), $(this)[0].files[0]);
					}
				});
			}

			var values = {};
			var addVals = function(name, value){
				if(values[name]){
					//쉼표를 치환 - CHR(44)는 오라클 캐릭터로 쉼표임 2024-02-22 제테마 연말정산 에러나서 수정함. java에서 재치환 해야됨
					values[name] = values[name] + "," + fnReplaceAll(value, ",", "CHR44");
				}else{
					values[name] = fnReplaceAll(value, ",", "CHR44");
				}
				//console.log(">>>  "+name, values[name]);
			}
			$.each($('#'+formId).find("[name]"), function(){
				//console.log(">> "+$(this).attr("name"), $(this).val());
				if("radio" == $(this).attr("type") || "checkbox" == $(this).attr("type")) {
					// radio 일 경우 check 된 것만
					if($(this).is(":checked")) {
						addVals($(this).attr("name"), fnNvl($(this).val(), ' '));
					}
				}  else {
					addVals($(this).attr("name"), fnNvl($(this).val(), ' '));
				}
			});
			$.each(values, function(name, value){
				//console.log(">>>>>  "+name, value);
				formData.append(name, value);
			});

			var v_params = {};
			var v_saveSucc = g_saveSucc;
			var v_saveFail = g_saveFail;
			if ( fnToString(p_option) != "" ) {
				if ( fnToString(p_option.params) != "" ) {
					v_params = p_option.params;
				}
				if ( fnToString(p_option.saveSucc) != "" ) {
					v_saveSucc = p_option.saveSucc;
				}
				if ( fnToString(p_option.saveFail) != "" ) {
					v_saveFail = p_option.saveFail;
				}
			}
			v_saveFail += "<br><br>";

			$.each(v_params, function(key, val){
				formData.append(key, val);
				//console.log("p_params p_params==>>", key + ":" + val);
			});

			var procAjax = $.ajax({
				url: actionUrl,
				type: 'POST',
				//dataType: "json",
				data: formData,
				processData: false,
				contentType: false,
				//async: false, //동기화처리
				cache: false,
				success: function(data, textStatus, jqXHR) {
					$("#ajax").remove();
					hideLoadingMask();
					if (jqXHR.status == 204) {
						console.log("ajax 204");
						location.href = "/common/sessionErrorForward.jsp";
					}

					//데이터 처리 시간 관계상 로딩 이미지 처리를 위해 수정함.
					if(data.message == null) {
						if(isSuccessMessage != false){
							fnCallSuccessMessage(v_saveSucc, callbackFunction, data);
						}else{
							if(callbackFunction)
								callbackFunction(data);
						}
					} else {
						Message.alertError(v_saveFail + data.message, function(){
							if(callbackFunction) // 실패시에도 후처리 해야 할 때가 있다.
								callbackFunction(data);
						});

					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					if (jqXHR.status == 204) {
						console.log("ajax 204");
						location.href = "/common/sessionErrorForward.jsp";
					}
					Message.alertError(v_saveFail + jqXHR.responseText );
				},
				complete: function() {
					if(typeof isLodingMask == "undefined" || isLodingMask != false) {
						hideLoadingMask(); //로딩마감
					}
					$.each($("input:text[data-amountOnly]"), function(index, obj){
						$(this).val( fnFormatCurrency($(this).val()) );
					});
				}
			});
		}
	}
	var v_confirmMsg = "";
	if ( fnToString(p_option) != "" ) {
		if ( fnToString(p_option.confirmMsg) != "" ) {
			v_confirmMsg = p_option.confirmMsg;
		}
	}
	if ( typeof isConfirmMessage == "undefined" || isConfirmMessage != false ) {
		var msg = g_save;
		if ( fnToString(v_confirmMsg) != "" ) {
			msg = v_confirmMsg;
		}
		Message.confirm(msg, function(isOk){
			if(isOk) doing();
		});
	} else {
		doing();
	}
	return false;
}

/**
 * Ajax로 form 데이터 저장 실행
 * @param formId
 * @param actionUrl
 * @param callbackFunction
 * @param isConfirmMessage 저장처리전 컨펌메시지 사용 여부 default : true
 * @param isSuccessMessage 저장처리후 완료메시지 사용 여부 default : true
 * @param isLodingMask default : true
 * @returns
 */
function fnSaveAjaxNoneValid(formId, actionUrl, callbackFunction, isConfirmMessage, isSuccessMessage, isLodingMask, p_option){
	var doing = function(){
		if(typeof isLodingMask == "undefined" || isLodingMask != false) {
			showLoadingMask(); //로딩시작
		}

		$.each($("input:text[data-amountOnly]"), function(index, obj){
			$(this).val( $(this).val().replace(/[^-\.[0-9]/gi,"") );
		});

		//저장처리
		var formData = new FormData();

		// setAttachFile : 첨부파일 설정 여부
		if(p_option && p_option.setAttachFile) {
			fnSetFileFormData(formData);    // 첨부파일 데이터

		} else {

			$.each($('#'+formId).find("[name]"), function(){
				if($(this).attr("type") == "file") { //파일로 넘길 시 VO 객체는 만들면 안된다.
				    formData.append($(this).attr("name"), $(this)[0].files[0]);
				}
			});
		}

		$.each($('#'+formId).find("[name]"), function(){

			if("radio" == $(this).attr("type") || "checkbox" == $(this).attr("type")) {
				// radio 일 경우 check 된 것만
				if($(this).is(":checked")) {
					formData.append($(this).attr("name"), fnNvl($(this).val(), ''));
				}
			}  else {
				formData.append($(this).attr("name"), fnNvl($(this).val(), ''));
			}
		});

		var v_params = {};
		var v_saveSucc = g_saveSucc;
		var v_saveFail = g_saveFail;
		if ( fnToString(p_option) != "" ) {
			if ( fnToString(p_option.params) != "" ) {
				v_params = p_option.params;
			}
			if ( fnToString(p_option.saveSucc) != "" ) {
				v_saveSucc = p_option.saveSucc;
			}
			if ( fnToString(p_option.saveFail) != "" ) {
				v_saveFail = p_option.saveFail;
			}
		}
		v_saveFail += "<br><br>";

		$.each(v_params, function(key, val){
			formData.append(key, val);
			//console.log("p_params p_params==>>", key + ":" + val);
		});

		var procAjax = $.ajax({
			url: actionUrl,
			type: 'POST',
			//dataType: "json",
			data: formData,
			processData: false,
			contentType: false,
			//async: false, //동기화처리
			cache: false,
			success: function(data, textStatus, jqXHR) {
				$("#ajax").remove();
				hideLoadingMask();
				if (jqXHR.status == 204) {
					console.log("ajax 204");
					location.href = "/common/sessionErrorForward.jsp";
				}

				//데이터 처리 시간 관계상 로딩 이미지 처리를 위해 수정함.
				if(data.message == null) {
					if(isSuccessMessage != false){
						fnCallSuccessMessage(v_saveSucc, callbackFunction, data);
					}else{
						if(callbackFunction)
							callbackFunction(data);
					}
				} else {
					Message.alertError(v_saveFail + data.message );
					if(callbackFunction) // 실패시에도 후처리 해야 할 때가 있다.
						callbackFunction(data);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				if (jqXHR.status == 204) {
					console.log("ajax 204");
					location.href = "/common/sessionErrorForward.jsp";
				}
				Message.alertError(v_saveFail + jqXHR.responseText );
			},
			complete: function() {
				if(typeof isLodingMask == "undefined" || isLodingMask != false) {
					hideLoadingMask(); //로딩마감
				}
				$.each($("input:text[data-amountOnly]"), function(index, obj){
					$(this).val( fnFormatCurrency($(this).val()) );
				});
			}
		});
	}
	var v_confirmMsg = "";
	if ( fnToString(p_option) != "" ) {
		if ( fnToString(p_option.confirmMsg) != "" ) {
			v_confirmMsg = p_option.confirmMsg;
		}
	}
	if ( typeof isConfirmMessage == "undefined" || isConfirmMessage != false ) {
		var msg = g_save;
		if ( fnToString(v_confirmMsg) != "" ) {
			msg = v_confirmMsg;
		}
		Message.confirm(msg, function(isOk){
			if(isOk) doing();
		});
	} else {
		doing();
	}
	return false;
}

/**
 * Ajax로 parmJson 데이터 저장 실행
 * @param parmJson
 * @param actionUrl
 * @param callbackFunction
 * @param isConfirmMessage 저장처리전 컨펌메시지 출력 여부 default : true
 * @param isSuccessMessage 저장처리후 완료메시지 출력 여부 default : true
 * @param isLodingMask default : true
 * @returns
 */
function fnSaveJsonAjax(parmJson, actionUrl, callbackFunction, isConfirmMessage, isSuccessMessage, isLodingMask){
	var doing = function(){
		if(typeof isLodingMask == "undefined" || isLodingMask != false) {
			showLoadingMask(); //로딩시작
		}

		var procAjax = $.ajax({
			url: actionUrl,
			type: 'POST',
			dataType: "json",
			data: parmJson,
			cache: false,
			//processData: false,
			//contentType: false,
			//async: false, //동기화처리
			success: function(data, textStatus, jqXHR) {
				$("#ajax").remove();
				hideLoadingMask();
				if (jqXHR.status == 204) {
					console.log("ajax 204");
					location.href = "/common/sessionErrorForward.jsp";
				}

				if(data.message == null) {
					if(isSuccessMessage != false){
						fnCallSuccessMessage(g_saveSucc, callbackFunction,data);
					} else {
						if(callbackFunction)
							callbackFunction(data);
					}

				} else {
					Message.alertError(g_saveFail+"-" + data.message );
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				if (jqXHR.status == 204) {
					console.log("ajax 204");
					location.href = "/common/sessionErrorForward.jsp";
				}
				//alert(textStatus);
				//document.write(jqXHR.responseText);
				Message.alertError(g_saveFail + jqXHR.responseText );
			},
			complete: function() {
				if(typeof isLodingMask == "undefined" || isLodingMask != false) {
					hideLoadingMask(); //로딩마감
				}
			}
		});
	}

	if(typeof isConfirmMessage == "undefined" || isConfirmMessage != false)
		Message.confirm(g_save, function(isOk){
			if(isOk) doing();
		});
	else {
		doing();
	}
	return false;
}

/**
 * Ajax로 데이터 삭제 실행
 * @param formId
 * @param actionUrl
 * @param callbackFunction
 * @param isSuccessMessage 저장처리후 완료메시지 출력 여부 default : true
 * @param isLodingMask default : true
 * @returns
 */
function fnDeleteAjax(formId, actionUrl, callbackFunction, isSuccessMessage, isLodingMask, p_option){
	var doing = function(){
		if(typeof isLodingMask == "undefined" || isLodingMask != false) {
			showLoadingMask(); //로딩시작
		}

		//저장처리
		var formData = new FormData();
		$.each($('#'+formId).find("[name]"), function(){
			if($(this).attr("type") == "file") { //파일로 넘길 시 VO 객체는 만들면 안된다.
				formData.append($(this).attr("name"), $(this)[0].files[0]);
			} else if("radio" == $(this).attr("type") || "checkbox" == $(this).attr("type")) {
				// radio 일 경우 check 된 것만
				if($(this).is(":checked")) {
					formData.append($(this).attr("name"), fnNvl($(this).val(), ''));
				}
			}  else {
				formData.append($(this).attr("name"), fnNvl($(this).val(), ''));
			}
		});
		var v_params = {};
		if ( fnToString(p_option) != "" ) {
			if ( fnToString(p_option.params) != "" ) {
				v_params = p_option.params;
			}
		}
		$.each(v_params, function(key, val){
			formData.append(key, val);
		});

		//삭제처리
		var procAjax = $.ajax({
			url: actionUrl,
			type: "POST",
			dataType: "json",
			data: formData,
			processData: false,
			contentType: false,
			//async: false, //동기화처리
			cache: false,
			success: function(data, textStatus, jqXHR) {
				$("#ajax").remove();
				hideLoadingMask();
				if (jqXHR.status == 204) {
					console.log("ajax 204");
					location.href = "/common/sessionErrorForward.jsp";
				}

				if(data.message == null) {
					if(isSuccessMessage != false)
						Message.alertSuccess(g_suDel, function(){
							if(callbackFunction)
								callbackFunction(data);
						});
					else
						if(callbackFunction)
							callbackFunction(data);
				} else {
					Message.alertError(g_failDel+"(" + data.message + ")");
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				if (jqXHR.status == 204) {
					console.log("ajax 204");
					location.href = "/common/sessionErrorForward.jsp";
				}
				document.write(jqXHR.responseText);
			},
			complete: function() {
				if(typeof isLodingMask == "undefined" || isLodingMask != false) {
					hideLoadingMask(); //로딩마감
				}
			}
		});
	}

	var msg = g_del;
	if (p_option && fnToString(p_option.confirmMsg) != "" ) {
		msg = p_option.confirmMsg;
	}
	Message.confirm(msg, function(isOk){
		if(isOk) doing();
	});

}

/**
 * 유효일자에 따른 상세내역 표시
 * HsAdminCodeMng.jsp
 * HsSystemPermMng.jsp
 * @param element
 * @param idPrefix
 * @param cnt
 * @returns
 */
function fnChangeEffdt(element, idPrefix, cnt){
	for(var i=1; i<=cnt; i++){
		if(element.value == i){
			document.all[idPrefix + i].style.display = "";
		} else {
			document.all[idPrefix + i].style.display = "none";
		}
	}
}

/**
 * 유효일자에 따른 상세내역 모두보기 표시
 * HsAdminCodeMng.jsp
 * HsSystemPermMng.jsp
 * @param div
 * @param cnt
 * @returns
 */
function fnShowAllDetailList(div, cnt){
	for(var i=1; i<=cnt; i++){
		document.all[div + i].style.display = "";
	}
}

/** hidden타입의 input값 change 감지
 * selector - hidden타입의 id값
 * callback - 후처리 함수
 * */
function fnChangeHiddenInput(selector, callback){
	var v_input = $(selector);
	var oldvalue = v_input.val();
	setInterval(function(){
		if( v_input.val() != oldvalue ){
			oldvalue = v_input.val();
			callback();
		}
	}, 100)
}

/**
 * 페이지 기본 설정 셋팅
 * @returns
 */
function fnInitialization(p_formId){

	var $document = $(document);
	if(isEmpty(p_formId) || $.trim(p_formId) == ''){
		p_formId = "";
	}else{
		p_formId = $.trim(p_formId)+" ";
		if(!p_formId.startsWith("#")) p_formId = "#"+p_formId;
		$document = $(p_formId);
	}

	fnMakeCalendar(p_formId);    // 달력 생성

	if(g_fixCheckboxYnValue){
		//console.log("체크박스 값 Y/N 고정 적용");
		//체크박스에 값 Y/N 자동설정
		//최초로드 시 값설정이 없을경우는 N으로 설정처리
		$(p_formId+"input:checkbox[value!='Y']").val("N");

		//초기값 설정 : $("#orgPrintFlag").val(data.item.orgPrintFlag).trigger("initCheck");
		//N값은 null로 넘어가므로 VO에서 N으로 초기값 설정 필요 : private String orgPrintFlag = "N";
		$(p_formId+"input:checkbox").bind('initCheck', function(event) {
			//console.log("체크박스 값 변경 initCheck 이벤트 ");
			if($(this).hasClass("orignValue")) {
				//Y,N이 아닌 기존 값으로 설정하기 위해서 class='orignValue' 설정
			} else {
				if($(this).val() == "Y") {
					$(this).prop("checked", true);
				} else {
					$(this).val("N");
					$(this).prop("checked", false);
				}
			}
		});
		$(p_formId+"input:checkbox").change(function() {
			//console.log("체크박스 값 변경 change 이벤트 ");
			if($(this).hasClass("orignValue")) {
				//Y,N이 아닌 기존 값으로 설정하기 위해서 class='orignValue' 설정
			} else {
				if($(this).is(":checked"))
					$(this).val("Y");
				else
					$(this).val("N");
			}
		});
	}

	//유효성체크 메세지 변경
	if($.validator)
		$.validator.messages.required = g_errReq;

	var frmPrj = $("#projectFromId");

	//프롬프트 처리 초기화
	$("#pwModal,#pwModal_sm,#pwModal_search").off('show.bs.modal');
	$("#pwModal,#pwModal_sm,#pwModal_search").on('show.bs.modal', showBsModal);



	//숫자만 numberOnly='true'
	$document.on("keyup"   , "input:text[data-numberOnly]", function() { $(this).val( $(this).val().replace(/[^0-9]/gi,"") ); });
	$document.on("click"   , "input:text[data-numberOnly]", function() { $(this).val( $(this).val().replace(/[^0-9]/gi,"") ); });
	$document.on("focusout", "input:text[data-numberOnly]", function() { $(this).val( $(this).val().replace(/[^0-9]/gi,"") ); });

	//숫자 +(-/.) amountOnly='true'
	$document.on("keyup"   , "input:text[data-amountOnly]", function() { $(this).val( fnFormatCurrency($(this).val().replace(/[^-\.[0-9]/gi,"")) ); });
	$document.on("click"   , "input:text[data-amountOnly]", function() { $(this).val( fnFormatCurrency($(this).val().replace(/[^-\.[0-9]/gi,"")) ); });
	$document.on("focusout", "input:text[data-amountOnly]", function() { $(this).val( fnFormatCurrency($(this).val().replace(/[^-\.[0-9]/gi,"")) ); });

	//data-amountOnly1 fnNumberFormat 함수 적용(ktw 2024-04-01) - 1. 숫자만 입력, 마이너스 허용, 소수점 허용, 콤마 처리) : -2,003.30
	$document.on("keyup"   , "input:text[data-amountOnly1]", function() { $(this).val( fnNumberFormat(this.value, true, true, true) ); });
	$document.on("click"   , "input:text[data-amountOnly1]", function() { $(this).val( fnNumberFormat(this.value, true, true, true) ); });
	$document.on("focusout", "input:text[data-amountOnly1]", function() { $(this).val( fnNumberFormat(this.value, true, true, true) ); });

	//data-amountOnly2 fnNumberFormat 함수 적용(ktw 2024-04-01) - 2. 숫자만 입력, 마이너스 허용, 소수점 불가, 콤마 처리 : -2,003
	$document.on("keyup"   , "input:text[data-amountOnly2]", function() { $(this).val( fnNumberFormat(this.value, true, false, true) ); });
	$document.on("click"   , "input:text[data-amountOnly2]", function() { $(this).val( fnNumberFormat(this.value, true, false, true) ); });
	$document.on("focusout", "input:text[data-amountOnly2]", function() { $(this).val( fnNumberFormat(this.value, true, false, true) ); });

	//data-amountOnly3 fnNumberFormat 함수 적용(ktw 2024-04-01) - 3. 숫자만 입력, 마이너스 불가, 소수점 불가, 콤마 처리 : 2,003
	$document.on("keyup"   , "input:text[data-amountOnly3]", function() { $(this).val( fnNumberFormat(this.value, false, false, true) ); });
	$document.on("click"   , "input:text[data-amountOnly3]", function() { $(this).val( fnNumberFormat(this.value, false, false, true) ); });
	$document.on("focusout", "input:text[data-amountOnly3]", function() { $(this).val( fnNumberFormat(this.value, false, false, true) ); });

	//data-amountOnly4 fnNumberFormat 함수 적용(ktw 2024-04-01) - 4. 숫자만 입력, 마이너스 허용, 소수점 불가, 콤마 처리 안함 : -2003
	$document.on("keyup"   , "input:text[data-amountOnly4]", function() { $(this).val( fnNumberFormat(this.value, true, false, false) ); });
	$document.on("click"   , "input:text[data-amountOnly4]", function() { $(this).val( fnNumberFormat(this.value, true, false, false) ); });
	$document.on("focusout", "input:text[data-amountOnly4]", function() { $(this).val( fnNumberFormat(this.value, true, false, false) ); });

	//영문대문자만 engUpperOnly='true'
	$document.on("keyup", "input:text[data-engUpperOnly]", function() { $(this).val( $(this).val().toUpperCase() ); });

	//영문만 engOnly='true'
	$document.on("keyup", "input:text[data-engOnly]", function() {$(this).val( $(this).val().replace(/[0-9]|[^\!-z]/gi,"") );});

	//영문 + 숫자 engNumberOnly='true'
	$document.on("keyup", "input:text[data-engNumberOnly]", function() {$(this).val( $(this).val().replace(/[^A-Za-z0-9]/gi,"") );});

	//영문 + 숫자 + 하이픈 + 언더바 engNumberStrOnly='true'
	$document.on("keyup", "input:text[data-engNumberStrOnly]", function() {$(this).val( $(this).val().replace(/[^A-Za-z0-9-_]/gi,"") );});

	//영문 + 띄어쓰기 engSpaceOnly='true'
	$document.on("keyup", "input:text[data-engSpaceOnly]", function() {$(this).val( $(this).val().replace(/[0-9]|[^\!-z\s]/gi,"") );});

	//한글만korOnly='true' -> 테스트결과 IE에서 안먹을 때가 종종있음.
//	$document.on("keyup", "input:text[data-korOnly]", function() {$(this).val( $(this).val().replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"\\]/g,"") );});
	$document.on("keyup", "input:text[data-korOnly]", function() {$(this).val( $(this).val().replace(/([^가-힣ㄱ-ㅎㅏ-ㅣ\x20])/i,"") );});

	$document.on("keyup", "input:text[data-engSpaceNumberOnly]", function() {$(this).val( $(this).val().replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '') );});
	//주민등록번호 nationalIdOnly='true'
	//$("input:text[nationalIdOnly]").mask("999999-9999999",{placeholder:""});
	// 영문,숫자,특수문자( ._- )만 허용 - 프로그램관리(프로그램번호),메뉴관리(메뉴번호) - 2021-10-06 ktw
	$document.on("keyup", "input:text[data-menuNumOnly]", function() {$(this).val( $(this).val().replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|\[\]{}()<>?|`~!@#$%^&*+=,;:/\"\\]/g, '') );});
	// 영문,숫자,특수문자 전체 허용 - 프로그램관리(프로그램번호),메뉴관리(메뉴번호) - 2021-12-31 조은성
	$document.on("keyup", "input:text[data-engNumCharOnly]", function() {$(this).val( $(this).val().replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '') );});

	$document.on("click", "button[data-uploadRunGroup]", function() {
		fnNewTab(null, "/hs/system/dataLoadExec/HsSystemDataLoadExec.do", {runGroup: $(this).attr("data-uploadRunGroup"), hdnMenuNo: "374"});
	});

	$document.on("change", "input:text[data-endDt]", function() {
		var compareObj = $("#"+$(this).attr("data-endDt"));
		if(isNotEmpty($(this).val()) && isNotEmpty(compareObj.val())){
			if($(this).val() > $(compareObj).val()){
				Message.alert("시작일자는 종료일자보다 이후로 설정 할 수 없습니다.");
				$(this).val('');
			}
		}
	});

	$document.on("change", "input:text[data-startDt]", function() {
		var compareObj = $("#"+$(this).attr("data-startDt"));
		if(isNotEmpty($(this).val()) && isNotEmpty(compareObj.val())){
			if($(this).val() < $(compareObj).val()){
				Message.alert("종료일자는 시작일자보다 이전으로 설정 할 수 없습니다.");
				$(this).val('');
			}
		}
	});

	/*
	//기타 초기스크립트 처리
	$(window).resize(function() {
		$('div.content').css('width',($(this).width() * .100));
	});
	*/
	//메세지 알림
	var msg = document.getElementById("initMessage");
	if(msg && msg.value.length > 0) {
		Message.alert(msg.value);
		msg.value = "";
	}

	//검색영역 엔터이벤트 적용
	//$("div [searchEvent] :input").keydown(function(key) {
	$document.on( "keydown", "div [data-searchEvent] :input[type=text]", function(key) {
		try {
			if(key.keyCode == 13) {
				var cEvent = $(this).closest("div [data-searchEvent]");
				if(cEvent != null) {
					var fnc = cEvent.attr("data-searchEvent");
					eval(fnc);
					return false;
				}
			}
		} catch(err) {}
	});

	//input 박스에서 엔터키 포커스 이동
	/*
	var inputs = $(':input').keypress(function(e){
		if (e.which == 13) {
		   e.preventDefault();
		   var nextInput = inputs.get(inputs.index(this) + 1);
		   if (nextInput) {
			  nextInput.focus();
		   }
		}
	});
	$("input:text").keydown(function(key) {
		try {
			if(key.keyCode == 13) {
				return false;
			}
		} catch(err) {}
	});
	*/

	/*
	$(".box-search :input").keydown(function(key) {
		try {
			if(key.keyCode == 13) {
				var cEvent = $(".box-search").attr("searchEvent");
				if(cEvent != "") {
					eval(cEvent);
					return false;
				}
			}
		} catch(err) {}
	});
	*/

	//모바일에서 더블클릭 이벤트 먹이기
	jQuery.event.special.dblclick = {
		setup: function(data, namespaces) {
			var agent = navigator.userAgent.toLowerCase();
			if (agent.indexOf('iphone') >= 0 || agent.indexOf('ipad') >= 0 || agent.indexOf('ipod') >= 0) {
				var elem = this,
					$elem = jQuery(elem);
				$elem.bind('touchend.dblclick', jQuery.event.special.dblclick.handler);
			} else {
				var elem = this,
					$elem = jQuery(elem);
				$elem.bind('click.dblclick', jQuery.event.special.dblclick.handler);
			}
		},
		teardown: function(namespaces) {
			var agent = navigator.userAgent.toLowerCase();
			if (agent.indexOf('iphone') >= 0 || agent.indexOf('ipad') >= 0 || agent.indexOf('ipod') >= 0) {
				var elem = this,
					$elem = jQuery(elem);
				$elem.unbind('touchend.dblclick');
			} else {
				var elem = this,
					$elem = jQuery(elem);
				$elem.unbind('click.dblclick', jQuery.event.special.dblclick.handler);
			}
		},
		handler: function(event) {
			var elem = event.target,
				$elem = jQuery(elem),
				lastTouch = $elem.data('lastTouch') || 0,
				now = new Date().getTime();
			var delta = now - lastTouch;
			if (delta > 20 && delta < 500) {
				$elem.data('lastTouch', 0);
				$elem.trigger('dblclick');
			} else {
				$elem.data('lastTouch', now);
			}
		}
	};

	//관리자모드일 경우 스타일지정
	var curUrl = location.href;
	if(curUrl.indexOf("/admin/") > -1 || curUrl.indexOf("/system/") > -1) {
		$(".wrapper.wrapper-content").addClass("pwadmin"); //관리자 모드 css
	}

	//Page 초기화설정--------------------------------------------------------------------------------------
	if(frmPrj) {
		// input 초기화버튼 display 처리
		$("#" + frmPrj.val()).find(".btn_esc").each(function(){
			var btnEscObj = $(this);
			if( $("#" + $(this).attr("data-id")).val() != "" ){
				btnEscObj.show();
			}else{
				btnEscObj.hide();
			}

			// hidden값일 때 change이벤트를 감지하지 못하여 change이벤트 대용으로 만든 function
			fnChangeHiddenInput($("#" + btnEscObj.attr("data-id")), function(){
				if( $("#" +btnEscObj.attr("data-id")).val() != "" || $("#" +btnEscObj.attr("data-id")).val() != "" ){
					btnEscObj.show();
				}else{
					btnEscObj.hide();
				}
			});
		});
		// input 초기화버튼 클릭 처리
		$(".btn_esc").unbind("click").bind("click", function() {
			var inputId = $(this).attr("data-id");
			var inputNm = $(this).attr("data-nm");
			if( $("#" + inputId).val() != "" || $("#" + inputNm).val() ){
				$("#" + inputId).val("");
				$("#" + inputNm).val("");
			}
		});

        $("a[id^='btn_esc']").unbind("click").bind("click", function() {
        	$(this).prevAll('input[type="text"]').val('');
    		$(this).prevAll('input[type="hidden"]').val('');   // 이전 hidden 값 초기화
        });

        $("span[class^='btn_esc']").unbind("click").bind("click", function() {debugger;
	        $(this).prevAll('input[type="text"]').val('');
	        $(this).prevAll('input[type="hidden"]').val('');   // 이전 hidden 값 초기화
        });

		// 팝업 닫기처리
		$(".layout.btn_close").unbind("click").bind("click", function() {
			fnPromptClose();
		});
		//SERVICE_ID 프롬프트 처리
		$("#searchServiceIdPrompt").click(function() {
			fnPopServiceId($(this));
		});
		$("#serviceIdPrompt").click(function() {
			fnPopServiceId($(this));
		});
		$('a[data-action="serviceId"]').click(function() {
			fnPopServiceId($(this));
		});

		//BUSINESSUNIT 프롬프트 처리
		$("#searchBusinessUnitPrompt").click(function() {
			fnPopBusinessUnit($(this));
		});
		$("#businessUnitPrompt").click(function() {
			fnPopBusinessUnit($(this));
		});
		$('a[data-action="businessUnit"]').click(function() {
			fnPopBusinessUnit($(this));
		});

		//사원검색 프롬프트 처리
		$("#btnEmpPromptSearch").unbind("click").bind("click", function() {
			fnEmpSearch($(this));
		});
		$("#empPrompt, .empPrompt").unbind("click").bind("click", function() {
			fnEmpInfoPrompt($(this));
		});
		$('a[data-action="empId"]').click(function() {
			fnPopEmpId($(this));
		});
//		$("#empPrompt").click(function() {
//			fnPopEmpId($(this));
//		});
//		$("#empPrompt1").click(function() {
//			fnPopEmpId($(this));
//		});
//		$("#empPrompt2").click(function() {
//			fnPopEmpId($(this));
//		});
//		$("#empPrompt3").click(function() {
//			fnPopEmpId($(this));
//		});
//		$("#empPrompt4").click(function() {
//			fnPopEmpId($(this));
//		});

		//사원검색 프롬프트 처리(강사용)
		$("#tutorEmpPrompt").click(function() {
			fnPopTutorEmpid($(this));
		});
		//강사검색 프롬프트 처리
		$("#tutorPrompt").click(function() {
			fnPopTutorId($(this));
		});

		//조직검색 프롬프트 처리
		/*
		$("#deptPrompt").click(function() {
			fnPopDept($(this));
		});
		$("#deptPrompt2").click(function() {
			fnPopDept($(this));
		});
		$("#deptPrompt3").click(function() {
			fnPopDept($(this));
		});
		$("#deptPromptSarch").click(function() {
			fnPopDept($(this));
		});*/

		//직무검색 프롬프트 처리
		$("#jobCdPrompt").click(function() {
			fnPopJobCd($(this));
		});

		//초기값 설정
		try {

			if($("#searchServiceId")) {
				if($("#searchServiceId").val() == "") {
					if($("#searchServiceId").hasClass("select2-hidden-accessible")){
						fnSetValue("SELECT2_AFTER", $("#searchServiceId"), g_defaultServiceId);
					}else{
						$("#searchServiceId").val(g_defaultServiceId);
					}
				}
			}

			if($("#serviceId")) {
				if($("#serviceId").val() == "") {
					if($("#serviceId").hasClass("select2-hidden-accessible")){
						fnSetValue("SELECT2_AFTER", $("#serviceId"), g_defaultServiceId);
					}else{
						$("#serviceId").val(g_defaultServiceId);
					}
				}
			}

			if($("#searchCompanyId")) {
				if($("#searchCompanyId").val() == "") {
					if($("#searchCompanyId").hasClass("select2-hidden-accessible")){
						fnSetValue("SELECT2_AFTER", $("#searchCompanyId"), g_defaultCompanyId);
					}else{
						$("#searchCompanyId").val(g_defaultCompanyId);
					}
				}
			}

			if($("#companyId")) {
				if($("#companyId").val() == "") {
					if($("#companyId").hasClass("select2-hidden-accessible")){
						fnSetValue("SELECT2_AFTER", $("#companyId"), g_defaultCompanyId);
					}else{
						$("#companyId").val(g_defaultCompanyId);
					}
				}
			}

			if($("#mainCompanyId")) {
				if($("#mainCompanyId").val() == "") {
					if($("#mainCompanyId").hasClass("select2-hidden-accessible")){
						fnSetValue("SELECT2_AFTER", $("#mainCompanyId"), g_defaultCompanyId);
					}else{
						$("#mainCompanyId").val(g_defaultCompanyId);
					}
				}
			}

			if($("#effdt")) {
				if($("#effdt").val() == "") {
					fnSetValue("DATE", $("#effdt"), fnGetToday());
				}
			}
		} catch(err) {
			//Message.alertError(err.message);
		}
	}

	//공통검색영역 초기화설정--------------------------------------------------------------------------------------
	//공통검색영역 뷰잉 처리
	$('#btnPwCommonSearch').click(function () {
		var schArea = $("#divPwCommonSearch");
		var conArea = $("#divPwContents");

		schArea.toggleClass("col-lg-0");
		schArea.toggleClass("col-lg-3");
 		schArea.toggleClass("animated fadeInLeft");
 		conArea.toggleClass("col-lg-12");
 		conArea.toggleClass("col-lg-9");
 		conArea.toggleClass("animated fadeInRight");
 		conArea.toggleClass("animated fadeInLeft");

 		if(schArea.hasClass('col-lg-3')) {
 			schArea.show();
 			$("#selPwCommonSearchPosition").select2();
 			$("#selPwCommonSearchDuty").select2();
 		} else {
			schArea.hide();
		}
	});

	//트리 전체열기, 전체닫기처리(tree)
	$('a[data-toggle="tree"]').click(function(){
		var v_SelTreeId = $(this).attr('data-target');
		var v_TreeAction = $(this).attr('data-action');
		if(v_TreeAction == "open") {
			$(v_SelTreeId).jstree("open_all");
		}
		else {
			$(v_SelTreeId).jstree("close_all");
		}
	});

	//트리 전체열기, 전체닫기처리(orgChart)
	$('a[data-toggle="orgChart"]').click(function(){
		var v_SelTreeId = $(this).attr('data-target');
		var v_TreeAction = $(this).attr('data-action');
		if(v_TreeAction == "open")
			$(v_SelTreeId).viewAll();
		else
			$(v_SelTreeId).viewSubTree("");
	});

	//ESS Main Page -> HR Admin Main Page로 변경
	$('#adminLink').parent().click(function(e) {
		$('#adminLinkForm').submit();
		return false;
	});
	$('#essLink').parent().click(function(e) {
		$('#essLinkForm').submit();
		return false;
	});

	//버튼권한설정
//	fnSetButtonPerm();
	//console.log("fnInitialization 실행 확인");
	//버튼액션 로그처리
	$document.off("click", "button");//이벤트 취소 - 중복 실행되는거 방지
	$document.on("click", "button", function(e) {
		var v_btnId = fnToString($(this).attr("id"));
		// 버튼id 없을때 return false
		if( v_btnId == '' ){
			return;
		}

		var v_url = location.href;
		//var v_formId = $("#projectFromId").val();
		//var v_form = $("#" + v_formId);
		var v_form = $(this).parents("form");

		if(v_form && typeof v_form.attr("id") !="undefined" ) {
		}else{
			v_form = $("#" + $("#projectFromId").val());
		}

		var v_params = "";
		if(v_form) v_params = v_form.serialize();

		var v_data = {btnId:v_btnId, actionUrl:v_url, params:v_params};
		var v_retBtnData = fnGetJsonData("/hs/system/scrtyMng/HsSystemScrtyMngBtnLogRegist.json", v_data); // HsSystemScrtyMngDAO.insertScrtyBtnLog
	});

	//form일 여러개 존재 할때 전체 폼에 validate을 설정한다.
	$("form").each(function(){
		$(this).validate({
			errorPlacement: function (error, element)
			{
	 			//console.log(element);
				if (element.is(":radio") || element.is(":checkbox") || element.is("select") ) {
					error[0].innerText = "* 필수 입력값입니다.";
					error.css("color", "red");

					if( !element.is("select") ){
						element.parent().append(error);
					}else{
						$(element).find(".requiredPlaceholder").remove();
						element.prepend('<option value="" selected disabled hidden class="requiredPlaceholder">* 필수 입력값입니다.</option>');
//						element.after(error);
					}
				} else {
					element.attr("placeholder", '* 필수 입력값입니다.');
//					element.attr("placeholder", "<spring:message code='pw.zz.errors.required'/>");
				}
			}
		});
	});

//	//form일 여러개 존재 할때 전체 폼에 validate을 설정한다.
//	$("form").each(function(){
//		$(this).validate({
//			errorPlacement: function (error, element)
//			{
//				console.log(element);
//				if (element.is(":radio") || element.is(":checkbox") || element.is("select") ) {
////					element.before(error);
//					element.after(error);
//				} else {
//					element.attr("placeholder", '필수 입력값입니다.');
////					element.attr("placeholder", "<spring:message code='pw.zz.errors.required'/>");
//				}
//			}
//		});
//	});

	// 20210914 조은성 (오픈노트 style JS 추가 - margin, padding, width 등등)
	fnOpennoteJs();
}

/**
 * jquery validate 메세지, placeholder 특정 메세지 초기화
 * @param formId
 * @returns
 */
function fnFormInitValidate(p_formId) {
	var $document = $(document);
	if(isEmpty(p_formId) || $.trim(p_formId) == ''){
		p_formId = "";
	}else{
		p_formId = $.trim(p_formId)+" ";
		if(!p_formId.startsWith("#")) p_formId = "#"+p_formId;
		$document = $(p_formId);
	}

	//placeholder 특정 메세지 초기화
	$(p_formId+"input, textarea").each(function(idx){
		var chk = $(this).attr("placeholder");
		if ( fnToString(chk).indexOf("필수 입력값입니다") > -1 ) {
			$(this).attr("placeholder", "");
		}
	});
	$(p_formId+"select").each(function(idx){
		var id = $(this).attr("id");
		if ( fnToString(id) != "" ) {
			$('#'+ id + ' option').each(function(){
				var text = $(this).text();
				if ( fnToString(text).indexOf("필수 입력값입니다") > -1 ) {
					$(this).remove();
				}
			});
		}
	});
	//$('#'+p_formId).validate().resetForm();
}

/**
 * ID값으로 select2 설정
 * @param p_code
 * @param p_selectId
 * @param p_type
 * @param p_jsonUrl
 * @param p_params
 * @param p_defaultMsg
 * @param p_emptyOption	: required속성 주고도 empty옵션을 주고싶을때
 * @param p_dataOption : option값 만들어질때 data속성도 같이 주고 싶을 때
 * @returns
 */
function fnSetSelectBox(p_code, p_selectId, p_type, p_jsonUrl, p_params, p_defaultMsg, p_defaultValue, p_emptyOption=false, p_dataOption=false) {
	var $obj;
	var v_data ;
	if(typeof p_selectId == 'object'){
		$obj = p_selectId;
	} else {
		$obj = $("#" + p_selectId);
	}

	// 해당 파라미터에 이상한 값 들어올시 반드시 false로 전환
	p_emptyOption = !!p_emptyOption;
	p_dataOption  = !!p_dataOption;

	//input:text 태그가 아닐경우는 select2 사용안함  -> 최종적으로 다 걷어낼 예정
	if($obj.attr("type") == "text") {
		fnSetSelect2(false, p_code, $obj, p_type, p_jsonUrl, p_params, p_defaultMsg);
	} else {

		//selectbox설정
		//데이터 조회
		v_data = fnGetSelectData($obj, p_code, p_type, p_jsonUrl, p_params);

		$obj.find("option").each(function() {
			$(this).remove();
		});

		//selectbox option 구성
		//$obj.append("<option value=''>선택</option>"); //기본값 -> 성능향상을 위한 변경
		var v_arrOption = [];
		if(v_data){
			$.each(v_data, function(i, item) {
				var v_value = "";
				var v_data	= "";
				var v_text	= "";
				if( p_dataOption ) {	// id와 text제외 나머지 필드는 data속성으로 정의
					$.each(item, function(key, value){
						if( key === "id" ) {
							v_value = value;
						} else if( key === "text" ) {
							v_text = value;
						} else {
							v_data += " data-" + key + "='" + value + "'";
						}
					});
					v_arrOption[i] = "<option value='" + v_value + "'" + v_data + ">" + v_text + "</option>";

				} else {
					v_arrOption[i] = "<option value='" + item.id + "'>" + item.text + "</option>";
				}
			});

			$obj.html(v_arrOption.join(''));
			if(!$obj.hasClass("required") || p_emptyOption){	// 빈값옵션이 true일때만
				$obj.prepend("<option value=''>"+g_ibSelect+"</option>");
				$obj.val("");
			}
			$.each(v_data, function(i, item) {//2023-08-22 기본으로 사용자의 회사를 디폴트로 선택하게 함
				if ( fnToString(p_code) == "COMPANY_ID" && item.id == g_defaultCompany ) {
					$obj.val(item.id).prop('selected', true);
					return false;//break
				} else if ( fnToString(p_jsonUrl).indexOf("/hs/common/com/HsCommonComCompanyIdSelect2.json") > -1 && item.id == g_defaultCompany ) {
					$obj.val(item.id).prop('selected', true);
					return false;//break
				}
			});
		}

		//초기값 선택 - 필수일경우 처음 값 선택
		if(typeof $obj.attr("data-init") != "undefined" && $obj.attr("data-init") != "") {
			$obj.val($obj.attr("data-init"));
		}

		if(p_defaultValue){
			fnSetValue("SELECT", $obj, p_defaultValue);
		}
	}
	if( typeof fnSetSelectBoxCallback == 'function' ) {
		fnSetSelectBoxCallback(p_code, p_selectId, p_type, p_jsonUrl, p_params, p_defaultMsg); // 필요시 이 함수를 각 화면에서 	재정의하여 사용한다.
	}
	return  v_data; //2018.03.12 리턴데이타 사용시
}

/**
 * CLASS명으로 select2 설정
 * @param p_code
 * @param p_selectClass
 * @param p_type
 * @param p_jsonUrl
 * @param p_params
 * @param p_defaultMsg
 * @returns
 */
function fnSetSelectBoxClass(p_code, p_selectClass, p_type, p_jsonUrl, p_params, p_defaultMsg) {
	var v_obj = $("." + p_selectClass);
	fnSetSelect2(true, p_code, v_obj, p_type, p_jsonUrl, p_params, p_defaultMsg);
}

/**
 * JOSN데이터를 가지고 selectbox 설정
 * @param p_objectId
 * @param p_jsonData
 * @param p_defaultMsg
 * @param p_emptyOption	: required속성 주고도 empty옵션을 주고싶을때
 * @param p_dataOption : option값 만들어질때 data속성도 같이 주고 싶을 때(쿼리에서 필드명 data_type으로 지정 필수)
 * @returns
 */
function fnSetSelectBoxData(p_objectId, p_jsonData, p_defaultVal, p_emptyOption=false, p_dataOption=false) {
	var $obj;
	if(typeof p_objectId == 'object'){
		$obj = p_objectId;
	} else {
		$obj = $("#" + p_objectId);
	}

	// 해당 파라미터에 이상한 값 들어올시 반드시 false로 전환
	p_emptyOption = !!p_emptyOption;
	p_dataOption  = !!p_dataOption;

	//selectbox option 구성
	var v_arrOption = [];
	if(p_jsonData){
		$.each(p_jsonData, function(i, item) {
			if( p_dataOption ) {
				v_arrOption[i] = "<option value='" + item.id + "' data-type='" + item.dataType + "' >" + item.text + "</option>";
			} else {
				v_arrOption[i] = "<option value='" + item.id + "'>" + item.text + "</option>";
			}
		});
	}
	$obj.html(v_arrOption.join(''));
	if(!$obj.hasClass("required") || p_emptyOption){
		$obj.prepend("<option value=''>"+g_ibSelect+"</option>");
		$obj.val("");
	}

	//초기값 선택 - 필수일경우 처음 값 선택
	if(p_defaultVal || typeof $obj.attr("data-init") != "undefined" && $obj.attr("data-init") != "") {
		$obj.val(p_defaultVal ? p_defaultVal : $obj.attr("data-init"));
	}
}


//팝업, 레이어 파라미터전달용 전역변수
var G_TXTID, G_TXTDESCR, G_COMPANY_ID, G_EFFDT, G_EFFENDDT, G_EMPID, G_EMPNO, G_NAME;
var G_DEPT, G_JOB_POSITION, G_MODAL_ID, G_PAGETYPE, G_BUSINESSUNIT, G_HIRE_DT, G_JOBCLSLVL, G_BIRTHDATE;
var G_SEARCH_ALL_YN, G_CHANGE_COMPANY_YN, G_OPTIONS, G_CALLBACK;
var G_APPR_TYPE, G_APPR_ID, G_APPR_TITLE, G_APPR_MEMO, G_APPR_MODIFY_YN, G_APPR_MODIFY_DT_YN;
var G_COURSEEFFDT, G_COURSETRAININGCODE, G_COURSETRAININGNAME;
var G_TRAININGFLAG, G_TRAININGTYPE, G_TRAININGTYPENM, G_TRAININGCODE, G_TRAININGNAME;
var G_REAL_GRID_VIEW_ID, G_REAL_GRID_DATA_PROVIDER, G_REAL_GRID_COLUMN_NM; //리얼그리드 값전달용
var G_CLASS_CODE_ID;
var G_IBSHEET_OBJ, G_IBSHEET_ROW;   // IBsheet object 전달용

var G_PROMPT_OBJ_EMP_SEARCH_INPUT, G_PROMPT_OBJ_EMP_ID, G_PROMPT_OBJ_EMP_NO, G_PROMPT_OBJ_EMP_NM, G_PROMPT_OBJ, G_HR_STATUS;	// 사원검색용 변수
var G_PROMPT_OBJ_DEPT_NM;
var G_PROMPT_OBJ_COSTCT, G_PROMPT_OBJ_COSTCT_NM; //코스트센터 검색용 변수

/**
 * 팝업창 데이터 초기화
 * @returns
 */
function fnInitPromptGlobal() {
	G_TXTID = "";
	G_TXTDESCR = "";
	G_COMPANY_ID = "";
	G_EFFDT = "";
	G_EFFENDDT = "";
	G_EMPID = "";
	G_EMPNO = "";
	G_NAME = "";

	G_DEPT = "";
	G_JOB_POSITION = "";
	G_MODAL_ID = "";
	G_PAGETYPE = "";
	G_BUSINESSUNIT = "";
	G_HIRE_DT = "";
	G_JOBCLSLVL = "";
	G_BIRTHDATE = "";

	G_SEARCH_ALL_YN = "";
	G_CHANGE_COMPANY_YN = "";
	G_OPTIONS = "";
	G_CALLBACK = "";

	G_APPR_TYPE = "";
	G_APPR_ID = "";
	G_APPR_TITLE = "";
	G_APPR_MEMO = "";
	G_APPR_MODIFY_YN = "";
	G_APPR_MODIFY_DT_YN = "";

	G_COURSEEFFDT = "";
	G_COURSETRAININGCODE = "";
	G_COURSETRAININGNAME = "";

	G_TRAININGFLAG = "";
	G_TRAININGTYPE = "";
	G_TRAININGTYPENM = "";
	G_TRAININGCODE = "";
	G_TRAININGNAME = "";

	//리얼그리드 값전달용
	G_REAL_GRID_VIEW_ID = "";
	G_REAL_GRID_DATA_PROVIDER = "";
	G_REAL_GRID_COLUMN_NM = "";

	G_CLASS_CODE_ID = "";

	//IBsheet 값전달용
	G_IBSHEET_OBJ = "";
	G_IBSHEET_ROW = "";

	// 사원검색용 변수 초기화
	G_PROMPT_OBJ_EMP_SEARCH_INPUT = "";
	G_PROMPT_OBJ_EMP_ID = "";
	G_PROMPT_OBJ_EMP_NO = "";
	G_PROMPT_OBJ_EMP_NM = "";
	G_HR_STATUS = "";

	// 코스트센터용 변수 초기화
	G_PROMPT_OBJ_COSTCT = "";
	G_PROMPT_OBJ_COSTCT_NM = "";

	// 부서노출용 변수
	G_PROMPT_OBJ_DEPT_NM = "";
}

/**
 * schObj에 attr 로 설정된 data를 팝업창에 설정
 * @param schObj 값 전달용 오브젝트
 * @returns
 */
function fnSetPromptGlobal(schObj) {
	G_TXTID = schObj.attr('data-id');
	//그리드에서 처리되는 경우
	if(G_TXTID == null) {
		G_TXTID = schObj.attr("id");
	}
	G_TXTDESCR = schObj.attr('data-nm');
	//오브젝트 또는 값으로 넘어왔을 경우에 대한 분기처리
	if($("#"+schObj.attr('data-companyId')).length > 0){
		G_COMPANY_ID = $("#"+schObj.attr('data-companyId')).val();
	}else{
		G_COMPANY_ID = schObj.attr('data-companyId');
	}
	G_EFFDT = schObj.attr('data-effDt');
	G_EFFENDDT = schObj.attr('data-effEndDt');
	G_EMPID = schObj.attr('data-empid');
	G_EMPNO = schObj.attr('data-empno');
	G_NAME = schObj.attr('data-name');

	// 주소 전달용(필요시 추가)
	G_POST_NO = schObj.attr('data-postNo');
	G_ADDR1 = schObj.attr('data-addr1');

	G_DEPT = schObj.attr('data-dept');
	G_JOB_POSITION = schObj.attr('data-jobPosition');
	//사원 이름 조회 팝업 사용시  pageType 수정가능 불가
	G_PAGETYPE = schObj.attr('data-pageType');
	G_BUSINESSUNIT = schObj.attr('data-businessUnit');
	G_HIRE_DT = schObj.attr('data-hireDt');
	G_JOBCLSLVL = schObj.attr('data-jobclsLvl');
	G_BIRTHDATE = schObj.attr('data-birthdate');

	G_SEARCH_ALL_YN = schObj.attr('data-searchAllYn');
	//조직트리 회사선택 YN
	G_CHANGE_COMPANY_YN = schObj.attr('data-changeCompanyYn');
	G_OPTIONS = schObj.attr('data-options');
	G_CALLBACK = schObj.attr('data-callback');

	G_APPR_TYPE = schObj.attr('data-apprType');
	G_APPR_ID = schObj.attr('data-apprId');
	G_APPR_TITLE = schObj.attr('data-apprTitle');
	G_APPR_MEMO = schObj.attr('data-apprMemo');
	G_APPR_MODIFY_YN = schObj.attr('data-apprModiYn');
	G_APPR_MODIFY_DT_YN = schObj.attr('data-apprModiDtYn');

	G_COURSEEFFDT = schObj.attr('data-courseEffdt');
	G_COURSETRAININGCODE = schObj.attr('data-courseTrainingCode');
	G_COURSETRAININGNAME = schObj.attr('data-courseTrainingName');

	G_TRAININGFLAG = schObj.attr('data-trainingFlag');
	G_TRAININGTYPE = schObj.attr('data-trainingType');
	G_TRAININGTYPENM = schObj.attr('data-trainingTypeNm');
	G_TRAININGCODE = schObj.attr('data-trainingCode');
	G_TRAININGNAME = schObj.attr('data-trainingName');

	//리얼그리드 값전달용
	G_REAL_GRID_VIEW_ID = schObj.attr('data-realGridViewId');
	G_REAL_GRID_DATA_PROVIDER = schObj.attr('data-realGridDataProvider');
	G_REAL_GRID_COLUMN_NM = schObj.attr('data-realGridColumnNm');

	//코드 class id
	G_CLASS_CODE_ID = schObj.attr('data-classCodeId');

	//IBsheet 값전달용
	G_IBSHEET_OBJ = schObj.attr('data-sheetObj');
	G_IBSHEET_ROW = schObj.attr('data-sheetRowId');

	// 사원검색용 변수 초기화
	G_PROMPT_OBJ_EMP_SEARCH_INPUT = schObj.attr("data-searchInput");
	G_PROMPT_OBJ_EMP_ID = schObj.attr("data-empId");
	G_PROMPT_OBJ_EMP_NO = schObj.attr("data-empNo");
	G_PROMPT_OBJ_EMP_NM = schObj.attr("data-empNm");
	G_HR_STATUS = schObj.attr("data-hrStatus");

	//코스트센터 검색용
	G_PROMPT_OBJ_COSTCT = schObj.attr("data-costct");
	G_PROMPT_OBJ_COSTCT_NM = schObj.attr("data-costctNm");

	//부서노출용 변수
	G_PROMPT_OBJ_DEPT_NM = schObj.attr("data-deptNm");
}

/**
 * 레이어팝업 크기설정
 * @param v_modal
 * @param width
 * @param height
 * @returns
 */
function fnSetPromptSize(v_modal, width, height) {
	if(width == 0 && height == 0) {
		v_modal.removeAttr("style");
	} else {
		if(width > 0 && height > 0) {
			v_modal.attr("style","width:" + width + "px;");
			v_modal.find("div.modal-content").attr("style","height:" + height + "px;");
		} else {
			if(width > 0) v_modal.attr("style","width:" + width + "px;");
			if(height > 0) v_modal.find("div.modal-content").attr("style","height:" + height + "px;");
		}
	}
}

/**
 * 타입별 초기값 설정
 * <br>ex) fnSetValue("TEXT", $("#"), "A", false);
 * @param p_type
 * <br>	= SELECT2 : 프롬프트용 자동완성 및 셀렉트 박스 생성 전에 값 처리
 * <br>	= SELECT2_AFTER : 프롬프트용 자동완성 및 셀렉트 박스 생성 후에 값 변경
 * <br>	= DATE : 달력팝업 된 날짜 입력 값 처리
 * <br>	= 그외 : TEXT, 일반 SELECT, CHECKBOX
 * @param p_obj  적용할 input or select 태그 obj
 * @param p_value 설정할 값
 * @param p_isReadOnly 활성/비활성 처리
 * @param p_format p_type == "DATE" 일때 날짜 포맷
 * @returns
 */
function fnSetValue(p_type, p_obj, p_value, p_isReadOnly, p_format) {
	switch(p_type) {
		case "SELECT2" : //프롬프트용 select2(처리 후 프롬프트 생성 함수 호출 - fnSetSelectBox)

			// 공백제거
			p_value = $.trim(p_value);

			p_obj.attr("data-init", p_value);
			p_obj.val(p_value);

			//var v_select2Obj = $("#select2-" + p_obj.attr("id") + "-container");
			//if(v_select2Obj) p_obj.select2("val", p_value);

			if(typeof p_isReadOnly != "undefined") {
				if(p_isReadOnly) p_obj.prop("disabled", true);
				else  p_obj.prop("disabled", false);
			}
			break;
		case "SELECT2_AFTER" : //프롬프트용 select2 생성 후 값 변경 시(SELECT2로 한방에 하고 싶지만 잘 안됨..)
			if(p_obj.val() != p_value) {
				p_obj.val("");
				p_obj.attr("data-init", "");

				p_obj.val(p_value);
				p_obj.attr("data-init", p_value);
				try{
					if(p_obj.hasClass("select2-hidden-accessible")) {
						p_obj.select2("val", "");
						p_obj.select2("val", p_value);
					}
				} catch(ex) {}
			}

			if(typeof p_isReadOnly != "undefined") {
				if(p_isReadOnly) p_obj.prop("disabled", true);
				else  p_obj.prop("disabled", false);
			}
			break;
		case "DATE" : //달력팝업용 input:text
			if(p_format) // format우선적용, null일 경우 script오류 방지를 위해 new Date
				p_obj.val(new Date(p_value).format(p_format).trim());
			else if(p_value)
				p_obj.val(p_value);

			/*if(typeof p_isReadOnly != "undefined") {
				if(p_isReadOnly) {
					p_obj.attr("readonly", true);
					p_obj.datepicker('remove');
				} else {
					p_obj.attr("readonly", false);
					fnSetDatepicker(p_obj);
				}
			}*/
			break;
		case "CHECK" : //input:checkbox
			p_obj.val(p_value).trigger("initCheck");

			if(typeof p_isReadOnly != "undefined") {
				//체크박스는 disable로 처리되는데 그러면 값이 안넘어감으로 아래와 같이 처리-> 잘안되므로 disalbe처리 ㅠㅠ 나중에 값 넘길때 풀어야 함
				if(p_isReadOnly) {
					p_obj.attr("disabled", true);
					//p_obj.attr("style","background-color:#eee;opacity:1;");
					//p_obj.attr("onclick","this.checked=!checked;");
					//p_obj.attr("ondbclick","this.checked=!checked;");
				} else {
					p_obj.attr("disabled", false);
					//p_obj.removeAttr("style");
					//p_obj.removeAttr("onclick");
					//p_obj.removeAttr("ondbclick");
				}
			}
			break;
		default :
			p_obj.val(p_value);

			if(typeof p_isReadOnly != "undefined") {
				if(p_isReadOnly) p_obj.attr("readonly", true);
				else p_obj.attr("readonly", false);
			}
	}
}

/**
 * 다국어 언어선택
 * @param p_langCd
 * @returns
 */
function fnSetLangCd(p_langCd){
	var v_formId = $("#projectFromId").val();
	var v_form = $("#" + v_formId);
	var v_frm = document.getElementById(v_formId);
	var v_formNavi = $("#" + v_formId + " [name=naviUrl]");
	var v_formPageMode = $("#" + v_formId + " [name=pageMode]");

	if(v_form && typeof v_formId != "undefined"){
		var v_url = location.href;

		// 네비게이션 url
		if(v_formNavi && typeof v_formNavi.val() != "undefined") {
			v_url = v_url + "&naviUrl=" + v_formNavi.val();
		}

		var v_params = v_form.serializeArray();
		v_params.push({ name: "langCd" , value:p_langCd});
		v_params.push({ name: "langCdCh" , value:"Y"}); //언어변환 감지를위해 추가
		fnPostMoveForName(v_url, v_params);

	} else {
		Message.alertError("현재페이지에 form에 대한 hidden태그가 없어 다국어 반영이 되지 않습니다. 개발자에 문의하여 수정하여 주세요.");
	}
}

/**
 * 페이지별 버튼 권한 설정
 * @returns
 */
function fnSetButtonPerm() {
	var v_curUrl = location.pathname + location.search; //파라미터 포함.

	var v_params = {"programUrl":v_curUrl};
	var v_data = fnGetJsonData("/hs/system/permMng/HsSystemDeniedBtnList.json", v_params); // HsSystemPermMngDAO.selectDeniedBtnList

	//권한제한 버튼 제외처리
	if(v_data.totSize > 0) {
		try {
			$.each(v_data.items, function(i, item) {
				var v_btnId = item.btnId;
				var v_actionUrl = item.actionUrl;

				$("#"+ v_btnId).remove();

				//id로 검색이 아니라 시작글자로 btnSave01, 02등도 권한설정할 수 있게 처리
				$( "button[id^='"+ v_btnId +"']" ).remove();
				$( "a[id^='"+ v_btnId +"']" ).remove();

				//btnCreate는 btnRegist까지 묶어서 제외처리
				if(v_btnId == "btnCreate") {
					$( "button[id^='btnRegist']" ).remove();
					$( "button[id^='btnNew']" ).remove();
					$( "a[id^='btnRegist']" ).remove();
					$( "a[id^='btnNew']" ).remove();
				}
				else if(v_btnId == "btnDelete") {
					$( "button[id^='btnDel']" ).remove();
					$( "a[id^='btnDel']" ).remove();
					$( "button[id^='btnSelDel']" ).remove();
					$( "a[id^='btnSelDel']" ).remove();
				}
			});
		} catch(err) {
			Message.alertError(err.message);
		}
	}
}

/**
 * select2 옵션 설정
 * @param v_code codeClass
 * @param v_obj 대상 Object
 * @param v_data data-Json
 * @returns
 */
function fnSetSelect2BoxOption(v_code, v_obj, v_data) {
	var select2Option = {
			data: v_data,
			width: '100%',
			allowClear: true,
		};

	return select2Option;
}

/*
 * 일반 selectbox형
 * 사용법 : html영역에 <input type="text" id="businessUnit" name="businessUnit" class="form-control required" /> 추가후

 	var selData01 = ""; //코드데이터 초기로드용

	//초기로드
	$(function() {
		//초기값설정(select2 선택박스 설정전에 처리한다.)
		$("#searchBusinessUnit").attr("data-init", "ddd");

		//select2 선택박스 설정
		selData01 = fnGetCodeData("BUSINESSUNIT"); //코드데이터 설정(페이지별 최초1회 실행)
		fnSetSelect2Box("CODE", $("#searchBusinessUnit"), selData01); //코드 선택박스 설정
	});
 */
/**
 * select2 데이터설정(object이용)
 * @param v_code codeClass
 * @param v_obj 대상 Object
 * @param v_params params
 * @returns
 */
function fnSetSelect2Box(v_code, v_obj, v_params) {
	if(v_code && v_obj && v_params) {
		v_obj.empty();
		v_obj.select2(fnSetSelect2BoxOption(v_code, v_obj, v_params));
		var v_initData = v_obj.attr("data-init");
		if(typeof v_initData !="undefined") {
			v_obj.select2("val", v_initData);
		}
	}
}

/**
 * select2 옵션 설정
 * @param v_code codeClass
 * @param v_obj 대상 Object
 * @param v_url url
 * @param v_params params
 * @param v_defMsg default message
 * @returns
 */
function fnSetSelect2Option(v_code, v_obj, v_url, v_params, v_defMsg) {

	var v_selectFormat = fnGetSelect2FormatMarkup;
	var v_selectFormatSelection = fnGetSelect2Format;

	v_msg = g_searchInfo;
	if(v_defMsg) v_msg = v_defMsg;

	//오브젝트 또는 값으로 넘어왔을 경우에 대한 분기처리
	var v_companyId = v_obj.attr("data-companyId");
	if($("#"+v_companyId).length > 0)
		v_companyId = $("#"+v_companyId).val();

	if(typeof v_params == "undefined" || v_params == "" || v_params == null) {
		v_params = {
			codeClass: v_code,
			effdt: v_obj.attr("data-effdt"),
			businessUnit: v_obj.attr("data-businessUnit"),
			pcodeId: v_obj.attr("data-pcodeId"),
			companyId: v_companyId,
			hrStatus: v_obj.attr("data-hrStatus"),
			empStatus: v_obj.attr("data-empStatus"),
			jobInd: v_obj.attr("data-jobInd"),
			supvLvl: v_obj.attr("data-supvLvl"),
			jobcd: v_obj.attr("data-jobcd"),
			jobTitle: v_obj.attr("data-jobTitle"),
			grade: v_obj.attr("data-grade"),
			empType: v_obj.attr("data-empType"),
			hireType: v_obj.attr("data-hireType")
		};
	}

	var select2Option = {
	 		placeholder: v_msg,
			allowClear: true,
			width: "100%",
			ajax: {
			url: v_url,
			dataType: 'json',
			delay: 500,
			data: function (params) {
				/*
				return {
					searchKeywordEncode: encodeURIComponent(params.term), // search term
					codeClass: v_code,
					effdt: v_obj.attr("data-effdt"),
					businessUnit: v_obj.attr("data-businessUnit"),
					pcodeId: v_obj.attr("data-pcodeId"),
					companyId: v_obj.attr("data-companyId"),
					hrStatus: v_obj.attr("data-hrStatus"),
					empStatus: v_obj.attr("data-empStatus"),
					jobInd: v_obj.attr("data-jobInd"),
					supvLvl: v_obj.attr("data-supvLvl"),
					jobcd: v_obj.attr("data-jobcd"),
					jobTitle: v_obj.attr("data-jobTitle"),
					grade: v_obj.attr("data-grade"),
					empType: v_obj.attr("data-empType"),
					hireType: v_obj.attr("data-hireType"),
					page: params.page
				}
				*/
				v_params.searchKeywordEncode = encodeURIComponent(params.term); // search term
				v_params.page = params.page;
				return v_params;
			},
			processResults: function (data, params) {
				params.page = params.page || 1;
				return {
					results: data.items,
					pagination: { more: (params.page * 10) < data.total_count }
				};
			},
			cache: false,
			success: function(response) {
				//alert(JSON.stringify(response));
			}
		},
		escapeMarkup: function (markup) { return markup; },
		minimumInputLength: 1,
		templateResult: v_selectFormat,
		templateSelection: v_selectFormatSelection
	};

	//기본값이 있을경우 조회설정
	if(typeof v_obj.attr("data-init") != "undefined" && v_obj.attr("data-init") != null && v_obj.attr("data-init") != "") {
		var id = v_obj.attr("data-init");
		v_params.searchId = id;

		//select box에 기본값 설정
		v_obj.append("<option value='"+ id +"' selected></option>");

		//select2 box에 기본값 설정
		select2Option.initSelection = function (element, callback) {
			$.ajax(v_url, {
				data: v_params,
				dataType: "json"
			}).done(function (data){
				//alert(JSON.stringify(data.items));
				callback(data.items);
			});
		}
	}

	return select2Option;
}

/**
 * 자동완성 selectbox형
 * 사용법 : html영역에 <select id="searchCodeClass" name="searchCodeClass"></select> 추가후

	//초기로드
	$(function() {
		//초기값설정(select2 선택박스 설정전에 처리한다.)
		$("#searchCodeClass").attr("data-init", "${item.searchCodeClass}");

		//select2 선택박스 설정 - codeClass
		fnSetSelect2Search("CODE_CLASS_ID", $("#searchCodeClass"), "S", "/hs/admin/codeMng/HsAdminCodeClassIdSelect2.json", "");
	});
 */
function fnSetSelect2Search(p_code, p_obj, p_url, p_params, p_defMsg) {
	if(p_code && p_obj && p_url) {
	 	p_obj.select2(fnSetSelect2Option(p_code, p_obj, p_url, p_params, p_defMsg));
	}
}

/**
 * URL로 넘어온 QueryString 반환
 * @returns
 */
function fnGetUrlParams() {
	var params = {};
	window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi,
		function(str, key, value) {
			params[key] = value;
	});
	return params;
}

/**
 * p_url을 호출하여 조회된 데이터를 json 형태로 리턴
 * @param p_url
 * @param p_params
 * @param p_async
 * @returns
 */
function fnGetJsonData(p_url, p_params, p_async) {
	var v_async = false; //동기화가 기본
	if(typeof p_async !="undefined" && p_async != null) {
		v_async = p_async;
	}
	var retData = "";
	var request = $.ajax({
		url: p_url,
		type: "POST",
		data: p_params,
		dataType: "json",
		async: v_async
	});

	request.done(function(returnData) {
		//$("#log").html( msg );
		retData = returnData;
	});

	request.fail(function(request,status) {
		//alert( "fnGetJsonData Request failed: " + status );
		Message.alertError(request.responseText);
	});

	return retData;
}

/**
 * selectbox 구성용 데이터 조회
 * @param p_obj
 * @param p_code
 * @param p_type
 * @param p_jsonUrl
 * @param p_params
 * @returns
 */
function fnGetSelectData(p_obj, p_code, p_type, p_jsonUrl, p_params) {
	if(!p_type) {
		p_type = "B";
	}

	//B타입에 대한 파라미터 설정(grid에서 처리를 위해)
	var v_companyId = "";
	var v_businessUnit = "";
	var v_effdt = "";
	var v_params = "";
	var v_initValue = "";

	if(p_obj) {
		v_companyId = p_obj.attr("data-companyId");
		if($("#"+v_companyId).length > 0) v_companyId = $("#"+v_companyId).val(); //오브젝트 또는 값으로 넘어왔을 경우에 대한 분기처리

		v_businessUnit = p_obj.attr("data-businessUnit");
		v_effdt = p_obj.attr("data-effdt");
		v_initValue = p_obj.attr("data-init");
	}

	if(typeof p_params == "undefined" || p_params == "" || p_params == null) {
		v_params = {codeClass: p_code, companyId: v_companyId, businessUnit: v_businessUnit, effdt: v_effdt, insEffStatus: "A"};
	} else {
		v_params = p_params;
		v_params.codeClass = p_code;
	}

	//데이터 조회
	var v_data;
	switch(p_code) {
		case "COMPANY_ID" : //회사
			if(p_obj != null && (typeof v_initValue == "undefined" || v_initValue == "")) {
				p_obj.attr("data-init", g_defaultCompanyId); //초기값 입력
			}
			v_data = fnGetSelect2Data(p_code, v_params);
			break;
		default :
			if(p_type == "B") { //일반 selectbox
				v_data = fnGetSelect2Data(p_code, v_params, p_type, "");
			} else if(p_type == "C") { //일반 selectbox (커스텀 데이터)
				v_data = fnGetSelect2Data(p_code, v_params, p_type, p_jsonUrl);
			}
	}

	return v_data;
}

/**
 * select2 선택결과 포맷설정
 * @param repo
 * @returns
 */
function fnGetSelect2Format(repo) {
	if(!repo.id) return repo.text;
	return "[" + repo.id + "] " + repo.text;
}

/**
 * select2 조회결과 포맷설정 - Markup 사용
 * @param repo
 * @returns
 */
function fnGetSelect2FormatMarkup(repo) {
	if (repo.loading) return repo.text;

	var markup = "";
	markup += "<span>[" + repo.id + "] " + repo.text + "</span>";

	return markup;
}

/**
 * 팝업 실행 및 파라메터 전송
 * @param p_name
 * @param p_url
 * @param p_params
 * @param p_option
 * @returns
 */
function fnPopWithParams(p_name, p_url, p_params, p_option) {
	var v_temp_win = window.open('about:blank', '_newtab');
	fnPostMove(p_url, p_params, p_name);
	v_temp_win.focus();
}

/**
 * 팝업 실행 및 파라메터 전송
 * @param p_name
 * @param p_url
 * @param p_params
 * @param p_option
 * @returns
 */
function fnPostPopWithParams(p_name, p_url, p_params, p_option) {
	// p_name은 target을 찾는것이므로 반드시 넘겨줘야됨
	var v_temp_win = window.open('', p_name, p_option);
	fnPostMove(p_url, p_params, p_name);
	v_temp_win.focus();
}

/**
 * 레이어팝업 처리(일반) pwModal Prompt 사용
 * @param schObj
 * @param url
 * @param width
 * @param height
 * @returns
 */
function fnPrompt(schObj, url, width, height){
	fnInitPromptGlobal();

	//값전달
	G_MODAL_ID = "pwModal";
	//기존팝업내용 제거
	$("#pwModal > div.modal-body").html("");

	//modal 설정
	var v_params = schObj.attr('data-params');
		v_params = eval("("+v_params+")");

	var v_modalBody = $("#pwModal > div.modal-dialog > div.modal-content > div.modal-body");
		v_modalBody.load(url, v_params);
	if(schObj.attr('title') != "") $('#pwModal .modal-title').text(schObj.attr('title'));
	if(schObj.attr('title2') != "") $('#pwModal .modal-title').text(schObj.attr('title2')); // 필수 값일 경우 title을 This field is required. 공용으로 사용하고 있어 title2변경

	fnSetPromptGlobal(schObj); //값 전달용 오브젝트id 저장

	fnSetPromptSize($("#pwModal > div.modal-dialog"), width, height); //레이어팝업 크기설정
	$('#pwModal').modal('show');
}

/**
 * 레이어팝업 pwModal_sm Prompt 사용
 * @param schObj
 * @param url
 * @param width
 * @param height
 * @returns
 */
function fnPromptMini(schObj, url){
	fnInitPromptGlobal();

	schObj.attr('data-toggle', 'modal');
	schObj.attr('data-target', '#pwModal_sm');
	schObj.attr('data-url', url);

	fnSetPromptGlobal(schObj); //값 전달용 오브젝트id 저장
}

/**
 * 레이어팝업 pwModal_search Prompt 사용
 * @param schObj
 * @param url
 * @param width
 * @param height
 * @returns
 */
function fnPromptSearch(schObj, url, width, height) {
	fnInitPromptGlobal();
	//값전달
	G_MODAL_ID = "pwModal_search";
	fnSetPromptGlobal(schObj);

	//modal 설정
	var v_params = schObj.attr('data-params');
		v_params = eval("("+v_params+")");
	var v_modalBody = $("#pwModal_search > div.modal-dialog > div.modal-content > div.modal-body");
		v_modalBody.load(url, v_params);
	if(schObj.attr('title') != "") $('#pwModal_search .modal-title').text(schObj.attr('title'));
	if(schObj.attr('title2') != "") $('#pwModal_search .modal-title').text(schObj.attr('title2')); // 필수 값일 경우 title을 This field is required. 공용으로 사용하고 있어 title2변경
	//레이어팝업 크기설정
	fnSetPromptSize($("#pwModal_search > div.modal-dialog"), width, height);
	$('#pwModal_search').modal('show');
}

/**
 * 레이어팝업에서 값선택 처리
 * @param txtIdVal
 * @param txtNmVal
 * @param txtEmpNo
 * @param txtDept
 * @param txtJobPosition
 * @param options
 * @param txtApprId
 * @param txtSNmVal
 * @param txtHireDt
 * @param callbackList
 * @returns
 */
function fnPromptSelect(txtIdVal, txtNmVal, txtEmpNo, txtDept, txtJobPosition, options, txtApprId, txtSNmVal, txtHireDt, callbackList){
	if(G_TXTID != "" && $('#'+G_TXTID).length > 0) {
		$('#'+G_TXTID).val(txtIdVal);
		$('#'+G_TXTID).change();
	}
	if(G_TXTDESCR != "" && $('#'+G_TXTDESCR).length > 0) {
		$('#'+G_TXTDESCR).val(txtNmVal);
		$('#'+G_TXTDESCR).change();
	}
	if(G_EMPNO != "" && $('#'+G_EMPNO).length > 0) {
		$('#'+G_EMPNO).val(txtEmpNo);
		$('#'+G_EMPNO).change();
	}
	if(G_DEPT != "" && $('#'+G_DEPT).length > 0) {
		$('#'+G_DEPT).val(txtDept);
		$('#'+G_DEPT).change();
	}
	if(G_JOB_POSITION != "" && $('#'+G_JOB_POSITION).length > 0) {
		$('#'+G_JOB_POSITION).val(txtJobPosition);
		$('#'+G_JOB_POSITION).change();
	}
	if(G_OPTIONS != "" && $('#'+G_OPTIONS).length > 0) {
		$('#'+G_OPTIONS).val(options);
		$('#'+G_OPTIONS).change();
	}
	if(G_APPR_ID != "" && $('#'+G_APPR_ID).length > 0) {
		$('#'+G_APPR_ID).val(txtApprId);
		$('#'+G_APPR_ID).change();
	}

	if(G_NAME != "" && $('#'+G_NAME).length > 0) {
		$('#'+G_NAME).val(txtSNmVal);
		$('#'+G_NAME).change();
	}

	if(G_HIRE_DT != "" && $('#'+G_HIRE_DT).length > 0) {
		$('#'+G_HIRE_DT).val(txtHireDt);
		$('#'+G_HIRE_DT).change();
	}

	//리얼그리드 값전달용
	if(G_REAL_GRID_VIEW_ID != ""  && typeof G_REAL_GRID_VIEW_ID != "undefined" && G_REAL_GRID_COLUMN_NM != ""  && typeof G_REAL_GRID_COLUMN_NM != "undefined") {
		var v_gridView = eval(G_REAL_GRID_VIEW_ID);
		var v_dataProvider = eval(G_REAL_GRID_DATA_PROVIDER);
		var v_dataRow = v_gridView.getCurrent().dataRow;
		var v_itemIndex = v_gridView.getCurrent().itemIndex;
		var v_fieldIndex = v_gridView.getCurrent().fieldIndex;

		//v_dataProvider.setValue(v_dataRow, v_fieldIndex, txtIdVal);
		if (options == "empid") {
			v_gridView.setValue(v_itemIndex, G_REAL_GRID_COLUMN_NM, txtEmpNo);
		} else {
			v_gridView.setValue(v_itemIndex, G_REAL_GRID_COLUMN_NM, txtIdVal);
		}
		//v_gridView.commitEditor(true);
	}

	//콜백함수 처리(리얼그리드용 분기처리)
	if(G_REAL_GRID_VIEW_ID != ""  && typeof G_REAL_GRID_VIEW_ID != "undefined" && G_REAL_GRID_COLUMN_NM != ""  && typeof G_REAL_GRID_COLUMN_NM != "undefined") {
		if(G_CALLBACK != "" && typeof G_CALLBACK != "undefined" && options =="empid") {
			var v_gridView = eval(G_REAL_GRID_VIEW_ID);
			var v_itemIndex = v_gridView.getCurrent().itemIndex;
			eval(G_CALLBACK + "('" + G_REAL_GRID_VIEW_ID + "','" + v_itemIndex + "','" + txtEmpNo + "','" + txtNmVal + "')");
		} else if(G_CALLBACK != "" && typeof G_CALLBACK != "undefined") {
			if(callbackList){
				var v_callback = eval(G_CALLBACK);
				var v_gridView = eval(G_REAL_GRID_VIEW_ID);
				var v_itemIndex = v_gridView.getCurrent().itemIndex;
				v_callback(v_gridView, v_itemIndex, callbackList);
			}else{
				var v_gridView = eval(G_REAL_GRID_VIEW_ID);
				var v_itemIndex = v_gridView.getCurrent().itemIndex;
				eval(G_CALLBACK + "('" + G_REAL_GRID_VIEW_ID + "','" + v_itemIndex + "','" + txtIdVal + "','" + txtNmVal + "')");
			}
		}
	} else {
		if(G_CALLBACK != "" && typeof G_CALLBACK != "undefined") {
			eval(G_CALLBACK + "('" + txtIdVal + "','" + txtNmVal + "','"+txtEmpNo+"')");
		}
	}

	if(G_MODAL_ID != "" && typeof G_MODAL_ID != "undefined") {
		if ( G_MODAL_ID == "pwModalMain" || G_MODAL_ID == "pwModalSub" ) {
			$('.layout.btn_close').click();
		} else {
			$('#' + G_MODAL_ID).modal('hide');
		}
	} else {
		$('#pwModal').modal('hide');
	}
}

/**
 * 공백제거
 * @param p_formId
 * @returns
 */
function fnTrimAllComponent(p_formId){
	//console.log(p_formId + " components trim");
	$.each($("#"+p_formId+" input, textarea"), function(i, item){
		//console.log(">> trim id : " + $(this).attr("id"));
		if ( $(this).attr("type") != "file" ) {
			$(this).val($(this).val().trim());
		}
	});
}

/**
 * 폼 내 변경 내역이 있는지 여부 조회
 * @param p_formId 폼아이디 - 필수
 * @returns
 */
function fnIsFormChanged(p_formId){
	var b = false;

	// 입력필드 공백제거
	fnTrimAllComponent(p_formId);

	// input, textarea 변경내역 여부 검사
	$.each($("#"+p_formId+" input, textarea"), function(i, item){
		if($(this).hasAttr("data-init")){
			//console.log("fnIsFormChanged - "  + $(this).attr("id") +" : "+ $(this).val() + " : " + $(this).attr("data-init") );
			if(typeof $(this).attr("id") != 'undefined' && $(this).prop("disabled") == false){
				if($(this).attr("type") == 'checkbox' || $(this).attr("type") == 'radio'){
					if($(this).val() != $(this).attr("data-init")
							&& ($(this).prop("checked") != ($(this).attr("data-init") == 'checked'))
							){
						console.log(">> changed " + $(this).attr("id") + " - '" + $(this).prop("checked") + "':'" + ($(this).attr("data-init") == 'checked') + "'(" + $(this).attr("data-init") + ")");
						b = true;
						return false
					}
				}else if($(this).val() != $(this).attr("data-init")){
					console.log(">> changed " + $(this).attr("id") + " - '" + $(this).val() + "':'" + $(this).attr("data-init") + "'");
					b = true;
					return false
				}
			}//
		}
	});

	// select는 별도 처리해줘야 함 - 동시 처리 시 다른 폼도 조회 하는 오류가 있음
	if(!b){
 		$.each($("#"+p_formId+" select"), function(i, item){
			if($(this).hasAttr("data-init")){
				//console.log("fnIsFormChanged - "  + $(this).attr("id") +" : "+ $(this).val() + " : " + $(this).attr("data-init") );
				if(typeof $(this).attr("id") != 'undefined'
						&& $(this).prop("disabled") == false
						&& fnNvl($(this).val(), "") != $(this).attr("data-init")
						){
					console.log(">> changed " + $(this).attr("id") + " - '" + fnNvl($(this).val(), "") + "':'" + $(this).attr("data-init") + "'");
					b = true;
					return false
				}
			}
		});
	}

	return b;
}

/**
 * 폼 내 데이터 클리어
 * @param p_formId 폼아이디 - 필수
 * @param p_excepts 제외 리스트 - 옵션
 * @returns
 */
function fnClearFormData(p_formId, p_excepts){

	$.each($("#"+p_formId).find("input[type='text'], textarea").not(":radio, :checkbox"), function(i, item){
		if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
			$(this).attr("data-init", "");
			$(this).val($(this).attr("data-init"));
		}
	});

	$.each($("#"+p_formId).find("select"), function(i, item){
		if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
			$(this).attr("data-init", "");
			$(this).val($(this).attr("data-init"));
		}
	});

	$.each($("#"+p_formId).find(":radio, :checkbox"), function(i, item){
		if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
			$(this).attr("data-init", "");
			$(this).prop("checked", false);
		}
	});
}

/**
 * 폼 내 입력창 사용 불가 셋팅
 * @param p_formId 폼아이디 - 필수
 * @param p_disable disable 여부 - 필수
 * @param p_exceptReadonly 읽기전용 제외 여부 - 옵션 default(전체) : false
 * @returns
 */
function fnFormDisable(p_formId, p_disable, p_exceptReadonly){
	//console.log("formId : " + p_formId + ", disable : " + p_disable + ", exceptReadonly : " + p_exceptReadonly);
	if(!p_exceptReadonly) p_exceptReadonly = false;

	$.each($("#"+p_formId).find("input, textarea").not(":radio, :checkbox, [type='hidden']"), function(i, item){
		if(!p_exceptReadonly || $(this).attr("readonly") != 'readonly') $(this).prop("disabled", p_disable);
	});

	$.each($("#"+p_formId).find("select"), function(i, item){
		if(!p_exceptReadonly || $(this).attr("readonly") != 'readonly') $(this).prop("disabled", p_disable);
	});

	$.each($("#"+p_formId).find(":radio, :checkbox"), function(i, item){
		if(!p_exceptReadonly || $(this).attr("readonly") != 'readonly') $(this).prop("disabled", p_disable);
	});
}

/**
 * 폼 내 데이터 data-init 현재 데이터로 셋팅
 * @param p_formId 폼아이디 - 필수
 * @param p_excepts 제외 리스트 - 옵션
 * @returns
 */
function fnSetFormDataInit(p_formId, p_excepts){

	$.each($("#"+p_formId).find("input, textarea").not(":radio, :checkbox"), function(i, item){
		if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
			$(this).attr("data-init", fnNvl($(this).val(), ''));
		}
	});

	$.each($("#"+p_formId).find("select"), function(i, item){
		if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
			$(this).attr("data-init", $(this).val() ? $(this).val() : '');
		}
	});

	$.each($("#"+p_formId).find(":radio, :checkbox"), function(i, item){
		if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
			$(this).attr("data-init", $(this).prop("checked") ? "checked" : "");
		}
	});
}

/**
 * 폼 내 데이터를 data-init 원래 값으로 복원
 * @param p_formId 폼아이디 - 필수
 * @param p_excepts 제외 리스트 - 옵션
 * @returns
 */
function fnRollbackFormData(p_formId, p_excepts){

	$.each($("#"+p_formId).find("input, textarea").not(":radio, :checkbox"), function(i, item){
		if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
			console.log($(this).attr("id") + " : " + $(this).attr("data-init"));
			$(this).val($(this).attr("data-init"));
		}
	});

	$.each($("#"+p_formId).find("select"), function(i, item){
		if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
			console.log($(this).attr("id") + " : " + $(this).attr("data-init"));
			$(this).val($(this).attr("data-init"));
		}
	});

	$.each($("#"+p_formId).find(":radio, :checkbox"), function(i, item){
		if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
			console.log($(this).attr("id") + " : " + $(this).attr("data-init"));
			$(this).prop("checked", ($(this).attr("data-init") == "checked"));
		}
	});
}

function fnGetJsonFormData(p_formId){
	var v_params = {};
	$.each($("#"+p_formId).serializeArray(), function(key, arr){
		if(arr.value != '') v_params[arr.name] = arr.value;
	});
	return v_params;
}

//RD 호출 공통함수 - 파라미터 처리
function fnCallReportView(p_params, p_mid, p_pid, p_title, p_bt, p_snm) {
	var v_url = g_rdUrl;
	v_url += "?mid=" + p_mid;
	v_url += "&pid=" + p_pid;
	v_url += "&t=" + encodeURIComponent(encodeURIComponent(p_title));
	v_url += "&bt=" + p_bt;
	v_url += "&s=" + p_snm;
	v_url += "&p=" + encodeURIComponent(encodeURIComponent(p_params));
	//v_url += "&v=NgmSsoName[JSESSIONID] NgmSsoData[<%=session.getId()%>]";
	console.log("RD 호출 공통함수 파라미터==>> g_rdUrl : " + g_rdUrl + ", p_mid : " + p_mid + ", p_pid : " + p_pid + ", p_title : " + p_title + ", p_bt : " + p_bt + ", p_snm : " + p_snm + ", p_params : " + p_params);
	console.log("RD 호출 공통함수 풀경로==>> v_url : " + v_url);
	var v_specs = "left=10,top=10,fullscreen=yes";
	v_specs += ",toolbar=no,menubar=no,status=no,scrollbars=no,resizable=no";
	window.open(v_url, '', v_specs);
}

/**
 * 사원 이미지 조회
 * p_imgObjId : 사원 이미지가 보여질 <img>태그의 id값
 * p_empId : 사원 empId
 * p_imgSize : 이미지 파일 사이즈 ( "" - 원본, "M" - 중간사이즈, "S" - 작은사이즈 )
 */
function fnSearchEmpImage(p_imgObjId, p_empId, p_imgSize) {
	var v_imgObj = $("#" + p_imgObjId);
	if ( fnToString(p_empId) == "" ) {
		v_imgObj.attr("src", "/opennote/img/sub/img_profile.png");
		v_imgObj.css("width", "100%");
		v_imgObj.css("height", "100%");
		return false;
	}

	var timestamp = new Date().getTime();
	if( !p_imgSize ) p_imgSize = "";

	v_imgObj.attr("src", "/hs/system/fileMng/HsSystemEmpImageFileDownload.do?empId=" + p_empId + "&fileMode=" + p_imgSize + "&cache=" + timestamp);
	v_imgObj.css("width", "100%");
	v_imgObj.css("height", "100%");

	// 사원 이미지가 없을 때 기본 이미지 보여주기
	if(!v_imgObj.hasAttr("onError") && !v_imgObj.hasAttr("onerror")) {
		v_imgObj.attr("onError", "this.src='/opennote/img/sub/img_profile.png;'");
	}
}

function fnSetRadio(p_code, p_ojbId, p_type, p_jsonUrl, p_params, p_required, p_addAll, p_defaultValue){
	var $obj;
	if(typeof p_ojbId == 'object'){
		$obj = p_ojbId;
	} else {
		$obj = $("#" + p_ojbId);
	}

	var v_data = fnGetSelectData($obj, p_code, p_type, p_jsonUrl, p_params);
	var html = new StringBuilder();
	if(v_data){
		if(p_addAll){
			html.AppendFormat('<input type="radio" name="{0}" id="{0}ALL" value="ALL" class="{1}" {2}>'
					, p_ojbId
					, p_required ? 'required' : ''
					, 'ALL' == p_defaultValue ? 'checked' : ''
			);
			html.AppendFormat('<label for="{0}ALL">ALL</label>', p_ojbId);
			html.Append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
		}
		$.each(v_data, function(i, item) {
			html.AppendFormat('<input type="radio" name="{0}" id="{0}{1}" value="{1}" class="mar_l10 {2}" {3}>'
					, p_ojbId
					, item.id
					, p_required ? 'required' : ''
					, item.id == p_defaultValue ? 'checked' : ''
			);
			html.AppendFormat('<label for="{0}{1}">{2}</label>', p_ojbId, item.id, item.text);
			html.Append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
		});

		$obj.parent().html(html.ToString());
	}
	html.RemoveAll();

	return v_data;
}

/**
 * 새창 팝업을 모달 형태로 만들기
 * @param p_popupObj
 * @returns
 */
function fnSetModalPop(p_popupObj, p_callback){

	window.addEventListener('beforeunload', (event)=>{
		event.preventDefault();
		/* 세션 만료로 오류 발생 > 주석처리
		 * if(p_callback){
			p_callback();
		}*/
		p_popupObj.close();
		clearInterval(interval);
	});

	showLoadingMask();
	$("#loading-bg").unbind().click(function() {
		p_popupObj.focus();
	});

	var interval = setInterval(function() {
		if(p_popupObj == undefined || p_popupObj.closed){
			if(p_callback){
				p_callback();
			}
			hideLoadingMask();
			clearInterval(interval);
		}
	}, 1000);
}

function fnDataTable(p_obj, p_data){
	if(p_obj && p_data){
		// 데이터 로드
		var v_data;
		if(p_data.ajax){
			fnSearchJsonAjax(p_data.ajax.data, p_data.ajax.url, function(data){
				v_data = data[p_data.ajax.dataSrc];
			});
		}
		else if(p_data.rows){
			v_data = p_data.rows;
		}
		else{
			return;
		}

		// 그리드 구성
		var html = new StringBuilder();
		if(v_data && v_data.length > 0){
			html.Append('<tbody>');
			$.each(v_data, function(i, item){
				html.Append('<tr>');
				$.each(p_data.columnDefs, function(idx, defs){
					var value = '';
					if(defs.render){
						value = defs.render('', '', item, {row:i});
					}else{
						value = fnNvl(item[p_data.columns[defs.targets].data], '');
						if(defs.filter){
							value = eval(defs.filter+'('+value+')');
						}
					}
					html.AppendFormat('<td class="{0}" style="width:{1};">{2}<td>'
							, defs.className
							, defs.width
							, value
							);
				});
				html.Append('</tr>');
			});
			html.Append('</tbody>');
		}
		else{
			html.Append('<tbody>');
			html.Append('<tr>');
			html.AppendFormat('<td colspan="{0}" >조회된 데이터가 없습니다.<td>', columnDefs.length);
			html.Append('</tr>');
			html.Append('</tbody>');
		}

		$(p_obj).append(html.ToString());
		html.removeAll();
	}
}

/*
* 같은 값이 있는 열을 병합함
* 사용법 : $('#테이블 ID').rowspan(0);
*/
$.fn.rowspan = function(colIdx, isStats) {
   return this.each(function(){
       var that;
       $('tr', this).each(function(row) {
           $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {

               if ($(this).html() == $(that).html()
                   && (!isStats
                           || isStats && $(this).prev().html() == $(that).prev().html()
                           )
                   ) {
                   rowspan = $(that).attr("rowspan") || 1;
                   rowspan = Number(rowspan)+1;

                   $(that).attr("rowspan",rowspan);

                   // do your action for the colspan cell here
                   $(this).hide();

                   //$(this).remove();
                   // do your action for the old cell here

               } else {
                   that = this;
               }

               // set the that if not already set
               that = (that == null) ? this : that;
           });
       });
   });
};


/*
* 같은 값이 있는 행을 병합함
* 사용법 : $('#테이블 ID').colspan (0);
*/
$.fn.colspan = function(rowIdx) {
   return this.each(function(){

       var that;
       $('tr', this).filter(":eq("+rowIdx+")").each(function(row) {
           $(this).find('th').filter(':visible').each(function(col) {
               if ($(this).html() == $(that).html()) {
                   colspan = $(that).attr("colSpan") || 1;
                   colspan = Number(colspan)+1;

                   $(that).attr("colSpan",colspan);
                   $(this).hide(); // .remove();
               } else {
                   that = this;
               }

               // set the that if not already set
               that = (that == null) ? this : that;

           });
       });
   });
}

/**
 * selectbox readonly 설정(ktw 2024-04-25)
 * @param p_obj : 객체 또는 id 명
 * @param p_readonly : readonly 여부(true,false)
 */
function fnSelectboxReadonly(p_obj, p_readonly) {
	var $p_obj;
	if(typeof p_obj == 'object'){
		$p_obj = p_obj;
	} else {
		$p_obj = $("#"+p_obj);
	}
	if( $p_obj.length > 0 ){
		if ( p_readonly == true ) {
			$p_obj.attr("style","color: #999 !important" ).css("pointer-events","none"); // 선택 못함
		} else {
			$p_obj.attr("style","color: #000" ).css("pointer-events","auto");
		}
	}
}

/**
 * checkbox, radio readonly 설정(ktw 2024-06-10)
 * @param p_obj : 객체 또는 id 명
 * @param p_readonly : readonly 여부(true,false)
 */
function fnCheckboxRadioReadonly(p_obj, p_readonly) {
	var $p_obj;
	if(typeof p_obj == 'object'){
		$p_obj = p_obj;
	} else {
		$p_obj = $("#"+p_obj);
	}
	if( $p_obj.length > 0 ){
		if ( p_readonly == true ) {
			$p_obj.attr("style","color: #999 !important" ).attr("onclick","return false;").prop("readonly",true); // 선택 못함
		} else {
			$p_obj.attr("style","color: #000" ).removeAttr("onclick").prop("readonly",false);
		}
	}
}

/**
 * 객체 내 입력창 Readonly 셋팅(ktw 2024-06-10)
 * @param p_obj 객체 또는 id 명 - 필수
 * @param p_readonly readonly 여부 - 필수
 * @returns
 */
function fnFormReadonly(p_obj, p_readonly, p_excepts){
	var $p_obj;
	if(typeof p_obj == 'object'){
		$p_obj = p_obj;
	} else {
		$p_obj = $("#"+p_obj);
	}
	//console.log("fnFormReadonly obj : " + p_obj.attr("id") + ", readonly : " + p_readonly);
	if( $p_obj.length > 0 ){
		//$.each($p_obj.find("input, textarea").not(":radio, :checkbox, [type='hidden']"), function(i, item){
		$.each($p_obj.find("input[type=text], textarea"), function(i, item){
			//console.log("fnFormReadonly text,textarea id==>>", $(this).attr("type"), $(this).attr("id"));
			if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
				$(this).prop("readonly", p_readonly);
			}
		});

		$.each($p_obj.find("select"), function(i, item){
			//console.log("fnFormReadonly select id==>>", "select", $(this).attr("id"));
			if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
				fnSelectboxReadonly($(this), p_readonly);
			}
		});

		$.each($p_obj.find(":radio, :checkbox"), function(i, item){
			//console.log("fnFormReadonly radio,checkbox id==>>", $(this).attr("type"), $(this).attr("id"));
			if(!(p_excepts && p_excepts.includes($(this).attr("id")))){
				fnCheckboxRadioReadonly($(this), p_readonly);
			}
		});
	}
}

/*
 * 2024.10.30 추가
 * 기존의 validate가 동적으로 생성된 요소의 필수값을 제대로 체크하지 못하여 따로 function생성
 */
function fnChkRequiredVaild(p_formId) {
	var validCnt = 0;
	var validTf  = true;
	var formId   = $("form");

	if( p_formId ) {
		formId = $("#" + p_formId);
	}

	formId.find(".required").each(function(){

		var validEl  = $(this);

			if( fnToString(validEl.val()) == '' && validEl.is(":visible") ) {
				// 기존 validate에서 사용중인 class넣어주기
				validEl.addClass("error");

			if (validEl.is(":radio") || validEl.is(":checkbox") || validEl.is("select") ) {
				validEl.innerText = "* 필수 입력값입니다.";

				if( !validEl.is("select") ){
					validEl.parent().append(error);
				}else{
					$(validEl).find(".requiredPlaceholder").remove();
					validEl.prepend('<option value="" selected disabled hidden class="requiredPlaceholder">* 필수 입력값입니다.</option>');
				}
			} else {
				validEl.attr("placeholder", '* 필수 입력값입니다.');
			}
				validCnt++;
			}
	});

	if( validCnt > 0 ) {
		validTf = false;
	}

	return validTf;
}

// 셀렉트박스 옵션 생성시 data값 같이 지정해서 만드는 함수
function fnSetSelectBoxMakeOption(p_selectObj, p_selectOption, p_emptyOption=true) {

	var v_selectObj = p_selectObj;

	// option값 초기화
	v_selectObj.empty();

	if (includeEmpty) {
		v_selectObj.append($('<option>', {
            value: '',
            text: '-- '+g_ibSelect+' --'
        }));
    }

    options.forEach(item => {
        const $option = $('<option>', {
            value: item.value,
            text: item.text
        });

        if (item.data) {
            Object.entries(item.data).forEach(([key, value]) => {
                $option.attr(`data-${key}`, value);
            });
        }

        $select.append($option);
    });

}

