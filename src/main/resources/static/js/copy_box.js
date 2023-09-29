function copy_to_clipboard(event) {
    var copyText = event.target.parentNode.previousElementSibling;
    copyText.select();
    copyText.setSelectionRange(0, 99999);
    document.execCommand("Copy");
    alert('초대 링크가 복사되었습니다.');
}