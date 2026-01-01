<%@ page language="java" contentType="application/javascript; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import ="egovframework.com.cmm.util.EgovUserDetailsHelper" %>
<%@ page import ="egovframework.com.cmm.service.EgovProperties" %>

// window.open 사용시 문제가 발생되는 경우 document.domain 추가함
var strServerName = '<%=request.getServerName()%>';
document.domain = strServerName;
//console.log("strServerName : ", strServerName);


//전역변수 선언부
var g_ok = "<span data-message-code="label.ok"></span>";
var g_yes = "<span data-message-code="label.yes"></span>";
var g_no = "<span data-message-code="label.no"></span>";
var g_cancel = "<span data-message-code="label.cancel"></span>";
var g_add = "<span data-message-code="label.add"></span>";
var g_title_alert = "<span data-message-code="label.alert"></span>";
var g_title_confirm = "<span data-message-code="label.check"></span>";
var g_title_prompt = "<span data-message-code="label.input"></span>";
var g_deleteRow = "<span data-message-code="label.delete"></span>";
var g_chkDeleteRow = "<span data-message-code="label.deleteSelected"></span>";
var g_deleteSelBtn = "<span data-message-code="label.deleteSelected"></span>";
var g_refreshBtn = "<span data-message-code="label.refresh"></span>";
var g_uploadFile = "<span data-message-code="label.fileUpload"></span>";
var g_titleMsg = "<span data-message-code="label.apprReq"></span>";
var g_serviceIdMsg = "<span data-message-code="label.serviceId"></span>";
var g_busiUnitMsg = "<span data-message-code="label.bizUnit"></span>";
var g_searchEmplMsg = "<span data-message-code="label.searchEmpl"></span>";
var g_jobclsSearchMsg = "<span data-message-code="label.jobclsSearch"></span>";
var g_orgSearchMsg = "<span data-message-code="label.deptSearch"></span>";
var g_reqNameMsg = "<span data-message-code="label.nameRegister"></span>";
var g_searchInfo = "<span data-message-code="label.searchValueInput"></span>";
var g_download = "<span data-message-code="label.download"></span>";
var g_instructor = "<span data-message-code="label.trainerSearch"></span>";
var g_searchCostctMsg = "<span data-message-code="label.searchCostct"></span>";

var g_validMsg 			= "<span data-message-code="message.validInputAllDate"></span>";
var g_number 			= "<span data-message-code="message.validInputNumOnly"></span>";
var g_searchEmpIdMsg 	= "<span data-message-code="message.validInputEmpIdNm"></span>";

var g_save 				= "<span data-message-code="message.saveConfirm"></span>";
var g_saveSucc 			= "<span data-message-code="message.saveSuccess"></span>";
var g_saveFail			= "<span data-message-code="message.saveFailure"></span>";
var g_request 			= "<span data-message-code="message.requestSuccess"></span>";
var g_failRequest 		= "<span data-message-code="message.requestFailure"></span>";
var g_msg_duplication 	= "<span data-message-code="message.hs.duplication"></span>";

var g_del 				= "<span data-message-code="message.deleteConfirm"></span>";
var g_suDel 			= "<span data-message-code="message.deleteSuccess.long"></span>";
var g_failDel 			= "<span data-message-code="message.deleteFailure"></span>";
var g_deleteSel 		= "<span data-message-code="message.deleteSelectedConfirm"></span>";

var g_errReq			= "<spring:message code='message.required'/>";
var g_errorsUpload04	= "<spring:message code='message.notExistFile'/>";
var g_nodataMsg 		= "<spring:message code='message.notExistData'/>";

//ibsheet 적용
var g_ibUnitCnt 		= "<spring:message code='label.unitCnt' />"; // 개
var g_ibEnterSearchNm	= "<spring:message code='message.ibEnterSearchNm' />"; // 검색어를 입력해 주세요.
var g_ibFilter			= "<spring:message code='label.filter' />"; // 필터
var g_ibSelect			= "<spring:message code='label.select' />"; // 선택
var g_ibPrev			= "<spring:message code='label.prev' />"; // 이전
var g_ibNext			= "<spring:message code='label.next' />"; // 다음
var g_ibCase			= "<spring:message code='label.case' />"; // 건

