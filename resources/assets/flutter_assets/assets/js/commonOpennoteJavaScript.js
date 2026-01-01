/* opennote  레이어팝업 처리(일반) pwModal Prompt 사용
 * @param schObj
 * @param url
 * @param width
 * @param height
 * @returns
 */
function fnMainPrompt(schObj, url, width, height, draggable, mode){
	fnInitPromptGlobal();
	
	$(".pop-header").show();    // X 버튼
	$(".pop-header > .btn_close").prop("disabled", false); // 활성화

	//값전달
	G_MODAL_ID = "pwModalMain";
	//기존 prompt내용 제거
	$("#pwModalMain > div.pop-content > div.scroll-content").html("");

	var v_params = schObj.attr('data-params');
	v_params = eval("("+v_params+")");

	var v_modalBody = $("#pwModalMain > div.pop-content");

	$("#pwModalMain > div.pop-content > div.scroll-content").load(url, v_params, function(data){
		if(!isEmpty(schObj.attr('data-title'))){
			$("#pwModalMain .layer_ttl").text(schObj.attr('data-title'));
		}
	});

	if(draggable != false){
		$("#pwModalMain").draggable({cancel:"table, .item, .tbl_wrap"});
	}

	//레이어팝업 노출 시, 백그라운드 스크롤 방지!
	$("body").addClass("stop-scroll");

	fnSetPromptGlobal(schObj); //값 전달용 오브젝트id 저장

	fnSetOpennotePromptSize($("#pwModalMain"), width, height, mode); //레이어팝업 크기설정
}

/* opennote  레이어팝업 처리(서브) pwModal Prompt 사용
 * @param schObj
 * @param url
 * @param width
 * @param height
 * @param mode : 모바일
 * @returns
 */
function fnSubPrompt(schObj, url, width, height, mode){
	fnInitPromptGlobal();

	$(".pop-header").show();    // X 버튼
	$(".pop-header > .btn_close").prop("disabled", false); // 활성화

	//값전달
	G_MODAL_ID = "pwModalSub";
	//기존 prompt내용 제거
	$("#pwModalSub > div.pop-content > div.scroll-content").html("");

	var v_params = schObj.attr('data-params');
	v_params = eval("("+v_params+")");

	var v_modalBody = $("#pwModalSub > div.pop-content");

	$("#pwModalSub > div.pop-content > div.scroll-content").load(url, v_params);

	$("#pwModalSub").draggable({cancel:"table, .item, .tbl_wrap"});

	//레이어팝업 노출 시, 백그라운드 스크롤 방지!
	$("body").addClass("stop-scroll");

	fnSetPromptGlobal(schObj); //값 전달용 오브젝트id 저장

	fnSetOpennotePromptSize($("#pwModalSub"), width, height, mode); //레이어팝업 크기설정
}

function fnSub2Prompt(schObj, url, width, height, mode){
	fnInitPromptGlobal();

	$(".pop-header").show();    // X 버튼
	$(".pop-header > .btn_close").prop("disabled", false); // 활성화

	//값전달
	G_MODAL_ID = "pwModalSub2";
	//기존 prompt내용 제거
	$("#pwModalSub2 > div.pop-content > div.scroll-content").html("");

	var v_params = schObj.attr('data-params');
	v_params = eval("("+v_params+")");

	var v_modalBody = $("#pwModalSub2 > div.pop-content");

	$("#pwModalSub2 > div.pop-content > div.scroll-content").load(url, v_params);

	$("#pwModalSub2").draggable({cancel:"table, .item, .tbl_wrap"});

	//레이어팝업 노출 시, 백그라운드 스크롤 방지!
	$("body").addClass("stop-scroll");

	fnSetPromptGlobal(schObj); //값 전달용 오브젝트id 저장

	fnSetOpennotePromptSize($("#pwModalSub2"), width, height, mode); //레이어팝업 크기설정
}

/**
 *
 * @param v_modal
 * @param width
 * @param height
 * @param mode
 *  :mode 모바일용 팝업 left 0 - 22.11.24 추가
 * @returns
 */
