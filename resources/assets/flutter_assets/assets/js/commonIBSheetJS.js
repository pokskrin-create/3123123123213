// IBSheet 생성
function fnIbsCreate(sheetOption){
	try { eval(sheetOption.id).dispose(); } catch (e) {}

	// IBSheet 객채용
	var sheetInfo = {};
	var p_sheetDivObj = $("#" + sheetOption.el);

	// 추가 Cfg option
	var cfgAdd = sheetOption.Cfg;
	var defAdd = sheetOption.Def;

	// 기본 옵션
	sheetOption.Cfg = {
//			Style: "IBSP",
			SearchMode : 2,
			Alternate : 2, 		// 짝수행에 하이라이트 표시
			FitWidth : true,	// 해당 sheet 화면에 꽉 차도록 조정
			HeaderCheck : 1,	// Type이 Bool인 열의 헤더에 체크박스를 생성합니다. ( 0 - 체크박스 생성안함, 1 - 체크박스 생성)
			HeaderMerge : 2,
			IgnoreFocused: true,
			PrevColumnMerge : 1,
			InfoRowConfig: "",
//			InfoRowConfig: {
//				// grid row info
//				"Visible": true,
//				"Space": "Top"
//			}
//			CanColMove : 0,		// 열 이동 가능 여부( 0 - 열 이동 불가능, 1 - 열 이동 가능(default))
//			CanColResize : true,		// 열 너비 조정 여부
//			CanEdit : 0,		// 시트의 편집 가능여부를 설정( 0 - 전체 편집불가, 1 - 전체 편집가능(default), 3 - 전체 편집 불가능(편집 가능 불가능에 대한 배경색을 표시하지 않음) )
//			CanSort : true,		// 열 정렬(Sort) 기능의 허용 여부
//			DataMerge : 1,		// 셀 값을 기준으로 병합할지 여부 및 병합 종류를 설정( 0 - 병합안함, 1 - 열 병합, 2 - 행 병합, 3 - 열 우선병합, 4 - 행 우선병합, 5 - 열 우선 사방병합, 6 - 행 우선 사방병합 )
//			HeaderMerge : 4,
	};
	// JSP에서 전달받은 추가 Cfg option append
	if( cfgAdd != '' && cfgAdd != null ){
		$.each( cfgAdd, function( key, value ){
			sheetOption.Cfg[key] = value;
		});
	}
	//console.log("sheetOption.Cfg.MainCol==>>", sheetOption.Cfg.MainCol);
	if ( fnToString(sheetOption.Cfg.MainCol) != "" || fnToString(sheetOption.Cfg.TreeNodeIcon) == "1" ) {//트리구조
		sheetOption.Cfg.CanSort = false;// 정렬 기능 사용 안함
	}

	// 우클릭 이벤트
	var callbackDetailDialog = sheetOption.callbackDetailDialog;	// dialog 버튼 콜백함수
	if( sheetOption.dialogable ){
		sheetOption.Def = {
			Row : { //데이터 영역 모든 행에 대한 설정
				AlternateColor:"#F1F1F1",  //짝수행에 대한 배경색
				Menu:{ //마우스 우측버특 클릭시 보여지는 메뉴 설정 (메뉴얼에서 Appedix/Menu 참고)
					"Items":[
						{"Name":"데이터 수정","Caption":1},
						{"Name":"정보입력",Value:"infoInput"}
					],
					"OnSave":function(item,data){//메뉴 선택시 발생 이벤트
						switch(item.Value){
							case 'infoInput':// 정보입력
								var row = item.Owner.Row;
								this.Sheet.showEditDialog(row, 500, 400, eval(callbackDetailDialog));
							break;
						}
					}
				},
				ShowHint : 0
			}
		}
		// JSP에서 전달받은 추가 Def option append
		if( defAdd != '' && defAdd != null ){
			$.each( defAdd, function( key, value ){
				sheetOption.Def.Row[key] = value;
			});
		}
	}

	// Left col 기본설정
	if( sheetOption.leftCols != false ){
		var header = "No";
		var headers = new Array();
		if(sheetOption.headerDepth && sheetOption.headerDepth > 1){
			for(var i=0; i<sheetOption.headerDepth ; i++){
				headers.push(header);
			}
		}

		if( sheetOption.LeftCols == null ){
			sheetOption.LeftCols = [
				{ Header: sheetOption.headerDepth > 1 ? headers : header , Name: "SEQ", Type: "Text", Width:50, Align:"Center"}
			];
		}else{
			sheetOption.LeftCols.unshift({ Header: sheetOption.headerDepth > 1 ? headers : header , Name: "SEQ", Type: "Text", Width:50, Align:"Center"});
		}
	}

	// sheetOption.deletable = true일 때, 삭제체크박스 추가
	if( sheetOption.deletable ) {
		var header = g_deleteRow;
		var headers = new Array();
		if(sheetOption.headerDepth && sheetOption.headerDepth > 1){
			for(var i=0; i<sheetOption.headerDepth ; i++){
				headers.push(header);
			}
		}
		sheetOption.Cols.unshift({Header: sheetOption.headerDepth > 1 ? headers : header	, Name: "delCheck"	, Extend : IB_Preset.DelCheck});
	}

	//Type이 Bool인 열의 헤더에 소팅기능 제거(2023-07-21)
	if ( sheetOption.Cfg.HeaderCheck != 0 && (sheetOption.boolCanSort == null || sheetOption.boolCanSort != true) ) {
		if ( sheetOption.LeftCols != null ) {
			for( var i = 0; i < sheetOption.LeftCols.length; i++ ){
				var visible = fnToString(sheetOption.LeftCols[i].Visible);
				var type = fnToString(sheetOption.LeftCols[i].Type);
				if ( visible != "0" && type == "Bool" && sheetOption.LeftCols[i].CanSort == null ) {
					//console.log("sheetOption.LeftCols[i]==>>", sheetOption.LeftCols[i]);
					sheetOption.LeftCols[i].CanSort = 0;
				}
			}
		}
		if ( sheetOption.Cols != null ) {
			for( var i = 0; i < sheetOption.Cols.length; i++ ){
				var visible = fnToString(sheetOption.Cols[i].Visible);
				var type = fnToString(sheetOption.Cols[i].Type);
				if ( visible != "0" && type == "Bool" && sheetOption.Cols[i].CanSort == null ) {
					//console.log("sheetOption.Cols[i]==>>", sheetOption.Cols[i]);
					sheetOption.Cols[i].CanSort = 0;
				}
			}
		}
		if ( sheetOption.RightCols != null ) {
			for( var i = 0; i < sheetOption.RightCols.length; i++ ){
				var visible = fnToString(sheetOption.RightCols[i].Visible);
				var type = fnToString(sheetOption.RightCols[i].Type);
				if ( visible != "0" && type == "Bool" && sheetOption.RightCols[i].CanSort == null ) {
					//console.log("sheetOption.RightCols[i]==>>", sheetOption.RightCols[i]);
					sheetOption.RightCols[i].CanSort = 0;
				}
			}
		}
	}
	
	//console.log("g_locale==>", g_locale); // 다국어확인
	//console.log("g_ibUnitCnt==>", g_ibUnitCnt);
	// sheet data 검색영역
	if( sheetOption.Solid == null ){
		if( sheetOption.searchable == null || sheetOption.searchable != false ){
			sheetOption.Solid = [
				{
					"Kind": "Search",
					"Space": 2,	// 1 - 하단에 위치, 2 - 상단에 위치
					"id": "searchZone",
					"Cells": "Expression,Sep1,Counts,Filter,Select,FindPrev,Find,Sep2",
					"Expression": {
						"Action": "Find",//Last
						"NoColor": 0,
						"CanFocus": 1,
						"Left": "5",
						"MinWidth": "90",
						"EmptyValue": "<s>"+g_ibEnterSearchNm+"</s>" // 검색어를 입력해 주세요.
					},
					"Sep1": {
						"Width": "10",
						"Type": "Html"
					},
					"Counts": {
						"Width": "50",
						"CanFocus": 0,
						"Type": "Html",
						"Formula": "(Sheet.SearchCount ? Sheet.SearchCount : (Sheet.FilterCount ? Sheet.FilterCount : count(7))) + ' ' + g_ibUnitCnt"
					},
					"Filter": {
						"Width": fnToInt(g_ibFilter.length) * 10 * (( g_locale == "kr" ) ? 2.5 : 1),
						"ButtonText": g_ibFilter
					},
					"Select": {   //선택 기능 버튼 (Expression 셀에 입력한 글자를 바탕으로 행단위로 선택)
						"Width": fnToInt(g_ibSelect.length) * 10 * (( g_locale == "kr" ) ? 2.5 : 1),
						"ButtonText": g_ibSelect
					},
					"FindPrev": {
						"Width": fnToInt(g_ibPrev.length) * 10 * (( g_locale == "kr" ) ? 2.5 : 1),
						"ButtonText": g_ibPrev
					},
					"Find": {
						"Width": fnToInt(g_ibNext.length) * 10 * (( g_locale == "kr" ) ? 2.5 : 1),
						"ButtonText": g_ibNext
					},
					"Sep2": {
						"Width": "5",
						"Type": "Html"
					}
				}
			];
		}
	}

	// height 조정(값이 없으면 width - 100%, height - 800px로 고정)
	if( sheetOption.height != null ){
//		p_sheetDivObj.css("height", sheetOption.height);
////		console.log("11 ==> ", sheetOption.height);
//		// 위에서 우선적으로 그리드 height 설정 후 그리드의 parent div의 height를 가져와서 그 height에 그리드 크기 맞춤
//		var pHeight = p_sheetDivObj.parent().css("height");
//		if( pHeight == "0px" ){
//			pHeight = sheetOption.height;
//		}
//		var v_height = Math.floor(pHeight.slice(0, pHeight.length-2));
//
////		console.log("pHeight ==> ", pHeight);
//		p_sheetDivObj.css("height", (v_height-20)+"px");
		p_sheetDivObj.css("height", sheetOption.height);
	}

	var eventAdd = sheetOption.Events;
	
	sheetOption.Events = {
		//공통검색이후 이벤트
		onAfterFilter: function(evtParam) {
			//console.log("onAfterFilter evtParam.sheet==>>", evtParam.sheet);
			if ( fnToString(evtParam.sheet.MainCol) != "" || fnToString(evtParam.sheet.TreeNodeIcon) == "1" ) {//트리구조
			} else {
				return false;//트리구조 아님
			}

			var rows = evtParam.sheet.getDataRows();
			$.each(rows, function(index, row){
				//console.log("onAfterFilter row==>>", index , row);
				if(row.Filtered){
					// 필터로 원래 감춰져야 하는 행을 보여지게 함
					row.Visible = 1;
					row.Filtered = 0;
					//row.Expanded = row.Level<3?1:0; //3 Depth 이하만 접음
					row.Expanded = 0;//펼치기
				} else {
					row.Expanded = 1;//펼치기
					row.Selected = 1;//선택(색상변경)
				}
			});
			// 접거나 펼침/ 보임/ 감춤 변경에 대한 화면 표시
			evtParam.sheet.renderBody();

		}
	}

	// JSP에서 전달받은 추가 Events option append
	if( eventAdd != '' && eventAdd != null ){
		$.each( eventAdd, function( key, value ){
			sheetOption.Events[key] = value;
		});
	}

	// sheet create셋팅
	sheetInfo = {
		id : sheetOption.id,
		el : sheetOption.el,
		options : sheetOption
	};

	// sheet 생성
	IBSheet.create(sheetInfo);
	var p_sheetObj = eval(sheetOption.id);
// 그리드 테마설정
//	p_sheetObj.setTheme("IBGR", "/plugin/ibsheet8/css/grace/main.css", 1);
//	p_sheetObj.setTheme("IBMR", "/plugin/ibsheet8/css/material/main.css", 1);
//	p_sheetObj.setTheme("IBMT", "/plugin/ibsheet8/css/mint/main.css", 1);
//	p_sheetObj.setTheme("IBSP", "/plugin/ibsheet8/css/simple/main.css", 1);
//	p_sheetObj.setTheme("IB", "/plugin/ibsheet8/css/default/main.css", 1);

    //복구버튼 추가
    if(sheetOption.refreshable == null) sheetOption.refreshable = true;
    var v_refreshId = 'btnRefresh_' + sheetOption.id;
    $("#" + v_refreshId).remove();
    if($('#' + v_refreshId).length == 0 && sheetOption.refreshable) {
	    if($('#' + v_refreshId).length == 0) {
//	    	p_sheetDivObj.after("<a href='javascript:;' id='" + v_refreshId + "' class='fs12px mar_l5'><img src='/opennote/img/sub/i_refresh.svg'> "+g_refreshBtn+"</a>");
	    	p_sheetDivObj.after("<a href='javascript:;' id='" + v_refreshId + "' class='fs12px mar_l10'><img src='/images/icons8-refresh-16.png'> "+g_refreshBtn+"</a>");
	    	$('#' + v_refreshId).on("click", function() {
	    		p_sheetObj.revertData();
	    	});
	    }
    }

//    // 행 선택삭제버튼 추가
//	var v_delRowId = 'btnDelete_' + sheetOption.id; //버튼 권한처리를 위한 고정 명칭으로 시작 필요
//    $("#" + v_delRowId).remove();
//	if($('#' + v_delRowId).length == 0 && sheetOption.deletable) {
//		p_sheetDivObj.after("<a href='javascript:;' id='" + v_delRowId + "' class='fs12px mar_l5'><i class='fa fa-trash'></i> " + g_deleteSelBtn + "</a>");
//
//		$('#' + v_delRowId).on("click", function() {
//			var rows = p_sheetObj.getRowsByChecked( "chkSheet" );
//			if( rows && rows.length == 0 ) {
//				Message.alert(g_chkDeleteRow);
//				return false;
//			}
//			p_sheetObj.deleteRows({"rows":rows});
//
//			// 변경 정보 count 메시지
//			var msg = fnIbsModiCnt(p_sheetObj, "DELETE");
//			Message.confirm(msg, function(isOk){
//				if(isOk){
//					var callbackFn = sheetOption.callbackDelete;
//					eval(callbackFn)();
////					fnSelDeleteGrid();
//				}
//			}, function(isCancel){
//				p_sheetObj.deleteRows({"rows":rows, "del" : 0});
//			});
//		});
//	}

	//행 추가버튼 추가
    if(sheetOption.insertable == null) sheetOption.insertable = false;
    var v_addRowId = 'btnCreate_' + sheetOption.id; //버튼 권한처리를 위한 고정 명칭으로 시작 필요
    $("#" + v_addRowId).remove();
    if($('#' + v_addRowId).length == 0 && sheetOption.insertable) {
//        //추가취소버튼 추가
//        var v_cancelId = 'btnCancel_' + sheetOption.id;
//        $("#" + v_cancelId).remove();
//        if($('#' + v_cancelId).length == 0) {
//        	p_sheetDivObj.after("<a href='javascript:;' id='" + v_cancelId + "' class='fs12px mar_l5'><i class='fa fa-refresh'></i> 추가취소</a>");
//        	$('#' + v_cancelId).on("click", function() {
////        		p_sheetObj.clear();
//        		var rows = p_sheetObj.getRowsByChecked("chkSheet");
//
//        		for (var i = 0; i < rows.length; i++) {
//        			if( rows[i].Added ){
//        				p_sheetObj.removeRow(rows[i]);
//        			}
////        			p_sheetObj.renderBody();
//        		}
//        	});
//        }

    	if(sheetOption.appendable != null && sheetOption.appendable == false) {  //버튼의 속성유무로 append여부처리
    		p_sheetDivObj.after("<a href='javascript:;' id='" + v_addRowId + "' class='fs12px mar_l10' isAppend='true'><img src='/opennote/img/main/i_plus_b.png'> "+g_add+"</a>");
    	} else {
    		p_sheetDivObj.after("<a href='javascript:;' id='" + v_addRowId + "' class='fs12px mar_l10'><img src='/opennote/img/main/i_plus_b.png'> "+g_add+"</a>");
    	}
    	$('#' + v_addRowId).on("click", function() {
    		var option;

    		if($(this).attr('isAppend') == 'true') { //버튼의 속성유무로 append여부처리
    			option = {"parent":p_sheetObj.getFocusedRow()};
    		}

    		p_sheetObj.addRow(option);
    	});
    }

    // sheet 다운로드 버튼 추가
    if(sheetOption.downable == null) sheetOption.downable = true;
    if(sheetOption.downRevertData == null) sheetOption.downRevertData = true;
    var v_downloadId = 'btnDownload_' + sheetOption.id;
    $("#" + v_downloadId).remove();
    if($('#' + v_downloadId).length == 0 && sheetOption.downable) {
//    	p_sheetDivObj.after("<a id='" + v_downloadId + "' class='fs12px mar_l5'><img src='/opennote/img/sub/check_on.png'>"+g_download+"</a>");
    	p_sheetDivObj.after("<a href='javascript:;' id='" + v_downloadId + "' class='fs12px mar_l5'><img src='/images/icons8-download-16.png'>"+g_download+"</a>");
    	$('#' + v_downloadId).on("click", function() {
    		if ( sheetOption.downRevertData ) {
        		p_sheetObj.revertData();//새로고침(최초 로딩 데이터로 초기화)
    		}
    		var downOption = {
//	    			fileName: "exceldown.xlsx"
                    fileName: "gridExport("+fnGetToday("")+").xlsx",
                    SheetDesign: 1,
                    merge: 1
    		};

    		// 다운로드 할 때 hidden 컬럼을 추가할지 여부(true - 추가, false - 추가안함)
    		if( !sheetOption.downVisible ){
    			downOption.downCols = "Visible";
    		}
    		// 다운로드 할 때 검색한 행만 보여줄지 여부(true - 보여줌, false - 안보여줌)
    		if( sheetOption.downRowsVisible ){
    			downOption.downRows = "Visible";
    		}
    		
    		//p_sheetObj.down2Excel(downOption);
    		window[p_sheetObj.id].down2Excel(downOption); // 23.11.28 ibSheet 엑셀 다운로드 오류로 위의 p_sheetObj.down2Excel(downOption)에서 객체 호출 방법 수정
		});
	}

}

