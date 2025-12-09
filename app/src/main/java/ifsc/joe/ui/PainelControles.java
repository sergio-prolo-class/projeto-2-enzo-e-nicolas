package ifsc.joe.ui;

import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
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
    
    // Componentes do placar de baixas
    private JPanel painelPlacar;
    private JLabel labelBaixasAldeoes;
    private JLabel labelBaixasArqueiros;
    private JLabel labelBaixasCavaleiros;
    private JLabel labelTotalBaixas;
    private Timer timerAtualizarPlacar;

    public PainelControles() {
        this.sorteio = new Random();
        configurarListeners();
        iniciarAtualizacaoPlacar();
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
     * Aldeões não podem atacar, então o botão é desabilitado quando selecionado.
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
        boolean aldeaoSelecionado = aldeaoRadioButton.isSelected();
        boolean cavaleiroSelecionado = cavaleiroRadioButton.isSelected();
        boolean todosSelecionado = todosRadioButton.isSelected();
        
        // Botão de ataque
        atacarButton.setEnabled(!aldeaoSelecionado);
        if (aldeaoSelecionado) {
            atacarButton.setToolTipText("Aldeões não podem atacar");
        } else {
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
     * Nota: Aldeões não atacam, então essa opção não é chamada para eles.
     */
    private void atacarPorTipoSelecionado() {
        if (arqueiroRadioButton.isSelected()) {
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
        final int PADDING = 50;
        int posX = sorteio.nextInt(Math.max(1, painelTela.getWidth() - PADDING));
        int posY = sorteio.nextInt(Math.max(1, painelTela.getHeight() - PADDING));
        return new int[]{posX, posY};
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
        painelPlacar.setLayout(new GridLayout(4, 1, 2, 2));
        painelPlacar.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Baixas"));
        
        labelBaixasAldeoes = new JLabel("Aldeões: 0");
        labelBaixasArqueiros = new JLabel("Arqueiros: 0");
        labelBaixasCavaleiros = new JLabel("Cavaleiros: 0");
        labelTotalBaixas = new JLabel("Total: 0");
        
        // Estiliza os labels
        Font fontePlacar = new Font("SansSerif", Font.BOLD, 11);
        labelBaixasAldeoes.setFont(fontePlacar);
        labelBaixasArqueiros.setFont(fontePlacar);
        labelBaixasCavaleiros.setFont(fontePlacar);
        labelTotalBaixas.setFont(fontePlacar);
        labelTotalBaixas.setForeground(new Color(180, 0, 0));
        
        painelPlacar.add(labelBaixasAldeoes);
        painelPlacar.add(labelBaixasArqueiros);
        painelPlacar.add(labelBaixasCavaleiros);
        painelPlacar.add(labelTotalBaixas);
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
            labelBaixasAldeoes.setText("Aldeões: " + getTela().getBaixasAldeoes());
            labelBaixasArqueiros.setText("Arqueiros: " + getTela().getBaixasArqueiros());
            labelBaixasCavaleiros.setText("Cavaleiros: " + getTela().getBaixasCavaleiros());
            labelTotalBaixas.setText("Total: " + getTela().getTotalBaixas());
        }
    }
}
