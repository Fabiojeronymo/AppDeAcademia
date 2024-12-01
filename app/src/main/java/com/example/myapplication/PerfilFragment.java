package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import android.view.inputmethod.EditorInfo;

public class PerfilFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private ImageView imageViewPerfil;
    private TextView textViewAlterarFoto;
    private TextView textViewNome; // TextView abaixo da foto de perfil
    private EditText editTextNome; // EditText para editar o nome do usuário
    private EditText editTextIdade;
    private EditText editTextAltura;
    private EditText editTextPeso;
    private EditText editTextBf;

    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Inicializa os componentes
        imageViewPerfil = view.findViewById(R.id.imageViewPerfil);
        textViewAlterarFoto = view.findViewById(R.id.textViewAlterarPerfil);
        editTextNome = view.findViewById(R.id.editTextNome);
        editTextIdade = view.findViewById(R.id.editTextIdade);
        editTextAltura = view.findViewById(R.id.editTextAltura);
        editTextPeso = view.findViewById(R.id.editTextPeso);
        editTextBf = view.findViewById(R.id.editTextBf);
        textViewNome = view.findViewById(R.id.textViewNome);

        // Carrega os dados salvos
        loadUserData();

        // Clique no TextView "Alterar foto" para abrir a galeria
        textViewAlterarFoto.setOnClickListener(v -> openGallery());

        // Configura navegação entre os campos com Enter
        setupEditTextNavigation();

        // Configura o TextWatcher no campo Nome
        setupNomeTextWatcher();

        // Adiciona um botão para salvar os dados
        Button salvarButton = view.findViewById(R.id.salvarButton);
        salvarButton.setOnClickListener(v -> saveUserData());

        return view;
    }

    // Método para salvar os dados no SharedPreferences
    private void saveUserData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Salva os dados dos EditText
        editor.putString("nome", editTextNome.getText().toString());
        editor.putString("idade", editTextIdade.getText().toString());
        editor.putString("altura", editTextAltura.getText().toString());
        editor.putString("peso", editTextPeso.getText().toString());
        editor.putString("bf", editTextBf.getText().toString());

        // Salva o URI da imagem de perfil, se houver
        if (imageUri != null) {
            editor.putString("imageUri", imageUri.toString());
        }

        // Salva as alterações
        editor.apply();
    }

    // Método para carregar os dados salvos
    private void loadUserData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);

        // Carrega os dados dos campos EditText
        String nome = sharedPreferences.getString("nome", "");
        String idade = sharedPreferences.getString("idade", "");
        String altura = sharedPreferences.getString("altura", "");
        String peso = sharedPreferences.getString("peso", "");
        String bf = sharedPreferences.getString("bf", "");

        // Preenche os EditTexts com os dados recuperados
        editTextNome.setText(nome);
        editTextIdade.setText(idade);
        editTextAltura.setText(altura);
        editTextPeso.setText(peso);
        editTextBf.setText(bf);

        // Carrega a imagem de perfil, se houver
        String imageUriString = sharedPreferences.getString("imageUri", null);
        if (imageUriString != null && !imageUriString.isEmpty()) {
            try {
                imageUri = Uri.parse(imageUriString);
                imageViewPerfil.setImageURI(imageUri);
            } catch (Exception e) {
                e.printStackTrace(); // Loga erro caso a URI seja inválida
                imageViewPerfil.setImageResource(R.drawable.default_profile_picture); // Define imagem padrão em caso de erro
            }
        }
    }

    // Método para abrir a galeria de imagens
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    // Método chamado quando a imagem é selecionada
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == getActivity().RESULT_OK && data != null) {
            imageUri = data.getData();
            imageViewPerfil.setImageURI(imageUri); // Define a imagem escolhida no ImageView
        }
    }

    // Configura os campos para navegar entre eles com a tecla Enter
    private void setupEditTextNavigation() {
        editTextNome.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editTextNome.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                editTextIdade.requestFocus();
                return true;
            }
            return false;
        });

        editTextIdade.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editTextIdade.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                editTextAltura.requestFocus();
                return true;
            }
            return false;
        });

        editTextAltura.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editTextAltura.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                editTextPeso.requestFocus();
                return true;
            }
            return false;
        });

        editTextPeso.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editTextPeso.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                editTextBf.requestFocus();
                return true;
            }
            return false;
        });

        editTextBf.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextBf.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    // Método para configurar o TextWatcher no campo Nome
    private void setupNomeTextWatcher() {
        editTextNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                textViewNome.setText(charSequence); // Atualiza o nome abaixo da foto de perfil
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
}
