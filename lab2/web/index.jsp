<%@ page contentType="text/html;charset=UTF-8" %>
<% %>
<html lang="ru">
<head>
  <link rel="icon" href="img/favicon.ico" type="image/x-icon">
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="css/style.css" media="all">
  <link rel="stylesheet" type="text/css" href="css/table_style.css" media="all">
  <title>Лабораторная 1</title>
</head>
<body>
<header>
  <div class="container menu">
    <img id="logo" class="float_left" src="img/logo.png" alt="Логотип">
    <div id="author" class="float_right">
      ЯКОВЛЕВ ГРИГОРИЙ P3213
    </div>
    <div class="clear"></div>
  </div>
</header>
<img id="background" src="img/back.png" alt="Зима">
<div class="container bordered" id="main">
  <h2 style="text-align: center" >Лабораторная 1. Чекер попадания. Вариант 13214</h2>
  <div class="container">
    <div class="container bordered double_element float_left">
      <div class="container">
        <label for="valueX">Выберите X:</label><br>
        <input class="input_style" type="button" name="x" value="-4">
        <input class="input_style" type="button" name="x" value="-3">
        <input class="input_style" type="button" name="x" value="-2">
        <input class="input_style" type="button" name="x" value="-1">
        <input class="input_style" type="button" name="x" value="0" id="valueX">
        <input class="input_style" type="button" name="x" value="1">
        <input class="input_style" type="button" name="x" value="2">
        <input class="input_style" type="button" name="x" value="3">
        <input class="input_style" type="button" name="x" value="4"><br>
        <span class="error" id="error_X"></span>
      </div>
      <div class="container">
        <label for="valueY">Введите Y:</label><br>
        <input name="y" type="text" id="valueY" class="input_style"><br>
        <span class="error" id="error_Y"></span>
      </div>
      <div class="container">
        <label for="valueR">Выберите R:</label><br>
        <input class="input_style" type="button" name="r" value="1" id="valueR">
        <input class="input_style" type="button" name="r" value="2">
        <input class="input_style" type="button" name="r" value="3">
        <input class="input_style" type="button" name="r" value="4">
        <input class="input_style" type="button" name="r" value="5"><br>
        <span class="error" id="error_R"></span>
      </div>
      <div class="container">
        <input type="button" class="input_style" value="Отправить" onclick="addHit()">
        <button type="button" class="input_style" onclick="
        //ОЧИСТКА ТАБЛИЦЫ">Очистить</button>
      </div>
    </div>
    <div class="container bordered double_element float_right">
      <img src="img/area.png" alt="Границы">
    </div>
    <div class="clear"></div>
  </div>
  <div class="container">
    <div class="container bordered">
      <table id="result_table">
        <tr>
          <th>X</th>
          <th>Y</th>
          <th>R</th>
          <th>Current time</th>
          <th>Execution time</th>
          <th>Hit result</th>
        </tr>
      </table>
    </div>
  </div>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.js"
        integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
        crossorigin="anonymous"></script>
<script type="text/javascript" src="scripts/main.js"></script>
</html>
