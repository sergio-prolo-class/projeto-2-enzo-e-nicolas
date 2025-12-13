package ifsc.joe.interfaces;

import ifsc.joe.domain.Recurso;

/**
 * Interface para entidades que podem coletar recursos.
 */
public interface Coletador {
    /**
     * Coleta um recurso específico.
     * 
     * @param recurso Recurso a ser coletado
     */
    void coletar(Recurso recurso);
}
