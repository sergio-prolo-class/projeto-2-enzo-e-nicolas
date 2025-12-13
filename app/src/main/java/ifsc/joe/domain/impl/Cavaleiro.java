package ifsc.joe.domain.impl;

import ifsc.joe.config.Constantes;
import ifsc.joe.domain.Personagem;

import java.awt.*;

/**
 * Classe que representa um Cavaleiro no jogo.
 * O Cavaleiro pode alternar entre montado e desmontado.
 * Quando montado, é mais rápido. Quando desmontado, é um guerreiro.
 * Especializado em combate corpo a corpo com alcance médio (75px).
 */
public class Cavaleiro extends Personagem {

    private static final String NOME_IMAGEM_MONTADO = "cavaleiro";
    private static final String NOME_IMAGEM_MONTADO_ATACANDO = "cavaleiro2";
    private static final String NOME_IMAGEM_DESMONTADO = "guerreiro";
    private static final String NOME_IMAGEM_DESMONTADO_ATACANDO = "guerreiro2";

    private boolean montado;

    public Cavaleiro(int x, int y) {
        super(x, y);
        this.montado = true; // Começa montado por padrão
    }

    @Override
    public String getNomeImagem() {
        return montado ? NOME_IMAGEM_MONTADO : NOME_IMAGEM_DESMONTADO;
    }

    @Override
    public String getNomeImagemAtacando() {
        // Retorna a imagem de ataque correspondente ao estado de montaria
        return montado ? NOME_IMAGEM_MONTADO_ATACANDO : NOME_IMAGEM_DESMONTADO_ATACANDO;
    }

    /**
     * Alterna o estado de montaria do cavaleiro.
     * Quando montado, fica mais rápido. Quando desmontado, é um guerreiro.
     */
    public void alternarMontado() {
        this.montado = !this.montado;
    }

    /**
     * Verifica se o cavaleiro está montado.
     *
     * @return true se montado, false se desmontado
     */
    public boolean isMontado() {
        return montado;
    }

    /**
     * A velocidade do Cavaleiro depende se está montado ou não.
     * Montado é mais rápido, desmontado tem velocidade normal.
     *
     * @return velocidade em pixels por movimento
     */
    @Override
    public int getVelocidade() {
        return montado ? Constantes.Cavaleiro.VELOCIDADE_MONTADO : Constantes.Cavaleiro.VELOCIDADE_DESMONTADO;
    }

    /**
     * O Cavaleiro tem alto poder de ataque devido à força do combate montado.
     *
     * @return valor de ataque do cavaleiro
     */
    @Override
    public int getAtaqueBase() {
        return Constantes.Cavaleiro.ATAQUE;
    }

    /**
     * O Cavaleiro tem mais vida por usar armadura pesada.
     *
     * @return vida inicial do cavaleiro
     */
    @Override
    public int getVidaInicial() {
        return Constantes.Cavaleiro.VIDA;
    }

    /**
     * O Cavaleiro tem alcance médio de ataque (lança/espada montada).
     *
     * @return alcance de ataque em pixels
     */
    @Override
    public int getAlcanceAtaque() {
        return Constantes.Cavaleiro.ALCANCE;
    }

    /**
     * Cor da aura do Cavaleiro (azul).
     *
     * @return cor da aura de alcance
     */
    @Override
    public Color getCorAlcance() {
        return Constantes.Cavaleiro.COR_AURA;
    }
}
