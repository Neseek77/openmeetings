<?php // $Id: index.php,v 1.0 2008/05/12 12:00:00 Sebastian Wagner Exp $
/**
 * This page lists all the instances of openmeetingsaudience in a particular course
 *
 * @author Sebastian Wagner
 * @version 
 * @package openmeetingsaudience
 **/

/// Replace openmeetingsaudience with the name of your module

    require_once("../../config.php");
    require_once("lib.php");

    $id = required_param('id', PARAM_INT);   // course

    if (! $course = get_record("course", "id", $id)) {
        error("Course ID is incorrect");
    }

    require_login($course->id);

    add_to_log($course->id, "openmeetingsaudience", "view all", "index.php?id=$course->id", "");


/// Get all required stringsopenmeetingsaudience

    $stropenmeetingsaudience = get_string("modulenameplural", "openmeetingsaudience");
    $stropenmeetingsaudience  = get_string("modulename", "openmeetingsaudience");


/// Print the header

    if ($course->category) {
        $navigation = "<a href=\"../../course/view.php?id=$course->id\">$course->shortname</a> ->";
    } else {
        $navigation = '';
    }

    print_header("$course->shortname: $stropenmeetingsaudience", "$course->fullname", "$navigation $stropenmeetingsaudience", "", "", true, "", navmenu($course));

/// Get all the appropriate data

    if (! $openmeetingsaudience = get_all_instances_in_course("openmeetingsaudience", $course)) {
        notice("There are no openmeetingsaudience", "../../course/view.php?id=$course->id");
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

    foreach ($openmeetingsaudience as $openmeetingsaudience) {
        if (!$openmeetingsaudience->visible) {
            //Show dimmed if the mod is hidden
            $link = "<a class=\"dimmed\" href=\"view.php?id=$openmeetingsaudience->coursemodule\">$openmeetingsaudience->name</a>";
        } else {
            //Show normal if the mod is visible
            $link = "<a href=\"view.php?id=$openmeetingsaudience->coursemodule\">$openmeetingsaudience->name</a>";
        }

        if ($course->format == "weeks" or $course->format == "topics") {
            $table->data[] = array ($openmeetingsaudience->section, $link);
        } else {
            $table->data[] = array ($link);
        }
    }

    echo "<br />";

    print_table($table);

/// Finish the page

    print_footer($course);

?>