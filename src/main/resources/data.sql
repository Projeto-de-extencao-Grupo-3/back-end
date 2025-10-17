INSERT INTO Oficinas (razao_social, cnpj, email, dt_criacao, status, senha)
VALUES ('GRO Track', '14.820.390/0001-50', 'geosmar@grotrack.com', '2025-09-20 09:15:00', 'ATIVA',
'$2a$10$jK9yW8hs15vmta/TyTRTZOAlXi8UGn0KQYtzqI5WY0WL7ek8RU0xu');
--Senha:123456

INSERT INTO funcionarios ( nome, cargo, especialidade, telefone, fk_oficina ) VALUES
('Diego Rafael Lima', 'Mecânico Chefe', 'Sistemas de Freios e Transmissão', '31999988888', 1),
('Luiz Fernando Rocha', 'Eletricista de Veículos', 'Elétrica e Eletrônica de Ônibus', '31988887777', 1);

INSERT INTO enderecos (cep, logradouro, numero, complemento, bairro, cidade, estado ) VALUES
('01001-000', 'Praça da Sé', 45, 'Apto 101', 'Centro', 'São Paulo', 'SP' ),
('70070-000', 'Eixo Monumental', 100, 'Torre B', 'Asa Sul', 'Brasília', 'DF');

INSERT INTO clientes (nome, cpf_cnpj, telefone, email, fk_oficina, fk_endereco) VALUES
('Ana Paula Martins', '123.456.789-09', '11987654321', 'ana.martins@example.com', 1, 1),
('Bruno Henrique Souza', '987.654.321-00', '11991234567', 'bruno.souza@example.com', 1, 2);

INSERT INTO veiculos (placa, marca, modelo, ano_modelo, cor, fk_cliente) VALUES
('FRO1C23', 'Caio', 'Apache Vip V', 2024, 'Vermelho', 1),
('TUR7E89', 'Marcopolo', 'Paradiso G8 1800 DD', 2022, 'Cinza', 2);
