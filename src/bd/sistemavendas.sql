-- MySQL dump 10.13  Distrib 9.4.0, for Win64 (x86_64)
-- Host: localhost    Database: sistemavendas
-- ------------------------------------------------------
-- Server version	9.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- tabela cliente
DROP TABLE IF EXISTS cliente;
CREATE TABLE cliente (
  codcliente int NOT NULL AUTO_INCREMENT,
  nome varchar(100) NOT NULL,
  endereco varchar(200) DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  telefone varchar(20) DEFAULT NULL,
  PRIMARY KEY (codcliente),
  UNIQUE KEY email (email)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO cliente VALUES 
(1,'Rodrigo Alves','Rua Caramelo, 234','RodrigoA.234@gmail.com','15930345966'),
(2,'Carlos Pereira','Rua Amendoim, 789','carlosPereira2034@gmail.com','11940308675'),
(3,'Maria de Souza','Avenida Brigadeiro, 456','mariasouza@gmail.com','11988404934');

-- tabela nota_fiscal
DROP TABLE IF EXISTS nota_fiscal;
CREATE TABLE nota_fiscal (
  codnota int NOT NULL AUTO_INCREMENT,
  codcliente int NOT NULL,
  datavenda datetime NOT NULL,
  PRIMARY KEY (codnota),
  KEY codcliente (codcliente),
  CONSTRAINT nota_fiscal_ibfk_1 FOREIGN KEY (codcliente) REFERENCES cliente (codcliente)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO nota_fiscal VALUES 
(1,1,'2025-09-18 10:30:00'),
(2,2,'2025-09-18 11:00:00'),
(3,1,'2025-09-19 09:45:00');

-- tabela produto
DROP TABLE IF EXISTS produto;
CREATE TABLE produto (
  codproduto int NOT NULL AUTO_INCREMENT,
  nome varchar(100) NOT NULL,
  descricao varchar(200) DEFAULT NULL,
  preco decimal(10,2) NOT NULL,
  estoque int NOT NULL,
  PRIMARY KEY (codproduto)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO produto VALUES 
(1,'Caneta','Caneta azul',2.50,100),
(2,'Caderno','Caderno 100 folhas',15.00,50),
(3,'Borracha','Borracha branca',1.50,200),
(4,'Lápis','Lápis 2B',1.00,150);

-- tabela produto_nota
DROP TABLE IF EXISTS produto_nota;
CREATE TABLE produto_nota (
  codnota int NOT NULL,
  codproduto int NOT NULL,
  quantidade int NOT NULL,
  preco_unitario decimal(10,2) NOT NULL,
  subtotal decimal(10,2) GENERATED ALWAYS AS ((quantidade * preco_unitario)) STORED,
  PRIMARY KEY (codnota,codproduto),
  KEY codproduto (codproduto),
  CONSTRAINT produto_nota_ibfk_1 FOREIGN KEY (codnota) REFERENCES nota_fiscal (codnota),
  CONSTRAINT produto_nota_ibfk_2 FOREIGN KEY (codproduto) REFERENCES produto (codproduto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO produto_nota (codnota, codproduto, quantidade, preco_unitario) VALUES 
(1,1,10,2.50),
(1,2,2,15.00),
(2,3,5,1.50),
(2,4,10,1.00),
(3,1,3,2.50),
(3,4,7,1.00);

-- Triggers (compatíveis com phpMyAdmin e MySQL 5.7)
DELIMITER $$

CREATE TRIGGER atualizar_estoque_after_insert
AFTER INSERT ON produto_nota
FOR EACH ROW
BEGIN
    UPDATE produto
    SET estoque = estoque - NEW.quantidade
    WHERE codproduto = NEW.codproduto;
END$$

CREATE TRIGGER atualizar_estoque_after_update
AFTER UPDATE ON produto_nota
FOR EACH ROW
BEGIN
    UPDATE produto
    SET estoque = estoque + OLD.quantidade - NEW.quantidade
    WHERE codproduto = NEW.codproduto;
END$$

CREATE TRIGGER atualizar_nota_after_update
AFTER UPDATE ON produto_nota
FOR EACH ROW
BEGIN
    -- lógica opcional
END$$

DELIMITER ;

-- Final settings
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
