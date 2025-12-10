package ifsc.joe.interfaces;

public interface Combatente {
    void atacar();

    int getAtaque();

    int getAlcanceAtaque();

    boolean estaNoAlcance(Posicionavel outro);
}
