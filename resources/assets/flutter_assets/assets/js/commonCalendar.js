/* 달력 공통 스크립트 */
//달력 이벤트 설정
function fnMakeCalendar(p_formId) {
	//console.log("fnMakeCalendar p_formId==>>", p_formId);
	var $document = $(document);
	if(isEmpty(p_formId) || $.trim(p_formId) == ''){
		p_formId = "";
	}else{
		p_formId = $.trim(p_formId)+" ";
		if(!p_formId.startsWith("#")) p_formId = "#"+p_formId;
		$document = $(p_formId);
	}

	fnDatepickerSetDefault();    // datepicker set defaults

	//날짜만 dateOnly='true'
	fnSetDatepicker($(p_formId+" input:text[data-dateOnly]"));
/*
	$("input:text[data-dateOnly]").on('focus', function() { //읽기모드일경우 달력팝업 지우기(p_obj.datepicker('remove')가 제대로 동작안함)
		if($(this).hasAttr('readonly')) {
			$(this).datepicker('hide');
			$(this).datepicker('remove')
			return false;
		}
	});
*/

//	$document.on('keypress', "input:text[data-dateOnly]", function (e) {
	$document.on('keyup', "input:text[data-dateOnly]", function (e) {
		var str_date = $(this).val();
		if(isNaN(str_date.charAt(str_date.length-1))) return;
		if ( str_date == "" ) return;
		var validDateCal = fnToString($(this).attr("data-validDateCal")); // 생년월일입력시 사용 안하려고 추가(ktw 2024-07-19)
		if ( validDateCal != "N" ) {
			if(!isValidDateCal(str_date)){
				$(this).val('');
				Message.alert("존재하지 않은 날짜를 입력하셨습니다. 다시 한번 확인 해주세요");
			}
		}
	});

/*
	$(".input-group-addon .fa-calendar").click(function() {
	   var target = $(this).parent().find("input").eq(0).attr("id");
	   $("#" + target).datepicker().focus();
	}) ;

	//font awesome 버전업그레이드 적용 2021-09-24
	$document.on("click", "span:has([data-icon='calendar'])", function(){
		var target = $(this).prevAll("input").eq(0).attr("id");
		$("#" + target).datepicker().focus();
	});*/

	//opennote 달력버튼 적용 2021-10-13
	$document.on("click", "a > img[src*='i_calendar']", function(){
		var target = $(this).parent().parent().find("input").not('[type="hidden"]').eq(0).attr("id");
		var obj = $("#" + target);
		if ( obj.prop('readonly') == true || obj.prop('disabled') == true ) {
		} else {
			obj.trigger("click");
			obj.focus();
		}
	});

	//$document.off("click", "button > img[src*='i_calendar']");//이벤트 취소 - 중복 실행되는거 방지
	$document.on("click", "button > img[src*='i_calendar']", function(){
		var target = $(this).parent().parent().find("input").not('[type="hidden"]').eq(0).attr("id");
		var obj = $("#" + target);
		//console.log("button > img[src*='i_calendar'] click==>>", obj.datepicker);
		if ( obj.prop('readonly') == true || obj.prop('disabled') == true ) {
		} else {
			obj.trigger("click");
			obj.focus();
		}
	});

	//YYYY-MM형식 날짜만 monthOnly='true' - 2017.03.21 jsh추가
	fnSetDatepickerYm($(p_formId+"input:text[data-monthOnly]"));
	fnSetDatepickerYm($(p_formId+"input:text[data-mask='9999-99']"));

	$document.on('keypress', "input:text[data-monthOnly]", function (e) {
		var str_date = $(this).val();
		if(isNaN(str_date.charAt(str_date.length-1))) return;
		if(!isValidMonth(str_date)){
			$(this).val('');
			Message.alert("존재하지 않은 월을 입력하셨습니다. 다시 한번 확인 해주세요");
		}
	});

	//YYYY-MM형식 날짜만 yearOnly='true' - 2018.02.01 jsh추가
	fnSetDatepickerYy($(p_formId+"input:text[data-yearOnly]"));
	fnSetDatepickerYy($(p_formId+"input:text[data-mask='9999']"));

}

//날짜 유효성 체크 (윤달 포함)
/**
 *  - wsseo -
 * isValidDate 라는 이름이 라이브러리마다 많이 중복되어 제대로 처리되지 않음.
 * 기존 function은 쓰던데가 있어 바꿀수 없으니 이름을 바꿔서 새로 등록
 * 여기서만 사용함. 다른데서 사용하지 말것
 * @param vDate
 * @returns
 */
