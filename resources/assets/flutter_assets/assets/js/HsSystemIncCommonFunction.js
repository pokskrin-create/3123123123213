<%@ page language="java" contentType="application/javascript; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import ="egovframework.com.cmm.util.EgovUserDetailsHelper" %>
<%@ page import ="egovframework.com.cmm.service.EgovProperties" %>

/** 사원검색팝업
 *
 * @param obj
 * layOutGubun - MAIN / SUB (MAIN은 첫번째 팝업일때, SUB는 팝업 위에 팝업을 띄울때)
 * @returns
 */

function fnEmpInfoPrompt(schObj, layoutGubun){

	//입력란이 비활성일 경우 처리 제외
	var v_objId = schObj.attr("data-empNo");
	if($("#"+v_objId).attr("readonly") == "readonly" || $("#"+v_objId).attr("disabled") == "disabled") return false;

	schObj.attr("title", g_searchEmplMsg);
	if( $("input:checkbox[id='empRetireChk']").is(":checked") ){
		schObj.attr("data-hrStatus", "I");
	}else{
		schObj.attr("data-hrStatus", "A");
	}

	if( $("#searchTalent").val() != "" ){
		schObj.attr("data-name", $("#searchTalent").val());
	}

	var v_params = {};
	try {

		// data-params 없을 경우에는 console오류나서 조건처리
		if( fnToString(schObj.attr("data-params")) != '' ) {
			v_params = JSON.parse(schObj.attr("data-params"));
		}

		//v_params = JSON.parse(schObj.attr("data-params"));
		//console.log("data-params111==>>", schObj.attr("data-params") );
		//console.log("data-params222==>>", JSON.parse(schObj.attr("data-params")) );
	} catch (e) {
		console.log('error : ', e);
	}
	//var v_params = {
	//		scrtyGubun : schObj.attr("data-scrty") != "false" ? "Y" : "N"
	//}
	v_params.scrtyGubun = ( schObj.attr("data-scrty") != "false" ) ? "Y" : "N"

	// 해당 함수를 호출하는 페이지에서 넘어온 param들을 설정해줍니다.
	// param으로 deptNm과 deptOnly가 넘어옵니다.
	// deptOnly의 파라미터는 무조건 deptOnly로 작성해주어야 합니다.
	// 아래에서 작성된 params는 HsSystemDeptList_tree.jsp페이지로 이동합니다.
	var p_params = fnToString(schObj.attr('data-params'));

	if(p_params != ''){
		p_params = eval("("+p_params+")");
		v_params.deptNm   = p_params.deptNm;
		v_params.deptOnly = p_params.deptOnly;
		v_params.multiSelect = p_params.multiSelect;

		/// task 협업동료 검색 시 본인 제외
		if( p_params.pageType == "TASKCOLLEAGE" ) {
			v_params.pageType = p_params.pageType;
			/// task 협업동료 검색 시 업무지시일 경우 본인 검색 가능하도록
			if( p_params.pageMode == "WORKORDER" ) {
				v_params.pageMode = p_params.pageMode;
			}
		}
	}

	// 개별발령 검색 조건 추가(회사 멀티셀렉, 퇴직 조건 제외)
	if( $("#" + schObj.attr("data-pageType")).val() == "actionMng" ) {
		v_params.pageType = "actionMng";            // 재직퇴직 조건 x
		schObj.attr("data-changeCompanyYn", "Y");   // 회사 선택 Y
		v_params.pageMode = "multiCom";             // 회사 멀티셀렉션
	}

	schObj.attr("data-params", JSON.stringify(v_params));

	var url = "/hs/system/pop/HsSystemEmployeesInfoTreePop.do";
	if( schObj.attr("data-roleType") == "MSS" ){
		/// 부서장 권한 조회용 사원검색 팝업
		url = "/hs/system/pop/HsSystemEmployeesInfoTreeMssPop.do";
	}

	if( layoutGubun == "SUB" ){
		fnSubPrompt(schObj, url, 1500, 730);
	}else if( layoutGubun == "SUB2" ){
		fnSub2Prompt(schObj, url, 1500, 730);
	}else{
		fnMainPrompt(schObj, url, 1500, 730);
	}

}

/** 사원검색 function
 *
 * @param obj
 * @returns
 *
 * @attribute(아래)
 * data-searchInput : 검색어 입력된 input의 id
 * data-empId : 검색된 사원id 값 입력될 태그 id
 * data-empNo : 검색된 사원번호 값 입력될 태그 id
 * data-empNm : 검색된 사원명 값 입력될 태그 id
 * data-callback : 검색 후 처리할 콜백function
 */
