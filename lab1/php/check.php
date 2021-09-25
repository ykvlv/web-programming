<?php

require_once("encoder.php");

function validateX($xVal) {
    return isset($xVal);
}

function validateR($rVal) {
    return isset($rVal);
}

function validateY($yVal) {
    $Y_MIN = -3;
    $Y_MAX = 5;
    if (!isset($yVal)) {
        return false;
    }

    $numY = str_replace(',', '.', $yVal);
    return is_numeric($numY) && $numY >= $Y_MIN && $numY <= $Y_MAX;
}

function validate($xVal, $yVal, $rVal) {
    return validateX($xVal) && validateY($yVal) && validateR($rVal);
}

function checkCircle($xVal, $yVal, $rVal) {
    return $xVal <= 0 && $yVal >= 0 && sqrt($xVal * $xVal + $yVal * $yVal) <= $rVal / 2;
}

function checkRectangle($xVal, $yVal, $rVal) {
    return $xVal >= 0 && $xVal <= $rVal / 2 && $yVal <= 0 && $yVal >= -$rVal;
}

function checkTriangle($xVal, $yVal, $rVal) {
    return $xVal >= 0 && $yVal >= 0 && $yVal + $xVal <= $rVal ;
}

function checkHit($xVal, $yVal, $rVal) {
    return checkCircle($xVal, $yVal, $rVal) || checkRectangle($xVal, $yVal, $rVal) || checkTriangle($xVal, $yVal, $rVal);
}

$xVal = $_GET['x'];
$yVal = $_GET['y'];
$rVal = $_GET['r'];
$timezoneOffset = $_GET['timezone'];
$results = array();

$isValid = validate($xVal, $yVal, $rVal);
$converted_isValid = $isValid ? 'true' : 'false';
$isHit = $isValid ? checkHit($xVal, $yVal, $rVal) : 'Sorry, Bro, try it again later!';
$converted_isHit = $isHit ? 'yes' : 'no';
$currentTime = date('H:i:s', time() - $timezoneOffset * 60);
$executionTime = round(microtime(true) - $_SERVER['REQUEST_TIME_FLOAT'], 7);
array_push($results, array(
    "validate" => $isValid,
    "xVal" => $xVal,
    "yVal" => $yVal,
    "rVal" => $rVal,
    "curtime" => $currentTime,
    "exectime" => $executionTime,
    "hitres" => $converted_isHit
));

echo toJSON($results);