/* IBSheet data search
    p_treeYn : 트리 구조 조회( 쿼리 조회 시 'Level' 컬럼 필요)
*/
function fnIbsLoadData(sheetOption, callback, p_treeYn, isLodingMask){
	var p_sheetObj = sheetOption.id;
	// 기존 조회된 데이터 클리어
	p_sheetObj.reloadData();
	// 검색 전 피벗그리드가 있으면 피벗그리드 제거 후 진행
    delete p_sheetObj.PivotRows;
    delete p_sheetObj.PivotCols;
    delete p_sheetObj.PivotData;
	p_sheetObj.PivotSheet && p_sheetObj.PivotSheet.clearPivotSheet();

    //파라미터 설정
    var v_params = {};
    if(sheetOption.params != null) { //추가 파라미터 설정
    	v_params = sheetOption.params;
    }
    if(sheetOption.formId != null) { //formId 있을 경우 form내 input값 파라미터 설정
    	var v_arr = $('#' + sheetOption.formId).serializeArray();
        $.each(v_arr, function(){
	        var v_jname;
	        $.each(this, function(i, val){
	            if (i == 'name') {
	                v_jname = val;
	            } else if (i == 'value') {
	            	v_params[v_jname] = val;
	            }
	        });
        });
    }

	// 검색 시 버튼을 막기위해 검색 버튼을 셋팅,  버튼 disabled
    var v_btnSearchObj = "";
    if (typeof p_btn != "undefined" && p_btn == '') {
    	if (typeof p_btn == "object") {
	    	v_btnSearchObj.attr("disabled", true);
    	} else {
    		v_btnSearchObj = $("#" + p_btn);
    		v_btnSearchObj.attr("disabled", true);
    	}
    } else if(typeof $("btnSearch") != "undefined" && typeof $("btnSearch") != "") {
    	v_btnSearchObj = $("#btnSearch");
    	v_btnSearchObj.attr("disabled", true);
    }

    showLoadingMask();

	$.ajax({
        type: 'post',
        dataType: 'json',
        data: v_params,
        url: sheetOption.url,
        cache: false,
        success: function (data, status, xhr) {
        	hideLoadingMask();
			//console.log("fnIbsLoadData ajax success xhr==>>", xhr);
			if (xhr.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp"; 
			}
			//console.log("fnIbsLoadData data==>>", data);
        	if ( fnToString(data.message) != "") {
        		Message.alertError(data.message);
        	}
        	if(p_treeYn) {
        		// 트리 데이터 형식으로 변환(조회 컬럼 중 "Level" 있어야 함)
                var treeData = {"Data": data.rows};
            	var convertData = IBSheet.v7.convertTreeData(treeData);

            	p_sheetObj.loadSearchData( convertData );    // 트리형태  조회

        	} else {
        		p_sheetObj.loadSearchData( data.rows );
        	}

			if (callback) { //콜백함수 호출
				callback(data);
           	}

			if(!isLodingMask){
				//hideLoadingMask();
			}
        },
        error: function (xhr, status, error) {
        	if (xhr.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp"; 
			}
        	// 막악던 버튼 다시 활성화
        	if (v_btnSearchObj != "") {
            	v_btnSearchObj.attr("disabled", false);
           	}
            Message.alert('Load failed: ' + (status == 'parsererror' ? 'Searching Error in Form '+sheetOption.formId : error));
	        hideLoadingMask();
        },
        complete: function (data) {
			// 막악던 버튼 다시 활성화
        	if (v_btnSearchObj != "") {
            	v_btnSearchObj.attr("disabled", false);
           	}
			if(!isLodingMask){
				hideLoadingMask();
			}
        },
		xhr: function () {
            var xhr = new window.XMLHttpRequest();
            //Download progress
            xhr.addEventListener("progress", function (evt) {
                if (evt.lengthComputable) {
//                    p_gridView.setProgress(0, evt.total, evt.loaded);
                }
            }, false);
			/*if(!isLodingMask){
				hideLoadingMask();
			}*/
            return xhr;
        }
    });
}

