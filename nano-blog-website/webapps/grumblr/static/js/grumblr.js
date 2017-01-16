/**
 * Created by xiaod on 2016/10/18.
 */

// add new post
$('#post-form').on('submit', function(event) {
    event.preventDefault(); // Prevent form from being submitted
    // TODO:  Use jQuery to send an Ajax POST request to add-blogs.
    var formField = $("#post-form").serializeArray();
    $.post("/add-blogs", formField)
        .done(function (data) {
            updateChanges(data);
            // clean input box
            $("#new-input").val("");
            // update blog-num on the website
            var num= parseInt($('#blog-num').html()) + 1;
            $('#blog-num').html( num);
        });
});


// function to update new comments
function updateComments(data) {
    // if error exist, return
    $('#comment-error' + data.blogid).text(data.message);
    if(data.message != '') return;
    // add new comments to website
    if($("#panel-body" + data.blogid).length<=0){
        // create a new dic to display comments
        var comlist = $("<div>");
        comlist.addClass("panel-body");
        comlist.attr({
        "id" : "panel-body" + data.blogid
    });
    }else{
        var comlist = $("#panel-body" + data.blogid);
        // update comment-num on the website
        var num= parseInt($('#comment-num' + data.blogid).html()) + 1;
        $('#comment-num' + data.blogid).html(' ' + num);
    }
  // Process comments
  for(var i = 0; i < data.comments.length; i++) {
    comlist.append(data.comments[i].html);
  }
  $("#send_comment" + data.blogid).append(comlist);


}

// update posts
function updateChanges(data) {
  // Display new messages
   $('#message').text(data.message);

  // Process posts
  for(var i = 0; i < data.blogs.length; i++) {
    $('#blogs-list').prepend(data.blogs[i].html);
      // add click listener to the new post
    $('#' + data.blogs[i].blogid).click(show_comment);
  }

  // Update timestamp
  $('#timestamp').val(data.timestamp);
}

// show comment-sender form and all existed comments
function show_comment(){
    var cid = $(this).attr("id");
    var div1 =  $("<div>");
    div1.attr({
        "id" : "send_comment" + cid,
        "class" :  "panel panel-info comment-div"
    });
    var div = $("<div>");
    div.attr({
        "class" : "panel-heading"
    })
    div.addClass("send_comment")
    var img = $("<img>");
    img.attr({
        "alt" : "samll image",
        "class" : "smaller-header",
        "src" : "/head-image/" + $(".blog-footer").attr("id")
    });
    var form = $("<form>");
    form.attr({
        "id": "com-form" + cid,
        "method": "post",
        "class" : "comment-form"
    });
    var input = $("<input>");
    input.attr({
        "id" : "comment-input",
        "type" : "text",
        "name" : "text",
        "class" : "comment-input",
        "placeholder" : "how do you think..."

    });
    var hid = $("<input>");
    hid.attr({
        "type" : "hidden",
        "value" : cid,
        "name" : "blog-id"
    });
    var btn = $("<input>");
    btn.attr({
        "type": "submit",
        "class" : "btn btn-sm btn-info comment-btn",
        "value" : "submit"
    });
    var error = $("<p>");
    error.attr({
        "id" : "comment-error" + cid,
        "class": "comment-error"
    })
    btn.html("send comment")
    input.appendTo(form);
    hid.appendTo(form);
    btn.appendTo(form);
    img.appendTo(div);
    form.appendTo(div);
    error.appendTo(div);
    div.appendTo(div1);
    $("#table-comment" + cid).append(div1);
    // set listener for new form
    form.on('submit', function(event) {
    event.preventDefault(); // Prevent form from being submitted
    var formField = $(this).serializeArray();
    $.post("/add-comment", formField)
        .done(function (data) {
            updateComments(data);
            $("#comment-input").val("");
        });
    });
    // get all comments
     $.get("/get-comments/" + cid)
            .done(function (data) {
            updateComments(data);
        });

    // change listener whenever it has been clicked. so the next time user click on it
    // will make comments hiden'
     $(this).unbind("click");
     $(this).click(function (event) {
        event.preventDefault();
         $("#send_comment" + cid).remove();
         $(this).unbind("click");
         $(this).click(show_comment);
    });
};

$(document).ready(function () {
  // Add event-handlers
    $('.comment-click').click(show_comment);

  // Periodically refresh post list
  window.setInterval(function(){
       $.get("/refresh-blogs/" + $('#timestamp').val())
            .done(function (data) {
            updateChanges(data);
        });
  }, 5000);

  // CSRF set-up copied from Django docs
  function getCookie(name) {
    var cookieValue = null;
    if (document.cookie && document.cookie != '') {
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var cookie = jQuery.trim(cookies[i]);
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) == (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
  }
  var csrftoken = getCookie('csrftoken');
  $.ajaxSetup({
    beforeSend: function(xhr, settings) {
        xhr.setRequestHeader("X-CSRFToken", csrftoken);
    }
  });
});