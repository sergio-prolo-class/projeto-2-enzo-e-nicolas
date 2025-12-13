package ifsc.joe.domain.impl;

import ifsc.joe.config.Constantes;
import ifsc.joe.domain.Personagem;
import ifsc.joe.domain.Recurso;
import ifsc.joe.interfaces.Coletador;

import java.awt.*;

/**
 * Classe que representa um Aldeão no jogo.
 * O Aldeão é um personagem básico com velocidade padrão.
 * Possui alcance curto de ataque (50px).
 */
public class Aldeao extends Personagem implements Coletador {

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

    /**
     * O Aldeão tem alcance curto de ataque.
     *
     * @return alcance de ataque em pixels
     */
    @Override
    public int getAlcanceAtaque() {
        return Constantes.Aldeao.ALCANCE;
    }

    /**
     * O Aldeão tem um ataque fraco.
     *
     * @return valor de ataque base
     */
    @Override
    public int getAtaqueBase() {
        return Constantes.Aldeao.ATAQUE;
    }

    @Override
    public int getVidaInicial() {
        return Constantes.Aldeao.VIDA;
    }

    @Override
    public int getVelocidade() {
        return Constantes.Aldeao.VELOCIDADE;
    }

    /**
     * Cor da aura do Aldeão (marrom/terra).
     *
     * @return cor da aura de alcance
     */
    @Override
    public Color getCorAlcance() {
        return Constantes.Aldeao.COR_AURA;
    }

    @Override
    public void coletar(Recurso recurso) {
        System.out.println("Aldeão coletou " + recurso.getQuantidade() + " de " + recurso.getTipo());
        // Animação ou som específico poderia ser aqui
    }
}