//IBSheet data save
function fnIbsSaveData(sheetOption, callback, isLodingMask){
	var p_sheetObj = sheetOption.id;
	var saveJson = p_sheetObj.getSaveJson({saveMode:2, showAlert:1});	//

	if( saveJson.Code == "IBS010" ){
		return false;
	}

    //파라미터 설정
    var v_params = {};
    if(sheetOption.params != null) { //추가 파라미터 설정
    	v_params = sheetOption.params;
    }
    v_params['sheetData'] = JSON.stringify(saveJson); //그리드 데이터 설정
    if(sheetOption.formId != null) { //formId 있을 경우 form내 input값 파라미터 설정
    	var v_arr = $('#' + sheetOption.formId).serializeArray();
        $.each(v_arr, function(){
	        var v_jname;
	        $.each(this, function(i, val){
	            if (i == 'name') {
	                v_jname = val;
	            } else if (i == 'value') {
	            	v_params[v_jname] = val;
	            }
	        });
        });
    }
	var v_saveSuccMsg = g_saveSucc;
	if ( fnToString(sheetOption.succMsg) != "" ) {
		v_saveSuccMsg = sheetOption.succMsg;
	}
	var v_saveFailMsg = g_saveFail;
	if ( fnToString(sheetOption.failMsg) != "" ) {
		v_saveFailMsg = sheetOption.failMsg;
	}
	v_saveFailMsg += "<br>";

	// 검색 시 버튼을 막기위해 검색 버튼을 셋팅,  버튼 disabled
    var v_btnSearchObj = "";
    if (typeof p_btn != "undefined" && p_btn == '') {
    	if (typeof p_btn == "object") {
	    	v_btnSearchObj.attr("disabled", true);
    	} else {
    		v_btnSearchObj = $("#" + p_btn);
    		v_btnSearchObj.attr("disabled", true);
    	}
    } else if(typeof $("btnSearch") != "undefined" && typeof $("btnSearch") != "") {
    	v_btnSearchObj = $("#btnSearch");
    	v_btnSearchObj.attr("disabled", true);
    }

	//저장처리
	var formData = new FormData();
	// setAttachFile : 첨부파일 설정 여부
	if(sheetOption && sheetOption.setAttachFile) {
		fnSetFileFormData(formData);    // 첨부파일 데이터
	}
	$.each(v_params, function(key, val){
		formData.append(key, val);
		//console.log("p_params p_params==>>", key + ":" + val);
	});

    showLoadingMask();

    $.ajax({
        url: sheetOption.url,
        type: 'post',
        //dataType: 'json',
        data: formData,
		processData: false,
		contentType: false,
		cache: false,
        success: function (data, status, xhr) {
			$("#ajax").remove();
        	hideLoadingMask();
			//console.log("fnIbsLoadData ajax success xhr==>>", xhr);
			if (xhr.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp"; 
			}

			//데이터 처리 시간 관계상 로딩 이미지 처리를 위해 수정함.
			if(data.message == null) {
				if(sheetOption.isSuccessMessage != false){
					fnCallSuccessMessage(v_saveSuccMsg, callback, data);
				}else{
					if(callback)
						callback(data);
				}
			} else {
				//에러메세지 콜백함수에서 처리 isFailMessageCallback 추가 (2023-06-13)
				if(callback && fnToString(sheetOption.isFailMessageCallback) == "true") {
					callback(data);
				} else {
					Message.alertError(v_saveFailMsg + data.message );
				}
			}
            // 성공
            /*if( data.message == null ){

            	if(sheetOption.isSuccessMessage == false){
            		if(callback) {
            			callback(data);
            		}
            	}else{
                 	fnCallSuccessMessage(g_request, callback, data);
            	}
//            	p_sheetObj.showMessageTime({message: '<span data-message-code="success.request.msg"></span>', time: 3000});
//             	Message.alert(g_request);
            }else{
            	//오류건 배경색 지정
        		var v_rowId = data.message.substr(data.message.indexOf("@@")+2);
            	var v_row = p_sheetObj.getRowById(v_rowId);
            	var err_msg = data.message.substr(0, data.message.indexOf("@@"));
            	if ( fnToString(err_msg) == "" ) {
            		err_msg = data.message;
            	}else{
            		data.message = err_msg;
            	}
//            	p_sheetObj.showMessageTime({message: err_msg.replace("<br/>", ""), time: 3000});
    			if(sheetOption.isSuccessMessage != false) Message.alertError( err_msg );

    			if(v_row) {
    				//오류건 배경색 지정
                	v_row["Color"] = "#FF0000";
                	p_sheetObj.refreshRow(v_row);
    			}

    			if (callback) { //콜백함수 호출
					callback(data);
	           	}
            }

            // 막악던 버튼 다시 활성화
        	if (v_btnSearchObj != "") {
            	v_btnSearchObj.attr("disabled", false);
           	}*/

			if(!isLodingMask){
				hideLoadingMask();
			}

//			if (callback) { //콜백함수 호출
//				callback(data);
//           	}
        },
        error: function (xhr, status, error) {
        	if (xhr.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp"; 
			}
			// 막악던 버튼 다시 활성화
        	if (v_btnSearchObj != "") {
            	v_btnSearchObj.attr("disabled", false);
           	}
        	if(sheetOption.isSuccessMessage != false){
        		//Message.alertError('Load failed: ' + (status == 'parsererror' ? 'Searching Error in Form '+sheetOption.formId : error));
				Message.alertError(v_saveFailMsg + xhr.responseText );
        	}
	        hideLoadingMask();
        },
        complete: function (data) {
			// 막악던 버튼 다시 활성화
        	if (v_btnSearchObj != "") {
            	v_btnSearchObj.attr("disabled", false);
           	}
        	if(!isLodingMask){
				hideLoadingMask();
			}
        },
		xhr: function () {
            var xhr = new window.XMLHttpRequest();
            //Download progress
            xhr.addEventListener("progress", function (evt) {
                if (evt.lengthComputable) {
//                    p_gridView.setProgress(0, evt.total, evt.loaded);
                }
            }, false);
	        //hideLoadingMask();
            return xhr;
        }
    });
}

