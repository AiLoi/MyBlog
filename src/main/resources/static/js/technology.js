//markdown
var testEditor;

//最小课程节点
var staticCourseNode = null;

//第二级课程几点
var staticMenuSonNode = null;

//最大级菜单节点
var staticMenuNode = null;

//获取当前浏览器分辨率
var maxWidth = window.screen.width;

var newHeight = 0;

var loadingFlag;

$(function () {

    $.ajaxSetup({
        complete:function (xhr,data) {
            var CONTEXTPATH = xhr.getResponseHeader("CONTEXTPATH");
            if(CONTEXTPATH !== null)
                window.location.href=CONTEXTPATH;
        },
        beforeSend: function () {
            //注意，layer.msg默认3秒自动关闭，如果数据加载耗时比较长，需要设置time
            loadingFlag= layer.msg('加载……', { icon: 16, shade: 0.01,shadeClose:false,time:60000 });
        }
    });


    //第一次加载
    //找到active节点
    var menu = $("#menuobj");

    //加载主菜单
    $.ajax({
        url: "/technology/get_menu_technology",
        async: false,
        success: function (dataobj) {
            dataobj = dataobj.data;
            var lanType;
            $.ajax({
                url: "/technology/get_menu_lan_type",
                async: false,
                success: function (data) {
                    lanType = data.data;
                    layer.close(loadingFlag);
                }
            });

            for (var i = 0; i < lanType.length; i++) {
                if (i === 0) {
                    menu.append('<li class="active nav-item dropdown">' +
                        '<a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">' + lanType[i].lanTypeName + '<b class="caret"></b></a>' +
                        '<ul class="dropdown-menu"></ul>' +
                        '</li>')
                } else
                    menu.append('<li class="nav-item dropdown">' +
                        '<a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">' + lanType[i].lanTypeName + '<b class="caret"></b></a>' +
                        '<ul class="dropdown-menu"></ul>' +
                        '</li>')
            }

            for (var i = 0; i < dataobj.length; i++) {
                if (i === 0) {
                    $($(menu.children().get(dataobj[i].lanTypeId - 1)).children().get(1)).append('<li class="active"><a class="menufun" href="#" id=' + dataobj[i].lanId + '>' + dataobj[i].lanName + '</a></li>');
                    $($(menu.children().get(dataobj[i].lanTypeId - 1)).children().get(1)).append('<li class="divider"></li>')
                } else {
                    $($(menu.children().get(dataobj[i].lanTypeId - 1)).children().get(1)).append('<li><a class="menufun" href="#" id=' + dataobj[i].lanId + '>' + dataobj[i].lanName + '</a></li>');
                    $($(menu.children().get(dataobj[i].lanTypeId - 1)).children().get(1)).append('<li class="divider"></li>')
                }
            }
        }
    });

    var menuobj = menu.children();


    for (var i = 0; i < menuobj.length; i++) {
        var temp = menuobj[i].className.split(" ");
        for (var j = 0; j < temp.length; j++) {
            if (temp[j] === "active") {
                //记录最大节点
                staticMenuNode = menuobj[i];

                //获取最大节点下的所有子节点
                var child = menuobj[i].childNodes;
                //去除多余节点
                for (var k = 0; k < child.length; k++) {
                    if (child[k].nodeName === "#text" && !/\s/.test(child.nodeValue)) {
                        menuobj[i].removeChild(child[k]);
                    }
                }
                //获取ul节点下的ul节点
                var gson = child[1].childNodes;

                //去除多余节点
                for (var m = 0; m < gson.length; m++) {
                    if (gson[m].nodeName === "#text" && !/\s/.test(gson.nodeValue)) {
                        child[1].removeChild(gson[m]);
                    }
                }
                for (var h = 0; h < gson.length; h++) {
                    var temp1 = gson[h].className;

                    if (temp1 === "active") {
                        staticMenuSonNode = gson[h];

                        //获取菜单id
                        var oldid = gson[h].childNodes[0].id;

                        //加载左侧菜单
                        $.ajax({
                            url: "/technology/get_left_menu",
                            data: {"menuId": oldid},
                            async: false,
                            success: function (data) {
                                data = data.data;
                                var menuobj = $("#left");
                                menuobj.empty();
                                menuobj.append('<li class="active"><a href="#" class="fast" name=' + data[0].courseId + '>' + '1 .  ' + data[0].courseName + '</a></li>');
                                for (var i = 1; i < data.length; i++) {
                                    menuobj.append('<li><a href="#" class="fast" name=' + data[i].courseId + '>' + (i + 1) + '.  ' + data[i].courseName + '</a></li>')
                                }
                                layer.close(loadingFlag);
                            }
                        });
                        break;
                    }
                }
                break;
            }
        }
    }

    //加载右侧对应的文章
    var leftobj = $("#left").children();
    for (var i = 0; i < leftobj.length; i++) {

        if (leftobj[i].className === "active") {


            if (staticCourseNode != null) {
                staticCourseNode.classList.remove("active");
            }
            staticCourseNode = leftobj[i];
            var id = leftobj[i].childNodes[0].name;

            $.ajax({
                url: "/technology/get_course_value",
                data: {"courseId": id},
                async: false,
                success: function (data) {
                    data = data.data;
                    $("#doc-content").html("<textarea style='display: none'>" + data.courseValue + "</textarea>");
                    testEditor = editormd.markdownToHTML("doc-content", {//注意：这里是上面DIV的id
                        htmlDecode: "style,script,iframe|on*",
                        emoji: true,
                        taskList: true,
                        tex: true, // 默认不解析
                        flowChart: true, // 默认不解析
                        sequenceDiagram: true // 默认不解析
                    });
                    layer.close(loadingFlag);
                }

            });
        }
    }
    //给第二子菜单绑定一个事件
    //后续加载
    $(".menufun").click(function (e) {
        //让第二节点为active
        staticMenuSonNode.classList.remove("active");
        staticMenuSonNode = e.target.parentNode;
        staticMenuSonNode.classList.add("active");

        //让第一节点为active

        staticMenuNode.classList.remove("active");
        staticMenuNode = e.target.parentNode.parentNode.parentNode;
        staticMenuNode.classList.add("active");
        /**
         * 清空页面左侧菜单，加载新的菜单
         */
        var newid = e.target.id;
        $.ajax({
            url: "/technology/get_left_menu",
            data: {"menuId": newid},
            async: false,
            success: function (data) {
                data = data.data;
                var menuobj = $("#left");
                menuobj.empty();
                menuobj.append('<li class="active"><a href="#" class="fast" name=' + data[0].courseId + '>' + '1  .  ' + data[0].courseName + '</a></li>');
                for (var i = 1; i < data.length; i++) {
                    menuobj.append('<li><a href="#" class="fast" name=' + data[i].courseId + '>' + (i + 1) + '.  ' + data[i].courseName + '</a></li>')
                }
                layer.close(loadingFlag);
            }


        });

        //加载文章
        leftobj = $("#left").children();

        if (staticCourseNode != null) {
            staticCourseNode.classList.remove("active");
        }

        for (var i = 0; i < leftobj.length; i++) {
            if (leftobj[i].className === "active") {
                staticCourseNode = leftobj[i];
                var id = leftobj[i].childNodes[0].name;
                $.ajax({
                    url: "/technology/get_course_value",
                    data: {"courseId": id},
                    async: false,
                    success: function (data) {
                        data = data.data;
                        $("#doc-content").html("<textarea style='display: none'>" + data.courseValue + "</textarea>");
                        testEditor = editormd.markdownToHTML("doc-content", {//注意：这里是上面DIV的id
                            htmlDecode: "style,script,iframe|on*",
                            emoji: true,
                            taskList: true,
                            tex: true, // 默认不解析
                            flowChart: true, // 默认不解析
                            sequenceDiagram: true // 默认不解析
                        });
                        layer.close(loadingFlag);
                    }

                });
            }
        }

        //再给左侧菜单绑定单机事件
        $(".fast").click(function (e) {

            //无论是什么都要加载文章
            if (staticCourseNode !== null) {
                staticCourseNode.classList.remove("active");
            }
            e.target.parentNode.classList.add("active");
            staticCourseNode = e.target.parentNode;
            $.ajax({
                url: "/technology/get_course_value",
                data: {"courseId": e.target.name},
                async: true,
                success: function (data) {
                    data = data.data;
                    $("#doc-content").html("<textarea style='display: none'>" + data.courseValue + "</textarea>");
                    //如果是手机
                    if (maxWidth < 1280) {
                        //隐藏左侧菜单
                        document.getElementById("left").style.display = 'none';
                    }
                    testEditor = editormd.markdownToHTML("doc-content", {//注意：这里是上面DIV的id
                        htmlDecode: "style,script,iframe|on*",
                        emoji: true,
                        taskList: true,
                        tex: true, // 默认不解析
                        flowChart: true, // 默认不解析
                        sequenceDiagram: true // 默认不解析
                    });
                    layer.close(loadingFlag);
                }
            });
            //当前分辨率为电脑的时候
            if (maxWidth > 1280) {
                if (document.body.scrollWidth < (maxWidth / 2))
                    document.getElementById("left").style.display = 'none';
            }
        });
        document.getElementById("courseName").innerHTML = staticMenuSonNode.childNodes[0].innerHTML;
        document.getElementById("courseNameD").innerHTML = staticMenuSonNode.childNodes[0].innerHTML;
    });

    $(".fast").click(function (e) {

        if (staticCourseNode !== null) {
            staticCourseNode.classList.remove("active");
        }

        e.target.parentNode.classList.add("active");
        staticCourseNode = e.target.parentNode;
        $.ajax({
            url: "/technology/get_course_value",
            data: {"courseId": e.target.name},
            async: true,
            success: function (data) {
                data = data.data;
                $("#doc-content").html("<textarea style='display: none'>" + data.courseValue + "</textarea>");
                //如果是手机
                if (maxWidth < 1280) {
                    //隐藏左侧菜单
                    document.getElementById("left").style.display = 'none';
                }
                testEditor = editormd.markdownToHTML("doc-content", {//注意：这里是上面DIV的id
                    htmlDecode: "style,script,iframe|on*",
                    emoji: true,
                    taskList: true,
                    tex: true, // 默认不解析
                    flowChart: true, // 默认不解析
                    sequenceDiagram: true // 默认不解析
                });
                layer.close(loadingFlag);
            }
        });


        //当分辨率为电脑的时候
        if (maxWidth > 1280) {
            if (document.body.scrollWidth < (maxWidth / 2))
                document.getElementById("left").style.display = 'none';
        }

    });
    document.getElementById("courseName").innerHTML = staticMenuSonNode.childNodes[0].innerHTML;
    document.getElementById("courseNameD").innerHTML = staticMenuSonNode.childNodes[0].innerHTML;

    $("#courseN,#courseName").click(function () {
        if (document.getElementById("left").style.display === 'none') {
            document.getElementById("left").style.display = null;
        } else
            document.getElementById("left").style.display = 'none';
    });

    $("#allsearch").click(function () {
        var courseName = $("#keyWord").val();
        $.ajax({
            url: "/technology/get_course_by_name",
            data: {"name": courseName},
            success: function (data) {

                data = data.data;
                if (data !== null && data !== undefined && data !== "") {  //要搜索的文章不在本模块
                    if (staticMenuSonNode.childNodes[0].id !== data.courseId) {
                        //先加载左侧菜单
                        $.ajax({
                            url: "/technology/get_left_menu",
                            data: {"menuId": data.lanId},
                            async: false,
                            success: function (dataList) {
                                dataList = dataList.data;
                                var menuobj = $("#left");
                                menuobj.empty();
                                menuobj.append('<li><a href="#" class="fast" name=' + dataList[0].courseId + '>' + '1  .  ' + dataList[0].courseName + '</a></li>');
                                for (var i = 1; i < dataList.length; i++) {
                                    menuobj.append('<li><a href="#" class="fast" name=' + dataList[i].courseId + '>' + (i + 1) + '.  ' + dataList[i].courseName + '</a></li>')
                                }
                                layer.close(loadingFlag);
                            }
                        });

                        //直接设置name值为 courseId的项为active
                        staticCourseNode = document.getElementsByName(data.courseId + "")[0].parentNode;
                        staticCourseNode.classList.add("active");

                        //更换第二季菜单的active
                        staticMenuSonNode.classList.remove("active");
                        staticMenuSonNode = document.getElementById(data.lanId).parentNode;
                        staticMenuSonNode.classList.add("active");

                        //更换第一级菜单的active
                        staticMenuNode.classList.remove("active");
                        staticMenuNode = staticMenuSonNode.parentNode.parentNode;
                        staticMenuNode.classList.add("active");
                        //加载小标题
                        document.getElementById("courseName").innerHTML = staticMenuSonNode.childNodes[0].innerHTML;
                        document.getElementById("courseNameD").innerHTML = staticMenuSonNode.childNodes[0].innerHTML;
                        //加载文章

                        $("#doc-content").html("<textarea style='display: none'>" + data.courseValue + "</textarea>");
                        testEditor = editormd.markdownToHTML("doc-content", {//注意：这里是上面DIV的id
                            htmlDecode: "style,script,iframe|on*",
                            emoji: true,
                            taskList: true,
                            tex: true, // 默认不解析
                            flowChart: true, // 默认不解析
                            sequenceDiagram: true // 默认不解析
                        });
                    }
                    //要加载的文章在本模块
                    else {
                        if (staticCourseNode !== null) {
                            staticCourseNode.classList.remove("active");
                        }
                        staticCourseNode = document.getElementsByName(ui.item.id + "")[0].parentNode;
                        staticCourseNode.classList.add("active");

                        $.ajax({
                            url: "/technology/get_course_value",
                            data: {"courseId": ui.item.id},
                            async: true,
                            success: function (data) {
                                data = data.data;
                                $("#doc-content").html("<textarea style='display: none'>" + data.courseValue + "</textarea>");
                                testEditor = editormd.markdownToHTML("doc-content", {//注意：这里是上面DIV的id
                                    htmlDecode: "style,script,iframe|on*",
                                    emoji: true,
                                    taskList: true,
                                    tex: true, // 默认不解析
                                    flowChart: true, // 默认不解析
                                    sequenceDiagram: true // 默认不解析
                                });
                                layer.close(loadingFlag);
                            }
                        })
                    }
                    $(".fast").click(function (e) {

                        //无论是什么都要加载文章

                        if (staticCourseNode !== null) {
                            staticCourseNode.classList.remove("active");
                        }
                        e.target.parentNode.classList.add("active");
                        staticCourseNode = e.target.parentNode;
                        $.ajax({
                            url: "/technology/get_course_value",
                            data: {"courseId": e.target.name},
                            async: true,
                            success: function (data) {
                                data = data.data;
                                $("#doc-content").html("<textarea style='display: none'>" + data.courseValue + "</textarea>");
                                //如果是手机
                                if (maxWidth < 1280) {
                                    //隐藏左侧菜单
                                    document.getElementById("left").style.display = 'none';
                                }
                                testEditor = editormd.markdownToHTML("doc-content", {//注意：这里是上面DIV的id
                                    htmlDecode: "style,script,iframe|on*",
                                    emoji: true,
                                    taskList: true,
                                    tex: true, // 默认不解析
                                    flowChart: true, // 默认不解析
                                    sequenceDiagram: true // 默认不解析
                                });
                                layer.close(loadingFlag);
                            }
                        });

                        //当前分辨率为电脑的时候
                        if (maxWidth > 1280) {
                            if (document.body.scrollWidth < (maxWidth / 2))
                                document.getElementById("left").style.display = 'none';
                        }
                    });
                    layer.close(loadingFlag);
                } else {
                    layer.alert("没有该笔记哦", {icon: 5});
                }

            }
        })

    });

    //首次加载 如果是手机
    if (maxWidth < 1280) {
        //隐藏左侧菜单，隐藏logo
        document.getElementById("left").style.display = 'none';
        //隐藏logo
        document.getElementById("logo").style.display = 'none';
        //显示手机端课程名
        document.getElementById("courseNameD").style.display = null;
        //隐藏电脑端课程名
        document.getElementById("courseName").style.display = 'none';
        //改变导航栏主题
        document.getElementById("head").className = "navbar navbar-default";
    }

    //如果是电脑，隐藏菜单，隐藏logo
    if (maxWidth > 1280) {
        if (document.body.scrollWidth < (maxWidth / 2))
            document.getElementById("left").style.display = 'none';
        if (document.body.scrollWidth > (maxWidth / 2))
            document.getElementById("left").style.display = null;
        if (document.body.scrollWidth < (0.519 * maxWidth))
            document.getElementById("logo").style.display = 'none';
        if (document.body.scrollWidth > (0.519 * maxWidth))
            document.getElementById("logo").style.display = null;
    }


    //无论是手机或者是电脑 ，当分辨率小于768时即为手机分辨率，实现对齐导航
    if (document.body.scrollWidth < 768) {
        document.getElementById("head").style.marginLeft = '4%';
        document.getElementById("head").style.marginRight = '4%';
        document.getElementById("headson").style.width = '100%';
        //显示手机端课程名
        document.getElementById("courseNameD").style.display = null;
        //隐藏电脑端课程名
        document.getElementById("courseName").style.display = 'none';
        //改变导航栏主题
        document.getElementById("head").className = "navbar navbar-default";

    }
    if (document.body.scrollWidth > 768) {
        document.getElementById("head").style.marginLeft = null;
        document.getElementById("head").style.marginRight = null;
        document.getElementById("headson").style.width = '70%';

        //显示手机端课程名
        document.getElementById("courseNameD").style.display = 'none';
        //隐藏电脑端课程名
        document.getElementById("courseName").style.display = null;
        //改变导航栏主题
        document.getElementById("head").className = "navbar navbar-inverse";
    }
});


