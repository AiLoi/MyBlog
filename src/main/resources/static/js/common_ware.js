// JavaScript Document
var menudata = null,indexloading = -1,loginuserdata=null,curruserid="";

//基础配置
var urlPrdfix = (function() {
    if (location.origin.indexOf('127.0.0.1') == -1) {
        return '';
    }
    return '';
}());
var urlaction = (function() {
    if (location.origin.indexOf('127.0.0.1') == -1) {
        return location.origin+'/token.action';
    }else
    	return 'http://127.0.0.1:8080/token.action';
}());

var uriapi = (function() {
    if (location.origin.indexOf('127.0.0.1') == -1) {
        return location.origin+'';
    }else
    	return 'http://127.0.0.1:8080';
}());

var httpApi = uriapi;
//基础配置 end

var token="a123456:1501234012202:6df9495fbc3c22b80af530ed44f9a84a";
$.ajax({
        type: "POST",
        url:urlaction,
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        data: null,
        contentType: "application/text",

        success: function (tokendata) {
          token=tokendata;
            
          console.log(token);
        },
        error: function (e) {
            errorcallback(e);
        }
});
/*
try {
    loginuserdata = JSON.parse(localStorage.getItem("loginuserdata"));
	console.log(data);
}catch(err){
	loginuserdata = null;
    localStorage.removeItem("loginuserdata");
}

if(loginuserdata==null){
	var authurl = 'http://192.168.0.9:8080/wms/web' + "/auth/getMenu";
	ajax(authurl, "GET", null, function (data) {
		console.log(data);
		loginuserdata=data;
        localStorage.setItem("loginuserdata", JSON.stringify(data));
		$(".disusername").html(loginuserdata.userName);
	}, function (e) {
		
	});
}*/

$(function () {
	$(".menuuser .bottom .border").siblings().hide();
    var userlogindate = new Date();
    var userlocalStorage = "menudata" + userlogindate.getFullYear() + "-" + userlogindate.getMonth() + "-" + userlogindate.getDate() + "-" + userlogindate.getHours();
	//if(loginuserdata!=null)$(".disusername").html(loginuserdata.userName);
    try {
        menudata = JSON.parse(localStorage.getItem(userlocalStorage));
    }
    catch (err) {
        menudata = null;
        localStorage.clear();
    }
    if (menudata == null) {
        localStorage.clear();
        $.getJSON(urlPrdfix+'/data/menu.json', function (data) {
            menudata = data;
            localStorage.setItem(userlocalStorage, JSON.stringify(menudata));
            setDrawMenu();
        });
    } else {
        setDrawMenu();
    }

    $(window).resize(function () {
        setScroll();
    });

    //时间
    var currdate = new Date();
    $(".currdate").html(currdate.getFullYear() + "年" + (currdate.getMonth() + 1) + "月" + currdate.getDate() + "日");

    //右上角的，个人信息菜单
    var objusermenu = null;
    $(".username").hover(function () {
        $(".menuuser").show();
    }, function () {
        objusermenu = setTimeout(function () { $(".menuuser").hide(); }, 300);
    });
    $(".menuuser").hover(function () {
        clearTimeout(objusermenu);
    }, function () {
        objusermenu = setTimeout(function () { $(".menuuser").hide(); }, 300);
    });

    //日期统一加类，直接绑定
    //bindDatePicker();

    //iframe点取消
    $(".btn_iframe_close_window").click(function () {
        parent.layer.close(parseInt(parent.$(".layui-layer-iframe").attr("times")));
    });

    $("#btn_a_clear").click(function () { localStorage.clear(); $(".menuuser").hide(); });

    $("label :checkbox").change(function () {
        if ($(this).is(":checked"))
            $(this).parent().addClass("on");
        else
            $(this).parent().removeClass("on");
    });
    $("label :checkbox").change();

    $("a").click(function () { $(this).blur(); })
});

