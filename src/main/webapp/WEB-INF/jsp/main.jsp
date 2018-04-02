<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
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
                        <div class="panel-heading">НАБОРЫ</div>
                        <div class="panel-body">
                            <div class="list-group">
                                <c:forEach items="${user.learnSets}" var="learnSet">
                                    <jsp:useBean id="learnSet" type="ru.mrak.LargeDictionary.model.user.LearnSet"/>
                                    <a class="list-group-item" href="#">
                                        <div class="row">
                                            <div class="col-sm-10">${learnSet.name}</div>
                                            <div class="col-sm-2 text-right">${learnSet.learnWords.size()}</div>
                                        </div>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <c:if test="${notReadBooks.size() > 0}">
                        <div class="panel panel-success">
                            <div class="panel-heading">НЕПРОЧИТАННЫЕ КНИГИ</div>
                            <div class="panel-body">
                                <div class="list-group">
                                    <c:forEach items="${notReadBooks}" var="notReadBook">
                                        <jsp:useBean id="notReadBook" type="ru.mrak.LargeDictionary.model.user.LearnBook"/>
                                        <a class="list-group-item" href="#">
                                            <div class="media">
                                                <div class="media-left">
                                                    <img src="resources/img/${notReadBook.book.pictureName}.png" class="media-object" style="width:60px">
                                                </div>
                                                <div class="media-body">
                                                    <h4 class="media-heading">${notReadBook.book.title}</h4>
                                                    <p>${notReadBook.book.description}</p>
                                                </div>
                                            </div>
                                        </a>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${readBooks.size() > 0}">
                        <div class="panel panel-success">
                            <div class="panel-heading">ПРОЧИТЕННЫЕ КНИГИ</div>
                            <div class="panel-body">
                                <div class="list-group">
                                    <c:forEach items="${readBooks}" var="readBook">
                                        <jsp:useBean id="readBook" type="ru.mrak.LargeDictionary.model.user.LearnBook"/>
                                        <a class="list-group-item" href="#">
                                            <div class="media">
                                                <div class="media-left">
                                                    <img src="resources/img/${readBook.book.pictureName}.png" class="media-object" style="width:60px">
                                                </div>
                                                <div class="media-body">
                                                    <h4 class="media-heading">Tom Sawyer</h4>
                                                    <p>Tom Sawyer is a boy of about 12 years of age, who resides in the fictional town of St. Petersburg, Missouri, in about the year 1845. Tom Sawyer's best friends include Joe Harper and Huckleberry Finn. In The Adventures of Tom Sawyer, Tom's infatuation with classmate Becky Thatcher is apparent as he tries to intrigue her with his strength, boldness, and handsome looks. He first sees her after he confessed his feelings for Amy Lawrence, one of his classmates. He lives with his half-brother Sid, his cousin Mary, and his stern Aunt Polly. There is no mention of Tom's father. Tom has another aunt, Sally Phelps, who lives very much so farther down the Mississippi.</p>
                                                </div>
                                            </div>
                                        </a>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
            <!-- Дополнительный столбец -->
            <div class="col-sm-3 sidenav">
                <div class="panel-group" id="accordion">
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">Популярные книги</a>
                            </h4>
                        </div>
                        <div id="collapse1" class="panel-collapse collapse in">
                            <div class="panel-body">

                                <div class="list-group">

                                    <a class="list-group-item" href="#">
                                        <div class="media">
                                            <div class="media-left">
                                                <img src="img/red-book.png" class="media-object" style="width:30px">
                                            </div>
                                            <div class="media-body">
                                                <h4 class="media-heading">Java learn</h4>
                                            </div>
                                        </div>
                                    </a>

                                    <a class="list-group-item" href="#">
                                        <div class="media">
                                            <div class="media-left">
                                                <img src="img/red-book.png" class="media-object" style="width:30px">
                                            </div>
                                            <div class="media-body">
                                                <h4 class="media-heading">SQL learn</h4>
                                            </div>
                                        </div>
                                    </a>

                                    <a class="list-group-item" href="#">
                                        <div class="media">
                                            <div class="media-left">
                                                <img src="img/red-book.png" class="media-object" style="width:30px">
                                            </div>
                                            <div class="media-body">
                                                <h4 class="media-heading">Linux learn</h4>
                                            </div>
                                        </div>
                                    </a>

                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">Новые книги</a>
                            </h4>
                        </div>
                        <div id="collapse2" class="panel-collapse collapse">
                            <div class="panel-body">
                                <div class="list-group">

                                    <a class="list-group-item" href="#">
                                        <div class="media">
                                            <div class="media-left">
                                                <img src="img/red-book.png" class="media-object" style="width:30px">
                                            </div>
                                            <div class="media-body">
                                                <h4 class="media-heading">Java learn</h4>
                                            </div>
                                        </div>
                                    </a>

                                    <a class="list-group-item" href="#">
                                        <div class="media">
                                            <div class="media-left">
                                                <img src="img/red-book.png" class="media-object" style="width:30px">
                                            </div>
                                            <div class="media-body">
                                                <h4 class="media-heading">SQL learn</h4>
                                            </div>
                                        </div>
                                    </a>

                                    <a class="list-group-item" href="#">
                                        <div class="media">
                                            <div class="media-left">
                                                <img src="img/red-book.png" class="media-object" style="width:30px">
                                            </div>
                                            <div class="media-body">
                                                <h4 class="media-heading">Linux learn</h4>
                                            </div>
                                        </div>
                                    </a>

                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse3">Популярные наборы</a>
                            </h4>
                        </div>
                        <div id="collapse3" class="panel-collapse collapse">
                            <div class="panel-body">
                                <div class="list-group">

                                    <a class="list-group-item" href="#">
                                        <div class="row">
                                            <div class="col-sm-10">Неправильные глаголы</div>
                                            <div class="col-sm-2 text-right">120</div>
                                        </div>
                                    </a>

                                    <a class="list-group-item" href="#">
                                        <div class="row">
                                            <div class="col-sm-10">Порядковые числительные</div>
                                            <div class="col-sm-2 text-right">25</div>
                                        </div>
                                    </a>

                                    <a class="list-group-item" href="#">
                                        <div class="row">
                                            <div class="col-sm-10">Сининимы</div>
                                            <div class="col-sm-2 text-right">350</div>
                                        </div>
                                    </a>

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


</body>
</html>