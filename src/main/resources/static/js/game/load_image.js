function setCookie(name, value, days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/";
}

function getCookie(name) {
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length == 2) {
        return parts.pop().split(";").shift();
    }
    return null;
}
function deleteCookie(name) {
    document.cookie = name + '=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function getRound(gameId, roundNum) {
    $.ajax({
        url: '/api/v1/game/'+gameId+'/round/'+roundNum,
        method: 'GET',
        success: function (data) {
            console.log(this.url);
            // console.log(data);
            // setCookie("roundInfo", JSON.stringify(data), 1);
            console.log(data);
            setCookie("worldcupId", String(data.worldcupId), 1);
            setCookie("gameId", String(data.gameId),1);
            setCookie("roundNum", String(data.roundNum),1);
            setCookie("item1Id", String(data.item1.id),1);
            setCookie("item2Id", String(data.item2.id),1);
            setCookie("roundId", String(data.id), 1);
            $("#item1Title").text(data.item1.title);
            $("#item2Title").text(data.item2.title);
            $("#item1Image").attr('src', data.item1.base64ItemImage);
            $("#item2Image").attr('src', data.item2.base64ItemImage);
            $("#gameTitle").text(data.game.title);
            roundContext = `[${data.game.currentRoundOrder}/${data.game.stageTotalRound}] ${data.game.currentRoundTypeTitle}`;
            $("#roundContext").text(roundContext);
        },
    })
}
//selectedItem -> 1: item1 / 2: item2;
function postRoundResult(selectedItem) {
    // var roundInfo    = getCookie("roundInfo"); // roundInfo 변수 선언 추가
    // var gameDataObject = JSON.parse(roundInfo);
    // var gameId = gameDataObject.gameId;
    // var roundNum = gameDataObject.roundNum;
    var gameId = getCookie("gameId");
    var roundNum = getCookie("roundNum");
    var item1Id = getCookie("item1Id");
    var item2Id = getCookie("item2Id");
    var worldcupId = getCookie("worldcupId");
    var roundId = Number(getCookie("roundId"));
    var selectedItemNum = selectedItem == 1 ? item1Id : item2Id;
    $.ajax({
        url: '/api/v1/game/' + gameId + `/round/` + roundNum,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            "roundId": roundId,
            "roundNum": roundNum,
            "selectedItem": selectedItemNum
        }),
        success: function (data) {
            console.log(data);
            if (data.isFinished) {
                redirectionPath = `${window.location.protocol}//${window.location.host}/game/${gameId}/result`;
                window.location.replace(redirectionPath);
            }
            return data.isFinished;
        },
        error: function (error) {
            console.log("Error: ", error);
        },
    })

}

function initializeGame() {
    var path = window.location.pathname;
    var segments = path.split('/');
    var gameId = segments[segments.length - 1];
    return gameId;
}

$('#item1Image').click(function () {
    var gameId = getCookie("gameId");
    var newRoundNum = Number(getCookie("roundNum")) + 1;
    if (!postRoundResult(1)) {
        getRound(gameId,newRoundNum);
    }
});

$('#item2Image').click(function () {
    var gameId = getCookie("gameId");
    var newRoundNum = Number(getCookie("roundNum")) + 1;
    if (!postRoundResult(2)) {
        getRound(gameId,newRoundNum);
    }
});
$(document).ready(function () {
    // console.log(initializeGame());
    getRound(initializeGame(), 0);
});