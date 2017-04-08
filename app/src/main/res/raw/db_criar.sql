CREATE TABLE restaurante ( id integer NOT NULL PRIMARY KEY, nome varchar(255) NOT NULL );
CREATE TABLE comida ( id integer NOT NULL PRIMARY KEY AUTOINCREMENT, restaurante_id integer NOT NULL, nome varchar(255) NOT NULL, preco real(20), FOREIGN KEY (restaurante_id) REFERENCES restaurantes (id) );