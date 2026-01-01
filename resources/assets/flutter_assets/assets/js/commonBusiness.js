
/**
 * 사원 정보 조회 - HsSystemIncVO 에 담긴 항목 조회 가능(p_empid or p_empno 를 넘겨줌)
 * @param p_companyId
 * @param p_empid
 * @param p_empno
 * @param p_hrStatus
 * @param p_searchAllYn
 * @returns
 */
function fnGetEmpData(p_companyId, p_empid, p_empno, p_hrStatus, p_searchAllYn) {
	var v_params = {companyId: p_companyId, empid: p_empid, empno: p_empno, hrStatus: p_hrStatus, searchAllYn: p_searchAllYn};
	var v_empData = fnGetJsonData('/hs/system/inc/HsSystemIncSearchEmp.json', v_params); // HsSystemIncDAO.selectEmpInfo
	return v_empData.item;
}

/**
 * 사원 정보 조회 - HsSystemIncVO 에 담긴 항목 조회 가능(기준일자 기준.) - 2017.01.25
 * @param p_companyId
 * @param p_empid
 * @param p_empno
 * @param p_baseDt
 * @param p_searchAllYn
 * @returns
 */
function fnGetEmpDataBaseDt(p_companyId, p_empid, p_empno, p_baseDt, p_searchAllYn) {
	var v_params = {companyId: p_companyId, empid: p_empid, empno: p_empno, baseDt: p_baseDt, searchAllYn: p_searchAllYn};
	var v_empData = fnGetJsonData('/hs/system/inc/HsSystemIncSearchEmpBaseDt.json', v_params); // HsSystemIncDAO.selectEmpInfoBaseDt
	return v_empData.item;
}

/**
 * 사원 정보 조회 - 전자결재 대상자(서비스ID 전체 사원, 재직자만)
 * @param p_companyId
 * @param p_empid
 * @param p_empno
 * @param p_hrStatus
 * @param p_searchAllYn
 * @returns
 */
function fnGetApprEmpData(p_companyId, p_empid, p_empno, p_hrStatus, p_searchAllYn) {
	var v_params = {companyId: p_companyId, empid: p_empid, empno: p_empno, hrStatus: p_hrStatus, searchAllYn: p_searchAllYn};
	// 재직자만 조회 가능. Controller에서 "A"로 설정. 파라미터 의미 없음
	var v_empData = fnGetJsonData('/hs/system/inc/HsSystemIncSearchApprEmp.json', v_params);	// HsSystemIncDAO.selectEmpInfo2
	return v_empData.item;
}

/**
 * 조직 정보 조회 -  PwHaAdminDeptVO 에 담긴 항목 조회 가능(p_companyId or p_businessUnit 를 넘겨줌)
 * @param p_companyId
 * @param p_businessUnit
 * @param p_dept
 * @returns
 */
function fnGetDeptData(p_companyId, p_businessUnit, p_dept) {
	var v_params = {companyId: p_companyId, businessUnit: p_businessUnit, dept: p_dept};
	var v_deptData = fnGetJsonData('/hs/system/inc/HsSystemIncSearchDept.json', v_params); // HsSystemIncDAO.selectDeptInfo
	return v_deptData.item;
}

/**
 * 직무 정보 조회 -  PwHaAdminJobcdVO 에 담긴 항목 조회 가능(p_companyId or p_businessUnit 를 넘겨줌)
 * @param p_companyId
 * @param p_businessUnit
 * @param p_jobcls
 * @returns
 */
function fnGetJobclsData(p_companyId, p_businessUnit, p_jobcls) {
	var v_params = {companyId: p_companyId, businessUnit: p_businessUnit, jobcls: p_jobcls};
	var v_jobclsData = fnGetJsonData('/hs/system/inc/HsSystemIncSearchJobcls.json', v_params);
	return v_jobclsData.item;
}

/**
 * 직무 레벨 정보 조회 -  PwHaAdminJobcdVO 에 담긴 항목 조회 가능(p_companyId or p_businessUnit 를 넘겨줌)
 * @param p_companyId
 * @param p_businessUnit
 * @param p_jobcd
 * @returns
 */
function fnGetJobcdData(p_companyId, p_businessUnit, p_jobcd) {
	var v_params = {companyId: p_companyId, businessUnit: p_businessUnit, jobcd: p_jobcd};
	var v_jobcdData = fnGetJsonData('/hs/system/inc/HsSystemIncSearchJobcd.json', v_params);
	return v_jobcdData.item;
}

/**
 * COMPANY_ID따른 FILED_GROUP으로 BUSINESSUNIT 가져오기
 * @param companyId
 * @param fieldGroup
 * @returns
 */
function fnGetBuUnitList(jsonParams){

	var businessUnit = "";
	if(jsonParams['companyId'] == null || jsonParams['companyId'] == "") jsonParams['companyId'] = g_defaultCompanyId;

	$.ajax('/hs/system/inc/HsSystemIncSearchBusinessUnit.json', { // HsSystemIncDAO.selectSearchBusinessUnit
		data: jsonParams,
		dataType: "json",
		async: false //동기화처리
	}).done(function (data){
		businessUnit = data.businessUnit;
	});

	return businessUnit;
}

/**
 * 직위에 따른 급여항목 기본 정보 조회 -  HsSystemIncVO 에 담긴 항목 조회 가능(p_companyId or p_businessUnit 를 넘겨줌)
 * @param p_companyId
 * @param p_businessUnit
 * @param p_jobPosition
 * @returns
 */
function fnGetPositionDefPayrollData(p_companyId, p_businessUnit, p_jobPosition) {
	var v_params = {companyId: p_companyId, businessUnit: p_businessUnit, jobPosition: p_jobPosition};
	var v_Data = fnGetJsonData('/hs/system/inc/HsSystemIncSearchJobPositionPayroll.json', v_params); // HsSystemIncDAO.selectJobPositionPayrollInfo
	return v_Data.item;
}

/**
 * 기준코드 데이터로드
 * @param v_codeClass
 * @param v_effdt
 * @param v_pcodeId
 * @returns
 */
function fnGetCodeData(v_codeClass, v_effdt, v_pcodeId) {
	var retData = "";
	var v_url = "/hs/common/com/HsCommonComCodeIdSelect2.json"; // HsAdminCodeMngDAO.selec2CodeIdList
	if(v_effdt == null) v_effdt = fnGetToday();
	var v_data = {codeClass: v_codeClass, effdt: v_effdt, codeId: v_pcodeId};

	retData = fnGetJsonData(v_url, v_data);
	return retData.items;
}

/**
 * 코드 속성 값 가져오기
 * @param p_codeClass
 * @param p_codeId
 * @param p_serviceId
 * @param p_businessUnit
 * @param p_effDt
 * @param p_attrNum
 * @returns
 */
function fnGetCodeAttr(p_codeClass, p_codeId, p_serviceId, p_businessUnit, p_effDt, p_attrNum){

	var v_serviceIdYn = "Y";

	if(p_serviceId == ""){
		v_serviceIdYn = "N";
	}

	var v_param = {};
	v_param["codeClass"] = p_codeClass;
	v_param["codeId"] = p_codeId;
	v_param["serviceIdYn"] = v_serviceIdYn;
	v_param["businessUnit"] = p_businessUnit;
	v_param["effdt"] = p_effDt;
	v_param["attrNum"] = p_attrNum;

	var v_attrVal = fnGetJsonData('/hs/admin/codeMng/HsAdminCodeAttrValue.json', v_param); // HsAdminCodeMngDAO.selectCodeAttrValue

 return v_attrVal.result;

}

