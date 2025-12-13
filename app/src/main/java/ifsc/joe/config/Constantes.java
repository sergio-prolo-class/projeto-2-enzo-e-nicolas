package ifsc.joe.config;

import java.awt.Color;

/**
 * Classe responsável por centralizar todas as constantes do jogo.
 * Facilita o balanceamento e ajustes visuais.
 */
public final class Constantes {

    // Construtor privado para impedir instancição (classe utilitária)
    private Constantes() {
    }

    public static final class Geral {
        private Geral() {
        }
    }

    public static final class Aldeao {
        private Aldeao() {
        }

        public static final int VIDA = 100;
        public static final int ATAQUE = 5;
        public static final int VELOCIDADE = 10;
        public static final int ALCANCE = 50;
        public static final Color COR_AURA = new Color(139, 90, 43, 50); // Marrom semi-transparente
    }

    public static final class Arqueiro {
        private Arqueiro() {
        }

        public static final int VIDA = 80;
        public static final int ATAQUE = 20;
        public static final int VELOCIDADE = 15;
        public static final int ALCANCE = 150;
        public static final Color COR_AURA = new Color(34, 139, 34, 50); // Verde semi-transparente
    }

    public static final class Cavaleiro {
        private Cavaleiro() {
        }

        public static final int VIDA = 150;
        public static final int ATAQUE = 25;
        public static final int VELOCIDADE_MONTADO = 20;
        public static final int VELOCIDADE_DESMONTADO = 10;
        public static final int ALCANCE = 75;
        public static final Color COR_AURA = new Color(65, 105, 225, 50); // Azul real semi-transparente
    }

    public static final class Interface {
        private Interface() {
        }

        public static final float REDUCAO_OPACIDADE = 0.1f;
        public static final int INTERVALO_FADE = 50; // milissegundos

        // Tooltip
        public static final int TOOLTIP_DISTANCIA_DETECCAO = 30;
        public static final int TOOLTIP_LARGURA = 140;
        public static final int TOOLTIP_ALTURA_LINHA = 15;
        public static final int TOOLTIP_PADDING_X = 10;
        public static final int TOOLTIP_PADDING_Y = 10;
        public static final int TOOLTIP_MARGIN_TEXTO = 20;
        public static final Color TOOLTIP_COR_FUNDO = new Color(0, 0, 0, 200);
        public static final Color TOOLTIP_COR_BORDA = Color.WHITE;

        // Geral
        public static final int PADDING_BORDAS = 50; // Padding para gerar posições aleatórias
    }

    public static final class Recursos {
        private Recursos() {
        }

        public static final int QUANTIDADE_PADRAO = 10;
        public static final Color COR_COMIDA = new Color(255, 69, 0); // Laranja avermelhado
        public static final Color COR_OURO = new Color(255, 215, 0); // Dourado
        public static final Color COR_MADEIRA = new Color(139, 69, 19); // Marrom madeira
    }
}
