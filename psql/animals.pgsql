--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.5.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: animal; Type: TABLE; Schema: public; Owner: melaniejohnson
--

CREATE TABLE animal (
    animal_id integer NOT NULL,
    animal_name character varying(20),
    animal_type_id integer NOT NULL,
    breed character varying(20),
    description character varying(100)
);


ALTER TABLE animal OWNER TO melaniejohnson;

--
-- Name: animal_animal_id_seq; Type: SEQUENCE; Schema: public; Owner: melaniejohnson
--

CREATE SEQUENCE animal_animal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animal_animal_id_seq OWNER TO melaniejohnson;

--
-- Name: animal_animal_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: melaniejohnson
--

ALTER SEQUENCE animal_animal_id_seq OWNED BY animal.animal_id;


--
-- Name: animal_type; Type: TABLE; Schema: public; Owner: melaniejohnson
--

CREATE TABLE animal_type (
    animal_type_id integer NOT NULL,
    species character varying(30)
);


ALTER TABLE animal_type OWNER TO melaniejohnson;

--
-- Name: animal_type_animal_type_id_seq; Type: SEQUENCE; Schema: public; Owner: melaniejohnson
--

CREATE SEQUENCE animal_type_animal_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animal_type_animal_type_id_seq OWNER TO melaniejohnson;

--
-- Name: animal_type_animal_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: melaniejohnson
--

ALTER SEQUENCE animal_type_animal_type_id_seq OWNED BY animal_type.animal_type_id;


--
-- Name: note; Type: TABLE; Schema: public; Owner: melaniejohnson
--

CREATE TABLE note (
    animal_id integer,
    note_text character varying(2000) NOT NULL,
    date_time date DEFAULT now() NOT NULL,
    note_id integer NOT NULL
);


ALTER TABLE note OWNER TO melaniejohnson;

--
-- Name: note_note_id_seq; Type: SEQUENCE; Schema: public; Owner: melaniejohnson
--

CREATE SEQUENCE note_note_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE note_note_id_seq OWNER TO melaniejohnson;

--
-- Name: note_note_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: melaniejohnson
--

ALTER SEQUENCE note_note_id_seq OWNED BY note.note_id;


--
-- Name: animal_id; Type: DEFAULT; Schema: public; Owner: melaniejohnson
--

ALTER TABLE ONLY animal ALTER COLUMN animal_id SET DEFAULT nextval('animal_animal_id_seq'::regclass);


--
-- Name: animal_type_id; Type: DEFAULT; Schema: public; Owner: melaniejohnson
--

ALTER TABLE ONLY animal_type ALTER COLUMN animal_type_id SET DEFAULT nextval('animal_type_animal_type_id_seq'::regclass);


--
-- Name: note_id; Type: DEFAULT; Schema: public; Owner: melaniejohnson
--

ALTER TABLE ONLY note ALTER COLUMN note_id SET DEFAULT nextval('note_note_id_seq'::regclass);


--
-- Data for Name: animal; Type: TABLE DATA; Schema: public; Owner: melaniejohnson
--

COPY animal (animal_id, animal_name, animal_type_id, breed, description) FROM stdin;
26	Jason	16	Brittany Spaniel	white with brown spots
30	Mark	14	calico	beige brown taupe & white\r\n 
31	Arnold	14	Tabby	small and energetic and fell down yeserday
895	Kyle	14	hairless	meow 
662	Sandy	14	Maine Coon	cute 
536	Arnold	14	Tabby	small and energetic and full of life
455	Molle	14	calico	very pretty 
34	Phillipe	15	hairy	 this guy is very hairy
35	Zach	14	Tabby	adorable 
36	Zach	14	Tabby	adorable 
702	Zach	14	Tabby	very adorable but eats too much and will throw up on your shoes.
744	Kitty-Kat	14	Maine Coon	very refined cat
321	Julie	14	Tabby	h
461	Chrissie	14	Tabby	Meow! I'm adorable 
5	Bob	14	Tabby	cute but a pain in the ass.
6	Lovely Lucy	14	Tabby	Dark and lovely\r\nnice, but moody 
11	George	14	cocker spaniel	very authoritarian   
12	Chelsea	14	Tabby	Gorgeous!!!  
19	Mary	14	Parakeet	Fluffy  puffy and old
21	Zilly	14	Tabby	Meow! She's adorable and knows it
23	Katherine Celeste	14	Tabby	Meow!!!!  be careful - do not turn your back on this cat.
24	Jonathan	14	tabby	Be careful with this one...  
38	Mulva	14	mountain	brown, needs a shave  
25	Laura	14	Tabby	Smart n' cunning  
193	Champ	17	Cockatiel	has lots of feathers
20	Billy Bob	14	Maine Coon	Likes grass  and dirt
107	PB&J	18	pond	not green, but not brown either
631	Babe	18	pond	green and bumpy
207	Miss Piggy	19	celebrity	glamorous
\.


