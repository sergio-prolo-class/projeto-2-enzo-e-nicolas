package ifsc.joe.domain.impl;

import ifsc.joe.config.Constantes;
import ifsc.joe.domain.Personagem;

import java.awt.*;

/**
 * Classe que representa um Arqueiro no jogo.
 * O Arqueiro é um personagem ágil com velocidade maior que o Aldeão.
 * Especializado em ataques à distância com maior alcance (150px).
 */
public class Arqueiro extends Personagem {

    private static final String NOME_IMAGEM = "arqueiro";
    private static final String NOME_IMAGEM_ATACANDO = "arqueiro2";

    public Arqueiro(int x, int y) {
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

    /**
     * O Arqueiro é mais ágil e se move mais rápido que o Aldeão.
     *
     * @return velocidade em pixels por movimento
     */
    @Override
    public int getVelocidade() {
        return Constantes.Arqueiro.VELOCIDADE;
    }

    /**
     * O Arqueiro tem ataque moderado com suas flechas.
     *
     * @return valor de ataque do arqueiro
     */
    @Override
    public int getAtaqueBase() {
        return Constantes.Arqueiro.ATAQUE;
    }

    /**
     * O Arqueiro tem menos vida por usar armadura leve.
     *
     * @return vida inicial do arqueiro
     */
    @Override
    public int getVidaInicial() {
        return Constantes.Arqueiro.VIDA;
    }

    /**
     * O Arqueiro tem o maior alcance de ataque, podendo acertar de longe.
     *
     * @return alcance de ataque em pixels
     */
    @Override
    public int getAlcanceAtaque() {
        return Constantes.Arqueiro.ALCANCE;
    }

    /**
     * Cor da aura do Arqueiro (verde).
     *
     * @return cor da aura de alcance
     */
    @Override
    public Color getCorAlcance() {
        return Constantes.Arqueiro.COR_AURA;
    }
}
