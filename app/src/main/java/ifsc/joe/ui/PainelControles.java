package ifsc.joe.ui;

import ifsc.joe.config.Constantes;
import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Classe responsável por gerenciar os controles e interações da interface.
 * Conecta os componentes visuais com a lógica do jogo (Tela).
 */
public class PainelControles {

    private final Random sorteio;
    private Tela tela;

    // Componentes da interface (gerados pelo Form Designer)
    private JPanel painelPrincipal;
    private JPanel painelTela;
    private JPanel painelLateral;
    private JButton bCriaAldeao;
    private JButton bCriaArqueiro;
    private JButton bCriaCavaleiro;
    private JRadioButton todosRadioButton;
    private JRadioButton aldeaoRadioButton;
    private JRadioButton arqueiroRadioButton;
    private JRadioButton cavaleiroRadioButton;
    private JButton atacarButton;
    private JButton montarButton;
    private JButton buttonCima;
    private JButton buttonEsquerda;
    private JButton buttonBaixo;
    private JButton buttonDireita;
    private JLabel logo;

    // Componentes do placar de baixas e recursos
    private JPanel painelPlacar;
    private JLabel labelBaixasAldeoes;
    private JLabel labelBaixasArqueiros;
    private JLabel labelBaixasCavaleiros;
    private JLabel labelTotalBaixas;

    // Recursos
    private JLabel labelComida;
    private JLabel labelOuro;
    private JLabel labelMadeira;
    private JButton botaoColetar;

    private Timer timerAtualizarPlacar;

    public PainelControles() {
        this.sorteio = new Random();
        configurarListeners();
        desabilitarFocoBotoes();
        configurarAtalhosDoTeclado();
        iniciarAtualizacaoPlacar();
    }

    /**
     * Desabilita o foco dos botões para evitar conflitos com atalhos de teclado.
     * Isso impede que a tecla espaço ative o botão que está com foco.
     * Também remove Tab das teclas de navegação de foco para usar como atalho.
     */
    private void desabilitarFocoBotoes() {
        atacarButton.setFocusable(false);
        montarButton.setFocusable(false);
        buttonCima.setFocusable(false);
        buttonBaixo.setFocusable(false);
        buttonEsquerda.setFocusable(false);
        buttonDireita.setFocusable(false);
        bCriaAldeao.setFocusable(false);
        bCriaArqueiro.setFocusable(false);
        bCriaCavaleiro.setFocusable(false);
        if (botaoColetar != null)
            botaoColetar.setFocusable(false);

        // Remove Tab das teclas de navegação de foco para usar como atalho
        painelPrincipal.setFocusTraversalKeysEnabled(false);
    }

    /**
     * Configura todos os listeners dos botões.
     */
    private void configurarListeners() {
        configurarBotoesMovimento();
        configurarBotoesCriacao();
        configurarBotaoAtaque();
        configurarBotaoMontar();
        configurarRadioButtons();
    }

    /**
     * Configura todos os listeners dos botões de movimento.
     * O movimento depende do tipo de personagem selecionado nos radio buttons.
     */
    private void configurarBotoesMovimento() {
        buttonCima.addActionListener(e -> movimentarPorTipoSelecionado(Direcao.CIMA));
        buttonBaixo.addActionListener(e -> movimentarPorTipoSelecionado(Direcao.BAIXO));
        buttonEsquerda.addActionListener(e -> movimentarPorTipoSelecionado(Direcao.ESQUERDA));
        buttonDireita.addActionListener(e -> movimentarPorTipoSelecionado(Direcao.DIREITA));
    }

    /**
     * Movimenta os personagens baseado no tipo selecionado nos radio buttons.
     *
     * @param direcao direção do movimento
     */
    private void movimentarPorTipoSelecionado(Direcao direcao) {
        if (aldeaoRadioButton.isSelected()) {
            getTela().movimentarAldeoes(direcao);
        } else if (arqueiroRadioButton.isSelected()) {
            getTela().movimentarArqueiros(direcao);
        } else if (cavaleiroRadioButton.isSelected()) {
            getTela().movimentarCavaleiros(direcao);
        } else {
            // Por padrão (ou se "Todos" estiver selecionado), movimenta todos
            getTela().movimentarPersonagens(direcao);
        }
    }

