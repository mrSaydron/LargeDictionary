# LargeDictionary
Проект сайта для изучения английских слов.  
Особенность состоит в том, что пользователь может создавать наборы слов для изучения, загружая или выбирая текст из уже 
имеющихся на сайте, в том числе выложенные другими пользователями. Для лучшего закрепления изученных слов, тексты можно 
сортировать по количеству содержащихся в них изученных слов, выбирая только тексты с изученными словами. 

Проект делается для изучения таких инструментов и фреймворков как Maven, Spring, Hibernate, Jackson, jQuery, Security.

#### Реализовано
- На слое модели классы слова и его переводов, классы книги, частотных слов и слов без перевода, классы пользователь, 
ролей, пользовательских списков изучаемых книг, слов и наборов слов. И репозиторий для всех классов с использованием SpringData.
- На сервисном слое классы сервисов для пользователя, работы с книгами и с переводами с методы необходимыми для контроллеров .
- На слое контроллеров контроллер заглавной страницы (main), контроллер словаря (dictionary).
- Часть функционала для ajax запросов по редактированию словаря пользователем.
- Заготовки для страниц: книга (book), список книг (books), словарь (dictionary), 
редактирование книги (editbook), изучение слов (learn), набор изучаемых слов (learnset), 
домашней (main).
- Частично реализованны страницы словарья (dictonary) и домашенй страницы (main).

#### Предстоит реализовать
- Редактирования и добавление нового слова в словарь на странице dictionary.
- Реализовать функционал на странице book: подсветка перевода слов.
- Реализовать функционал на странице books: добавление и удаление книг из списка пользователя, сортировка списка книг по задаеым параметрам.
- Реализовать функционал на странице editbook: добавление книги, парсинг, добавление перевода слов отсутствующих в словаре.
- Реализовать парсинг с помощью регулрных выражений.
- Реализовать функционал на странице learn: добавить в модель слова (LearnWord), функционал для изучения слов.
- Добавить авторизацию пользователя.
- Добавить регистрацию пользователя.
- Добавить админку.
- Не забывть писать тесты на все новые методы.
- Сформировать статический словарь вместо поиска по яндекс словарю.

