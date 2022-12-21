<h1>Roomer</h1>
<h2>Приложение для поиска соседей и комнат для совместного проживания.</h2>
<h2>Функционал</h2>
<ul>
  <li>Регистрация</li>
  <li>Чат с пользователями</li>
  <li>Отзывы о пользователях</li>
  <li>Список избранных комнат</li>
  <li>Просмотр информации о доступных комнатах и пользователях</li>
</ul>
<h2>Документация</h2>
<h3>Навигация</h3>
<p>Composable верстка панели навигации: NavbarHostContainer. Все подграфы основного графа навигации необходимо добавлять как функции-расширения класса NavGraphBuilder</p>
<p>Навигация в Compose работает на основе класса NavGraph. Навигация работает на основе deeplink. </p>
<p>Вложенная навигация должна располагаться в отдельных методах (пример - fun NavGraphBuilder.chatGraph(navController: NavHostController)). 
В данных методах нужно прописывать startDestination как базовую страницу для навигации (например Profile) и все ссылки на другие страницы внутри страницы навигации. </p>
<p>Ссылки на страницы добавляются в граф навигации при помощи метода NavGraphBuilder.composable(deeplink){ ComposeFunction.invoke()} </p>
