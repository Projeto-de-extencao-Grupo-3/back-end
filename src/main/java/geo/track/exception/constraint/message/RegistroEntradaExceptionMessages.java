package geo.track.exception.constraint.message;

public class RegistroEntradaExceptionMessages {
    public static final String REGISTRO_ENTRADA_NAO_ENCONTRADO = "Registro de Entrada não encontrado ou não pertence a esta oficina";
    public static final String REGISTRO_ENTRADA_CONFLITO_STATUS = "Já existe um registro de entrada para este veículo com uma ordem de serviço aguardando orçamento ou entrada nesta oficina.";
    public static final String VEICULO_JA_POSSUI_ENTRADA_EM_ANDAMENTO = "Já existe uma entrada que está em andamento com este veículo";
}