//IBSheet data save - FormData 형식(파일)
function fnIbsSaveDataFormData(sheetOption, callback){
	var p_sheetObj = sheetOption.id;
	var saveJson = p_sheetObj.getSaveJson({saveMode:2, showAlert:1});

	if( saveJson.Code == "IBS010" ){
		return false;
	}

    //파라미터 설정
	var v_params = new FormData();
	if(sheetOption.params != null) { //추가 파라미터 설정
		$.each(sheetOption.params, function(idx, val){
			v_params.append(idx, val);
		});
    }
	fnSetFileFormDataIBSheet(v_params);						// 파일
	v_params.append('sheetData', JSON.stringify(saveJson)); //그리드 데이터 설정

	if(sheetOption.formId != null) { //formId 있을 경우 form내 input값 파라미터 설정
    	var v_arr = $('#' + sheetOption.formId).serializeArray();
        $.each(v_arr, function(){
	        var v_jname;
	        $.each(this, function(i, val){
	            if (i == 'name') {
	                v_jname = val;

	            } else if (i == 'value') {
	            	v_params.append(v_jname, val);
	            }
	        });
        });
    }

	var v_saveSuccMsg = g_saveSucc;
	if ( fnToString(sheetOption.succMsg) != "" ) {
		v_saveSuccMsg = sheetOption.succMsg;
	}
	var v_saveFailMsg = g_saveFail;
	if ( fnToString(sheetOption.failMsg) != "" ) {
		v_saveFailMsg = sheetOption.failMsg;
	}
	v_saveFailMsg += "<br><br>";

	// 검색 시 버튼을 막기위해 검색 버튼을 셋팅,  버튼 disabled
    var v_btnSearchObj = "";
    if (typeof p_btn != "undefined" && p_btn == '') {
    	if (typeof p_btn == "object") {
	    	v_btnSearchObj.attr("disabled", true);
    	} else {
    		v_btnSearchObj = $("#" + p_btn);
    		v_btnSearchObj.attr("disabled", true);
    	}
    } else if(typeof $("btnSearch") != "undefined" && typeof $("btnSearch") != "") {
    	v_btnSearchObj = $("#btnSearch");
    	v_btnSearchObj.attr("disabled", true);
    }

    showLoadingMask();

    $.ajax({
        type: 'post',
        processData: false,		    // FormData 형식(파일)
		contentType: false,			// FormData 형식(파일)
        data: v_params,
        url: sheetOption.url,
        cache: false,
        success: function (data, status, xhr) {
			$("#ajax").remove();
        	hideLoadingMask();
			//console.log("fnIbsLoadData ajax success xhr==>>", xhr);
			if (xhr.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp"; 
			}

			//데이터 처리 시간 관계상 로딩 이미지 처리를 위해 수정함.
			if(data.message == null) {
				if(sheetOption.isSuccessMessage != false){
					fnCallSuccessMessage(v_saveSuccMsg, callback, data);
				}else{
					if(callback)
						callback(data);
				}
			} else {
				Message.alertError(v_saveFailMsg + data.message );
			}
            // 성공
            /*if( data.message == null ){

            	if(sheetOption.isSuccessMessage == false){
            		if(callback) {
            			callback(data);
            		}
            	}else{
                 	fnCallSuccessMessage(g_request, callback, data);
            	}
//            	p_sheetObj.showMessageTime({message: '<span data-message-code="success.request.msg"></span>', time: 3000});
//             	Message.alert(g_request);
            }else{
            	//오류건 배경색 지정
        		var v_rowId = data.message.substr(data.message.indexOf("@@")+2);
            	var v_row = p_sheetObj.getRowById(v_rowId);
            	var err_msg = data.message.substr(0, data.message.indexOf("@@"));
            	if ( fnToString(err_msg) == "" ) {
            		err_msg = data.message;
            	}else{
            		data.message = err_msg;
            	}
//            	p_sheetObj.showMessageTime({message: err_msg.replace("<br/>", ""), time: 3000});
    			if(sheetOption.isSuccessMessage != false) Message.alertError( err_msg );

    			if(v_row) {
    				//오류건 배경색 지정
                	v_row["Color"] = "#FF0000";
                	p_sheetObj.refreshRow(v_row);
    			}

    			if (callback) { //콜백함수 호출
					callback(data);
	           	}
            }

            // 막악던 버튼 다시 활성화
        	if (v_btnSearchObj != "") {
            	v_btnSearchObj.attr("disabled", false);
           	}*/

	        hideLoadingMask();

//			if (callback) { //콜백함수 호출
//				callback(data);
//           	}
        },
        error: function (xhr, status, error) {
        	if (xhr.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp"; 
			}
			// 막악던 버튼 다시 활성화
        	if (v_btnSearchObj != "") {
            	v_btnSearchObj.attr("disabled", false);
           	}
        	if(sheetOption.isSuccessMessage != false){
        		//Message.alertError('Load failed: ' + (status == 'parsererror' ? 'Searching Error in Form '+sheetOption.formId : error));
				Message.alertError(v_saveFailMsg + xhr.responseText );
        	}
	        hideLoadingMask();
        },
        complete: function (data) {
			// 막악던 버튼 다시 활성화
        	if (v_btnSearchObj != "") {
            	v_btnSearchObj.attr("disabled", false);
           	}
	        hideLoadingMask();
        },
		xhr: function () {
            var xhr = new window.XMLHttpRequest();
            //Download progress
            xhr.addEventListener("progress", function (evt) {
                if (evt.lengthComputable) {
//                    p_gridView.setProgress(0, evt.total, evt.loaded);
                }
            }, false);
	        //hideLoadingMask();
            return xhr;
        }
    });
}

