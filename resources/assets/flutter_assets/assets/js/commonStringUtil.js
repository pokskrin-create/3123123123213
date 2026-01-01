
/**
 * date 객체에 대해 포맷 문자열 생성 prototype
 * ex) new Date().format('yyyy-mm-dd') => 2017-01-01
 *	  new Date().format('yyyy/mm/dd') => 2017/01/01
 */
Date.prototype.format = function(f) {
	if (!this.valueOf()) return " ";

	//var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
	var weekName = ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"];
	var d = this;

	return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
		switch ($1) {
			case "yyyy": return d.getFullYear();
			case "yy": return (d.getFullYear() % 1000).zf(2);
			case "MM": return (d.getMonth() + 1).zf(2);
			case "dd": return d.getDate().zf(2);
			case "E": return weekName[d.getDay()];
			case "HH": return d.getHours().zf(2); // 24시간제
			case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2); // 12시간제
			case "mm": return d.getMinutes().zf(2);
			case "ss": return d.getSeconds().zf(2); // 초
			case "SS": return d.getMilliseconds().zf(3); // 밀리초
			//case "a/p": return d.getHours() < 12 ? "오전" : "오후";
			case "a/p": return d.getHours() < 12 ? "am" : "pm";
			default: return $1;
		}
	});
};

/*
 * datepicker 한글 추가
 */
(function($){
//	$.fn.datepicker.dates['kr'] = {
//		days: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"],
//		daysShort: ["일", "월", "화", "수", "목", "금", "토", "일"],
//		daysMin: ["일", "월", "화", "수", "목", "금", "토", "일"],
//		months: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
//		monthsShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
//		today:"오늘",
//		clear: "Clear",
//		titleFormat: "yyyy년 MM"
//	};
	$.datepicker.setDefaults({
		  closeText			: "닫기"
		, currentText		: "오늘"
		, prevText			: '이전 달'
		, nextText			: '다음 달'
		, monthNames		: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
		, monthNamesShort	: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
		, dayNames			: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일']
		, dayNamesShort		: ['일', '월', '화', '수', '목', '금', '토']
		, dayNamesMin		: ['일', '월', '화', '수', '목', '금', '토']
		, weekHeader		: "주"
		, yearSuffix		: '년'
	});
}(jQuery));

/* 달력아이콘 클릭 이벤트 */
/*$("span.input-group-addon").click(function() {
	var target = $(this).parent().find("input").eq(0).attr("id");
	$("#" + target).datepicker().focus();
});*/

/**
 * 전화번호에 '-' 추가
 * 적용되는 전화번호
    1. 010-XXXX-XXXX : 11자리 휴대폰번호
	2. 010-XXX-XXXX : 10자리 휴대폰번호
	3. 02-XXXX-XXXX : 10자리 서울 전화번호
	4. 031-XXX-XXXX :  10자리 서울 외 전화번호
	5. 1688-XXXX : 8자리 업체 번호
 * num - 전화번호 ex) 01012345678 or 0101234567
 * type - 0으로 값을 주면 가운데자리를 숨겨줌 ex) 010-xxxx-5678 or 010-xxx-4567
 */
function fnPhoneFomatter(num, b_masking){
	var formatNum = '';
	// 특수문자 체크
	var regSpc = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

	if ( fnToString(num) == "" ) {
		return "";
	}

	// 특수문자가 포함되어있으면 true, 아니면 false
	// 특수문자 포함시 문자 그대로 return
	if( regSpc.test(num) == true ) {
		return num;
	}

	if(num.length==11){
	    if(b_masking){
	        formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
	    }else{
	        formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
	    }
	}else if(num.length==8){
	    formatNum = num.replace(/(\d{4})(\d{4})/, '$1-$2');
	}else{
	    if(num.indexOf('02')==0){
	        if(b_masking){
	            formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-****-$3');
	        }else{
	            formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
	        }
	    }else{
	    	// 주재원 해외번호로 인한 조건 추가
	    	if(num.length==10){
	    		if(b_masking){
	    			formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
	    		}else{
	    			formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
	    		}
	    	}else{
	    		return num;
	    	}
	    }
	}
	return formatNum;
}

/**
 * 문자로 변환
 * @param obj
 * @returns
 */
function fnToString(obj) {
	if (obj == undefined || obj == "undefined" || obj == null || obj == "null" || obj == "NaN") {
		return "";
	} else {
		return $.trim(obj.toString());
	}
}

/**
 * 숫자로 변환(정수)
 * @param str
 * @returns 999,999 => 999999
 * 	<br>	null => 0
 */
function fnToInt(str) {
	if (str != undefined && str != null && str.toString().trim() != "") {
		str = str.toString().trim();
		var x = str.startsWith("-") ? -1 : 1;
		str = str.toString().replace(/[^0-9]/gi,"");
		if ($.isNumeric(str) == false) {
			return 0;
		} else {
			return parseInt(str, 10) * x;
		}
	} else {
		return 0;
	}
}

/**
 * 문자열에서 숫자만 남긴 문자열 반환(소수포함)
 * @param x
 * @returns 999,999.99 => 999999.99
 * 	<br>	null => ''
 */
function fnToDigit(x) {
	if(!!!x) return '';
	var strX = x.toString().trim();
	var x = strX.startsWith("-") ? "-" : "";
	var tmp1;
	var tmp2 = "";
	if(strX.indexOf(".") == -1) {
		tmp1 = strX;
	} else {
		tmp1 = strX.substr(0, strX.indexOf("."));
		tmp2 = strX.substr(strX.indexOf("."));
	}
	return x + fnNvl(tmp1, "").toString().replace(/[^\d]+/g, '') + tmp2;
}

/**
 * camalcase 변환
 * @param str
 * @returns
 */
function fnToCamelcase(str){
	if(str==null ) return "";
	str = $.trim(str.toLowerCase());
	var after = str.replace(/_(\w)/g, function(word) {
		return word.toUpperCase();
	});
	return after.replace(/_/g, "");
}

/**
 * 금액을 한글로 표현한다.
 * @param num
 * @returns
 */
