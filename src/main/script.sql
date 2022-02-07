create table role
(
    id   bigint not null
        constraint role_pkey
            primary key,
    name varchar(255)
);

alter table role
    owner to postgres;

create table users
(
    id        bigint           not null
        constraint users_pkey
            primary key,
    civility  varchar(255),
    email     varchar(255),
    enabled   boolean,
    firstname varchar(255),
    lastname  varchar(255),
    locked    boolean,
    password  varchar(255),
    wallet    double precision not null
);

alter table users
    owner to postgres;

create table bank_account
(
    id_bank_account integer          not null
        constraint bank_account_pkey
            primary key,
    amount          double precision not null,
    iban            varchar(255),
    users_id        bigint           not null
        constraint fkm55p50v2mxwyxw3hqda611wgk
            references users
);

alter table bank_account
    owner to postgres;

create table transactions
(
    id             bigserial
        constraint transactions_pkey
            primary key,
    amount         double precision not null,
    commission     double precision not null,
    description    varchar(255),
    beneficiary_id bigint           not null
        constraint fkiicxasautxpvcx31wuuubbvyb
            references users,
    payer_id       bigint           not null
        constraint fk1grbegsy5x0g168mo784c6nrc
            references users
);

alter table transactions
    owner to postgres;

create table transfer
(
    id              bigserial
        constraint transfer_pkey
            primary key,
    amount          double precision not null,
    credit          double precision not null,
    debit           double precision not null,
    description     varchar(255),
    bank_account_id integer
        constraint fklj75pjijlbcltmfbt5g12tx2u
            references bank_account,
    users_id        bigint
        constraint fktdrkww29vngwb3m60ljtptyx9
            references users
);

alter table transfer
    owner to postgres;

create table users_contact_list
(
    user_entity_id  bigint not null
        constraint fksppbduiboicgvcbsb353qbwo1
            references users,
    contact_list_id bigint not null
        constraint fk78wes4vghl1xpgwn9b8h5qkbo
            references users
);

alter table users_contact_list
    owner to postgres;

create table users_roles
(
    user_entity_id bigint not null
        constraint fk7v417qhe0i2m9h8njggvciv00
            references users,
    roles_id       bigint not null
        constraint fk15d410tj6juko0sq9k4km60xq
            references role
);

alter table users_roles
    owner to postgres;


