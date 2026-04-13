package geo.track.infraestructure.exception.constraint.message;

public class ClienteExceptionMessages {
    public static final String CPF_EXISTENTE = "O CPF do cliente informado já existe para esta oficina";
    public static final String CLIENTE_NAO_ENCONTRADO_ID = "O ID %d não foi encontrado ou não pertence a esta oficina";
    public static final String CLIENTE_NAO_ENCONTRADO_NOME = "O nome não foi encontrado para esta oficina";
    public static final String CLIENTE_NAO_ENCONTRADO_CPF_CNPJ = "CPF não foi encontrado para esta oficina";
    public static final String CLIENTE_NAO_ENCONTRADO_ID_OU_OFICINA = "Não existe cliente com esse ID ou não pertence a esta oficina";
    public static final String CLIENTE_NAO_ENCONTRADO_PLACA = "A placa do veículo informado não pertence a nenhum cliente";
    public static final String CLIENTE_NAO_PODE_SER_DELETADO_ORDENS_ABERTAS = "O cliente não pode ser deletado porque possui ordens de serviço em aberto";
}