function fnToKoreanNumber(num) {
	var hanA = new Array("","일","이","삼","사","오","육","칠","팔","구","십");
	var danA = new Array("","십","백","천","","십","백","천","","십","백","천","","십","백","천");
	var result = "";
	num = isNumber(num) ? num.toString() :  numberWithoutCommas(num);

	for(i=0; i<num.length; i++) {
		str = "";
		han = hanA[num.charAt(num.length-(i+1))];
			if(han != "") str += han+danA[i];
			if(i == 4) str += "만";
			if(i == 8) str += "억";
			if(i == 12) str += "조";
		result = str + result;
	}

	return result ;
}

/**
 * 금액 표기
 * @param x
 * @returns 999999 => 999,999
 */
function fnFormatCurrency(x) {
	if(x != undefined || x != null ){
		var xx = x.toString().split(".");
		x =  xx[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		if(xx.length > 1){
			x += "." + xx[1];
		}
		return x;
	} else {
		return x ;
	}
}

/**
 * 숫자 자릿수에 맞게 0 채우기
 * @param n 대상문자
 * @param digits 자릿수 갯수
 * @returns 0으로 채워진 자릿수
 */
function fnLpad(n, digits) {
	var zero = '';
	n = n.toString();

	if (n.length < digits) {
		for (i = 0; i < digits - n.length; i++)
			zero += '0';
	}

	return zero + n;
}

/**
 * null 값 대체
 * @param str 원본
 * @param defValue 대체값
 * @returns
 */
function fnNvl(str, defValue) {
	return !isEmpty(str) ? str : defValue;
}

/**
 * 문자열에서 숫자만 골래내서 정수로 변환
 */
function fnParseInt(str){
	if(isEmpty(str)){
		str = "0";
	}
	str = str.replace(/[^0-9]/gi,"");
	return parseInt(str);
}

/**
 * empty check
 * @param value
 * @returns true/false
 */
function isEmpty(value){
	if( value == "" || value == null || value == 'undefined' || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ) {
		return true;
	} else {
		return false;
	}
}

/**
 * empty check
 * @param value
 * @returns true/false
 */
function isNotEmpty(value){
	if( value == "" || value == null || value == 'undefined' || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ) {
		return false;
	} else {
		return true;
	}
}

/**
 * 입력값이 숫자인지를 확인한다
 * @param sVal 입력스트링
 * @returns true/false
 */
function isNumber(sVal) {
	if(sVal.length < 1) {
		return false;
	}
	for(i=0; i<sVal.length; i++) {
		iBit = parseInt(sVal.substring(i,i+1));	 //문자(Char)를 숫자로 변경
		if(('0' < iBit) || ('9' > iBit)) {
			//alert(i+':'+iBit+':'+'Mun');
		} else {
			return false;
		}
	}
	return true;
}

/**
 * 이메일의 유효성을 체크
 * @param str
 * @returns true/false
 */
function isEmail(str) {
	var reg_email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
	if(!reg_email.test(str)) {
		Message.alert("유효하지 않은 이메일 형식입니다.");
		return false;
	}
	else {
		return true;
	}
}

/**
 * 주민번호 검증
 * @param number1 주민 번호 앞자리
 * @param number2 주민 번호 뒷자리
 * @returns true/false
 */
function isNid(number1, number2) {
	// 주민번호 검증공식 마지막 자리를 제외한 앞자리수를 규칙에 맞게 곱한다.
	// 123456-1234567
	// ****** ******
	// 234567 892345
	// 곱한 각각에 자리수를 더한다.
	// 더한수를 11 로 나눈 후 나눈 나머지 수를 구한다.
	// 나눈 나머지 수를 11에 뺀다.
	// 11 - (각각더한수%11)
	// 주민번호 마지막 번호와 일치하면 검증일치
	var totalNumber = 0;
	var num = 2;
	var juminNum = number1 + "" + number2;
	for (i = 0; i < juminNum.length - 1; i++) {
		if (num > 9) {
			num = 2;
		}
		totalNumber = totalNumber + (parseInt(juminNum.charAt(i)) * num);
		num++;
	}
	totalNumber = 11 - (totalNumber % 11);
	var mm = number1.substring(2, 4);
	var dd = number1.substring(4, 6);
	var gender = number2.substring(0, 1);
	if (number1 == "" || number2 == "") {
		Message.alert("주민등록번호를 입력하세요.");
		return false;
	} else if (!isInputNumberOnly(number1) || !isInputNumberOnly(number2)) {
		Message.alert("유효하지 않은 주민등록번호 입니다.");
		return false;
	} else if (number1.length != 6 || number2.length != 7) {
		Message.alert("유효하지 않은 주민등록번호 입니다.");
		return false;
	} else if (parseInt(mm) < 1 || parseInt(mm) > 12) {
		Message.alert("유효하지 않은 주민등록번호 입니다.");
		return false;
	} else if (parseInt(dd) < 1 || parseInt(dd) > 31) {
		Message.alert("유효하지 않은 주민등록번호 입니다.");
		return false;
	} else if (parseInt(gender) > 4 || parseInt(gender) < 1) {
		Message.alert("유효하지 않은 주민등록번호 입니다.");
		return false;
	} else if (totalNumber != juminNum.charAt(juminNum.length - 1)) {
		Message.alert("유효하지 않은 주민등록번호 입니다.");
		return false;
	} else {
		return true;
	}
}

/**
 * 사업자 번호 검증
 * @param str
 * @returns true/false
 */
function isBusinessNumber(str) {
	// 사업자번호 오류검증
	// 아래 공식으로 계산후 10의 배수가 나오면 검증일치
	var num = new Array();
	num[0] = 1;
	num[1] = 3;
	num[2] = 7;
	num[3] = 1;
	num[4] = 3;
	num[5] = 7;
	num[6] = 1;
	num[7] = 3;
	num[8] = 5;
	var totalNumber = 0;
	var _num = 0;
	for (i = 0; i < str.length - 1; i++) {
		_num = parseInt(str.charAt(i)) * num[i];
		_num = "" + _num;
		if (i < 8) {
			totalNumber = totalNumber + parseInt(_num.charAt(_num.length - 1));
		} else {
			totalNumber = (_num.charAt(_num.length - 2) == "") ? totalNumber + 0 : totalNumber + parseInt(_num.charAt(_num.length - 2));
			totalNumber = totalNumber + parseInt(_num.charAt(_num.length - 1));
		}
	}
	totalNumber = totalNumber + parseInt(str.charAt(str.length - 1));
	var num1 = str.substring(0, 3);
	var num2 = str.substring(3, 5);
	var num3 = str.substring(5, 10);
	if (str == "") {
		Message.alert("사업자번호를 입력하세요.");
		return false;
	} else if (num1.length != 3 || num2.length != 2 || num3.length != 5) {
		Message.alert("유효하지 않은 사업자 번호입니다.");
		return false;
	} else if (!isInputNumberOnly(str)) {
		Message.alert("유효하지 않은 사업자 번호입니다.");
		return false;
	} else if (totalNumber % 10 != 0) {
		Message.alert("유효하지 않은 사업자 번호입니다.");
		return false;
	} else {
		return true;
	}
}

/**
 * 법인번호 검증
 * @param str
 * @returns true/false
 */
function isCorporationNumber(str) {
	// 법인번호 오류검증 공식
	// 법인번호에서 마지막 자리를 제외한
	// 앞에 모든 자리수를 1과 2를 순차적으로 곱한다.
	// 예) 1234567890123
	// ************
	// 121212121212
	// 각각 곱한 수를 모든 더하고 10으로 나눈 나머지 수를 구한다.
	// (각각더한수 % 10)
	// 나눈 나머지 수와 법인번호 마지막 번호가 일치하면 검증일치
	var totalNumber = 0;
	var num = 0;
	for (i = 0; i < str.length - 1; i++) {
		if (((i + 1) % 2) == 0) {
			num = parseInt(str.charAt(i)) * 2;
		} else {
			num = parseInt(str.charAt(i)) * 1;
		}
		if (num > 0) {
			totalNumber = totalNumber + num;
		}
	}
	totalNumber = (totalNumber % 10 < 10) ? totalNumber % 10 : 0;
	if (str == "") {
		Message.alert("법인번호를 입력하세요.");
		return false;
	} else if (str.length != 13) {
		Message.alert("유효하지 않은 법인 번호입니다.");
		return false;
	} else if (!isInputNumberOnly(str)) {
		Message.alert("유효하지 않은 법인 번호입니다.");
		return false;
	} else if (totalNumber != str.charAt(str.length - 1)) {
		Message.alert("유효하지 않은 법인 번호입니다.");
		return false;
	} else {
		return true;
	}
}

/**
 * 한글 입력체크
 * @param str
 * @returns 한글만 입력했으면 true, 그렇지 않은 경우 false
 */
function isInputKoreanOnly(str) {
	var strCode;
	for (i = 0; i < str.length; i++) {
		strCode = str.charCodeAt(i);
		if (strCode < 12593 || strCode > 12643 && strCode < 44032 || strCode > 55203) {
			Message.alert("한글만 입력이 가능합니다.");
			return false;
		} else {
			return true;
		}
	}
}

/**
 * 숫자입력체크 숫자면 true
 * @param num
 * @returns 숫자만 입력했으면 true, 그렇지 않은 경우 false
 */
function isInputNumberOnly(num) {
	var numCode;
	for (i = 0; i < num.length; i++) {
		numCode = num.charCodeAt(i);
		if (numCode <= 57 && numCode >= 48) {
			return true;
		} else {
			Message.alert("숫자만 입력이 가능합니다.");
			return false;
		}
	}
}

/**
 * 영문입력체크
 * @param str
 * @returns 영문만 입력했으면 true, 그렇지 않은 경우 false
 */
function isInputEnglishOnly(str) {
	var strCode;
	for (i = 0; i < str.length; i++) {
		strCode = str.charCodeAt(i);
		if (strCode <= 90 && strCode >= 65 || strCode <= 122 && strCode >= 97) {
			return true;
		} else {
			Message.alert("영문만 입력이 가능합니다.");
			return false;
		}
	}
}

/**
 * 영문 대소문자 여부
 * @param str
 * @returns 대문자면 1 / 소문자명 2 / 영문이 아니면 false
 */
function isEnglishCase(str) {
	var strCode;
	for (i = 0; i < str.length; i++) {
		strCode = str.charCodeAt(i);
		if (strCode <= 90 && strCode >= 65) {
			return "1";
		} else if (strCode <= 122 && strCode >= 97) {
			return "2";
		} else {
			Message.alert("영문이 아니거나 값이 없습니다.");
			return false;
		}
	}
}

//날짜 유효성 체크 (윤달 포함)
function isValidDate(vDate) {
	var vValue = vDate;
	var vValue_Num = vValue.replace(/[^0-9]/g, ""); //숫자를 제외한 나머지는 예외처리 합니다.

// 함수는 아래 따로 적어두겠습니다.
	if ((vValue_Num) == "") {
		//Message.alert("날짜를 입력 해 주세요.");
		return false;
	}

	//8자리가 아닌 경우 false
	if (vValue_Num.length != 8) {
		//Message.alert("날짜를 20200101 or 2020-01-01 형식으로 입력 해 주세요.");
		return false;
	}

//8자리의 yyyymmdd를 원본 , 4자리 , 2자리 , 2자리로 변경해 주기 위한 패턴생성을 합니다.
	var rxDatePattern = /^(\d{4})(\d{1,2})(\d{1,2})$/;
	var dtArray = vValue_Num.match(rxDatePattern);

	if (dtArray == null) {
		return false;
	}

	//0번째는 원본 , 1번째는 yyyy(년) , 2번재는 mm(월) , 3번재는 dd(일) 입니다.
	dtYear = dtArray[1];
	dtMonth = dtArray[2];
	dtDay = dtArray[3];

	//yyyymmdd 체크
	if (dtYear < 1900 || dtYear > 2999) {
		//Message.alert("입력할 수 없는 년도를 입력하셨습니다. 다시 한번 확인 해주세요");
		return false;
	}
	else if (dtMonth < 1 || dtMonth > 12) {
		//Message.alert("존재하지 않은 월을 입력하셨습니다. 다시 한번 확인 해주세요");
		return false;
	}
	else if (dtDay < 1 || dtDay > 31) {
		//Message.alert("존재하지 않은 일을 입력하셨습니다. 다시 한번 확인 해주세요");
		return false;
	}
	else if ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31) {
		//Message.alert("존재하지 않은 일을 입력하셨습니다. 다시 한번 확인 해주세요");
		return false;
	}
	else if (dtMonth == 2) {
		var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
		if (dtDay > 29 || (dtDay == 29 && !isleap)) {
			//Message.alert("존재하지 않은 일을 입력하셨습니다. 다시 한번 확인 해주세요");
			return false;
		}
	}

	return true;
}

