--			CREATE TABLE CRMT_BUSINESSOBJECTS 
--			(
--			  ID NUMBER(10, 0) NOT NULL 
--			, TITLE VARCHAR2(100) 
--			, TYPE VARCHAR2(50) 
--			, PAYLOAD XMLTYPE
--			);

-- create sequence seq_businessobjects start with 0 increment by 1 minvalue 0 maxvalue 100000000


set serveroutput on buffer 2560000

declare 
-- declare types
  -- array of varchar
  type array_of_string is table of varchar2(100);
  -- array of integer
  type array_of_integer is table of integer;

  -- count of record which will be create
  table_count integer:=100000;
  -- time range begin
  date_begin date:=to_date('01.01.2011', 'DD.MM.YYYY');
  -- time range end 
  date_end date:=to_date('31.12.2011', 'DD.MM.YYYY');
  
-------------------------- #begin Static Data  ---------------------------------
-- declare static data 
  -- product_type
  static_data_id_product_type array_of_integer:=array_of_integer(
    1504, 1505, 1506, 1507, 1508, 1509, 1510, 1511, 1512, 1513, 1514, 1515, 1516, 1517, 1518, 1519, 1520, 1521, 1522, 1523, 1524, 1525, 1526, 1527, 1528, 1529, 1530, 1531, 1532
  );
  static_data_name_product_type array_of_string:=array_of_string(
    'DEFAULT', 'DEFAULT', 'BasketForward', 'SyntheticFuture', 'Forward', 'QAsian', 'VanillaOption', 'VarianceSwap', 'OptionStrategy', 'BasketOption', 'Accumulator', 'BasketSyntheticFuture', 'BasketQAsian', 'CPPTSwapEquity', 'CPPT', 'TOIS', 'OIS', 'DEFAULT', 'DEFAULT', 'DEFAULT', 'DEFAULT', 'DEFAULT', 'FRA', 'SPS', 'DEFAULT', 'DEFAULT', 'CDO', 'MBS', 'Unfunded CDS'
  );

  -- trade_source_system
  static_data_id_trade_source array_of_integer:=array_of_integer(
    10, 7, 14, 2, 4, 3, 6, 5, 12, 8, 11, 9, 13, 1, 17, 15, 16
  );
  static_data_name_trade_source array_of_string:=array_of_string(
    'BridgeOBS', 'CXL', 'Colt', 'GR', 'GR_APAC', 'GR_EU', 'GR_STM', 'Klondike', 'Laredo', 'MOR', 'NTS', 'OBS', 'Rambo', 'Sabre', 'Sport', 'Stomp Prod', 'Stomp RMP'
  );

  -- booking_location: select value, name from crm_t_staticdata where category='QMS_BookingLocation'  and active=1
  static_data_id_booking_loc array_of_integer:=array_of_integer(
    3, 2, 1
  );
  static_data_name_booking_loc array_of_string:=array_of_string(
    'APAC', 'EMEA', 'US' 
  );

  -- risk_class: select * from crm_t_staticdata where category='QMS_RiskClass' 
  static_data_id_risk_class array_of_integer:=array_of_integer(
    879, 880, 881, 882, 883, 884, 885, 886, 887, 888, 889, 890, 891, 892, 893, 894, 895, 896, 897, 898, 899, 900, 901, 902, 903, 904, 905, 906, 907, 908, 909, 910, 911, 912, 913, 914, 915, 916, 917
  );
  static_data_name_risk_class array_of_string:=array_of_string(
    '5027', '502A', '502B', '502C', '502D', '502H', '5031', '5038', '503A', '504A', '504B', '504N', '504T', '504Z', '505A', '505C', '5089', '5107', '5113', '5124', '5167', '516C', '5171', '517A', '5180', '5249', '5251', '5269', '5270', '5281', '5282', '530A', '5313', '531A', '5320', '5341', '5364', '5380', '5383'
  );
  
  static_data_id_ubs_cur array_of_integer:=array_of_integer(
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202
  );
  static_data_name_ubs_cur array_of_string:=array_of_string(
    'MYR', 'MZM', 'NAD', 'NGN', 'NIO', 'NOK', 'NPR', 'OMR', 'PAB', 'PEN', 'PHP', 'PKR', 'QAR', 'ROL', 'RUB', 'RUX', 'RWF', 'SBD', 'SCR', 'SDP', 'SGX', 'SHP', 'SIT', 'SLL', 'SOS', 'SRG', 'SVC', 'SYP', 'THB', 'THX', 'TJS', 'TOP', 'TRL', 'TRY', 'TWD', 'TWX', 'TZS', 'UGX', 'USD', 'VEB', 'VUV', 'XAA', 'XAG', 'XAL', 'XAU', 'XCD', 'XDR', 'XNA', 'XOF', 'XPF', 'XPT', 'XRU', 'YER', 'ZMK', 'ZWD', 'NZD', 'PGK', 'PLN', 'RON', 'SAR', 'SDD', 'SGD', 'SKK', 'SRD', 'STD', 'SZL', 'TMM', 'TPE', 'TTD', 'UAH', 'UYU', 'VND', 'WST', 'XAF', 'XB1', 'XCU', 'XH1', 'XIR', 'XJ1', 'XNI', 'XPB', 'XPD', 'XRH', 'XSN', 'XZN', 'YUM', 'ZRN', 'AFA', 'AZM', 'BND', 'BZD', 'CRC', 'ECS', 'GIP', 'HTG', 'JOD', 'KRX', 'LSL', 'MKD', 'MXN', 'PYG', 'SEK', 'TND', 'UZS', 'XH2', 'ZAR', 'AED', 'AFN', 'ALL', 'AMD', 'ANG', 'AOA', 'ARS', 'AUD', 'AWG', 'AZN', 'BAM', 'BBD', 'BDT', 'BGL', 'BGN', 'BHD', 'BIF', 'BMD', 'BOB', 'BRL', 'BRX', 'BSD', 'BTN', 'BWP', 'BYR', 'CAD', 'CDF', 'CHF', 'CLP', 'CNX', 'CNY', 'COP', 'CSD', 'CUP', 'CVE', 'CYP', 'CZK', 'DJF', 'DKK', 'DOP', 'DZD', 'EEK', 'EGP', 'ERN', 'ETB', 'EUR', 'FJD', 'FKP', 'GBP', 'GEL', 'GHC', 'GMD', 'GNF', 'GTQ', 'GWP', 'GYD', 'HKD', 'HNL', 'HRK', 'HUF', 'IDR', 'ILS', 'INR', 'IQD', 'IRR', 'ISK', 'JMD', 'JPY', 'KES', 'KGS', 'KHR', 'KMF', 'KPW', 'KRW', 'KWD', 'KYD', 'KZT', 'LAK', 'LBP', 'LKR', 'LRD', 'LTL', 'LVL', 'LYD', 'MAD', 'MDL', 'MGA', 'MGF', 'MMK', 'MNT', 'MOP', 'MRO', 'MTL', 'MUR', 'MVR', 'MWK'
  );

