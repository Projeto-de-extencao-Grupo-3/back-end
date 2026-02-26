package geo.track.exception.constraint.message;

public class OrdemDeServicoExceptionMessages {
    public static final String ORDEM_JA_EXISTE_PARA_REGISTRO_ENTRADA = "Já existe uma ordem de serviço para o registro de entrada com ID %d";
    public static final String ORDEM_NAO_ENCONTRADA_ID = "O ID %d não foi encontrado ou não pertence a esta oficina";
    public static final String ORDEM_NAO_ENCONTRADA_ID_GENERICO = "Não existe uma ordem com esse ID"; // For cases where idOficina is not yet available or not relevant
    public static final String ORDEM_NAO_PODE_SER_DELETADA_COM_SERVICOS = "Não é possível deletar ordem de serviço que possui serviços anexados";
    public static final String SOLICITACAO_RECUSADA = "Solicitação recusada";
    public static final String STATUS_INVALIDO = "Status inválido. Os permitidos são: %s";
}
