CREATE TABLE compte_bancaire_entity (
    numero_de_compte VARCHAR(255) PRIMARY KEY,
    solde NUMERIC,
    type_de_compte VARCHAR(31) NOT NULL
);

CREATE TABLE compte_courant_entity (
   numero_de_compte VARCHAR(255) PRIMARY KEY,
   autorisation_decouvert NUMERIC,
   FOREIGN KEY (numero_de_compte) REFERENCES compte_bancaire_entity(numero_de_compte)
);

CREATE TABLE livret_epargne_entity (
   numero_de_compte VARCHAR(255) PRIMARY KEY,
   plafond_depot NUMERIC,
   FOREIGN KEY (numero_de_compte) REFERENCES compte_bancaire_entity(numero_de_compte)
);