    /**
     * Configura todos os listeners dos botões de criação
     */
    private void configurarBotoesCriacao() {
        bCriaAldeao.addActionListener(e -> criarAldeaoAleatorio());
        bCriaArqueiro.addActionListener(e -> criarArqueiroAleatorio());
        bCriaCavaleiro.addActionListener(e -> criarCavaleiroAleatorio());
    }

    /**
     * Configura o listener do botão de ataque.
     * O ataque depende do tipo de personagem selecionado nos radio buttons.
     */
    private void configurarBotaoAtaque() {
        atacarButton.addActionListener(e -> atacarPorTipoSelecionado());
    }

    /**
     * Configura o listener do botão de montar/desmontar.
     * Só funciona para cavaleiros.
     */
    private void configurarBotaoMontar() {
        montarButton.addActionListener(e -> {
            getTela().alternarMontariaCavaleiros();
        });
        montarButton.setToolTipText("Montar/Desmontar cavaleiros");
    }

    /**
     * Configura os listeners dos radio buttons para habilitar/desabilitar botões.
     * Só cavaleiros podem montar/desmontar.
     */
    private void configurarRadioButtons() {
        // Listener para atualizar estado dos botões conforme seleção
        aldeaoRadioButton.addActionListener(e -> atualizarEstadoBotoes());
        arqueiroRadioButton.addActionListener(e -> atualizarEstadoBotoes());
        cavaleiroRadioButton.addActionListener(e -> atualizarEstadoBotoes());
        todosRadioButton.addActionListener(e -> atualizarEstadoBotoes());

        // Configura estado inicial (Aldeão começa selecionado por padrão)
        atualizarEstadoBotoes();
    }

    /**
     * Atualiza o estado dos botões baseado no radio button selecionado.
     * - Desabilita ataque quando Aldeão estiver selecionado
     * - Habilita montar apenas quando Cavaleiro ou Todos estiver selecionado
     */
    private void atualizarEstadoBotoes() {
        boolean cavaleiroSelecionado = cavaleiroRadioButton.isSelected();
        boolean todosSelecionado = todosRadioButton.isSelected();

        // Botão de ataque
        if (aldeaoRadioButton.isSelected()) {
            atacarButton.setEnabled(false);
            atacarButton.setToolTipText("Aldeão não pode atacar");
        } else {
            atacarButton.setEnabled(true);
            atacarButton.setToolTipText("Atacar personagens no alcance");
        }

        // Botão de montar (só para cavaleiros)
        montarButton.setEnabled(cavaleiroSelecionado || todosSelecionado);
        if (cavaleiroSelecionado || todosSelecionado) {
            montarButton.setToolTipText("Montar/Desmontar cavaleiros");
        } else {
            montarButton.setToolTipText("Selecione Cavaleiro ou Todos");
        }
    }

    /**
     * Faz os personagens atacarem baseado no tipo selecionado nos radio buttons.
     */
    private void atacarPorTipoSelecionado() {
        if (aldeaoRadioButton.isSelected()) {
            // Aldeão não ataca
        } else if (arqueiroRadioButton.isSelected()) {
            getTela().atacarArqueiros();
        } else if (cavaleiroRadioButton.isSelected()) {
            getTela().atacarCavaleiros();
        } else {
            // Por padrão (ou se "Todos" estiver selecionado), ataca todos (exceto aldeões)
            getTela().atacarPersonagens();
        }
    }

    /**
     * Cria um aldeão em posição aleatória na tela.
     */
    private void criarAldeaoAleatorio() {
        int[] posicao = gerarPosicaoAleatoria();
        getTela().criarAldeao(posicao[0], posicao[1]);
    }

    /**
     * Cria um arqueiro em posição aleatória na tela.
     */
    private void criarArqueiroAleatorio() {
        int[] posicao = gerarPosicaoAleatoria();
        getTela().criarArqueiro(posicao[0], posicao[1]);
    }

    /**
     * Cria um cavaleiro em posição aleatória na tela.
     */
    private void criarCavaleiroAleatorio() {
        int[] posicao = gerarPosicaoAleatoria();
        getTela().criarCavaleiro(posicao[0], posicao[1]);
    }

