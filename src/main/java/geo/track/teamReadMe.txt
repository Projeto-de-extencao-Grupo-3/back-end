Este readMe é determinado como um guia prático para entender sobre como está sendo estabelecido o padrão do Projeto.

Controller: Diretório que contém os pontos de entrada da aplicação, local que irá ter todos os endpoints.
Domain: Diretório determinado para ficar as classes de Domínio ou Entidade (Referentes as tabelas do Banco)
Repository: Diretório com as classes que representa a conexão com o Banco de Dados referente a uma Domínio/Entidade específica
Service: Diretório que possue as classes que vão conter à regra de negócio para os Endpoints (Controllers) consumirem
Request: Diretório que permanece os DTO (Data Transfer Object)