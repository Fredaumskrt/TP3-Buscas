package aed3;

import java.util.ArrayList;

public class RelacionamentoNN {

    private IndiceInvertido indiceInvertido; // Índice para gerenciar as associações

    public RelacionamentoNN(IndiceInvertido indiceInvertido) {
        this.indiceInvertido = indiceInvertido;
    }
    // Adiciona uma associação entre tarefa e rótulo
    public void adicionarAssociacao(String rotulo, int idTarefa, int frequencia) throws Exception {
        indiceInvertido.addTerm(rotulo, idTarefa, frequencia);
    }

    // Remove uma associação entre tarefa e rótulo
    public void removerAssociacao(String rotulo, int idTarefa) throws Exception {
        indiceInvertido.removeTerm(rotulo, idTarefa);
    }

    // Lista os IDs de tarefas associadas a um rótulo
    public ArrayList<Integer> listarTarefasPorRotulo(String rotulo) throws Exception {
        return indiceInvertido.getDocs(rotulo);
    }

    // Lista os rótulos associados a uma tarefa
    public ArrayList<String> listarRotulosPorTarefa(int idTarefa) throws Exception {
        return indiceInvertido.getTerms(idTarefa);
    }
}
