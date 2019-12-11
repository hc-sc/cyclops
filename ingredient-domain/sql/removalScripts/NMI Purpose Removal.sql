-- --------------------
-- Removes all NMI purposes for an ingredient
-- Author: Mathieu Dugré
-- Date: 2012/04/17
-- JH:2012/12/06: seems to assume there is ONLY an NMI role.
-- --------------------


select *
from ingredients
where ingred_id = 4145;

select *
from INGREDIENT_ROLES
where ingred_id = 4145;

select * 
from INGREDIENT_ROLE_PURPOSES
where ingredrole_id = 8464;

delete from ingredient_role_purposes
where ingredrole_id in (
	select ingredrole_id
	from INGREDIENT_ROLES
	where ingred_id = 4145
);