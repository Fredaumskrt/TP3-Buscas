package aed3;

import java.util.ArrayList;

public class CRUDRotulos {
    private ArvoreBMais<String> arvoreRotulos; // Árvore B+ para armazenar os rótulos
    private IndiceInvertido indiceInvertido; // Índice para as associações N:N

    public CRUDRotulos(String arquivoArvore, IndiceInvertido indiceInvertido) throws Exception {
        arvoreRotulos = new ArvoreBMais<>(10, arquivoArvore); // Ordem 10 para a árvore
        this.indiceInvertido = indiceInvertido;
    }

    // Adiciona um rótulo ao sistema
    public void adicionarRotulo(String rotulo, int idTarefa, int frequencia) throws Exception {
        // Adiciona o rótulo na árvore B+ se ainda não existir
        if (!arvoreRotulos.existe(rotulo)) {
            arvoreRotulos.inserir(rotulo, arvoreRotulos.gerarProximoID());
        }
        // Atualiza o índice invertido com a associação entre rótulo e tarefa
        indiceInvertido.addTerm(rotulo, idTarefa, frequencia);
    }

    // Busca rótulos pelo nome
    public ArrayList<Integer> buscarRotulo(String rotulo) throws Exception {
        if (arvoreRotulos.existe(rotulo)) {
            return arvoreRotulos.ler(rotulo);
        }
        return new ArrayList<>(); // Retorna vazio se não encontrar
    }

    // Remove um rótulo do sistema
    public void removerRotulo(String rotulo) throws Exception {
        if (arvoreRotulos.existe(rotulo)) {
            arvoreRotulos.excluir(rotulo);
            // O índice invertido será atualizado automaticamente nas operações de tarefa
        }
    }

    // Edita o nome de um rótulo
    public void editarRotulo(String rotuloAntigo, String rotuloNovo) throws Exception {
        if (arvoreRotulos.existe(rotuloAntigo)) {
            int id = arvoreRotulos.ler(rotuloAntigo).get(0); // Supondo que o ID está na lista
            arvoreRotulos.excluir(rotuloAntigo);
            arvoreRotulos.inserir(rotuloNovo, id);
        }
    }
}