    /**
     * Gera uma posição aleatória dentro dos limites da tela.
     *
     * @return array com [posX, posY]
     */
    private int[] gerarPosicaoAleatoria() {
        int posX = sorteio.nextInt(Math.max(1, painelTela.getWidth() - Constantes.Interface.PADDING_BORDAS));
        int posY = sorteio.nextInt(Math.max(1, painelTela.getHeight() - Constantes.Interface.PADDING_BORDAS));
        return new int[] { posX, posY };
    }

    /**
     * Obtém a referência da Tela com cast seguro.
     */
    private Tela getTela() {
        if (tela == null) {
            tela = (Tela) painelTela;
        }
        return tela;
    }

    /**
     * Retorna o painel principal para ser adicionado ao JFrame.
     */
    public JPanel getPainelPrincipal() {
        return painelPrincipal;
    }

    /**
     * Método chamado pelo Form Designer para criar componentes customizados.
     * Este método é invocado antes do construtor.
     */
    private void createUIComponents() {
        this.painelTela = new Tela();
        criarPainelPlacar();
    }

    /**
     * Cria o painel de placar com os labels.
     */
    private void criarPainelPlacar() {
        painelPlacar = new JPanel();
        // Reduzido para 4 linhas: 2 de baixas (2col) + 1 recursos + 1 botão
        painelPlacar.setLayout(new GridLayout(4, 1, 2, 2));
        painelPlacar.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Status"));

        labelBaixasAldeoes = new JLabel("Ald: 0");
        labelBaixasArqueiros = new JLabel("Arq: 0");
        labelBaixasCavaleiros = new JLabel("Cav: 0");
        labelTotalBaixas = new JLabel("Tot: 0");

        // Tooltips para siglas
        labelBaixasAldeoes.setToolTipText("Baixas de Aldeões");
        labelBaixasArqueiros.setToolTipText("Baixas de Arqueiros");
        labelBaixasCavaleiros.setToolTipText("Baixas de Cavaleiros");
        labelTotalBaixas.setToolTipText("Total de Baixas");

        // Painel aninhado para baixas (2x2)
        JPanel painelBaixasRow1 = new JPanel(new GridLayout(1, 2));
        JPanel painelBaixasRow2 = new JPanel(new GridLayout(1, 2));

        painelBaixasRow1.add(labelBaixasAldeoes);
        painelBaixasRow1.add(labelBaixasArqueiros);
        painelBaixasRow2.add(labelBaixasCavaleiros);
        painelBaixasRow2.add(labelTotalBaixas);

        // Painel aninhado para recursos (Compacto)
        JPanel painelRecursos = new JPanel(new GridLayout(1, 3, 2, 0));
        labelComida = new JLabel("C: 0");
        labelOuro = new JLabel("O: 0");
        labelMadeira = new JLabel("M: 0");

        // Tooltips para explicar as siglas
        labelComida.setToolTipText("Estoque de Comida");
        labelOuro.setToolTipText("Estoque de Ouro");
        labelMadeira.setToolTipText("Estoque de Madeira");

        // Estiliza os labels
        Font fontePlacar = new Font("SansSerif", Font.BOLD, 11);
        labelBaixasAldeoes.setFont(fontePlacar);
        labelBaixasArqueiros.setFont(fontePlacar);
        labelBaixasCavaleiros.setFont(fontePlacar);
        labelTotalBaixas.setFont(fontePlacar);
        labelTotalBaixas.setForeground(new Color(180, 0, 0));

        labelComida.setFont(fontePlacar);
        labelComida.setForeground(Constantes.Recursos.COR_COMIDA);
        labelOuro.setFont(fontePlacar);
        labelOuro.setForeground(new Color(180, 150, 0));
        labelMadeira.setFont(fontePlacar);
        labelMadeira.setForeground(Constantes.Recursos.COR_MADEIRA);

        painelRecursos.add(labelComida);
        painelRecursos.add(labelOuro);
        painelRecursos.add(labelMadeira);

        botaoColetar = new JButton("Coletar");
        botaoColetar.setToolTipText("Coletar recursos próximos (Aldeões) [Atalho: C]");
        botaoColetar.setFocusable(false);
        botaoColetar.addActionListener(e -> {
            if (getTela() != null)
                getTela().coletarRecursosProximos();
        });

        painelPlacar.add(painelBaixasRow1);
        painelPlacar.add(painelBaixasRow2);
        painelPlacar.add(painelRecursos);
        painelPlacar.add(botaoColetar);
    }

