"use strict";

$(document).ready(function() {
	gnb();
	// space();

	// calenderShow();
})

function gnb() {
	$(".depth2").hide();
	$(".depth3").hide();
	$(".depth4").hide();
	$(".depth5").hide();
	$(".depth6").hide();
	$(".js-gnb li > a").off("click").on("click", function() {
		// $(this).removeAttr("href");
		var element = $(this).parent("li");
		if (element.hasClass("open")) {
			element.removeClass("open");
			element.removeClass("active");
			element.find("li").removeClass("open");
			element.find("ul").slideUp(200);
		} else {
			element.addClass("open");
			element.children("ul").slideDown(200);
			element.siblings("li").children("ul").slideUp(200);
			element.siblings("li").removeClass("open");
			element.siblings("li").find("li").removeClass("open");
			element.siblings("li").find("ul").slideUp(200);
		}
	});
}

$(function() {
	var dates = $("#from1, #to1")
			.datepicker(
					{
						buttonImage : "img/common/i_calendar.svg",
						onSelect : function(selectedDate) {
							var option = this.id == "from1" ? "minDate"
									: "maxDate", instance = $(this).data(
									"datepicker"), date = $.datepicker
									.parseDate(
											instance.settings.dateFormat
													|| $.datepicker._defaults.dateFormat,
											selectedDate, instance.settings);
							dates.not(this).datepicker("option", option, date);
						}
					});
});

$(document).ready(function() {
	// 210930 추가
	$('a').each(function() {
		if (!$(this).attr('href')) {
			$(this).attr('href', "javascript:void(0);");
		}
	})

	$(".btn_esc").off("click").on("click", function() {
		$(this).prev('.text').val('');
	})

	$(".disabled").off("click").on("click", function(e) {
		e.preventDefault();
	})
	// 추가 끝

	// main화면 검색영역 event
	$('.dashboard_header .search .search_icon').off("click").on("click", function() {
		if( !$('.dashboard_header .search').hasClass("open") ){
			$('.dashboard_header .search').addClass('open');
			$('.search_area_bg').css('display', 'block');
		}
	});
	$(".search_area_bg").off("click").on("click", function() {
		$('.dashboard_header .search').removeClass('open');
		$(this).css("display", "none");
	});
	// main화면 검색영역 event

	$(".profile .sub_menu").hide();
	$(".profile_name").off("click").on("click", function() {
		$(".profile .sub_menu").slideToggle();
		$('.my_bg').css('display', 'block');
	});

	swiper5();

	// 메뉴 버튼 클릭
	$(".btn_toggle").off("click").on("click", function() {

		$(".left_area").toggle();    // 좌측 메뉴
		$('.area_bg').css('display', 'block');    // 전체영역 block

		// 현재 메뉴 펼치기 (menu ID 가 있을 경우)
		if($("#currentMenuId").val()) {
			var $thisMenu = $("#li_" + $("#currentMenuId").val());

			if(!$thisMenu.hasClass("open")) {
				$thisMenu.addClass("open");    // 해당 메뉴 표시

				var depth = $thisMenu.closest("ul").attr("class");    // 메뉴 depth

				if(depth == "depth3") {
					$thisMenu.closest(".depth2").prev("a").click();
					$thisMenu.closest(".depth3").prev("a").click();

				} else if(depth == "depth2") {
					$thisMenu.closest(".depth2").prev("a").click();
				}
			}
		}
	})

	$(".box_layer").off("click").on("click", function() {
		$(this).toggleClass("on");
	});

	/* jsp 화면에서 click 이벤트 처리 해서 주석
	$(".layer_area").off("click").on("click", function() {
	    $(this).toggleClass("on");
	});
	*/

	// 전체영역 block 해제
	$('.area_bg').off("click").on("click", function() {
		$(".left_area").hide();
		$('.detail_box').hide();
		$(this).css("display", "none");
	});

	$('.global_bg').off("click").on("click", function() {
		$(".global_menu").hide();        // 언어 layer
		$("#alert_rcpt_menu").hide();    // 알림 layer 닫기

		$(this).css("display", "none");
	});
	$('.my_bg').off("click").on("click", function() {
		$(".sub_menu").hide();
		$(this).css("display", "none");
	});

	$(".btn_chat").off("click").on("click", function() {
		$(".chat_box").toggle();
	});

	$(".global_menu").hide();
	$(".global").off("click").on("click", function() {
		$(".global_menu").slideToggle();
		$('.global_bg').css('display', 'block');
	});

	$('.detail_box').hide();
	$(".detail .btn_detail").off("click").on("click", function() {
		$(this).next().toggle();
		$('.area_bg').css('display', 'block');
	});

	/* 팝업 바깥 부분 클릭 이벤트 : 팝업닫기 */
	$('#js-popup-bg').off("click").on("click", function() {
		// 팝업 닫기 불가
		if ( !$(".pop-header").html() ) {
			return false;
		}
		if ( $(".pop-header > .btn_close").is(":disabled") ) { // 참고 : commonOpennoteJavaScript.js fnSetDisablePopupClose()
			return false;
		}

		fnPromptClose();    // commonOpennoteJavaScript 모달 닫기
	});

	/* 팝업 바깥 부분(sub modal) 클릭 이벤트 : 팝업닫기 */
	$('#js-sub-popup-bg').off("click").on("click", function() {

		// 팝업 닫기 불가
		if ( $(".pop-header > .btn_close").is(":disabled") ) { // 참고 : commonOpennoteJavaScript.js fnSetDisablePopupClose()
			return false;
		}

		fnPromptClose();    // commonOpennoteJavaScript 모달 닫기
	});

	$('.m_tab_btn').off("click").on("click", function() {
		$('.menu_wrap').toggleClass('visible');
	})

	// 임시 주석처리
	// const chart=document.querySelector('#language_chart');
	// const div=document.createElement('div');
	// const div2=document.createElement('div');
	//
	// div.setAttribute('class', 'label_bg');
	// div2.setAttribute('class', 'label_bg2');
	//
	// chart.append(div, div2);

	$('.active').after().click(function() {
		// alert('clickable!');
		console.log('clickable!');
	});
})