var g_ibDetail			= "<spring:message code='label.detail' />"; // 상세
var g_ibInput			= "<spring:message code='label.input' />"; // 입력
var g_ibPreview			= "<spring:message code='label.preview' />"; // 미리보기
var g_ibHistory			= "<spring:message code='label.he.historyList' />"; // 이력
var g_ibAttach			= "<spring:message code='label.attach' />"; // 첨부
var g_ibUpdate			= "<spring:message code='label.update' />"; // 수정
var g_ibDelete			= "<spring:message code='label.delete' />"; // 삭제


var g_hideColumn		= "<spring:message code='label.hideColumn' />"; // 컬럼 감추기
var g_unhideColumns		= "<spring:message code='label.unhideColumns' />"; // 컬럼 감추기 취소
var g_saveColumnInfo	= "<spring:message code='label.saveColumnInfo' />"; // 컬럼 정보 저장
var g_cancelColumnInfo	= "<spring:message code='label.cancelColumnInfo' />"; // 컬럼 정보 저장 취소
var g_createFilterRow	= "<spring:message code='label.createFilterRow' />"; // 필터행 생성
var g_hideFilter		= "<spring:message code='label.hideFilter' />"; // 필터 감추기

var g_fixCheckboxYnValue = true;

var g_rdUrl = "<%= EgovProperties.getProperty("Globals.ReportServer") %>/ReportingServer/html5/rdViewNxGhr.jsp";
var g_gwUrl = "<%= EgovProperties.getProperty("GLOBALS.GWURL") %>/cefAppLegacyDispatcher.jsp";

//로그인 사원정보 기본값
var g_defaultCompanyId;
var g_defaultCompany = "${loginData.companyId}";
var g_defaultServiceId = "${loginData.serviceId}";
var g_defaultDept = "${loginData.employeeInfoVO.dept}";
var g_selectboxMgrCompanyList = "${LoginVO.selectboxMgrCompanyList}";
var g_selectboxAllCompanyList = "${LoginVO.selectboxAllCompanyList}";
var g_selectboxSecDeptList = "${LoginVO.selectboxSecDeptList}";
var g_selectboxMgrDeptList ="${LoginVO.selectboxMgrDeptList}";
var g_selectboxAllDeptList ="${LoginVO.selectboxAllDeptList}";
var g_empidGird = "${LoginVO.empId}";
var g_nameGird = "${LoginVO.name}";
var g_loginLangCd = "${LoginVO.langCd}";
var g_realGridFolder = "${realGridFolder}";
var g_userId = "${LoginVO.userId}";
var g_seedDataYn = (g_userId == "SEED_DATA") ? "Y" : "N";

function diffMsg(iMonth,iLastDay){
	var msg ="<spring:message code="errors.date.diff" arguments="'+ iMonth +','+ iLastDay +'" />";
	return msg;
}
function monthMsg(iMonth){
	var msg ="<spring:message code="errors.date.month" arguments="'+ iMonth +'" />";
	return msg;
}
function yearMsg(iYear){
	var msg ="<spring:message code="errors.date.year" arguments="'+ iYear +'" />";
	return msg;
}
<c:import charEncoding="utf-8" url="js/commonJavaScript.js" />
<c:import charEncoding="utf-8" url="js/commonStringUtil.js" />
<c:import charEncoding="utf-8" url="js/commonBusiness.js" />
<c:import charEncoding="utf-8" url="js/commonPopup.js" />
<c:import charEncoding="utf-8" url="js/commonCalendar.js" />
<c:import charEncoding="utf-8" url="js/commonOpennoteJavaScript.js" />
<c:import charEncoding="utf-8" url="js/commonIBSheetJS.js" />

