$(document).ready(function () {
    let token = getUrlParameter("token");
    checkInvitation(token);

    $("#join-button").on('click', function () {
        requestJoin(token)
    });
});

function requestJoin(token) {
    $.ajax({
        url: "/api/v1/invi/join?token=" + token,
        type: "POST",
        dataType: "json",
        success: function (data, textStatus, xhr) {
            console.log("hi join");
            redirect("/worldcup/" + data.id + "/details");
        },
        error: function () {
            alert("만료되거나 올바르지 않은 초대 토큰입니다.");
        },
    })
}

function checkInvitation(token) {
    $.ajax({
        url: "/api/v1/invi?token=" + token,
        type: "GET",
        dataType: "json",
        success: function (data, textStatus, xhr) {
            renderPage(data);
            console.log("hihi");
        },
        error: function () {
            alert("만료되거나 올바르지 않은 초대 토큰입니다.");
        },
    })
}

function renderPage(data) {
    $("#worldcup-title").text(data.worldcup.title);
    $("#worldcup-content").text(data.worldcup.content);
}

export function getUrlParameter(sParam) {

    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;


    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

export function redirect(targetUrl) {
    window.location.href = getBaseUrl() + targetUrl;
}

function getBaseUrl() {
    return window.location.protocol + "//" + window.location.host;
}