CREATE DATABASE diadiabuy
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       LC_COLLATE = 'it_IT'
       LC_CTYPE = 'it_IT'
       CONNECTION LIMIT = -1;
       
CREATE SCHEMA public
  AUTHORIZATION postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
COMMENT ON SCHEMA public IS 'standard public schema';

CREATE TABLE fornitori
(
  id_fornitore integer NOT NULL,
  nome text NOT NULL,
  indirizzo text NOT NULL,
  telefono text NOT NULL,
  CONSTRAINT fornitori_pkey PRIMARY KEY (id_fornitore),
  CONSTRAINT fornitori_nome_key UNIQUE (nome)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE fornitori OWNER TO postgres;

CREATE TABLE fornitura
(
  fornitore integer NOT NULL,
  prodotto integer NOT NULL,
  CONSTRAINT fornitura_pkey PRIMARY KEY (fornitore, prodotto),
  CONSTRAINT fornitura_fornitore_fkey FOREIGN KEY (fornitore)
      REFERENCES fornitori (id_fornitore) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fornitura_prodotto_fkey FOREIGN KEY (prodotto)
      REFERENCES prodotti (id_prodotto) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE fornitura OWNER TO postgres;

CREATE TABLE ordini
(
  id_ordine integer NOT NULL,
  codice text NOT NULL,
  stato text NOT NULL,
  data timestamp with time zone NOT NULL,
  id_utente integer NOT NULL,
  CONSTRAINT ordini_pkey PRIMARY KEY (id_ordine),
  CONSTRAINT ordini_id_utente_fkey FOREIGN KEY (id_utente)
      REFERENCES utenti (id_utente) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE NO ACTION,
  CONSTRAINT ordini_codice_key UNIQUE (codice)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE ordini OWNER TO postgres;

CREATE TABLE prodotti
(
  id_prodotto integer NOT NULL,
  nome text NOT NULL,
  codice text NOT NULL,
  descrizione text NOT NULL,
  prezzo double precision NOT NULL DEFAULT 0,
  disponibilita integer NOT NULL DEFAULT 0,
  CONSTRAINT prodotti_pkey PRIMARY KEY (id_prodotto),
  CONSTRAINT prodotti_codice_key UNIQUE (codice)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE prodotti OWNER TO postgres;

CREATE TABLE righe_ordine
(
  id_riga_ordine integer NOT NULL,
  quantita integer NOT NULL,
  id_prodotto integer NOT NULL,
  id_ordine integer NOT NULL,
  numero_di_riga integer NOT NULL,
  nome_prodotto text NOT NULL,
  CONSTRAINT righe_ordine_pkey PRIMARY KEY (id_riga_ordine),
  CONSTRAINT righe_ordine_id_ordine_fkey FOREIGN KEY (id_ordine)
      REFERENCES ordini (id_ordine) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT righe_ordine_id_prodotto_fkey FOREIGN KEY (id_prodotto)
      REFERENCES prodotti (id_prodotto) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE NO ACTION,
  CONSTRAINT righe_ordine_id_ordine_key UNIQUE (id_ordine, id_prodotto),
  CONSTRAINT righe_ordine_id_ordine_key1 UNIQUE (id_ordine, numero_di_riga)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE righe_ordine OWNER TO postgres;

CREATE TABLE utenti
(
  id_utente integer NOT NULL,
  username text NOT NULL,
  ruolo text NOT NULL,
  CONSTRAINT utenti_pkey PRIMARY KEY (id_utente),
  CONSTRAINT utenti_username_key UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE utenti OWNER TO postgres;

CREATE SEQUENCE fornitore_sequence_id
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 9999999
  START 91
  CACHE 1;
ALTER TABLE fornitore_sequence_id OWNER TO postgres;

CREATE SEQUENCE ordine_sequence_id
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 9999999
  START 47
  CACHE 1;
ALTER TABLE ordine_sequence_id OWNER TO postgres;

CREATE SEQUENCE prodotto_sequence_id
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 9999999
  START 379
  CACHE 1;
ALTER TABLE prodotto_sequence_id OWNER TO postgres;

CREATE SEQUENCE riga_ordine_sequence_id
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 9999999
  START 75
  CACHE 1;
ALTER TABLE riga_ordine_sequence_id OWNER TO postgres;

CREATE SEQUENCE utente_sequence_id
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 9999999
  START 132
  CACHE 1;
ALTER TABLE utente_sequence_id OWNER TO postgres;
