<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="table" class="table.Table" scope="application"/>

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
        Выберите X:<br>
        <c:forEach items="${[-4, -3, -2, -1, 0, 1, 2, 3, 4]}" var="x">
          <input class="input_style" type="button" name="x" value="${x}">
        </c:forEach><br>
        <span class="error" id="error_X"></span>
      </div>
      <div class="container">
        <label for="valueY">Введите Y:</label><br>
        <input name="y" type="text" id="valueY" class="input_style"><br>
        <span class="error" id="error_Y"></span>
      </div>
      <div class="container">
        Выберите R:<br>
        <c:forEach items="${[1, 2, 3, 4, 5]}" var="r">
          <input class="input_style" type="button" name="r" value="${r}">
        </c:forEach><br>
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
          <th>Result</th>
        </tr>
        <c:forEach var="hit" items="${table.validHits}">
          <tr class="validHit">
            <td>${hit.x}</td>
            <td>${hit.y}</td>
            <td>${hit.r}</td>
            <td>${hit.currentTime}</td>
            <td>${hit.executionTime}</td>
            <td>${hit.result}</td>
          </tr>
        </c:forEach>
        <c:if test="${table.countInvalidHits > 0}">
          <tr>
            <td id="invalidHits" colspan="6">Невалидных запросов: ${table.countInvalidHits}</td>
          </tr>
        </c:if>
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