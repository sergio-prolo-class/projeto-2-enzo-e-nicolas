package ifsc.joe.interfaces;

import ifsc.joe.enums.Direcao;

public interface Movel extends Posicionavel {
    void mover(Direcao direcao, int maxLargura, int maxAltura);

    int getVelocidade();
}