// function calenderShow() {
// $('#daterange_box').dateRangePicker({
// inline : true, // 달력 보이게 설정
// alwaysOpen : true,// 달력 보이게 설정
// container : '#calender_box', // 달력이 들어올 태그 ID
// singleMonth : true, // 달력 한달만 보이게 설정
// showTopbar : false,
// hoveringTooltip : false, // 호버툴팁 막기
// selectForward : true,
// getValue : function() {
// if ($('#range_form').val() && $('#range_to').val())
// return $('#range_form').val() + ' to ' + $('#range_to').val();
// else
// return '';
// },
// setValue : function(s, s1, s2) {
// $('#range_form').val(s1); // 시작일 값 표시
// $('#range_to').val(s2); // 종료일 값 표시
// },
// })
//
// var picker = $('#daterange_box2').dateRangePicker(
// {
// inline : true, // 달력 보이게 설정
// alwaysOpen : true,// 달력 보이게 설정
// container : '#calender_box2', // 달력이 들어올 태그 ID
// singleMonth : true, // 달력 한달만 보이게 설정
// showTopbar : false,
// hoveringTooltip : false, // 호버툴팁 막기
// selectForward : true,
// getValue : function() {
// if ($('#range_form2').val() && $('#range_to2').val())
// return $('#range_form2').val() + ' to '
// + $('#range_to2').val();
// else
// return '';
// },
// setValue : function(s, s1, s2) {
// $('#range_form2').val(s1); // 시작일 값 표시
// $('#range_to2').val(s2); // 종료일 값 표시
// }
// })
// }

function swiper5() {
	var swiper = new Swiper('.mySwiper', {
		navigation : {
			nextEl : ".swiper-button-next",
			prevEl : ".swiper-button-prev",
		},
		slidesPerView : 'auto',
	});

	var swiper2 = new Swiper(".infoSwiper", {
		slidesPerView : 2,
		scrollbar : {
			el : ".swiper-scrollbar",
			hide : false,
		},
	});

	var swiper3 = new Swiper(".recordSwiper", {
		navigation : {
			nextEl : ".swiper-button-next",
			prevEl : ".swiper-button-prev",
		},
		slidesPerView : 'auto',
	});

	var swiper4 = new Swiper(".recordSwiper2", {
		navigation : {
			nextEl : ".swiper-button-next2",
			prevEl : ".swiper-button-prev2",
		},
		slidesPerView : 1,
	});

	var swiper5 = new Swiper(".recordSwiper3", {
		navigation : {
			nextEl : ".swiper-button-next3",
			prevEl : ".swiper-button-prev3",
		},
		slidesPerView : 1,
	});

	var swiper6 = new Swiper(".recordSwiper4", {
		navigation : {
			nextEl : ".swiper-button-next4",
			prevEl : ".swiper-button-prev4",
		},
		slidesPerView : 1,
	});

	var swiper7 = new Swiper('.section_search', {
		slidesPerView : 3,
		centeredSlides : true,
		loop : false,
		spaceBetween : 100,
		slideToClickedSlide : true,
		initialSlide : 1,
	});
    var swiper8 = new Swiper(".self", {
        navigation: {
          nextEl: ".swiper-button-next4",
          prevEl: ".swiper-button-prev4",
        },

//        breakpoints: {
//          640: {
//            slidesPerView:1,
//          },
//          768: {
//            slidesPerView: 4,
//          },
//        },
        allowTouchMove : false,
		slidesPerView : 5,
    });
	 var swiper5 = new Swiper(".layerSwiper", {
		navigation : {
			nextEl : ".swiper-button-next3",
			prevEl : ".swiper-button-prev3",
		},
		slidesPerView : 1,
	});

	var swiper = new Swiper('.main_slide', {
		type : 'fraction',
		slidesPerView : '1',
        allowTouchMove : false,
		pagination : {
			el : ".page",
		},
		navigation : {
			nextEl : ".swiper-button-next",
			prevEl : ".swiper-button-prev",
		},
	});

	var swiper10 = new Swiper('.global_slide', {
	    slidesPerView: "8",
	    breakpoints: {
	      640: {
	        slidesPerView: 2,
	        spaceBetween: 20,
	      },
	      1024: {
	        slidesPerView: 4,
	        spaceBetween: 40,
	      },
	      1400: {
	        slidesPerView: 5,
	        spaceBetween: 0,
	      },
	      1600: {
	        slidesPerView: 6,
	        spaceBetween: 0,
	      },
	    },
	    navigation: {
	      nextEl: ".swiper-button-next-g",
	      prevEl: ".swiper-button-prev-g",
	    },
	  });
}

