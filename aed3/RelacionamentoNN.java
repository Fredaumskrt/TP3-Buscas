package aed3;

import java.util.ArrayList;

public class RelacionamentoNN {

    private IndiceInvertido indiceInvertido; 

    public RelacionamentoNN(IndiceInvertido indiceInvertido) {
        this.indiceInvertido = indiceInvertido;
    }
    
    public void adicionarAssociacao(String rotulo, int idTarefa, int frequencia) throws Exception {
        indiceInvertido.addTerm(rotulo, idTarefa, frequencia);
    }

    
    public void removerAssociacao(String rotulo, int idTarefa) throws Exception {
        indiceInvertido.removeTerm(rotulo, idTarefa);
    }

    
    public ArrayList<Integer> listarTarefasPorRotulo(String rotulo) throws Exception {
        return indiceInvertido.getDocs(rotulo);
    }

    
    public ArrayList<String> listarRotulosPorTarefa(int idTarefa) throws Exception {
        return indiceInvertido.getTerms(idTarefa);
    }
}
