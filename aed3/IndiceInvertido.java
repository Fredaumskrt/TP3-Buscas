package aed3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IndiceInvertido {
    private ListaInvertida listaInvertida;
    private HashMap<String, Integer> mapeamentoTermos; 
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
        int df = listaInvertida.ler(idTermo).size(); 
        return Math.log((double) totalTarefas / (df + 1)); 
    }

    
    public ArrayList<ElementoLista> search(String termo) throws Exception {
        if (!mapeamentoTermos.containsKey(termo)) {
            return new ArrayList<>(); 
        }

        int idTermo = mapeamentoTermos.get(termo);
        ArrayList<ElementoLista> elementos = listaInvertida.ler(idTermo);
        double idf = calculateIDF(termo);

        
        for (ElementoLista elemento : elementos) {
            float tf = elemento.getFrequencia();
            elemento.setFrequencia((float) (tf * idf));  
        }

        
        elementos.sort((a, b) -> Float.compare(b.getFrequencia(), a.getFrequencia()));

        return elementos;
    }

    
    public void atualizarIndiceInvertido(Tarefa tarefa) throws Exception {
        
        String[] palavras = tarefa.getDescricao().split("\\s+");

        
        List<String> palavrasFiltradas = filtrarPalavras(palavras);

        
        for (String palavra : palavrasFiltradas) {
            float tf = calcularTF(palavra, palavrasFiltradas);
            addTerm(palavra, tarefa.getId(), (int) tf);  
        }

        
        incrementarTarefas();
    }

    
    private List<String> filtrarPalavras(String[] palavras) {
        
        return Arrays.stream(palavras)
                     .map(p -> p.toLowerCase()) 
                     .filter(p -> !stopWords.contains(p)) 
                     .collect(Collectors.toList());
    }

    
    private float calcularTF(String palavra, List<String> palavrasFiltradas) {
        long ocorrencias = palavrasFiltradas.stream().filter(p -> p.equals(palavra)).count();
        return (float) ocorrencias / palavrasFiltradas.size();  
    }
}
