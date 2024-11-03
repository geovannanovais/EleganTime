const formulario = document.getElementById('loginForm');
const iEmail = document.getElementById('email');
const iSenha = document.getElementById('senha');

formulario.addEventListener('submit', function (event) {
    event.preventDefault();

    fetch('http://localhost:8080/usuarios/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: iEmail.value,
            senha: iSenha.value
        })
    })
        .then(response => {
            if (response.ok) {
                // Redireciona para a página de destino em caso de login bem-sucedido
                window.location.href = 'areaAdmin.html';
            } else {
                // Mostra uma mensagem de erro se o login falhar
                alert("Login ou senha incorretos.");
            }
        })
        .catch(error => {
            console.error("Erro ao fazer login:", error);
        });
});