// 저장 후 data처리
function fnIbsSaveAfterFrmData(sheetOption, callback){
	var formTag = document.getElementById("sheetAddRowFrm");

	if(formTag != null){
		formTag.remove();
	}

	var v_frm = document.createElement('form');

	var v_objs, v_value;
	var v_params = sheetOption.params;
	var data = '';
	$.each(v_params, function(key, val){
		v_objs = document.createElement('input');
		v_objs.setAttribute('type', 'hidden');
		v_objs.setAttribute('name', key);
		v_objs.setAttribute('value', val);
		v_frm.appendChild(v_objs);
	});

	v_frm.setAttribute('method', 'post');
	v_frm.setAttribute('id', 'sheetAddRowFrm');
	document.body.appendChild(v_frm);

	var sheetObj = sheetOption.id;
	var row = sheetObj.addRow();
	var sheetParam = {
			sheet : sheetObj,
			form : document.querySelector("#sheetAddRowFrm"),
			row : row,
//			formPreFix : ""
	}

	IBSheet.v7.IBS_CopyForm2Sheet(sheetParam);

	sheetObj.focus( row );	// 선택된 행 포커스
	sheetObj.acceptChangedData();

	return row;
}

//IBSheet data delete
/*
 * 사용안함
function fnIbsDeleteData(sheetOption, callback){
	var p_sheetObj = sheetOption.id;
	var saveJson = p_sheetObj.getSaveJson({saveMode:2});

    //파라미터 설정
    var v_params = {};
    if(sheetOption.params != null) { //추가 파라미터 설정
    	v_params = sheetOption.params;
    }
    v_params['sheetData'] = JSON.stringify(saveJson); //그리드 데이터 설정
    if(sheetOption.formId != null) { //formId 있을 경우 form내 input값 파라미터 설정
    	var v_arr = $('#' + sheetOption.formId).serializeArray();
        $.each(v_arr, function(){
	        var v_jname;
	        $.each(this, function(i, val){
	            if (i == 'name') {
	                v_jname = val;
	            } else if (i == 'value') {
	            	v_params[v_jname] = val;
	            }
	        });
        });
    }

	// 검색 시 버튼을 막기위해 검색 버튼을 셋팅,  버튼 disabled
    var v_btnSearchObj = "";
    if (typeof p_btn != "undefined" && p_btn == '') {
    	if (typeof p_btn == "object") {
	    	v_btnSearchObj.attr("disabled", true);
    	} else {
    		v_btnSearchObj = $("#" + p_btn);
    		v_btnSearchObj.attr("disabled", true);
    	}
    } else if(typeof $("btnSearch") != "undefined" && typeof $("btnSearch") != "") {
    	v_btnSearchObj = $("#btnSearch");
    	v_btnSearchObj.attr("disabled", true);
    }

    showLoadingMask();

    $.ajax({
        type: 'post',
        dataType: 'json',
        data: v_params,
        url: sheetOption.url,
        success: function (data, status, xhr) {
        	hideLoadingMask();
			//console.log("fnIbsLoadData ajax success xhr==>>", xhr);
			if (xhr.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp"; 
			}
 
            // 성공
            if( data.message == null ){
//            	p_sheetObj.showMessageTime({message: '<span data-message-code="success.request.msg"></span>', time: 3000});
             	Message.alert(g_suDel);

    			if (callback) { //콜백함수 호출
    				callback(data);
               	}
            }


	        hideLoadingMask();
        },
        error: function (xhr, status, error) {
        	if (xhr.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp"; 
			}
			// 막악던 버튼 다시 활성화
        	if (v_btnSearchObj != "") {
            	v_btnSearchObj.attr("disabled", false);
           	}
            Message.alert('Load failed: ' + (status == 'parsererror' ? 'Searching Error in Form '+sheetOption.formId : error));
	        hideLoadingMask();
        },
        complete: function (data) {
			// 막악던 버튼 다시 활성화
        	if (v_btnSearchObj != "") {
            	v_btnSearchObj.attr("disabled", false);
           	}
	        hideLoadingMask();
        },
		xhr: function () {
            var xhr = new window.XMLHttpRequest();
            //Download progress
            xhr.addEventListener("progress", function (evt) {
                if (evt.lengthComputable) {
//                    p_gridView.setProgress(0, evt.total, evt.loaded);
                }
            }, false);
	        hideLoadingMask();
            return xhr;
        }
    });
}
 */

