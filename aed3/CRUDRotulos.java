package aed3;

import java.util.ArrayList;

public class CRUDRotulos {
    private ArvoreBMais<String> arvoreRotulos;
    private IndiceInvertido indiceInvertido;

    public CRUDRotulos(String arquivoArvore, IndiceInvertido indiceInvertido) throws Exception {
        arvoreRotulos = new ArvoreBMais<>(10, arquivoArvore);
        this.indiceInvertido = indiceInvertido;
    }

    public void adicionarRotulo(String rotulo, int idTarefa, int frequencia) throws Exception {

        if (!arvoreRotulos.existe(rotulo)) {
            arvoreRotulos.inserir(rotulo, arvoreRotulos.gerarProximoID());
        }

        indiceInvertido.addTerm(rotulo, idTarefa, frequencia);
    }

    public ArrayList<Integer> buscarRotulo(String rotulo) throws Exception {
        if (arvoreRotulos.existe(rotulo)) {
            return arvoreRotulos.ler(rotulo);
        }
        return new ArrayList<>();
    }

    public void removerRotulo(String rotulo) throws Exception {
        if (arvoreRotulos.existe(rotulo)) {
            arvoreRotulos.excluir(rotulo);

        }
    }

    public void editarRotulo(String rotuloAntigo, String rotuloNovo) throws Exception {
        if (arvoreRotulos.existe(rotuloAntigo)) {
            int id = arvoreRotulos.ler(rotuloAntigo).get(0);
            arvoreRotulos.excluir(rotuloAntigo);
            arvoreRotulos.inserir(rotuloNovo, id);
        }
    }
}
