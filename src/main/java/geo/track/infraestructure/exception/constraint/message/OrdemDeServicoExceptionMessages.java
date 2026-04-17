package geo.track.infraestructure.exception.constraint.message;

import geo.track.jornada.infraestructure.persistence.entity.Status;

import java.util.Arrays;

public class OrdemDeServicoExceptionMessages {
    public static final String ORDEM_JA_EXISTE_PARA_REGISTRO_ENTRADA = "Já existe uma ordem de serviço para o registro de entrada informado";
    public static final String ORDEM_NAO_ENCONTRADA_ID = "Não foi encontrada nenhuma ordem de serviço com este IDg";
    public static final String ORDEM_NAO_PODE_SER_DELETADA_COM_SERVICOS = "Não é possível deletar ordem de serviço que possui serviços anexados";
    public static final String SOLICITACAO_RECUSADA = "Solicitação recusada";
    public static final String STATUS_INVALIDO = "Status inválido. Os permitidos são: %s".formatted(Arrays.stream(Status.values()).map(Enum::name).toList());
public static final String ULTIMA_ORDEM_NAO_ENCONTRADA_POR_VEICULO = "Não foi encontrada nenhuma ordem de serviço para este veículo";
}
