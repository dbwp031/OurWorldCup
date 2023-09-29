function displayFiles() {
    const fileList = document.getElementById('fileList');
    const files = document.getElementById('fileInput').files;

    // 기존 목록을 지웁니다.
    fileList.innerHTML = '';

    for(let i = 0; i < files.length; i++) {
        const li = document.createElement('li');
        li.textContent = files[i].name;

        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = 'Delete';
        deleteBtn.onclick = function() {
            deleteFile(i);
        };

        li.appendChild(deleteBtn);
        fileList.appendChild(li);
    }
}

function deleteFile(index) {
    const fileList = document.getElementById('fileList');
    const files = document.getElementById('fileInput').files;

    // 파일 목록에서 해당 인덱스의 파일을 제거하는 것은 직접적으로는 불가능합니다.
    // 그러므로 사용자에게 파일 선택을 다시 요청하는 방법을 사용합니다.
    alert('Please reselect files excluding the one you want to delete.');

    // 파일 목록을 초기화합니다.
    fileList.innerHTML = '';
    document.getElementById('fileInput').value = '';
}