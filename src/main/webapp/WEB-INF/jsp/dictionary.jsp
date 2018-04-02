<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<script type="text/javascript" src="resources/js/dictionary.js" defer></script>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

    <div class="container-fluid">
        <div class="row content">
            <!-- Отступ слева -->
            <div class="col-sm-2 sidenav">
            </div>
            <!-- Основной столбец -->
            <div class="col-sm-5 text-left">
                <div class="panel-group">
                    <div class="panel panel-success">
                        <div class="panel-heading">Словарь</div>
                        <div class="panel-body">
                            <table class="table table-hover" id="learnWordTable" cellspacing="0" width="100%">
                                <thead>
                                <tr onclick="#">
                                    <th>
                                        <label><input type="checkbox" value=""></label>
                                    </th>
                                    <th>СЛОВО</th>
                                    <th>ПЕРЕВОД</th>
                                    <th>ЧАСТОТА</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Дополнительный столбец -->
            <div class="col-sm-3 sidenav">
                <div class="panel-group">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="слово" id="searchWord">
                                <div class="input-group-btn">
                                    <button class="btn btn-default" type="submit">
                                        <i class="glyphicon glyphicon-search"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="panel panel-success">
                        <div class="panel-heading" onclick="add()">Добавить слово</div>
                    </div>
                    <div class="panel panel-success">
                        <div class="panel-heading" id="deleteWords">Удалить слова</div>
                    </div>
                    <div class="panel panel-success">
                        <div class="panel-heading">Новый набор</div>
                    </div>
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse3">Добавить в набор</a>
                            </h4>
                        </div>
                        <div id="collapse3" class="panel-collapse collapse">
                            <div class="panel-body">
                                <div class="list-group">
                                    <c:forEach items="${user.learnSets}" var="learnSet">
                                        <jsp:useBean id="learnSet" type="ru.mrak.LargeDictionary.model.user.LearnSet"/>
                                        <a class="list-group-item" href="#">${learnSet.name}</a>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Отступ справа -->
            <div class="col-sm-2 sidenav">
            </div>
        </div>
    </div>

    <!-- Окно создания/редактирования слова -->
    <div id="editWord" class="modal fade">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id="modalTitle"></h4>
                </div>
                <div class="modal-body">
                    <!-- Форма -->
                    <form class="form-horizontal" id="editForm">
                        <input type="hidden" class="id" value="id" name="id">
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="word">Слово:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="word" placeholder="Слово" name="word">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="transcription">Транскрипция:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="transcription" placeholder="Введите транскрипцию" name="transcription">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="translation">Перевод:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="translation" placeholder="Введите перевод" name="translation">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="button" class="btn btn-default" id="save-button">Сохранить</button>
                                <button type="button" class="btn btn-danger" style="display: none" id="delete-button">Удалить</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <!-- Предупреждение -->
                    <div class="alert alert-warning" style="display: none" id="user-edited">
                        Пользовательский вариант перевода. <a href="#" class="alert-link" id="return-transmition">Вернуть общий пeревод</a>.
                    </div>
                    <div class="alert alert-warning" style="display: none" id="alredy-use">
                        Слово уже есть в словаре.
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
