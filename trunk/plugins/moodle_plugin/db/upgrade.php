<?php  //$Id: upgrade.php,v 1.0 2008/05/12 12:00:00 Sebastian Wagner Exp $

function xmldb_openmeetings_upgrade($oldversion) {

    global $CFG, $DB, $OUTPUT;
    
    $dbman = $DB->get_manager();

    $result = true;
    
    if ($oldversion < 20111001) {
    
    	// Define field allow_recording to be added to openmeetings
    	$table = new xmldb_table('openmeetings');
    	$field = new xmldb_field('allow_recording', XMLDB_TYPE_INTEGER, '10', XMLDB_UNSIGNED, XMLDB_NOTNULL, null, '1', 'room_recording_id');
    
    	// Conditionally launch add field allow_recording
    	if (!$dbman->field_exists($table, $field)) {
    		$dbman->add_field($table, $field);
    	}
    
    	// openmeetings savepoint reached
    	upgrade_mod_savepoint(true, 20111001, 'openmeetings');
    }
    
    if ($oldversion < 20111002) {
    
    	// Define field introformat to be dropped from openmeetings
    	$table = new xmldb_table('openmeetings');
    	$field = new xmldb_field('introformat');
    
    	// Conditionally launch drop field introformat
    	if ($dbman->field_exists($table, $field)) {
    		$dbman->drop_field($table, $field);
    	}
    
    	// openmeetings savepoint reached
    	upgrade_mod_savepoint(true, 20111002, 'openmeetings');
    }
    
    if ($oldversion == 20111002) {
    	$result = false;
    }
    
    return $result;
}



?>
