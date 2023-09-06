function checkPassword() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const passwordErrorNotMatch = document.getElementById('passwordErrorNotMatch');
    const passwordErrorNoInput = document.getElementById('passwordErrorNoInput');
    if (password === "" || confirmPassword === "") {
        passwordErrorNoInput.style.display = 'block';
    } else {
        passwordErrorNoInput.style.display = 'none';
    }

    if (password !== confirmPassword) {
        passwordErrorNotMatch.style.display = 'block'; // 표시
    } else {
        passwordErrorNotMatch.style.display = 'none';  // 숨기기
    }
}
