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
        var content = $("#input"+id).val();
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
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else{
        comments.addClass("in");
        e.setAttribute("data-collapse", "in");
        e.classList.add("active");

    }
}