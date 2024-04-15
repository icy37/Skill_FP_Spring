PGDMP     5                    |            skill_fp    15.4    15.4                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            	           1262    24901    skill_fp    DATABASE     �   CREATE DATABASE skill_fp WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE skill_fp;
                postgres    false            �            1259    24924 
   operations    TABLE     �   CREATE TABLE public.operations (
    operation_id integer NOT NULL,
    user_id integer,
    operation_type integer NOT NULL,
    amount numeric(10,2) NOT NULL,
    operation_date timestamp without time zone NOT NULL,
    description text
);
    DROP TABLE public.operations;
       public         heap    postgres    false            �            1259    24923    operations_operation_id_seq    SEQUENCE     �   CREATE SEQUENCE public.operations_operation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public.operations_operation_id_seq;
       public          postgres    false    217            
           0    0    operations_operation_id_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public.operations_operation_id_seq OWNED BY public.operations.operation_id;
          public          postgres    false    216            �            1259    24903    users    TABLE     h   CREATE TABLE public.users (
    id integer NOT NULL,
    balance numeric(10,2) DEFAULT 0.00 NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    24902    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    215                       0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    214            l           2604    24927    operations operation_id    DEFAULT     �   ALTER TABLE ONLY public.operations ALTER COLUMN operation_id SET DEFAULT nextval('public.operations_operation_id_seq'::regclass);
 F   ALTER TABLE public.operations ALTER COLUMN operation_id DROP DEFAULT;
       public          postgres    false    216    217    217            j           2604    24906    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    215    215                      0    24924 
   operations 
   TABLE DATA           p   COPY public.operations (operation_id, user_id, operation_type, amount, operation_date, description) FROM stdin;
    public          postgres    false    217   H                 0    24903    users 
   TABLE DATA           ,   COPY public.users (id, balance) FROM stdin;
    public          postgres    false    215   �                  0    0    operations_operation_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.operations_operation_id_seq', 5, true);
          public          postgres    false    216                       0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 1, true);
          public          postgres    false    214            p           2606    24929    operations operations_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.operations
    ADD CONSTRAINT operations_pkey PRIMARY KEY (operation_id);
 D   ALTER TABLE ONLY public.operations DROP CONSTRAINT operations_pkey;
       public            postgres    false    217            n           2606    24912    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    215            q           2606    24930 "   operations operations_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.operations
    ADD CONSTRAINT operations_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 L   ALTER TABLE ONLY public.operations DROP CONSTRAINT operations_user_id_fkey;
       public          postgres    false    217    215    3182               �   x�����0�s2Ehd;v�d&�2p����TBB-+85��8!���2:t�  8��Gh!	
<hr��S���j�bN)�g3�B�D,���J��ζأ�n�M-7����G�����?�6$@��_G^��&C1pLJ��bs=��{o�72��
�Jd�            x�3�40�30������ 7     