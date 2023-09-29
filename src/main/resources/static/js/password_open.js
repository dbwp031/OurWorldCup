$(document).ready(function(){
    $('.password-open i').on('click',function(){
        $('input').toggleClass('active');
        var inputElemnet = $("#pwOpenCloseTarget");
        if($('input').hasClass('active')){
            $(this).attr('class',"fa-solid fa-eye-slash");
            inputElemnet.attr("type", "text");
            console.log("password -> text");
        }else{
            $(this).attr('class',"fa-solid fa-eye");
            inputElemnet.attr("type", "password");
            console.log("text -> password");
        }
    });
})