//IBSheet combo setting
function fnIbsSetDropDown(p_sheetObj, p_sheetCode, p_sheetColId, p_sheetRowObj, p_type, p_url, p_params, p_defaultValue, p_emptyOption){
//	var v_column = p_gridView.columnByName(p_columnId);
//	if (!v_column) return false;
	if(p_emptyOption != false) {
		p_emptyOption = true;
	}
	var v_data = fnGetSelectData(null, p_sheetCode, p_type, p_url, p_params); //데이터 조회

	// 드롭다운 설정
	fnIbsSetDropDownData(p_sheetObj, p_sheetColId, p_sheetRowObj, v_data, p_emptyOption, p_params, p_defaultValue);

	return v_data; //전체 데이타 필요한 경우
}

//IBSheet combo set
function fnIbsSetDropDownData(p_sheetObj, p_sheetColId, p_sheetRowObj, v_data, emptyOption, p_params, p_defaultValue){
	var v_enum = "", v_enumKey = "";

	if(emptyOption) {
		//빈값 입력
		v_enum = "|";
		v_enumKey = "|";
	}
	//전체 선택값 추가 - text:전체, val:* (2021-12-09)
	//fnIbsSetDropDown(sheetObj, "COMPANY_ID", "companyId", "", "B", "", {pageType: "ALL", addAll: "Y"});
	if (p_params != null && typeof p_params != "undefined") {
		if (typeof p_params == "object") {
			if (p_params.addAll && fnToString(p_params.addAll) == "Y") {
				if ( g_loginLangCd != "KOR" ) {
					v_enum += "|ALL";
					v_enumKey += "|*";
				} else {
					v_enum += "|전체";
					v_enumKey += "|*";
				}
			}
		}
	}
	if(v_data){
		for(var i = 0; i < v_data.length; i++){
			v_enum = v_enum + '|' + fnXssChangeInputValue(v_data[i].text);
			v_enumKey = v_enumKey + '|' + fnXssChangeInputValue(v_data[i].id);
		}
	}

	/*
	 * 관계형 dropDown을 위한 row데이터
	 * row데이터가 없을 경우 null적용
	 * row데이터가 있으면 해당하는 row에만 동적으로 dropDown 변경
	 */
	if( p_sheetRowObj == "" ){
		p_sheetRowObj = null;
	}

	p_sheetObj.setAttribute(p_sheetRowObj, p_sheetColId, "Type", "Enum", 1);
//	p_sheetObj.setAttribute(p_sheetRowObj, p_sheetColId, "IconAlign", "Right", 1);
	p_sheetObj.setAttribute(p_sheetRowObj, p_sheetColId, "Enum", v_enum ,1);
	p_sheetObj.setAttribute(p_sheetRowObj, p_sheetColId ,"EnumKeys", v_enumKey, 1);

	if(p_defaultValue){
		p_sheetObj.setValue(p_sheetRowObj, p_sheetColId, p_defaultValue);
	}
}

/* 관계형 드롭다운 설정
 * 설명 : 상위컬럼의 Enum 에 대해  하위컬럼 Related 설정
 * 파라미터  p_parentCol : 상위 컬럼 ID
 *    ,  p_relatedCol: 하위 컬럼 ID
 *    , p_data: 하위 컬럼의 전체 목록(relId, id, text)
 *    , p_sheetRowObj: 시트 ROW 적용(null 이면 전체 적용)
 * */
