const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

$(document).ready(function() {
    $("#submit").click(
        function() {
            // да вообще по*уй
            const data = '{"username":"' + $('#username').val() +
                '","password":"' + $('#password').val() +
                '","confirmPassword":"' + $('#confirmPassword').val() + '"}'
            sendAjaxForm(data)
            return false;
        }
    );
});

function sendAjaxForm(data) {
    $.ajax({
        url:        "/registration",
        type:       "POST",
        contentType: "application/json",
        dataType:   "json",
        data: data,
        success: [
            function(response) {
                if (response.success) {
                    location.href = "/login"
                } else {
                    $('#registrationError').text(response.message)
                }
            }
        ],
        error: [
            function() {
                $('#registrationError').text("Ошибка. Данные не отправлены.")
            }
        ]
    });
}