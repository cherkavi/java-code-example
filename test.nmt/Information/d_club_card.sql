-- Start of DDL Script for Table BC_ADMIN.AA
-- Generated 5-Sep-2008 11:39:12 from BC_ADMIN@demo

CREATE TABLE d_club_card
    (card_serial_number             VARCHAR2(20),
    nt_icc                         NUMBER NOT NULL,
    cd_card1                       NUMBER(30,0),
    id_club                        NUMBER,
    id_issuer                      NUMBER NOT NULL,
    id_payment_system              NUMBER NOT NULL,
    name_payment_system            VARCHAR2(250) NOT NULL,
    cd_currency                    NUMBER(10,0) NOT NULL,
    name_currency                  VARCHAR2(250),
    sname_currency                 VARCHAR2(25),
    id_nat_prs                     NUMBER,
    date_open                      VARCHAR2(75),
    date_close                     VARCHAR2(75),
    expiry_date                    VARCHAR2(75),
    ver_key                        NUMBER(10,0),
    id_card_status                 NUMBER NOT NULL,
    name_card_status               VARCHAR2(250) NOT NULL,
    id_card_state                  NUMBER NOT NULL,
    name_card_state                VARCHAR2(250) NOT NULL,
    id_card_type                   NUMBER NOT NULL,
    name_card_type                 VARCHAR2(250) NOT NULL,
    id_bon_category                NUMBER NOT NULL,
    name_bon_category              VARCHAR2(250) NOT NULL,
    id_disc_category               NUMBER NOT NULL,
    name_disc_category             VARCHAR2(250) NOT NULL,
    club_bon                       VARCHAR2(18),
    club_bon_percent               VARCHAR2(19),
    club_disc                      VARCHAR2(18),
    club_disc_percent              VARCHAR2(19),
    bal_acc                        VARCHAR2(19),
    bal_cur                        VARCHAR2(19),
    bal_bon_per                    VARCHAR2(19),
    bal_disc_per                   VARCHAR2(19),
    date_acc                       VARCHAR2(75),
    date_mov                       VARCHAR2(75),
    date_calc                      VARCHAR2(75),
    date_onl                       VARCHAR2(75),
    bal_full                       VARCHAR2(18),
    disc_full                      VARCHAR2(18),
    creation_date                  DATE NOT NULL,
    created_by                     NUMBER(5,0) NOT NULL,
    last_update_date               DATE NOT NULL,
    last_update_by                 NUMBER(5,0) NOT NULL,
    contract_number                VARCHAR2(50),
    contract_date                  VARCHAR2(75),
    contract_desc                  VARCHAR2(1000),
    last_update_card_state         VARCHAR2(75),
    date_last_trans                VARCHAR2(75),
    id_last_trans                  NUMBER,
    date_last_term_card_req        VARCHAR2(75),
    id_last_term_card_req          NUMBER,
    need_update_param              VARCHAR2(40),
    update_param_date              VARCHAR2(75),
    new_id_card_status             VARCHAR2(40),
    new_id_bon_category            VARCHAR2(40),
    new_id_disc_category           VARCHAR2(40),
    new_club_disc                  VARCHAR2(18),
    new_club_bon                   VARCHAR2(18),
    new_bal_acc                    VARCHAR2(19),
    new_bal_cur                    VARCHAR2(19),
    new_bal_bon_per                VARCHAR2(19),
    new_bal_disc_per               VARCHAR2(19),
    new_date_acc                   VARCHAR2(75),
    new_date_mov                   VARCHAR2(75),
    new_date_calc                  VARCHAR2(75),
    id_term_which_updated_param    VARCHAR2(40),
    club_msg                       VARCHAR2(160),
    club_msg_send_date             VARCHAR2(75),
    new_id_card_state              NUMBER(5,0),
    language                       VARCHAR2(4) NOT NULL)
  PCTFREE     10
  INITRANS    1
  MAXTRANS    255
  TABLESPACE  bcdata
  STORAGE   (
    INITIAL     65536
    MINEXTENTS  1
    MAXEXTENTS  2147483645
  )
  NOCACHE
  MONITORING
/





-- End of DDL Script for Table BC_ADMIN.AA