function fnIbsSetDropDownRelData(p_sheetObj, p_parentCol, p_relatedCol, p_data, p_sheetRowObj) {
	if( isEmpty(p_sheetRowObj) ){
		p_sheetRowObj = null;
	}

	// 상위 컬럼
	p_sheetObj.setAttribute(p_sheetRowObj, p_parentCol, "Clear", p_relatedCol, 1);   // 상위컬럼에 따라 하위컬럼 clear 적용
	// 하위(Relate)
	p_sheetObj.setAttribute(p_sheetRowObj, p_relatedCol, "Related", p_parentCol, 1);  // 관계 설정

    var parentCd = p_sheetObj.getAttribute( p_sheetRowObj, p_parentCol, "EnumKeys");  // 상위 컬럼 목록

    if(!isEmpty(parentCd)) {

        var arrParentCd =  parentCd.split("|");    // 상위 코드 배열
        var v_enum;
        var v_enumKeys;

        for(var idx in arrParentCd) {
        	if(p_sheetObj.getAttribute( p_sheetRowObj, p_parentCol, "Required") == '0'){
        		v_enum = "|선택";
        		v_enumKeys = "|";
        	}else{
        		v_enum = "";
        		v_enumKeys = "";
        	}

        	for(var idx2 in p_data) {
        		if(arrParentCd[idx] == p_data[idx2].relId ) {
        			v_enumKeys += "|" + p_data[idx2].id;
        			v_enum     += "|" + p_data[idx2].text;
        		}
        	}
        	// 하위 column enum, enumKey 설정
        	p_sheetObj.setAttribute(p_sheetRowObj, p_relatedCol, "Enum" + arrParentCd[idx]     , v_enum ,1);
        	p_sheetObj.setAttribute(p_sheetRowObj, p_relatedCol ,"EnumKeys" + arrParentCd[idx] , v_enumKeys, 1);
        }

        p_sheetObj.setAttribute(p_sheetRowObj, p_relatedCol, "Type", "Enum", 1);
//        p_sheetObj.setAttribute(null, p_relatedCol, "IconAlign", "Right", 1);
    }
}


/* 관계형 드롭다운 설정 */
function fnIbsSetDropDownRelated(p_sheetObj, p_data, p_parentColId, p_relatedColId, p_sheetRowObj) {
	if( p_sheetRowObj == "" ){
		p_sheetRowObj = null;
	}
	// 부모 컬럼
	p_sheetObj.setAttribute(p_sheetRowObj, p_parentColId, "Clear", p_relatedColId, 1);

    // 하위(Relate) Enum
	p_sheetObj.setAttribute(p_sheetRowObj, p_relatedColId, "Related", p_parentColId, 1);  // 관계 설정

    if(p_data){
        var v_enum = "";
        var v_enumKey = "";
        var v_enumRel = "";
        var v_enumRelKey = "";
        var v_reKey;    // 하위 enum ID

    	if(p_sheetObj.getAttribute( p_sheetRowObj, p_parentColId, "Required") != '1'){
            var v_enum = "|선택";
            var v_enumKey = "|";
    	}

        for(var idx in p_data) {
        	if(p_sheetObj.getAttribute( p_sheetRowObj, p_relatedColId, "Required") != '1'){
                var v_enumRel = "|선택";
                var v_enumRelKey = "|";
        	}

    		v_enum = v_enum + '|' + fnXssChangeInputValue(p_data[idx].text);
    		v_enumKey = v_enumKey + '|' + fnXssChangeInputValue(p_data[idx].id);
    		v_enumRel = v_enumRel + '|' + fnXssChangeInputValue(p_data[idx].relatedText);
    		v_enumRelKey = v_enumRelKey + '|' + fnXssChangeInputValue(p_data[idx].relatedId);

            v_reKey = fnXssChangeInputValue(p_data[idx].id);
			p_sheetObj.setAttribute(p_sheetRowObj, p_relatedColId, "Type", "Enum", 1);
//			p_sheetObj.setAttribute(p_sheetRowObj, p_relatedColId, "IconAlign", "Right", 1);
			p_sheetObj.setAttribute(p_sheetRowObj, p_relatedColId, "Enum" + v_reKey, v_enumRel, 1);
			p_sheetObj.setAttribute(p_sheetRowObj, p_relatedColId, "EnumKeys" + v_reKey, v_enumRelKey, 1);
        }

        // 상위 Enum
        p_sheetObj.setAttribute(p_sheetRowObj, p_parentColId, "Type", "Enum", 1);
//        p_sheetObj.setAttribute(p_sheetRowObj, p_parentColId, "IconAlign", "Right", 1);
        p_sheetObj.setAttribute(p_sheetRowObj, p_parentColId, "Enum", v_enum , 1);
        p_sheetObj.setAttribute(p_sheetRowObj, p_parentColId, "EnumKeys", v_enumKey, 1);
    }
}

// Excel Upload
// uploadType - 업로드 방식 구분(String)
function fnIbsUploadExcel(p_sheetObj, uploadType) {
	if( uploadType == "DIALOG" ){	// 다이얼로그 방식
		p_sheetObj.showUploadDialog('excel');
	}else{
		p_sheetObj.loadExcel({
			mode: 'HeaderMatch'//시트의 헤더행의 타이틀과 엑셀의 첫번째 행부터 타이틀을 비교해서 읽습니다. (default: "HeaderMatch")
		});
	}
}

// IBSheet confirm message
//function fnIbsModiCnt(p_sheetObj, modiGubun){
function fnIbsModiCnt(p_sheetObj){
	var addCnt = p_sheetObj.getRowsByStatus("Added").length;
	var chaCnt = p_sheetObj.getRowsByStatus("Changed").length;
	var delCnt = p_sheetObj.getRowsByStatus("Deleted").length;
	var msg = "";

//	if( modiGubun != 'DELETE' ){
		msg  = "<span style='margin-left:50%;'>저장 : " + addCnt + "</span><br>";
		msg += "<span style='margin-left:50%;'>수정 : " + chaCnt + "</span><br>";
		msg += "<span style='margin-left:50%;'>삭제 : " + delCnt + "</span><br>";
		msg += "<span style='margin-left:43%;'>" + g_save + "</span>";
//	}else{
//		msg += "<span style='margin-left:50%;'>삭제 : " + delCnt + "</span><br>";
//		msg += "<span style='margin-left:36%;'>" + g_deleteSel + "</span>";
//	}

	return msg;
}

/**
<pre>
//사용법 - jsp
function fnSaveMulti() {
	if(!$("#${projectCode}").valid()) {
		return false;
	}
	Message.confirm('<span data-message-code="common.save.msg"></span>', function(isOk){
		if(isOk){
			$("#menuSystem").removeAttr("disabled", "disabled");
			var v_params = {};
			var v_sheetMultiOption = {
				formId: '${projectCode}',
				url: "<c:url value='/hs/system/menu/HsSystemMenuModify.json'/>",
				params: v_params
			};
			var v_arraySheetObj = [];
			var v_sheetOption01 = {
				name: "sheet_param",
				id: sheetObj
			};
			var v_sheetOption02 = {
				name: "sheet_btn",
				id: sheetObj02
			};
			var v_sheetOption03 = {
				name: "sheet_quick",
				id: sheetObj03
			};
			v_arraySheetObj.push([v_sheetOption01]);
			v_arraySheetObj.push([v_sheetOption02]);
			v_arraySheetObj.push([v_sheetOption03]);
			fnIbsSaveMultiData(v_sheetMultiOption, v_arraySheetObj, fnAfterSave);
		}
	});
}
//사용법 - java : HsSystemMenuServiceImpl.modifyMenu 참고
</pre>
 * @param p_sheetMultiOption
 * @param p_arraySheetObj
 * @param p_callback
 * @returns
 */
