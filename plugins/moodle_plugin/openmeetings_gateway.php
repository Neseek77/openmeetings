<?php  // $Id: lib.php,v 1.0 2008/05/14 12:00:00 Sebastian Wagner Exp $
/*
 * Created on 13.05.2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 * 
 * Sebastian Wagner
 */
 
// in serializeType: name=parameters, type=http://services.axis.openmeetings.org:loginUser^, use=literal, encodingStyle=, unqualified=qualified
// in serializeType: name=parameters, type=http://services.axis.openmeetings.org:loginUser^, use=literal, encodingStyle=, unqualified=qualified


//<loginUser xmlns=http://services.axis.openmeetings.org><SID>dad083bbeb46d077e3748d40d11a28fe</SID><username>swagner</username><userpass>asdasd</userpass></loginUser>
//<loginUser xmlns="http://services.axis.openmeetings.org"><SID>93e7e34036e1b0a243a481c12bd4fb7d</SID><username>SebastianWagner</username><userpass>asdasd</userpass></loginUser>
//addRoom xmlns="http://services.axis.openmeetings.org"><SID>e4477f29c7c99bca7768c06a75e35cdc</SID><name>MOODLE_COURSE_ID_6_NAME_asdasdasd</name><roomtypes_id>1</roomtypes_id><comment>Created by SOAP-Gateway for Moodle Platform</comment><numberOfPartizipants>4</numberOfPartizipants><ispublic>true</ispublic><videoPodWidth>270</videoPodWidth><videoPodHeight>280</videoPodHeight><videoPodXPosition>2</videoPodXPosition><videoPodYPosition>2</videoPodYPosition><moderationPanelXPosition>400</moderationPanelXPosition><showWhiteBoard>true</showWhiteBoard><whiteBoardPanelXPosition>276</whiteBoardPanelXPosition><whiteBoardPanelYPosition>2</whiteBoardPanelYPosition><whiteBoardPanelHeight>592</whiteBoardPanelHeight><whiteBoardPanelWidth>660</whiteBoardPanelWidth><showFilesPanel>true</showFilesPanel><filesPanelXPosition>2</filesPanelXPosition><filesPanelYPosition>284</filesPanelYPosition><filesPanelHeight>310</filesPanelHeight><filesPanelWidth>270</filesPanelWidth></addRoom>

//echo "DIRROOT: ".$CFG->dirroot."<br/>";

require_once($CFG->dirroot.'/mod/openmeetings/lib/nusoap.php');
//require_once($CFG->dirroot.'/lib/soaplib.php');

class openmeetings_gateway {
	
	var $session_id = "";

	/**
	 * TODO: Get Error Service and show detailed Error Message
	 */
	function openmeetings_loginuser() {
		global $USER, $CFG;
	
		//		echo $CFG->openmeetings_red5host."<br/>";
		//		echo $CFG->openmeetings_red5port."<br/>";	
		//		
		//		echo "USER: ".$CFG->openmeetings_openmeetingsAdminUser."<br/>";
		//		echo "Pass: ".$CFG->openmeetings_openmeetingsAdminUserPass."<br/>";
		
		//echo "DIRROOT: ".$CFG->dirroot."<br/>";
		
		$client_userService = new nusoap_client_om("http://".$CFG->openmeetings_red5host.":".$CFG->openmeetings_red5port."/openmeetings/services/UserService?wsdl", "wsdl");
		$client_userService->setUseCurl(true);
		//echo "Client inited"."<br/>";
		$err = $client_userService->getError();
		if ($err) {
			echo '<h2>Constructor error</h2><pre>' . $err . '</pre>';
			echo '<h2>Debug</h2><pre>' . htmlspecialchars($client->getDebug(), ENT_QUOTES) . '</pre>';
			exit();
		}  
	
		$result = $client_userService->call('getSession');
		if ($client_userService->fault) {
			echo '<h2>Fault (Expect - The request contains an invalid SOAP body)</h2><pre>'; print_r($result); echo '</pre>';
		} else {
			$err = $client_userService->getError();
			if ($err) {
				echo '<h2>Error</h2><pre>' . $err . '</pre>';
			} else {
				//echo '<h2>Result</h2><pre>'; print_r($result); echo '</pre>';
				$this->session_id = $result["return"]["session_id"];
				//echo '<h2>Result</h2><pre>'; printf(); echo '</pre>';
				$params = array(
	    			'SID' => $this->session_id,
	    			'username' => $CFG->openmeetings_openmeetingsAdminUser,
	    			'userpass' => $CFG->openmeetings_openmeetingsAdminUserPass
				);
				
				//$params = array();
				
				$result = $client_userService->call('loginUser',$params);
				//echo '<h2>Params</h2><pre>'; print_r($params); echo '</pre>';
				if ($client_userService->fault) {
					echo '<h2>Fault (Expect - The request contains an invalid SOAP body)</h2><pre>'; print_r($result); echo '</pre>';
				} else {
					$err = $client_userService->getError();
					if ($err) {
						echo '<h2>Error</h2><pre>' . $err . '</pre>';
					} else {
						//echo '<h2>Result</h2><pre>'; print_r($result); echo '</pre>';
						$returnValue = $result["return"];	
						//echo '<h2>returnValue</h2><pre>'; printf($returnValue); echo '</pre>';		
					}
				}
			}
		}   
		if ($returnValue>0){
	    	return true;
		} else {
			return false;
		}
	}

