package aed3;

import java.util.ArrayList;
import java.util.HashMap;

public class IndiceInvertido {
    private ListaInvertida listaInvertida;
    private HashMap<String, Integer> mapeamentoTermos; // Mapeia termos para IDs na ListaInvertida
    private int totalTarefas;

    public IndiceInvertido(int quantidadeMaximaTermos, String nomeArquivoLista) throws Exception {
        listaInvertida = new ListaInvertida(quantidadeMaximaTermos, nomeArquivoLista);
        mapeamentoTermos = new HashMap<>();
        totalTarefas = 0;
    }

    public void incrementarTarefas() {
        totalTarefas++;
    }

    public void addTerm(String termo, int idTarefa, int frequencia) throws Exception {
        int idTermo;
        if (!mapeamentoTermos.containsKey(termo)) {
            idTermo = listaInvertida.criarLista();
            mapeamentoTermos.put(termo, idTermo);
        } else {
            idTermo = mapeamentoTermos.get(termo);
        }
        listaInvertida.inserir(idTermo, new ElementoLista(idTarefa, frequencia));
    }

    public double calculateIDF(String termo) throws Exception {
        if (!mapeamentoTermos.containsKey(termo)) {
            return 0.0;
        }
        int idTermo = mapeamentoTermos.get(termo);
        int df = listaInvertida.ler(idTermo).size(); // Número de documentos contendo o termo
        return Math.log((double) totalTarefas / (df + 1)); // Adiciona 1 para evitar divisão por zero
    }

    public ArrayList<ElementoLista> search(String termo) throws Exception {
        if (!mapeamentoTermos.containsKey(termo)) {
            return new ArrayList<>(); // Termo não encontrado
        }

        int idTermo = mapeamentoTermos.get(termo);
        ArrayList<ElementoLista> elementos = listaInvertida.ler(idTermo);
        double idf = calculateIDF(termo);

        // Calcula TFxIDF
        for (ElementoLista elemento : elementos) {
            float tf = elemento.getFrequencia();
            elemento.setFrequencia((float) (tf * idf));
        }

        // Ordena os elementos pela relevância (TFxIDF)
        elementos.sort((a, b) -> Float.compare(b.getFrequencia(), a.getFrequencia()));

        return elementos;
    }
}