//表单数据验证
var FormDataCheck = {
    isNull: function (v) {
        var p = /^.+$/;
        return !p.test(v);
    },
    checkStrLen: function (s, x, y) {
        //s为要验证的字符,x为最小长度,y为最大长度
        eval("var p = /^.{" + x + "," + y + "}$/");
        return p.test(s);
    },
    isNumber: function (s, x, y) {
        //s为要验证的字符,x为最小长度,y为最大长度
        eval("var p = /^\\d{" + x + "," + y + "}$/");
        return p.test(s);
    },
    isExcel: function (v) {
        var reg = /\.(xls|xlsx)$/i;
        return reg.test(v);
    },
    isPicture: function (v) {
        var reg = /\.(jpg|png|jpeg|gif)$/i;
        return reg.test(v);
    },
    isMp4: function (v) {
        var reg = /\.(mp4|flv)$/i;
        return reg.test(v);
    },
    isAllowFile: function (v) {
        var reg = /\.(gif|jpg|jpeg|png|rar|zip|doc|docx|pdf|xlsx|xls|ppt|mp4|txt)$/i;
        return reg.test(v);
    },
    isEmail: function (v) {
        var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return reg.test(v);
    },
    isUserName: function (v) {
        var reg = /^[a-zA-Z]\w{3,11}$/;
        return reg.test(v);
    },
    isUserRealName: function (v) {
        var reg = /^[\u4e00-\u9fa5]{2,10}$/;
        return reg.test(v);
    },
    isCellPhone: function (v) {
        var reg = /^1[345678]\d{9}$/;
        return reg.test(v);
    },
    isChinese: function (v) {
        var reg = /^[\u4e00-\u9fa5]+$/;
        return reg.test(v);
    },
    isPassword: function (v) {
        var reg = /^[a-zA-Z0-9_\.\-\!\@\#\$\%]{6,18}$/;
        return reg.test(v);
    },
    isDecimal: function(v) {
        if (v != null && typeof (v) != "undefined") {
            var str = v.toString();
            if (str != "") {
                if (str.length == 1 && str == "0") {
                    return true;
                } else {
                    var pattern = '^-?[1-9]\\d*$|^-?0\\.\\d*$|^-?[1-9]\\d*\\.\\d*$';
                    var reg = new RegExp(pattern, 'g');
                    return reg.test(str);
                }
            }else
                return false;
        }
        return false;
    }
}

var CApp = {
    initBase: function (name, data) {
        var nodes = [["span", "div", "th", "td", "b"], ["input", "textarea", "select"]];
        var appblock = $("[c-app='" + name + "']");
        var appmodels = appblock.find("[c-model]");
        appmodels.each(function (i, item) {
            var curr = $(this);
            var nodeName = $(this)[0].nodeName.toLowerCase();
            if (nodes[0].indexOf(nodeName) > -1) {
                curr.html(formartNullString(data[curr.attr("c-model")]));
            }
            if (nodes[1].indexOf(nodeName) > -1) {
                curr.val(data[curr.attr("c-model")]);
            }

        });
    },
    initTable: function (name, data) {
        var appblock = $("[c-app='" + name + "']");
        var apptr = appblock.find("tr:eq(1)");
        appblock.find("tr:gt(0)").remove();
        $.each(data, function (i, item) {
            var appclone = apptr.clone();
            
            var models = appclone.find("[c-model]");
            models.each(function () {
                var curr = $(this);
                if (curr.attr("c-model") == "indexnumber") {
                    curr.html(i + 1);
                } else
                    curr.html(item[curr.attr("c-model")]);
            });

            var appchildren = appclone.find("*");
            appchildren.each(function () {
                var currnode = $(this);
                for (var j = 0; j < currnode[0].attributes.length; j++) {
                    for (var o in item) {
                        eval("var re = /{{" + o.toString() + "}}/;");
                        currnode[0].attributes[j].value = currnode[0].attributes[j].value.replace(re, item[o]);
                    }
                }
            });

            appblock.append(appclone);
        });
    }
}

///校验时间，控制前后时间的设置
///StartDateClass 前一个时间的类名（如：开始时间）
///EndDateClass 后一个时间的类名（如：结束时间）
function checkDateDifference(StartDateClass, EndDateClass) {
    var startdate = $('.' + StartDateClass);
    var endtdate = $('.' + EndDateClass);
    //开始时间决定着结束时间不能早于开始时间
    startdate.datetimepicker().on('changeDate', function (ev) {
        endtdate.datetimepicker('setStartDate', ev.date.getFullYear() + '-' + (ev.date.getMonth() + 1) + '-' + (ev.date.getDate()));
    });

    //结束时间决定着开始时间不能晚于结束时间
    endtdate.datetimepicker().on('changeDate', function (ev) {
        startdate.datetimepicker('setEndDate', ev.date.getFullYear() + '-' + (ev.date.getMonth() + 1) + '-' + (ev.date.getDate()));
    });
}

//设置滚动条
function setScroll() {
    var navscoll = $("#navscoll");
    if (navscoll.length == 0) return true;
    var hnavscoll = $("#hnavscoll");
    var hnavscoll_div = $("#hnavscoll div");
    var navscoll_div = $("#navscoll div");
    hnavscoll_div.height($("#navscollbox ul").height());
    if (hnavscoll_div.height() <= hnavscoll.height()) {
        $("#navscollbg,#hnavscoll").hide();
    } else {
        $("#navscollbg,#hnavscoll").show();

        var scollheight = hnavscoll.height() / hnavscoll_div.height() * hnavscoll.height();
        navscoll_div.height(scollheight);
        navscoll.css({ "height": "100%" });
        navscoll.height(navscoll.height() - scollheight);

        var aa = true, scrolla, scrollb;
        $("#navscollbox").scroll(function () {
            var scollvalue = $("#navscollbox ul").height() - $(this).height();
            navscoll_div.css({ "top": (($(this).scrollTop() / scollvalue * 100) + "%") });
        });
        hnavscoll.hover(function () {
            hnavscoll.scrollTop($("#navscollbox").scrollTop());
            hnavscoll.scroll(function () {
                $("#navscollbox").scrollTop($(this).scrollTop());
            });
        }, function () {
        });
    }
}
function setDrawMenu() {
    setFristMenu();
    setSecondMenu();
    if (location.href.indexOf("key=") > -1) {
        var menukey = parseInt(GetQueryString("key"));
        var ids = getMenuItemIDs(menukey);
        
        if (ids.length == 0) {
            $("a.frist[data-id='" + menukey + "']").parent().addClass("on");
        } else if (ids.length == 1) {
            if (menukey == ids[0]) {
                $("a.frist[data-id='" + ids[0] + "']").parent().addClass("on");
            }
            else {
                $("a.frist[data-id='" + ids[0] + "']").click();
                $("a.second").removeClass("on");
                $("a.second[data-id='" + menukey + "']").addClass("on");
            }

        } else if (ids.length == 2) {
            $("a.frist[data-id='" + ids[1] + "']").click();
            $("a.second[data-id='" + ids[0] + "']").click();
            $("a.second").removeClass("on");
            $("a.third[data-id='" + menukey + "']").addClass("on");
        } else {

        }
    }
}
function setFristMenu() {
    $.each(menudata, function (i, item) {
        if (item.parent == 0) {
            var href = item.url == "javascript:void(0)" ? item.url : (urlPrdfix + item.url);
            var isHasChildren = hasChildren(item.id);
            $("ul.fisrt").append("<li class='frist " + (isHasChildren ? "ico ico-arrow" : "") + "'><a class='frist ico ico-" + item.class + "' data-id='" + item.id + "' href='" + href + "'>" + item.name + "</a></li>");
        }
    });
    setScroll();
}
function setSecondMenu() {
    $("a.frist").click(function () {
        var curr = $(this);
        if (curr.siblings("ul.second").length > 0) {
            curr.siblings("ul.second").remove();
            curr.parent().removeClass("arrowtop");
        } else {
            $("ul.fisrt>li").removeClass("on");
            $("ul.second").remove();
            curr.parent().addClass("on arrowtop");
            var id = curr.attr("data-id");
            curr.after("<ul class='second'></ul>");
            $.each(menudata, function (i, item) {
                if (item.parent == id) {
                    var href = item.url == "javascript:void(0)" ? item.url : (urlPrdfix + item.url);
                    var isHasChildren = hasChildren(item.id);
                    $("ul.second").append("<li><a class='second " + (isHasChildren ? "ico ico-arrow" : "") + "' data-id='" + item.id + "' href='" + href + "'>" + item.name + "</a></li>");
                }
            });
            setScroll();
            setThirdMenu();
        }
    });
}
function setThirdMenu() {
    $("a.second").click(function () {
        var curr = $(this);
        if (curr.siblings("ul.third").length > 0) {
            curr.siblings("ul.third").remove();
            curr.removeClass("arrowtop");
        } else {
            $("a.second").removeClass("on");
            $("ul.third").remove();
            curr.addClass("on arrowtop");
            var id = curr.attr("data-id");
            curr.after("<ul class='third'></ul>");
            $.each(menudata, function (i, item) {
                if (item.parent == id) {
                    var href = item.url == "javascript:void(0)" ? item.url : (urlPrdfix + item.url);
                    $("ul.third").append("<li><a class='third' data-id='" + item.id + "' href='" + href + "'>" + item.name + "</a></li>");
                }
            });
            setScroll();
            $("a.third").click(function () {
                $("a.second").removeClass("on");
                var curr = $(this);
                curr.addClass("on");
            });
        }
    });


}
//菜单，是否有子元素
function hasChildren(id) {
    var isHas = false;
    $.each(menudata, function (i, item) {
        if (item.parent == id) {
            isHas = true;
            return false;
        }
    });
    return isHas;
}
//菜单，根据子节点，查询
function getMenuItemIDs(id) {
    var items = new Array();
    $.each(menudata, function (i, item) {
        if (item.id == id) {

            if (item.parent == 0) {
                return false;
            }
            items.push(item.parent);
            id = item.parent;
        }

    });
    $.each(menudata, function (i, item) {
        if (item.id == id) {

            if (item.parent == 0) {
                return false;
            }
            items.push(item.parent);
        }

    });
    return items;
}

///Table中的删除
///table 操作的Table
function tabledelete(table) {
    table.find(".btn_table_delete").click(function () {
        var id = $(this).attr("data-id");
        var text = "确定" + $(this).text() + "吗？";
        layer.confirm(text, {
            btn: ['确定', '取消'],
            icon: 3
        }, function () {
            //每个有删除页面，都要单独有function_delete(id)函数
            function_delete(id);
            layer.close(parseInt($(".layui-layer-shade").attr("times")));
        }, function () {

        });
    });
    
}

///Table中的编辑或查看
///table 操作的Table
function tableedit(table) {
     
   $(table).on("click",".btn_table_edit",function(){
        
        var title = $(this).attr("data-title");
        var href = encodeURI($(this).attr("data-href")||$(this).attr("my-href"));
        var size = $(this).attr("data-size");
        var size_x = size.split(",")[0];
        var size_y = size.split(",")[1];
        layer.open({
            type: 2,
            title: title,
            shadeClose: false,
            shade: 0.8,
            area: [size_x, size_y],
            content: href
        });
    });
}

function bindDatePicker(panel) {
    var txtdate = null;

    if (panel == undefined) {
        txtdate = $('.txtdatepicker');
    } else {
        txtdate = panel.find('.txtdatepicker');
    }

    txtdate.each(function () {
        var pos = $(this).attr("data-pickerPosition") == undefined ? "" : $(this).attr("data-pickerPosition");
        $(this).datetimepicker({
            language: 'zh-CN',
            minView: 2,
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            pickerPosition: pos,
        });
    });
}

///格式化三位小数
///x 数字
function formartDecimal_f(x) {
    var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        //alert('function:changeTwoDecimal->parameter error');
        return false;
    }
    f_x = Math.round(f_x * 1000) / 1000;
    var s_x = f_x.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = s_x.length;
        s_x += '.';
    }
    while (s_x.length <= pos_decimal + 3) {
        s_x += '0';
    }
    return s_x;
}