//IBSheet multi data save
function fnIbsSaveMultiData(p_sheetMultiOption, p_arraySheetObj, p_callback){
	var v_formId = p_sheetMultiOption.formId;
	var v_url = p_sheetMultiOption.url;

	//파라미터 설정
	var v_params = {};
	if(p_sheetMultiOption.params != null) { //추가 파라미터 설정
		v_params = p_sheetMultiOption.params;
	}
	if(v_formId != null) { //formId 있을 경우 form내 input값 파라미터 설정
		var v_arr = $('#' + v_formId).serializeArray();
		$.each(v_arr, function(){
			var v_jname;
			$.each(this, function(i, val){
				if (i == 'name') {
					v_jname = val;
				} else if (i == 'value') {
					v_params[v_jname] = val;
				}
			});
		});
	}

	//파라미터 설정(다중 그리드 포함)
	var v_sheetParams = {};
	var errorCnt = 0;
	$.each(p_arraySheetObj, function(i, sheetOpt){
		var v_sheetName = sheetOpt[0].name;
		var v_sheetObj = sheetOpt[0].id;
	 	var v_saveJson = v_sheetObj.getSaveJson({saveMode:2, showAlert:1});	//

		if( v_saveJson.Code == "IBS010" ){
			errorCnt++;
			return false;//break;
		}
		//파라미터 설정
		v_sheetParams[v_sheetName] = JSON.stringify(v_saveJson); //그리드 데이터 설정
	});
	if ( errorCnt > 0 ) {
		return false;
	}
	v_params['sheetData'] = JSON.stringify(v_sheetParams); //그리드 데이터 설정
//	console.log(v_params);

	var v_saveSuccMsg = g_request;
	if ( fnToString(p_sheetMultiOption.succMsg) != "" ) {
		v_saveSuccMsg = p_sheetMultiOption.succMsg;
	}
	var v_saveFailMsg = "";
	if ( fnToString(p_sheetMultiOption.failMsg) != "" ) {
		v_saveFailMsg = p_sheetMultiOption.failMsg;
	}
	if(v_saveFailMsg != "")v_saveFailMsg += "<br><br>";

	// 검색 시 버튼을 막기위해 검색 버튼을 셋팅,  버튼 disabled
	var v_btnSearchObj = "";
	if (typeof p_btn != "undefined" && p_btn == '') {
		if (typeof p_btn == "object") {
			v_btnSearchObj.attr("disabled", true);
		} else {
			v_btnSearchObj = $("#" + p_btn);
			v_btnSearchObj.attr("disabled", true);
		}
	} else if(typeof $("btnSearch") != "undefined" && typeof $("btnSearch") != "") {
		v_btnSearchObj = $("#btnSearch");
		v_btnSearchObj.attr("disabled", true);
	}

	showLoadingMask();

	$.ajax({
		type: 'post',
		dataType: 'json',
		data: v_params,
		url: v_url,
		cache: false,
		success: function (data, status, xhr) {
        	hideLoadingMask();
			//console.log("fnIbsLoadData ajax success xhr==>>", xhr);
			if (xhr.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp"; 
			}

			// 성공
			if( data.message == null ){
//				p_sheetObj.showMessageTime({message: '<span data-message-code="success.request.msg"></span>', time: 3000});
//			 	Message.alert(g_request);
				if(p_sheetMultiOption.isSuccessMessage != false){
					fnCallSuccessMessage(v_saveSuccMsg, p_callback, data);
				}else{
					if(p_callback)
						p_callback(data);
				}

//				if (p_callback) { //콜백함수 호출
//					p_callback(data);
//			   	}
			}else{
				//오류건 배경색 지정
//				var v_rowId = data.message.substr(data.message.indexOf("@@")+2);
//			 	//var v_row = p_sheetObj.getRowById(v_rowId);
				var err_msg = data.message;
				if(err_msg && err_msg.indexOf("@@") > 0) err_msg = err_msg.substr(0, err_msg.indexOf("@@"));// message에 "@@" 이 포함되지 않은 경우 처리
				if ( err_msg != "" ) {
					Message.alertError( v_saveFailMsg + err_msg );
				} else {
					if(p_callback) {
						p_callback(data);
					}
				}
//		 		//오류건 배경색 지정
//			 	v_row["Color"] = "#FF0000";
//			 	p_sheetObj.refreshRow(v_row);
			}
			hideLoadingMask();
		},
		error: function (xhr, status, error) {
			if (xhr.status == 204) {
				console.log("ajax 204");
				location.href = "/common/sessionErrorForward.jsp"; 
			}
			// 막악던 버튼 다시 활성화
			if (v_btnSearchObj != "") {
				v_btnSearchObj.attr("disabled", false);
		   	}
			Message.alert('Load failed: ' + (status == 'parsererror' ? 'Searching Error in Form '+v_formId : error));
			hideLoadingMask();
		},
		complete: function (data) {
			// 막악던 버튼 다시 활성화
			if (v_btnSearchObj != "") {
				v_btnSearchObj.attr("disabled", false);
		   	}
			hideLoadingMask();
		},
		xhr: function () {
			var xhr = new window.XMLHttpRequest();
			//Download progress
			xhr.addEventListener("progress", function (evt) {
				if (evt.lengthComputable) {
//					p_gridView.setProgress(0, evt.total, evt.loaded);
				}
			}, false);
			hideLoadingMask();
			return xhr;
		}
	});
}

//row의 상태 여부를 반환
function fnIsIbsRowStatus(p_row, p_tatus){
	if(!p_row || !p_tatus){
		return false;
	}
	var status = fnToString(p_row[p_tatus]);
	return(status == "1" || status == "true");
}

// row의 Status를 text로 반환 "Added", "Changed", "Deleted"
function fnGetIbsRowStatus(p_row){
	var v_status = ["Added", "Changed", "Deleted"];
	var status = "";
	$.each(v_status, function(index, stat){
		if(fnIsIbsRowStatus(p_row, stat)){
			status = stat;
			return;
		}
	});
	return status;
}

/*
 * pivot sheet의 셋팅값을 가져온다
 * data DB저장용(사용자가 저장한 값을 기본값으로 하여 피벗을 셋팅해주기 위함)
 * return - {row : '', col : '', data : ''}
 * */
function fnIbsGetPivotSetting(sheetObj){
	return sheetObj.PivotDialogInfo;
}

/*  sheet 내 중복 데이터 유효성 체크
 *  param(p_colIds : 비교할 대상이 되는 열이름 ,를 붙여 복수개 가능  / p_status : 비교대상에서 제외할 행의 상태 ,를 붙여 복수 가능)
 */
function fnSheetDupCheck(p_sheetObj, p_colIds, p_status) {
	// 중복체크
    var dupRows = p_sheetObj.getRowsByDup( p_colIds , false, false, false, p_status );

    if(dupRows && dupRows.length > 0) {
        var v_msg = g_msg_duplication;    // 중복된 데이터가 있습니다.
        var dupIdx = "";

        for(var idx in dupRows) {
            dupIdx = "";

            for(var idx2 in dupRows[idx]) {
                if(dupIdx) {
                    dupIdx += ", ";
                }
                dupIdx += dupRows[idx][idx2].SEQ;
            }
            v_msg += "<br/>[" + dupIdx + "]";
        }

        Message.alertError(v_msg);
        return false;
    }

    return true;
}


