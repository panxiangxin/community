function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    comment2Target(questionId,commentContent,1);
}
function comment2Target(targetId, content, type) {
    if(!content){
        alert("回复内容不能为空~~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "type": type,
            "content": content
        }),
        success: function (response) {
            if(response.code === 200){
                window.location.reload();
            }else {
                if(response.code === 2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=793dded55d424653dc07&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    })
}
function comment(e) {
        var id = e.getAttribute("data-id");
        console.log(id);
        var content = $("#input-"+id).val();
        console.log(content);
        comment2Target(id,content,2);
}
/**
 * 展开二级评论
 **/
function collapseComments(e) {

    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else{
        var subCommentContainer = $("#comment-"+ id);

        if(subCommentContainer.children().length !== 1){
            //展开二级评论
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }else {
            $.getJSON( "/comment/" + id, function(data) {

                $.each( data.data.reverse(), function(index,comment) {
                    var mediaLeftElement = $("<div/>",{
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu",
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>",{
                        "class": "media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                            "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                        }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }

    }
}