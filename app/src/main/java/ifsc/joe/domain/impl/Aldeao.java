package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;

/**
 * Classe que representa um Aldeão no jogo.
 * O Aldeão é um personagem básico com velocidade padrão.
 */
public class Aldeao extends Personagem {

    private static final String NOME_IMAGEM = "aldeao";
    private static final String NOME_IMAGEM_ATACANDO = "aldeao2";

    public Aldeao(int x, int y) {
        super(x, y);
    }

    @Override
    public String getNomeImagem() {
        return NOME_IMAGEM;
    }

    @Override
    public String getNomeImagemAtacando() {
        return NOME_IMAGEM_ATACANDO;
    }
}
