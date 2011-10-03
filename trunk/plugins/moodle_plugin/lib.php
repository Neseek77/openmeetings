<?php  // $Id: lib.php,v 1.0 2008/05/14 12:00:00 Sebastian Wagner Exp $

$old_error_handler = set_error_handler("myErrorHandler");
require_once($CFG->dirroot.'/config.php');
require_once($CFG->dirroot.'/mod/openmeetings/openmeetings_gateway.php');

//include('../mod/openmeetings/lib/nusoap.php');
// error handler function
function myErrorHandler($errno, $errstr, $errfile, $errline)
{
    switch ($errno) {
    case E_USER_ERROR:
        echo "<b>My ERROR</b> [$errno] $errstr<br />\n";
        echo "  Fatal error on line $errline in file $errfile";
        echo ", PHP " . PHP_VERSION . " (" . PHP_OS . ")<br />\n";
        echo "Aborting...<br />\n";
        exit(1);
        break;

    case E_USER_WARNING:
        echo "<b>My WARNING</b> [$errno] $errstr<br />\n";
        break;

    case E_USER_NOTICE:
        echo "<b>My NOTICE</b> [$errno] $errstr<br />\n";
        break;

    default:
        //echo "Unknown error type: [$errno] $errstr<br />\n";
        break;
    }

    /* Don't execute PHP internal error handler */
    return true;
}

function openmeetings_add_instance($openmeetings) {
	global $USER, $CFG, $DB;
	
	$openmeetings_gateway = new openmeetings_gateway();
	if ($openmeetings_gateway->openmeetings_loginuser()) {
		
		//Roomtype 0 means its and recording, we don't need to create a room for that
		if ($openmeetings->type != 0) {
			$openmeetings->room_id = $openmeetings_gateway->openmeetings_createRoomWithModAndType($openmeetings);
		}
		
	} else {
		echo "Could not login User to OpenMeetings, check your OpenMeetings Module Configuration";
		exit();
	}

    # May have to add extra stuff in here #
    return $DB->insert_record("openmeetings", $openmeetings);
}


function openmeetings_update_instance($openmeetings) {
	global $DB;
	
    $openmeetings->timemodified = time();
    $openmeetings->id = $openmeetings->instance;

	$openmeetings_gateway = new openmeetings_gateway();
	if ($openmeetings_gateway->openmeetings_loginuser()) {
		
		//Roomtype 0 means its and recording, we don't need to update a room for that
		if ($openmeetings->type != 0) {
			$openmeetings->room_id = $openmeetings_gateway->openmeetings_updateRoomWithModeration($openmeetings);
		}
		
	} else {
		echo "Could not login User to OpenMeetings, check your OpenMeetings Module Configuration";
		exit();
	}

    # May have to add extra stuff in here #
    return $DB->update_record("openmeetings", $openmeetings);
}


function openmeetings_delete_instance($id) {
	global $DB;
	
    if (! $openmeetings = $DB->get_record("openmeetings", array("id"=>"$id"))) {
        return false;
    }
    

    $result = true;
    
    $openmeetings_gateway = new openmeetings_gateway();
	if ($openmeetings_gateway->openmeetings_loginuser()) {
		
		//Roomtype 0 means its and recording, we don't need to update a room for that
		if ($openmeetings->type != 0) {
			$openmeetings->room_id = $openmeetings_gateway->openmeetings_deleteRoom($openmeetings);
		}
		
	} else {
		echo "Could not login User to OpenMeetings, check your OpenMeetings Module Configuration";
		exit();
	}
	
    # Delete any dependent records here #

    if (! $DB->delete_records("openmeetings", array("id"=>"$openmeetings->id"))) {
        $result = false;
    }

    return $result;
    
}


function openmeetings_user_outline($course, $user, $mod, $openmeetings) {
    return $return;
}


function openmeetings_user_complete($course, $user, $mod, $openmeetings) {
    return true;
}


function openmeetings_print_recent_activity($course, $isteacher, $timestart) {
    global $CFG;

    return false;  //  True if anything was printed, otherwise false 
}


function openmeetings_cron () {
    global $CFG;

    return true;
}


function openmeetings_grades($openmeetingsid) {
   return NULL;
}


function openmeetings_get_participants($openmeetingsid) {
    return false;
}

function openmeetings_scale_used ($openmeetingsid,$scaleid) {
    $return = false;

    return $return;
}

?>