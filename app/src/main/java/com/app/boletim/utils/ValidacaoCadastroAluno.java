package com.app.boletim.utils;

import android.widget.EditText;

public class ValidacaoCadastroAluno {
    public static void validarCampoVazio(EditText nome, EditText email, EditText senha, EditText instituicao, EditText mediaInstitucional, EditText mediaPessoal) {
        if (nome.getText().toString().isEmpty() || senha.getText().toString().isEmpty() || instituicao.getText().toString().isEmpty() || mediaInstitucional.getText().toString().isEmpty() || mediaPessoal.getText().toString().isEmpty()) {
            throw new IllegalArgumentException("O campo não pode estar vazio");
        }
    }
}