	/**
	 * TODO: Check Error handling
	 * 
	 * @deprecated this method is deprecated
	 * 
	 */
	function openmeetings_createroom($openmeetings,$roomtypes_id) {
		global $USER, $CFG;
	
		//		echo $CFG->openmeetings_red5host."<br/>";
		//		echo $CFG->openmeetings_red5port."<br/>";	
		//		foreach ($CFG as $key => $value){
		//    		echo "KEY: ".$key." Value: ".$value."<br/>";
		//    	}
    	$course_name = 'MOODLE_COURSE_ID_'.$openmeetings->course.'_NAME_'.$openmeetings->name;
    	//echo "CourseName: ".$course_name."<br/>";	
		
		//echo $client_userService."<br/>";
	    
	 	$client_roomService = new nusoap_client_om("http://".$CFG->openmeetings_red5host.":".$CFG->openmeetings_red5port."/openmeetings/services/RoomService?wsdl", true);
		
		$err = $client_roomService->getError();
		if ($err) {
			echo '<h2>Constructor error</h2><pre>' . $err . '</pre>';
			echo '<h2>Debug</h2><pre>' . htmlspecialchars($client->getDebug(), ENT_QUOTES) . '</pre>';
			exit();
		}  
		$params = array(
			'SID' => $this->session_id,
			'name' => $course_name,
			'roomtypes_id' => $roomtypes_id,
			'comment' => 'Created by SOAP-Gateway for Moodle Platform',
			'numberOfPartizipants' => 4,
			'ispublic' => true,
			'videoPodWidth' => 270, 
			'videoPodHeight' => 280,
			'videoPodXPosition' => 2, 
			'videoPodYPosition' => 2, 
			'moderationPanelXPosition' => 400, 
			'showWhiteBoard' => true, 
			'whiteBoardPanelXPosition' => 276, 
			'whiteBoardPanelYPosition' => 2, 
			'whiteBoardPanelHeight' => 592, 
			'whiteBoardPanelWidth' => 660, 
			'showFilesPanel' => true, 
			'filesPanelXPosition' => 2, 
			'filesPanelYPosition' => 284, 
			'filesPanelHeight' => 310, 
			'filesPanelWidth' => 270
		);
		$result = $client_roomService->call('addRoom',$params);
		if ($client_roomService->fault) {
			echo '<h2>Fault (Expect - The request contains an invalid SOAP body)</h2><pre>'; print_r($result); echo '</pre>';
		} else {
			$err = $client_roomService->getError();
			if ($err) {
				echo '<h2>Error</h2><pre>' . $err . '</pre>';
			} else {
				//echo '<h2>Result</h2><pre>'; print_r($result["return"]); echo '</pre>';
				return $result["return"];
			}
		}   
		return -1;
	}
	
	function openmeetings_createroomwithmod($openmeetings) {
		global $USER, $CFG;
	
		//		echo $CFG->openmeetings_red5host."<br/>";
		//		echo $CFG->openmeetings_red5port."<br/>";	
		//		foreach ($CFG as $key => $value){
		//    		echo "KEY: ".$key." Value: ".$value."<br/>";
		//    	}
    	$course_name = 'MOODLE_COURSE_ID_'.$openmeetings->course.'_NAME_'.$openmeetings->name;
    	//echo "CourseName: ".$course_name."<br/>";	
		
		//echo $client_userService."<br/>";
	    
	 	$client_roomService = new nusoap_client_om("http://".$CFG->openmeetings_red5host.":".$CFG->openmeetings_red5port."/openmeetings/services/RoomService?wsdl", true);
		
		$err = $client_roomService->getError();
		if ($err) {
			echo '<h2>Constructor error</h2><pre>' . $err . '</pre>';
			echo '<h2>Debug</h2><pre>' . htmlspecialchars($client->getDebug(), ENT_QUOTES) . '</pre>';
			exit();
		}  
		
		$isModeratedRoom = false;
		if ($openmeetings->is_moderated_room == 1) {
			$isModeratedRoom = true;
		}
		
		$params = array(
			'SID' => $this->session_id,
			'name' => $course_name,
			'roomtypes_id' => $openmeetings->type,
			'comment' => 'Created by SOAP-Gateway for Moodle Platform',
			'numberOfPartizipants' => $openmeetings->max_user,
			'ispublic' => true,
			'appointment' => false, 
			'isDemoRoom' => false, 
			'demoTime' => 0, 
			'isModeratedRoom' => $isModeratedRoom
		);
		$result = $client_roomService->call('addRoomWithModeration',$params);
		if ($client_roomService->fault) {
			echo '<h2>Fault (Expect - The request contains an invalid SOAP body)</h2><pre>'; print_r($result); echo '</pre>';
		} else {
			$err = $client_roomService->getError();
			if ($err) {
				echo '<h2>Error</h2><pre>' . $err . '</pre>';
			} else {
				//echo '<h2>Result</h2><pre>'; print_r($result["return"]); echo '</pre>';
				return $result["return"];
			}
		}   
		return -1;
	}
	