//날짜(연월) 유효성 체크
function isValidMonth(vDate) {
	var vValue = vDate;
	var vValue_Num = vValue.replace(/[^0-9]/g, ""); //숫자를 제외한 나머지는 예외처리 합니다.

//함수는 아래 따로 적어두겠습니다.
	if ((vValue_Num) == "") {
		//Message.alert("날짜를 입력 해 주세요.");
		return false;
	}

	//8자리가 아닌 경우 false
	if (vValue_Num.length != 6) {
		//Message.alert("날짜를 20200101 or 2020-01-01 형식으로 입력 해 주세요.");
		return false;
	}

	//6자리의 yyyyMM를 원본 , 4자리 , 2자리로 변경해 주기 위한 패턴생성을 합니다.
	var rxDatePattern = /^(\d{4})(\d{1,2})$/;
	var dtArray = vValue_Num.match(rxDatePattern);

	if (dtArray == null) {
		return false;
	}

	//0번째는 원본 , 1번째는 yyyy(년) , 2번재는 mm(월) , 3번재는 dd(일) 입니다.
	dtYear = dtArray[1];
	dtMonth = dtArray[2];

	//yyyymmdd 체크
	if (dtYear < 1900 || dtYear > 2999) {
		//Message.alert("입력할 수 없는 년도를 입력하셨습니다. 다시 한번 확인 해주세요");
		return false;
	}
	else if (dtMonth < 1 || dtMonth > 12) {
		//Message.alert("존재하지 않은 월을 입력하셨습니다. 다시 한번 확인 해주세요");
		return false;
	}

	return true;
}