--------------------------- #end Static Data  ----------------------------------

------------------------- #begin Temp variables --------------------------------
-- declare variables 
  -- id of current record 
  table_id integer:=null;
  
  product_type varchar2(1024):=null;
  trade_reference_number varchar2(1024):=null;
  trade_source_system varchar2(1024):=null;
  group_number varchar2(1024):=null;
  additional_system_trade_ref varchar2(1024):=null;
  client_name varchar2(1024):=null;
  cconsole_code varchar2(1024):=null;
  tics_code varchar2(1024):=null;
  booking_location varchar2(1024):=null;
  trader varchar2(1024):=null;
  marketer varchar2(1024):=null;
  broker varchar2(1024):=null;
  risk_class varchar2(1024):=null;
  ref_entity varchar2(1024):=null;
  instrument_id varchar2(1024):=null;
  fixing_date varchar2(1024):=null;
  ubs_trade_date varchar2(1024):=null;
  ubs_effective_date varchar2(1024):=null;
  ubs_maturality_date varchar2(1024):=null;
  ubs_notional varchar2(1024):=null;
  ubs_currency varchar2(1024):=null;
  client_trade_date varchar2(1024):=null;
  client_effective_date varchar2(1024):=null;
  client_maturity_date varchar2(1024):=null;
  client_notional varchar2(1024):=null;
  client_currency varchar2(1024):=null;
  last_updated varchar2(1024):=null;
  last_updated_by varchar2(1024):=null;

------------------------- #end   Temp variables --------------------------------