//监控浏览器分辨率
$(window).resize(function () {
    //如果是电脑
    if (maxWidth > 1280) {
        //当前页面小于一半的时候隐藏左侧菜单
        if (document.body.scrollWidth > (maxWidth / 2))
            document.getElementById("left").style.display = null;
        if (document.body.scrollWidth < (maxWidth / 2))
            document.getElementById("left").style.display = 'none';


        //当达到一定程度时隐藏logo
        if (document.body.scrollWidth < (0.519 * maxWidth))
            document.getElementById("logo").style.display = 'none';
        if (document.body.scrollWidth > (0.519 * maxWidth))
            document.getElementById("logo").style.display = null;
    }
    //如果是手机
    if (maxWidth < 1280) {
        document.getElementById("logo").style.display = 'none';
    }
    if (document.body.scrollWidth < 776) {
        document.getElementById("head").style.marginLeft = '4%';
        document.getElementById("head").style.marginRight = '4%';
        document.getElementById("headson").style.width = '100%';
        //隐藏手机端课程名
        document.getElementById("courseNameD").style.display = null;
        //显示电脑端课程名
        document.getElementById("courseName").style.display = 'none';
        //改变导航栏主题
        document.getElementById("head").className = "navbar navbar-default";
    }
    if (document.body.scrollWidth > 776) {
        //隐藏手机端课程名
        document.getElementById("courseNameD").style.display = 'none';
        //显示电脑端课程名
        document.getElementById("courseName").style.display = null;
        //改变导航栏主题
        document.getElementById("head").className = "navbar navbar-inverse";

        document.getElementById("head").style.marginLeft = null;
        document.getElementById("head").style.marginRight = null;
        document.getElementById("headson").style.width = '70%';
    }
});

