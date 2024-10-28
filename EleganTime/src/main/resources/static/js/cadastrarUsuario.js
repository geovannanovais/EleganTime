// Captura o formulário
const formulario = document.querySelector("form");

const iNome = document.querySelector("#nome");
const iCpf = document.querySelector("#cpf");
const iEmail = document.querySelector("#email");
const iGrupo = document.querySelector("#grupo");
const iSenha = document.querySelector("#senha");

// Metedo para enviar os dados em json que vai acessar a api
function cadastrar() {

    // Endpoint é o endereço de acesso a api
    fetch('http://localhost:8080/usuarios', {

        method: 'POST', // Método HTTP
        headers: {
            'Content-Type': 'application/json' // Informando que estamos enviando JSON
        },
        body: JSON.stringify({ // Convertendo o objeto dados para JSON
            nome: iNome.value,
            cpf: iCpf.value,
            email: iEmail.value,
            grupo: iGrupo.value,
            senha: iSenha.value
        })
    })
        .then(function (res) { console.log(res) })
        .catch(function (res) { console.log(res) })
};

function limpar() {
    iNome.value = "";
    iCpf.value = "";
    iEmail.value = "";
    iGrupo.value = "";
    iSenha.value = "";
}

formulario.addEventListener('submit', function (event) {
    event.preventDefault();

    cadastrar();
    limpar();

});