function view_show(num) {
	var left = (($(window).width() - $("#display_view" + num).width()) / 2);
	var top = (($(window).height() - $("#display_view" + num).height()) / 2);
	$("#display_view" + num).css({
		'left' : left,
		'top' : top,
		'position' : 'fixed'
	});
	document.getElementById("display_view" + num).style.display = "block";
	document.getElementById("js-popup-bg").style.display = "block";
	$("#display_view" + num).draggable();
	return false;
}
function view_hide(num) {
	$("body").removeClass("ofHidden");
	document.getElementById("display_view" + num).style.display = "none";
	document.getElementById("js-popup-bg").style.display = "none";
	return false;
}

// tab
function tab() {
	$('.tab_content').hide();
	$('.tab_content.current').show();
	$('ul.tab_menu li').click(
			function(event) {
				event.preventDefault();
				var tabId = $(this).attr('id');

				if (tabId == "tab01_last"
						&& !$("#" + tabId).attr("data-success")) {
					return;
				}

				$(this).closest('.tab_area').find(
						'ul.tab_menu li, .tab_content').not(
						'ul.tab_menu li.current').removeClass('current');
				$(
						'.tab_content[data-tab="' + tabId
								+ '"], ul.tab_menu li[id="' + tabId + '"]')
						.addClass('current').stop().fadeIn();
				$(
						'.tab_content[data-tab="' + tabId
								+ '"], ul.tab_menu li[id="' + tabId + '"]')
						.siblings('.tab_content').stop().hide();
				$(
						'.tab_content[data-tab="' + tabId
								+ '"], ul.tab_menu li[id="' + tabId + '"]')
						.siblings().removeClass('current');
			});

	/* jsp 에서 tab click 이벤트 선언해서 주석 처리함
	$('ul.self_tab li').click(function (event) {
	    event.preventDefault();
	    var tabId = $(this).attr('id');
	    $(this).closest('.tab_area').find('ul.self_tab li, .tab_content').not('ul.self_tab li.current').removeClass('current');
	    $('.tab_content[data-tab="' + tabId + '"], ul.self_tab li[id="' + tabId + '"]').addClass('current').stop().fadeIn();
	    $('.tab_content[data-tab="' + tabId + '"], ul.self_tab li[id="' + tabId + '"]').siblings('.tab_content').stop().hide();
	    $('.tab_content[data-tab="' + tabId + '"], ul.self_tab li[id="' + tabId + '"]').siblings().removeClass('current');
	  });
    */
}