-------------------------#begin Utility functions-------------------------------
  /**
  get random integer from array 
  */
  function getRandomFromIntegerArray(number_array in array_of_integer) return integer
  is 
  begin
    return number_array(dbms_random.value(1,number_array.count));
  end;

  /**
  get random string from array 
  */
  function getRandomFromStringArray(string_array in array_of_string) return integer
  is 
  begin
    return string_array(dbms_random.value(1,string_array.count));
  end;

  /**
    get single random value 
  */
  function getSingleStaticData(integer_array in array_of_integer, string_array in array_of_string) return varchar2
  is
    t1 integer;
  begin
    t1:=dbms_random.value(1,integer_array.count);
    return   '<ref_staticdata>' 
           ||'   <id>' || to_char(integer_array(t1)) || '</id>'
           ||'   <name>' || string_array(t1) || '</name>'
					 ||'</ref_staticdata>';
  end;

  /**
    get list of random values 
  */
  function getMultiStaticData(integer_array in array_of_integer, string_array in array_of_string, random_count in integer) return varchar2
  is 
    t1 integer;
    counter integer;
    return_value varchar2(1024):=null;
  begin
    return_value:='<list>';
    for counter in 1..random_count loop
       return_value:=return_value || getSingleStaticData( integer_array, string_array );
    end loop;
    return_value:=return_value||'</list>';
    return return_value;
  end;

  /** 
    create XML element 
  */
  function getXmlElementByName(element_name in varchar2, element_value in varchar2) return varchar2
  is 
  begin
     return '<'||element_name||'>' || element_value || '</'||element_name||'>';
  end;

  /**
    create Random Date between two dates 
  */
  function getRandomDate(begin_date in date, end_date in date) return Date
  is 
  begin
     return TO_DATE( TRUNC(DBMS_RANDOM.VALUE( to_number(to_char(begin_date, 'J')) , 
                                              to_number(to_char(end_date, 'J')) 
                                              )
                           ),
                    'J');
  end;
  
  /**
     create String of random values 
  */
  function getRandomString(string_length in integer) return varchar2
  is
  begin
    return dbms_random.string('A', string_length);
  end;
  
  /**
     create Random as string 
  */
  function getRandomInteger(begin_integer in integer, end_integer in integer) return integer
  is 
  begin
    return to_char(dbms_random.value(begin_integer, end_integer));
  end;

  /**
     create Random digits as string 
  */
  function getRandomDigit(digit_count in integer) return varchar2
  is 
     i integer;
     return_value integer;
  begin
    return_value:=1;
    for i in 1..(digit_count-1) loop
      return_value:=return_value*10;
    end loop;
    return to_char(trunc(dbms_random.value(return_value, return_value*10-1)));
  end;
  
  function dateToChar(current_date in date) return varchar2
  is
  begin
    return to_char(current_date, 'YYYY-MM-DD')||'T'||to_char(current_date, 'HH24:MI:SS')||'Z';
  end;
  
-------------------------#end   Utility functions-------------------------------

--          create String of random values 
-- function getRandomString(string_length in integer) return varchar2
--          create Random Date between two dates 
-- function getRandomDate(begin_date in date, end_date in date) return Date
--          create XML element 
-- function getXmlElementByName(element_name in varchar2, element_value in varchar2) return varchar2
--          get list of random values 
-- function getMultiStaticData(integer_array in array_of_integer, string_array in array_of_string, random_count in integer) return varchar2
--          get single random value 
-- function getSingleStaticData(integer_array in array_of_integer, string_array in array_of_string) return varchar2
--          get random string from array 
-- function getRandomFromStringArray(string_array in array_of_string) return integer
--          get random integer from array 
-- function getRandomFromIntegerArray(number_array in array_of_integer) return integer
--          create Random as string 
-- function getRandomInteger(begin_integer in integer, end_integer in integer) return integer
--          create Random digits as string 
-- function getRandomDigit(digit_count in integer) return integer


begin
  dbms_output.put_line('begin : '|| to_char(sysdate, 'HH24:MI:SS'));
