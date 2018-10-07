/*CREATE TEMP TABLE mytable AS
SELECT *
FROM orig_table;
*/

--delete from "CS6500".task03_a;

--/*
CREATE TABLE "CS6500".task03_a AS
SELECT usaf, extract(year from yyyymmddhhmm) as theyear, temps
FROM "CS6500".all_years_reduced
where temps is not null
--group by usaf, theyear, temps
--order by usaf, theyear, temps
--limit 500
;
--*/