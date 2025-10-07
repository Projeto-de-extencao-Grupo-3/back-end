INSERT INTO clientes (nome, cpf_cnpj, telefone, email) VALUES
('Ana Paula Martins', '123.456.789-09', '11987654321', 'ana.martins@example.com'),
('Bruno Henrique Souza', '987.654.321-00', '11991234567', 'bruno.souza@example.com'),
('Carla Fernanda Dias', '111.222.333-44', '21988776655', 'carla.dias@example.com'),
('Diego Rafael Lima', '555.666.777-88', '3199998888', 'diego.lima@example.com'),
('Eduarda Silva Costa', '222.333.444-55', '41987651234', 'eduarda.costa@example.com');

INSERT INTO Oficinas (razao_social, cnpj, email, dt_criacao, status, senha)
VALUES ('GRO Track', '14.820.390/0001-50', 'geosmar@grotrack.com', '2025-09-20 09:15:00', 'ATIVA',
'$2a$10$jK9yW8hs15vmta/TyTRTZOAlXi8UGn0KQYtzqI5WY0WL7ek8RU0xu');
--Senha:123456

INSERT INTO veiculos (placa, marca, ano_modelo, ano_fabricacao, cor) VALUES
('ABC1D23', 'Toyota', 2023, 2022, 'Prata'),
('XYZ9K87', 'Honda', 2024, 2023, 'Preto'),
('JKL4M56', 'Ford', 2022, 2021, 'Branco'),
('QWE8R12', 'Chevrolet', 2025, 2024, 'Vermelho'),
('MNO7P34', 'Volkswagen', 2023, 2022, 'Azul');