/**
 * 날짜 여부를 확인한다.(월일 or 년월 or 년월일)
 * @param sYmd 입력스트링(MMDD or YYYYMM or YYYYMMDD)
 * @returns true이면 날짜
 */
function isDate(sYmd) {
	var bResult;  // 결과값을 담는 변수(Boolean)
	switch (sYmd.length) {
		case 4://월일
			bResult = isDateMD(sYmd);
			break;
		case 6://년월
			bResult =  isDateYM(sYmd);
			break;
		case 8://년월일
			bResult =  isDateYMD(sYmd);
			break;
		default:
			bResult = false;  // 날짜 값이 아님
			break;
	}
	return bResult;
}

/**
 * 날짜 여부를 확인한다.(년월일)
 * @param sYmd YYYYMMDD
 * @returns true이면 날짜
 */
function isDateYMD(sYmd) {
	// 길이 확인
	if(sYmd.length != 8) {
		Message.alert(g_validMsg);
		return false;
	}
	// 숫자 확인
	if(!isNumber(sYmd)) {
		Message.alert(g_number);
		return false;
	}
	var iYear = parseInt(sYmd.substring(0,4),10);  // 년도 입력(YYYY)
	var iMonth = parseInt(sYmd.substring(4,6),10);   //월입력(MM)
	var iDay = parseInt(sYmd.substring(6,8),10);	 //일자입력(DD)
	if((iMonth < 1) ||(iMonth >12)) {
		var msg = monthMsg(iMonth);
		Message.alert(msg);
		return false;
	}
	//각 달의 총 날수를 구한다
	//var iLastDay = lastDay(sYmd.substring(0,6));  // 해당월의 마지말날 계산
	var iLastDay = ( new Date( iYear, iMonth, 0) ).getDate();  // 해당월의 마지말날 계산

	if((iDay < 1) || (iDay > iLastDay)) {
		var msg = diffMsg(iMonth,iLastDay);
		Message.alert(msg);
		return false;
	}
	return true;
}

/**
 * 날짜 여부를 확인한다.(년월)
 * @param sYmd YYYYMM
 * @returns true이면 날짜
 */
function isDateYM(sYM) {
	// 숫자 확인
	if(!isNumber(sYM)) {
		Message.alert(g_number);
		return false;
	}
	// 길이 확인
	if(sYM.length != 6) {
		Message.alert(g_validMsg);
		return false;
	}

	var iYear = parseInt(sYM.substring(0,4),10); //년도값을 숫자로
	var iMonth = parseInt(sYM.substring(4,6),10);  //월을 숫자로

	if((iYear < 1899) ||(iYear >9999)) {
		var msg = yearMsg(iYear);
		Message.alert(msg);
		return false;
	}
	if((iMonth < 1) ||(iMonth >12)) {
		var msg = monthMsg(iMonth);
		Message.alert(msg);
		return false;
	}
	return true;
}

/**
 * 날짜 여부를 확인한다.(월일)
 * @param sYmd MMDD
 * @returns true이면 날짜
 */
function isDateMD(sMD) {
	// 숫자 확인
	if(!isNumber(sMD)) {
		Message.alert(g_number);
		return false;
	}
	// 길이 확인
	if(sMD.length != 4) {
		Message.alert(g_validMsg);
		return false;
	}
	var iMonth = parseInt(sMD.substring(0,2),10);  //해당월을 숫자값으로
	var iDay = parseInt(sMD.substring(2,4),10);	//해당일을 숫자값으로
	if((iMonth < 1) ||(iMonth >12)) {
		var msg = monthMsg(iMonth);
		Message.alert(msg);
		return false;
	}

	//각 달의 총 날수를 구한다
	if (iMonth < 8 ) {
		var iLastDay = 30 + (iMonth%2);
	}
	else {
		var iLastDay = 31 - (iMonth%2);
	}
	if (iMonth == 2) {
		iLastDay = 29;
	}

	if((iDay < 1) || (iDay > iLastDay)) {
		var msg = diffMsg(iMonth,iLastDay);
		Message.alert(msg);
		return false;
	}
	return true;
}

/**
 * 시작일이 종료일보다 앞선 날짜인지 체크
 * @param sYMD 시작일
 * @param eYMD 종료일
 * @returns true/false
 */
function isValidDateOrder(sYMD, eYMD) {
	if(sYMD.length!=10){
		return true;
	}
	if(eYMD.length!=10){
		return true;
	}

	//-을 구분자로 연,월,일로 잘라내어 배열로 반환
	var startArray = sYMD.split('-');
	var endArray = eYMD.split('-');

	//배열에 담겨있는 연,월,일을 사용해서 Date 객체 생성
	var start_date = new Date(startArray[0], startArray[1], startArray[2]);
	var end_date = new Date(endArray[0], endArray[1], endArray[2]);

	//날짜를 숫자형태의 날짜 정보로 변환하여 비교한다.
	if(start_date.getTime() > end_date.getTime()) {
		//Message.alert("종료날짜보다 시작날짜가 작아야합니다.");
		return false;
	}else{
		return true;
	}
}