//动态input下拉，全局搜索
$(function () {
    //var searchid = staticMenuSonNode.childNodes[0].id;
    $("#keyWord").autocomplete({
        source: function (requset, respone) {
            //request对象只有一个term属性，对应用户输入的文本
            $.ajax({
                url: "/technology/get_course_name",
                data: {
                    "keyWord": requset.term
                },
                success: function (data) {
                    data = data.data;
                    respone($.map(data, function (item) {
                        return {
                            label: item.courseName,  //下拉显示的内容
                            value: item.courseName,   //下拉对应的值
                            id: item.courseId,
                            lanId: item.lanId
                        }
                    }));
                    layer.close(loadingFlag);
                }
            });
        },
        select: function (event, ui) {
            $("#keyWord").val(ui.item.value);
            //获取第二级菜单的id


            //要搜索的文章不在本模块
            if (staticMenuSonNode.childNodes[0].id !== ui.item.lanId) {
                //先加载左侧菜单
                $.ajax({
                    url: "/technology/get_left_menu",
                    data: {"menuId": ui.item.lanId},
                    async: false,
                    success: function (data) {
                        data = data.data;
                        var menuobj = $("#left");
                        menuobj.empty();
                        menuobj.append('<li><a href="#" class="fast" name=' + data[0].courseId + '>' + '1  .  ' + data[0].courseName + '</a></li>');
                        for (var i = 1; i < data.length; i++) {
                            menuobj.append('<li><a href="#" class="fast" name=' + data[i].courseId + '>' + (i + 1) + '.  ' + data[i].courseName + '</a></li>')
                        }
                        layer.close(loadingFlag);
                    }
                });

                //直接设置name值为 courseId的项为active
                staticCourseNode = document.getElementsByName(ui.item.id + "")[0].parentNode;
                staticCourseNode.classList.add("active");

                //更换第二季菜单的active
                staticMenuSonNode.classList.remove("active");
                staticMenuSonNode = document.getElementById(ui.item.lanId).parentNode;
                staticMenuSonNode.classList.add("active");

                //更换第一级菜单的active
                staticMenuNode.classList.remove("active");
                staticMenuNode = staticMenuSonNode.parentNode.parentNode;
                staticMenuNode.classList.add("active");
                //加载小标题
                document.getElementById("courseName").innerHTML = staticMenuSonNode.childNodes[0].innerHTML;
                document.getElementById("courseNameD").innerHTML = staticMenuSonNode.childNodes[0].innerHTML;
                //加载文章

                $.ajax({
                    url: "/technology/get_course_value",
                    data: {"courseId": ui.item.id},
                    async: false,
                    success: function (data) {
                        data = data.data;
                        $("#doc-content").html("<textarea style='display: none'>" + data.courseValue + "</textarea>");
                        testEditor = editormd.markdownToHTML("doc-content", {//注意：这里是上面DIV的id
                            htmlDecode: "style,script,iframe|on*",
                            emoji: true,
                            taskList: true,
                            tex: true, // 默认不解析
                            flowChart: true, // 默认不解析
                            sequenceDiagram: true // 默认不解析
                        });
                        layer.close(loadingFlag);
                    }
                });

                newHeight = staticCourseNode.offsetTop;
                $("#left").animate({scrollTop: newHeight - $("#left").height() / 2}, 200);

            }
            //要加载的文章在本模块
            else {

                if (staticCourseNode !== null) {
                    staticCourseNode.classList.remove("active");
                }


                staticCourseNode = document.getElementsByName(ui.item.id + "")[0].parentNode;
                staticCourseNode.classList.add("active");

                $.ajax({
                    url: "/technology/get_course_value",
                    data: {"courseId": ui.item.id},
                    async: true,
                    success: function (data) {
                        data = data.data;
                        $("#doc-content").html("<textarea style='display: none'>" + data.courseValue + "</textarea>");
                        testEditor = editormd.markdownToHTML("doc-content", {//注意：这里是上面DIV的id
                            htmlDecode: "style,script,iframe|on*",
                            emoji: true,
                            taskList: true,
                            tex: true, // 默认不解析
                            flowChart: true, // 默认不解析
                            sequenceDiagram: true // 默认不解析
                        });
                        layer.close(loadingFlag);
                    }
                });

                newHeight = staticCourseNode.offsetTop;

                $("#left").animate({scrollTop: newHeight - $("#left").height() / 2}, 300);

            }
            $(".fast").click(function (e) {

                //无论是什么都要加载文章

                if (staticCourseNode !== null) {
                    staticCourseNode.classList.remove("active");
                }
                e.target.parentNode.classList.add("active");
                staticCourseNode = e.target.parentNode;
                $.ajax({
                    url: "/technology/get_course_value",
                    data: {"courseId": e.target.name},
                    async: true,
                    success: function (data) {
                        data = data.data;
                        $("#doc-content").html("<textarea style='display: none'>" + data.courseValue + "</textarea>");
                        //如果是手机
                        if (maxWidth < 1280) {
                            //隐藏左侧菜单
                            document.getElementById("left").style.display = 'none';
                        }
                        testEditor = editormd.markdownToHTML("doc-content", {//注意：这里是上面DIV的id
                            emoji: true,
                            taskList: true,
                            tex: true, // 默认不解析
                            flowChart: true, // 默认不解析
                            sequenceDiagram: true, // 默认不解析
                            htmlDecode: "style,script,iframe|on*"
                        });
                        layer.close(loadingFlag);
                    }
                });

                //当前分辨率为电脑的时候
                if (maxWidth > 1280) {
                    if (document.body.scrollWidth < (maxWidth / 2))
                        document.getElementById("left").style.display = 'none';
                }
            });
        }
    })
});

