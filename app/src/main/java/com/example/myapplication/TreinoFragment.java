package com.example.myapplication;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TreinoFragment extends Fragment {

    private TextView tvTreinoTitle;
    private TextView nomeTreino;
    private int treinoAtual = 0; // Inicia com "TREINO A"
    private final String[] treinos = {"TREINO A", "TREINO B", "TREINO C", "CARDIO + ABS"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_treino, container, false);

        tvTreinoTitle = view.findViewById(R.id.tituloTreino);
        nomeTreino = view.findViewById(R.id.nomeTreino); // Atualizando TextView para o nome do treino
        RecyclerView recyclerView = view.findViewById(R.id.rvExercicios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configurar dados de treino inicial
        atualizarTreino(treinoAtual, recyclerView);

        // Botões de navegação
        view.findViewById(R.id.setaEsquerda).setOnClickListener(v -> {
            treinoAtual = (treinoAtual - 1 + treinos.length) % treinos.length;
            atualizarTreino(treinoAtual, recyclerView);
        });

        view.findViewById(R.id.setaDireita).setOnClickListener(v -> {
            treinoAtual = (treinoAtual + 1) % treinos.length;
            atualizarTreino(treinoAtual, recyclerView);
        });

        return view;
    }

    private void atualizarTreino(int indiceTreino, RecyclerView recyclerView) {
        // Atualiza o título do treino na tela
        tvTreinoTitle.setText("INICIANTE\n" + "treino ABC");
        nomeTreino.setText(treinos[indiceTreino]); // Atualiza o nome do treino

        // Dados fictícios para os treinos
        ArrayList<Exercise> exercises = new ArrayList<>();
        switch (treinos[indiceTreino]) {
            case "TREINO A":
                exercises.add(new Exercise("SUPINO RETO ARTICULADO", "4  12"));
                exercises.add(new Exercise("SUPINO INCLINADO MAQUINA", "4  12"));
                exercises.add(new Exercise("SUPINO DECLINADO MAQUINA", "4  12"));
                exercises.add(new Exercise("TRICPES FRANCES MAQUINA", "4  12"));
                exercises.add(new Exercise("SUPINO NO CROSS CORDA", "4  10"));
                exercises.add(new Exercise("SUPINO PARALELAS MAQUINA", "4  10"));
                exercises.add(new Exercise("ELEVAÇÃO LATERAL", "4  10"));
                exercises.add(new Exercise("ELEVAÇÃO FRONTAL SUPINADA", "4  10"));
                break;

            case "TREINO B":
                exercises.add(new Exercise("AGACHAMENTO SMITH", "4  12"));
                exercises.add(new Exercise("AGACHAMENTO HACK 45", "4  12"));
                exercises.add(new Exercise("CADEIRA EXTENSORA", "4  12"));
                exercises.add(new Exercise("CADEIRA EXTENSORA UNILATERAL", "4  12"));
                exercises.add(new Exercise("MESA FLEXORA", "4  12"));
                exercises.add(new Exercise("AGACHAMENTO SUMO", "4  12"));
                exercises.add(new Exercise("CADEIRA ADUTORA", "4  12"));
                exercises.add(new Exercise("CADEIRA ABDUTORA", "4  12"));
                exercises.add(new Exercise("PANTURRILHAS", "4  10"));
                break;

            case "TREINO C":
                exercises.add(new Exercise("PULLEY ANTERIOR", "4  12"));
                exercises.add(new Exercise("REMADA ANTERIOR TRIANGULO", "4  12"));
                exercises.add(new Exercise("REMADA MAQUINA ABERTA", "4  12"));
                exercises.add(new Exercise("CRUCIFIXO INVERTIDO", "4  12"));
                exercises.add(new Exercise("REMADA CURVADA COM BARRA", "4  12"));
                exercises.add(new Exercise("REMADA CAVALINHO MAQUINA", "4  12"));
                exercises.add(new Exercise("ROSCA MARTELO COM HALTERES", "4  12"));
                exercises.add(new Exercise("ROSCA ALTERNADA COM HALTERES", "4  12"));
                break;

            case "CARDIO + ABS":
                exercises.add(new Exercise("BICICLETA ERGOMETRICA", "15 MIN"));
                exercises.add(new Exercise("REMADOR HIDRAULICO", "10 MIN"));
                exercises.add(new Exercise("ESTEIRA ERGOMETRICA", "20 MIN"));
                exercises.add(new Exercise("ABDOMEN SUPRA ALTO", "4 12"));
                exercises.add(new Exercise("ABDOMEM INFRA SOLO", "4 12"));
                exercises.add(new Exercise("ABDOMEM REMADOR", "4 12"));
                exercises.add(new Exercise("LOMBAR MAQUINA", "4 12"));
                break;
        }

        // Atualizar RecyclerView
        ExerciseAdapter adapter = new ExerciseAdapter(exercises);
        recyclerView.setAdapter(adapter);
    }
}