/**
 * 회사 목록 전체 selectbox
 * @param obj Id
 * @returns companyList
 */
function fnSelectAllCompany(p_objId) {
	var companyList = fnSetSelectBox("COMPANY_ID", p_objId, "B", "", {pageType: "ALL"});       // 조회 목록

	$("#" + p_objId).prepend("<option value='*'>전체</option>");

	return companyList;
}

/**
 * codeClass가 SERVICE_ID, COMPANY_ID, DEPT, COUNTRY, CURRENCY, LANG_CD, JOB_POSITION, SAL_POSITION 중 하나인 경우는 p_type, p_jsonUrl 사용 안함
 * @param p_codeClass codeClass
 * @param p_params 검색조건 params
 * @param p_type 조회 타입 'B' 이면 코드 테이블에서 조회, 'C'이면 p_jsonUrl 을 실행하여 조회
 * @param p_jsonUrl p_type == 'C' 인 경우 조회 url
 * @returns
 */
function fnGetSelect2Data(p_codeClass, p_params, p_type, p_jsonUrl) {
 	var v_data;
	switch(p_codeClass) {
		case "SERVICE_ID" : //서비스ID
			v_data = fnGetJsonData("/hs/common/com/HsCommonComServiceIdSelect2.json", p_params); // HsSystemServiceMngDAO.selectServiceSelect2List
			v_data = v_data.items;
			break;
		case "COMPANY_ID" : //회사
			//v_data = pwCommonMyCompanyListData;
			v_data = fnGetJsonData("/hs/common/com/HsCommonComCompanyIdSelect2.json", p_params); // PwHaAdminCompanyDAO.selectCompanySelect2List
			v_data = v_data.items;
			break;
		case "DEPT" : //부서 -- 조직계층이 부레벨까지만 기본적으로 보여줌. (서무는 권한)
			v_data = fnGetJsonData("/hs/common/com/HsCommonComDeptCodeSelect2.json", p_params); // PwHaAdminDeptDAO.selectDeptSelect2List
			v_data = v_data.items;
			break;
		case "DEPT_MSS" : //부서 -- 조직계층이 부레벨까지만 기본적으로 보여줌. (서무는 권한)
			v_data = fnGetJsonData("/hs/common/com/HsCommonComDeptMssCodeSelect2.json", p_params); // PwHaAdminDeptDAO.selectDeptSelect2List
			v_data = v_data.items;
			break;
		case "ESTAB_ID" : //사업장
			v_data = fnGetJsonData("/hs/common/com/HsCommonComEstabIdSelect2.json", p_params); //
			v_data = v_data.items;
			break;
		case "COUNTRY" : //국가
			v_data = fnGetJsonData("/hs/common/com/HsCommonComCountrySelect2.json", p_params); // HsAdminCodeMngDAO.selec2CodeIdList
			v_data = v_data.items;
			break;
		case "CURRENCY" : //통화
			v_data = fnGetJsonData("/hs/common/com/HsCommonComCodeIdSelect2.json", p_params); // HsAdminCodeMngDAO.selec2CodeIdList
			v_data = v_data.items;
			break;
		case "LANG_CD" : //다국어선택입력언어
			v_data = fnGetJsonData("/hs/common/com/HsCommonComCodeIdSelect2.json", p_params); // HsAdminCodeMngDAO.selec2CodeIdList
			v_data = v_data.items;
			break;
		case "GRADE" : //직급
			p_params.jikjeType = "GRADE";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "JOB_POSITION" : //직위
			p_params.jikjeType = "JOB_POSITION";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "JOB_TITLE" : //직책
			p_params.jikjeType = "JOB_TITLE";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "EMP_TYPE" : //사원유형
			p_params.jikjeType = "EMP_TYPE";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "ACCT_DIV" : //회계구분
			p_params.jikjeType = "ACCT_DIV";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "ACCOUNT_CD" : //사원유형
			p_params.jikjeType = "ACCOUNT_CD";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "REDUCT_TYPE" : //감액유형
			p_params.jikjeType = "REDUCT_TYPE";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
//		case "ORG_TYPE" : //조직유형
//			p_params.jikjeType = "70";
//			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
//			v_data = v_data.items;
//			break;
		case "ORG_LVL" : //조직특성
			p_params.jikjeType = "ORG_LVL";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "ORG_DIV" : //조직부문
			p_params.jikjeType = "ORG_DIV";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "GROUP_ORG_DIV" : //조직부문
			p_params.jikjeType = "GROUP_ORG_DIV";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "BUSN_DIV" : //사업부
			p_params.jikjeType = "BUSN_DIV";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "TRAINING_MAIN_CLS" : //교육대분류
			p_params.jikjeType = "TRAINING_MAIN_CLS";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "TRAINING_SUB_CLS" : //교육중분류
			p_params.jikjeType = "TRAINING_SUB_CLS";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "SAL_POSITION" : //Pay Grade
			v_data = fnGetJsonData("/py/admin/paystructure/PwPyAdminPayStructureSalGradeCodes.json", p_params);
			v_data = v_data.items;
			break;
		case "LOCATION_ID" : //위치
			v_data = fnGetJsonData("/hs/common/com/HsCommonComLocationIdSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "CC_CATEGORY" : //코스트센터 범주
			p_params.jikjeType = "CC_CATEGORY";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "JIKJE_TYPE" : //직재공통 - jikjeType 파라미터가 반드시 넘어와야 된다.
			//p_params.jikjeType = "ORG_DIV";
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "NONTAX_TYPE_SETTING" : //비과세유형
			v_data = fnGetJsonData("/hp/admin/nonTaxSettingMng/selectNonTaxSettingSelect2List.json", p_params);
			v_data = v_data.items;
			break;
		case "EVAL_GRADE" : //평가등급
			p_params.jikjeType = "EVAL_GRADE";
			p_params.companyId = ( fnToString(p_params.companyId) == "" ) ? g_defaultCompany : fnToString(p_params.companyId);
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "PER_TYPE" : //평가유형
			p_params.jikjeType = "PER_TYPE";
			p_params.companyId = ( fnToString(p_params.companyId) == "" ) ? g_defaultCompany : fnToString(p_params.companyId);
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		case "GOAL_TYPE" : //목표유형
			p_params.jikjeType = "GOAL_TYPE";
			p_params.companyId = ( fnToString(p_params.companyId) == "" ) ? g_defaultCompany : fnToString(p_params.companyId);
			v_data = fnGetJsonData("/hs/common/com/HsCommonComJikjeCodeSelect2.json", p_params);
			v_data = v_data.items;
			break;
		default :
			if(p_type == "B") { //일반 selectbox
				//v_data = fnGetCodeData(p_codeClass);
				v_data = fnGetJsonData("/hs/common/com/HsCommonComCodeIdSelect2.json", p_params); // HsCommonComDAO.selec2CodeIdList
				v_data = v_data.items;
			} else if(p_type == "C") { //일반 selectbox (커스텀 데이터)
				v_data = fnGetJsonData(p_jsonUrl, p_params);
				v_data = v_data.items;
			} else { //자동완성 selectbox
			}
	}

	return v_data;
}

/**
 * 사원 사진 설정
 * @param p_imgObjId
 * @param p_empid
 * @returns
 */
function fnSetEmpPhoto(p_imgObjId, p_empid) {
	var v_imgObj = $("#"+p_imgObjId);
	var timestamp = new Date().getTime();
	v_imgObj.attr("src", "/hs/system/inc/HsSystemIncSelEmpPhoto.do?empid=" + p_empid + "&cache="+timestamp); // HsSystemIncDAO.selectEmpPhoto

	if(!v_imgObj.hasAttr("onError") && !v_imgObj.hasAttr("onerror")) {
		v_imgObj.attr("onError", "this.src='/images/default.png;'");
		//v_imgObj.attr("onError", "this.src='/plugin/INSPINIA/img/a4.jpg;'"); //임시 사진 표시
	}

	if(!v_imgObj.hasAttr("width")) v_imgObj.attr("width", "100");
	if(!v_imgObj.hasAttr("height")) v_imgObj.attr("height", "150");
}

/**
 * 부서선택 입력란 등록
 * @param divTreeId
 * @param divTreeTxtId
 * @param companyId
 * @returns
 */
function fnSetDeptTree(divTreeId, divTreeTxtId, companyId) {
	//데이터 생성
	var v_treeData = [];
	for(i = 0; i < pwCommonMyDeptListData.length; i++) {
		if(pwCommonMyDeptListData[i].companyId == companyId) {
			var v_pdept = pwCommonMyDeptListData[i].pdept;
			if(v_pdept == "") v_pdept = "#";
			v_treeData.push({
				'id': pwCommonMyDeptListData[i].dept,
				'parent': v_pdept,
				'text': '[' + pwCommonMyDeptListData[i].dept + '] ' + pwCommonMyDeptListData[i].deptNm
			});
		}
	}

	//트리생성
	var v_tree = $('#' + divTreeId);
	if ( v_tree.jstree(true) ) {
		v_tree.jstree(true).destroy();//jstree 제거
	}
	if(v_tree) {
		v_tree.jstree({
			'core' : {
				 //'data' : v_treeData
				 /*
				 [
					{ "id" : "ajson1", "parent" : "#", "text" : "Simple root node" },
					{ "id" : "ajson2", "parent" : "#", "text" : "Root node 2" },
					{ "id" : "ajson3", "parent" : "ajson2", "text" : "Child 1" },
					{ "id" : "ajson4", "parent" : "ajson2", "text" : "Child 2" }
				 ]
				 */
			 } ,
			 'plugins' : [ 'types', 'search' ],
			 'types' : {
				 'default' : {
					 'icon' : 'fa fa-folder'
				 }
			 }
		});
		v_tree.jstree(true).settings.core.data = v_treeData;
		v_tree.jstree(true).refresh();

		//부서트리 검색처리
		var v_PwCommonSearchTo = false;
		var v_PwCommonSearchDeptNmInput = $('#'+divTreeTxtId);
		v_PwCommonSearchDeptNmInput.keyup(function () {
			if(event.keyCode == 13) {
				return false;
			} else {
				if(v_PwCommonSearchTo) { clearTimeout(v_PwCommonSearchTo); }
				v_PwCommonSearchTo = setTimeout(function () {
					var v = v_PwCommonSearchDeptNmInput.val();
				  	v_tree.jstree(true).search(v);
				}, 250);
			}
		});

		//트리 전체열기, 전체닫기처리
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

		//공통-선택된 부서에 대한 정보 설정
		v_tree.on("changed.jstree", function (e, data) {
			var i, j, selId = [] , selText = [];
			for(i = 0, j = data.selected.length; i < j; i++) {
			  selId.push(data.instance.get_node(data.selected[i]).id);
			  selText.push(data.instance.get_node(data.selected[i]).text);
			}

			v_PwCommonSearchDeptNmInput.attr("data-dept", selId);
			v_PwCommonSearchDeptNmInput.val(selText);
		});

	}
}

/**
 * 조직 트리 조직도 전체 : 데이터 설정
 * @param divTreeId
 * @param divTreeTxtId
 * @param companyId
 * @param effdt
 * @param dept
 * @param p_async
 * @returns
 */
function fnSetAllDeptTreeEffdt(divTreeId, divTreeTxtId, companyId, effdt, dept, p_async) {
	var v_tree = $('#' + divTreeId);
	if(v_tree.length == 0) {
		return false;
	}
	if ( v_tree.jstree(true) ) {
		v_tree.jstree(true).destroy();//jstree 제거
	}

	var v_async = false; //동기화가 기본
	if(typeof p_async !="undefined" && p_async != null) {
		v_async = p_async;
	}

	//데이터 생성
	var v_treeData = [];

	if(effdt == null)	effdt = fnGetToday();

	$.ajax('/ha/admin/dept/PwHaAdminAllDeptTreeEffList.json', { // PwHaAdminDeptDAO.selectAllDeptTreeEffListJson
		data: {companyId : companyId, effdt : effdt, dept : dept},
		dataType: "json",
		async: v_async //동기화처리
	}).done(function (data){
		for(i = 0; i < data.treeEffList.length; i++) {
			var v_pdept = data.treeEffList[i].pdept;
			if(v_pdept == "" || v_pdept == null){ v_pdept = "#"; }
			// 레벨
			var isOpen = {};
			if(data.treeEffList[i].treeLvl <= 1) isOpen = {"opened" : true, "selected" : true };
			else isOpen = {};

			v_treeData.push({
				'id': data.treeEffList[i].dept,
				'treeNodeId': data.treeEffList[i].treeNodeId,
				'parent': v_pdept,
				'name': data.treeEffList[i].deptNm,
				'lvl': data.treeEffList[i].lvl,
				/*'text': "[" + data.treeEffList[i].dept + "] - " + data.treeEffList[i].deptNm,*/
				'text': data.treeEffList[i].dept + " - " + data.treeEffList[i].deptNm,
				'state': isOpen
			});
		}

		//트리생성
		v_tree.jstree({
			'core' : {
				 //'data' : v_treeData
				 /*
				 [
					{ "id" : "ajson1", "parent" : "#", "text" : "Simple root node" },
					{ "id" : "ajson2", "parent" : "#", "text" : "Root node 2" },
					{ "id" : "ajson3", "parent" : "ajson2", "text" : "Child 1" },
					{ "id" : "ajson4", "parent" : "ajson2", "text" : "Child 2" }
				 ]
				 */
			 } ,
			 'plugins' : [ 'types', 'search' ],
			 'types' : {
				 'default' : {
					 'icon' : 'fa fa-folder'
				 }
			 }
		});
		v_tree.jstree(true).settings.core.data = v_treeData;
		v_tree.jstree(true).refresh();

		//부서트리 검색처리
		var v_PwCommonSearchTo = false;
		var v_PwCommonSearchDeptNmInput = $('#'+divTreeTxtId);
		if(v_PwCommonSearchDeptNmInput.val() != ""){
			if(v_PwCommonSearchTo) { clearTimeout(v_PwCommonSearchTo); }
			v_PwCommonSearchTo = setTimeout(function () {
				var v = v_PwCommonSearchDeptNmInput.val();
			  	v_tree.jstree(true).search(v);
			}, 1500);
		}
		v_PwCommonSearchDeptNmInput.keyup(function () {
			if(event.keyCode == 13) {
				return false;
			} else {
				if(v_PwCommonSearchTo) { clearTimeout(v_PwCommonSearchTo); }
				v_PwCommonSearchTo = setTimeout(function () {
					var v = v_PwCommonSearchDeptNmInput.val();
				  	v_tree.jstree(true).search(v);
				}, 250);
			}
		});

		//트리 전체열기, 전체닫기처리
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
	});
}

/**
 * 조직 트리 : 데이터 설정
 * @param divTreeId
 * @param divTreeTxtId
 * @param companyId
 * @param effdt
 * @param dept
 * @param p_async
 * @param attCompanyYn
 * @param url
 * @param folding
 * @param dept
 * @returns
 */
function fnSetDeptTreeEffdt(divTreeId, divTreeTxtId, p_params, p_async, attCompanyYn, url, folding, dept) {
	var v_tree = $('#' + divTreeId);
	if(v_tree.length == 0) {
		return false;
	}
	if ( v_tree.jstree(true) ) {
		v_tree.jstree(true).destroy();//jstree 제거
	}

	var v_async = false; //동기화가 기본
	if(typeof p_async !="undefined" && p_async != null) {
		v_async = p_async;
	}
	var setUrl ="" //url 주소
	if(url){
		setUrl = url
	}else{
		setUrl = '/ha/admin/dept/selectDeptTreeList.json';
	}

	//데이터 생성
	var v_treeData = [];
	var retData = "";//리턴 데이터

	var request = $.ajax({
		url: setUrl,
		data: p_params,
		dataType: "json",
		async: v_async //동기화처리
	});
	request.done(function (data){
		//console.log("/js/commonBusiness.js fnSetDeptTreeEffdt data==>>", data);
		var treeEffList = data.rows;
		retData = data.rows;
		for(i = 0; i < treeEffList.length; i++) {
			var v_pdept = treeEffList[i].parentNodeNo;
			if(v_pdept == "" || v_pdept == null){ v_pdept = "#"; }
			// 레벨
			var isOpen = {};
			
			//폴딩 기본 세팅(부서 트리 모두 펼치기 위함)
			if(folding){
				isOpen.opened = true;
			}else{
				if(treeEffList[i].treeLvl <= 1) isOpen = {"opened" : true, "selected" : true };
				else isOpen = {};
			}
			
			//본인 부서 기본 세팅
			if(dept){
				if(dept == treeEffList[i].dept){
					isOpen.selected = true;
				}
			}

			v_treeData.push({
				'id': treeEffList[i].treeNodeNo,
				'treeNodeId': treeEffList[i].treeNodeNo,
				'parent': v_pdept,
				'name': treeEffList[i].deptNm,
				'lvl': treeEffList[i].level,
				'text': treeEffList[i].deptNm,
				'state': isOpen
			});
		}
		//==============================
		//2022-11-25 모바일 직원검색(부서원검색(조직도)HaMobileOrgEmpList.jsp) 드레그앤 드롭 사용안함
		var jstreeDndDisable = fnToString(p_params.jstreeDndDisable);
		//console.log("fnSetDeptTreeEffdt jstreeDndDisable==>>", jstreeDndDisable);
		var arrPlugins = [ 'dnd', 'search', 'types' ];
		if ( jstreeDndDisable == "Y" ) {
			// 원소 'dnd' 삭제
			for(let i = 0; i < arrPlugins.length; i++) {
				if(arrPlugins[i] === 'dnd')  {
					arrPlugins.splice(i, 1);
					i--;
				}
			}
		}
		//==============================
		//트리생성
		v_tree.jstree({
			'core' : {
				 //'data' : v_treeData
				 /*
				 [
					{ "id" : "ajson1", "parent" : "#", "text" : "Simple root node" },
					{ "id" : "ajson2", "parent" : "#", "text" : "Root node 2" },
					{ "id" : "ajson3", "parent" : "ajson2", "text" : "Child 1" },
					{ "id" : "ajson4", "parent" : "ajson2", "text" : "Child 2" }
				 ]
				 */
			 } ,
//			 'plugins' : [ 'dnd', 'search', 'types' ],
			 'plugins' : arrPlugins,
			 'types' : {
				 'default' : {
					 'icon' : 'fa fa-folder'
				 }
			 },
		}).bind("refresh.jstree", function (e,data) {
			//화상조직도 내 최초 조회만 본인 부서로 조회하기 위함(최초 조회가 아니거나 부서 없으면 최상위 트리거)
			if(v_tree.jstree("get_selected").length < 1){
				var arrData = data.instance && data.instance.settings.core.data;
				
				if(arrData){
					var targetData = arrData.filter(function(el){if(el.state.selected){return el;}});
					
					if(targetData.length > 0){
						var targetId = targetData[0].id;
						$("#"+targetId+"_anchor").trigger("click");
					}else{
						$("#1_anchor").trigger("click");
					}
				}
			}else{
				var targetId = v_tree.jstree('get_selected')[0];
				$("#"+targetId+"_anchor").trigger("click");
			}
	    });
		v_tree.jstree(true).settings.core.data = v_treeData;
		v_tree.jstree(true).settings.core.check_callback = true;
		v_tree.jstree(true).refresh();

		//부서트리 검색처리
		var v_PwCommonSearchTo = false;
		var v_PwCommonSearchDeptNmInput = $('#'+divTreeTxtId);
		if(v_PwCommonSearchDeptNmInput.val() != ""){
			if(v_PwCommonSearchTo) { clearTimeout(v_PwCommonSearchTo); }
			v_PwCommonSearchTo = setTimeout(function () {
				var v = v_PwCommonSearchDeptNmInput.val();
			  	v_tree.jstree(true).search(v);
			}, 1500);
		}
		v_PwCommonSearchDeptNmInput.keyup(function () {
			if(event.keyCode == 13) {
				return false;
			} else {
				if(v_PwCommonSearchDeptNmInput.val().length >1 && event.keyCode != 8){
					if(v_PwCommonSearchTo) { clearTimeout(v_PwCommonSearchTo); }
						v_PwCommonSearchTo = setTimeout(function () {
							var v = v_PwCommonSearchDeptNmInput.val();
						  	v_tree.jstree(true).search(v);
						}, 250);
				}
			}
		});

		//트리 전체열기, 전체닫기처리
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
	});
	request.fail(function(request,status) {
		//alert( "fnGetJsonData Request failed: " + status );
		//Message.alertError(request.responseText);
		console.log("/js/commonBusiness.js fnSetDeptTreeEffdt request.fail==>>", request.responseText);
	});
	return retData;
}

/**
 * 화상조직도용 조직 트리 : 데이터 설정
 * @param divTreeId
 * @param divTreeTxtId
 * @param companyId
 * @param effdt
 * @param dept
 * @param p_async
 * @returns
 */
function fnSetChartDeptTreeEffdt(divTreeId, divTreeTxtId, companyId, effdt, dept, p_async) {
	var v_tree = $('#' + divTreeId);
	if(v_tree.length == 0) {
		return false;
	}
	if ( v_tree.jstree(true) ) {
		v_tree.jstree(true).destroy();//jstree 제거
	}

	var v_async = false; //동기화가 기본
	if(typeof p_async !="undefined" && p_async != null) {
		v_async = p_async;
	}

	//데이터 생성
	var v_treeData = [];

	if(effdt == null)	effdt = fnGetToday();

	$.ajax('/ha/admin/dept/PwHaAdminChartDeptTreeEffList.json', { // PwHaAdminDeptDAO.selectChartDeptTreeEffListJson
		data: {companyId : companyId, effdt : effdt, dept : dept},
		dataType: "json",
		async: v_async //동기화처리
	}).done(function (data){
		for(i = 0; i < data.treeEffList.length; i++) {
			var v_pdept = data.treeEffList[i].pdept;
			if(v_pdept == "" || v_pdept == null){ v_pdept = "#"; }
			// 레벨
			var isOpen = {};
			if(data.treeEffList[i].treeLvl <= 1) isOpen = {"opened" : true, "selected" : true };
			else isOpen = {};

			v_treeData.push({
				'id': data.treeEffList[i].dept,
				'treeNodeId': data.treeEffList[i].treeNodeId,
				'parent': v_pdept,
				'name': data.treeEffList[i].deptNm,
				'lvl': data.treeEffList[i].lvl,
				/*'text': "[" + data.treeEffList[i].dept + "] - " + data.treeEffList[i].deptNm,*/
				'text': data.treeEffList[i].dept + " - " + data.treeEffList[i].deptNm,
				'state': isOpen
			});
		}

		//트리생성
		v_tree.jstree({
			'core' : {
				 //'data' : v_treeData
				 /*
				 [
					{ "id" : "ajson1", "parent" : "#", "text" : "Simple root node" },
					{ "id" : "ajson2", "parent" : "#", "text" : "Root node 2" },
					{ "id" : "ajson3", "parent" : "ajson2", "text" : "Child 1" },
					{ "id" : "ajson4", "parent" : "ajson2", "text" : "Child 2" }
				 ]
				 */
			 } ,
			 'plugins' : [ 'types', 'search' ],
			 'types' : {
				 'default' : {
					 'icon' : 'fa fa-folder'
				 }
			 }
		});
		v_tree.jstree(true).settings.core.data = v_treeData;
		v_tree.jstree(true).refresh();

		//부서트리 검색처리
		var v_PwCommonSearchTo = false;
		var v_PwCommonSearchDeptNmInput = $('#'+divTreeTxtId);
		if(v_PwCommonSearchDeptNmInput.val() != ""){
			if(v_PwCommonSearchTo) { clearTimeout(v_PwCommonSearchTo); }
			v_PwCommonSearchTo = setTimeout(function () {
				var v = v_PwCommonSearchDeptNmInput.val();
			  	v_tree.jstree(true).search(v);
			}, 1500);
		}
		v_PwCommonSearchDeptNmInput.keyup(function () {
			if(event.keyCode == 13) {
				return false;
			} else {
				if(v_PwCommonSearchTo) { clearTimeout(v_PwCommonSearchTo); }
				v_PwCommonSearchTo = setTimeout(function () {
					var v = v_PwCommonSearchDeptNmInput.val();
				  	v_tree.jstree(true).search(v);
				}, 250);
			}
		});

		//트리 전체열기, 전체닫기처리
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
	});
}

/**
 * 직무 트리 : 데이터 설정
 * @param divTreeId
 * @param divTreeTxtId
 * @param companyId
 * @param effdt
 * @returns
 */
function fnSetJobTreeEffdt(divTreeId, divTreeTxtId, companyId, effdt) {
	//데이터 생성
	var v_treeData = [];

	if(effdt == null)	effdt = fnGetToday();

	$.ajax('/ha/admin/jobTree/PwHaAdminJobTreeEffList.json', {
		data: {companyId : companyId, effdt : effdt},
		dataType: "json",
		async: false //동기화처리
	}).done(function (data){
		for(i = 0; i < data.jobEffList.length; i++) {
			var v_pdob = data.jobEffList[i].pjobCd;

			if(v_pdob == "" || v_pdob == null){ v_pdob = "#"; }
			// 레벨
			var isOpen = {};
			if(data.jobEffList[i].treeLvl <= 1) isOpen = {"opened" : true, "selected" : true };
			else isOpen = {};

			v_treeData.push({
				'id': data.jobEffList[i].jobCd,
				'parent': v_pdob,
				'name': data.jobEffList[i].jobNm,
				/*'text': "[" + data.jobEffList[i].jobCd + "] - " + data.jobEffList[i].jobNm,*/
				'text': data.jobEffList[i].jobCd + " - " + data.jobEffList[i].jobNm,
				'state': isOpen,
				'lvl' : data.jobEffList[i].treeLvl
			});
		}
	});

	//트리생성
	var v_tree = $('#' + divTreeId);
	if ( v_tree.jstree(true) ) {
		v_tree.jstree(true).destroy();//jstree 제거
	}
	if(v_tree) {
		v_tree.jstree({
			'core' : {
			 } ,
			 'plugins' : [ 'types', 'search' ],
			 'types' : {
				 'default' : {
					 'icon' : 'fa fa-folder'
				 }
			 }
		});

		v_tree.jstree(true).settings.core.data = v_treeData;
		v_tree.jstree(true).refresh();

		//직무트리 검색처리
		var v_PwCommonSearchTo = false;
		var v_PwCommonSearchJobNmInput = $('#'+divTreeTxtId);
		v_PwCommonSearchJobNmInput.keyup(function () {
			if(event.keyCode == 13) {
				return false;
			} else {
				if(v_PwCommonSearchTo) { clearTimeout(v_PwCommonSearchTo); }
				v_PwCommonSearchTo = setTimeout(function () {
					var v = v_PwCommonSearchJobNmInput.val();
				  	v_tree.jstree(true).search(v);
				}, 250);
			}
		});

		//트리 전체열기, 전체닫기처리
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
	}

}

/**
 * 역량 트리 : 데이터 설정
 * @param divTreeId
 * @param divTreeTxtId
 * @param companyId
 * @param effdt
 * @returns
 */
function fnSetCompTreeEffdt(divTreeId, divTreeTxtId, companyId, effdt) {
	//데이터 생성
	var v_treeData = [];

	if(effdt == null)	effdt = fnGetToday();

	$.ajax('/ha/admin/jobTree/PwHaAdminCompTreeEffList.json', {
		data: {companyId : companyId, effdt : effdt},
		dataType: "json",
		async: false //동기화처리
	}).done(function (data){
		for(i = 0; i < data.jobEffList.length; i++) {
			var v_pdob = data.jobEffList[i].pjobCd;
			if(v_pdob == "" || v_pdob == null){ v_pdob = "#"; }
			// 레벨
			var isOpen = {};
			if(data.jobEffList[i].treeLvl <= 1) isOpen = {"opened" : true, "selected" : true };
			else isOpen = {};
			v_treeData.push({
				'id': data.jobEffList[i].jobCd,
				'parent': v_pdob,
				'name': data.jobEffList[i].jobNm,
				/*'text': "[" + data.jobEffList[i].jobCd + "] - " + data.jobEffList[i].jobNm,*/
				'text': data.jobEffList[i].jobCd + " - " + data.jobEffList[i].jobNm,
				'state': isOpen,
				'lvl' : data.jobEffList[i].treeLvl
			});
		}
	});

	//트리생성
	var v_tree = $('#' + divTreeId);
	if ( v_tree.jstree(true) ) {
		v_tree.jstree(true).destroy();//jstree 제거
	}

	if(v_tree) {
		v_tree.jstree({
			'core' : {
			 } ,
			 'plugins' : [ 'types', 'search' ],
			 'types' : {
				 'default' : {
					 'icon' : 'fa fa-folder'
				 }
			 }
		});
		v_tree.jstree(true).settings.core.data = v_treeData;
		v_tree.jstree(true).refresh();

		//역량트리 검색처리
		var v_PwCommonSearchTo = false;
		var v_PwCommonSearchJobNmInput = $('#'+divTreeTxtId);
		v_PwCommonSearchJobNmInput.keyup(function () {
			if(event.keyCode == 13) {
				return false;
			} else {
				if(v_PwCommonSearchTo) { clearTimeout(v_PwCommonSearchTo); }
				v_PwCommonSearchTo = setTimeout(function () {
					var v = v_PwCommonSearchJobNmInput.val();
				  	v_tree.jstree(true).search(v);
				}, 250);
			}
		});

		//트리 전체열기, 전체닫기처리
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
	}

}

/**
 * 알림 등록
 * @param p_type 기준코드 NOTI_TYPE
 * @param p_title 제목
 * @param p_content 내용
 * @param p_companyId 회사코드
 * @param p_url [옵션]이동URL(업을경우 알림 페이지로 이동)
 * @param p_params [옵션]파라미터(JSON)
 * @returns
 */
function fnSetNotification(p_type, p_title, p_content, p_companyId, p_url, p_params) {
	showLoadingMask(); //로딩시작

 	//저장처리
	var procAjax = $.ajax({
		url: "/hs/ess/noti/HsEssNotiRegist.json", // HsEssNotiDAO.insertNoti
		type: "post",
		dataType: "json",
		data: {notiType: p_type, notiTitle: p_title, descr: p_content, companyId: p_companyId, url: p_url, params: p_params},
		success: function(data, textStatus, jqXHR) {
			$("#ajax").remove();

			if(data.message == null) {
	  			hideLoadingMask(); //로딩마감
				if(isSuccessMessage != false) fnCallSuccessMessage(g_request);
				return true;
			}
			else {
	  			hideLoadingMask(); //로딩마감
				Message.alertError(g_failRequest+"-" + data.message );
				return false;
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
	  		hideLoadingMask(); //로딩마감
			Message.alertError(textStatus);
			return false;
		}
	});
}

/**
 * select2 설정 초기화
 * @param p_isClass
 * @param p_code
 * @param p_obj
 * @param p_type
 * @param p_jsonUrl
 * @param p_params
 * @param p_defaultMsg
 * @returns
 */
function fnSetSelect2(p_isClass, p_code, p_obj, p_type, p_jsonUrl, p_params, p_defaultMsg) {

	if(!p_type) {
		p_type = "B";
	}

	var v_msg = p_defaultMsg;

	//B타입에 대한 파라미터 설정(grid에서 처리를 위해)
	var v_params = "";
	if(typeof p_params == "undefined" || p_params == "" || p_params == null) {

		//오브젝트 또는 값으로 넘어왔을 경우에 대한 분기처리
		var v_companyId = p_obj.attr("data-companyId");
		if($("#"+v_companyId).length > 0)
			v_companyId = $("#"+v_companyId).val();

		var v_businessUnit = p_obj.attr("data-businessUnit");
		var v_effdt = p_obj.attr("data-effdt");
		v_params = {codeClass: p_code, companyId: v_companyId, businessUnit: v_businessUnit, effdt: v_effdt, insEffStatus: "A"};
	} else {
		v_params = p_params;
		v_params.codeClass = p_code;
	}

	//신규입력건의 경우는 활성된 데이터만 입력가능해야 하기 때문에 조건 입력
	if( ($("#cmd") && $("#cmd").val() == "Create") || ($("#pageMode") && $("#pageMode").val() == "C") ) {
		if(v_params.insEffStatus == null || v_params.insEffStatus == "")
			v_params.insEffStatus = "A";
	}

	var v_initValue = p_obj.attr("data-init");

	switch(p_code) {
		case "SERVICE_ID" : //서비스ID
			fnSetSelect2Box(p_code, p_obj, fnGetSelect2Data(p_code, v_params, "B", ""));
			break;
		case "COMPANY_ID" : //회사
			if(typeof v_initValue == "undefined") p_obj.attr("data-init", g_defaultCompanyId); //초기값 입력
			fnSetSelect2Box(p_code, p_obj, fnGetSelect2Data(p_code, v_params, "B", ""));
			break;
		case "DEPT" : //부서 -> 자동완성으로 변경
			if(typeof v_initValue == "undefined") p_obj.attr("data-init", g_defaultDept); //초기값 입력
			fnSetSelect2Box(p_code, p_obj, fnGetSelect2Data(p_code, v_params, p_type, ""));
			break;
		case "EMPID" : //사원
			if(!v_msg) v_msg = g_searchEmpIdMsg;

			if(p_jsonUrl) {
				fnSetSelect2Search(p_code, p_obj, p_jsonUrl, p_params, v_msg);
			} else {
				fnSetSelect2Search(p_code, p_obj, "/hr/com/PwHrComEmpListSelect2.json", p_params, v_msg);
			}
			break;
		case "COUNTRY" : //국가
			fnSetSelect2Box(p_code, p_obj, fnGetSelect2Data(p_code, v_params, "B", ""));
			break;
		case "CURRENCY" : //통화
			fnSetSelect2Box(p_code, p_obj, fnGetSelect2Data(p_code, v_params, "B", ""));
			break;
		default :
			if(p_type == "B") { //일반 selectbox
				fnSetSelect2Box(p_code, p_obj, fnGetSelect2Data(p_code, v_params, p_type, ""));

			} else if(p_type == "C") { //일반 selectbox (커스텀 데이터)
				fnSetSelect2Box(p_code, p_obj, fnGetSelect2Data(p_code, p_params, p_type, p_jsonUrl));

			} else { //자동완성 selectbox
				if(p_jsonUrl) {
					fnSetSelect2Search(p_code, p_obj, p_jsonUrl, p_params, v_msg);
				} else {
					fnSetSelect2Search(p_code, p_obj, "/hs/common/com/HsCommonComCodeIdSelect2.json", p_params, v_msg); // HsAdminCodeMngDAO.selec2CodeIdList
				}
			}
	}
}

/**
 * SERVICE_ID검색팝업
 * @param schObj
 * @returns
 */
function fnPopServiceId(schObj){
	schObj.attr("title", g_serviceIdMsg);
	var url = "/hs/system/pop/HsSystemDivServiceIdList.do";
	fnPrompt(schObj, url, 0, 0);
}

/**
 * BUSINESSUNIT검색팝업
 * @param schObj
 * @returns
 */
function fnPopBusinessUnit(schObj){
	schObj.attr("title", g_busiUnitMsg);
	var url = "/hs/system/pop/HsSystemDivBusinessUnitList.do";
	fnPrompt(schObj, url, 0, 0);
}

/**
 * 사원검색팝업
 * @param schObj
 * @returns
 */
function fnPopEmpId(schObj){
	//입력란이 비활성일 경우 처리 제외
	var v_objId = schObj.attr("data-empno");
	if($("#"+v_objId).attr("readonly") == "readonly" || $("#"+v_objId).attr("disabled") == "disabled") return false;

	schObj.attr("title", g_searchEmplMsg);
	var url = "/hs/system/pop/HsSystemDivEmpIdList_real.do";
	fnPromptSearch(schObj, url, 1300, 0);
}

/**
 * 사원검색팝업-그리드용
 * @param schObj
 * @param rowId
 * @returns
 */
function fnPopEmpIdGrid(schObj, rowId){

	//입력란이 비활성일 경우 처리 제외
	var v_objId;
	if(schObj.attr("data-id").indexOf(rowId) == -1) { //최초 생성되면 id부여 필요없음
		v_objId = rowId + "_" + schObj.attr("data-empno");
	} else {
		v_objId = schObj.attr("data-empno");
	}

	if($("#"+v_objId).attr("readonly") == "readonly" || $("#"+v_objId).attr("disabled") == "disabled") return false;

	//그리드 입력폼 id 설정 (rowid + "_" + 속성값)
	var v_companyId, v_Id, v_name, v_empno, v_dept, v_jobPosition;

	//회사 는 상단폼에서 검색
	//if(schObj.hasAttr("data-companyId")) schObj.attr("data-companyId", rowId + "_" + schObj.attr("data-companyId"));

	if(schObj.attr("data-id").indexOf(rowId) == -1) { //최초 생성되면 id부여 필요없음
	   	//기본 id, name
		if(schObj.hasAttr("data-id")) schObj.attr("data-id", rowId + "_" + schObj.attr("data-id"));
		if(schObj.hasAttr("data-nm")) schObj.attr("data-nm", rowId + "_" + schObj.attr("data-nm"));

	   	//사원정보용
		if(schObj.hasAttr("data-empno")) schObj.attr("data-empno", rowId + "_" + schObj.attr("data-empno"));
		if(schObj.hasAttr("data-dept")) schObj.attr("data-dept", rowId + "_" + schObj.attr("data-dept"));
		if(schObj.hasAttr("data-jobPosition")) schObj.attr("data-jobPosition", rowId + "_" + schObj.attr("data-jobPosition"));
	}

	schObj.attr("title", g_searchEmplMsg);
	var url = "/hs/system/pop/HsSystemDivEmpIdList_real.do";
	fnPromptSearch(schObj, url, 1300, 0);
}

/**
 * 부서 검색
 * @param schObj
 * @returns
 */
function fnPopDept(schObj){
	schObj.attr("title", g_orgSearchMsg);
	var url = "/hs/system/pop/HsSystemDivDeptList_real.do";
	fnPromptSearch(schObj, url, 1024, 0);
}

/**
 * 조직검색팝업-그리드용
 * @param schObj
 * @param rowId
 * @returns
 */
function fnPopDeptGrid(schObj, rowId){

	//입력란이 비활성일 경우 처리 제외
	var v_objId;
	if(schObj.attr("data-id").indexOf(rowId) == -1) { //최초 생성되면 id부여 필요없음
		v_objId = rowId + "_" + schObj.attr("data-id");
	} else {
		v_objId = schObj.attr("data-id");
	}
	if($("#"+v_objId).attr("readonly") == "readonly" || $("#"+v_objId).attr("disabled") == "disabled") return false;

	//그리드 입력폼 id 설정 (rowid + "_" + 속성값)
	var v_companyId, v_Id, v_name, v_empno, v_dept, v_jobPosition;

	//회사 는 상단폼에서 검색
	//if(schObj.hasAttr("data-companyId")) schObj.attr("data-companyId", rowId + "_" + schObj.attr("data-companyId"));

	if(schObj.attr("data-id").indexOf(rowId) == -1) { //최초 생성되면 id부여 필요없음
	   	//기본 id, name
		if(schObj.hasAttr("data-id")) schObj.attr("data-id", rowId + "_" + schObj.attr("data-id"));
		if(schObj.hasAttr("data-nm")) schObj.attr("data-nm", rowId + "_" + schObj.attr("data-nm"));
	}

	schObj.attr("title", g_orgSearchMsg);

	//특정 상위부서 팝업처리 (org_lvl=180) 부서내 이동발령
	var url = "";
	if(schObj.attr("data-options") == 'gridPDept'){
		url = "/hs/system/pop/HsSystemDivPDeptList.do";
		fnPromptSearch(schObj, url, 600, 0);
	}else{
		schObj.attr("data-options", "gridDept");
		url = "/hs/system/pop/HsSystemDivDeptList_real.do";
		fnPromptSearch(schObj, url, 1000, 0);
	}

}

/**
 * 직무검색팝업
 * @param schObj
 * @returns
 */
function fnPopJobCd(schObj){
	schObj.attr("title",g_jobclsSearchMsg);
	var url = "/hs/system/pop/HsSystemDivJobCdList.do";
	fnPromptSearch(schObj, url, 1024, 0);
}

/**
 * 직무검색 -그리드용
 * @param schObj
 * @param rowId
 * @returns
 */
function fnPopJobCdGrid(schObj, rowId){

	//그리드 입력폼 id 설정 (rowid + "_" + 속성값)
	var v_companyId, v_Id, v_name, v_empno, v_dept, v_jobPosition;

	//회사 는 상단폼에서 검색
	//if(schObj.hasAttr("data-companyId")) schObj.attr("data-companyId", rowId + "_" + schObj.attr("data-companyId"));

	if(schObj.attr("data-id").indexOf(rowId) == -1) { //최초 생성되면 id부여 필요없음
	   	//기본 id, name
		if(schObj.hasAttr("data-id")) schObj.attr("data-id", rowId + "_" + schObj.attr("data-id"));
		if(schObj.hasAttr("data-nm")) schObj.attr("data-nm", rowId + "_" + schObj.attr("data-nm"));

	}
	schObj.attr("title", g_jobclsSearchMsg);
	var url = "/hs/system/pop/HsSystemDivJobCdList.do";

	fnPromptSearch(schObj, url, 400, 0);
}

/**
 * 첨부파일 팝업
 * @param schObj
 * @param v_fileSet 파일을 한꺼번에 올린 여러개의 파일들의 묶음 Key
 * @param v_prgArea 필드에 업무 모듈을 지정한다. (ex: ZZ, HR, PY, TL 등)
 * @param v_prgName 필드에 프로젝트 명을 지정한다.
 * @param v_fileMode newMode가 아니면 다운로드 버튼을 뺀 나머지 모든 버튼 숨김처리한다.
 * @returns
 */
function fnPopFileDetail(schObj, v_fileSet, v_prgArea, v_prgName, v_fileMode){
	var url = "/hs/system/fileMng/HsSystemUpDownFilePop.do";
	var params = "{fileSet:'" + v_fileSet + "',prgArea:'" + v_prgArea + "',prgName:'" + v_prgName + "',fileMode:'" + v_fileMode + "'}";
	schObj.attr("data-params", params);
	schObj.attr("title", g_uploadFile);

	fnPromptSearch(schObj, url, 600, 0);
}

/**
 * 파일 목록(다운로드용)
 * @param p_objId
 * @returns
 */
function fnPopFileList(p_objId) {
		var obj = p_objId;
		if(!obj.attr('data-id') || !$("#"+obj.attr('data-id')).val()) {
			Message.alert(g_errorsUpload04);
			return false;
		}
		var url = "/hs/system/fileMng/HsSystemDownloadFile.do";
		var params = "{fileSetNm:'" + obj.attr('data-id') +"'}";
		obj.attr("data-params", params);
		obj.attr("title", g_download);
		fnPrompt(obj, url, 550, 300);
}

/**
 * 파일 다운로드
 * @param v_attachId
 * @returns
 */
function fnFileDownload(v_attachId){

	if(v_attachId==undefined||v_attachId==null||v_attachId==""){
		// 해당데이터가 없습니다.
		Message.alert(g_nodataMsg);
	}else{
		var v_param = {attachId: v_attachId};
		fnPopWithParams("downloadFile", "/hs/system/fileMng/HsSystemFileDownload.do", v_param);
	}

	return false;
}

/**
 * 파일 삭제
 * @param v_attachId 파일을 한꺼번에 올린 여러개의 파일들의 묶음 Key
 * @param v_delChk
 * @param v_callback
 * @returns
 */
function fnFileDelete(v_attachId, v_delChk, v_callback){

	if(v_attachId==undefined||v_attachId==null||v_attachId==""){
		// 해당데이터가 없습니다.
		Message.alert(g_nodataMsg);

	}else{
		// 삭제하시겠습니까?
		Message.confirm(g_del, function(isOk){
			if(isOk){

				showLoadingMask(); //로딩시작

			   	//삭제처리
				var procAjax = $.ajax({
					url: "/hs/system/fileMng/HsSystemFileDelete.json",
					type: "post",
					dataType: "json",
					data: {attachId: v_attachId, delChk: v_delChk},
					success: function(data, textStatus, jqXHR) {
						$("#ajax").remove();

						if(data.message == null) {
				  			hideLoadingMask(); //로딩마감
							if(isSuccessMessage != false) fnCallSuccessMessage(g_suDel);
							if(v_callback)
								v_callback();
							return true;
						}
						else {
				  			hideLoadingMask(); //로딩마감
							Message.alertError( data.message );
							if(v_callback)
								v_callback();
							return false;
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
				  		hideLoadingMask(); //로딩마감
						Message.alertError(textStatus);
						if(v_callback)
							v_callback();
						return false;
					}
				});

			}else return false;
		});
	}

	return false;
}

/**
 * RD 호출 공통함수 - 프린터키사용
 * @param p_printId
 * @param p_mid
 * @param p_pid
 * @param p_title
 * @returns
 */
function fnPopReportViewPrintId(p_printId, p_mid, p_pid, p_title) {
	var v_url = "/ReportingServer/html5/rdViewNxGhr.jsp";

	v_url += "?mid=" + p_mid;
	v_url += "&pid=" + p_pid;
	v_url += "&t=" + p_title;
	v_url += "&p=["+p_printId+"]";
	var v_specs = "left=10,top=10,fullscreen=yes";
	v_specs += ",toolbar=no,menubar=no,status=no,scrollbars=no,resizable=no";
	window.open(v_url, '', v_specs);
}

/**
 * RD 호출 공통함수 - 리포트 보기 팝업
 * @param p_params
 * @param p_mid
 * @param p_pid
 * @param p_title
 * @param p_bt
 * @returns
 */
function fnPopReportView(p_params, p_mid, p_pid, p_title, p_bt) {
	var v_url = "";
	v_url = g_rdUrl;

	v_url += "?mid=" + p_mid;
	v_url += "&pid=" + p_pid;
	v_url += "&t=" + p_title;
	v_url += "&p=" + p_params;
	v_url += "&bt=" + p_bt;
	//console.log(v_url);
	var v_specs = "left=10,top=10,fullscreen=yes";
	v_specs += ",toolbar=no,menubar=no,status=no,scrollbars=no,resizable=no";
	window.open(v_url, '', v_specs);
}

/**
 * RD 호출 공통함수 - 리포트 파일 다운로드
 * @param p_params
 * @param p_mid
 * @param p_pid
 * @param p_title
 * @param p_bt
 * @returns
 */
function fnReportDownload(p_params, p_mid, p_pid, p_title, p_bt) {

	var v_url = "/hs/system/inc/HsSystemIncDownloadReportFile.do";

	var v_params = {mid : p_mid, pid : p_pid, title:p_title,  params : p_params}  ;

	var mrdPath =  "./"+p_mid + "/"+ p_pid +".mrd";

	var invoker = new m2soft.crownix.ReportingServerInvoker('http://100.1.2.103:7090/ReportingServer/service');
		invoker.addParameter('opcode', '500');
		invoker.addParameter('mrd_path', mrdPath);
		invoker.addParameter('mrd_param', '/rfn [sample.txt]');
		invoker.addParameter('export_type', 'pdf');
		invoker.addParameter('protocol', 'sync');

		invoker.invoke();
}

/**
 * 외부 강사 검색 팝업
 * @param schObj
 * @returns
 */
function fnPopExternalInstructor(schObj){
	//입력란이 비활성일 경우 처리 제외
	var v_objId = schObj.attr("data-empno");
	if($("#"+v_objId).attr("readonly") == "readonly" || $("#"+v_objId).attr("disabled") == "disabled") return false;

	schObj.attr("title", g_instructor);
	var url = "/tm/admin/trainingcourse/PwTmAdminDivExternalInstructor.do";

	fnPromptSearch(schObj, url, 800, 0);
}

/*
	전자결재 호출
		p_obj : 값전달용 오브젝트
		p_companyId : 회사코드
		p_apprType : 결재업무타입(결재최종 완료 시 후처리가 필요한 경우 결재타입을 찾기위해 사용)
		p_apprId : 수정시 조회할 결재 ID가 들어있는 입력 폼 ID (결재 기안후 결재ID가 넘어옴)
		p_title : 결재제목
		p_contentsId : 결재내용이 담긴 최상위 html 태그ID
		p_modiYn : 결재내용 수정여부
		p_modiDtYn : 기안일 수정 여부
		p_callBack : 완료 후 처리할 호출 펑션명
	참고 : 근태관리 / 일근태관리 / 연장근로 신청

	* 최종 결재완료 후 처리가 필요한 경우 PW_ZZ_APPR_PG.main 패키지를 수정한다.
 */
function fnPopApprReq(p_obj, p_companyId, p_apprType, p_apprId, p_title, p_contentsId, p_modiYn, p_modiDtYn, p_callBack) {
	var v_url = "/hs/ess/appr/HsEssApprDivReq.do"; //전자결재 URL
	p_obj.attr("data-companyId", p_companyId);
	p_obj.attr("data-apprType", p_apprType);
	p_obj.attr("data-apprId", p_apprId);
	p_obj.attr("title", g_titleMsg);
	p_obj.attr("data-apprTitle", p_title);
	p_obj.attr("data-apprMemo", p_contentsId);
	p_obj.attr("data-apprModiYn", p_modiYn);
	p_obj.attr("data-apprModiDtYn", p_modiDtYn);
	p_obj.attr("data-callback", p_callBack);
	fnPrompt(p_obj, v_url, 1400, 800);
}


/**
 * 서명 불러오기
 * p_ObjId : 서명 이미지가 보여질 <img>태그의 id값
 * p_eSignId : 서명파일 아이디
 */
function fnEmpSignature(p_ObjId, p_eSignId) {
	var v_imgObj = $("#" + p_ObjId);
	var timestamp = new Date().getTime();
	v_imgObj.attr("src", "/hc/common/signature/HsSystemSignatureDownload.do?eSignId=" + p_eSignId + "&cache=" + timestamp);
	v_imgObj.show();

	// 이미지가 없을 때 기본 이미지 보여주기
	/*if(!v_imgObj.hasAttr("onError") && !v_imgObj.hasAttr("onerror")) {
		v_imgObj.attr("onError", "this.src='/opennote/img/sub/img_profile.png;'");
	}*/
}
