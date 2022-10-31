SELECT
	pm.id,
	pm.category,
	pm.group_id,
	pm.name,
	pm.price,
	pm.color,
	pm.size,
	pim.id AS img_id,
	pim.product_id,
	pim.temp_name
FROM
	product_mst pm
	LEFT OUTER JOIN product_img pim ON (pim.product_id = (select
																				id
																			FROM 
																				product_mst
																			where
																				group_id = 15
																			LIMIT 1))
WHERE
	group_id = 15;
ORDER BY 
	id;