/* 급여 유형/코드 목록 조회 URL */
const fnHpSelectDataUrl = p_type => {
	var url = {
			  "ERN_CD"      : "/hp/admin/payCodeMng/HpAdminErnCdSelect2List.json" 	// 소급전달항목 지급공제 지급항목
			, "DED_CD"      : "/hp/admin/payCodeMng/HpAdminDedCdSelect2List.json" 	// 소급전달항목 지급공제 공제항목
			, "ZZ_CD"       : "/hp/admin/payCodeMng/HpAdminZzCdSelect2List.json" 	// 급여외 소득항목
			, "PAY_TYPE"    : "/hp/admin/payType/HpAdminPayTypeSelect2List.json" 	// 급여유형
			, "PAY_GRP"     : "/hp/admin/payGroup/HpAdminPayGrpSelect2List.json" 	// 급여그룹 리스트
			, "SAL_PLAN"    : "/hp/admin/salPlan/HpAdminSalPlanSelect2List.json" 	// 급여제도 리스트
			, "SAL_STEP"    : "/hp/admin/salStep/HpAdminSalStepSelect2List.json" 	// 호봉관리 리스트
			, "SAL_GRADE"   : "/hp/admin/salGrade/HpAdminSalGradeSelect2List.json" 	// 급여직급
			, "RUN_GRP"     : "/hp/admin/runGrpMng/HpAdminRunGrpSelect2List.json" 	// 실행그룹 조회
			, "PAYVER"      : "/hp/admin/payDateMng/HpAdminPayverSelect2List.json" 	// 급여차수 조회
			, "PAY_ST_TYPE" : "/hp/admin/payFormula/HpAdminPayStTypeSelect2List.json" 		// 계산공식 조회유형/조회데이터/조회항목/연산내용등
			, "PROD_PROCESS_CD" : "/hp/admin/paySpecialHealthCheckupMng/HpAdminPaySpecialHealthCheckupProdProcSelect2List.json"	// 특수건강검진 공정 코드
	};
	return url[p_type];
}

/* 급여 코드(유형) 목록 조회 */
function fnGetHpSelectData(p_type, p_jsonParams) {
	return fnGetJsonData(fnHpSelectDataUrl(p_type), p_jsonParams).items;
}

/* 급여 코드(유형) select Box option 으로 추가 */
function fnSetHpSelectBox(p_type, p_objectId, p_jsonParams) {
	var v_data = fnGetHpSelectData(p_type, p_jsonParams);
	console.log("fnSetHpSelectBox >> ", v_data);
	fnSetSelectBoxData(p_objectId, v_data)
	return v_data;
}

function fnSetHpValue(p_id, p_name, p_value){

	if(value == null || value == 'null') value = '';
	if($("#"+objId).attr("type") == 'radio'){
		$.each($("#"+g_ItemformId1).find("input[name="+key+"]"), function(){
			$(this).prop("checked", (value == $(this).val()));
		});
	}
	else if($("#"+objId).attr("type") == 'checkbox'){
		var vals = p_value.split(",");
		$.each($("#"+g_ItemformId1).find("input[name="+key+"]"), function(){
			var checked = false;
			for(var i=0; i<vals.length; i++){
				if(vals[i] == $(this).val()){
					checked = true;
				}
			}
			$(this).prop("checked", checked);
		});
	}
	else{
		var attr = $("#"+objId).attr('data-amountOnly');
		if (typeof attr !== 'undefined' && attr !== false) {
			value = fnFormatCurrency(value);
		}
		$("div#"+key).text(fnFormatCurrency(value));
		$("span#"+key).text(fnFormatCurrency(value));
		$("input#"+key).val(fnFormatCurrency(value));
	}
}

// sumo 다중선택 selectbox
/*
 * $('select.SlectBox')[0].sumo.selectItem('volo');	-> value로 목록에서 항목을 선택
 * $('select.SlectBox')[0].sumo.selectAll();	-> 모든 항목 목록을 선택
 * $('select.SlectBox')[0].sumo.enable();	-> SumoSelect 컨트롤을 활성화
 * $('select.SlectBox')[0].sumo.disable();	-> SumoSelect 컨트롤 비활성화
 * $('select.SlectBox')[0].sumo.reload();	-> 원래 선택 요소 위에 UI를 다시로드, UI 및 sumoselct를 업데이트 할 수 있음.
 *
 * 참고 URL - https://hemantnegi.github.io/jquery.sumoselect/
 */
function fnSumoSelect(p_objId, p_placeholder, p_csvDispCount){

	$("#"+p_objId+" option[value='']").remove();

	var sumoCombo = $('#'+p_objId).SumoSelect({
		placeholder: p_placeholder
	   ,csvDispCount: p_csvDispCount ? p_csvDispCount : 7 // 선택 항목을 몇개까지 텍스트로 보일지(10으로 지정되어있을 때, 선택항몽이 11개부터 '11개 항목이 선택되었습니다.'로 표시됨)
	   ,prefix : ''
// 	   ,up : true // 드롭다운을 열 방향(true/false)
// 	   ,max : 5	// 선택할 옵션의 최대 수
// 	   ,search : true	// 검색 활성화(true/false)
	});

	$('#'+p_objId)[0].sumo.reload();
	$(".options").css("height", "130px");
	$('#'+p_objId)[0].sumo.unSelectAll();

	return sumoCombo;
}

/**
 * 첨부파일이 이미지인경우 이용
 * attachId에 해당하는 이미지를 화면에 출력한다.
 * @param p_containerId 이미지들을 출력할 위치 아이디
 * @param p_attachIds ","를 구분자로하는 attachId 리스트 문자열(String)
 * @returns
 */
function fnShowImages(p_containerId, p_attachIds){

	var v_imgDivObj = $("#"+p_containerId);
	if ( p_attachIds != "" ) {
		$.each(p_attachIds.split(","), function(index, attachId){
			console.log("attachId : " +attachId);
			var imgId = "img_"+attachId;
			v_imgDivObj.append('<div><img src="" id="'+imgId+'"></div>');
			var v_imgObj = $("#"+imgId);
			fnViewAttachFileImage(v_imgObj, attachId)
			v_imgObj.css("width", "100%");
			v_imgObj.css("height", "100%");
		});
    } else {
    	v_imgDivObj.append('<img src="/images/no_image.jpg">');
    }
}
