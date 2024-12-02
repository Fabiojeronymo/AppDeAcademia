package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProgressoFragment extends Fragment {

    private CheckBox checkCompareceu;
    private EditText editEvolucaoCarga;
    private EditText editExercicio;
    private ListView listDiasComparecimento;
    private ListView listCargas;
    private List<String> diasComparecimento;
    private List<String> cargas;
    private ArrayAdapter<String> diasAdapter;
    private ArrayAdapter<String> cargasAdapter;

    private SharedPreferences sharedPreferences;

    // Método correto para sobrescrever onCreateView do Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla o layout do fragmento
        View rootView = inflater.inflate(R.layout.fragment_progresso, container, false);

        checkCompareceu = rootView.findViewById(R.id.checkCompareceu);
        editEvolucaoCarga = rootView.findViewById(R.id.editEvolucaoCarga);
        editExercicio = rootView.findViewById(R.id.editExercicio);  // Novo EditText para o exercício
        listDiasComparecimento = rootView.findViewById(R.id.listDiasComparecimento);
        listCargas = rootView.findViewById(R.id.listCargas);


        // Inicializa as listas de dias e cargas
        diasComparecimento = new ArrayList<>();
        cargas = new ArrayList<>();

        // Configura os adaptadores para as listas
        diasAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, diasComparecimento);
        cargasAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, cargas);

        listDiasComparecimento.setAdapter(diasAdapter);
        listCargas.setAdapter(cargasAdapter);

        // Carrega as preferências
        sharedPreferences = getActivity().getSharedPreferences("Progresso", Context.MODE_PRIVATE);
        carregarProgresso();

        // Configura o botão para salvar o progresso
        Button btnSalvarProgresso = rootView.findViewById(R.id.btnSalvarProgresso);
        btnSalvarProgresso.setOnClickListener(v -> salvarProgresso());

        return rootView;
    }

    private void salvarProgresso() {
        // Se o usuário marcou como compareceu, salva o dia de comparecimento
        if (checkCompareceu.isChecked()) {
            String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            adicionarDiaComparecimento(dataAtual);
        }

        // Salva a evolução de carga e o exercício
        String evolucaoCarga = editEvolucaoCarga.getText().toString();
        String exercicio = editExercicio.getText().toString();

        if (!evolucaoCarga.isEmpty() && !exercicio.isEmpty()) {
            adicionarCarga(exercicio, evolucaoCarga);
        } else {
            Toast.makeText(getActivity(), "Por favor, insira o exercício e a evolução de carga.", Toast.LENGTH_SHORT).show();
        }
    }

    private void adicionarDiaComparecimento(String dia) {
        if (!diasComparecimento.contains(dia)) {
            // Adiciona o dia de comparecimento no início da lista para exibir os mais recentes primeiro
            diasComparecimento.add(0, dia); // Adiciona no início para exibir os mais recentes primeiro
            diasAdapter.notifyDataSetChanged();


            // Salva os dias nas preferências
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> diasSet = new HashSet<>(diasComparecimento);
            editor.putStringSet("diasComparecimento", diasSet);
            editor.apply();
        }
    }

    private void adicionarCarga(String exercicio, String carga) {
        // Cria uma string composta "Exercício - Carga (Data)"
        String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String cargaComExercicio = exercicio + " - " + carga + " (" + dataAtual + ")";

        // Adiciona a carga com exercício no início da lista para exibir as mais recentes primeiro
        cargas.add(0, cargaComExercicio);
        cargasAdapter.notifyDataSetChanged();

        // Salva as cargas nas preferências
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> cargasSet = new HashSet<>(cargas);
        editor.putStringSet("cargas", cargasSet);
        editor.apply();
    }

    private void carregarProgresso() {
        // Carrega os dias e as cargas das preferências
        if (sharedPreferences.contains("diasComparecimento")) {
            diasComparecimento.clear();
            Set<String> diasSalvos = sharedPreferences.getStringSet("diasComparecimento", new HashSet<>());
            diasComparecimento.addAll(diasSalvos);
            diasAdapter.notifyDataSetChanged();
        }

        if (sharedPreferences.contains("cargas")) {
            cargas.clear();
            Set<String> cargasSalvas = sharedPreferences.getStringSet("cargas", new HashSet<>());
            cargas.addAll(cargasSalvas);
            cargasAdapter.notifyDataSetChanged();
        }


    }

}
