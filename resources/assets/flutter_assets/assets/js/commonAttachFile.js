
var g_commonAttach =  {

	"fnCallbackDeleteFile": function() {}     // CALLBACK 삭제
};

var $fileList = {};    // attach file List
var g_fileObjId = "";  // file object id

//첨부파일 컴포넌트 설정
function fnSetFileComponent(p_loadObj, p_fileObjId, p_fileSet, p_pageMode, p_params, callback) {

	var v_params = {fileSet  : p_fileSet
			      , fileObjId: p_fileObjId
			      , pageMode : p_pageMode
			      };
	if(p_params) $.each(p_params, function(key, value){
		v_params[key] = value;
	});
	// load 첨부파일 컴포넌트
	p_loadObj.load("/hc/common/fileAttach/fileAttachComponent.do", v_params, callback);
}


//첨부파일 팝업 설정 (목록)
function fnOpenFileListPop(p_fileObjId, p_fileSet) {
	var p_params = {
			fileSet  : p_fileSet,
			fileObjId : p_fileObjId
	};

	var opt = { "data-params"  : JSON.stringify(p_params) };
    var srcObj = $("<div />", opt);

	fnMainPrompt(srcObj, "/hc/common/fileAttach/fileAttachModal.do", 800, 500);
}


/* formdata 에 첨부파일 데이터 append */
function fnSetFileFormData(p_formData) {

	for(var key in $fileList) {

		for(var idx = 0; idx < $fileList[key].length ; idx++){
			p_formData.append(key , $fileList[key][idx]);
		}
	}
}

/* 첨부파일 수 */
function fnGetAtchFileCnt(v_obj) {
	var v_length = 0;

	if($fileList["atchFileObj"]) {    // 첨부한 파일 수
		v_length += $fileList[v_obj].length;
	}

	if( $("#" + v_obj) ) {    // 업로드 된 파일 수
		v_length += $("#list_" + v_obj + " div").length;
	}
	return v_length;
}

//파일다운로드
function fnDownloadFile(p_attachId){
    if( p_attachId ){
		var v_param = {attachId: p_attachId};
		fnPostMove("/hc/common/attachFile/downloadAttachFile.do", v_param);
        //location.href = "/hc/common/attachFile/downloadAttachFile.do?attachId=" + p_attachId;
    }else{
        Message.alert(g_errorsUpload04);
    }
}



//파일삭제
function fnDeleteFile(p_attachId, p_maxCnt){

    //이전파일삭제
    if ( p_attachId ) {
        Message.confirm(g_del, function(){
            var v_url = '/hc/common/attachFile/deleteAttachFile.json';

            var v_params = {
                attachId : p_attachId
            };

            var data = fnGetJsonData(v_url, v_params);

            if(data.success){
            	var v_objId = $("#" + p_attachId).parent().attr("id");
            	v_objId = v_objId.substring(5);

            	$("#" + p_attachId).remove();    // 해당 파일 html 삭제

            	fnShowAttachFileList(v_objId);    // attach file 영역 diplay 처리

            	g_commonAttach.fnCallbackDeleteFile();    // CALLBACK 파일삭제

            	if(p_maxCnt != "" && p_maxCnt != undefined){
        			if($("#" + p_attachId).find("input").length >= p_maxCnt){
        				$("#divBtnUpload").hide();
        				$("div[id^=file_table_]").hide();
        				$("#btnSavePop").hide();
        			}else{
        				$("#divBtnUpload").show();
        				$("div[id^=file_table_]").show();
        				$("#btnSavePop").show();

        				/*if($("#" + p_attachId).find("input").length == 0){
        					$("p[id^=fileAR]").remove();
        				}*/
        			}
        		}else{
        			$("#divBtnUpload").show();
        			$("div[id^=file_table_]").show();
        			$("#btnSavePop").show();
        		}

            } else{
                Message.alert(data.message);
            }
        });
    }
}

/* 파일 목록 조회 */
function fnSearchAttachFile(p_objId, p_modal, p_maxCnt) {
    var v_fileSet = $("#fileSet_" + p_objId).val();

	if(v_fileSet) {
		var v_params = {fileSet: v_fileSet};
		fnSearchJsonAjax(v_params, "/hc/common/fileAttach/selectAttachFileList.json", function(data){
			fnCallbackSearchFile(data, p_objId, p_modal, p_maxCnt);
		});
	}
}