--   dbms_output.put_line('count:  '|| temp_string(dbms_random.value(1,temp_string.count)));
--   dbms_output.put_line('count:  '|| getRandomFromIntegerArray(temp_integer));
--   dbms_output.put_line('count:  '|| getRandomInteger(100, 1000));
--   dbms_output.put_line('count:  '|| getRandomDigit(7));


  -- execute logic 
  for n in 1..table_count loop
    select seq_businessobjects.nextval into table_id from dual; 

    product_type:=getMultiStaticData(static_data_id_product_type, static_data_name_product_type, 3);
      product_type:=getXmlElementByName('product_type', product_type);
    trade_reference_number:=getRandomDigit(11);
      trade_reference_number:=getXmlElementByName('trade_reference_number', trade_reference_number);
    trade_source_system:=getSingleStaticData(static_data_id_trade_source ,static_data_name_trade_source);
      trade_source_system:=getXmlElementByName('trade_source_system', trade_source_system);
    group_number:=to_char(getRandomInteger(1000,1000000));
      group_number:=getXmlElementByName('group_number', group_number);
    additional_system_trade_ref:='';
      additional_system_trade_ref:=getXmlElementByName('additional_system_trade_ref', additional_system_trade_ref);
    cconsole_code:=getRandomDigit(4);
      cconsole_code:=getXmlElementByName('cconsole_code', cconsole_code);
    client_name:='ABC Bank '||cconsole_code;
      client_name:=getXmlElementByName('client_name', client_name);
    tics_code:=getRandomDigit(6);
      tics_code:=getXmlElementByName('tics_code', tics_code);
    booking_location:=getSingleStaticData(static_data_id_booking_loc, static_data_name_booking_loc);
      booking_location:=getXmlElementByName('booking_location', booking_location);
    trader:='trader '||getRandomDigit(2);
      trader:=getXmlElementByName('trader', trader);
    marketer:='marketer '||getRandomDigit(2);
      marketer:=getXmlElementByName('marketer', marketer);
    broker:='broker '||getRandomDigit(2);
      broker:=getXmlElementByName('broker', broker);
    risk_class:=getSingleStaticData(static_data_id_risk_class, static_data_name_risk_class);
      risk_class:=getXmlElementByName('risk_class', risk_class);
    ref_entity:='Ford Motor Company';
      ref_entity:=getXmlElementByName('ref_entity', ref_entity);
    instrument_id:=to_char(getRandomInteger(1000,1000000));
      instrument_id:=getXmlElementByName('instrument_id', instrument_id);
    fixing_date:=dateToChar(getRandomDate(date_begin, date_end));
      fixing_date:=getXmlElementByName('fixing_date', fixing_date);
    ubs_trade_date:=dateToChar(getRandomDate(date_begin, date_end));
      ubs_trade_date:=getXmlElementByName('ubs_trade_date', ubs_trade_date);
    ubs_effective_date:=dateToChar(getRandomDate(date_begin, date_end));
      ubs_effective_date:=getXmlElementByName('ubs_effective_date', ubs_effective_date);
    ubs_maturality_date:=dateToChar(getRandomDate(date_begin, date_end));
      ubs_maturality_date:=getXmlElementByName('ubs_maturality_date', ubs_maturality_date);
    ubs_notional:=getRandomDigit(7);
      ubs_notional:=getXmlElementByName('ubs_notional', ubs_notional);
    ubs_currency:=getSingleStaticData(static_data_id_ubs_cur, static_data_name_ubs_cur);
      ubs_currency:=getXmlElementByName('ubs_currency', ubs_currency);
    client_trade_date:=dateToChar(getRandomDate(date_begin, date_end));
      client_trade_date:=getXmlElementByName('client_trade_date', client_trade_date);
    client_effective_date:=dateToChar(getRandomDate(date_begin, date_end));
      client_trade_date:=getXmlElementByName('client_effective_date', client_effective_date);
    client_maturity_date:=dateToChar(getRandomDate(date_begin, date_end));
      client_maturity_date:=getXmlElementByName('client_maturity_date', client_maturity_date);
    client_notional:=to_char(getRandomInteger(1000,1000000));
      client_notional:=getXmlElementByName('client_notional', client_notional);
    client_currency:=getSingleStaticData(static_data_id_ubs_cur, static_data_name_ubs_cur);
      client_currency:=getXmlElementByName('client_currency', client_currency);
    last_updated:='System';
      last_updated:=getXmlElementByName('last_updated', last_updated);
    last_updated_by:=dateToChar(getRandomDate(date_begin, date_end));
      last_updated_by:=getXmlElementByName('last_updated_by', last_updated_by);
      
    insert into CRMT_BUSINESSOBJECTS ( id, title, type, payload ) 
    values (
      table_id 
     ,'title ' || to_char(table_id)
     ,'GENERIC_XML'
     ,xmltype('<?xml version="1.0" encoding="utf-8" ?>'
              || '<payload>'
              || product_type 
              || trade_reference_number
              || trade_source_system
              || group_number
              || additional_system_trade_ref
              || client_name
              || cconsole_code
              || tics_code
              || booking_location
              || trader
              || marketer
              || broker
              || risk_class 
              || ref_entity
              || instrument_id
              || fixing_date
              || ubs_trade_date
              || ubs_effective_date
              || ubs_maturality_date
              || ubs_notional
              || ubs_currency
              || client_trade_date
              || client_effective_date
              || client_maturity_date
              || client_notional
              || client_currency
              || last_updated
              || last_updated_by
              || '</payload>' 
              )
    );
  -- dbms_output.put_line('' || to_char(n) || ' / ' || to_char(table_count) || '   id: ' || to_char(table_id) );  
  commit;
  end loop;
  dbms_output.put_line('end : '|| to_char(sysdate, 'HH24:MI:SS'));
  
end;

-- select seq_businessobjects.nextval from dual
-- select count(*) from crmt_businessobjects 
-- select to_char(sysdate, 'YYYY-MM-DD')||'T'||to_char(sysdate, 'HH24:MI:SS')||'Z' from dual
-- delete from crmt_businessobjects 