///格式化金额
///金额
function outputmoney(number) {
    number = number.replace(/\,/g, "");
    if (isNaN(number) || number == "") return "";
    number = Math.round(number * 100) / 100;
    if (number < 0)
        return '-' + outputdollars(Math.floor(Math.abs(number) - 0) + '') + outputcents(Math.abs(number) - 0);
    else
        return outputdollars(Math.floor(number - 0) + '') + outputcents(number - 0);
}

function outputdollars(number) {
    if (number.length <= 3)
        return (number == '' ? '0' : number);
    else {
        var mod = number.length % 3;
        var output = (mod == 0 ? '' : (number.substring(0, mod)));
        for (i = 0; i < Math.floor(number.length / 3) ; i++) {
            if ((mod == 0) && (i == 0))
                output += number.substring(mod + 3 * i, mod + 3 * i + 3);
            else
                output += ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
        }
        return (output);
    }
}
function outputcents(amount) {
    amount = Math.round(((amount) - Math.floor(amount)) * 100);
    return (amount < 10 ? '.0' + amount : '.' + amount);
}
///格式化金额  end

///空字符串处理
///字符串
function formartNullString(str) {
    return (str == null || str == "") ? "--" : str;
}

function showloading() {
    indexloading = layer.load(3, { shade: [0.4, '#000'] });
}

