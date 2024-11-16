// Captura o formulário
const formulario = document.querySelector("form");

const iNome = document.querySelector("#nome");
const iCpf = document.querySelector("#cpf");
const iEmail = document.querySelector("#email");
const idataNascimento = document.querySelector("#dataNascimento");
const iGenero = document.querySelector("#genero");
const iCep = document.querySelector("#cep");
const iLogradouro = document.querySelector("#logradouro");
const iNumero = document.querySelector("#numero");
const iComplemento = document.querySelector("#complemento");
const iBairro = document.querySelector("#bairro");
const iCidade = document.querySelector("#cidade");
const iUf = document.querySelector("#uf");
const iEnderecoEntrega = document.querySelector("#enderecoEntrega");

// Método para consultar o CEP na API
function consultarCep() {
    const cep = iCep.value.replace(/\D/g, ''); // Remove caracteres não numéricos

    if (cep.length === 8) {
        fetch(`https://viacep.com.br/ws/${cep}/json/`)
            .then(response => response.json())
            .then(data => {
                if (!data.erro) {
                    iLogradouro.value = data.logradouro;
                    iBairro.value = data.bairro;
                    iCidade.value = data.localidade;
                    iUf.value = data.uf;
                } else {
                    alert("CEP não encontrado.");
                }
            })
            .catch(error => console.error('Erro ao consultar o CEP:', error));
    } else {
        alert("CEP inválido. Deve conter 8 dígitos.");
    }
}

// Método para enviar os dados em JSON que vai acessar a API
function cadastrar() {
    fetch('/cadastrarCliente', { // Ajuste para endpoint relativo
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            nome: iNome.value,
            cpf: iCpf.value,
            email: iEmail.value,
            dataNascimento: idataNascimento.value,
            genero: iGenero.value,
            cep: iCep.value,
            enderecoFaturamento: {
                cep: iCep.value,
                logradouro: iLogradouro.value,
                numero: iNumero.value,
                complemento: iComplemento.value,
                bairro: iBairro.value,
                cidade: iCidade.value,
                uf: iUf.value
            },
            enderecoEntrega: iEnderecoEntrega.value
        })
    })
        .then(res => {
            if (res.ok) {
                alert('Cadastro realizado com sucesso!');
                window.location.href = 'login.html';
            } else {
                alert('Erro ao cadastrar. Verifique os dados e tente novamente.');
            }
        })
        .catch(error => console.error('Erro ao cadastrar:', error));
}

// Função para limpar o formulário
function limpar() {
    iNome.value = "";
    iCpf.value = "";
    iEmail.value = "";
    idataNascimento.value = "";
    iGenero.value = "";
    iCep.value = "";
    iLogradouro.value = "";
    iNumero.value = "";
    iComplemento.value = "";
    iBairro.value = "";
    iCidade.value = "";
    iUf.value = "";
    iEnderecoEntrega.value = "";
}

// Adiciona um evento para o campo de CEP para consultar a API ao perder o foco
iCep.addEventListener('blur', consultarCep);

// Adiciona um evento para o envio do formulário
formulario.addEventListener('submit', function (event) {
    event.preventDefault();
    cadastrar();
});

document.addEventListener('DOMContentLoaded', function () {
    const cpfInput = document.getElementById('cpf');

    cpfInput.addEventListener('input', function (event) {
        let cpf = event.target.value.replace(/\D/g, ''); // Remove caracteres não numéricos

        if (cpf.length > 11) cpf = cpf.slice(0, 11); // Limita a 11 dígitos

        if (cpf.length > 9) {
            event.target.value = cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
        } else if (cpf.length > 6) {
            event.target.value = cpf.replace(/(\d{3})(\d{3})(\d{0,3})/, "$1.$2.$3");
        } else if (cpf.length > 3) {
            event.target.value = cpf.replace(/(\d{3})(\d{0,3})/, "$1.$2");
        } else {
            event.target.value = cpf;
        }
    });
});
