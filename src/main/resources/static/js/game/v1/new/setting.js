$(document).ready(function () {
        const pathSegments = window.location.pathname.split('/');
        const worldcupId = pathSegments[2]; // '12345'
        console.log(worldcupId);
        getRoundTypes(worldcupId);

        let roundType = null;
        $("#roundTypesContainer").on("click", "button", function () {
            roundType = $(this).data("round-type");
            selectRoundTypes(worldcupId, roundType)
        });

    }
);

function selectRoundTypes(worldcupId, roundType) {

    $.ajax({
        url: `/api/v1/worldcup/${worldcupId}/game?initRound=${roundType}`,
        type: 'POST',
        contentType: `application/json`,
        success: function (data, textStatus, xhr) {
            console.log(data.gameId);
            redirect(`/game/${data.gameId}`); //TODO: 설정해줘야됨

        },
        error: function () {
            alert("selectRoundTypes Error");
        },

    })
}

function getRoundTypes(worldcupId) {
    $.ajax({
        url: `/api/v1/worldcup/${worldcupId}/roundTypes`,
        type: "GET",
        dataType: "json",
        success: function (data, textStatus, xhr) {
            renderPage(data, worldcupId);
            console.log("success");
        },
        error: function () {
            alert("getRoundTypes Error");
        },
    })
}

function renderPage(data, worldcupId) {
    let roundTypes = data.roundTypes;
    let container = $("#roundTypesContainer");

    $("#worldcup-title").text(data.title);
    roundTypes.forEach(round => {
        let btnDiv = $("<div></div>").addClass("text-center");
        let btn = $("<button></button>")
            .text(`${round}강`)
            .attr("data-round-type", round)
            .attr("id", `round-type-${round}`)
        container.append(btnDiv.append(btn));
    })
}

function redirect(targetUrl) {
    window.location.href = getBaseUrl() + targetUrl;
}

function getBaseUrl() {
    return window.location.protocol + "//" + window.location.host;
}