///格式化文件名
function formartFileName(name) {
    var name1 = name.split(".")[0];
    var name2 = name.split(".")[1];
    if (name1.length > 9) {
        name = name1.substr(0, 3) + "..." + name1.substr(name1.length - 3) + "." + name2;
    }
    return name;
}

///格式化文件大小
function formartFileSize(size) {
    size = size / 1024 * 10;
    size = Math.floor(size);
    size = size / 10;
    return size;
}

///物料编码
function formartCodeNo(str) {
	if (str.length > 10) {
	    str = str.substr(0, 10) + "<br/>" + str.substr(10, str.length - 10);
	}
	return str;
}

var lastshowsum = { "classname": "", "ojblayerid": -1, "objClearid": -1 };
function tableshowsum() {
    $(".showsum1,.showsum2,.showsum3,.showsum4").unbind("hover");
    $(".showsum1,.showsum2,.showsum3,.showsum4").hover(function () {
        var curr = $(this);
        var showsum = "";
        if (curr.hasClass("showsum1")) {
            showsum = ".showsum1";
        } else if (curr.hasClass("showsum2")) {
            showsum = ".showsum2";
        } else if (curr.hasClass("showsum3")) {
            showsum = ".showsum3";
        } else if (curr.hasClass("showsum4")) {
            showsum = ".showsum4";
        }
        $(showsum).addClass("sum");
        if (showsum == lastshowsum.classname) { clearTimeout(lastshowsum.objClearid); }
        else {
            var sum = 0.0;
            $(showsum).each(function () {
                var i = $(this).attr("data-val");
                if (i != undefined)
                    sum += parseFloat(i);
            });
            lastshowsum.ojblayerid = layer.tips(($(showsum).eq(0).html() + "：（总）" + formartDecimal_f(sum)), $(showsum).eq(0), {
                tips: [2, '#f7b824'],
                time: 20000
            });
        }
        lastshowsum.classname = showsum;
    }, function () {
        $(".showsum1,.showsum2,.showsum3,.showsum4").removeClass("sum");
        var ojblayerid = lastshowsum.ojblayerid;
        lastshowsum.objClearid = setTimeout(function () { layer.close(ojblayerid); lastshowsum.classname = "";}, 300);
    });
}