function fnSetOpennotePromptSize(v_modal, width, height, mode){
	v_modal.removeAttr("style");
	var top;
	var left;

	if(width == 0 && height == 0) {
		v_modal.css({'width':'50%','height':'81%'});

		left = "25%";
		top = "7%";
//		v_modal.css({'width':'41vw','height':'72vh'});
	} else {
		if(width > 0 && height > 0) {
			v_modal.css({'width':width});
			v_modal.css({'height':height});
		} else {
			if(width > 0) {
				v_modal.css({'width':width});
			}

			if(height > 0) {
				v_modal.css({'height':height});
			}else{
				if(height == "auto"){
					v_modal.css({'height':"auto"});
				}else{
					v_modal.css({'height':'550'});
				}
			}
		}

		left = (( $(window).width() - width) / 2 );
		if( height == 0 ){
			top = "7%";
//			top = (( $(window).height() - 800) / 2 )
		}else{
			if($(window).height() > height){
				top = (( $(window).height() - height) / 2 )
			}else{
				top = "7%";
			}
		}
	}


	var popupBackId;
	if( G_MODAL_ID == "pwModalMain" ){
		popupBackId = "js-popup-bg";
	}else{
		popupBackId = "js-sub-popup-bg";
	}

	document.getElementById(G_MODAL_ID).style.display = "block";
	document.getElementById(popupBackId).style.display = "block";
	v_modal.css({'left':left,'top':top, 'position':'fixed'});
	if( mode ){
		v_modal.css({'left':0,'top':top, 'position':'fixed'});
	}
}

// 모달 main/sub 팝업닫기
function fnPromptClose(){
	$("body").removeClass("ofHidden");
	document.getElementById(G_MODAL_ID).style.display = "none";

	try {
		$("#" + G_MODAL_ID).find("table.IBMainTable").each(function(){
			var id = $(this).attr("id");
			IBSheet[id].dispose();
		});
	} catch (e) {}

	// main 프롬프트에서의 호출이면 팝업 배경 hide(sub일 때는 main 프롬프트가 열려있다는 것이기 때문에 hide처리 하지않음.)
	if( "pwModalMain" == G_MODAL_ID){
		document.getElementById("js-popup-bg").style.display = "none";
		$(".popup-bg").css("display", "none");

	}else if( "pwModalSub2" == G_MODAL_ID){
		//document.getElementById("js-sub-popup-bg").style.display = "none";
		G_MODAL_ID = "pwModalSub";	// 서브 팝업이 닫히면 G_MODAL_ID 기본값 지정
	}else{
		document.getElementById("js-sub-popup-bg").style.display = "none";
		G_MODAL_ID = "pwModalMain";	// 서브 팝업이 닫히면 G_MODAL_ID 기본값 지정
	}
	//레이어팝업 닫을 때, 백그라운드 스크롤 허용!
	$("body").removeClass("stop-scroll");

	return false;
}

/* 팝업 닫기 불가 : 버튼 제거 */
function fnSetDisablePopupClose() {

	$(".pop-header").hide();    // X 버튼 제거(empty를 hide로 바꿈)
	$(".pop-header > .btn_close").prop("disabled", true); // 비활성화
}

function fnCalenderShow(calId, conId, callback) {
	$(calId).dateRangePicker({
		inline:true, //달력 보이게 설정
		alwaysOpen:true,//달력 보이게 설정
		container: conId,   //달력이 들어올 태그 ID
		singleMonth: true, //달력 한달만 보이게 설정
		singleDate : true,
		showTopbar: false,
        monthSelect: true,
        yearSelect: true,
//		showDropdowns: true,
//		hoveringTooltip: false, //호버툴팁 막기
//		selectForward: true,
//		setDate : 'today',
//		autoClose: true,
		showShortcuts: true,
//		getValue: function(){
//			if ($('#range_form').val() && $('#range_to').val() ){
//				return $('#range_form').val() + ' to ' + $('#range_to').val();
//			}else{
//				return '';
//			}
//		},
		setValue: function(s,s1,s2) {
			var p_params = {
					s : s,
					s1 : s1,
					s2 : s2
			}
			callback(p_params);
		},
	})


//	var picker = $('#daterange_box2').dateRangePicker({
//		inline:true, //달력 보이게 설정
//		alwaysOpen:true,//달력 보이게 설정
//		container: '#calender_box2',   //달력이 들어올 태그 ID
//		singleMonth: true, //달력 한달만 보이게 설정
//		showTopbar: false,
//		hoveringTooltip: false, //호버툴팁 막기
//		selectForward: false,
//		getValue: function(){
//			if ($('#range_form2').val() && $('#range_to2').val() ){
//				return $('#range_form2').val() + ' to ' + $('#range_to2').val();
//			}else{
//				return '';
//			}
//		},
//		setValue: function(s,s1,s2) {
//			$('#range_form2').val(s1); //시작일 값 표시
//			$('#range_to2').val(s2); //종료일 값 표시
//		}
//	})
}

// opennote javascript 호출
function fnOpennoteJs(){
//	gnb();
//	calenderShow();
	swiper5();
//	swiper8();
	space();
	tab();	// 일반화면 tab

	Scrollbar.initAll({alwaysShowTracks: true,});
}