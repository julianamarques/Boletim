package com.app.boletim.utils;

import android.widget.EditText;

public class ValidacaoCadastroDisciplina {
    public static void validarCampoVazio(EditText nome, EditText professor) {
        if (nome.getText().toString().isEmpty() || professor.getText().toString().isEmpty()) {
            throw new IllegalArgumentException("O campo não pode estar vazio");
        }
    }
}
