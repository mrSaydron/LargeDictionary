<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="main">БольшойСловарь</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Словарь <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="dictionary">Словарь</a></li>
                        <li><a href="learnset">Наборы</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Книги <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="books">Книги</a></li>
                        <li><a href="editbook">Добавить книгу</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span> Меню <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Настройки</a></li>
                        <li><a href="#">Выход</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>