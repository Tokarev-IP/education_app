# exchange_app_yandex
Использую Finnhub Api.
Данное Api имеет ограничение на количество запросов в минуту. При их превышении будет ошибка HTTP 429. Акции, которые не могут загрузится будут под ProgressBar.

Приложение состоит из трех фрагментов:
1 - List - список акций SP500
2 - Favorites - список избранных акций
3 - News - новости всего рынка

При нажатии на item акции в 1ом и 2ом фрагменте откроется фрагмент Chart и появится график цены акции. Закрытие этого фрагмента через кнопку "Назад" ("Back").
Можно осуществлять поиск акций в 1ом и 2ом фрагментах через EditText.
Добавлять в избранное и удалять из него можно по нажатию "звездочки".
