<%@ page contentType="text/html;charset=UTF-8" %>
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
      <form onsubmit="return validateForm()" method="post" action="table/addHit">
        <div class="container">
          <label for="valueX">Выберите X:</label><br>
          <label><input type="radio" name="x" value="-4">-4</label>
          <label><input type="radio" name="x" value="-3">-3</label>
          <label><input type="radio" name="x" value="-2">-2</label>
          <label><input type="radio" name="x" value="-1">-1</label>
          <label><input type="radio" name="x" value="0" id="valueX">0</label>
          <label><input type="radio" name="x" value="1">1</label>
          <label><input type="radio" name="x" value="2">2</label>
          <label><input type="radio" name="x" value="3">3</label>
          <label><input type="radio" name="x" value="4">4</label><br>
          <span class="error" id="error_ch_X"></span>
        </div>
        <div class="container">
          <label for="in_Y">Введите Y:</label><br>
          <input name="y" type="text" id="in_Y" class="input_style" placeholder="от -3 до 5"><br>
          <span class="error" id="error_in_Y"></span>
        </div>
        <div class="container">
          <label for="sl_R">Выберите R:</label><br>
          <select name="r" id="sl_R" class="input_style">
            <option value="not_chosen" selected>--- R ---</option>
            <option value="1">1</option>
            <option value="1.5">1.5</option>
            <option value="2">2</option>
            <option value="2.5">2.5</option>
            <option value="3">3</option>
          </select><br>
          <span class="error" id="error_sl_R"></span>
        </div>
        <div class="container">
          <input type="submit" class="input_style" value="Отправить">
          <button type="button" class="input_style" onclick="
          //ОЧИСТКА ТАБЛИЦЫ">Очистить</button>
        </div>
      </form>
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