function isValidDateCal(vDate) {
	var vValue = vDate;
	var vValue_Num = vValue.replace(/[^0-9]/g, ""); //숫자를 제외한 나머지는 예외처리 합니다.

	//함수는 아래 따로 적어두겠습니다.
	if ((vValue_Num.length > 0 && vValue_Num) == "") {
		console.log(">> isValidDate 0 : "+vDate);
		//Message.alert("날짜를 입력 해 주세요.");
		return false;
	}

	//8자리가 아닌 경우 false - keypress 에 걸려 있어서 주석처리
	//if (vValue_Num.length != 8) {
	//	console.log(">> isValidDate 1 : "+vDate);
		//Message.alert("날짜를 20200101 or 2020-01-01 형식으로 입력 해 주세요.");
	//	return false;
	//}

	if (vValue_Num.length == 8) {
		//8자리의 yyyymmdd를 원본 , 4자리 , 2자리 , 2자리로 변경해 주기 위한 패턴생성을 합니다.
		var rxDatePattern = /^(\d{4})(\d{1,2})(\d{1,2})$/;
		var dtArray = vValue_Num.match(rxDatePattern);

		if (dtArray == null) {
			console.log(">> isValidDate 2 : "+vDate);
			return false;
		}

		//0번째는 원본 , 1번째는 yyyy(년) , 2번재는 mm(월) , 3번재는 dd(일) 입니다.
		dtYear = dtArray[1];
		dtMonth = dtArray[2];
		dtDay = dtArray[3];

		//yyyymmdd 체크
		if (dtYear < 1900 || dtYear > 2999) {
			console.log(">> isValidDate 3 : "+vDate);
			//Message.alert("입력할 수 없는 년도를 입력하셨습니다. 다시 한번 확인 해주세요");
			return false;
		}
		else if (dtMonth < 1 || dtMonth > 12) {
			console.log(">> isValidDate 4 : "+vDate);
			//Message.alert("존재하지 않은 월을 입력하셨습니다. 다시 한번 확인 해주세요");
			return false;
		}
		else if (dtDay < 1 || dtDay > 31) {
			console.log(">> isValidDate 5 : "+vDate);
			//Message.alert("존재하지 않은 일을 입력하셨습니다. 다시 한번 확인 해주세요");
			return false;
		}
		else if ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31) {
			console.log(">> isValidDate 6 : "+vDate);
			//Message.alert("존재하지 않은 일을 입력하셨습니다. 다시 한번 확인 해주세요");
			return false;
		}
		else if (dtMonth == 2) {
			var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
			if (dtDay > 29 || (dtDay == 29 && !isleap)) {
				console.log(">> isValidDate 7 : "+vDate);
				//Message.alert("존재하지 않은 일을 입력하셨습니다. 다시 한번 확인 해주세요");
				return false;
			}
		}
	}
	console.log(">> isValidDate : true");
	return true;
}

/* 년월일 달력 기본 설정 */
function fnDatepickerSetDefault() {

	$.datepicker.setDefaults($.datepicker.regional['ko']);    // 다국어 설정

	$.datepicker.setDefaults({
		//showOn: "both" // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
	    //, buttonImage: "/application/db/jquery/images/calendar.gif" // 버튼 이미지
	    //, buttonImageOnly: true 
	    changeMonth: true
	    , changeYear: true
	    , minDate: '-130y'
	    , numberOfMonths: [1, 1]
	    , stepMonths: 1
	    , yearRange: function() {
	        var currentYear = new Date().getFullYear();
	        return (currentYear - 100) + ':' + (currentYear + 2);
	    }()
	    , dateFormat: "yy-mm-dd"
	    //, showAnim: "slide"
		, showMonthAfterYear: true
		//, dayNamesMin: ['월', '화', '수', '목', '금', '토', '일']     // 요일의 한글 형식.
	    //, monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'] // 월의 한글 형식.
	    //, yearRange: "2010:2013" //연도 범위
	    , beforeShow: function(input, inst) {
	    	// 팝업에서 datePicker동적으로 생성시 제자리에 생성 안되는부분 수정
	        var rect = input.getBoundingClientRect();
	        
	        setTimeout(function(){
	            var scrollTop = $("body").scrollTop();
	            var $tmpInput = $(input);
				
				// data-dateOnly를 사용하는 곳에서 팝업이면서, 동적으로 생성시 날짜 팝업 위치 제대로 안잡히는경우 data-datePopOnly="Y" 속성 적용
	            if ($tmpInput.attr("data-datePopOnly") == "Y") {
	                inst.dpDiv.css({ top: rect.top + input.offsetHeight + scrollTop });	// 팝업 - 팝업에서는 부모창의 스크롤바 위치에 영향을 받지 않아야됨
	            }

	            $('.ui-datepicker').css('z-index', 2000);

	            // 연도 내림차순 강제 적용
	            sortYearDescending(inst);
	        }, 0);

	        if ($(this).prop('readonly') == true || $(this).prop('disabled') == true) { // 월달력 막기(ktw : 2024-06-05)
	            return false;
	        }
	    },

	    onChangeMonthYear: function(year, month, inst) {
	        setTimeout(function() {
	            sortYearDescending(inst);
	        }, 0);
	    }
	});
}

function sortYearDescending(inst) {
    var yearSelect = $(inst.dpDiv).find(".ui-datepicker-year");
    if (yearSelect.length > 0) {
        var options = yearSelect.find("option").toArray();
        options.sort(function(a, b) {
            return parseInt(b.value) - parseInt(a.value);
        });
        yearSelect.empty().append(options);
    }
}