function space(e) {
	// startsWith func
	String.prototype.startsWith = function(str) {
		if (this.length < str.length) {
			return false;
		}
		return this.indexOf(str) == 0;
	}

	// endsWith func
	String.prototype.endsWith = function(str) {
		if (this.length < str.length) {
			return false;
		}
		return this.lastIndexOf(str) + str.length == this.length;
	}

	$('body').find($("[class*='mar_']")).each(function() {
		var _mar = $(this).attr('class').split(' ');

		for (var i = 0; i < _mar.length; i++) {
			var num = '';
			var position = '';
			var arr = _mar[i].split('');
			if (_mar[i].startsWith('mar_') === true) {
				for (var j = 0; j < arr.slice(5).length; j++) {
					num += arr.slice(5)[j];
				}

				if (arr[4] === 't')
					position += 'margin-top';
				if (arr[4] === 'r')
					position += 'margin-right';
				if (arr[4] === 'b')
					position += 'margin-bottom';
				if (arr[4] === 'l')
					position += 'margin-left';

				$(this).css(position, parseInt(num));
			}
		}
	});

	$('body').find($("[class*='pad_']")).each(function() {
		var _pad = $(this).attr('class').split(' ');

		for (var i = 0; i < _pad.length; i++) {
			var num = '';
			var position = '';
			var arr = _pad[i].split('');
			if (_pad[i].startsWith('pad_') === true) {
				for (var j = 0; j < arr.slice(5).length; j++) {
					num += arr.slice(5)[j];
				}

				if (arr[4] === 't')
					position += 'padding-top';
				if (arr[4] === 'r')
					position += 'padding-right';
				if (arr[4] === 'b')
					position += 'padding-bottom';
				if (arr[4] === 'l')
					position += 'padding-left';

				$(this).css(position, parseInt(num));
			}
		}
	});

	$('body').find($("[class*='w']")).each(
			function() {
				var _w = $(this).attr('class').split(' ');

				for (var i = 0; i < _w.length; i++) {
					var num = _w[i].replace(/[^0-9]/g, '');
					if (_w[i].startsWith('w')
							&& (_w[i].endsWith('p') || _w[i].endsWith('px'))) {
						if (_w[i].startsWith('w') === true
								&& _w[i].endsWith('p'))
							$(this).css("width", num + "%");
						else if (_w[i].startsWith('w') === true
								&& _w[i].endsWith('px'))
							$(this).css("width", num + "px");
					}
				}
			});

	$('body').find($("[class*='fs']"))
			.each(
					function() {
						var _fs = $(this).attr('class').split(' ');

						for (var i = 0; i < _fs.length; i++) {
							var num = _fs[i].replace(/[^0-9,_]/g, '');
							var underbar = _fs[i].replace(/[^_]/g, '');
							if (_fs[i].startsWith('fs')
									&& (_fs[i].endsWith('p')
											|| _fs[i].endsWith('px') || _fs[i]
											.endsWith('rem'))) {
								if (underbar) {
									num = num.replace('_', '.');
								}
								if (_fs[i].startsWith('fs') === true
										&& _fs[i].endsWith('p'))
									$(this).css("fontSize", num + "%");
								else if (_fs[i].startsWith('fs') === true
										&& _fs[i].endsWith('px'))
									$(this).css("fontSize", num + "px");
								else if (_fs[i].startsWith('fs') === true
										&& _fs[i].endsWith('rem'))
									$(this).css("fontSize", num + "rem");
							}
						}
					});
};

if (/Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent)) {
	$(window).on('load', function() {
		// Check all tables. You may need to be more restrictive.
		$('table').each(function() {
			var element = $(this);
			// Create the wrapper element
			var scrollWrapper = $('<div />', {
				'class' : 'scrollable',
				'html' : '<div />' // The inner div is needed for styling
			}).insertBefore(element);
			// Store a reference to the wrapper element
			element.data('scrollWrapper', scrollWrapper);
			// Move the scrollable element inside the wrapper element
			element.appendTo(scrollWrapper.find('div'));
			// Check if the element is wider than its parent and thus needs to
			// be scrollable
			if (element.outerWidth() > element.parent().outerWidth()) {
				element.data('scrollWrapper').addClass('has-scroll');
			}
			// When the viewport size is changed, check again if the element
			// needs to be scrollable
			$(window).on('resize orientationchange', function() {
				if (element.outerWidth() > element.parent().outerWidth()) {
					element.data('scrollWrapper').addClass('has-scroll');
				} else {
					element.data('scrollWrapper').removeClass('has-scroll');
				}
			});
		});
		$('.tab_menu').each(function() {
			var element = $(this);
			// Create the wrapper element
			var scrollWrapper = $('<div />', {
				'class' : 'scrollable',
				'html' : '<div />' // The inner div is needed for styling
			}).insertBefore(element);
			// Store a reference to the wrapper element
			element.data('scrollWrapper', scrollWrapper);
			// Move the scrollable element inside the wrapper element
			element.appendTo(scrollWrapper.find('div'));
			// Check if the element is wider than its parent and thus needs to
			// be scrollable
			if (element.outerWidth() > element.parent().outerWidth()) {
				element.data('scrollWrapper').addClass('has-scroll');
			}
			// When the viewport size is changed, check again if the element
			// needs to be scrollable
			$(window).on('resize orientationchange', function() {
				if (element.outerWidth() > element.parent().outerWidth()) {
					element.data('scrollWrapper').addClass('has-scroll');
				} else {
					element.data('scrollWrapper').removeClass('has-scroll');
				}
			});
		});
	});
}
