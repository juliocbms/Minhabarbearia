CREATE TABLE AGENDAMENTOS (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    id_barbeiro BIGINT NOT NULL,
    start_at TIMESTAMP WITH TIME ZONE NOT NULL,
    end_at TIMESTAMP WITH TIME ZONE NOT NULL,
    data_lancamento DATE NOT NULL,
    CONSTRAINT FK_CLIENTE FOREIGN KEY (id_cliente) REFERENCES USUARIOS(id) ON DELETE CASCADE,
    CONSTRAINT FK_BARBEIRO FOREIGN KEY (id_barbeiro) REFERENCES USUARIOS(id) ON DELETE CASCADE,
    CONSTRAINT UK_AGENDAMENTOS_INTERVALO UNIQUE (start_at, end_at)
);
