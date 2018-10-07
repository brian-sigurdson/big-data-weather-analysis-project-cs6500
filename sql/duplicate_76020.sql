SELECT * -- usaf, yyyymmddhhmm, temps
/*
FROM 
(
	SELECT a.usaf, a.yyyymmddhhmm, a.temps
	FROM "CS6500".all_years_reduced a
) sub
*/
FROM "CS6500".all_years_reduced
where usaf = 76020
order by temps, yyyymmddhhmm
;
