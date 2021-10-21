<%@ page import="table.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
  Table table = (Table) request.getServletContext().getAttribute("table");
  Hit hit = (Hit) request.getServletContext().getAttribute("hit");

  if (table == null) {
    table = new Table();
    request.getServletContext().setAttribute("table", table);
  } %>

<html lang="ru">
<head>
  <link rel="icon" href="img/favicon.ico" type="image/x-icon">
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="css/style.css" media="all">
  <link rel="stylesheet" type="text/css" href="css/table_style.css" media="all">
  <title>Лабораторная 1</title>
</head>
<body onload="init()">
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
        Выберите X:<br>

        <% for (int x = -4; x < 5; x++) {
          out.println(String.format("<input class=\"input_style\" type=\"button\" name=\"x\" value=\"%d\">", x));
        } %><br>

        <span class="error" id="error_X"></span>
      </div>
      <div class="container">
        <label for="valueY">Введите Y:</label><br>
        <input name="y" type="text" id="valueY" class="input_style"><br>
        <span class="error" id="error_Y"></span>
      </div>
      <div class="container">
        Выберите R:<br>

        <% for (int r = 1; r < 6; r++) {
          out.println(String.format("<input class=\"input_style\" type=\"button\" name=\"r\" value=\"%d\">", r));
        } %><br>

        <span class="error" id="error_R"></span>
      </div>
      <div class="container">
        <button type="button" class="input_style" onclick="addHit()">Отправить</button>
        <button type="button" class="input_style" onclick="clearTable()">Очистить</button>
      </div>
    </div>
    <div class="container bordered double_element float_right">
      <canvas id="canvas"></canvas>
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
          <th>Hit</th>
        </tr>

        <%= table.getValidHits()
                .stream()
                .map(h -> String.format("<tr class=\"history\"><td>%f</td><td>%f</td><td>%d</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                        h.getX(),
                        h.getY(),
                        h.getR(),
                        h.getCurrentTime(),
                        h.getExecutionTime(),
                        h.isHit())).collect(Collectors.joining("")) %>
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