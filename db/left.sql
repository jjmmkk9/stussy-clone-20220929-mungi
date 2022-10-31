SELECT
	um.user_code,
	um.user_id,
	um.user_password,
	um.user_name,
	um.user_email,
	ud.user_phone,
	ud.user_address
FROM
	user_mst um
	LEFT OUTER JOIN user_dtl ud ON(ud.user_code = um.user_code);
	
	
SELECT 
	um.user_code,
	um.user_id,
	um.user_password,
	um.user_name,
	um.user_email,
	
	ud.user_phone,
	ud.user_address
	FROM 
		user_mst um
		LEFT OUTER JOIN user_dtl ud ON(ud.user_code = um.user_code)
	WHERE 
		um.user_id = 'mungi5'user_mst