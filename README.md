# Проектна задача по предметот Електронска и Мобилна Трговија

Целта на оваа проектна задача е да изградам веб апликација која што би ги имала сите функционалности што би биле потребни во еден комплетно функционален онлајн Пет Шоп.

**Технологии кои се користени при изградба на проектот**
               
+ **Backend**: Spring
+ **Frontend Design**: Thymeleaf
+ **Database**: Postgre SQL



## Структура на базата

Базата е направена во Postgre и е составена од вкупно 14 табели. Нејзината структура е прикажана на сликата подоле.

<img src="https://i.ibb.co/xz89ZML/db.png">

## Функционалности на проектот

### Автентикација

Автентикацијата ми е имплементирана со помош на **Spring Security**. Доколку некој првпат се најавува на веб страната, има опција да регистрира нов корисник или пак да се најави со неговиот google акаунт, кое што ми е направено со помош на **OAuth 2.0**.

<img src="https://i.ibb.co/MBb3nLb/najava.png" width="600">

Информациите што се зачувуваат за секој корисник се:
+ **корисничко име**
+ **лозинка (хеширана)**
+ **адреса**
+ **град**
+ **држава**
+ **тип на корисник**

Има три типа на корисници:
+ **Basic user** - при регистрација на нов корисник, тој се зачувува како basic user. Тоа значи дека тој ќе може да ги користи сите основни функционалности на страната како што се:
	+ Разгледување на продуктите
	+ Гледање на деталите на секој поединечен продукт
	+ Додавање на продуктите во кошничка
	+ Додавање на продукт во групата омилени (wihslist)
	+ Разгледување на својата кошничка
	+ Купување на продуктите што се наоѓаат во кошничката со помош на кредитна картичка
	+ Менување на својата адреса
+ **Moderator**
	+ Сите горенаведени функционалности
	+ Додавање на нова ликација во листата на физчки локации на продавницата
	+ Додавање на нов производител на продукти за миленичиња
	+ Додавање на нов продукт
+ **Admin**
	+ Сите горенаведени функционалности
	+ Додавање/бришење на модератори
	+ Додавање/бришење на купони за попут

### Преглед на сите продукти

Достапните продуктите може да се гледаат без разлика дали корисникот е автентициран или не. Продуктите може да се филтрираат врз основа на нивната категорија и миленечето за кое што се наменети.

<img src="https://i.ibb.co/h79vWHv/shop.png">

Со доближување на маусот на некој продукт ќе ни се појават три опции:
+ **Приказ на детали**
+ **Додавање во кошничка**
+ **Додавање во омилени**

### Приказ на детали за даден продукт

При кликање на даден продукт ќе ни се отвори деталната страна за него која што може да се види на сликата подоле.

<img src="https://i.ibb.co/5BL3XXV/single-product-1.png">

На оваа страна може да се види:
+ **Производителот на продуктот**
+ **Рејтингот на продуктот**
+ **Бројот на продадени продукти**
+ **Цената**

При избор на количината, може тој продукт да се додаде во кошничка со помош на копчето **Buy**.

Копчината **Edit** и **Delete** се достапни само доколку најавениот корисник е **Moderator** или  **Admin**.

На долниот дел од страната може да се види во кој физички продавници е достапен производот како и слични производи на него.

<img src="https://i.ibb.co/crFhFfQ/single-product-2.png">

### Преглед на кошничката

Во овој преглед може да се гледаат сите продукти кои што се додадени во кошничката.

Функционалности кои што ни се достапни во овој преглед:
+ **Бришење на продукт од кошничка**
+ **Купување на продуктите од дадената кошничка со помош на кредитна картичка**
+ **Внесување на купон за попуст на целокупната сума**
+ **Преглед на адресата на која што треба да се достават производите**


<img src="https://i.ibb.co/FYfcFpF/cart.png">

Купувањето на производите со помош на кредитна картичка е имплементирано со помош на тестирачката околина на stripe (https://stripe.com/) и изгледа како на сликата подоле.

<img src="https://i.ibb.co/S0RqkXW/stripe.png">


### Преглед на сопствениот профил

Функционалности кои што се достапни на оваа страна:
+ **Преглед на омилените производи (wishlist)**
+ **Додавање на продукти од омилени во кошничка**
+ **Бришење на продукти од омилени**
+ **Менување на адресата на дадениот корисник**
+ **Преглед на сите минати нарачки**



<img src="https://i.ibb.co/ngR7WFY/profile.png">

---

<img src="https://i.ibb.co/F3nDZzH/profile-2.png">

### Форма за додавање на нов продукт

Функционалности кои што се достапни на оваа страна:
+ **Додавање на нов продукт**
+ **Едитирање на веќе постоечки продукт**
+ **Бришење на постоечки продукт**

<img src="https://i.ibb.co/rMDgX0r/add-product.png">

### Форма за додавање на нов производител

Функционалности кои што се достапни на оваа страна:
+ **Додавање на нов производител**
+ **Едитирање на веќе постоечки производител**
+ **Бришење на постоечки производител**

<img src="https://i.ibb.co/98rbF61/add-manufacturer.png">

### Форма за додавање на нова физичка локација на продавницата

Функционалности кои што се достапни на оваа страна:
+ **Додавање на нова физичка локација на продавницата**
+ **Едитирање на веќе постоечки продавница**
+ **Бришење на постоечка продавница**

<img src="https://i.ibb.co/p2YCYr4/Add-Store-Location.png">

### Админ мени

Функционалности кои што се достапни на оваа страна:
+ **Додавање на нов купон за попуст**
+ **Бришење на веќе постоечки купон**
+ **Додавање на нов модератор**
+ **Бришење на модератор**

<img src="https://i.ibb.co/dPbxCcb/admin.png">





