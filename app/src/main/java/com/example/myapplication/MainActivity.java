package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private TextView menuTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando o TextView do título
        menuTitle = findViewById(R.id.menuTitle);

        // Exibe o fragmento inicial (Treino)
        setFragment(new TreinoFragment(), "Meu Treino");

        // Configurando os botões
        findViewById(R.id.btnMeuTreino).setOnClickListener(v -> setFragment(new TreinoFragment(), "Meu Treino"));
        findViewById(R.id.btnMeuProgresso).setOnClickListener(v -> setFragment(new ProgressoFragment(), "Meu Progresso"));
        findViewById(R.id.btnMeuPerfil).setOnClickListener(v -> setFragment(new PerfilFragment(), "Meu Perfil"));
    }

    /**
     * Substitui o fragmento atual e atualiza o título do menu.
     *
     * @param fragment O novo fragmento a ser exibido.
     * @param title    O título a ser exibido no topo.
     */
    private void setFragment(Fragment fragment, String title) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        // Atualiza o título do menu
        menuTitle.setText(title);
    }
}
