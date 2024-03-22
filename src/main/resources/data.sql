-- 장르 데이터 추가
delete from TB_ART_GENRE_LIST;

insert into TB_ART_GENRE_LIST (ART_GENRE_ID, ART_GENRE_NM) values (1, '로맨스');
insert into TB_ART_GENRE_LIST (ART_GENRE_ID, ART_GENRE_NM) values (2, '코미디');
insert into TB_ART_GENRE_LIST (ART_GENRE_ID, ART_GENRE_NM) values (3, '공포');
insert into TB_ART_GENRE_LIST (ART_GENRE_ID, ART_GENRE_NM) values (4, '추리');
insert into TB_ART_GENRE_LIST (ART_GENRE_ID, ART_GENRE_NM) values (5, '스릴러');
insert into TB_ART_GENRE_LIST (ART_GENRE_ID, ART_GENRE_NM) values (6, '고전');
insert into TB_ART_GENRE_LIST (ART_GENRE_ID, ART_GENRE_NM) values (7, '드라마');
insert into TB_ART_GENRE_LIST (ART_GENRE_ID, ART_GENRE_NM) values (8, '어린이');
insert into TB_ART_GENRE_LIST (ART_GENRE_ID, ART_GENRE_NM) values (9, '전체');

-- 지역 데이터 추가
delete from TB_ART_AREA;

insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('00', '전체');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('UNI', '대학로');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('11', '서울');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('41', '경기');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('43', '충청');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('47', '경상');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('45', '전라');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('51', '강원');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('50', '제주');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('28', '인천');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('30', '대전');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('27', '대구');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('29', '광주');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('26', '부산');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('31', '울산');
insert into TB_ART_AREA (AREA_CODE, AREA_NM) values ('36', '세종');