/* callback 파일 목록 조회 */
function fnCallbackSearchFile(data, p_objId, p_modal, p_maxCnt) {

//	console.log("fnCallbackSearchFile data==>>", data);
//	console.log("fnCallbackSearchFile p_objId==>>", p_objId);
//	console.log("fnCallbackSearchFile p_modal==>>", p_modal);
	if(data && data.rows) {
		var dataMap;
		var vHtml = "";

		for(var idx in data.rows) {
			dataMap = data.rows[idx];
			vHtml += "<div class='input' id='" + dataMap.attachId + "'>"
			vHtml +=     "<input type='text' class='text' value='" + dataMap.orgFileName + "' readonly />";    // 파일명
			vHtml +=     "<button type='button' class='btn_input' onClick='javascript:fnDownloadFile(\"" + dataMap.attachId + "\");'>";
			vHtml +=         "<img src='/opennote/img/sub/i_down.svg' alt=''></button>";    // download img
			// 첨부파일 목록 modal에서 호출한건지 체크 p_modal=true면 modal
			if( !p_modal ){
				vHtml +=     "<button type='button' class='btn_input' onClick='javascript:fnDeleteFile(\"" + dataMap.attachId + "\", \"" + p_maxCnt + "\");' data-id='btnHsFileDel' data-del-id='btnHsFileDel_" + p_objId + "'>";
	            vHtml +=         "<img src='/opennote/img/sub/i_del.svg' alt=''></button>";    // delete img
			}
            vHtml += "</div>"
		}

		$("#list_" + p_objId).html(vHtml);

		fnShowAttachFileList(p_objId);

		if(dataMap != null && (p_maxCnt != "" && p_maxCnt != undefined)){
			if($("#" + dataMap.attachId).find("input").length >= p_maxCnt){
				$("#divBtnUpload").hide();
				$("div[id^=file_table_]").hide();
				$("#btnSavePop").hide();
			}else{
				$("#divBtnUpload").show();
				$("div[id^=file_table_]").show();
				$("#btnSavePop").show();
			}
		}else{
			$("#divBtnUpload").show();
			$("div[id^=file_table_]").show();
			$("#btnSavePop").show();
		}
	}

}

/* 사원 이미지 파일 업로드 */
function fnEmpImageUpload(fileObj, p_params) {
	//저장처리
	var formData = new FormData();
    formData.append(fileObj.attr("name"), fileObj[0].files[0]);

    if ( fnToString(p_params) != "" ) {
    	$.each(p_params, function(key, val){
			formData.append(key, val);
		});
    }

	$.ajax({
		url: "/hc/common/attachFile/savePhotoAttachFile.json",
		type: 'POST',
		//dataType: "json",
		data: formData,
		processData: false,
		contentType: false,
		async: false, //동기화처리
		success: function(data, textStatus, jqXHR) {
			$("#ajax").remove();

			//데이터 처리 시간 관계상 로딩 이미지 처리를 위해 수정함.
			if(data.message == null) {
				fnCallSuccessMessage(g_saveSucc, "", data);
				fnSearchEmpImage(p_params.imgTagId, p_params.empId);
			} else {
				Message.alertError(g_saveFail+"-" + data.message );
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			Message.alertError(g_saveFail + jqXHR.responseText );
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

function fnShowAttachFileList(p_objId) {
	if( $("#list_" + p_objId + " div").length > 0 ) {
		$("#list_" + p_objId).show();
	} else {
		$("#list_" + p_objId).hide();
	}
}

// 이미지 첨부파일 화면에 출력
function fnViewAttachFileImage(p_obj, p_attachId){
	p_obj.attr("src", "/hc/common/attachFile/downloadAttachImageFile.do?attachId="+p_attachId+"&cache="+new Date().getTime());
}

/*
 * 첨부파일 팝업
 * param p_objId 팝업 소스 아이디
 * param p_layoutGubun 메인/서브 구분
 * param p_params 전송 파라메터
		v_params = {
			  id : 첨부 파일별 구분 아이디
			, fileSet : 2345
			, limitCnt : 1
			, limitSize : "3MB"
			//, allowExts : "JPG,JPEG,PNG,GIF,PDF" - 아직 미 구현
			, saveCallback : "fnAfterSaveFile"
			, deleteCallback : "fnSearchList"
		}
 */
function fnOpenFilePop(p_objId, p_layoutGubun, p_params, p_mode) {

	var url = "/hc/common/fileAttach/fileAttachPop.do";
	var srcObj = $("#"+p_objId);

	// 전달파라미터 설정
	srcObj.attr("data-params", JSON.stringify(p_params));     // params

	if (p_layoutGubun == "SUB") {
		fnSubPrompt(srcObj, url, 800, 0, p_mode);
	} else {
		fnMainPrompt(srcObj, url, 800, 0, true, p_mode);     // p_mode : 모바일
//		fnMainPrompt(srcObj, url, 800, 0);
	}

}

// 파일 리스트 상세 팝업 추가
function fnAttachView(p_seq, p_fileSet){
	fnOpenFilePop("popId", "SUB", {id: p_seq, fileSet : p_fileSet, pageMode : 'READ'});

	$("#pwModalSub").css("height", "auto");
}
