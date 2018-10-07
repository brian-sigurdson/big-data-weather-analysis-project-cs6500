/* I tired of working with sub-select queries and simply created two views
from the queries Sean helped me write and joined on usaf.

now the output can be dumped into excel and should only require a couple sorts to answer parts 
*/

SELECT nya.usaf, nya.numYearsActive, ya.theYear
FROM "CS6500".v_usaf_num_years_active nya, "CS6500".v_usaf_year_active ya
where nya.usaf = ya.usaf and nya.usaf in
(
select b.usaf
from "CS6500".v_task02_b b
)

