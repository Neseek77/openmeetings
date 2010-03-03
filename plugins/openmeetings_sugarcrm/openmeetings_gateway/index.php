<?php // $Id: index.php,v 1.0 2008/05/12 12:00:00 Sebastian Wagner Exp $
/**
 * This page lists all the instances of openmeetings in a particular course
 *
 * @author Sebastian Wagner
 * @version 
 * @package openmeetings
 **/

/// Replace openmeetings with the name of your module

    require_once("../../config.php");
    require_once("lib.php");

    $id = required_param('id', PARAM_INT);   // course

    if (! $course = get_record("course", "id", $id)) {
        error("Course ID is incorrect");
    }

    require_login($course->id);

    add_to_log($course->id, "openmeetings", "view all", "index.php?id=$course->id", "");


/// Get all required stringsopenmeetings

    $stropenmeetings = get_string("modulenameplural", "openmeetings");
    $stropenmeetings  = get_string("modulename", "openmeetings");


/// Print the header

    if ($course->category) {
        $navigation = "<a href=\"../../course/view.php?id=$course->id\">$course->shortname</a> ->";
    } else {
        $navigation = '';
    }

    print_header("$course->shortname: $stropenmeetings", "$course->fullname", "$navigation $stropenmeetings", "", "", true, "", navmenu($course));

/// Get all the appropriate data

    if (! $openmeetings = get_all_instances_in_course("openmeetings", $course)) {
        notice("There are no openmeetings", "../../course/view.php?id=$course->id");
        die;
    }

/// Print the list of instances (your module will probably extend this)

    $timenow = time();
    $strname  = get_string("name");
    $strweek  = get_string("week");
    $strtopic  = get_string("topic");

    if ($course->format == "weeks") {
        $table->head  = array ($strweek, $strname);
        $table->align = array ("center", "left");
    } else if ($course->format == "topics") {
        $table->head  = array ($strtopic, $strname);
        $table->align = array ("center", "left", "left", "left");
    } else {
        $table->head  = array ($strname);
        $table->align = array ("left", "left", "left");
    }

    foreach ($openmeetings as $openmeetings) {
        if (!$openmeetings->visible) {
            //Show dimmed if the mod is hidden
            $link = "<a class=\"dimmed\" href=\"view.php?id=$openmeetings->coursemodule\">$openmeetings->name</a>";
        } else {
            //Show normal if the mod is visible
            $link = "<a href=\"view.php?id=$openmeetings->coursemodule\">$openmeetings->name</a>";
        }

        if ($course->format == "weeks" or $course->format == "topics") {
            $table->data[] = array ($openmeetings->section, $link);
        } else {
            $table->data[] = array ($link);
        }
    }

    echo "<br />";

    print_table($table);

/// Finish the page

    print_footer($course);

?>