/**
 * 일까지 실행하는 datePicker 실행
 * @param p_obj 대상
 * @param isRangePicker 주위를 클릭해도 달력 실행할 것인지 여부. default false
 * @returns
 */
function fnSetDatepicker(p_obj, p_options) {
	if(!p_obj || !p_obj.datepicker || p_obj.prop("readonly") ) {
		return false;
	}

	p_obj.mask("9999-99-99",{placeholder:"yyyy-mm-dd"});
	// 20220106 조은성 추가 - 달력 input을 클릭하면 이전에 입력했던 날짜가 자동완성목록으로 뜨는부분 방지
	p_obj.attr("autocomplete", "off");
    p_obj.datepicker(p_options);

}

/**
 * 월까지 표시하는 datePicker 실행
 * 참고 : https://kidsysco.github.io/jquery-ui-month-picker/
 *       https://github.com/KidSysco/jquery-ui-month-picker/wiki/Events
 * @param p_obj
 * @returns
 */
function fnSetDatepickerYm(p_obj, p_options) {

	if(p_obj) {
		// 언어
		var langOptions = {
              year: ""
            , prevYear: "Previous Year"
            , nextYear: "Next Year"
            , next12Years: "Jump Forward 12 Years"
            , prev12Years: "Jump Back 12 Years"
            , nextLabel: "다음"
            , prevLabel: "이전"
            , buttonText: "Open Month Chooser"
            , jumpYears: "Jump Years"
            , backTo: "Back to"
            , months: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
        }

		p_obj.MonthPicker({
			Button: false
    		, MonthFormat: 'yy-mm'
    	    , i18n : langOptions
    	});
        // 추가 옵션 설정
		if(p_options) {
			p_obj.MonthPicker({
				p_options
	    	});
		}
		// 변경 후 이벤트 호출
		p_obj.MonthPicker({
		    OnAfterChooseMonth: function(selectedDate) {
		    	// 해당 이벤트가 있을경우 별도의 화면에서 data-monthOnly에 change이벤트 생성시 두번씩 호출되는 문제가 있어서 주석처리함 2024-08-14
		    	//$(this).change();
		    }
		    , OnBeforeMenuOpen: function(e){ // 월달력 막기(ktw : 2024-06-05)
				key = (e) ? e.keyCode : event.keyCode;
				//console.log("MonthPicker OnBeforeMenuOpen==>> ", $(this).prop('readonly'), $(this).prop('disabled'));
				if ( $(this).prop('readonly') == true || $(this).prop('disabled') == true ) {
					if (e) { //표준
						e.preventDefault();
					} else { //익스용
						event.keyCode = 0;
						event.returnValue = false;
					}
				}
			}
		});
		p_obj.mask("9999-99",{placeholder:"yyyy-mm"});
		// 20220106 조은성 추가 - 달력 input을 클릭하면 이전에 입력했던 날짜가 자동완성목록으로 뜨는부분 방지
		p_obj.attr("autocomplete", "off");
	}
}

/**
 * datePicker가 년으로 표기
 * @param p_obj
 * @returns
 */
function fnSetDatepickerYy(p_obj) {
	if(p_obj && p_obj.mask)
		p_obj.mask("9999",{placeholder:"yyyy"});
	//datePicker가 년까지 표시되게 수정 - 2017.12.14 jmkim추가
	if(p_obj){
		if ( p_obj.length == 1 ) {
			if(p_obj.is("[data-noneInit]")){
				p_obj.yearpicker({
				});
			}else{
				p_obj.yearpicker({
					year : parseInt(fnGetThisYear())
				});
			}
		} else {
			for(var i=0; i<p_obj.length; i++){//(2023-12-12 ktw 추가) data-yearOnly 또는 data-mask='9999' 가 한 화면에 1개 이상이면
				var objId = p_obj[i].id;
				if( $("#"+objId).length > 0 ) {
					var v_obj = $("#"+objId);
					if( v_obj.is("[data-noneinit]")){//기본 년도 설정 안함
						v_obj.yearpicker({
						});
					}else{
						v_obj.yearpicker({
							year : parseInt(fnGetThisYear())
						});
					}
				}
			}
		}
		/*p_obj.datepicker({
			keyboardNavigation: true,
			forceParse: false,
			autoclose: true,
			currentText: "올해",
			format: "yyyy",
			yearRange: 'c-100:c+10',
			changeYear: true,
			startView: 2,
			minViewMode: 2,
			todayHighlight:true,
			language: ('KOR' == g_loginLangCd ? 'kr' : 'en')	// 달력 언어 설정
		});*/

		// 20220106 조은성 추가 - 달력 input을 클릭하면 이전에 입력했던 날짜가 자동완성목록으로 뜨는부분 방지
		p_obj.attr("autocomplete", "off");
		$(".yearpicker-footer").addClass("c");
	}

}