/**
 * 시작시간이 종료시간보다 앞선 시간인지 체크
 * @param sTM 시작시간
 * @param eTM 종료시간
 * @returns true/false
 */
function isValidTimeOrder(sTM, eTM) {
	var sTM = sTM.replace(":","");
	var eTM = eTM.replace(":","");

	if(sTM.length!=4){
		return false;
	}
	if(eTM.length!=4){
		return false;
	}

	//날짜를 숫자형태의 날짜 정보로 변환하여 비교한다.
	if(sTM.substr(sTM.length-2 ,2) >= 60){
		return false;
	}
	if(eTM.substr(eTM.length-2 ,2) >= 60){
		return false;
	}

	if(sTM >= eTM) {
		//Message.alert("종료날짜보다 시작날짜가 작아야합니다.");
		return false;
	}else{
		return true;
	}
}

/**
 * 아이디 체크
 * <br> 첫글자 영문(소,대)이고 영문(소,대),숫자이여야 하고 최소 8자
 * @param str
 * @returns
 */
function isCheckedIdRule(str) {
	var regexp = /^[a-zA-Z][a-zA-Z0-9]{7,}$/;
	if (str.match(regexp) == str) {
		return true;
	} else {
		Message.alert("아이디는 영문 또는 숫자, 8자이상, 첫글자는 영문자로 시작하여야 합니다.");
		return false;
	}
}

/**
 * 패스워드 체크
 * <br> 8~16자이여야하고 아이디가 포함되어서는 안되고 같은 글자가 4자이상이면 false
 * @param passStr1
 * @param passStr2
 * @param idStr
 * @returns
 */
function isCheckedPasswordRule(passStr1, passStr2, idStr) {
	var regexp = /^[a-zA-Z][a-zA-Z0-9]{7,}$/;
	if (passStr1.match(regexp) != passStr1) {
		Message.alert("비밀번호는 영문 또는 숫자, 8자이상, 첫글자는 영문자로 시작하여야 합니다.");
		return false;
	}
	var regexp1 = /(\w)\1\1\1/;
	if (passStr1 != passStr2) {
		Message.alert("비밀번호와 비밀번호 확인이 다릅니다.");
		return false;
	} else if (passStr1.length < 8 || passStr1.length > 16) {
		Message.alert("비밀번호는 8자 이상 16자 이하 이여야 합니다.");
		return false;
	} else if (passStr1.indexOf(idStr) > -1) {
		Message.alert("비밀번호에 아이디가 포함되어 있어서는 안됩니다.");
		return false;
	} else if (passStr1.match(regexp1) != null) {
		Message.alert("비밀번호에 같은 문자를 4번 이상 사용하실 수 없습니다.");
		return false;
	} else {
		return true;
	}
}

/**
 * 날짜에 일수를 더하거나 뺀 날짜를 반환한다.
 * @param strDate YYYY(구분자)MM(구분자)DD
 * @param addDays 일(+-정수)
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */
function fnAddDays(strDate, addDays, separator) {
	if(!separator) separator = "-";
	var dateinfo = strDate.split(separator);
	var d = new Date(dateinfo[0], dateinfo[1]-1, dateinfo[2]);
	if (addDays == null) {
		addDays = 0;
	}
	d.setDate(d.getDate() + parseInt(addDays));

	var s = fnLpad(d.getFullYear(), 4) + separator +
			fnLpad(d.getMonth() + 1, 2) + separator +
			fnLpad(d.getDate(), 2);
	return s;
}

/**
 * 날짜에 개월수를 더하거나 뺀 날짜를 반환한다.
 * @param strDate YYYY(구분자)MM(구분자)DD
 * @param addMonths 월(+-정수)
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */
function fnAddMonths(strDate, addMonths, separator) {
	if(!separator) separator = "-";
	var dateinfo = strDate.split(separator);
	var d = new Date(dateinfo[0], dateinfo[1]-1, dateinfo[2]);
	if (addMonths == null) {
		addMonths = 0;
	}
	d.setMonth(d.getMonth() + parseInt(addMonths));

	var s = fnLpad(d.getFullYear(), 4) + separator +
			fnLpad(d.getMonth() + 1, 2) + separator +
			fnLpad(d.getDate(), 2);
	return s;
}

/**
 * 년월에 월 더하기 계산
 * @param p_strDt(yyyy-mm)
 * @returns add month(yyyy-mm)
 */
function fnGetAddMonthYm(p_strYm, p_add, p_separator) {

	if(!p_separator) {
		p_separator = "-";
	}
    var v_strDt = p_strYm + p_separator + "01";

    var regexAllCase = new RegExp(p_separator, "gi")

	if( !isDate(v_strDt.replace(regexAllCase,"")) ) {   // 날짜 유효성 체크
        return "";
    }

	var toYmd = fnAddMonths( v_strDt, p_add, p_separator);

    return toYmd.substring(0,7);
}

/**
 * 날짜에 년수를 더하거나 뺀 날짜를 반환한다.
 * @param strDate YYYY(구분자)MM(구분자)DD
 * @param addYears 연(+-정수)
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */
function fnAddYears(strDate, addYears, separator) {
	if(!separator) separator = "-";
	var dateinfo = strDate.split(separator);
	var d = new Date(dateinfo[0], dateinfo[1]-1, dateinfo[2]);
	if (addYears == null) {
		addYears = 0;
	}
	d.setYear(d.getYear() + parseInt(addYears));

	var s = fnLpad(d.getFullYear(), 4) + separator +
			fnLpad(d.getMonth() + 1, 2) + separator +
			fnLpad(d.getDate(), 2);
	return s;
}