--
-- Name: animal_animal_id_seq; Type: SEQUENCE SET; Schema: public; Owner: melaniejohnson
--

SELECT pg_catalog.setval('animal_animal_id_seq', 38, true);


--
-- Data for Name: animal_type; Type: TABLE DATA; Schema: public; Owner: melaniejohnson
--

COPY animal_type (animal_type_id, species) FROM stdin;
14	cat
15	goat
16	dog
17	bird
18	frog
19	pig
\.


--
-- Name: animal_type_animal_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: melaniejohnson
--

SELECT pg_catalog.setval('animal_type_animal_type_id_seq', 19, true);


--
-- Data for Name: note; Type: TABLE DATA; Schema: public; Owner: melaniejohnson
--

COPY note (animal_id, note_text, date_time, note_id) FROM stdin;
455	in excellent health!	2016-09-03	10
702	healthy	2016-09-03	11
702	perfectly healthy	2016-09-03	12
321	healthy. No checkup required	2016-09-03	13
895	in perfect health	2016-09-03	14
321	Latest checkup reveals that she is in great health!	2016-09-06	17
6	A quick note to test....	2016-09-08	18
5	Here is a test note	2016-09-18	19
744	Great checkup today. Kitty-Kat tried to eat several cotton swabs, but other than that, super visit	2016-09-19	20
12	Chelsea is in great health!	2016-09-20	23
19	Mary needs to be eating more worms	2016-09-20	24
21	Zilly is a healthy, happy cat	2016-09-20	25
21	Zilly should not eating so much fish	2016-09-20	26
25	Her symptons are acute. I think we should keep Laura overnight for observation	2016-09-21	27
25	Laura seems to be afraid of the dark. I recommend her getting a nightlight.	2016-09-21	28
107	This is the first note of the rest of PB&J's life	2016-11-19	29
11	George is still a cat	2016-11-22	30
\.


--
-- Name: note_note_id_seq; Type: SEQUENCE SET; Schema: public; Owner: melaniejohnson
--

SELECT pg_catalog.setval('note_note_id_seq', 30, true);


--
-- Name: animal_pkey; Type: CONSTRAINT; Schema: public; Owner: melaniejohnson
--

ALTER TABLE ONLY animal
    ADD CONSTRAINT animal_pkey PRIMARY KEY (animal_id);


--
-- Name: animal_type_pkey; Type: CONSTRAINT; Schema: public; Owner: melaniejohnson
--

ALTER TABLE ONLY animal_type
    ADD CONSTRAINT animal_type_pkey PRIMARY KEY (animal_type_id);


--
-- Name: note_note_id_pk; Type: CONSTRAINT; Schema: public; Owner: melaniejohnson
--

ALTER TABLE ONLY note
    ADD CONSTRAINT note_note_id_pk PRIMARY KEY (note_id);


--
-- Name: animal_animal_id_uindex; Type: INDEX; Schema: public; Owner: melaniejohnson
--

CREATE UNIQUE INDEX animal_animal_id_uindex ON animal USING btree (animal_id);


--
-- Name: animal_type_animal_type_id_uindex; Type: INDEX; Schema: public; Owner: melaniejohnson
--

CREATE UNIQUE INDEX animal_type_animal_type_id_uindex ON animal_type USING btree (animal_type_id);


--
-- Name: note_note_id_uindex; Type: INDEX; Schema: public; Owner: melaniejohnson
--

CREATE UNIQUE INDEX note_note_id_uindex ON note USING btree (note_id);


--
-- Name: animal_animal_type_animal_type_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: melaniejohnson
--

ALTER TABLE ONLY animal
    ADD CONSTRAINT animal_animal_type_animal_type_id_fk FOREIGN KEY (animal_type_id) REFERENCES animal_type(animal_type_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: note_animal_animal_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: melaniejohnson
--

ALTER TABLE ONLY note
    ADD CONSTRAINT note_animal_animal_id_fk FOREIGN KEY (animal_id) REFERENCES animal(animal_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

