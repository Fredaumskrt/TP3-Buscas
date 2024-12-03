package aed3;

import java.util.ArrayList;

public class CRUDRotulos {
    private ArvoreBMais<String> arvoreRotulos;
    private IndiceInvertido indiceInvertido;

    public CRUDRotulos(String arquivoArvore, IndiceInvertido indiceInvertido) throws Exception {
        arvoreRotulos = new ArvoreBMais<>(10, arquivoArvore);
        this.indiceInvertido = indiceInvertido;
    }

    public ArrayList<Integer> buscarRotulo(String rotulo) throws Exception {

        if (arvoreRotulos.existe(rotulo)) {

            return arvoreRotulos.ler(rotulo);
        }

        return new ArrayList<>();
    }

    public void adicionarRotulo(String rotulo, int idTarefa, int frequencia) throws Exception {
        if (!arvoreRotulos.existe(rotulo)) {
            arvoreRotulos.inserir(rotulo, arvoreRotulos.gerarProximoID());
        }

        indiceInvertido.addTerm(rotulo, idTarefa, frequencia);
    }

    public void removerRotulo(String rotulo) throws Exception {
        if (arvoreRotulos.existe(rotulo)) {

            ArrayList<Integer> idsTarefas = arvoreRotulos.ler(rotulo);
            for (int idTarefa : idsTarefas) {

                indiceInvertido.removerTermo(rotulo, idTarefa);
            }

            arvoreRotulos.excluir(rotulo);
        }
    }

    public void editarRotulo(String rotuloAntigo, String rotuloNovo) throws Exception {
        if (arvoreRotulos.existe(rotuloAntigo)) {

            ArrayList<Integer> idsTarefas = arvoreRotulos.ler(rotuloAntigo);

            for (int idTarefa : idsTarefas) {
                indiceInvertido.removerTermo(rotuloAntigo, idTarefa);
            }

            arvoreRotulos.excluir(rotuloAntigo);
            arvoreRotulos.inserir(rotuloNovo, arvoreRotulos.gerarProximoID());

            for (int idTarefa : idsTarefas) {
                indiceInvertido.addTerm(rotuloNovo, idTarefa, 1);
            }
        }
    }
}