/**
 * 날짜에 년수를 더하거나 뺀 날짜를 반환한다.
 * @param strDate YYYY(구분자)MM(구분자)DD
 * @param addYears 연(+-정수)
 * @param addMonths 월(+-정수)
 * @param addDays 일(+-정수)
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */
function fnAddYmd(strDate, addYears, addMonths, addDays, separator) {
	if(!separator) separator = "-";
	var dateinfo = strDate.split(separator);
	var d = new Date(dateinfo[0], dateinfo[1]-1, dateinfo[2]);

	if (addYears == null) {
		addYears = 0;
	}
	if (addMonths == null) {
		addMonths = 0;
	}
	if (addDays == null) {
		addDays = 0;
	}

	d.setYear(d.getFullYear() + parseInt(addYears));
	d.setMonth(d.getMonth() + parseInt(addMonths));
	d.setDate(d.getDate() + parseInt(addDays));

	var year  = d.getFullYear();
	var month = fnLpad(d.getMonth() + 1, 2);
	var day   = fnLpad(d.getDate(), 2);

	return date =   (year + separator + month + separator + day);
}

/**
 * 두일자의 월 차이를 구한다.
 * @param toDate
 * @param fromDate
 * @param separator
 * @returns
 */
function fnCalMonths(toDate, fromDate, separator) {
	if(!separator) separator = "-";
	var tDt = toDate.split(separator);
	var fDt = fromDate.split(separator);
	var td,fd;

	td = new Date(tDt[0], tDt[1]-1, 1);
	fd = new Date(fDt[0], fDt[1]-1, 1);

	var years = td.getFullYear() - fd.getFullYear();
	var months = td.getMonth() - fd.getMonth();

	return  years * 12 + months;
}

/**
 * 날짜 형태를 리턴한다.(년월일시분초)
 * @param dt date Type (Date())
 * @returns YYYYMMDDHH24MISS
 */
function fnGetDateTimeDigit(dt) {
	year = "" + dt.getFullYear();
	month = fnLpad((dt.getMonth() + 1), 2);
	day = fnLpad(dt.getDate(), 2);
	hour = fnLpad(dt.getHours(), 2);
	minute = fnLpad(dt.getMinutes(), 2);
	second = fnLpad(dt.getSeconds(), 2);
	return year + month + day + hour + minute + second;
}

/**
 * 현재일자,시간 문자열 변환
 * @returns YYYY-MM-DD HH24:MI:SS
 */
function fnGetThisDateTime() {
	if(!separator) separator = "-";
	var d = new Date();

	var s = fnLpad(d.getFullYear(), 4) + '-' +
			fnLpad(d.getMonth() + 1, 2) + '-' +
			fnLpad(d.getDate(), 2) + ' ' +
			fnLpad(d.getHours(), 2) + ':' +
			fnLpad(d.getMinutes(), 2) + ':' +
			fnLpad(d.getSeconds(), 2);

	return s;
}

/**
 * 오늘 날짜를 리턴한다.(년월일)
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */
function fnGetToday(separator) {
	if(!separator) separator = "-";
	var now =  new Date();
	var year  = now.getFullYear();
	var month = fnLpad(now.getMonth() + 1, 2); // 0부터 시작하므로 1더함 더함
	var day   = fnLpad(now.getDate(), 2);
	return date =   (year + separator + month + separator + day);
}

/**
 * 현재 연월을 리턴한다.(년월)
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM
 */
function fnGetThisMonth(separator) {
	if(!separator) separator = "-";
	var now =  new Date();
	var year  = now.getFullYear();
	var month = fnLpad(now.getMonth() + 1, 2); // 0부터 시작하므로 1더함 더함
	return date =   (year + separator + month);
}

/**
 * 현재 년도를 리턴한다.(년)
 * @returns YYYY
 */
function fnGetThisYear(dt) {
	if(!dt) dt =  new Date();
	var year  = dt.getFullYear();
	return date =   ("" + year );
}

/**
 * 전 일을 리턴한다.(년월일)
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */
function fnGetYesterday(separator) {
	if(!separator) separator = "-";
	var now =  new Date();
	var dayOfMonth = now.getDate();
	now.setDate( dayOfMonth - 1 )  ;

	var year  = now.getFullYear();
	var month = fnLpad(now.getMonth() + 1, 2); // 0부터 시작하므로 1더함 더함
	var day   = fnLpad(now.getDate(), 2);

	return date =   (year + separator + month + separator + day);
}

/**
 * 전 월을 리턴한다.(년월)
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM
 */
function fnGetPrevMonth(ym, separator) {
	if(!separator) separator = "-";
	var now = new Date();
	if(ym){
		ym = fnToDigit(ym);
		now = new Date(ym.substring(0,4), parseInt(ym.substring(4, 6))-1);
	}
	var year = now.getFullYear();
	year += (year < 2000) ? 1900 : 0;
	d = new Date(year, now.getMonth()-1);
	return fnLpad(d.getFullYear(), 4) + separator + fnLpad(d.getMonth() + 1, 2);
}

/**
 * 현재월의 첫날을 리턴한다.
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */
function fnGetThisFirstDay(separator) {
	if(!separator) separator = "-";
	var now =  new Date();
	var year  = now.getFullYear();
	var month = fnLpad(now.getMonth() + 1, 2); // 0부터 시작하므로 1더함 더함
	return date =   (year + separator + month + separator + "01");
}

/**
 * 현재월의 마지막날을 리턴한다.
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */
function fnGetThisLastday(separator) {
	if(!separator) separator = "-";
	var now =  new Date();
	var year = now.getFullYear();
	var month = fnLpad(now.getMonth() + 1, 2); // 0부터 시작하므로 1더함 더함
	var lastDay = (new Date(year, month, 0)).getDate();
	return date =   (year + separator + month + separator + lastDay);
}

/**
 * 대상월의 첫날을 리턴한다.
 * @param strDate YYYY(구분자)MM(구분자)DD
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */
function fnGetFirstDay(strDate, separator) {
	if(isEmpty(strDate)) return '';
	if(!separator) separator = "-";
	var dateinfo = strDate.split(separator);
	return date = (dateinfo[0] + separator + dateinfo[1] + separator + "01");
}

/**
 * 대상월의 마지막날을 리턴한다.
 * @param strDate YYYY(구분자)MM(구분자)DD
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */
function fnGetLastday(strDate, separator) {
	if(isEmpty(strDate)) return '';
	if(!separator) separator = "-";
	var dateinfo = strDate.split(separator);
	var year  = dateinfo[0];
	var month = dateinfo[1]; // 0부터 시작하므로 1더함 더함
	var lastDay = (new Date(year, month, 0)).getDate();
	return date =   (year + separator + month + separator + lastDay);
}

