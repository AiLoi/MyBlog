var loadingFlag;


var testEditor;

var articleId = window.location.href.split("?")[1].substring(0, 12).split("=")[1];


var articleVo;

$(function () {
    $.ajaxSetup({
        beforeSend: function () {
            //注意，layer.msg默认3秒自动关闭，如果数据加载耗时比较长，需要设置time
            loadingFlag = layer.msg('加载……', {icon: 16, shade: 0.01, shadeClose: false, time: 60000});
        },
        complete: function (xhr, data) {
            var CONTEXTPATH = xhr.getResponseHeader("CONTEXTPATH");
            if (CONTEXTPATH !== null)
                window.location.href = CONTEXTPATH;
        }
    });


    $.ajax({
        url: "/blog/user/is_expired",
        async: false,
        success: function (data) {
            //判断会话是否过期

            var isExpired = data.data.expired;
            //获取当前会话的userinfo
            if (isExpired) {
                localStorage.setItem("userDetails", JSON.stringify(data.data.userDetails));
                var userinfo = JSON.parse(localStorage.getItem("userDetails"));
                document.getElementById("login").style.display = 'none';
                $(".figureURL")[0].src = userinfo.figureUrl;
                $(".figureURL")[1].src = userinfo.figureUrl;
                $("#username").html(userinfo.nickname);

                $.ajax({
                    url: "/write/get_article_details",
                    type: 'post',
                    async: false,
                    data: {"articleId": articleId},
                    success: function (article) {
                        articleVo = article.data;
                        article = article.data;
                        $(".title")[0].innerHTML = article.title;
                        $("#author").append(
                            '                <a class="avatar" id="header_avatar" href="#">' +
                            '                    <img src="" alt="头像">' +
                            '                </a>' +
                            '                <div class="info">' +
                            '                    <span class="name">' +
                            '                        <a href="#">' + article.blogUser.nickname + '</a>' +
                            '                    </span>' +
                            '                    <a class="btn btn-success follow"><i class="iconfont ic-follow">' +
                            '                    </i><span>关注</span>' +
                            '                    </a>' +
                            '                    <div class="meta">' +
                            '                        创建时间：<span class="publish-time">' + article.createTimeStr + '</span>' +
                            '                            阅读：<span class="views-count">' + article.visitCount + '</span>' +
                            '                            评论：<span class="comments-count">' + article.commentCount + '</span>' +
                            '                    </div>'
                        );

                        $("#header_avatar").children()[0].src = article.blogUser.figureUrl;

                        $("#doc-content").html("<textarea style='display: none'>" + article.markdownContent + "</textarea>");
                        testEditor = editormd.markdownToHTML("doc-content", {//注意：这里是上面DIV的id
                            // htmlDecode: "style,script,iframe|on*",
                            emoji: true,
                            taskList: true,
                            tex: true, // 默认不解析
                            flowChart: true, // 默认不解析
                            sequenceDiagram: true // 默认不解析
                        });

                        //加载底部头像
                        $("#bottom_avatar").children()[0].src = userinfo.figureUrl;


                        //加载评论
                        var commentList = article.blogCommentVoList;

                        for (var i = 0; i < commentList.length; i++) {
                            //评论者头像
                            var avatar = "comment_avatar" + i;


                            //富文本id
                            var fir_textareaId = 'fir_textarea_' + new Date().getTime() + i;

                            //一級评论的用户信息
                            var comment_blog_user = commentList[i].blogUser;

                            //为每条评论设置id
                            var comment_id = "comment_id_" + commentList[i].commentId;

                            //获取每条评论下的二级评论
                            var replyCommentList = commentList[i].replyCommentList;

                            var reply_user_id = "reply_user_id_" + commentList[i].blogUser.userId;

                            $("#normal-comment-list").append(
                                ' <div>' +
                                '                        <div class="comment" id=' + comment_id + '>' +
                                '                            <div class=' + reply_user_id + '>' +
                                '                                <div class="author">' +
                                '                                    <div data-v-f3bf5228="" class="v-tooltip-container" style="z-index: 0;">' +
                                '                                        <div class="v-tooltip-content">' +
                                '                                            <a href="#" target="_blank" class="avatar">' +
                                '                                                <img id=' + avatar + '  alt="头像" src="">' +
                                '                                            </a>' +
                                '                                        </div> <!---->' +
                                '                                    </div>' +
                                '                                    <div class="info">' +
                                '                                        <a href="#" target="_blank" class="name">' + comment_blog_user.nickname + '</a>' +
                                '                                        <div class="meta">' +
                                '                                            <span>' + (i + 1) + '楼·' + commentList[i].createTimeStr + '</span>' +
                                '                                        </div>' +
                                '                                    </div>' +
                                '                                </div>' +
                                '                                <div class="comment-wrap">' +
                                '                                    <p>' + commentList[i].commentContent + '</p>' +
                                '                                    <div>' +
                                '                                        <a class="fir-reply">' +
                                '                                            <span class="iconfont icon-Chat" style="padding: 5px;"></span>' +
                                '                                        </a>' +
                                '                                    </div>' +
                                '                                </div>' +
                                '                            </div>' +
                                '                            <div class="sub-comment-list hideText" style="display: none" >' +
                                '                                <div>' +
                                '                                   <textarea class="comment_text" style="display: none;" id=' + fir_textareaId + '></textarea>' +
                                '                                </div>' +
                                '                                <div class="write-function-block">' +
                                '                                    <a class="btn btn-send fir-reply-send">发送</a>' +
                                '                                </div>' +
                                '                            </div>' +
                                '                        </div>' +
                                '</div>'
                            );


                            if (replyCommentList !== null) {

                                for (var j = 0; j < replyCommentList.length; j++) {

                                    var sec_textareaId = 'sec_textarea_' + new Date().getTime() + j;


                                    var sec_reply_user_id = 'sec_reply_user_id_' + replyCommentList[j].blogUser.userId;


                                    $(document.getElementById(comment_id)).append(
                                        '                           <div class="sub-comment-list">' +
                                        '                                <div class="sub-comment">' +
                                        '                                    <p class=' + sec_reply_user_id + '>' +
                                        '                                        <div data-v-f3bf5228="" class="v-tooltip-container" style="z-index: 0;">' +
                                        '                                             <div class="v-tooltip-content">' +
                                        '                                                  <a href="#">' + replyCommentList[j].blogUser.nickname + '</a>' +
                                        '                                                  ：' +
                                        '                                             </div>' +
                                        '                                        </div>' +
                                        '                                        <span>' + '<a class="maleskine-author" href="#">@' + replyCommentList[j].replyBlogUser.nickname + '&nbsp;' + '</a>' + replyCommentList[j].replyContent + '</span>' +
                                        '                                    </p>' +
                                        '                                    <div class="sub-tool-group">' +
                                        '                                        <span>' + replyCommentList[j].createTimeStr + '</span>' +
                                        '                                        <a class="sec-reply">' +
                                        '                                            <span class="iconfont icon-Chat" style="padding: 5px;"></span>' +
                                        '                                        </a>' +
                                        '                                    </div>' +
                                        '                                </div>' +
                                        '                            </div>' +
                                        '                            <div class="sub-comment-list hideText" style="display: none" >' +
                                        '                                <div>' +
                                        '                                   <textarea class="comment_text" style="display: none;" id=' + sec_textareaId + '></textarea>' +
                                        '                                </div>' +
                                        '                                <div class="write-function-block">' +
                                        '                                    <a class="btn btn-send sec-reply-send">发送</a>' +
                                        '                                </div>' +
                                        '                            </div>'
                                    )
                                }
                            }


                            document.getElementById(avatar).src = comment_blog_user.figureUrl;


                        }

                    }
                });


                document.getElementById("userDetails").style.display = null;

            } else {
                localStorage.setItem("userDetails", null);
            }
            layer.close(loadingFlag);

        }


    });

    //给用户评论渲染
    layui.use('layedit', function () {
        var layedit = layui.layedit;

        var userinfo = JSON.parse(localStorage.getItem("userDetails"));

        var index = layedit.build('demo', {
            height: 120,
            tool: ['strong', 'italic', 'underline', 'del', '|', 'left', 'center', 'right', 'link', 'unlink', 'face']
        }); //建立编辑器

        //给发送绑定接口
        $(".send").click(function () {
            var comment_content = layedit.getContent(index);

            if(comment_content ===null || ""===comment_content) {
                layer.alert("评论不能为空",{icon:5});
                return;
            }

            var userId = JSON.parse(localStorage.getItem("userDetails")).userId;

            var comment = {
                "commentContent": comment_content,
                "userId": userId,
                "articleId": articleId
            };
            $.ajax({
                url: "/write/add_comment",
                contentType: "application/json",
                type: "post",
                async: false,
                data: JSON.stringify(comment),
                success: function (data) {

                    layer.close(loadingFlag);
                    var blogCommentVo = data.data;
                    //为每条评论设置id
                    var comment_id = "comment_id_" + blogCommentVo.commentId;
                    var reply_user_id = "reply_user_id_" + blogCommentVo.blogUser.userId;
                    var fir_textareaId = 'fir_textarea_' + new Date().getTime() + comment_id;


                    var i = $("#normal-comment-list").children().length + 1;
                    var avatar = "comment_avatar_" + i;


                    if (data.data !== null) {
                        layer.alert("评论成功", {icon: 6});
                        $("#normal-comment-list").append(
                            ' <div>' +
                            '                        <div class="comment" id=' + comment_id + '>' +
                            '                            <div class=' + reply_user_id + '>' +
                            '                                <div class="author">' +
                            '                                    <div data-v-f3bf5228="" class="v-tooltip-container" style="z-index: 0;">' +
                            '                                        <div class="v-tooltip-content">' +
                            '                                            <a href="#" target="_blank" class="avatar">' +
                            '                                                <img id=' + avatar + '  alt="头像" src="">' +
                            '                                            </a>' +
                            '                                        </div> <!---->' +
                            '                                    </div>' +
                            '                                    <div class="info">' +
                            '                                        <a href="#" target="_blank" class="name">' + blogCommentVo.blogUser.nickname + '</a>' +
                            '                                        <div class="meta">' +
                            '                                            <span>' + (i + 1) + '楼·' + blogCommentVo.createTimeStr + '</span>' +
                            '                                        </div>' +
                            '                                    </div>' +
                            '                                </div>' +
                            '                                <div class="comment-wrap">' +
                            '                                    <p>' + blogCommentVo.commentContent + '</p>' +
                            '                                    <div>' +
                            '                                        <a class="fir-reply">' +
                            '                                            <span class="iconfont icon-Chat" style="padding: 5px;"></span>' +
                            '                                        </a>' +
                            '                                    </div>' +
                            '                                </div>' +
                            '                            </div>' +
                            '                            <div class="sub-comment-list hideText" style="display: none" >' +
                            '                                <div>' +
                            '                                   <textarea class="comment_text" style="display: none;" id=' + fir_textareaId + '></textarea>' +
                            '                                </div>' +
                            '                                <div class="write-function-block">' +
                            '                                    <a class="btn btn-send fir-reply-send">发送</a>' +
                            '                                </div>' +
                            '                            </div>' +
                            '                        </div>' +
                            '</div>'
                        );
                        document.getElementById(avatar).src = blogCommentVo.blogUser.figureUrl;
                    } else {
                        layer.alert("评论失败", {icon: 5});
                    }

                }
            })

        })
    });


    //给一级回复绑定
    $("body").on("click", ".fir-reply", function (e) {

        //出发回复下边的富文本的id
        var textId = $($(e.target).parent().parent().parent().parent().next().children()[0]).children()[0].id;


        //当前用户信息
        var userinfo = JSON.parse(localStorage.getItem("userDetails"));


        //主评论id;
        var comment_id = $(document.getElementById(textId)).parent().parent().parent()[0].id.substring(11);


        //当前用户id
        var userId = userinfo.userId;


        //被回复者id
        var reply_user_id = $(document.getElementById("comment_id_" + comment_id)).children()[0].className.substring(14);


        //当前回复下的富文本隐藏状态
        var status = $(e.target).parent().parent().parent().parent().next().get(0).style.display;


        if (status === 'none') {
            layui.use('layedit', function () {
                var layedit = layui.layedit;
                var index = layedit.build(textId, {
                    height: 120,
                    tool: ['strong', 'italic', 'underline', 'del', '|', 'left', 'center', 'right', 'link', 'unlink', 'face']
                });

                $(e.target).parent().parent().parent().parent().next().get(0).style.display = null;

                //给一级回复绑定接口
                $("body").on("click", ".fir-reply-send", function (e) {

                    var reply_content = layedit.getContent(index);

                    if(reply_content ===null || ""===reply_content) {
                        layer.alert("评论不能为空",{icon:5});
                        return;
                    }
                    var reply = {
                        "replyContent": reply_content,
                        "userId": userId,
                        "replyUserId": reply_user_id,
                        "commentId": comment_id
                    };

                    $.ajax({
                        url: "/write/add_reply_comment",
                        contentType: "application/json",
                        type: "post",
                        async: false,
                        data: JSON.stringify(reply),
                        success: function (data) {
                            if (data.data) {
                                layer.close(loadingFlag);
                                layer.alert("回复成功", {icon: 6});

                                var replyCommentVo = data.data;

                                var sec_textareaId = 'sec_textarea_' + new Date().getTime();


                                var sec_reply_user_id = 'sec_reply_user_id_' + replyCommentVo.blogUser.userId;

                                $(document.getElementById($(document.getElementById(textId)).parent().parent().parent()[0].id)).append(
                                    '                           <div class="sub-comment-list">' +
                                    '                                <div id="comment-40151796" class="sub-comment">' +
                                    '                                    <p class=' + sec_reply_user_id + '>' +
                                    '                                        <div data-v-f3bf5228="" class="v-tooltip-container" style="z-index: 0;">' +
                                    '                                             <div class="v-tooltip-content">' +
                                    '                                                  <a href="#">' + replyCommentVo.blogUser.nickname + '</a>' +
                                    '                                                  ：' +
                                    '                                             </div>' +
                                    '                                        </div>' +
                                    '                                        <span>' + '<a class="maleskine-author" href="#">@' + replyCommentVo.replyBlogUser.nickname + '&nbsp;' + '</a>' + replyCommentVo.replyContent + '</span>' +
                                    '                                    </p>' +
                                    '                                    <div class="sub-tool-group">' +
                                    '                                        <span>' + replyCommentVo.createTimeStr + '</span>' +
                                    '                                        <a class="sec-reply">' +
                                    '                                            <span class="iconfont icon-Chat" style="padding: 5px;"></span>' +
                                    '                                        </a>' +
                                    '                                    </div>' +
                                    '                                </div>' +
                                    '                            </div>' +
                                    '                            <div class="sub-comment-list hideText" style="display: none" >' +
                                    '                                <div>' +
                                    '                                   <textarea class="comment_text" style="display: none;" id=' + sec_textareaId + '></textarea>' +
                                    '                                </div>' +
                                    '                                <div class="write-function-block">' +
                                    '                                    <a class="btn btn-send sec-reply-send">发送</a>' +
                                    '                                </div>' +
                                    '                            </div>'
                                )
                            } else {
                                layer.alert("回复失败", {icon: 5});
                            }
                        }
                    })
                })

            });
        } else {
            $(e.target).parent().parent().parent().parent().next()[0].style.display = 'none';
        }
    });


    $("body").on("click", ".sec-reply", function (e) {

        //出发回复下边的富文本的id
        var textId = $($(e.target).parent().parent().parent().parent().next().children()[0]).children()[0].id;

        //当前用户信息
        var userinfo = JSON.parse(localStorage.getItem("userDetails"));


        //主评论id;
        var comment_id = $(document.getElementById(textId)).parent().parent().parent()[0].id.substring(11);

        //当前用户id
        var userId = userinfo.userId;


        //被回复者id
        var reply_user_id = $(e.target).parent().parent().parent().children()[0].className.substring(18);


        //当前回复下的富文本隐藏状态
        var status = $(e.target).parent().parent().parent().parent().next().get(0).style.display;


        if (status === 'none') {
            layui.use('layedit', function () {
                var layedit = layui.layedit;
                var index = layedit.build(textId, {
                    height: 120,
                    tool: ['strong', 'italic', 'underline', 'del', '|', 'left', 'center', 'right', 'link', 'unlink', 'face']
                });
                $(e.target).parent().parent().parent().parent().next().get(0).style.display = null;

                //给一级回复绑定接口
                $("body").on("click", ".sec-reply-send", function (e) {

                    var reply_content = layedit.getContent(index);

                    var reply = {
                        "replyContent": reply_content,
                        "userId": userId,
                        "replyUserId": reply_user_id,
                        "commentId": comment_id
                    };

                    $.ajax({
                        url: "/write/add_reply_comment",
                        contentType: "application/json",
                        type: "post",
                        async: false,
                        data: JSON.stringify(reply),
                        success: function (data) {
                            if (data.data) {
                                layer.close(loadingFlag);
                                layer.alert("回复成功", {icon: 6});
                                var replyCommentVo = data.data;

                                var sec_textareaId = 'sec_textarea_' + new Date().getTime();


                                var sec_reply_user_id = 'sec_reply_user_id_' + replyCommentVo.blogUser.userId;

                                $(document.getElementById($(document.getElementById(textId)).parent().parent().parent()[0].id)).append(
                                    '                           <div class="sub-comment-list">' +
                                    '                                <div id="comment-40151796" class="sub-comment">' +
                                    '                                    <p class=' + sec_reply_user_id + '>' +
                                    '                                        <div data-v-f3bf5228="" class="v-tooltip-container" style="z-index: 0;">' +
                                    '                                             <div class="v-tooltip-content">' +
                                    '                                                  <a href="#">' + replyCommentVo.blogUser.nickname + '</a>' +
                                    '                                                  ：' +
                                    '                                             </div>' +
                                    '                                        </div>' +
                                    '                                        <span>' + '<a class="maleskine-author" href="#">@' + replyCommentVo.replyBlogUser.nickname + '&nbsp;' + '</a>' + replyCommentVo.replyContent + '</span>' +
                                    '                                    </p>' +
                                    '                                    <div class="sub-tool-group">' +
                                    '                                        <span>' + replyCommentVo.createTimeStr + '</span>' +
                                    '                                        <a class="sec-reply">' +
                                    '                                            <span class="iconfont icon-Chat" style="padding: 5px;"></span>' +
                                    '                                        </a>' +
                                    '                                    </div>' +
                                    '                                </div>' +
                                    '                            </div>' +
                                    '                            <div class="sub-comment-list hideText" style="display: none" >' +
                                    '                                <div>' +
                                    '                                   <textarea class="comment_text" style="display: none;" id=' + sec_textareaId + '></textarea>' +
                                    '                                </div>' +
                                    '                                <div class="write-function-block">' +
                                    '                                    <a class="btn btn-send sec-reply-send">发送</a>' +
                                    '                                </div>' +
                                    '                            </div>'
                                )
                            } else {
                                layer.alert("回复失败", {icon: 5});
                            }
                        }
                    })
                })

            });

        } else {
            $(e.target).parent().parent().parent().parent().next()[0].style.display = 'none';
        }
    });


    $.ajax({
        url: "/write/get_article_like_keyword",
        type: "post",
        data: {"keyWord": articleVo.keyWord, "articleId": articleId},
        success: function (data) {
            var articleObj = $("#note-list");

            data = data.data;

            if (data !== null) {
                for (var i = 0; i < data.length; i++) {

                    var noteId = "article_" + data[i].articleId;

                    articleObj.append(
                        '        <div class="note" id=' + noteId + '>' +
                        '            <a class="title" href="#">' + data[i].title + '</a>' +
                        '            <p class="description">' + data[i].markdownContent + '</p>' +
                        '            <a class="author" href="#">' +
                        '                <div class="avatar">' +
                        '                    <img src="" alt="头像">' +
                        '                </div>' +
                        '                <span class="name">' + data[i].blogUser.nickname + '</span>' +
                        '            </a>' +
                        '        </div>');
                    $($($(document.getElementById(noteId)).children()[2]).children()[0]).children()[0].src = data[i].blogUser.figureUrl;


                    $(document.getElementById(noteId)).children()[0].href="https://ailuoli.cn/article_details?articleId="+data[i].articleId;

                }
            }


            layer.close(loadingFlag);
        }
    });


});

$(function () {
    $(".dropdown").mouseover(function () {
        $(this).addClass("open");
    });

    $(".dropdown").mouseleave(function () {
        $(this).removeClass("open");
    })
});