	/*
	 * Usage if this Method will work if you have no need to simulate always the same user in 
	 * OpenMeetings, if you want to do this check the next method that also remembers the 
	 * ID of the external User
	 * 
	 * 
	 */
	function openmeetings_setUserObject($username, $firstname, $lastname, 
			$profilePictureUrl, $email) {
	    global $USER, $CFG;
	 	$client_userService = new nusoap_client_om("http://".$CFG->openmeetings_red5host.":".$CFG->openmeetings_red5port."/openmeetings/services/UserService?wsdl", true);
		
		$err = $client_userService->getError();
		if ($err) {
			echo '<h2>Constructor error</h2><pre>' . $err . '</pre>';
			echo '<h2>Debug</h2><pre>' . htmlspecialchars($client->getDebug(), ENT_QUOTES) . '</pre>';
			exit();
		}  
		$params = array(
			'SID' => $this->session_id,
			'username' => $username,
			'firstname' => $firstname,
			'lastname' => $lastname,
			'profilePictureUrl' => $profilePictureUrl,
			'email' => $email
		);
		$result = $client_userService->call('setUserObject',$params);
		if ($client_roomService->fault) {
			echo '<h2>Fault (Expect - The request contains an invalid SOAP body)</h2><pre>'; print_r($result); echo '</pre>';
		} else {
			$err = $client_userService->getError();
			if ($err) {
				echo '<h2>Error</h2><pre>' . $err . '</pre>';
			} else {
				//echo '<h2>Result</h2><pre>'; print_r($result["return"]); echo '</pre>';
				return $result["return"];
			}
		}   
		return -1;
	}
	
	/**
	 * Sets the User Id and remembers the User, 
	 * the value for $systemType is any Flag but usually should always be the same, 
	 * it only has a reason if you have more then one external Systems, so the $userId will not 
	 * be unique, then you can use the $systemType to give each system its own scope
	 * 
	 * so a unique external user is always the pair of: $userId + $systemType
	 * 
	 * in this case the $systemType is 'moodle'
	 * 
	 */
	function openmeetings_setUserObjectWithExternalUser($username, $firstname, $lastname, 
			$profilePictureUrl, $email, $userId, $systemType) {
	    global $USER, $CFG;
	 	$client_userService = new nusoap_client_om("http://".$CFG->openmeetings_red5host.":".$CFG->openmeetings_red5port."/openmeetings/services/UserService?wsdl", true);
		
		$err = $client_userService->getError();
		if ($err) {
			echo '<h2>Constructor error</h2><pre>' . $err . '</pre>';
			echo '<h2>Debug</h2><pre>' . htmlspecialchars($client->getDebug(), ENT_QUOTES) . '</pre>';
			exit();
		}  
		$params = array(
			'SID' => $this->session_id,
			'username' => $username,
			'firstname' => $firstname,
			'lastname' => $lastname,
			'profilePictureUrl' => $profilePictureUrl,
			'email' => $email,
			'externalUserId' => $userId,
			'externalUserType' => $systemType
		);
		$result = $client_userService->call('setUserObjectWithExternalUser',$params);
		if ($client_roomService->fault) {
			echo '<h2>Fault (Expect - The request contains an invalid SOAP body)</h2><pre>'; print_r($result); echo '</pre>';
		} else {
			$err = $client_userService->getError();
			if ($err) {
				echo '<h2>Error</h2><pre>' . $err . '</pre>';
			} else {
				//echo '<h2>Result</h2><pre>'; print_r($result["return"]); echo '</pre>';
				return $result["return"];
			}
		}   
		return -1;
	}
}

?>