/**
 * 대시보드 용 연월을 리턴한다.
 * @param baseDate 기준일자 DD(일)
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */
function fnGetDashboardMonth(baseDate, separator) {
	if(!separator) separator = "-";
	var now = new Date();
	var year = now.getFullYear();
	year += (year < 2000) ? 1900 : 0;
	if ( now.getDate() >= base ) {
		d = new Date(year, now.getMonth()-1);
	}else {
		d = new Date(year, now.getMonth()-2);
	}
	return fnLpad(d.getFullYear(), 4) + separator + fnLpad(d.getMonth() + 1, 2);
}

/**
 * 현재날짜 기준의 한 주를 리턴한다.(일요일 ~ 토요일)
 * ex - 현재날짜 : 2021-12-09
 * return - [2021-12-05, 2021-12-06, 2021-12-07, 2021-12-08, 2021-12-09, 2021-12-10, 2021-12-11]
 * @param baseDate 기준일자 DD(일)
 * @param separator 구분자. null 이면 '-' 사용
 * @returns YYYY(구분자)MM(구분자)DD
 */

function fnGetCurrentWeek(separator) {
	if(!separator) separator = "-";

	var currentDay = new Date();
	var theYear = currentDay.getFullYear();
	var theMonth = currentDay.getMonth();
	var theDate  = currentDay.getDate();
	var theDayOfWeek = currentDay.getDay();

 	var thisWeek = [];

	for(var i=0; i<7; i++) {
		var resultDay = new Date(theYear, theMonth, theDate + (i - theDayOfWeek));
		var yyyy = resultDay.getFullYear();
		var mm = Number(resultDay.getMonth()) + 1;
		var dd = resultDay.getDate();

		mm = String(mm).length === 1 ? '0' + mm : mm;
		dd = String(dd).length === 1 ? '0' + dd : dd;

		thisWeek[i] = yyyy + separator + mm + separator + dd;
	}

	return thisWeek;
}

function fnGetDistanceDays(startDate, endDate){
	if(!startDate || !endDate){
		return null;
	}
	return Math.ceil(((new Date(endDate)).getTime()-(new Date(startDate)).getTime()) / (1000*60*60*24));
}


//받은 입력값 특수문자 변환 처리
function fnXssChangeInputValue(pValue) {
	var strReturenValue = "";
	pValue = fnToString(pValue);
	strReturenValue = pValue.replace(/&/gi, '&amp;').replace(/</gi, '&lt;').replace(/>/gi, '&gt;').replace(/"/gi, '&quot;').replace(/'/gi, '&apos;');
	return strReturenValue;
}

//받은 입력값 특수문자 변환 처리
function fnXssChangeOutputValue(pValue) {
	var strReturenValue = "";
	pValue = fnToString(pValue);
	strReturenValue = pValue.replace(/&amp;/gi, '&').replace(/&lt;/gi, '<').replace(/&gt;/gi, '>').replace(/&quot;/gi, '"').replace(/&apos;/gi, '\'').replace(/&nbsp;/gi, ' ');
	return strReturenValue;
}

/** 해당하는 날짜의 요일명 구하기
 * @param 날짜, 언어, 접두, 접미
 * @returns 접두 + 요일명 + 접미
 * ex : getInputDayLabel("2022-02-08", 'KOR', "(", ")") = (화)
*/
function fnGetInputDayLabel(p_date, p_langCd, p_wordBef, p_wordAft) {
	if(isEmpty(p_langCd)) {
		p_langCd = "KOR";
	}
	if(isEmpty(p_wordBef)) {
		p_wordBef = "";
	}
	if(isEmpty(p_wordAft)) {
		p_wordAft = "";
	}

	var week = { "KOR" : ["일", "월", "화", "수", "목", "금", "토"]
			   , "ENG" : ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"]
	};

	var today = new Date(p_date).getDay();
	var todayLabel = week[p_langCd][today];

	return p_wordBef + todayLabel + p_wordAft;
}

/** 시간:분 에 대한 validation
 * @param time
 * @returns 시간:분
*/
function getInputDayLabelddd(p_time) {
	if(isEmpty($(this).val())) { //빈값 체크
		$(this).val("00:00");
	} else {
    	var v_hour = $(this).val().substr(0,2);
    	var v_minute = $(this).val().substr(2,2);
    	if(00 > Number(v_hour) || 23 < Number(v_hour)) { //시 체크 : 00~23
    		Message.alert('<span data-message-code="message.hs.survey.setTime.error1"></span>');
    		v_hour = "00";
    	}
		if(00 > Number(v_minute) || 59 < Number(v_minute)) { //분 체크 : 00~59
    		Message.alert('<span data-message-code="message.hs.survey.setTime.error2"></span>');
    		v_minute = "00";
    	}
		$(this).val( String(v_hour) + ":" + String(v_minute) );
	}

}

function fnReplaceAll(str, searchStr, replaceStr){
    return str.split(searchStr).join(replaceStr);
}


function fnCreateElement(p_tag, p_attrs, p_parent){
	if(!p_tag) return null;
	var obj = document.createElement(p_tag);
	$.each(p_attrs, function(key, value){
		obj.setAttribute(key, value);
	});
	if(p_parent) p_parent.appendChild(obj);
	return obj;
}

function fnByteLength(str){
	var strLength = 0;
	for (i = 0; i < str.length; i++){
		var code = str.charCodeAt(i);
		var ch = str.substr(i,1).toUpperCase();
		//체크 하는 문자를 저장
		strPiece = str.substr(i,1)

		code = parseInt(code);

		if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0))){
			strLength = strLength + 3; //UTF-8 3byte 로 계산
		}else{
			strLength = strLength + 1;
		}
	 }
	 return strLength;
}

function fnContains(arr, value){
	if(!arr || !value){
		return false;
	}
	var b = false;
	$.each(arr, function(i, val){
		if(val == value){
			b = true;
		}
	});
	return b;
}

function fnSetCheckValue(id, value){
	$('#'+id).attr("checked", $('#'+id).val() == value);
}

function fnSetCheckValueByName(name, value){
	if(isNotEmpty(name)){
		$.each($('input[name="'+name+'"]'), function(){
			$(this).attr("checked", isNotEmpty(value) && value == $(this).val());
		});
	}
}

