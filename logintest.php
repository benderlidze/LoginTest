<?

include_once('db_connect.php');

if ( ($_GET['name'])!="" && isset($_GET['signin']) )
{
	
	$name = mysql_real_escape_string($_GET['name']);
	$email = mysql_real_escape_string($_GET['email']);
	$phone = mysql_real_escape_string($_GET['phone']);
	$spinner = mysql_real_escape_string($_GET['spinner']);
	
	$q='select * from login
		where name="'.$name.'" 
		limit 1';
	$result = mysql_query($q);
	if (mysql_num_rows($result)>0)
	{
		$resp ='error_name';
		
	}else{
						
		$query='
			INSERT INTO 
			login
			set
			name="'.$name.'",
			email="'.$email.'",
			phone="'.$phone.'",
			spinner="'.$spinner.'"
		';
		mysql_query($query);
		
		$resp = 'data_saved';
	} 
	
}

if ( ($_GET['name'])!="" && ($_GET['email'])!="" && isset($_GET['login']) )
{
	$name = mysql_real_escape_string($_GET['name']);
	$email = mysql_real_escape_string($_GET['email']);
	
	$q='select * from login
		where name="'.$name.'" 
		limit 1';
	$result = mysql_query($q);
	if (mysql_num_rows($result)>0)
	{
		$resp ='success_login';
	}else{
		$resp ='error_login';
	}	
}

if ( ($_GET['email'])!="" && isset($_GET['forget']) )
{
	$email = mysql_real_escape_string($_GET['email']);
	
	$q='select * from login
		where email="'.$email.'" 
		limit 1';
	$result = mysql_query($q);
	if (mysql_num_rows($result)>0)
	{
		while( $row=mysql_fetch_object( $result ) )
				{
					$resp ='success_forget';
					mail($row->email, 'Your name(password)', 'Your name(password):'.$row->name);
				}
		
	}else{
		$resp ='error_forget';
	}	
}

echo json_encode($resp);
?>