function fnEmpSearch(obj){
	if( $("#" + obj.attr("data-searchInput")).val() == "" ){
		Message.alert("검색어를 입력해주세요.");
		return;
	}
	// 공통 변수 초기화
	fnInitPromptGlobal();
	
	G_PROMPT_OBJ_EMP_SEARCH_INPUT = obj.attr("data-searchInput");
	G_PROMPT_OBJ_EMP_ID = obj.attr("data-empId");
	G_PROMPT_OBJ_EMP_NO = obj.attr("data-empNo");
	G_PROMPT_OBJ_EMP_NM = obj.attr("data-empNm");
	G_COMPANY_ID = obj.attr("data-companyId");
	G_HR_STATUS = obj.attr("data-hrStatus");
	G_PAGETYPE = obj.attr("data-pageType");
	G_CALLBACK = obj.attr("data-callback");

	var p_params = {};

	if( obj.attr("data-roleType") == "MSS" ){
		//셀프서비스>부서원정보>부서원정보 2023-03-29 회사 제거(유혜경 요청)
	} else {
		if( $("#" + G_COMPANY_ID).val() == "" ){
			Message.alert("회사를 선택해주세요.");
			return;
		}
		p_params.companyId = $("#" + G_COMPANY_ID).val()
	}
	//var p_params = {
	//		searchTalent : $("#" + G_PROMPT_OBJ_EMP_SEARCH_INPUT).val(),
	//		companyId : $("#" + G_COMPANY_ID).val(),
	//		empNo : $("#" + G_PROMPT_OBJ_EMP_NO).val(),
	//		hrStatus : "A",
	//		scrtyGubun : "Y"
	//};
	p_params.searchTalent = $("#" + G_PROMPT_OBJ_EMP_SEARCH_INPUT).val();
	p_params.empNo = $("#" + G_PROMPT_OBJ_EMP_NO).val();
	p_params.hrStatus = "A";
	p_params.scrtyGubun = "Y";

	if( fnToString(obj.attr("data-scrty")) == "false" ){
		p_params.scrtyGubun = "N";
	}

	if( $("input:checkbox[id='empRetireChk']").is(":checked") || G_HR_STATUS == "I" ){
		p_params.hrStatus = "I";
	}
	
	if( G_PAGETYPE != "" || G_PAGETYPE != null) {
		p_params.pageType = G_PAGETYPE;
	}
	
	// 개별발령 검색 조건 추가(회사 멀티셀렉, 퇴직 조건 제외)
	if( fnToString(obj.attr("data-pageType")) == "actionMng" || $("#" + obj.attr("data-pageType")).val() == "actionMng" ) {
		p_params.pageType = "actionMng";
	}

	// 사원정보 검색
	var url = "/hs/common/com/HsCommonComEmployeesInfo.json"; // 사원정보 조회
	// 데이터 권한 체크(회사 있으면 기존 사원정보 조회)
//	var roleComChk = fnGetJsonData("/hs/common/com/HsCommonComCompanyIdSelect2.json", {codeClass:"COMPANY_ID"});
//	if( !(roleComChk.items.length > 0) ){
		//if( obj.attr("data-roleType") == "MSS" ){
		//	/// 부서장 권한 조회용 사원정보 조회
		//	p_params.searchDeptChk = "Y"; // 부서하위체크
		//	url = "/hs/common/com/HsCommonComEmployeesInfoMss.json";
		//}
//	}


	//기본값 : 데이터 권한
	//셀프서비스 부서장 화면이면,
	// 1) 데이터 권한 있으면  1번과 동일하게 데이터 권한 적용
	// 2) 데이터 권한 없으면 -> 부서장 권한 적용
	if( obj.attr("data-roleType") == "MSS" ){//셀프서비스>부서원정보
		var v_data = fnGetJsonData("/hs/common/com/HsCommonComUserRoleChk.json");//사원권한 체크 - 부서장급 확인용(ROLE_TYPE  = 'D' 확인한다.)
		//console.log("HsCommonComUserRoleChk==>>", v_data);
		if( fnToInt(v_data.cnt) == 0 ) {//데이터 권한 확인
			/// 부서장 권한 조회용 사원정보 조회
			p_params.searchDeptChk = "Y"; // 부서하위체크
			url = "/hs/common/com/HsCommonComEmployeesInfoMss.json";
		}
	}

	var empData = fnGetJsonData(url, p_params);

	if( fnToString(empData.items) != '' ){
		var empInfo = empData.items;
		// 조회된 사원정보 변수에 셋팅
		G_PROMPT_OBJ = empInfo;

		// 사원정보가 하나 이상일 때
		if( empInfo.length > 1 ){
			fnEmpListPrompt(obj);
		}else if( empInfo.length == 1 ){	// 사원정보가 한명일 때
			if( fnToString(G_PROMPT_OBJ_EMP_ID) != '' ){
				if( $("#" + G_PROMPT_OBJ_EMP_ID)[0].localName == "input" || $("#" + G_PROMPT_OBJ_EMP_ID)[0].localName == "select" || $("#" + G_PROMPT_OBJ_EMP_ID)[0].localName == "textarea" ){
					$("#" + G_PROMPT_OBJ_EMP_ID).val( empInfo[0].empId );
				}else{
					$("#" + G_PROMPT_OBJ_EMP_ID).html( empInfo[0].empId );
				}
			}

			if( fnToString(G_PROMPT_OBJ_EMP_NO) != '' ){
				if( $("#" + G_PROMPT_OBJ_EMP_NO)[0].localName == "input" || $("#" + G_PROMPT_OBJ_EMP_NO)[0].localName == "select" || $("#" + G_PROMPT_OBJ_EMP_NO)[0].localName == "textarea" ){
					$("#" + G_PROMPT_OBJ_EMP_NO).val( empInfo[0].empNo );
				}else{
					$("#" + G_PROMPT_OBJ_EMP_NO).html( empInfo[0].empNo );
				}
			}

			if( fnToString(G_PROMPT_OBJ_EMP_NM) != '' ){
				if( $("#" + G_PROMPT_OBJ_EMP_NM)[0].localName == "input" || $("#" + G_PROMPT_OBJ_EMP_NM)[0].localName == "select" || $("#" + G_PROMPT_OBJ_EMP_NM)[0].localName == "textarea" ){
					$("#" + G_PROMPT_OBJ_EMP_NM).val( empInfo[0].name );
				}else{
					$("#" + G_PROMPT_OBJ_EMP_NM).html( empInfo[0].name );
				}
			}
			if( fnToString(G_CALLBACK) != '' ){
				eval(G_CALLBACK)(empInfo[0]);
			}
		}
	}else{
		Message.alert('<span data-message-code="message.notExistData"></span>');
		if( fnToString(G_CALLBACK) != '' ){
			eval(G_CALLBACK)();
		}

		return;
	}
}

