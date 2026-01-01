/* 공통 팝업 호출 모음
 * 1. 부서 팝업
 */
/* 부서 찾기 팝업 열기 */
var g_popupDept = {
	// 팝업 열기
    'open': function(p_params, p_callback, p_SheetObj) {
    	if( !isEmpty(p_callback) ) {
    		this.calFunc = p_callback;
    	}
    	if(!isEmpty(p_SheetObj)) {
    		this.sheetObj = p_SheetObj;
    	}

        var opt = {  "data-params"  : JSON.stringify(p_params) };

        var obj = $("<div />", opt);
        var url = "/hs/system/pop/HsSystemDeptList.do";   // 부서 팝업 호출

        var display_status = document.getElementById("js-popup-bg").style.display; //현재 popup상태(main/sub)
        if("block" == display_status) { //popup이 떠있는 상태 : sub 호출
        	fnSubPrompt(obj, url, 1000, 750);
        } else { //popup이 떠있지 않은 상태(ex:none)
            fnMainPrompt(obj, url, 1000, 750);
        }
    }
    , 'calFunc' : function() {}    // callback 함수 설정
    , 'callback': function(data) {
        // callback 함수 호출
    	if(!isEmpty(this.calFunc)) {
    		this.calFunc(data);
    	}

    	// sheet에 dept 값 설정
    	if(!isEmpty(this.sheetObj)) {
    		fnSetSheetDeptInfo(this.sheetObj, data);
    	}
    }
    , 'sheetObj': null
}

/* 조회 부서 selectbox List 에 값이 없을경우 append
 * p_obj : selectbox obj ,  p_data: 추가할 값
 * */
function fnSelboxDeptAppend(p_obj, p_data) {
    var v_dept   = p_data.dept;
    var v_deptNm = p_data.deptNm;

	if(p_obj.find("option[value='" + v_dept + "']").length == 0) {
		p_obj.append("<option value='" + v_dept + "'>" + v_deptNm + "</option>");
	}
	p_obj.val(v_dept);   // append 값 선택
}

/* sheet에 dept 값 설정 */
function fnSetSheetDeptInfo(p_sheetObj, v_data) {
	if(v_data) {
		var v_row = p_sheetObj.getFocusedRow();

        for(var vKey in v_data) {
        	p_sheetObj.setValue( v_row, vKey, v_data[vKey] );
        }
	}
}

/* 조회 부서 selectbox List 에 값이 없을경우 append
 * p_obj : selectbox obj ,  p_data: 추가할 값
 * */
function fnSelboxDeptAppend(p_obj, p_data) {
    var v_dept   = p_data.dept;
    var v_deptNm = p_data.deptNm;

	if(p_obj.find("option[value='" + v_dept + "']").length == 0) {
		p_obj.append("<option value='" + v_dept + "'>" + v_deptNm + "</option>");
	}
	p_obj.val(v_dept);   // append 값 선택
}

/**
 * sheet에서 긴 text column의 상세보기 popup
 * obj - popup을 띄우기 위해 파라미터 전달용 object
 * v_option - parameter와 그 외 option( sheetObj, sheetRowId )
 * layoutGubun - 첫번째 띄워줄 main팝업인지 팝업위에 팝업을 띄워줄 sub팝업인지에 대한 구분 (구분자 - MAIN, SUB)
 *
 * ******** 참고 *********
 * v_option안의 parameter에서 disabled를 true로 주면 변경불가 상태로 화면 띄움
 */
function fnSheetTextDetailPopup(obj, v_option, layoutGubun){
	var url = "/hs/system/pop/HsSystemCommentsPop.do";
	var srcObj = obj;

	//전달파라미터 설정
	srcObj.attr("data-params", JSON.stringify(v_option.params));
	srcObj.attr("data-sheetObj", v_option.sheetRowObj);
	srcObj.attr("data-sheetRowId", v_option.sheetRowId);
	srcObj.attr("data-callback", v_option.callback);

	if( layoutGubun == "SUB" ){
		fnSubPrompt(srcObj, url, 550, 600);
	}else{
		fnMainPrompt(srcObj, url, 550, 600);
	}
}

/**
 * 알림 메시지 설정 팝업 호출
 */
var g_popupAlertDefn = {
	"open" : function(p_alertType) {    // 알림 설정 팝업
        // alert구분
		if(!p_alertType) {
			return false;
		}

		var v_params = {alertType : p_alertType};
		var url = "/hs/common/popup/popupAlertDefn.do";    // 알림 설정 팝업 url
	    var schObj = $("<div>");

	    schObj.attr("data-params", JSON.stringify(v_params));

	    fnMainPrompt(schObj, url, 1400, 800, false);   // 드래그 false
	}
    , "message" : function(p_alertType) {    // 알림 메시지 팝업
    	// alert구분
		if(!p_alertType) {
			return false;
		}

		var v_params = {alertType : p_alertType};
		var url = "/hs/common/popup/popupAlertDefnMessage.do";    // 알림 설정 팝업 url
	    var schObj = $("<div>");

	    schObj.attr("data-params", JSON.stringify(v_params));

	    fnMainPrompt(schObj, url, 1000, 800, false);   // 드래그 false
    }
};

