<?php

function validateX($x) {
    $possible_values = array(-4, -3, -2, -1, 0, 1, 2, 3, 4);
    return isset($x) && in_array($x, $possible_values);
}

function validateR($r) {
    $possible_values = array(1, 1.5, 2, 2.5, 3);
    return isset($r) && in_array($r, $possible_values);
}

function validateY($y) {
    $Y_MAX_LENGTH = 15;
    $Y_MIN = -3;
    $Y_MAX = 5;

    return isset($y) && is_numeric($y) && strlen($y) < $Y_MAX_LENGTH && $y >= $Y_MIN && $y <= $Y_MAX;
}

function validate($x, $y, $r) {
    return validateX($x) && validateY($y) && validateR($r);
}

function validateTimezone($timezoneOffset) {
    return isset($timezoneOffset) && is_numeric($timezoneOffset) && $timezoneOffset % 60 === 0;
}

function checkCircle($x, $y, $r) {
    return $x <= 0 && $y >= 0 && sqrt($x * $x + $y * $y) <= $r / 2;
}

function checkRectangle($x, $y, $r) {
    return $x >= 0 && $x <= $r / 2 && $y <= 0 && $y >= -$r;
}

function checkTriangle($x, $y, $r) {
    return $x >= 0 && $y >= 0 && $y + $x <= $r ;
}

function checkHit($x, $y, $r) {
    return checkCircle($x, $y, $r) || checkRectangle($x, $y, $r) || checkTriangle($x, $y, $r);
}

function check() {
    $x = $_GET['x'];
    $y = $_GET['y'];
    $r = $_GET['r'];
    $timezoneOffset = $_GET['tz'];
    date_default_timezone_set(timezone_name_from_abbr("UTC"));

    if (validate($x, $y, $r) && validateTimezone($timezoneOffset)) {
        $isHit = checkHit($x, $y, $r);
        $currentTime = date('H:i:s', time() - $timezoneOffset * 60);
        $executionTime = round(microtime(true) - $_SERVER["REQUEST_TIME_FLOAT"], 7);
        return "<validate>" . "true" . "</validate>\n" .
            "<x>" . $x . "</x>\n" .
            "<y>" . $y . "</y>\n" .
            "<r>" . $r . "</r>\n" .
            "<currentTime>" . $currentTime  . "</currentTime>\n" .
            "<executionTime>" . $executionTime . "</executionTime>\n" .
            "<hitRes>" . ($isHit ? "yes" : "no") . "</hitRes>\n";
    }
    return "<validate>" . "false" . "</validate>";
}

echo check();