function fnSetCheckValuesByName(name, values){
	if(isNotEmpty(name)){
		$.each($('input[name="'+name+'"]'), function(){
			$(this).attr("checked", isNotEmpty(values) && fnContains(values, $(this).val()));
		});
	}
}

/**
 * 숫자 입력 (-1234.001, 마이너스, 소수점, 콤마)
 * @param val
 * @param isMinus
 * @param isFloat
 * @param isComma
 * @returns
<pre>
1. 숫자만 입력, 마이너스 허용, 소수점 허용, 콤마 처리, 공백시 기본값:0 : -2,003.30
&lt;input type='text' onkeyup = "this.value=fnNumberFormat(this.value, true, true, true, true)"&gt;
<br>
2. 숫자만 입력, 마이너스 허용, 소수점 불가, 콤마 처리, 공백시 기본값:0  : -2,003
&lt;input type='text' onkeyup = "this.value=fnNumberFormat(this.value, true, false, true, true)"&gt;
<br>
3. 숫자만 입력, 마이너스 불가, 소수점 불가, 콤마 처리, 공백시 기본값:0  : 2,003
&lt;input type='text' onkeyup = "this.value=fnNumberFormat(this.value, false, false, true, true)"&gt;
<br>
4. 숫자만 입력, 마이너스 허용, 소수점 불가, 콤마 처리 안함, 공백시 기본값:""  : -2003
&lt;input type='text' onkeyup = "this.value=fnNumberFormat(this.value, true, false, false, false)"&gt;
</pre>
 */
function fnNumberFormat(val, isMinus, isFloat, isComma, isDefZero){
	var str = fnToString(val);
	if ( isDefZero && str == "" ) {
		str = 0;
	}
	
	//일단 마이너스, 소수점을 제외한 문자열 모두 제거
	str = str.toString().replace(/[^-\.0-9]/g, '');
	//마이너스
	if(isMinus){
		str = fnChgMinusFormat(str);   
	} else {
		str = str.replace('-','');
	}

	//소수점
	if(isFloat){
		str = fnChgFloatFormat(str);       
	} else {
		if(!isMinus ){
			str = str.replace('-','');
		}
		str = str.replace('.','');
		if(str.length>1){
			str = Math.floor(str);
			str = String(str);
		}
	}

	//콤마처리
	if(isComma){
		var parts = str.toString().split('.');
		if(str.substring(str.length - 1, str.length)=='.'){
			str = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g,",") +".";
		} else {
			str = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g,",") + (parts[1] ? "." + parts[1] : "");
		}
	}
	return str;
}

function fnChgFloatFormat(str){
	var idx = str.indexOf('.');  
	if(idx<0){  
		return str;
	} else if(idx>0){
		var tmpStr = str.substr(idx+1);
		if(tmpStr.length>1){
			if(tmpStr.indexOf('.')>=0){
				tmpStr =  tmpStr.replace(/[^\d]+/g, '');
				str = str.substr(0,idx+1) + tmpStr;
			}
		}
	} else if(idx==0){
		str = '0'+str;
	}
	return str;  
}

function fnChgMinusFormat(str){
	var idx = str.indexOf('-');
	if(idx==0){
		var tmpStr = str.substr(idx+1);
		//뒤에 마이너스가 또 있는지 확인
		if(tmpStr.indexOf('-')>=0){
			tmpStr = tmpStr.replace('-','');
			str = str.substr(0,idx+1) + tmpStr;  
		}
	} else if(idx>0){
		str = str.replace('-','');
	} else if(idx<0){
		return str;
	}
	return str;
}

/**
 * 실수(-,소수점 포함) 계산식에서 사용
 * @param str
 * @returns -999,999.01 => -999999.01
 * 	<br>	null => 0
 */
function fnToFloat(value) {
	if ( fnToString(value) != "" ) {
		value = value.toString().replace(/[^-\.0-9]/g, ''); // 마이너스, 소수점 제외 하고 제거
		var regExp = /^([-]?([0-9]))+(\.{1}[0-9]+)?$/g;
		if (!regExp.test(value)) {
			value = 0;
		}
	} else {
		value = 0;
	}
	return Number(value);
}

/**
 * 소수점 자리수 계산
 * @param num = 계산할 숫자
 * @param fixCnt = 소수점 자리수(기본값:1)
 * @param opt = "1":반올림, "2":올림, "3":버림(기본값:"3")
**/
function fnFloatFixed( num, fixCnt, opt ) {
	num = fnToFloat(num);
	fixCnt = fnToInt(fixCnt);
	opt = fnToString(opt);
	if ( fixCnt == 0 ) fixCnt = 1;
	if ( opt == "" ) opt = "3";
	var count = Math.pow(10,fixCnt);
	if ( opt == "1" ) {
		num = Math.round( num * count) / count; // 반올림
	} else if ( opt == "2" ) {
		num = Math.ceil( num * count) / count; // 올림
	} else {
		num = Math.floor( num * count) / count; // 버림
	}
	num = num.toFixed(fixCnt)// 0을 채워주기 toFixed를 이용
	return parseFloat(num); // 소수점 마지막 자리 0이면 제거됨
}

/**
 * 두 날짜사이의 일수를 계산한다
 * @param day1 예) 2024-06-27
 * @param day2     2024-06-30
 * @returns       => 3
 */
function fnMinusBetweenDays(day1, day2) {
	var betweenDay = 0;
	if(isEmpty(day1)) return false;
	if(isEmpty(day2)) day2 = fnGetToday();
	var betweenDay = (new Date(day1) - new Date(day2)) / (24 * 60 * 60 * 1000);
	
	return betweenDay;
}

/**
 * 주어진 년도 값에 delta(+1 또는 -1)를 더하거나 빼서 반환하는 공통 함수
 * 
 * @param {string|number} yearValue - 현재 년도 값 (문자열 또는 숫자 가능)
 * @param {number} delta - 증감할 값 (+1: 다음 년도, -1: 이전 년도 등)
 * @returns {number} 조정된 새로운 년도 값
 */
function fnCalYear(yearValue, delta) {
    // 입력값을 정수로 변환
    var year = parseInt(yearValue, 10);

    // 변환 실패하면 현재 년도로 기본 설정
    if (isNaN(year)) {
        year = new Date().getFullYear();
    }

    // 증감 계산
    var newYear = year + delta;

    // 결과 반환
    return newYear;
}