///取URL的值
///值名
function GetQueryString(name) {
    var url=decodeURI(window.location.search);
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = url.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

///那些判断是否是数字的都用这个做
///把Input传进来
function CheckisDecimal(currinput) {
    if (FormDataCheck.isDecimal(currinput.val()) == false) {
        layer.tips("格式为数字", currinput, {
            tips: [2, '#f7b824'],
            tipsMore: true,
            time: 5000
        });
        return false;
    } else if (currinput.val().indexOf(".") > -1 && currinput.val().split(".")[1].length > 3) {
        layer.tips("小数位不能大于三位", currinput, {
            tips: [2, '#f7b824'],
            tipsMore: true,

            time: 5000
        });
        return false;
    } else {
        return true;
    }
}

///对比数量，订单数量不能小于（已收货数量+到货数量）
///indexitem index
///arraylist 数组
///currinput 输入的到货数量
function contrastQuantity(indexitem, arraylist, currinput) {
    var ordersum = arraylist[indexitem].zddsl * 1000;
    var Receiving = arraylist[indexitem].zrksl * 1000;
    var AOGsum = currinput.val() * 1000;
    if (ordersum < (Receiving + AOGsum)) {
        layersMoretips("订单数量不能小于（已收货数量+到货数量）", currinput);
        return -1;
    } else {
        return AOGsum / 1000;
    }
}

function dataBindFile(statevalue, arraylist) {
    $(".filebox").empty();
    $.each(arraylist.file, function (i, item) {
        var staticname = "";
        if (item.static == 1) staticname = "上传完成";
        var strtext = "";
        strtext += ("<div class='file ico ico-file col-lg-4 col-xs-6'>");
        strtext += ("<div class='fileer'>" + item.by + "</div>");
        strtext += ("<div class='filename'><span title='" + item.filename + "'>" + formartFileName(item.filename) + "</span>  (" + item.filesize + "kb)</div>");
        strtext += ("<div class='clearfx'></div>");
        if (statevalue==2)
            strtext += ("<div class='fileer'><a href='javascript:void(0)' data-id='" + item.id + "'>删除</a></div>");
        else
            strtext += ("<div class='fileer'></div>");
        strtext += ("<div class='filename'>" + staticname + "</div>");
        strtext += ("</div>");
        $(".filebox").append(strtext);
    });
    deletefile();
}

///直接调用关闭按键
function iframe_close_window() {
    $(".btn_iframe_close_window").click();
}

function setspandmap(value) {
    $(".mapadress span:last").html(value);
}

///绘制翻页
///翻页的数据对象
///当前面的索引0开始
///每页多少条
function drawpager(obj, pageindex, count) {
    try {
        if (obj.total > count) {
            $(".btnlinum").remove();
            $(".btnlistart,.btnliend").removeClass("disabled");
            $(".pagerbox").show().data("index", pageindex);
            var j = Math.ceil(obj.total / count);
            for (i = 0; i < j; i++) {
                if (i == pageindex)
                    $(".btnliend").before("<li class='btnlinum active'><a href='javascript:void(0);'>" + (i + 1) + "</a></li>");
                else
                    $(".btnliend").before("<li class='btnlinum'><a href='javascript:actionpager(" + i + ");'>" + (i + 1) + "</a></li>");
            }
            if (pageindex == 0) $(".btnlistart").addClass("disabled");
            if (pageindex == (j - 1)) $(".btnliend").addClass("disabled");

            $(".btnlistart:not([class*='disabled']) a").click(function () {
                pageindex = $(".pagerbox").data("index");
                if (pageindex == 0) return;
                actionpager(pageindex - 1);
            });
            $(".btnliend:not([class*='disabled']) a").click(function () {
                pageindex = $(".pagerbox").data("index");
                if (pageindex == (j - 1)) return;
                actionpager(pageindex + 1);
            });
        }
    }
    catch (e) {
    }

}

///给Table空时，加个显示
///table 要操作的Table
function dataIsNull(table) {
    if (table.find("tr").length == 1) table.append("<tr class='datanull'><td class='datanull' colspan='" + (table.find("th").length) + "'>没有可显示的数据</td></tr>");
}

///ajax请求
///URL
///TYPE，get,post,put,delete
///data 数据
///successcallback 成功回调数据
///errorcallback 失败回调数据
function ajax(url, type, data, successcallback, errorcallback) {
    $.ajax({
        type: type,
        url: url,
        data: data,
        xhrFields: {
                        withCredentials: true
                    },
                    crossDomain: true,
        contentType: "application/json",
        success: function (data) {
            successcallback(data);
        },
        error: function (e) {
            errorcallback(e);
        }
    });

}

///多个Tips直接调用
///tips
///el element
function layersMoretips(tips,el) {
    layer.tips(tips, el, {
        tips: [2, '#f7b824'],
        tipsMore: true,
        time: 5000
    });
}

///创建Select(列表用).selZCLFS
///data 数据
///selectedvalue 如果需要选中给值，不需要给-1
///index 没有给null,index是组件数据时为了使用方便
function createselect(data, selectedvalue, index) {
    var options = "";
    if (data == null) {
        return "<select class='form-control selZCLFS' data-index='" + index + "'><option value=''></option></select>";
    }
    $.each(data.body, function (i, item) {
        if (selectedvalue == item.key)
            options += "<option selected value='" + item.key + "'>" + item.value + "</option>";
        else
            options += "<option value='" + item.key + "'>" + item.value + "</option>";
    });
    if (index == null)
        return "<select class='form-control selZCLFS'>" + options + "</select>";
    else
        return "<select class='form-control selZCLFS' data-index='" + index + "'>" + options + "</select>";
}