    /**
     * Inicia o timer para atualizar o placar periodicamente.
     */
    private void iniciarAtualizacaoPlacar() {
        timerAtualizarPlacar = new Timer(100, e -> atualizarPlacar());
        timerAtualizarPlacar.start();
    }

    /**
     * Atualiza os valores do placar na interface.
     */
    private void atualizarPlacar() {
        if (labelBaixasAldeoes != null && tela != null) {
            labelBaixasAldeoes.setText("Ald: " + getTela().getBaixasAldeoes());
            labelBaixasArqueiros.setText("Arq: " + getTela().getBaixasArqueiros());
            labelBaixasCavaleiros.setText("Cav: " + getTela().getBaixasCavaleiros());
            labelTotalBaixas.setText("Tot: " + getTela().getTotalBaixas());

            // Atualiza Recursos
            if (labelComida != null)
                labelComida.setText("C: " + getTela().getEstoqueComida());
            if (labelOuro != null)
                labelOuro.setText("O: " + getTela().getEstoqueOuro());
            if (labelMadeira != null)
                labelMadeira.setText("M: " + getTela().getEstoqueMadeira());
        }
    }

    /**
     * Configura todos os atalhos de teclado usando KeyEventDispatcher.
     * Esta abordagem intercepta TODAS as teclas globalmente, independente do foco.
     */
    private void configurarAtalhosDoTeclado() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            // Só processa eventos de tecla pressionada (ignora released e typed)
            if (e.getID() != KeyEvent.KEY_PRESSED) {
                return false;
            }

            int keyCode = e.getKeyCode();

            switch (keyCode) {
                // === MOVIMENTO (WASD e Setas) ===
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    movimentarPorTipoSelecionado(Direcao.CIMA);
                    return true;

                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    movimentarPorTipoSelecionado(Direcao.BAIXO);
                    return true;

                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    movimentarPorTipoSelecionado(Direcao.ESQUERDA);
                    return true;

                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    movimentarPorTipoSelecionado(Direcao.DIREITA);
                    return true;

                // === CRIAR PERSONAGENS (1, 2, 3) ===
                case KeyEvent.VK_1:
                case KeyEvent.VK_NUMPAD1:
                    criarAldeaoAleatorio();
                    return true;

                case KeyEvent.VK_2:
                case KeyEvent.VK_NUMPAD2:
                    criarArqueiroAleatorio();
                    return true;

                case KeyEvent.VK_3:
                case KeyEvent.VK_NUMPAD3:
                    criarCavaleiroAleatorio();
                    return true;

                // === ATACAR (Espaço) ===
                case KeyEvent.VK_SPACE:
                    atacarPorTipoSelecionado();
                    return true;

                // === ALTERNAR FILTRO DE TIPO (Tab) ===
                case KeyEvent.VK_TAB:
                    alternarFiltroTipo();
                    return true;

                // === MONTAR/DESMONTAR (M) ===
                case KeyEvent.VK_M:
                    getTela().alternarMontariaCavaleiros();
                    return true;

                // === COLETAR (C) ===
                case KeyEvent.VK_C:
                    getTela().coletarRecursosProximos();
                    return true;

                default:
                    return false; // Deixa outras teclas passarem
            }
        });
    }

    /**
     * Alterna entre os filtros de tipo de personagem na ordem:
     * Todos -> Aldeão -> Arqueiro -> Cavaleiro -> Todos...
     */
    private void alternarFiltroTipo() {
        if (todosRadioButton.isSelected()) {
            aldeaoRadioButton.setSelected(true);
        } else if (aldeaoRadioButton.isSelected()) {
            arqueiroRadioButton.setSelected(true);
        } else if (arqueiroRadioButton.isSelected()) {
            cavaleiroRadioButton.setSelected(true);
        } else {
            todosRadioButton.setSelected(true);
        }
        // Atualiza o estado dos botões após mudar a seleção
        atualizarEstadoBotoes();
    }
}
