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

INSERT INTO registro_entrada (data_entrada_prevista, data_entrada_efetiva, responsavel, cpf, extintor, macaco, chave_roda, geladeira, monitor, fk_veiculo) VALUES
('2025-10-25', '2025-10-25', 'Carlos Souza', '98765432109', TRUE, TRUE, TRUE, 0, 0, 1),
('2025-10-20', '2025-10-21', 'Mariana Alves', '12345678909', TRUE, TRUE, TRUE, 1, 1, 2);

INSERT INTO ordem_de_servicos (valor_total, dt_saida_prevista, dt_saida_efetiva, status, seguradora, nf_realizada, pagt_realizado, fk_entrada)
VALUES
(1850.00, '2025-10-28', NULL, 2, FALSE, FALSE, FALSE, 1),
(4500.00, '2025-10-27', '2025-10-27', 4, TRUE, TRUE, TRUE, 2);

INSERT INTO produtos (nome, fornecedor_nf, preco_compra, preco_venda, quantidade_estoque) VALUES
('Filtro de óleo', 'NF-987654', 120.50, 180.00, 45),
('Pastilha de freio', 'NF-123456', 90.00, 150.00, 60),
('Bateria 150Ah', 'NF-555222', 400.00, 650.00, 20),
('Amortecedor traseiro', 'NF-998877', 250.00, 390.00, 30),
('Correia dentada', 'NF-441122', 70.00, 120.00, 50);

INSERT INTO itens_produtos (quantidade, preco_peca, fk_produto,fk_order_de_servico) VALUES
(2, 180.00, 1, 1),
(4, 150.00, 2, 1);

INSERT INTO servicos (tipo_servico, titulo_servico, tempo_base, ativo) VALUES
(3, 'Troca de óleo e filtro', 60, TRUE),
(3, 'Revisão do sistema de freios', 120, TRUE),
(1, 'Reparo em parachoque dianteiro', 180, TRUE),
(2, 'Repintura completa da lateral direita', 240, TRUE);

INSERT INTO itens_servicos
(preco_cobrado, parte_veiculo, lado_veiculo, cor, especificacao_servico, observacoes_item, fk_servico, fk_order_de_servico)
VALUES
(350.00, 1, 1, 'Vermelho', 'Reparo no parachoque dianteiro após colisão leve', 'Necessário retoque de pintura', 3, 1),
(420.00, 3, 4, 'Vermelho', 'Repintura completa da lateral direita devido a riscos', 'Usar tinta metálica original', 4, 1);
