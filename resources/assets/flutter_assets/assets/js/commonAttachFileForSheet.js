
var g_fileList = new Array(); // global file List

var g_file = {"sheetObj" : null
		   , "fnOpenSheetFilePop": function(p_params, p_layoutGubun, p_sheetObj, p_sizeOption) {

			   this.sheetObj = p_sheetObj;

			    var url = "/hc/common/fileAttach/fileAttachModalSheetFilePop.do";
				var srcObj = $("<div>");

				// 전달파라미터 설정
				srcObj.attr("data-params", JSON.stringify(p_params));
//				srcObj.attr("data-sheetObj", p_params.sheetRowObj);
//				srcObj.attr("data-sheetRowId", p_params.id); // sheetRowId
				
				/// 팝업 사이즈 setting - width / height
				var width = 0, height = 0;
				if(p_sizeOption){
					width = p_sizeOption.width ? p_sizeOption.width : 800;
					height = p_sizeOption.height ? p_sizeOption.height : 0;
				}

				if (p_layoutGubun == "SUB") {
					fnSubPrompt(srcObj, url, width, height);
				} else {
					fnMainPrompt(srcObj, url, width, height);
				}
		   }

	};


/* 그리드 내 팝업 오픈
 * p_params : fileSet(파일셋)
			, id(row id)
			, formId(projectCode)
			, pageMode(뷰모드 첨부가능하면 null)
			, sheetRowObj(sheetId 문자열)
			, p_sizeOption(팝업 사이즈 옵션 - width, height)
 */
function fnOpenSheetFilePop(p_params, p_layoutGubun, p_sheetObj, p_sizeOption) {
	/*
	var url = "/hc/common/fileAttach/fileAttachModalSheetFilePop.do";
	var srcObj = $("<div>");

	// 전달파라미터 설정
	srcObj.attr("data-params", JSON.stringify(p_params));     // params
//	srcObj.attr("data-sheetObj", p_params.sheetRowObj);       // eval 함수 쓰지 않기 위해 상단의 방식으로 넘기고 사용하지 x
//	srcObj.attr("data-sheetRowId", p_params.id);              // ..

	if (p_layoutGubun == "SUB") {
		fnSubPrompt(srcObj, url, 800, 0);
	} else {
		fnMainPrompt(srcObj, url, 800, 0);
	}
	*/
	g_file.fnOpenSheetFilePop(p_params, p_layoutGubun, p_sheetObj, p_sizeOption);
}


/* formdata 에 첨부파일 데이터 append */
function fnSetFileFormDataIBSheet(p_formData) {

	for ( var key in g_fileList) {

		for (var idx = 0; idx < g_fileList[key].length; idx++) {
			p_formData.append(key, g_fileList[key][idx]);
		}
	}
}


/* 저장 후 파일리스트 콜백처리 */
function fnCallbackSaveSheetfile(p_fileSetMap, p_fileSetColId, p_fileCntColId) {

	// 새로 저장한 fileSet set
	if( !isEmpty(p_fileSetMap)  ) {

		for(var key in p_fileSetMap) {
			var rowId = key;

			var changeRow = g_file.sheetObj.getRowById(rowId);

			if(changeRow) {	// 해당 row가 있을 경우만
				g_file.sheetObj.setValue(changeRow, p_fileSetColId, p_fileSetMap[key]);    // fileSetMap의 key(rowId)로 저장한 fileSet을 setValue 해줌
				g_file.sheetObj.setValue(changeRow, p_fileCntColId, 0 );                   // 수정한 fileCnt 0으로 초기화
			}
		}
	}

}


/* 글로벌 파일리스트 초기화 */
function fnInitGlobalFileList() {

	// 글로벌 파일리스트 초기화
	g_fileList = [];

}