// 검색된 사원 list popup
function fnEmpListPrompt(obj){
 	var srcObj = obj;

 	//전달파라미터 설정
 	srcObj.attr("title", "사원정보");
 	fnMainPrompt(srcObj, "/hs/system/pop/HsSystemEmployeesInfoPop.do", 1300, 0);
}

/* 사원정보 값 선택처리
 * empCallbackObj - 사원정보 data
 */
function fnEmpInfoAfter(empCallbackObj){
	if( fnToString(G_PROMPT_OBJ_EMP_ID) != '' ){
		$("#" + G_PROMPT_OBJ_EMP_ID).val( empCallbackObj.empId );
	}

	if( fnToString(G_PROMPT_OBJ_EMP_NO) != '' ){
		$("#" + G_PROMPT_OBJ_EMP_NO).val( empCallbackObj.empNo );
	}

	if( fnToString(G_PROMPT_OBJ_EMP_NM) != '' ){
		$("#" + G_PROMPT_OBJ_EMP_NM).val( empCallbackObj.name );
	}

	if( fnToString(G_PROMPT_OBJ_DEPT_NM) != '' ){
		$("#" + G_PROMPT_OBJ_DEPT_NM).val( empCallbackObj.deptNm );
	}

	$("#searchTalent").val(empCallbackObj.name);

	if( fnToString(G_CALLBACK) != '' ){
		eval(G_CALLBACK)(empCallbackObj)
	}

	// 팝업 닫기
	fnPromptClose();
}

/** 코스트센터 검색팝업
 *
 * @param obj
 * layOutGubun - MAIN / SUB (MAIN은 첫번째 팝업일때, SUB는 팝업 위에 팝업을 띄울때)
 * @returns
 */
function fnCostctPrompt(schObj, layoutGubun){
	//입력란이 비활성일 경우 처리 제외
	var v_objId = schObj.attr("data-costct");
	if($("#"+v_objId).attr("readonly") == "readonly" || $("#"+v_objId).attr("disabled") == "disabled") return false;

	schObj.attr("title", g_searchCostctMsg);

	//if( $("input:checkbox[id='empRetireChk']").is(":checked") ){
	//	schObj.attr("data-effStatus", "I");
	//}else{
	//	schObj.attr("data-effStatus", "A");
	//}


	//if( $("#searchTalent").val() != "" ){
	//	schObj.attr("data-name", $("#searchTalent").val());
	//}

	var v_params = {
			scrtyGubun : schObj.attr("data-scrty") != "false" ? "Y" : "N"
	}
	schObj.attr("data-params", JSON.stringify(v_params));

	//var url = "/hs/system/pop/HsSystemEmployeesInfoTreePop.do";
	var url = "/hs/system/pop/HsSytemPopCostctList2.do";

	if( layoutGubun == "SUB" ){
		fnSubPrompt(schObj, url, 1000, 700);
	}else{
		fnMainPrompt(schObj, url, 1000, 700);
	}

}

/* 코스트센터 값 선택처리
 * costctCallbackObj - 코스트센터정보 data
 */
function fnCostctAfter(costctCallbackObj){
	if( fnToString(G_PROMPT_OBJ_COSTCT) != '' ){
		$("#" + G_PROMPT_OBJ_COSTCT).val( costctCallbackObj.costct );
	}

	if( fnToString(G_PROMPT_OBJ_COSTCT_NM) != '' ){
		$("#" + G_PROMPT_OBJ_COSTCT_NM).val( costctCallbackObj.costctNm );
	}

	//$("#searchTalent").val(empCallbackObj.name);

	if( fnToString(G_CALLBACK) != '' ){
		eval(G_CALLBACK)(costctCallbackObj)
	}

	// 팝업 닫기
	fnPromptClose();
}











